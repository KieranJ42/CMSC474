package cmsc420_s23;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

//import cmsc420_s23.ClusterAssignment.AssignedPair;

public class SMkdTree<LPoint extends LabeledPoint2D> {
		
	private final int REBUILDOFFSET;
	private final LPoint STARTCENTER;
	private Rectangle2D rootCell;
	private Node root;
	private int deleteCount;
	
	private abstract class Node {
		protected LinkedList<LPoint> contenders;
		abstract LPoint findNode(Point2D q);
		abstract Node insertNode(LPoint pt, Rectangle2D cell) throws Exception;
		abstract Node deleteNode(Point2D pt);
		abstract int size();
		abstract boolean inNode();
		abstract LPoint leftMostPoint();
		abstract ArrayList<String> listNodes();
		abstract ArrayList<String> listNodesWithCenters();
		abstract ArrayList<LPoint> getPointList();
		abstract ArrayList<ClusterAssignment<LPoint>.AssignedPair> makeAssignments(ClusterAssignment<LPoint> ca);
		public String toString() {
			return "";
		}
		abstract void addCenter(LPoint center);
		abstract ArrayList<LPoint> nearestNeighborNode(Point2D center, ArrayList<LPoint> bestPoint);
		abstract Node bulkCreate(ArrayList<LPoint> points, Rectangle2D cell) throws Exception;
	}
	
	private class ByXThenY implements Comparator<LPoint> {
		public int compare(LPoint pt1, LPoint pt2) {
			if (pt1.get(0)!=pt2.get(0)) {
				return pt1.get(0)-pt2.get(0)>0?1:-1;
			} else {
				return pt1.get(1)-pt2.get(1)>0?1:-1;
			}
		}
	}
	private class ByYThenX implements Comparator<LPoint> {
		public int compare(LPoint pt1, LPoint pt2) {
			if (pt1.get(1)!=pt2.get(1)) {
				return pt1.get(1)-pt2.get(1)>0?1:-1;
			} else {
				return pt1.get(0)-pt2.get(0)>0?1:-1;
			}
		}
	}
	
	public double max4(double a, double b, double c, double d) {
		return (a>b)?((a>c)?(a>d?a:d):(c>d?c:d)):((b>c)?(b>d?b:d):(c>d?c:d));
	}
	
	private class InternalNode extends Node {
		int cutDim;
		double cutVal;
		Node left, right;
		int size; //n counter from slides (Lect12)
		int insertionCounter; //m counter from slides (Lect12)
		Rectangle2D cell;
		double rMin;
		
		ArrayList<ClusterAssignment<LPoint>.AssignedPair> makeAssignments(ClusterAssignment<LPoint> ca) {
			ArrayList<ClusterAssignment<LPoint>.AssignedPair> retVal = new ArrayList<ClusterAssignment<LPoint>.AssignedPair>();
			retVal.addAll(left.makeAssignments(ca));
			retVal.addAll(right.makeAssignments(ca));
			return retVal;
		}
		
		void addCenter(LPoint center) {
			if (cell.contains(center.getPoint2D()) 
					|| cell.distanceSq(center.getPoint2D())<=rMin) {
				contenders.add(center);
				contenders = fixContenders(contenders);
				left.addCenter(center);
				right.addCenter(center);
			}
				
		}
		
		ArrayList<LPoint> nearestNeighborNode(Point2D center, ArrayList<LPoint> bestPoint) {
			if (bestPoint == null) {
				bestPoint = new ArrayList<LPoint>();
			}
			
			//create cells based on value of cutDim
			Rectangle2D lcell = new Rectangle2D(cell.low,
					(cutDim==0?new Point2D(cutVal,cell.high.get(cutDim==0?1:0))
							:new Point2D(cell.high.get(cutDim==0?1:0),cutVal)));
			Rectangle2D rcell = new Rectangle2D((cutDim==0? 
					new Point2D(cutVal,cell.low.get(cutDim==0?1:0))
							:new Point2D(cell.low.get(cutDim==0?1:0),cutVal)),
									cell.high);
			
			
			if (bestPoint.size() == 0) {
				// if size == 0 we must call nearestNeighbor on one subtree 
				// and then potentially on the other
				if (lcell.distanceSq(center)<=rcell.distanceSq(center)) {
					bestPoint = left.nearestNeighborNode(center, bestPoint);//rec1 //rec4
					if (bestPoint.size()==0 || 
							bestPoint.get(0).getPoint2D().distanceSq(center)>rcell.distanceSq(center)) {
						bestPoint = right.nearestNeighborNode(center, bestPoint);
					}
				} else {
					bestPoint = right.nearestNeighborNode(center, bestPoint);//rec2 //rec3
					if (bestPoint.size()==0 || 
							bestPoint.get(0).getPoint2D().distanceSq(center)>=lcell.distanceSq(center)) {
						//System.out.println("arrived 2 at cutDim: " + cutDim + " and cutVal: " + cutVal);
						bestPoint = left.nearestNeighborNode(center, bestPoint);
					}
				}
			} else {
				// if size != 0 we might need to call nearestNeighbor on one
				// subtree and if so maybe on the other as well
				if (lcell.distanceSq(center)<=rcell.distanceSq(center)) {
					if(bestPoint.get(0).getPoint2D().distanceSq(center)>=lcell.distanceSq(center)) {
						bestPoint = left.nearestNeighborNode(center, bestPoint);
						if (bestPoint.get(0).getPoint2D().distanceSq(center)>rcell.distanceSq(center)) {
							bestPoint = right.nearestNeighborNode(center, bestPoint);
						}
					}
				} else {
					if(bestPoint.get(0).getPoint2D().distanceSq(center)>rcell.distanceSq(center)) {
						bestPoint = right.nearestNeighborNode(center, bestPoint);
						if (bestPoint.get(0).getPoint2D().distanceSq(center)>=lcell.distanceSq(center)) {
							bestPoint = left.nearestNeighborNode(center, bestPoint);
						}
					}
				}
			}
			return bestPoint;
			
			
			
		}
		
		Node deleteNode(Point2D pt) {
			if (pt.get(cutDim)<cutVal) {
				Node node = left.deleteNode(pt);
				if (node!=null)
					size--;
				return node;
			} else {
				Node node = right.deleteNode(pt);
				if (node!=null)
					size--;
				return node;
			}
		}
		
		public String toString() {
			return "{"+left.toString() + "\n[Cut Dim: "+cutDim+"  Cut Val: "+cutVal+
					"   Size: "+size+"   Insertions: "+insertionCounter+"]\n"+
					right.toString()+"}";
		}
		
		boolean inNode() { return true; }
		
		LPoint leftMostPoint() { return left.leftMostPoint(); }
		
		public InternalNode(int cutDim, double cutVal, Rectangle2D cell, LinkedList<LPoint> contends) {
			contenders = new LinkedList<LPoint>(contends);
			this.cutDim = cutDim;
			this.cutVal = cutVal;
			size = 0;
			insertionCounter = 0;
			this.cell = cell;
			contenders = fixContenders(contenders);
			
			Rectangle2D lcell = new Rectangle2D(cell.low,
					(cutDim==0?new Point2D(cutVal,cell.high.get(cutDim==0?1:0))
							:new Point2D(cell.high.get(cutDim==0?1:0),cutVal)));
			Rectangle2D rcell = new Rectangle2D((cutDim==0? 
				new Point2D(cutVal,cell.low.get(cutDim==0?1:0))
				:new Point2D(cell.low.get(cutDim==0?1:0),cutVal)),
				cell.high);
			
			this.left = new ExternalNode(null, contenders,lcell);
			this.right = new ExternalNode(null, contenders,rcell);
		}
		
		public LinkedList<LPoint> fixContenders(LinkedList<LPoint> contenders) {
			double min;
			LPoint temp = contenders.getFirst();
			double tempMin;
			min = max4(temp.getPoint2D().distanceSq(cell.low),
					temp.getPoint2D().distanceSq(cell.high),
					temp.getPoint2D().distanceSq(
							new Point2D(cell.high.getX(),cell.low.getY())),
					temp.getPoint2D().distanceSq(
							new Point2D(cell.low.getX(),cell.high.getY()))
					);
			for (int i = 1; i < contenders.size(); i++) {
				temp = contenders.get(i);
				tempMin = max4(temp.getPoint2D().distanceSq(cell.low),
						temp.getPoint2D().distanceSq(cell.high),
						temp.getPoint2D().distanceSq(
								new Point2D(cell.high.getX(),cell.low.getY())),
						temp.getPoint2D().distanceSq(
								new Point2D(cell.low.getX(),cell.high.getY()))
						);
				min = min<tempMin?min:tempMin;
			}
			rMin = min;
			for (int i = contenders.size()-1; i >= 0; i--) {
				temp = contenders.get(i);
				if(cell.distanceSq(temp.getPoint2D())>min)
					contenders.remove(i);
			}
			return contenders;
		}
		
		int size() { return size; }
		
		LPoint findNode(Point2D q) {
			if (q.get(cutDim)<cutVal) {
				return left.findNode(q);
			} else {
				return right.findNode(q);
			}
		}
		
		Node bulkCreate(ArrayList<LPoint> points, Rectangle2D cell) {
			//create externalNode if size == 0 or 1
			if (points.size()==0) {
				return new ExternalNode(null,contenders,cell);
			}
			if (points.size()==1) {
				return new ExternalNode(points.get(0),contenders,cell);
			}
			
			//otherwise have to split into two new cells
			int myDim = cell.getWidth(0)>=cell.getWidth(1)?0:1;
			Collections.sort(points, (myDim==0?new ByXThenY():new ByYThenX()));
			if (points.get(0).get(myDim)==points.get(points.size()-1).get(myDim)) {
				//degenerate nodes in a line
				myDim = myDim==0?1:0; //switch cutting dimension
			}
			
			double myVal = (cell.getHigh().get(myDim)+cell.getLow().get(myDim))/2;
			if (myVal<points.get(0).get(myDim)) {
				myVal = points.get(0).get(myDim); //slide midpoint
			} else if (myVal>points.get(points.size()-1).get(myDim)) {
				myVal = points.get(points.size()-1).get(myDim); //slide midpoint
			}
			
			//seperate points into new lists
			ArrayList<LPoint> leftPoints = new ArrayList<LPoint>();
			ArrayList<LPoint> rightPoints = new ArrayList<LPoint>();
			for (int i = 0; i < points.size(); i++) {
				if (points.get(i).get(myDim)<myVal) {
					leftPoints.add(points.get(i));
				} else {
					rightPoints.add(points.get(i));
				}
			}
			
			
			Rectangle2D lcell = new Rectangle2D(cell.low,
						(myDim==0?new Point2D(myVal,cell.high.get(myDim==0?1:0))
								:new Point2D(cell.high.get(myDim==0?1:0),myVal)));
			Rectangle2D rcell = new Rectangle2D((myDim==0? 
					new Point2D(myVal,cell.low.get(myDim==0?1:0))
					:new Point2D(cell.low.get(myDim==0?1:0),myVal)),
					cell.high);
			
		
			
			InternalNode returnNode = new InternalNode(myDim, myVal, cell, contenders);
			returnNode.left = bulkCreate(leftPoints, lcell);
			returnNode.right = bulkCreate(rightPoints, rcell);
			returnNode.size = points.size();
			
			return returnNode;
			
		}
		
		
		
		ArrayList<LPoint> getPointList() {
			ArrayList<LPoint> temp = new ArrayList<LPoint>();
			temp.addAll(left.getPointList());
			temp.addAll(right.getPointList());
			return temp;
		}
		
		Node insertNode(LPoint pt, Rectangle2D cell) throws Exception {
			
			if (pt.get(cutDim)<cutVal) {
				left = left.insertNode(pt, cutDim==0?(new Rectangle2D(this.cell.low,
						new Point2D(cutVal,this.cell.high.getY()))):
							(new Rectangle2D(this.cell.low,
									new Point2D(this.cell.high.getX(),cutVal))));
				
			} else {
				right = right.insertNode(pt, cutDim==0?(new Rectangle2D(
						new Point2D(cutVal,this.cell.low.getY()),this.cell.high)):
							(new Rectangle2D(new Point2D(this.cell.low.getX(),cutVal)
									,this.cell.high)));
				
			}
			size++;
			insertionCounter++;
			if (insertionCounter > (size + REBUILDOFFSET)/2) {
				return bulkCreate(getPointList(),cell);
			} else {
				return this;
			}
		}

		ArrayList<String> listNodes() {
			ArrayList<String> retVal = new ArrayList<String>();
			if (cutDim==0)
				retVal.add("(x=" + cutVal + ") " + size + ":" + insertionCounter);
			else
				retVal.add("(y=" + cutVal + ") " + size + ":" + insertionCounter);
			retVal.addAll(right.listNodes());
			retVal.addAll(left.listNodes());
			return retVal;
		}
		
		ArrayList<String> listNodesWithCenters() {
			ArrayList<String> retVal = new ArrayList<String>();
			String temp;
			if (cutDim==0)
				temp = "(x=" + cutVal + ") " + size + ":" + insertionCounter;
			else
				temp = "(y=" + cutVal + ") " + size + ":" + insertionCounter;
			temp += " => {";
			Collections.sort(contenders, new Alphabetical());
			Iterator<LPoint> iter = contenders.iterator();
			int count = 0;
			while(iter.hasNext()&&count<10) {
				temp+=iter.next().getLabel() + " ";
				count++;
			}
			if (iter.hasNext()) {
				temp = temp.substring(0, temp.length()-1) + "...}";
			} else {
				temp = temp.substring(0, temp.length()-1) + "}";
			}
			retVal.add(temp);

			Rectangle2D lcell = new Rectangle2D(cell.low,
					(cutDim==0?new Point2D(cutVal,cell.high.get(cutDim==0?1:0))
							:new Point2D(cell.high.get(cutDim==0?1:0),cutVal)));
			Rectangle2D rcell = new Rectangle2D((cutDim==0? 
					new Point2D(cutVal,cell.low.get(cutDim==0?1:0))
							:new Point2D(cell.low.get(cutDim==0?1:0),cutVal)),
									cell.high);
			
			retVal.addAll(right.listNodesWithCenters());
			retVal.addAll(left.listNodesWithCenters());
			return retVal;
		}
	}
	
	private class Alphabetical implements Comparator<LPoint> {
		public int compare(LPoint pt1, LPoint pt2) {
			return pt1.getLabel().compareTo(pt2.getLabel());
		}
	}
	
	private class ExternalNode extends Node {
		LPoint point;
		Rectangle2D cell;
		
		ArrayList<ClusterAssignment<LPoint>.AssignedPair> makeAssignments(ClusterAssignment<LPoint> ca) {
			if (point!=null) {
				double min = point.getPoint2D().distanceSq(contenders.getFirst().getPoint2D());
				LPoint assignMin = contenders.getFirst();
				//String str = contenders.getFirst().getLabel();
				Iterator<LPoint> iter = contenders.iterator();
				double tempMin;
				LPoint tempPoint;
				while (iter.hasNext()) {
					tempPoint = iter.next();
					tempMin = point.getPoint2D().distanceSq(tempPoint.getPoint2D());
					if (tempMin<min) {
						min = tempMin;
						assignMin = tempPoint;
					} else if (tempMin==min) {
						if(assignMin.getX()>tempPoint.getX()) {
							min = tempMin;
							assignMin = tempPoint;
						} else if (assignMin.getX()==tempPoint.getX() && assignMin.getY()>tempPoint.getY()) {
							min = tempMin;
							assignMin = tempPoint;
						}
					}
				}
				ArrayList<ClusterAssignment<LPoint>.AssignedPair> retVal = new ArrayList<ClusterAssignment<LPoint>.AssignedPair>();
				retVal.add(ca.new AssignedPair(point,assignMin,min));
				return retVal;
			}
			return new ArrayList<ClusterAssignment<LPoint>.AssignedPair>();
		}
		
		void addCenter(LPoint center) {
			contenders.add(center);
		}
		
		ArrayList<LPoint> nearestNeighborNode(Point2D center, ArrayList<LPoint> bestPoint) {
			if (point == null) {
				return bestPoint;
			}
			if (bestPoint == null) {
				bestPoint = new ArrayList<LPoint>();
			}
			if(bestPoint.size()==0 ) {
				bestPoint.add(point);
			} else if (bestPoint.get(0).getPoint2D().distance(center)>=point.getPoint2D().distance(center)) {
				if(bestPoint.get(0).getPoint2D().distance(center)==point.getPoint2D().distance(center)) {
					if (bestPoint.get(0).getX()>point.getX()) {
						bestPoint.add(0, point);
					} else if (bestPoint.get(0).getX()==point.getX() &&
							bestPoint.get(0).getY()>point.getY()) {
						//new best point because of lower lexicographic score
						bestPoint.add(0, point);
					} else {
						bestPoint.add(point);
					}
				} else {
					//must be a best point because dist is less than old best
					bestPoint.add(0, point);
				}
			} else {
				bestPoint.add(point);
			}
			return bestPoint;
		}
		
		Node bulkCreate(ArrayList<LPoint> points, Rectangle2D cell) throws Exception {
			//will only ever be called on a tree of size 0 or 1
			if (points.size()==0) {
				return new ExternalNode(null,contenders,cell);
			} 
			return new ExternalNode(points.get(0),contenders,cell);
		}
		
		Node deleteNode(Point2D pt) {
			if (point == null) {
				return null;
			} else if (point.getPoint2D().equals(pt)) {
				point = null;
				return this;
			} else {
				return null;
			}
		}
		
		int size() { return point==null?0:1; }
		
		public String toString() {
			if (point != null)
				return "("+point.getX()+", "+point.getY()+") ";
			else
				return "null point";
		}
		
		ArrayList<LPoint> getPointList() {
			ArrayList<LPoint> temp = new ArrayList<LPoint>();
			if (point!=null) {
				temp.add(point);
			}
			return temp;
		}
		
		ExternalNode(LPoint point, LinkedList<LPoint> contends, Rectangle2D cell) {
			this.point = point;
			contenders = new LinkedList<LPoint>(contends);
			this.cell = cell;
		}
		
		ExternalNode(LPoint point, LPoint startCenter, Rectangle2D cell) {
			contenders = new LinkedList<LPoint>();
			this.point = point;
			contenders.add(startCenter);
			this.cell = cell;
		}
		
		boolean inNode() { return false; }
		
		LPoint leftMostPoint() { return point; }
		
		LPoint findNode(Point2D q) {
			if (point == null) {
				return null;
			}
			if (point.getPoint2D().equals(q)) {
				return point;
			} else {
				return null;
			}
		}
		
		Node insertNode(LPoint pt, Rectangle2D cell) throws Exception {
			
			if (point == null) {
				point = pt;
				this.cell = cell;
				return this;
			}
			if (point.getPoint2D().equals(pt.getPoint2D())) {
				throw new Exception("Insertion of duplicate point");
			}
			//find cutDim
			int cutDim = cell.getWidth(0)>=cell.getWidth(1)?0:1;
			if (pt.get(cutDim)==point.get(cutDim)) {
				cutDim = cutDim==0?1:0; //if x is cutDim and x's are equal make
				//cutDim Y and vice versa
			}
			
			//find our cutVal
			double cutVal = (cell.high.get(cutDim)+cell.low.get(cutDim))/2;
			double pointVal = point.get(cutDim);
			double ptVal = pt.get(cutDim);
			if (cutVal>pointVal && cutVal>ptVal) {
				cutVal = pointVal>ptVal?pointVal:ptVal;
			} else if (cutVal<pointVal && cutVal<ptVal) {
				cutVal = pointVal<ptVal?pointVal:ptVal;
			}
			
			//create our new internal node
			InternalNode node = new InternalNode(cutDim, cutVal, cell, contenders);
			//add our external nodes
			
			Rectangle2D lcell = new Rectangle2D(cell.low,
					(cutDim==0?new Point2D(cutVal,cell.high.get(cutDim==0?1:0))
							:new Point2D(cell.high.get(cutDim==0?1:0),cutVal)));
			Rectangle2D rcell = new Rectangle2D((cutDim==0? 
					new Point2D(cutVal,cell.low.get(cutDim==0?1:0))
							:new Point2D(cell.low.get(cutDim==0?1:0),cutVal)),
									cell.high);
			
			node.insertNode(pt, lcell);
			node.insertNode(point, rcell);
			node.insertionCounter = 0;
			return node;
		}

		ArrayList<String> listNodes() {
			ArrayList<String> retVal = new ArrayList<String>();
			retVal.add("["+(point==null?"null":point.toString()) + "]");
			return retVal;
		}
		
		ArrayList<String> listNodesWithCenters() {
			ArrayList<String> retVal = new ArrayList<String>();
			String temp;
			temp = "["+(point==null?"null":point.toString()) + "]";
			
			contenders = fixContenders(contenders);
			temp += " => {";
			Collections.sort(contenders, new Alphabetical());
			Iterator<LPoint> iter = contenders.iterator();
			int count = 0;
			while(iter.hasNext()&&count<10) {
				temp+=iter.next().getLabel() + " ";
				count++;
			}
			if (iter.hasNext()) {
				temp = temp.substring(0, temp.length()-1) + "...}";
			} else {
				temp = temp.substring(0, temp.length()-1) + "}";
			}
			
			retVal.add(temp);
			return retVal;
		}

		public LinkedList<LPoint> fixContenders(LinkedList<LPoint> contenders) {
			double min;
			LPoint temp = contenders.getFirst();
			double tempMin;
			min = max4(temp.getPoint2D().distanceSq(cell.low),
					temp.getPoint2D().distanceSq(cell.high),
					temp.getPoint2D().distanceSq(
							new Point2D(cell.high.getX(),cell.low.getY())),
					temp.getPoint2D().distanceSq(
							new Point2D(cell.low.getX(),cell.high.getY()))
					);
			for (int i = 1; i < contenders.size(); i++) {
				temp = contenders.get(i);
				tempMin = max4(temp.getPoint2D().distanceSq(cell.low),
						temp.getPoint2D().distanceSq(cell.high),
						temp.getPoint2D().distanceSq(
								new Point2D(cell.high.getX(),cell.low.getY())),
						temp.getPoint2D().distanceSq(
								new Point2D(cell.low.getX(),cell.high.getY()))
						);
				min = min<tempMin?min:tempMin;
			}
			for (int i = contenders.size()-1; i >= 0; i--) {
				temp = contenders.get(i);
				if(cell.distanceSq(temp.getPoint2D())>min)
					contenders.remove(i);
			}
			return contenders;
		}
		
	}
	
	public SMkdTree(int rebuildOffset, Rectangle2D rootCell, LPoint startCenter) { 
		REBUILDOFFSET = rebuildOffset;
		this.rootCell = rootCell;
		root = new ExternalNode(null,startCenter,rootCell);
		STARTCENTER = startCenter;
		deleteCount = 0;
	}
	public void clear() { 
		root = new ExternalNode(null,STARTCENTER,rootCell);
		deleteCount = 0;
	}
	public int size() { return root==null?0:root.size(); }
	public int deleteCount() { return deleteCount; }
	public LPoint find(Point2D q) { return root.findNode(q); }
	public void insert(LPoint pt) throws Exception { 
		if (!rootCell.contains(pt.getPoint2D()))
			throw new Exception("Attempt to insert a point outside bounding box");
		root = root.insertNode(pt, rootCell);
	}
	public void delete(Point2D pt) throws Exception { 
		Node node = root.deleteNode(pt);
		if (node == null) {
			throw new Exception("Deletion of nonexistent point");
		} else {
			deleteCount++;
			if (deleteCount>size()) {
				root = root.bulkCreate(root.getPointList(),rootCell);
				deleteCount = 0;
			}
		}
	}
	public ArrayList<String> list() { return root.listNodes(); }
	public LPoint nearestNeighbor(Point2D center) { 
		if (size()==0) 
			return null;
		ArrayList<LPoint> points = root.nearestNeighborNode(center, null); 
		return points.get(0);
	}
	public ArrayList<LPoint> nearestNeighborVisit(Point2D center) { 
		if (size()==0) 
			return new ArrayList<LPoint>();
		ArrayList<LPoint> points = root.nearestNeighborNode(center, null); 
		Collections.sort(points, new ByXThenY());
		return points;
	}

	public String toString() {
		return root.toString();
	}
	
	
	// ----------------------------------------------------------------
	// Possible new additions for Programming Assignment 3
	// You may modify/add/remove these as you see fit, since they will 
	// just be used internally by your code.
	// ----------------------------------------------------------------
	void addCenter(LPoint center) { 
		root.addCenter(center);
	}
	ArrayList<String> listWithCenters() { return root.listNodesWithCenters(); }
	
	ArrayList<ClusterAssignment<LPoint>.AssignedPair> listAssignments(ClusterAssignment<LPoint> ca) {
		ArrayList<ClusterAssignment<LPoint>.AssignedPair> assignments = root.makeAssignments(ca);
		return assignments;
	}
}

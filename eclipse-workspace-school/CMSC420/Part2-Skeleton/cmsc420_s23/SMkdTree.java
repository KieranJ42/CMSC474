package cmsc420_s23;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

//Following the same convention as standard kd-trees, 
//if a point lies on the cutting line, it is placed in the right subtree.

//For the first part of the assignment (due Wed, Apr 5), implement the 
//constructor and the functions size, find, insert, clear, and list.

public class SMkdTree<LPoint extends LabeledPoint2D> {
	
	private final int REBUILDOFFSET;
	private Rectangle2D rootCell;
	private Node root;
	private int deleteCount;
	
	private abstract class Node {
		abstract LPoint findNode(Point2D q);
		abstract Node insertNode(LPoint pt, Rectangle2D cell) throws Exception;
		abstract Node deleteNode(Point2D pt);
		abstract int size();
		abstract boolean inNode();
		abstract LPoint leftMostPoint();
		abstract ArrayList<String> listNodes();
		abstract ArrayList<LPoint> getPointList();
		public String toString() {
			return "";
		}
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
	
	private class InternalNode extends Node {
		int cutDim;
		double cutVal;
		Node left, right;
		int size; //n counter from slides (Lect12)
		int insertionCounter; //m counter from slides (Lect12)
		Rectangle2D cell;
		
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
		
		public InternalNode(int cutDim, double cutVal, Rectangle2D cell) {
			this.cutDim = cutDim;
			this.cutVal = cutVal;
			size = 0;
			insertionCounter = 0;
			this.cell = cell;
			this.left = new ExternalNode(null);
			this.right = new ExternalNode(null);
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
				return new ExternalNode(null);
			}
			if (points.size()==1) {
				return new ExternalNode(points.get(0));
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
			
		
			
			InternalNode returnNode = new InternalNode(myDim, myVal, cell);
			returnNode.left = bulkCreate(leftPoints,lcell);
			returnNode.right = bulkCreate(rightPoints,rcell);
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
	}
	
	private class ExternalNode extends Node {
		LPoint point;
		
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
				return new ExternalNode(null);
			} 
			return new ExternalNode(points.get(0));
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
		
		ExternalNode(LPoint point) {
			this.point = point;
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
			InternalNode node = new InternalNode(cutDim, cutVal, cell);
			//add our external nodes
			node.insertNode(pt, cell);
			node.insertNode(point, cell);
			node.insertionCounter = 0;
			return node;
		}

		ArrayList<String> listNodes() {
			ArrayList<String> retVal = new ArrayList<String>();
			retVal.add("["+(point==null?"null":point.toString()) + "]");
			return retVal;
		}

		
	}
	
	public SMkdTree(int rebuildOffset, Rectangle2D rootCell) { 
		REBUILDOFFSET = rebuildOffset;
		this.rootCell = rootCell;
		root = new ExternalNode(null);
		deleteCount = 0;
	}
	public void clear() { root = new ExternalNode(null); }
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
	
	
	// The following is needed only for the Challenge Problem

	private class LPointIterator implements Iterator<LPoint> {
		public LPointIterator() { /* ... */ }
		public boolean hasNext() { /* ... */ return false; }
		public LPoint next() throws NoSuchElementException { /* ... */ return null; }

	}
	public LPointIterator iterator() { /* ... */ return new LPointIterator(); }
}


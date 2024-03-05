package cmsc420_s23;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ClusterAssignment<LPoint extends LabeledPoint2D> {
	
	private SMkdTree<LPoint> kdTree; // storage for the sites
	private ArrayList<LPoint> centers;
	private final LPoint STARTCENTER;
	private final int REBUILDOFFSET;
	private final Rectangle2D BBOX;
	
	// ------------------------------------------------------------------------
	// The following class is not required, but you may find it helpful. It
	// represents the triple (site, center, squared-distance). Feel free to
	// delete or modify.
	// ------------------------------------------------------------------------
	public class AssignedPair implements Comparable<AssignedPair> {
		private LPoint site; // a site
		private LPoint center; // its assigned center
		private double distanceSq; // the squared distance between them
		public AssignedPair(LPoint site, LPoint center, double distanceSq) {
			this.site = site;
			this.center = center;
			this.distanceSq = distanceSq;
		}
		public int compareTo(AssignedPair o) { 
			if (distanceSq == o.distanceSq) {
				if (site.getX()==o.site.getX()) {
					return site.getY()>o.site.getY()?1:-1;
				} else
					return site.getX()>o.site.getX()?1:-1;
			} else
				return distanceSq>o.distanceSq?1:-1;
			
		} // for sorting
	}
	
	private class Alphabetical implements Comparator<LPoint> {
		public int compare(LPoint pt1, LPoint pt2) {
			return pt1.getLabel().compareTo(pt2.getLabel());
		}
	}

	public ClusterAssignment(int rebuildOffset, Rectangle2D bbox, LPoint startCenter) { 
		kdTree = new SMkdTree<LPoint>(rebuildOffset, bbox, startCenter);
		centers = new ArrayList<LPoint>();
		centers.add(startCenter);
		STARTCENTER = startCenter;
		BBOX = bbox;
		REBUILDOFFSET = rebuildOffset;
	}
	public void addSite(LPoint site) throws Exception { kdTree.insert(site); }
	public void deleteSite(LPoint site) throws Exception {  
		kdTree.delete(site.getPoint2D());
	}
	public void addCenter(LPoint center) throws Exception { 
		kdTree.addCenter(center);
		centers.add(center);
	}
	public int sitesSize() { return kdTree.size(); }
	public int centersSize() { return centers.size(); }
	public void clear() { 
		kdTree.clear();
		kdTree = new SMkdTree<LPoint>(REBUILDOFFSET, BBOX, STARTCENTER);
		centers = new ArrayList<LPoint>();
		centers.add(STARTCENTER);
	}
	public ArrayList<String> listKdWithCenters() { return kdTree.listWithCenters(); }
	
	public ArrayList<String> listCenters() { 
		ArrayList<String> retVal = new ArrayList<String>();
		Collections.sort(centers, new Alphabetical());
		for (int i = 0; i < centers.size(); i++) {
			retVal.add(centers.get(i).toString());
		}
		return retVal;
	}
	public ArrayList<String> listAssignments() { 
		ArrayList<AssignedPair> assignments = kdTree.listAssignments(this);
		Collections.sort(assignments);
		ArrayList<String> retVal = new ArrayList<String>();
		for (int i = 0; i < assignments.size(); i++) {
			retVal.add("["+assignments.get(i).site.getLabel()+"->"+
					assignments.get(i).center.getLabel()+"] distSq = "+
					assignments.get(i).distanceSq);
		}
		return retVal;
	}
	public void deleteCenter(LPoint center) throws Exception { /* ... */ } // For extra credit only
}

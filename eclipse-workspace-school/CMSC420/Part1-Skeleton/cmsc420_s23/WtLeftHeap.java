package cmsc420_s23;

import java.util.ArrayList;

public class WtLeftHeap<Key extends Comparable<Key>, Value> {
	
	Node root;
	int size;
	int printMethod = 0;
	
	public class Locator { 
		
		private Node node;
		//private int loc;
		
		public String toString() {
			
			return "Value: " + node.val + "\tKey: " + node.key
					+ "\tweight: " + node.weight;
		}
		
		private Locator (Node node/*, int loc*/) {
			this.node = node;
			//this.loc = loc;
		}	
		private Node getNode() {
			return node;
		}
		private void setNode(Node node) {
			this.node = node;
		}
		{/*private int getLoc() {
			return loc;
		}
		
		private void setLoc(int newLoc) {
			loc = newLoc;
		}
		
		public String toString() {
			return loc + "";
		}*/}
	}

	public class Node {
		Key key;
		Value val;
		Locator loc;
		Node rightChild;
		Node leftChild;
		Node parent;
		int weight;
		
		private Node(Key key, Value val) {
			this.key = key;
			this.val = val;
			loc = null;
			rightChild = null;
			leftChild = null;
			parent = null;
		}
		/*private Key getKey() {
			return key;
		}
		private void setKey(Key key) {
			this.key = key;
		}
		private Value getVal() {
			return val;
		}
		private void setVal(Value val) {
			this.val = val;
		}
		private Locator getLoc() {
			return loc;
		}	
		*/
		private void setLoc(Locator loc) {
			this.loc = loc;
		}
		
		public String toString() {
			String retVal = "";
			if (printMethod == 2) {
				retVal = "Value: " + val + "\tKey: " + key
						+ "\tweight: " + weight;
			}
			if (printMethod == 1) {
				retVal = "Value: " + val + "\tKey: " + key
						+ "\tweight: " + weight
						+ "\n" + val + " Left Child: \n" + 
						(leftChild!=null?leftChild.toString():"null") +
						"\n" + val + " Right Child: \n" + 
						(rightChild!=null?rightChild.toString():"null");
			}
			if (printMethod == 0) {
				retVal = "Value: " + val + "\tKey: " + key 
						+ "\tparent: " + (parent!=null?parent.val:"null")
						+ "\n" + val + " Left Child: \n" + 
						(leftChild!=null?leftChild.toString():"null") +
						"\n" + val + " Right Child: \n" + 
						(rightChild!=null?rightChild.toString():"null");
			}
			return retVal;
		}
	}
	
	public WtLeftHeap() { 
		root = null;
		size = 0;
	}
	public int size() { 
		return size;
	}
	public void clear() { 
		root = null;
		size = 0;
	}
	public Locator insert(Key x, Value v) {
		Locator loc;
		if (root == null) {
			Node tempNode = new Node(x,v);
			loc = new Locator(tempNode);
			tempNode.setLoc(loc);
			root = tempNode;
			tempNode.weight = 1;
			size += 1;
		} else {
			WtLeftHeap<Key, Value> h2 = new WtLeftHeap<Key,Value>();
			loc = h2.insert(x,v);
			mergeWith(h2);
		}
		return loc;
	}
	public Node merge(Node u, Node v) {
		if (u==null) return v;
		if (v==null) return u;
		if (u.key.compareTo(v.key)<0) {
			Node temp = u;
			u = v;
			v = temp;
		}
		if (u.leftChild==null) {
			u.leftChild = v;
			u.weight = u.weight + v.weight;
			v.parent = u;
		} else {
			Node temp = merge(u.rightChild,v);
			temp.parent = u;
			u.rightChild = temp;
			if (u.leftChild.weight<u.rightChild.weight) {
				Node tempChild = u.leftChild;
				u.leftChild = u.rightChild;
				u.rightChild = tempChild;
			}
			u.weight = u.leftChild.weight + u.rightChild.weight + 1;
		}
		return u;
	}
	public void mergeWith(WtLeftHeap<Key, Value> h2) {
		if (h2 != null) {
			if (this != h2) {
				root = merge(this.root, h2.root);
				size+=h2.size;
				h2.clear();
			}
		}
	}
	public Value extract() throws Exception { 
		if (root == null) {
			throw new Exception("Extract from empty heap");
		}
		size -= 1;
		Node temp = root;
		root = merge(root.leftChild,root.rightChild);
		if (root!=null)
			root.parent = null;
		return temp.val;
	}
	public void updateKey(Locator loc, Key x) throws Exception { 
		Node node = loc.getNode(); 
		node.key = x;
		while (node.parent!=null && node.key.compareTo(node.parent.key)>0) {
			Key tempKey = node.key;
			Value tempVal = node.val;
			Locator tempLoc = node.loc;
			node.key = node.parent.key;
			node.val = node.parent.val;
			node.loc = node.parent.loc;
			node.loc.node = node;
			node.parent.key = tempKey;
			node.parent.val = tempVal;
			node.parent.loc = tempLoc;
			node.parent.loc.node = node.parent;
			node = node.parent;
		}
		while ((node.leftChild!=null && node.key.compareTo(node.leftChild.key)<0)
			|| (node.rightChild!=null && node.key.compareTo(node.rightChild.key)<0)) {
			if (node.leftChild == null) {
				Key tempKey = node.key;
				Value tempVal = node.val;
				Locator tempLoc = node.loc;
				node.key = node.rightChild.key;
				node.val = node.rightChild.val;
				node.loc = node.rightChild.loc;
				node.loc.node = node;
				node.rightChild.key = tempKey;
				node.rightChild.val = tempVal;
				node.rightChild.loc = tempLoc;
				node.rightChild.loc.node = node.rightChild;
				node = node.rightChild;
			} else if (node.rightChild == null) {
				Key tempKey = node.key;
				Value tempVal = node.val;
				Locator tempLoc = node.loc;
				node.key = node.leftChild.key;
				node.val = node.leftChild.val;
				node.loc = node.leftChild.loc;
				node.loc.node = node;
				node.leftChild.key = tempKey;
				node.leftChild.val = tempVal;
				node.leftChild.loc = tempLoc;
				node.leftChild.loc.node = node.leftChild;
				node = node.leftChild;
			} else if (node.leftChild.key.compareTo(node.rightChild.key)>0) {
				Key tempKey = node.key;
				Value tempVal = node.val;
				Locator tempLoc = node.loc;
				node.key = node.leftChild.key;
				node.val = node.leftChild.val;
				node.loc = node.leftChild.loc;
				node.loc.node = node;
				node.leftChild.key = tempKey;
				node.leftChild.val = tempVal;
				node.leftChild.loc = tempLoc;
				node.leftChild.loc.node = node.leftChild;
				node = node.leftChild;
			} else {
				Key tempKey = node.key;
				Value tempVal = node.val;
				Locator tempLoc = node.loc;
				node.key = node.rightChild.key;
				node.val = node.rightChild.val;
				node.loc = node.rightChild.loc;
				node.loc.node = node;
				node.rightChild.key = tempKey;
				node.rightChild.val = tempVal;
				node.rightChild.loc = tempLoc;
				node.rightChild.loc.node = node.rightChild;
				node = node.rightChild;
			}
		}
		
	}
	public Key peekKey() { return root==null?null:root.key; }
	public Value peekValue() { return root==null?null:root.val; }
	public ArrayList<String> list() { 
		return listHelper(root);
	}
	public ArrayList<String> listHelper(Node node) {
		ArrayList<String> list = new ArrayList<String>();
		if (node == null) {
			list.add("[]");
		} else {
			list.add("(" + node.key + ", " + node.val + ") [" 
		+ node.weight + "]");
			list.addAll(listHelper(node.rightChild));
			list.addAll(listHelper(node.leftChild));
		}
		return list;
	}
	
	public String toString() {
		if (root == null) return "null";
		return root.toString();
	}

}

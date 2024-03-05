package cmsc420_s23; // Do not alter this line or your program will fail the autograder

import java.util.ArrayList;

public class AdjustableStack<Element> {

	private ArrayList<Pair> stack;
	//private ArrayList<Integer> locTracker;
	
	public class Locator { 
		
		private Element elem;
		private int loc;
		
		private Locator (Element elem, int loc) {
			this.elem = elem;
			this.loc = loc;
		}
		
		private int getLoc() {
			return loc;
		}
		
		private void setLoc(int newLoc) {
			loc = newLoc;
		}
		
		public String toString() {
			return loc + "";
		}
	}

	public class Pair {
		private Locator loc;
		private Element elem;
		private Pair (Element elem, Locator loc) {
			this.loc = loc;
			this.elem = elem;
		}
		private Locator getLoc() {
			return loc;
		}
		private Element getElem() {
			return elem;
		}
		public String toString() {
			return  "(" + elem.toString() + ", " + loc.toString() + ")";
		}
	}
	
	public AdjustableStack() { 
		stack = new ArrayList<Pair>();
		//locTracker = new ArrayList<Integer>();
	}
	
	public Locator push(Element element) { 
		Locator retVal = new Locator(element, stack.size());
		stack.add(new Pair(element,retVal));
		//stack.add(0, );
		//retVal.getLoc();
		return retVal; 
	}
	
	public Element pop() throws Exception { 
		if (stack.isEmpty())
			throw new Exception("Pop of empty stack");
		else {
			return stack.remove(stack.size()-1).getElem();
		}
	}
	public Element peek() throws Exception { 
		if (stack.isEmpty())
			throw new Exception("Peek of empty stack");
		else {
			return stack.get(stack.size()-1).getElem();
		}
	}
	public int size() { return stack.size(); }
	public ArrayList<Element> list() { 
		ArrayList<Element> list = new ArrayList<Element>();
		for (Pair p: stack)
			list.add(0,p.getElem());
		return list;
	}
	public void promote(Locator loc) { 
		int index = loc.getLoc();
		if (index == stack.size()-1) {
			//do nothing, top of stack
		} else {
			Pair temp = stack.get(index);
			stack.set(index, stack.get(index+1));
			stack.set(index+1, temp);
			stack.get(index+1).getLoc().setLoc(index+1);
			stack.get(index).getLoc().setLoc(index);
		}
	}
	public void demote(Locator loc) {
		int index = loc.getLoc();
		if (index == 0) {
			//do nothing, bottom of stack
		} else {
			Pair temp = stack.get(index);
			stack.set(index, stack.get(index-1));
			stack.set(index-1, temp);
			stack.get(index-1).getLoc().setLoc(index-1);
			stack.get(index).getLoc().setLoc(index);
		}
	}
	public int getDepth(Locator loc) {
		return stack.size() - 1 - loc.getLoc(); 
	}
	public String toString() {
		return stack.toString();
	}
}

package linkyList;

import java.lang.IllegalArgumentException;
import java.lang.IndexOutOfBoundsException;
import java.util.Iterator;
import java.util.NoSuchElementException;


/*
 * The function of this class is to store a list of values with pointers to 
 * each consecutive value
 * Therefore the only way to access a specific index value is to go through
 * all Nodes prior to it until you reach the spot you are looking for
 */
public class LinkyList<T extends Comparable<T>> implements Iterable<T> {

	private Node head;


	/*
	 * The object that stores the data in the list and the next Node that is
	 * needed to be traversed through to get further into the list
	 */
	private class Node {

		private T data;
		private Node next;

		//basic constructor
		public Node() {
			this(null);
		}

		//constructor with data value given
		public Node(T data) {
			this.data = data;
			next = null;
		}

	}

	private class LinkyListIterator implements Iterator<T> {

		Node current;
		Node prev;
		boolean started = false;
		boolean firstCall = true;
		boolean calledThisRun = true;

		public LinkyListIterator() {
			current = null;
			prev = null;
		}

		public boolean hasNext() {
			if (!started) {
				return head != null;
			}
			return current.next != null;
		}

		public T next() throws NoSuchElementException {
			//System.out.println("inside next");
			if (head == null)
				throw new NoSuchElementException();
			if (!calledThisRun)
				prev = current;
			calledThisRun = false;
			if (firstCall) {
				current = head;

				firstCall = false;
				started = true;
			}  else if (!hasNext()) {
				throw new NoSuchElementException();
			} else {
				current = current.next;
			}
			return current.data;
		}

		public void remove() throws IllegalStateException {
			
			if (current == null || calledThisRun)
				throw new IllegalStateException();
			else if (prev == null) {
				//System.out.println("inside remove head");
				head = head.next;
				current = null;
				firstCall = true;
				started = false;
			} else {
				prev.next = current.next;
			}
			calledThisRun = true;
		}
	}

	public Iterator iterator() {
		return new LinkyListIterator();
	}

	//basic constructor
	public LinkyList() {
		head = null;
	}

	/*
	 * copy constructor
	 * copies each individual data value into new Nodes so one can be edited
	 * without the other changing
	 */
	public LinkyList(LinkyList<T> anotherLinkyList) {

		if (anotherLinkyList.firstNode() != null) {
			Node current1 = anotherLinkyList.firstNode();

			head = new Node(current1.data);
			Node current2 = head;


			while (current1.next != null) {
				current2.next = new Node(current1.next.data);
				current1 = current1.next;
				current2 = current2.next; 
			}
		}
	}


	/*
	 * used solely to access the first element of a LinkyList that isn't "this"
	 */
	public Node firstNode() {
		return head;
	}


	/*
	 * Inserts an element into the list in order
	 * the order of the list is from smallest to largest according to the
	 * compareTo method of the data of the elements of the list
	 */
	public void orderedInsert(T newElement) {

		//makes the node that will need to be added
		Node addTo = new Node(newElement);

		//if list is empty, put the node at the front
		if (head == null) {
			head = addTo;
		} else {

			//manually checks the first element so we can have temporary nodes
			//current and prev
			if (head.data.compareTo(newElement) > 0) {
				addTo.next = head;
				head = addTo;
			} else {
				Node prev = head;
				Node current = head.next;
				while(current != null) {

					//if current finds an element greater than what is added
					//node is added before current and method ends
					if (current.data.compareTo(newElement) > 0) {
						addTo.next = current;
						prev.next = addTo;
						return;
					}
					prev = current;
					current = current.next;
				}

				//if current never finds an element greater, it means the value
				//being added is the greatest and is added to the end
				prev.next = addTo;
			}
		}
	}


	/*
	 * returns the length of the list
	 */
	public int listNumElements() {

		//if head doesn't exist return 0 length, otherwise it starts a counter 
		//at 1 (because head exists)
		if (head == null)
			return 0;
		Node current = head;
		int counter = 1;

		//keeps going through the elements as long as they have a value 
		//incrementing counter each time
		while (current.next != null) {
			current = current.next;
			counter++;
		}

		return counter;
	}


	/*
	 * returns the data value of an element at a specific index
	 */
	public T getElementAtIndex(int index) throws IndexOutOfBoundsException {

		//if index is greater than or equal to length throw an error
		//because the value can't exist
		if (listNumElements() <= index)
			throw new IndexOutOfBoundsException();

		//goes through the elements index times to get the appropriate value
		Node current = head;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current.data;
	}


	/*
	 * returns the pointer of the last occurrence of a node with a matching
	 * value in the list if it exists
	 * otherwise returns null
	 */
	public T lookForElement(T element) {
		Node current = head;
		T retVal = null;

		/*
		 * parses through the list and if the compareTo() method (which we know
		 * the data must have) returns 0 between the two data values sets 
		 * retVal to that value
		 * This value will be reset if the value exists multiple times in the 
		 * list
		 * Therefore it returns the pointer to the last occurrence with the 
		 * value
		 */
		while (current != null) {
			if (current.data.compareTo(element) == 0) 
				retVal = current.data;
			current = current.next;
		}
		return retVal;
	}


	/*
	 * returns a string representation of the value within the list
	 */
	public String toString() {
		String retVal = "";
		Node current = head;

		//parses through the list adding the toString() of each value to the 
		//overall toString()
		while (current != null) {
			retVal += current.data.toString() + " ";
			current = current.next;
		}

		/*
		 * if any data was added to the toString value, 1 extra space would 
		 * be added to the end which would need to be deleted
		 */
		if (retVal.length() != 0)
			retVal = retVal.substring(0, retVal.length()-1);

		return retVal;
	}


	/*
	 * returns the index of the first occurrence of element
	 */
	public int getIndexOfElement(T element)  {
		Node current = head;
		int index = 0;

		//parses through the list and if element and the node's value are equal
		//returns the index counter for the list
		while (current != null) {
			if (current.data.compareTo(element) == 0)
				return index;
			index++;
			current = current.next;
		}

		//if element is not found returns -1
		return -1;
	}


	/*
	 * Tries to remove a specific Node from the list with a matching data value
	 * to element
	 */
	public boolean elementRemove(T element) {

		//returns false if head is null
		//can't remove a node that doesn't exist
		if (head == null) {
			return false;
		} else {

			//manually checks the first node so we can make temporary Nodes:
			//prev and current
			if (head.data.compareTo(element) == 0) {
				head = head.next;
				return true;
			}
		}
		Node prev = head;
		Node current = head.next;

		//parses through the list and if it finds a matching value removes it
		//by setting prev's next value to current's next value (which removes 
		//all pointers to current)
		while (current != null) {
			if (current.data.compareTo(element) == 0) {
				prev.next = current.next;
				return true;
			}
			prev = current;
			current = current.next;
		}

		//returns false if element wasn't found and nothing was removed
		return false;
	}


	/*
	 * Tries to remove a Node with a specific index value
	 */
	public void removeElementWithIndex(int index)
			throws IndexOutOfBoundsException {

		//throws an exception if index is greater than or equal to the length
		//of the list
		if (listNumElements() <= index)
			throw new IndexOutOfBoundsException();

		//manually checks if index is 0 and removes the first element if it is
		if (index == 0)
			head = head.next;

		//parses through the list until it reaches the appropriate index 
		//(index-1 so we can remove the next element) and removes it
		Node current = head;
		for (int i = 0; i < index-1; i++)
			current = current.next;
		current.next = current.next.next;
	}


	/*
	 * deletes all Nodes in the list by setting head = null
	 */
	public void reset() {
		head = null;
	}


	/*
	 * returns an inclusive sublist of the index values between startIndex and
	 * endIndex in the current list
	 */
	public LinkyList<T> subList(int startIndex, int endIndex)
			throws IndexOutOfBoundsException {

		//throws an exception if endIndex or startIndex is greater than or 
		//equal to the length of the list because this value can't exist
		//or if startIndex or endIndex is negative because this is also
		//impossible
		if (listNumElements() <= endIndex || listNumElements() <= startIndex
				|| startIndex < 0 || endIndex < 0)
			throw new IndexOutOfBoundsException();

		//if both values are valid but startIndex is greater than endIndex
		//returns an empty list
		LinkyList<T> retVal = new LinkyList<T>();
		if (startIndex > endIndex)
			return retVal;

		//creates a new list by parsing the current list and adding elements
		//only when the current position (given by counter) is less than
		//or equal to endIndex and greater than or equal to startIndex
		int counter = 0;
		Node current = head;
		while (counter <= endIndex) {
			if(startIndex <= counter) {
				retVal.orderedInsert(current.data);
			}
			current = current.next;
			counter++;
		}
		return retVal;
	}


	/*
	 * compares the elements of two lists
	 */
	public int compareTo(LinkyList<T> anotherLinkyList) {

		//can only check through the length of the smallest length list
		//therefore finds and set maxCheckLength to that value
		int size1 = listNumElements();
		int size2 = anotherLinkyList.listNumElements();
		int maxCheckLength = size1 < size2 ? size1 : size2;


		int i = 0;
		while (i < maxCheckLength) {
			//if two corresponding values are different in the list return the 
			//compareTo() value of the two values
			if (getElementAtIndex(i).compareTo(anotherLinkyList
					.getElementAtIndex(i)) != 0)
				return getElementAtIndex(i).compareTo(anotherLinkyList
						.getElementAtIndex(i));
			i++;
		}

		//if all the checked values were the same return the difference in 
		//length for the compareTo result
		return size1 - size2;
	}

}

package bsearchPolyTree;

@SuppressWarnings("unchecked")

/*
 * handles most functions of the project from determining if a node needs to 
 * be added vs altered, deleting a part of the tree, and a few other important
 * tasks that would be helpful for a binary search tree
 */

public class NonemptyBsearchPolyTree<K extends Comparable<K>, V>
implements BsearchPolyTree<K, V> {

	K key;
	V value;
	BsearchPolyTree<K,V> leftChild;
	BsearchPolyTree<K,V> rightChild;


	/*
	 * initializes everything for a NonemptyTree
	 */
	public NonemptyBsearchPolyTree(K newKey, V newValue) {
		key = newKey;
		value = newValue;
		leftChild = EmptyBsearchPolyTree.getInstance();
		rightChild = EmptyBsearchPolyTree.getInstance();
	}


	/*
	 * either creates a new NonemptyTree or changes the value of an existing 
	 * one
	 */
	public NonemptyBsearchPolyTree<K, V> storeKeyAndValue(K newKey, V newValue) {
		if (newKey == null || newValue == null)
			throw new IllegalArgumentException();

		if (newKey.compareTo(this.key) == 0) {
			this.value = newValue;
		} else if (newKey.compareTo(this.key) < 0) {
			leftChild = leftChild.storeKeyAndValue(newKey, newValue);
		} else {
			rightChild = rightChild.storeKeyAndValue(newKey, newValue);
		}
		return this;
	}


	/*
	 * returns the key of a tree
	 */
	public K getKey() {
		return key;
	}


	/*
	 * returns the value of a tree
	 */
	public V getValue() {
		return value;
	}


	/*
	 * returns the total number of elements in the tree
	 */
	public int numElements() {
		return 1 + leftChild.numElements() + rightChild.numElements();
	}


	/*
	 * goes through the tree and returns the value of a tree with a specific
	 * key
	 */
	public V getValueForKey(K keyToLookUp) {
		if (keyToLookUp == null)
			throw new IllegalArgumentException();

		if (keyToLookUp.compareTo(key) == 0) {
			return value;
		} else if (keyToLookUp.compareTo(key) < 0) {
			return leftChild.getValueForKey(keyToLookUp);
		} else {
			return rightChild.getValueForKey(keyToLookUp);
		}
	}


	/*
	 * returns the max key within a tree
	 */
	public K max() throws EmptyBsearchPolyTreeException {
		try {
			return rightChild.max();
		} catch (EmptyBsearchPolyTreeException ex) {
			return key;
		}
	}


	/*
	 * returns the min key within a tree
	 */
	public K min() throws EmptyBsearchPolyTreeException {
		try {
			return leftChild.min();
		} catch (EmptyBsearchPolyTreeException ex) {
			return key;
		}
	}


	/*
	 * returns true if this tree and otherTree have the same elements
	 */
	public boolean hasSameKeys(BsearchPolyTree<K, V> otherTree) {
		if (otherTree == null)
			throw new IllegalArgumentException();

		if (!otherTree.hasKey(key))
			return false;
		return leftChild.hasSameKeys(otherTree) && 
				rightChild.hasSameKeys(otherTree);
	}


	/*
	 * returns true if the variable keyCheck is in initial tree used
	 */
	public boolean hasKey(K keyCheck) {
		if (keyCheck == null)
			throw new IllegalArgumentException();

		if (key.compareTo(keyCheck) == 0)
			return true;
		return leftChild.hasKey(keyCheck) || rightChild.hasKey(keyCheck);
	}


	/*
	 * returns the number of elements at a given level on the tree
	 */
	public int numElementsAtLevel(int level) {
		if (level < 0)
			return 0;

		if (level == 0)
			return 1;
		else
			return leftChild.numElementsAtLevel(level-1) + 
					rightChild.numElementsAtLevel(level-1);
	}


	/*
	 * helper method for delete method
	 * it makes the left child of the max value in the removed tree's subtree
	 * the right child of the parent of the removed node keeping binary search
	 * tree rules intact
	 */
	public BsearchPolyTree<K, V> replaceRightChild(K kReplace) {
		if (kReplace == null)
			return leftChild;
		if (rightChild.getKey() == null)
			return leftChild;
		if (rightChild.getKey().compareTo(kReplace) == 0)
			rightChild = rightChild.replaceRightChild(null);
		else
			rightChild = rightChild.replaceRightChild(kReplace);
		return this;
	}


	/*
	 * attempts to delete a specific value from a tree
	 */
	public BsearchPolyTree<K, V> deleteKeyAndValue(K keyToRemove) {
		if (keyToRemove == null)
			throw new IllegalArgumentException();

		/*
		 * when we have found the node we check to see if it has a left subtree
		 * if it doesn't we just set this tree to the right child and its
		 * possible subtree (could also just set it an emptyTree but that's 
		 * okay)
		 * if the left does have a subtree we do the same idea with checking 
		 * the right subtree. If it's not there we set this to the left 
		 * subtree
		 */
		if (key.compareTo(keyToRemove) == 0) {
			try {
				K temp = null;
				temp = leftChild.max();
				if (temp != null) {
					try {
						rightChild.min();

						/*
						 * both subtrees exist so we set this to the max of the
						 * left subtree and call our helper method
						 */
						value = getValueForKey(temp);
						key = temp;
						leftChild = leftChild.replaceRightChild(temp);
					} catch (EmptyBsearchPolyTreeException ex) {
						return leftChild;
					}
				}
			} catch (EmptyBsearchPolyTreeException ex) {
				return rightChild;
			}
		} else if (keyToRemove.compareTo(key) < 0) {
			leftChild = leftChild.deleteKeyAndValue(keyToRemove);
		} else {
			rightChild = rightChild.deleteKeyAndValue(keyToRemove);
		}
		return this;
	}


	/*
	 * prints our tree neatly using a in order search
	 */
	public String toString() {
		return leftChild.toString() + key.toString() + "->" + 
				value.toString() + " " + rightChild.toString();
	}

}

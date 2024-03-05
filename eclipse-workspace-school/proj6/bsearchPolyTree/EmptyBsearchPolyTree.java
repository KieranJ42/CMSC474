package bsearchPolyTree;

@SuppressWarnings({"unchecked", "rawtypes"})

/*
 * Singleton object used to represent points off the leaf nodes of BST's
 * Primarily helper methods for the methods in the nonempty tree class
 */

public class EmptyBsearchPolyTree<K extends Comparable<K>, V>
implements BsearchPolyTree<K, V> {

	static EmptyBsearchPolyTree<Integer,Integer> singleton;


	/*
	 * makes sure there is only ever one empty tree in existence
	 */
	public static EmptyBsearchPolyTree getInstance() {
		if (singleton == null)
			singleton = new EmptyBsearchPolyTree<>();
		return singleton;
	}


	/*
	 * private constructor so people can't manually create empty trees
	 */
	private EmptyBsearchPolyTree() {

	}


	/*
	 * if it gets to this method we know a new nonempty tree needs to be
	 * created because it didn't exist elsewhere in the tree
	 */
	public NonemptyBsearchPolyTree<K, V> storeKeyAndValue(K newKey, V newValue) {
		if (newKey == null || newValue == null)
			throw new IllegalArgumentException();

		return new NonemptyBsearchPolyTree(newKey, newValue);
	}


	/*
	 * returns zero as this doesn't count as one of the nodes within the tree
	 */
	public int numElements() {
		return 0;
	}


	/*
	 * returning null means the key wasn't in the tree
	 */
	public V getValueForKey(K keyToLookUp) {
		if (keyToLookUp == null)
			throw new IllegalArgumentException();

		return null;
	}


	/*
	 * throws an exception because this can only ever be called if the starting
	 * tree was empty
	 */
	public K max() throws EmptyBsearchPolyTreeException {
		throw new EmptyBsearchPolyTreeException();
	}


	/*
	 * throws an exception because this can only ever be called if the starting
	 * tree was empty
	 */
	public K min() throws EmptyBsearchPolyTreeException {
		throw new EmptyBsearchPolyTreeException();
	}


	/*
	 * If we get to this call it means every other key in the tree has been
	 * checked and we can return true;
	 */
	public boolean hasSameKeys(BsearchPolyTree<K, V> otherTree) {
		return true;
	}


	/*
	 * returns zero as this doesn't count as one of the nodes within the tree
	 */
	public int numElementsAtLevel(int level) {
		return 0;
	}


	/*
	 * if we get to this we know the tree didn't have our node so we return
	 * this to be able to return the whole tree at the end
	 */
	public BsearchPolyTree<K, V> deleteKeyAndValue(K keyToRemove) {
		if (keyToRemove == null)
			throw new IllegalArgumentException();
		return this;
	}


	/*
	 * assists in making our neat representation of a tree
	 */
	public String toString() {
		return "";
	}


	/*
	 * if we get here we know the key is not in the tree 
	 */
	public boolean hasKey(K keyCheck) {
		if (keyCheck == null)
			throw new IllegalArgumentException();

		return false;
	}


	/*
	 * returns null because empty trees don't have a key
	 */
	public K getKey() {
		return null;
	}


	/*
	 * returns null because empty trees don't have a value
	 */
	public V getValue() {
		return null;
	}


	/*
	 * returns this to be able to reconstruct the tree properly
	 */
	public BsearchPolyTree<K, V> replaceRightChild(K kReplace) {
		return this;
	}

}

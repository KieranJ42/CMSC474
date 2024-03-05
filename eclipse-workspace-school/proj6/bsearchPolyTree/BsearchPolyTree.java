package bsearchPolyTree;

// (c) Larry Herman, 2021.  You are allowed to use this code yourself, but
// not to provide it to anyone else.

public interface BsearchPolyTree<K extends Comparable<K>, V> {

	public NonemptyBsearchPolyTree<K, V> storeKeyAndValue(K newKey, V newValue);
	public int numElements();
	public V getValueForKey(K keyToLookUp);
	public K max() throws EmptyBsearchPolyTreeException;
	public K min() throws EmptyBsearchPolyTreeException;
	public int numElementsAtLevel(int level);
	public boolean hasSameKeys(BsearchPolyTree<K, V> otherTree);
	public BsearchPolyTree<K, V> deleteKeyAndValue(K keyToRemove);
	public boolean hasKey(K keyCheck);

	//added helper methods
	public V getValue();
	public K getKey();
	public BsearchPolyTree<K, V> replaceRightChild(K kReplace);

}

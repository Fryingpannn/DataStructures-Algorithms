//Matthew Pan 40135588, November 2020

package A4;

import java.util.ArrayList;
import java.util.Random;

public class IntelligentSIDC {
	
	private int size;
	//threshold to change data structure depending on size
	private final int THRESHOLD = 50000;
	//the two data structures
	private AVLTree tree;
	private ArrayList<Student> list;
	
	//constructors
	IntelligentSIDC(){ //defaults to list
		setSIDCThreshold(100);
		size = 100;
	}
	IntelligentSIDC(int size){
		setSIDCThreshold(size);
	}
	
	/**
	 * This method decides which data structure will be used for the
	 * given input size. If size is over 50 000, AVL Tree will be used.
	 * 
	 * @param size size of data structure
	 */
	private void setSIDCThreshold(int size) {
		if(size >= 100 && size <= 500000) {
			this.size = size;
			if(size > THRESHOLD)
				tree = new AVLTree();
			else
				list = new ArrayList<Student>(size);
		}
		else {
			System.out.println("Please try again with a size between 100 and 500 000.");
			System.exit(0);
		}
	}
	
	/**
	 * fill up the data structure with random keys
	 */
	public void generate() {
		Random rand = new Random();
		if(size > THRESHOLD) { //AVL tree
			for(int i = 0; i < size; i++) {
				tree.add(new Student(rand.nextInt(89999999) + 10000000, i+1));
			}
		}
		else {	//Sequence list
			for(int i = 0; i < size; i++)
				list.add(new Student(rand.nextInt(89999999) + 10000000, i+1));
			mergesort(list); //sort the list after insertion
		}
	}
	
	//method to get of SIDC
	public int getSize() {
		if(size > THRESHOLD)
			return tree.getSize();
		else
			return list.size();
	}
	
	/**helper method
	 * @return true if key valid, false otherwise
	 */
	private boolean validateKey(int key) {
		if(key < 10000000 || key > 99999999) {
			System.out.println("Invalid ID: key '" + key + "'. "
					+ "Please enter a key of 8 digits.");
			return false;
		}
		else
			return true;
	}
	
	//merge sort methods to sort list when multiple insertions are done at once
	private void mergesort(ArrayList<Student> arr) {
		//base case when 1 element left
		int n = arr.size();
		if(n < 2) return;
		
		//separate array in half
		int mid = n/2;
		
		ArrayList<Student> left = new ArrayList<Student>(mid+1);
		ArrayList<Student> right = new ArrayList<Student>(n-mid+1);
		for(int i = 0; i < mid; i++)
			left.add(arr.get(i));
		for(int i = mid; i < n; i++)
			right.add(arr.get(i));
		//conquer
		mergesort(left);
		mergesort(right);
		//merge
		merge(left, right, arr);
	}
	private void merge(ArrayList<Student> left, ArrayList<Student> right, ArrayList<Student> arr) {
		//i represents how many we added from left, and j represents how many we added from right to arr
		int i = 0, j = 0;
		//keep merging until reaches length of both array combined
		while(i + j < arr.size()) { //compare keys in both array and add the minimum. if one is done, add all of the other.
			if(j == right.size() || (i < left.size() && left.get(i).getKey() < right.get(j).getKey()))
				arr.set(i+j, left.get(i++));
			else
				arr.set(i+j, right.get(j++));
		}
	}
	
	/**Adds the key to its sorted location in the data structure
	 * @param key key to add
	 * @param value value to add
	 */
	public void add(int key, int value) {
		if(validateKey(key)) {
			if(size > THRESHOLD) {
				tree.add(new Student(key, value));
				if(tree.added()) size++; else tree.updateAdded();
			}
			else {
				addList(new Student(key, value));
				size = list.size();
				thresholdCheck();
			}
		}
	}
	
	/**
	 * Converts list into AVL Tree if threshold size exceeded.
	 */
	private void thresholdCheck() {
		if(size > THRESHOLD) {
			tree = new AVLTree();
			int tempSize = size;
			for(Student i : list)
				tree.add(i);
			size = ++tempSize;
			list.clear();
			list = null;
		}
	}
	
	/** Binary search for insertion; O(n) due to shift
	 * @param key student to insert
	 * @param l leftmost index
	 * @param r rightmost index (size-1)
	 * @return true if insertion success, false otherwise
	 */
	private boolean addList(Student key, int l, int r) {
		if(r >= l) {
			int mid = l + (r-l)/2;
			
			int current = list.get(mid).getKey();
			int prev = mid == 0? 0 : list.get(mid+1).getKey();
			int next = mid == list.size()-1? 0 : list.get(mid+1).getKey();
			
			if(current == key.getKey()) //duplicate found, no insertion
				return false;
			else if(current < key.getKey() && next > key.getKey()) {
				list.add(mid+1, key); //inserts in the middle
				return true;
			}
			else if(current > key.getKey() && prev < key.getKey()) {
				list.add(mid, key);
				return true;
			}
			else if(current > key.getKey())
				return addList(key, l, mid-1);
			else if(current < key.getKey())
				return addList(key, mid+1, r);	
		}
		return false;
	}
	//helper method for insertion
	private boolean addList(Student key) {
		//directly add if list is empty or key is larger than largest key
		if(list.isEmpty() || key.getKey() > list.get(list.size()-1).getKey()) {
			list.add(key);
			return true;
		}// add to front if smaller than smallest key
		else if(key.getKey() < list.get(0).getKey()) {
			list.add(0, key);
			return true;
		}
		else //add in middle
			return addList(key, 0, list.size()-1);
	}
	
	/**
	 * @param key key to remove
	 */
	public void remove(int key) {
		if(validateKey(key)) {
			if(size > THRESHOLD)
				tree.remove(key);
			else {
				removeList(key, 0, list.size()-1);
				size = list.size();
			}
		}
	}
	
	/**
	 * Uses binary search to find the key in list then removes it.
	 * O(n) since the array needs to resize.
	 * @param key key to remove
	 * @param l leftmost index
	 * @param r right most index (size-1)
	 * @return true if removal success
	 */
	private boolean removeList(int key, int l, int r) {
		//base case
		if(l > r)
			return false;
		
		//midpoint
		int mid = l + (r-l)/2;
		
		int currentKey = list.get(mid).getKey();
		if(currentKey == key) {
			list.remove(mid);
			return true;
		}
		else if(currentKey > key)
			return removeList(key, l, mid - 1);
		else 
			return removeList(key, mid + 1, r);
	}
	
	/**
	 * @return sorted sequence of keys
	 */
	public ArrayList<Integer> allKeys() {
		if(size > THRESHOLD)
			return tree.sortedKeys();
		else
			return keysList();
	}
	
	/**
	 * @return sorted sequence of all keys from list
	 */
	private ArrayList<Integer> keysList(){
		ArrayList<Integer> keys = new ArrayList<Integer>(list.size());
		for(Student i : list) {
			keys.add(i.getKey());
		}
		return keys;
	}
	
	/**
	 * @param key to get value of
	 * @return value of key, -1 if invalid
	 */
	public int getValue(int key) {
		if(validateKey(key)) {
			if(size >THRESHOLD)
				return tree.getValue(key);
			else
				return getValueList(key);
		}
		else
			return -1; //invalid key
	}
	
	/**Iterative binary search to find value of given key O(logn)
	 * @param key key to get value of
	 * @return value of key
	 */
	private int getValueList(int key) {
		int mid = binarySearch(key);
		return list.get(mid).getValue();
	}
	
	/**
	 * @param key 
	 * @return successor of key
	 */
	public int nextKey(int key) {
		if(validateKey(key)) {
			if(size > THRESHOLD)
				return tree.nextKey(key);
			else
				return nextKeyList(key);
		}
		else
			return -1; //invalid key
	}
	
	/**
	 * @param key to get successor of
	 * @return successor of key
	 */
	private int nextKeyList(int key) {
		int mid = binarySearch(key);
		return list.get(mid+1).getKey();
	}
	
	/**Helper method binary search
	 * @param key
	 * @return index of the key
	 */
	private int binarySearch(int key) {
		int l = 0; int r = list.size()-1;
		while(r >= l) {
			int mid = l + (r-l)/2;
			
			int current = list.get(mid).getKey();
			if(current == key) {
				return mid;
			}
			else if(current < key)
				l = mid + 1;
			else
				r = mid - 1;
		}
		return -1;
	}
	
	/**
	 * @param key
	 * @return predecessor of key
	 */
	public int prevKey(int key) {
		if(validateKey(key)) {
			if(size > THRESHOLD)
				return tree.prevKey(key);
			else
				return prevKeyList(key); //temp
		}
		else
			return -1; //invalid key
	}
	
	/**
	 * @param key
	 * @return predecessor of key
	 */
	private int prevKeyList(int key) {
		int mid = binarySearch(key);
		return list.get(mid-1).getKey();
	}
	
	/**
	 * @param key1 start
	 * @param key2 end
	 * @return number of keys between key1 and key2 (inclusive)
	 */
	public int rangeKey(int key1, int key2) {
		if(key2 <= key1)
			return 0;
		if(size > THRESHOLD)
			return tree.rangeKey(key1, key2).size();
		else
			return rangeKeyList(key1, key2); //temp
	}
	
	/**
	 * @param key1
	 * @param key2
	 * @return number of keys in between
	 */
	private int rangeKeyList(int key1, int key2) {
		int indexKey1 = binarySearch(key1);
		int indexKey2 = binarySearch(key2);
		
		return indexKey2 - indexKey1 + 1;
	}
	
	public void print() {
		if(size > THRESHOLD)
			tree.display();
		else {
			for(Student i : list)
				System.out.println(i);
		}
	}
	
	//displays which ds is being used
	public void datastructure() {
		System.out.print("\nCurrently using: ");
		if(list == null)
			System.out.println("[AVL Tree]");
		else
			System.out.println("[Sequence]");
	}
}
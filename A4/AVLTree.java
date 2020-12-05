//Matthew Pan 40135588, November 2020

package A4;

import java.util.ArrayList;

public class AVLTree {
	
	/* ----- inner node class begin ------ */
	private class Node {
		//left and right child
		Node leftC;
		Node rightC;
		
		//key/value of the node
		private Student data;
		//height of current node in the tree
		private int height;
		
		//constructors
		Node(){ 
			data = null; 
			height = 0; 
			size++;
			}
		Node(Student key){
			data = key;
			height = 1;
			size++;
		}
		
		//accessors
		int getKey() { return data.getKey(); }
		int getValue() { return data.getValue(); }
	}
	/* ----- inner node class end ------ */
	
	Node root;
	int size = 0;
	//for nextKey()
	boolean stopAfterNext;
	//to know if an insertion was successful
	boolean added = true;
	
	//constructor
	public AVLTree() {
		root = new Node();
	}
	
	/* return height of input node */
	private int height(Node n) {
		if(n == null)
			return 0; //leaf node
		else
			return n.height;
	}
	
	/**
	 * @param n node to get balance factor of
	 * @return balance factor of input node; balanced if -1, 0 or 1.
	 */
	private int balance(Node n) {
		if(n == null)
			return 0;
		else
			return height(n.leftC) - height(n.rightC);
	}
	
	/**
	 * @param n where to start searching from
	 * @return smallest key value node starting from n
	 */
	public Node minKey(Node n) {
		Node temp = n;
		while(temp.leftC != null)
			temp = temp.leftC;
		return temp;
	}
	
	/**
	 * Trinode restructuring for the subtree rooted at c
	 * @param c node at which imbalance occured
	 * @return new root node for the balanced subtree
	 */
	Node rotateRight(Node c) {
		//temp vars
		Node a = c.leftC;
		Node b = a.rightC;
		//double rotation
		a.rightC = c;
		c.leftC = b;
		//update heights of rotated nodes
		c.height = 1 + Integer.max(height(c.leftC), height(c.rightC));
		a.height = 1 + Integer.max(height(a.leftC), height(a.rightC));
		//a becomes the new root after rotation
		return a;
	}
	
	/**
	 * Trinode restructuring for the subtree rooted at c
	 * @param c node at which imbalance occured
	 * @return new root node for the balanced subtree
	 */
	Node rotateLeft(Node c) {
		//temp vars
		Node a = c.rightC;
		Node b = a.leftC;
		//double rotation
		a.leftC = c;
		c.rightC = b;
		//update heights of rotated nodes
		c.height = 1 + Integer.max(height(c.leftC), height(c.rightC));
		a.height = 1 + Integer.max(height(a.leftC), height(a.rightC));
		//a becomes the new root after rotation
		return a;
	}
	
	/**
	 * Helper method to insert new key into tree
	 * @param key new key to insert in tree
	 */
	public void add(Student key) { //THIS NEEDS TO BE IN OTHER CLASS, SHOULD BE KEY + NAME
		if(root.data == null)
			root.data = key;
		else
			root = insert(root, key); //insert always returns the new root
	}
	
	/** O(log n): restructuring is constant, finding the insertion location is log n
	 * The insertion first inserts like a normal BST. Then on the way back in the
	 * recursive stack, it updates the height of each node and fixes any imbalances.
	 * 
	 * @param node recurses on this node to find the spot for insertion, starts with root
	 * @param key new key to insert in tree
	 * @return node used in recursion stack to update tree structure
	 */
	private Node insert(Node node, Student key) {
		
		//BST insertion
		if(node == null)
			return new Node(key);
		
		if(key.getKey() > node.getKey())
			node.rightC = insert(node.rightC, key);
		else if(key.getKey() < node.getKey())
			node.leftC = insert(node.leftC, key);
		else {
			added = false;//insertion unsuccessful
			return node; //if equal, quit
		}
		
		//update height of each node during recursion
		node.height = 1 + Integer.max(height(node.leftC), height(node.rightC));
		
		//get the balance factor of each node during recursion to check for imbalance
		int balance = balance(node);
		
		//trinode restructuring
		//case 1: all left
		if(balance > 1 && key.getKey() < node.leftC.getKey())
			return rotateRight(node);
		//case 3: all right
		if(balance < -1 && key.getKey() > node.rightC.getKey())
			return rotateLeft(node);
		//case 2: left then right
		if(balance > 1 && key.getKey() > node.leftC.getKey()) {
			node.leftC = rotateLeft(node.leftC);
			return rotateRight(node);
		}	
		//case 4: right then left
		if(balance < -1 && key.getKey() < node.rightC.getKey()) {
			node.rightC = rotateRight(node.rightC);
			return rotateLeft(node);
		}	
		return node;
	}
	
	/**O(log n): constant time for restructuring and log n time to find the key to remove
	 * @param node to start searching for the key to remove
	 * @param key the student to remove
	 * @return nodes used during recursive satck
	 */
	Node remove(Node node, int key) {
		//find the node to delete
		if(node == null)
			return node; //if key not in tree
		if(key > node.getKey())
			node.rightC = remove(node.rightC, key);
		else if(key < node.getKey())
			node.leftC = remove(node.leftC, key);
		else {
		//normal BST delete if key is equal to nodes key
			
			//if the node to delete has either 1 or no child nodes
			if(node.leftC == null || node.rightC == null) {
				Node temp;
				if(node.rightC == null)
					temp = node.leftC;
				else
					temp = node.rightC;
				
				//if no child, set the node to null to delete after
				if(temp == null) {
					temp = node;
					node = null;
					size--;
				}
				else {// if one child, replace node with its child
					node = temp;
					size--;
				}
			}
			else { //if the node to delete has 2 child nodes:
				//replace the node to delete with the next one in inorder traversal
				//and delete that node
				Node temp = minKey(node.rightC);
				node.data.setKey(temp.getKey());
				node.data.setValue(temp.getValue());
				node.rightC = remove(node.rightC, temp.getKey());
			}
		}
		//if node had no child
		if(node == null)
			return node;
		
		//update height of every node in recursion stack
		node.height = 1 + Integer.max(height(node.leftC), height(node.rightC));
		
		//balance factor
		int balance = balance(node);
		
		//Trinode restructuring if balance is not 0, 1 or -1 (imbalance)
		//case 1
		if(balance > 1 && balance(node.leftC) >= 0)
			return rotateRight(node);
		//case 2
		if(balance > 1 && balance(node.leftC) < 0) {
			node.leftC = rotateLeft(node.leftC);
			return rotateRight(node);
		}
		//case 3
		if(balance < -1 && balance(node.rightC) <= 0)
			return rotateLeft(node);
		//case 4
		if(balance < -1 && balance(node.rightC) > 0) {
			node.rightC = rotateRight(node.rightC);
			return rotateLeft(node);
		}
		return node;
	}
	//helper method for remove()
	public void remove(int key) {
		root = remove(root, key);
	}
	
	/**O(n) inorder traversal: adds all keys to sequence
	 * @param node root node
	 * @param arr empty sequence
	 * @return sorted sequence containing all keys
	 */
	public ArrayList<Integer> allKeys(Node node, ArrayList<Integer> arr) {
		if(node == null || size == 0)
			return arr;
		
		arr = allKeys(node.leftC, arr);
		arr.add(node.data.getKey());
		arr = allKeys(node.rightC, arr);
		return arr;
	}
	//helper method for allKeys()
	public ArrayList<Integer> sortedKeys(){
		return allKeys(root, new ArrayList<Integer>(size));
	}
	
	/**O(n) inorder traversal: adds appropriate keys to the array
	 * Same as allKeys() but provide a range
	 * @param begin start index of range
	 * @param end end index of range
	 * @return array of specified range of the keys (inclusive)
	 */
	public ArrayList<Student> rangedKeys(Node node, ArrayList<Student> arr, int begin, int end){
		if(node == null || size == 0)
			return arr;
		
		arr = rangedKeys(node.leftC, arr, begin, end);
		if(node.getKey() >= begin && node.getKey() <= end)
			arr.add(node.data);
		arr = rangedKeys(node.rightC, arr, begin, end);
		return arr;
	}
	//helper method for rangedKeys()
	public ArrayList<Student> rangeKey(int key1, int key2){
		return rangedKeys(root, new ArrayList<Student>(key2-key1+1), key1, key2);
	}
	
	/**O(logn) binary search
	 * @param node to start searching from (root)
	 * @param key key we are searching the value of
	 * @return value of the key (student age, -1 if key not found)
	 */
	private int getValue(Node node, int key) {
		if(node == null)
			return -1;
		
		if(key > node.getKey())
			return getValue(node.rightC, key);
		else if(key < node.getKey())
			return getValue(node.leftC, key);	
		else
			return node.getValue();
	}
	//helper method for getValue()
	public int getValue(int key) { return getValue(root, key); }
	
	/**
	 * @param key key we want the predecessor of
	 * @return predecessor of key, or -1 if key not found or if it's the first key
	 */
	public int prevKey(int key) {
		ArrayList<Student> arr = rangeKey(0, key);
		if(arr.size() > 1) 
			return arr.get(arr.size()-2).getKey();
		else
			return -1;
	}
	
	/**O(n): inorder traversal
	 * Stores the successor key in an array of 1 and returns it
	 * @param root starting node for inorder traversal
	 * @param key key to find successor of
	 * @return successor key, -1 if not found
	 */
	private int[] nextKey(Node root, int key, int[] arr) {
		if(root == null)
			return arr;
		
		arr = nextKey(root.leftC, key, arr);
		if(stopAfterNext == true) {
			arr[0] = root.getKey();
			stopAfterNext = false;
		}
		if(root.getKey() == key)
			stopAfterNext = true;
		arr = nextKey(root.rightC, key, arr);
		return arr;
	}
	//helper method for nextKey()
	public int nextKey(int key) { 
		stopAfterNext = false;
		int succ = nextKey(root, key, new int[1])[0];
		if(succ != 0)
			return succ;
		else
			return -1;
	}
	
	//methods to keep track of number of elements in tree
	public boolean added() { return added; }
	public void updateAdded() { added = true; }
	
	/**
	 * helper method to print tree
	 */
	public void display() {
		print(root);
	}
	/**
	 * Prints the tree in inorder fashion for sorted display.
	 * @param root root node
	 */
	private void print(Node root) {
		if(root == null)
			return;
		print(root.leftC);
		System.out.println(root.data);
		print(root.rightC);
	}
	
	//return number of elements in tree
	public int getSize() {
		return size;
	}	
}

package A3;

import java.util.ArrayList;

/**
 * @author Matthew Pan 40135588
 *
 * Priority Queue using an unsorted LinkedList. Used to simulate CPU scheduling for executing processes/jobs
 * in an operating system.
 */
public class HeapPQ {
	private ArrayList<Job> list;
	//counter to track for starved processes; increments every time a process finishes.
	//activates starve check when it's a multiple of 30
	private static int starveCheck = 0;
	//number of priority changes due to starvation
	private static int priorityChanges = 0;
	//total wait time of all jobs
	private static long totalWaitTime = 0;

	//constructors for arraylist based heap
	public HeapPQ() { list = new ArrayList<Job>(); }
	public HeapPQ(ArrayList<Job> jobs) { 
		list = jobs;
		heapify();
	}
	
	/**
	 * bottom-up heap construction O(n)
	 */
	private void heapify() {
		//bottom-up ordering starting at parent index of last index (around halfway)
		int startIndex = parent(list.size() -1);
		for(int i = startIndex; i >= 0; i--) {
			downheap(i);
		}
		//set entry time and current time for jobs in list
		for(Job job : list) {
			Job.setCurrentTime(Job.getCurrentTime() +1);
			job.setEntryTime(Job.getCurrentTime());
		}
	}
	
	//methods to manipulate arraylist in a tree-like structure
	private int parent(int j) { return (j-1)/2; }
	private int left(int j) { return j*2 + 1; }
	private int right(int j) { return j*2 + 2; }
	private boolean hasLeft(int j) { return left(j) < list.size(); }  //since complete binary tree, left
	private boolean hasRight(int j) { return right(j) < list.size(); }//or right will be last index element
	
	//swaps two elements in the arraylist
	private void swap(int i, int j) {
		Job temp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, temp);
	}
	
	/**
	 * Used after insertion: restores min-heap order by moving the new element up the heap until it is
	 * after an element with same or higher priority.
	 * 
	 * @param last is the index starting at the last element, which is where the new element is inserted
	 */
	private void upheap(int last) {
		while(last > 0) {
			int p = parent(last);
			//break if parent is higher priority than new element
			if(list.get(p).compareTo(list.get(last)) <= 0) break;
			swap(last, p);
			last = p;
		}
	}
	
	/**
	 * Used in re-prioritize method: restores min-heap order by moving the new element up the heap until it is
	 * at before the element with same priority.
	 * 
	 * @param last is the index starting at the last element, which is where the new element is inserted
	 */
	private void upheapMore(int last) {
		while(last > 0) {
			int p = parent(last);
			//break if parent is higher priority than new element
			if(list.get(p).compareTo(list.get(last)) < 0) break;
			swap(last, p);
			last = p;
		}
	}
	
	/**
	 * Finds the smaller sibling between the two child of 'first', and if this child has
	 * higher priority than parent, swap them until it isn't the case.
	 * 
	 * @param first is the index starting at 0, which is where the unordered element is after a removeMin
	 */
	private void downheap(int first) {
		while(hasLeft(first)) {
			int left = left(first);
			int smallerSibling = left;
			if(hasRight(first)) {
				int right = right(first);
				if(list.get(left).compareTo(list.get(right)) > 0)
					smallerSibling = right;
			}
			if(list.get(first).compareTo(list.get(smallerSibling)) <= 0) break;
			swap(first, smallerSibling);
			first = smallerSibling;
		}
	}
	
	/**
	 * inserts a new job in the PQ and upheaps to restore order.
	 * 
	 * @param job new job to insert in PQ
	 */
	public void insert(Job job) {
		if(job != null) {
			//increment entry time and add to PQ
			Job.setCurrentTime(Job.getCurrentTime() +1);
			job.setEntryTime(Job.getCurrentTime());
			list.add(job);
			upheap(list.size() -1);
		}
		else
			System.out.println("You tried to insert an invalid job.");
	}
	
	/**
	 * Removes and returns job with highest priority, those with
	 * equal priority will result in FIFO order. O(logn)
	 * 
	 * @return Job with highest priority 
	 */
	public Job removeMin() {
		if(!list.isEmpty()) {
			swap(0, list.size()-1);
			Job min = list.remove(list.size()-1);
			downheap(0);
			return min;
		}
		else
			return null;
	}
	
	/**
	 * @return Job with highest priority (does not remove it). O(1)
	 */
	public Job min() {
		if(!list.isEmpty())
			return list.get(0);
		else
			return null;
	}
	
	/**
	 *  executes each job in the PQ until it is empty. Re-prioritize is done if 30
	 *  processes have been terminated. Time complexity is O(n logn) where n is the number
	 *  of jobs times logn time for both removeMin and upheap.
	 */
	public void execute() {
		Job top;
		while(!list.isEmpty()) {
			top = removeMin();
			if(!top.execute()) {
				//increment entry time and add to PQ
				Job.setCurrentTime(Job.getCurrentTime() +1);
				list.add(top);
				upheap(list.size() -1);
			}
			//if job complete
			else { 
				totalWaitTime += top.getWaitTime();
				starveCheck++;
				if(starveCheck % 30 == 0)
					reprioritize();
			}
		}
	}
	
	/**
	 * re-prioritizes the oldest job that has never been executed so that it executes next.
	 * (finds oldest not executed job, set its priority to 1, upheap)
	 */
	public void reprioritize() {
		Job low = list.get(0);
		Job newLow; int index = 0; boolean change = false;
		for(int i = 1; i < list.size(); i++) {
			newLow = list.get(i);
			if(low.getEntryTime() > newLow.getEntryTime() && newLow.getCurrentJobLength() == newLow.getJobLength()) {
				low = newLow;
				change = true;
				index = i;
			}
		}
		//if a starved process exists, set priority to 1
		if(change) {
			priorityChanges++;
			low.setFinalPriority(1);
			upheapMore(index);
			System.out.println(" -------- Executing " + low.getJobName() + " next to prevent starved process --------");
		}
		else
			System.out.println(" -------- 30 jobs have been terminated, no starved process found --------");
	}
	
	/**
	 * @return size of PQ
	 */
	public int size() { return list.size(); }
	
	/**
	 * @return true if PQ empty, false otherwise
	 */
	public boolean isEmpty() { return size() == 0; }
	
	/**
	 * prints the PQ
	 */
	public void print() {
		if(list.isEmpty())
			System.out.println("-> Priority Queue is empty.");
		for(Job o : list)
			System.out.println(o);
	}
	
	public int getPriorityChanges() { return priorityChanges; }
	
	public long getTotalWaitTime() { return totalWaitTime; }
}

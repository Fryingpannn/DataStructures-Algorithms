package A3;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * @author Matthew Pan 40135588
 * 
 * Priority Queue using an unsorted LinkedList. Used to simulate CPU scheduling for executing processes/jobs
 * in an operating system.
 */
public class UnsortedPQ {
	private LinkedList<Job> list = new LinkedList<Job>();
	//counter to track for starved processes; increments every time a process finishes.
	//activates starve check when it's a multiple of 30
	private static int starveCheck = 0;
	//number of priority changes due to starvation
	private static int priorityChanges = 0;
	//total wait time of all jobs
	private static long totalWaitTime = 0;

	public UnsortedPQ() {}
	
	/**
	 * @param job new job to insert in PQ
	 */
	public void insert(Job job) {
		if(job != null) {
			//increment entry time and add to PQ
			Job.setCurrentTime(Job.getCurrentTime() +1);
			job.setEntryTime(Job.getCurrentTime());
			list.addFirst(job);
		}
	}
	
	/**
	 * Helper method used for removeMin() and min()
	 * @return Job with top priority (smallest jobPriority number) O(n)
	 */
	public Job findMin() {
		if(list.isEmpty())
			return null;
		
		Job top = list.peekFirst();
		ListIterator<Job> it = list.listIterator(1);
		//find highest priority job (smallest number)
		Job current;
		while(it.hasNext()) {
			current = it.next();
			//if current is higher priority, update top priority job
			if(current.compareTo(top) <= 0) {
				top = current;
			}
		}
		return top;
	}
	
	/**
	 * Removes and returns job with highest priority, those with
	 * equal priority will result in FIFO order. O(n)
	 * 
	 * @return Job with highest priority 
	 */
	public Job removeMin() {
		Job min = findMin();
		list.remove(min);
		return min;
	}
	
	/**
	 * @return Job with highest priority (does not remove it). O(n)
	 */
	public Job min() {
		return findMin();
	}
	
	/**
	 *  executes each job in the PQ until it is empty. Re-prioritize is done if 30
	 *  processes have been terminated. Time complexity is O(n*T) where n is the number
	 *  of jobs and T is the total job length of all jobs combined.
	 */
	public void execute() {
		Job job;
		while(!isEmpty()) {
			job = removeMin();
			//if job not completely terminated, execute will return false so we put the job back in PQ
			if(!job.execute()) {
				list.addFirst(job);
				Job.setCurrentTime(Job.getCurrentTime() +1);
			}
			//if job terminated
			else {
				totalWaitTime += job.getWaitTime();
				starveCheck++;
				if(starveCheck % 30 == 0)
					reprioritize();
			}
		}
	}
	
	/**
	 * re-prioritizes the oldest job that has never been executed so that it executes next. 
	 * Increments current time cycle.
	 */
	public void reprioritize() {
		ListIterator<Job> it = list.listIterator(1); 
		Job old = list.peekFirst(); 
		Job.setCurrentTime(Job.getCurrentTime() +1); //incrementing time
		Job newOld; boolean change = false;
		//finds the oldest job that has never been executed
		while(it.hasNext()) {
			newOld = it.next();
			if(newOld.getEntryTime() < old.getEntryTime() && newOld.getJobLength() == newOld.getCurrentJobLength()) {
				old = newOld;
				change = true;
			}
		}
		//if a starved process exists, set priority to 1
		if(change) {
			priorityChanges++;
			old.setFinalPriority(1);
			System.out.println(" -------- Executing " + old.getJobName() + " next to prevent starved process --------");
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

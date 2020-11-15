package A3;

import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Matthew Pan 40135588
 *
 * - Simulator process jobs from PQ based on priority (FIFO order if tie breaker)
 * - Prevent starvation of low priority jobs: each time 30 jobs are processed, simulator must
 *   check in PQ for the oldest job that has never been executed and change its priority to 1,
 *   such that it's executed right away next.
 * 
 * Simulating CPU scheduling for executing processes/jobs as in an operating system.
 * The program will keep looping to partially execute a job at a time until none are left.
 */
public class PriorityQueueSimulatorTester {
	static String systemTime;
	/**
	 * Testing unsorted list PQ
	 * 
	 * 100 jobs = around 200ms
	 * 1000 jobs = around 1000ms
	 * 10 000 jobs = around 20 000 ms (20 s)
	 * 100 000 jobs = around 3 000 000ms (50 mins)
	 * 	-> rate of increase is quadratic
	 * @param jobs jobsInputArray
	 * @param pq priority queue
	 */
	static void UnsortedList(ArrayList<Job> jobs, UnsortedPQ pq) {
		long start = System.currentTimeMillis();
		for(int i = 0; i < jobs.size(); i++) {
			pq.insert(jobs.get(i));
		}
		pq.execute();
		long end = System.currentTimeMillis();
		systemTime = end - start + "ms";
		printReport(pq, jobs.size());
	}
	
	/**
	 * Testing heap based PQ: built using bottom-up construction
	 * 
	 * 100 jobs = around 200ms
	 * 1000 jobs = around 1000ms
	 * 10 000 jobs = around 15 000 ms (15 s)
	 * 100 000 jobs = around 194 906 (3 mins)
	 * 	-> rate of increase is 
	 * 
	 * @param jobs array of jobs
	 */
	static void Heap(ArrayList<Job> jobs) {
		int nbOfJobs = jobs.size();
		long start = System.currentTimeMillis();
		HeapPQ pq = new HeapPQ(jobs);
		pq.execute();
		long end = System.currentTimeMillis();
		systemTime = end - start + "ms";
		printReport(pq, nbOfJobs);
	}
	
	//helper method to create an array of jobs of given size
	static void createArray(ArrayList<Job> arr, int size) {
		System.out.println("---------------------- Displaying array -----------------------------");
		for(int i = 0; i < size; i++) {
			arr.add(new Job(i+1));
			System.out.println(arr.get(i));
		}
		System.out.println("---------------------------------------------------------------------\n");
	}
	
	public static void main(String[] args) {
		Scanner read = new Scanner(System.in);
		//choose number of jobs you want to test
		System.out.print(" - Input max number of jobs: ");
		int maxNumberOfJobs = read.nextInt();
		System.out.print(" - Do you want to use \n\t1. Unsorted list or 2. Heap Priority Queue?\n\tEnter number: ");
		int choice = read.nextInt();
		ArrayList<Job> jobsInputArray = new ArrayList<Job>(maxNumberOfJobs);
		UnsortedPQ unsortedPQ = new UnsortedPQ();
		//initializing array
		createArray(jobsInputArray, maxNumberOfJobs);
		
		//choose between unsorted list or heap method
		switch(choice) {
			case 1: UnsortedList(jobsInputArray, unsortedPQ);
				break;
			case 2: Heap(jobsInputArray);
				break;
			default: System.out.println("Wrong input. Try again!");
		}
		read.close();
	}
	
	//print report and write to file
	static void printReport(UnsortedPQ pq, int jobNb) {
		System.out.println("\n--------------------------------- Unsorted List Report ---------------------------------");
		System.out.println("Current systemtime (cycles): " + Job.getCurrentTime());
		System.out.println("Total number of jobs executed: " + jobNb + " processes");
		System.out.println("Average process waiting time: " + pq.getTotalWaitTime()/jobNb + " cycles");
		System.out.println("Total number of priority changed: " + pq.getPriorityChanges());
		System.out.println("Actual system time needed to execute all jobs: " + systemTime);
		//write to output file
		try(PrintWriter out = new PrintWriter(new FileWriter("A3-listPQ-process-report.txt", true))) {
		out.println("\n--------------- Unsorted List Report -------------------");
		out.println("Current systemtime (cycles): " + Job.getCurrentTime());
		out.println("Total number of jobs executed: " + jobNb + " processes");
		out.println("Average process waiting time: " + pq.getTotalWaitTime()/jobNb + " cycles");
		out.println("Total number of priority changed: " + pq.getPriorityChanges());
		out.println("Actual system time needed to execute all jobs: " + systemTime);
		}
		catch(IOException e) {
			e.getMessage();
		}
	}
	
	//print report and write to file
	static void printReport(HeapPQ pq, int jobNb) {
		System.out.println("\n--------------------------------- Heap PQ Report ---------------------------------");
		System.out.println("Current systemtime (cycles): " + Job.getCurrentTime());
		System.out.println("Total number of jobs executed: " + jobNb + " processes");
		System.out.println("Average process waiting time: " + pq.getTotalWaitTime()/jobNb + " cycles");
		System.out.println("Total number of priority changed: " + pq.getPriorityChanges());
		System.out.println("Actual system time needed to execute all jobs: " + systemTime);
		//write to output file
		try(PrintWriter out = new PrintWriter(new FileWriter("A3-heapPQ-process-report.txt", true))) {
		out.println("\n--------------- Heap PQ Report -------------------");
		out.println("Current systemtime (cycles): " + Job.getCurrentTime());
		out.println("Total number of jobs executed: " + jobNb + " processes");
		out.println("Average process waiting time: " + pq.getTotalWaitTime()/jobNb + " cycles");
		out.println("Total number of priority changed: " + pq.getPriorityChanges());
		out.println("Actual system time needed to execute all jobs: " + systemTime);
		}
		catch(IOException e) {
			e.getMessage();
		}
	}

}

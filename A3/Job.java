package A3;

import java.util.Random;

/* 
 * @author Matthew Pan 40135588
 * 
 * Jobs class which represent the processes in the priority queue.
 * */
public class Job implements Comparable<Job> {
	//"JOB_(arrayIndexNb+1)"
	private String jobName;
	//needed CPU cycles for this job, randomize between 1 and 70 cycles
	private int jobLength;
	//remaining job length at any time
	private int currentJobLength;
	//initial priority, randomize between 1 (highest) and 40
	private int jobPriority;
	//final priority at termination time
	private int finalPriority;
	//time of when job entered PQ, set to currentTime when inserted
	private long entryTime;
	//time of when job terminated, currenTime when executed
	private long endTime;
	//total amount of wait time before execution, doesn't include execution time
	//(endTime - entryTime - jobLength) does not include execution's time, maybe endTime-1
	private long waitTime;
	//counter to track current time (CPU cycles);
	//+1 for every PQ insertion, job execution and search for starved process
	private static long currentTime = 0;
	
	/* default constructor */
	Job() { }
	
	/* param constructor: initialize job with name, the
	 * priority is randomized.
	 * 
	 * @param index the index in the array where the job is */
	Job(int index) {
		Random prio = new Random();
		jobName = "JOB_" + index;
		jobPriority = prio.nextInt(41) + 1;
		finalPriority = jobPriority;
		jobLength = prio.nextInt(71) + 1;
		currentJobLength = jobLength;
		entryTime = 0;
		endTime = 0;
		waitTime = 0;
	};
	
	//test constructor for non-random priority------------------------
	Job(int index, int priority) {
		Random prio = new Random();
		jobName = "JOB_" + index;
		jobPriority = priority;
		finalPriority = jobPriority;
		jobLength = prio.nextInt(71) + 1;
		currentJobLength = jobLength;
		entryTime = 0;
		endTime = 0;
		waitTime = 0;
	};
	
	/**
	 * decrements job length, sets end time and wait time.
	 * @return true if the job has terminated completely, false otherwise
	 */
	public boolean execute() {
		if(currentJobLength > 0) {
			currentJobLength--;
			System.out.print(" -> Now executing "); 
			System.out.println(this.toString());
		}
		//if job terminated
		if(currentJobLength <= 0) {
			endTime = currentTime;
			waitTime = endTime - entryTime - jobLength;
			currentTime++;
			return true;
		}
		//if job needs to be put back in PQ
		else {
			currentTime++;
			return false;
		}
	}
	
	/**
	 * compares the priority of two jobs, if same = FIFO order
	 * @return -1 if this job is higher priority or equal to the passed one (no swap)
	 */
	public int compareTo(Job o) {
			if(this.finalPriority < o.getFinalPriority())
				return -1;
			else if(this.finalPriority == o.getFinalPriority())
				return 0;
			else
				return 1;
	}

	
	/**
	 * @param o job to compare with
	 * @return true if they are the same job
	 */
	public boolean equals(Job o) {
		return jobName.equals(o.getJobName());
	}
	
	/** print the job */
	public String toString() {
		return jobName + ": [Job length - " + jobLength + " cycles]" + " [Remaining length - " + currentJobLength + " cycles]"
	+ " [Initial priority - " +Integer.toString(jobPriority) + "]" + " [Current priority - " + finalPriority + "]" + " [Current time - " + currentTime + "]";
	}
	
	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * @return the jobLength
	 */
	public int getJobLength() {
		return jobLength;
	}

	/**
	 * @param jobLength the jobLength to set
	 */
	public void setJobLength(int jobLength) {
		this.jobLength = jobLength;
	}

	/**
	 * @return the currentJobLength
	 */
	public int getCurrentJobLength() {
		return currentJobLength;
	}

	/**
	 * @param currentJobLength the currentJobLength to set
	 */
	public void setCurrentJobLength(int currentJobLength) {
		this.currentJobLength = currentJobLength;
	}

	/**
	 * @return the jobPriority
	 */
	public int getJobPriority() {
		return jobPriority;
	}

	/**
	 * @param jobPriority the jobPriority to set
	 */
	public void setJobPriority(int jobPriority) {
		this.jobPriority = jobPriority;
	}

	/**
	 * @return the finalPriority
	 */
	public int getFinalPriority() {
		return finalPriority;
	}

	/**
	 * @param finalPriority the finalPriority to set
	 */
	public void setFinalPriority(int finalPriority) {
		this.finalPriority = finalPriority;
	}

	/**
	 * @return the entryTime
	 */
	public long getEntryTime() {
		return entryTime;
	}

	/**
	 * @param entryTime the entryTime to set
	 */
	public void setEntryTime(long entryTime) {
		this.entryTime = entryTime;
	}

	/**
	 * @return the endTime
	 */
	public long getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the waitTime
	 */
	public long getWaitTime() {
		return waitTime;
	}

	/**
	 * @param waitTime the waitTime to set
	 */
	public void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}

	/**
	 * @return the currentTime
	 */
	public static long getCurrentTime() {
		return currentTime;
	}
	public static void setCurrentTime(long n) {
		currentTime = n;
	}
}

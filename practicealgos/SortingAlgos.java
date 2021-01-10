package practicealgos;
import java.util.ArrayList;
import java.util.Random;

import A4.Student;

public class SortingAlgos {

	
	public void test() {
		int[] arr = {12,3};
		insertionSort(arr, 4);
	}
	public static void insertionSort(int arr[], int n) {
		
		for(int i = 1; i < n; i++) {
			
			//Chose a key and a marker j, will first compare these two
			//if arr[j] is bigger, also sort everything before it to the right
			int key = arr[i];
			int j = i - 1;
			
			while(j >= 0 && arr[j] > key) {
				arr[j+1] = arr[j];
				j--;
			}
			arr[j+1] = key; //assign the replaced value to where it belongs, j+1 because while loop finished with decrement
		}
	}
	
	//recursive method which chooses a pivot than sorts everything to the left and right of it recursively
	public static void quicksort(int[] arr, int l,int r) {
		 
		if(l >= r)	//base case, quit when first index becomes equal to last index
			return;
		
		int p = partition(arr, l, r); //returns the index of pivot
		
		quicksort(arr, l, p-1); //recursion on smaller elements
		quicksort(arr, p+1, r); //recursion on bigger elements
	}
	
	//partitions the array in a way to make the smaller elements on the left and bigger elements on right side of pivot
	public static int partition(int[] arr, int l, int p) {
		Random rand = new Random();
		
		//random pivot
		int pivotInd = rand.nextInt(p-l) + l;
		swap(arr, pivotInd, p);
		int pivot = arr[p];
		
		//last element as pivot
		//int pivot = arr[p];

		int i = l - 1; // this is the index that indicates the last element that is smaller than the pivot
		
		for(; l < p; l++) { //j is the index that indicates the last element that is larger than the pivot
			
			if(arr[l] < pivot) {	//switch the values at i and j if a smaller value than pivot found (separate small & large)
				i++;				//to make it reverse order, simply put  < pivot
				swap(arr, i, l);
			}
		}
		
		//switch the pivot in between the smaller and larger elements
		swap(arr, i+1, p);
		
		return i+1;	//return index of the pivot which is now in between smaller and larger elements
	}
	
	//swap method which can be reused, make sure to include array as parameter
	public static void swap(int[] nums, int i, int j) {
		int temp = nums[i];
		nums[i] = nums[j];
		nums[j] = temp;
	}
	
	//binary search, return index of searched value (array must be SORTED)
	static int binarySearch(int[] arr, int l, int r, int x) {
		
		if(r >= l) {
			int mid = l + (r-l)/2;
			
			if(x == arr[mid])
				return mid;
			else if(x > arr[mid])
				return binarySearch(arr, mid+1, r, x);
			else if(x < arr[mid])
				return binarySearch(arr, l, mid-1, x);
		}
		return -1; //not found
	}

	//merge sort methods to sort list when multiple insertions are done at once
//	private void mergesort(ArrayList<Student> arr) {
//		//base case when 1 element left
//		int n = arr.size();
//		if(n < 2) return;
//		
//		//separate array in half
//		int mid = n/2;
//		
//		ArrayList<Student> left = new ArrayList<Student>(mid+1);
//		ArrayList<Student> right = new ArrayList<Student>(n-mid+1);
//		for(int i = 0; i < mid; i++)
//			left.add(arr.get(i));
//		for(int i = mid; i < n; i++)
//			right.add(arr.get(i));
//		//conquer
//		mergesort(left);
//		mergesort(right);
//		//merge
//		merge(left, right, arr);
//	}
//	private void merge(ArrayList<Student> left, ArrayList<Student> right, ArrayList<Student> arr) {
//		//i represents how many we added from left, and j represents how many we added from right to arr
//		int i = 0, j = 0;
//		//keep merging until reaches length of both array combined
//		while(i + j < arr.size()) { //compare keys in both array and add the minimum. if one is done, add all of the other.
//			if(j == right.size() || (i < left.size() && left.get(i) < right.get(j)))
//				arr.set(i+j, left.get(i++));
//			else
//				arr.set(i+j, right.get(j++));
//		}
//	}
	
	
	public static void main(String[] args) {
		int[] arr = {6, 3, 7, 1, 10, 8, 2, 4, 9, 5};
		int[] arr2 = {3, 4, 5, 6};
		
		System.out.println("Quick Sort Time: ");
		//runtime speed quick sort
		int[] arrCopy = {6, 3, 7, 1, 10, 8, 2, 4, 9, 5};
		long startTime2 = System.nanoTime();
		quicksort(arrCopy, 0, arrCopy.length -1);
		long stopTime2 = System.nanoTime();
		System.out.println(stopTime2 - startTime2);
		
		System.out.println("Insertion Sort Time: ");
		//runtime speed test insertion sort
		long startTime = System.nanoTime();
		insertionSort(arr, arr.length);
		long stopTime = System.nanoTime();
		System.out.println(stopTime - startTime);
		
		
		quicksort(arr2, 0, arr2.length -1);
		
		//test cases
        int[] a1 = {1, 2, 3};
        int[] a2 = {3, 2, 1};
        int[] a3 = {};
        int[] a4 = {3, 1, -1, 0, 2, 5};
        int[] a5 = {2, 2, 1, 1, 0, 0, 4, 4, 2, 2, 2};

        int[] a1Sorted = {1, 2, 3};


        quicksort(a2, 0, a2.length-1);
//        quicksort(a2);
//        quicksort(a3);
//        quicksort(a4);
//        quicksort(a5);
//        quicksort(a6);
//        quicksort(a7);
//        quicksort(a8);
//        quicksort(a9);
		
        System.out.println("Quicksorting + Insertion sort: ");
		for(int a : arrCopy)
			System.out.print(a + " ");
		System.out.println();
		for(int a : arr)
			System.out.print(a + " ");
		
		System.out.println("\nBinary Search: ");
		System.out.println(binarySearch(arr, 0, arr.length, 3));
		
		
		int[] arr111 = {15, 5, 20, 2, 13, 8, 10, 14, 6, 11, 18, 4, 17, 12, 7, 9};
		quicksort(arr111,0,arr111.length-1);
		for(int i : arr111)
			System.out.print(i + " ");

	}

}

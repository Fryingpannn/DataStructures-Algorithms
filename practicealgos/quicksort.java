public class QuickSort{

	public static void quicksort(int[] arr, int l, int r){

		if(l >= r)
			return;

		int p = partition(arr, l, r);

		quicksort(arr, l, p-1);
		quicksort(arr, p+1, r);
	}

	public static int partition(int[] arr, int l, int r){
		//Random rand = new Random();

		//int pivot = rand.nextInt(r - l) + l;
		//swap(arr, pivot, r)
		int pivot = arr[r];
		int i = l-1; //keeps track of the last smallest element

		for(int j = l; j < r; j++){
			if(arr[j] < pivot){	//swaps with itself if no bigger value, that's why need i
				i++;
				swap(arr, i, j);
			}
		}
		swap(arr, i+1, r);
		return i+1;
	}

	public static void swap(int[] arr, int i, int j){
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

}
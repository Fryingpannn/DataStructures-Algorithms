package practicealgos;

import java.util.ArrayList;

// simply change the hash functions in hash or doubleHash
// to adapt to your double hash implementation.
public class Hashing {
	static int size = 11;
	static int[] arr = new int[size];
	static int EMPTY = -88888888;
	public static int nbCollision = 0;
	
	//things to change
	// 1. size of hash table
	// 2. comment out the other code in put(int) for appropriate hash technique
	// 3. change the hash functions to given one
	public static void main(String[] args) {
		//element -99 means empty spot in array
		for(int i = 0; i < size; i++)
			arr[i] = EMPTY;
		
		/*=== testing double hashing from assignment 3 ===*/
//		put(25); put(12); put(42); put(31); put(35); put(39);
//		del(31); put(48); del(25); put(18);
//		put(29); put(29); put(35);
		
		// LINEAR/QUAD PROBING TEST/DOUBLE HASH
	//	put(13); put(22); put(20); put(15); put(2); put(16); put(45);
		
		
		//q3 double hash
//		put(22); put(59); put(47); put(32); put(31); del(59); put(71);
//		del(32); put(18); put(39);
		
		put(16); put(27); put(22); put(59); put(44); put(32); del(59);
		put(33); del(32); put(71);
		
		//print();
		System.out.println("Collision nb: " + Hashing.nbCollision);
		
//		int[] arr = {0, 2, 2, 5, 2, 1, 3, 4, 6};
//		IncrJump(arr, 5);
	}
	
	static void put(int nb) {
		arr[hash(nb)] = nb; // double hashing
//		arr[linearprob(nb)] = nb; // linear probing
//		arr[quadraticprob(nb)] = nb; // quad
	}
	
	//remove a nb
	static void del(int nb) {
		for(int i = 0; i < size; ++i)
			if(arr[i] == nb)
				arr[i] = EMPTY;
	}
	
	//returns index location to add new element
	static int hash(int nb) {
		
		//1. mod function
		int index = nb % size;
		
		if(arr[index] == EMPTY)
			return index;
		else
			return doubleHash(nb, index);
	}
	
	// returns index location to add new element
	// should only need to change "(q-nb%q)" and "q"
	static int doubleHash(int nb, int firstH) {
		int i = 1;  // i = 1, 2, ...
		
		int index = 1;
		boolean go = true; //just to make loop run the first time
		int secondHash = 3 - nb % 3; //second hash function 
		
		//keep hashing till reach an empty spot in array
		while(go) {
			index = ( (firstH) + i * (secondHash) ) % size; //(hashFunc1 + i * hashFunc2) mod N
			i++;
			nbCollision++;
			
			if(arr[index] == EMPTY) //stop loop, found empty spot
				go = false;
		}
		return index;
	}
	
	//linear probing
	static int linearprob(int nb) {
		int index = (3*nb + 5) % size; //1st hash func
		
		boolean go = true;
		if(arr[index] != EMPTY) {
			while(go) {
					index = (index + 1) % size; //linear probing
					nbCollision++;
				if(arr[index] == EMPTY)
					go = false;
			}
		}
		return index;
	}
	
	//quadratic probing
	static int quadraticprob(int nb) {
		int index = (nb) % size; // 1st hash func
		
		int i = 1;
		boolean go = true;
		if(arr[index] != EMPTY) {
			while(go) {
					index = (index + i*i) % size; // quadratic probing
					nbCollision++;
					++i;
				if(arr[index] == EMPTY)
					go = false;
			}
		}
		return index;
	}

	static void print() {
		int k=0;
		for(int i : arr) {
			System.out.println(k++ + " [" + i + "] ");
		}
	}
	
	//testing valid array for reaching case 0
	private static boolean IncrJump(int[] arr, int curr) {
        System.out.println(curr + " index. Element: " + arr[curr]);

        if (arr[curr] == 0) return true;

        int place = arr[curr];
        arr[curr] = -1;

        if (curr + place + 1 < arr.length)
            if (arr[curr + place+1] != -1)
                return IncrJump(arr, curr + place+1);

        if (curr - (place + 1) >= 0)
            if (arr[curr - (place+1)] != -1)
                return IncrJump(arr, curr - (place+1));

        return false;
    }
}

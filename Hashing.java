package general;

import java.util.ArrayList;

// simply change the hash functions in hash or doubleHash
// to adapt to your double hash implementation.
public class Hashing {
	static int size = 11;
	static int[] arr = new int[size];
	static int EMPTY = -88888888;
	public static int nbCollision = 0;
	
	public static void main(String[] args) {
		//element -99 means empty spot in array
		for(int i = 0; i < size; i++)
			arr[i] = EMPTY;
		
		/*=== testing double hashing from assignment 3 ===*/
//		put(25); put(12); put(42); put(31); put(35); put(39);
//		del(31); put(48); del(25); put(18);
//		put(29); put(29); put(35);
		
		/*=== testing linear probing ===*/
		put(12); put(44); put(13); put(88); put(23); put(94);
		put(11); put(39); put(20); put(16); put(5);
		
		
		print();
		System.out.println("Collision nb: " + Hashing.nbCollision);
	}
	
	static void put(int nb) {
		//arr[hash(nb)] = nb; //double hashing
		arr[linearprob(nb)] = nb; //linear probing
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
		int q = 7; //part of the second hash function. (change this as needed)
		
		int index = 0;
		boolean go = true; //just to make loop run the first time
		
		//keep hashing till reach an empty spot in array
		while(go) {
			index = ( (firstH) + i * (q-nb%q) ) % size; //(hashFunc1 + i * hashFunc2) mod N
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
		int index = (3*nb + 5) % size; //1st hash func
		
		boolean go = true;
		if(arr[index] != EMPTY) {
			while(go) {
					index = (index^2) % size; //quadratic probing
					nbCollision++;
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
}

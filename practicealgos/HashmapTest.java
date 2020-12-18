package practicealgos;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.Queue;

public class HashmapTest {

	public static void main(String[] args) {
		
		//          HASHMAPS
		
		//mapping a strings as keys to integers as values (Map is an abstract class)
		Map<String, Integer> m = new HashMap<String, Integer>(4, 0.5f); //optional, just use default constructor usually
		// (4, 0.5f) is -> (initial capacity of map, load factor)  -->
		// (number of rows aka size of table, nb of items needed before we double size), here it's at 50% = double (like ArrayList)
		
		//using methods to access table (map)
		//the keys are unique, adding the same will overwrite previous. Case-sensitive!!
		m.put("Cookies", 1); //will put cookie:1 in random row of table
		m.put("Brownies", 2);
		
		m.putIfAbsent("Pudding", 3);
		
		m.replace("Cookies", 3); //replace the value associated with key
		
		m.get("Brownies"); // retrieve value of a key
		
		//copying everything in m inside new hashmap, same as using copy constructor
		Map<String, Integer> mCopy = new HashMap<String, Integer>();
		mCopy.putAll(m);
		
		//iterating over keys: use ketSet() and get()
		for (String snack : mCopy.keySet())
			System.out.println(snack + " : " + mCopy.get(snack));
		
		//Entry<String, Integer> mEntry = ........ use entry with iterator to get full row pairs
		
		System.out.println("hello".hashCode());
		
	}

}

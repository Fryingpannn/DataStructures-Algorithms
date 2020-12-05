//Matthew Pan 40135588, November 2020

package A4;

public class Student {
	//key
	private int id;
	//value
	private int age;
	
	//constructors
	Student(){id = 0; age = 0;}
	Student(int id, int age){
		this.id = id;
		this.age = age;
	}
	public Student(Student copy) {
		copy.setKey(id);
		copy.setValue(age);
	}
	
	//accessors
	int getKey() { return id; }
	int getValue() { return age; }
	void setKey(int id) { this.id = id; }
	void setValue(int age) { this.age = age; }
	//print
	public String toString() {
		return "ID: " + id + " | Age: " + age;
	}
}
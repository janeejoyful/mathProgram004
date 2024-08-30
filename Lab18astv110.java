// Lab18astv110.java
// Janee Yeak
// 11 May 2021
// This program sorts students by gpa, by age, and by id number. It can also search for a student based on the 
// ID number. It then displays the students again without the student that was searched for. 
// It searches through the students' ID using binary search. :)


import java.io.*;
import java.util.*;
import java.text.DecimalFormat;


public class Lab18astv110
{
	public static void main(String[] args) throws IOException
	{
		List studentArray = new List(100);
		studentArray.getList();
		studentArray.display("UNSORTED LIST OF STUDENTS");
		studentArray.pause();

		studentArray.gpaSort();
		studentArray.display("STUDENTS SORTED IN DESCENDING ORDER BY GPA");
		studentArray.pause();

		studentArray.ageSort();
		studentArray.display("STUDENTS SORTED IN ASCENDING ORDER BY AGE");
		studentArray.pause();

		studentArray.idSort();
		studentArray.display("STUDENTS SORTED IN ASCENDING ORDER BY ID#");
		studentArray.pause();

		int studentID = getID();
    	int index = studentArray.search(studentID);

		if (index == -1)
		    System.out.println("There is no student with an ID# of "+studentID+".\n");
		else
		{
			studentArray.displayStudent(index);   // displays the information for the found student
  			studentArray.delete(index);           // remove the same student from the array
			studentArray.display("STUDENTS SORTED IN ASCENDING ORDER BY ID# WITHOUT STUDENT# "+studentID);
			studentArray.pause();
		}
	}

	public static int getID()
	{
		Scanner input = new Scanner(System.in);
		System.out.print("\nEnter the 6-digit ID of the student.  { 100000 - 999999 }  -->  ");
		return input.nextInt();
	}
}


class Person
{
	public String name;
	public int id;
	public int age;
	public double gpa;

	Person(String n, int ID, int a,double g)
	{
		name = n;
		id = ID;
		age = a;
		gpa = g;
	}
   
   public String toString()
   {
      return "ID is " + id + "name is " + name + "age is " + age + "gpa is " + gpa;
   }
   
}


class List
{
	private Person student[];	// stores array elements
	private int capacity;		// actual array capacity
	private int size;			// number of elements in the array

	public List(int c)
	{
		capacity = c;
		size = 0;
		student = new Person[capacity];
	}

	public void getList() throws IOException
	{
		FileReader inFile = new FileReader("students.dat");
		BufferedReader inStream = new BufferedReader(inFile);
		String s1,s2,s3,s4;
		int age, id;
		double gpa;
		int index = 0;
		while( ((s1 = inStream.readLine()) != null) &&
			   ((s2 = inStream.readLine()) != null) &&
			   ((s3 = inStream.readLine()) != null) &&
			   ((s4 = inStream.readLine()) != null) )
		{
			String name = s1;
			id = Integer.parseInt(s2);
			age = Integer.parseInt(s3);
			gpa = Double.parseDouble(s4);

			student[index] = new Person(name,id,age,gpa);
			index++;
		}
		inStream.close();
		size = index;
	}

	private String spaces(String name)
    {
    	int tab = 24 - name.length();
    	String temp = "";
    	for (int j = 1; j <= tab; j++)
    	    temp += " ";
    	return temp;
    }

	public void display(String listInfo)
	{
		DecimalFormat output = new DecimalFormat("0.000");
		System.out.println("\nDISPLAYING "+ listInfo);
		System.out.println("\nStudent ID#     Student Name            Age         GPA");
		System.out.println("============================================================");

		for (int k = 0; k < size; k++)
			System.out.println(student[k].id + "          "+student[k].name + spaces(student[k].name) + student[k].age + "          " + output.format(student[k].gpa));
	}

	public void pause()
	{
		Scanner input = new Scanner(System.in);
		String dummy;
		System.out.println("\nPress <Enter> to continue.");
		dummy = input.nextLine();
	}

	public void displayStudent(int index)
	{ 
      System.out.println("Name:  " + student[index].name);
      System.out.println("Age:   " + student[index].age);
      System.out.println("GPA:   " + student[index].gpa);
	}

	private void swap(int x, int y)
	{
      Person temp = student[x];
      student[x] = student[y];  
      student[y] = temp;

	}

	public void gpaSort()
	{   
   
      boolean sorted; // in case you are wondering why I used the bubble sort, it is because I found it difficult 
		int p = 1;      // to use other sort methods.  I tried all of them (Except for merge sort)
		do              // and the bubble sort was friendly and forgiving, so here it is.Maybe fate wanted me to use the 
		{               // ineffcient bubble sort. (If I spelt ineffcient wrongly, sorry ) 
  		   sorted = true;    // I think I will use the bubble sort for the other ones too.
			for (int q = 0; q < 33-p; q++) 
				if (student[q].gpa < student[q+1].gpa)
				{
					swap(q,q+1);
					sorted = false;
				}
			p++;
		}
		while (!sorted);  
   } 
	

	public void ageSort()
	{
      boolean sorted; 
		int p = 1;      
		do              
		{               
		   sorted = true;
			for (int q = 0; q < 33-p; q++) 
				if (student[q].age > student[q+1].age)
				{
					swap(q,q+1);
					sorted = false;
				}
			p++;
		}
		while (!sorted); 
	}

	public void idSort()
	{
      boolean sorted; 
		int p = 1;      
		do              
		{               
		   sorted = true;
			for (int q = 0; q < 33-p; q++) 
				if (student[q].id > student[q+1].id)
				{
					swap(q,q+1);
					sorted = false;
				}
			p++;
		}
		while (!sorted);
	}

	public int search(int studentID)
	{
      idSort();
      
      boolean found = false;
		int lo = 0;
		int hi = size-1;
		int mid = 0;
		while (lo <= hi && !found)
		{
			mid = (lo + hi) / 2;
			if (student[mid].id == studentID)
				found = true;
			else
			{
				if (studentID > student[mid].id)
					lo = mid + 1;
				else
					hi = mid - 1;
			}
		}
		if (found)
			return mid;
		else
			return -1;
	}

	public void delete(int index)
	{
		// Precondition:  "index" stores the index of a student object that exists in the "student" array.
		// Postcondition: The student object at index "index" is removed from the "student" array.
		//                All other objects in the "student" array are unaffected.
      
      
      Person[] student2 = new Person[student.length];;
      int hi = 0;
      
      for (int k = 0; k < student.length; k++)
      {
         if(k != index)
         {
            student2[hi] = student[k];
            hi++;
         }
      }   
      
      student = student2;
      size--;

	}
} 


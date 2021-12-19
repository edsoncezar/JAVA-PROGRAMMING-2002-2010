    /* CS235 Project 1
	 *  Brett Greenwood
	 *  January 25, 2002
	 */
import java.io.*;
import StudentDataBase;

public class Proj1 {
    
public static void main (String args[]) throws IOException 
{
  int totAccesses = 0;
	int queries = 0;
	int unsuc = 0;
	int N = 125; //number of students to read in
	
	try {
    String filename = "names.txt";
    BufferedReader br = new BufferedReader(new FileReader (filename));
    
    StudentDataBase list = new StudentDataBase();
    //StudentNode s;
    String ssn=null, name=null, address1=null, address2=null, phone=null;
    
    String line = br.readLine();
  	char char1;
	
		//-----------------------------------------
		//read in the students and build database |
		//-----------------------------------------		
		int i = 1;
    //while (line != null) {
	//for (int i=0; i < N; i++) {
			while (i <= N) {
				char1 = line.charAt(0);
        if(Character.isDigit(char1)) {
            //s = new StudentNode();
            
						ssn = line;
            name = br.readLine();     //read 4 more lines, inserting student data
            address1 = br.readLine();
            address2 = br.readLine();
            phone = br.readLine();
            
            list.AddStudent (ssn, name, address1, address2, phone);
						i++;
			//System.out.println(ssn);
        }
        else {
            list.AddCourse(line);     //add this course to s
        }
        line = br.readLine();
    }
	
	//-----------------------------------------
	//  open the query file and find matches  |
	//-----------------------------------------
	
	//Open the query file and the output file
	br = new BufferedReader(new FileReader ("REQUESTS.TXT"));
	
	FileWriter fw = new FileWriter ("queries.txt");
	BufferedWriter bw = new BufferedWriter (fw);
	PrintWriter outFile = new PrintWriter (bw);
	line = br.readLine();
	
	while (line != null) { 		//	While there are more queries do
		
		//String query = line;	//read a query (ssn)
		String result = list.GetName(line);	//call GetName(ssn)
		
		if (result != ""){ 		//GetName returned a name {
			totAccesses += list.GetAccessCount();	//add access count to total accesses.
			queries ++;		//count 1 query.
			
		}
		else
			unsuc++;
		
		outFile.println(line);	//write the ssn and name to the output file.
		outFile.println(result);
		//System.out.println(totAccesses + ", " + queries);
		line = br.readLine();
	}
	
	outFile.close();	//Close the output file
		
	list.DumpToFile("names_out.txt");
	}// end catch
	catch (IOException e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
	}
	
	System.out.println("Average number of nodes accessed per query: " + totAccesses/queries);//Report Average Access-count
    System.out.println("Number of unsuccessful queries: " + unsuc);
}
}

/*3. Write a report that includes the following:
a) Explain how Student Data Base class is an example of a data model. Tell what the values in the data model are (the static part) and what the operations are (the dynamic part).

b) Modify the loop that reads the students and adds them to the Student Data Base so that it terminates after reading in N students. 

The following is psuedocode for the modifications needed: 

For i = 1 to N
Read the data for the next student
Add the student to the Student Data Base (call AddStudent)
For each of the student's classes
read the class and add it to
the Student Data Base (call AddClass)

Run you program for N = 2000, 1000, 500, 250, 125 (where N is the number of students read into your database) and report in a table the average access count for each value of N. 

The table might look like this:


N      Average Access Count
125        	6
250        	12
500        	23
1000       	45
2000      	88

*/

/*public class List {
Node head;
Node current;
}*/

/*5 classes:
 *list, node
 *student
 *course
 *project/main
 */
/*
Node
data
Object data;
Node next;

List
data
Node head
Node current

methods: goToFront, goToBack, goToNext <-- to move pointer current
isFront (current==head), isBack (current.next==null)
getData, setData (Object d), insert(Object d) 

List st_list = new List();
st_list.insert(new Student);
st_list.gotoNext();
 */

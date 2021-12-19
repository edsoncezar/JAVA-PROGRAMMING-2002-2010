import java.util.*;

public class CustomerListNode{

	private String salutation;
	private String fname;
	private String lname;
    private String address;
    private String email;
    private String recentPurchase;
    private int preferedMailingSystem; // 0 = regular mail  1 = email
    private String customerID;

	private CustomerListNode next;
 

        public CustomerListNode()
        {
            fname = "";
            lname = "";
            salutation = "";
            email = "";
            address = "";
            recentPurchase = "";
            preferedMailingSystem = -1;
            customerID = "";
            next = null;
        }
            
 public CustomerListNode(String jtextString) 
 {
        String delim = " ";
        StringTokenizer fields = new StringTokenizer(jtextString);
        int i=0;

        while (fields.hasMoreTokens()) {

                switch (i) {
                    case 0:  lname = fields.nextToken(delim); 
                             break;
                    case 1:  fname = fields.nextToken(delim); 
                             break;
                    case 2:  salutation = fields.nextToken(delim); 
                             break;
                    case 3:
                            email = fields.nextToken(delim);
                            break;
                    default: System.out.print("default switch"); 
                             break;
                } //End switch
                i++;
        } //End while
        next = null;
   } //End parse string constructor
    
	
	public CustomerListNode(String s1,String s2,String s3,String s4,
			String s5, String s6, int i, String s7){
		
		salutation = s1;
		fname = s2;
		lname = s3;
        email = s4;
        address = s5;
        recentPurchase = s6;
        preferedMailingSystem = i;
        customerID = s7;
        next = null;

	} // CustomerListNode
	
	public void setData(String s1,String s2,String s3,String s4,
			String s5, String s6, int i, String s7){
	
		salutation = s1;
		fname = s2;
		lname = s3;
        email = s4;
        address = s5;
        recentPurchase = s6;
        preferedMailingSystem = i;
        customerID = s7;

	} // setData()
	
	public String getLastName(){
		return lname;
	}
        
    public String getFirstName() {
            return fname;
    }
        
    public String getSalutation() {
            return salutation;
    }
    
    public String getEmail() {
    	return email;
    }
    
    public String getAddress() {
    	return address;
    }
    
    public String getRecentPurchase() {
    	return recentPurchase;
    }
    
    public int getPreferedMailingSystem() {
    	return preferedMailingSystem;
    }
    
    public String getCustomerID() {
		return customerID;
	}
	
	public String getData(){
		return salutation + " " + fname + " " + lname;
	} // getData()
	
	public String toString(){
	
		return salutation + " " + fname + " " + lname;
	} // toString()
	
	public void setNext(CustomerListNode nextPtr){
	
		next = nextPtr;
	} // setNext()
	
	public CustomerListNode getNext(){
	
		return next;
	} // getNext()
	
} // CustomerListNode()

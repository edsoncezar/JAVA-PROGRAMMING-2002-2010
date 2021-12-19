// Filename Friend.java.
// Second Java object written for 
// the JFL book chapter 2 - see text.
//
// Fintan Culwin, V0.1, August 1997.

public class Friend extends Object { 

private String friendsName;
private int    friendsAge  = 0;

private static int          numberOfFriends     = 0;
private static final String PERSONAL_GREETING   = "My Dear Friend";

   Friend( String thierName,
           int    thierAge) { 
     
      friendsName = new String( thierName);      
      friendsAge  = thierAge;       
      numberOfFriends++;
   } // End Friend constructor.
   

   public String friendsNameIs(){ 
      return friendsName;   
   } // End friendsNameIs.

   public int friendsAgeIs(){ 
      return friendsAge;   
   } // End friendsAgeIs.


   public void birthday(){    
      friendsAge++;
   } // End birthday.


   static public int numberOfFriendsIs() { 
     return numberOfFriends;   
   } //End numberOfFriendsIs
   
   static public String greetingIs() { 
      return PERSONAL_GREETING;
   } //End greetingIs.   

} // End Friend.

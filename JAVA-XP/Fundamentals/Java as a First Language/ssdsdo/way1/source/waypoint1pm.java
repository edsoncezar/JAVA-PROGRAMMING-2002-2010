// WayPoint1am.java
//    For assessment purposes only.
//    Lacks real comments!
// v0.1  10/01
// By P.Campbell
//=====================================

public class WayPoint1pm {

   String userName ="pm student name";
   String StudentNo="000000000000000";


   public static void main( String[] args){

	 WayPoint1pm myClass = new WayPoint1pm();
				  
	 myClass.displayName();
	 myClass.displayCode();

   }   // End main()

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// Students should ignore everything below this point
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   private void displayName() {
	 int x;

	 for( x = 2; x < userName.length(); x+=2)
		 System.out.println( "Your name is   : " + userName);
	 System.out.println( "Student Number : " + StudentNo);
   }   // End displayName()
//===============================
//DisplayCode()
// Used to confirm student has
// completed the work set
//===============================
   private void displayCode() {

	 int letterCount, codeValue = 0;

	 for( letterCount = 0; letterCount < userName.length(); letterCount++){
		codeValue += (int) userName.charAt(letterCount) * ( letterCount + 1);
	 } // End for
	 codeValue *= 2;

	 if( this.toString().substring(0,6).equals( "Way1pm")) codeValue++;
	 System.out.println( "Code  : " + codeValue);
	 System.out.println( "Class : " + this.getClass().getName());
	 System.out.println( "home  : " + System.getProperty("user.home"));
	 System.out.println( "name  : " + System.getProperty("user.name")); 
   
   }   // End displayCode()
}   // End class WayPoint1a

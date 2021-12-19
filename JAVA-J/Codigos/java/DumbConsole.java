import java.io.*;

/**
 * <p> The <code>DumbConsole</code> class can be used by interactive
 * console-based applications to obtain various types of input from 
 * the standard input stream.   </p>
 *
 * <p> It is <i>dumb</i> because it does not do any error handling.  
 * For example, <code>getInt()</code> does not ensure that only valid 
 * <code>int</code> values are entered. </p>
 *
 * <p> Example usages: </p>
 * <pre>
 *    int i = DumbConsole.getInt("Enter small int: ", System.out);
 *    float f = DumbConsole.getFloat("Enter price: ", System.err);
 *    String s = DumbConsole.getString("What's your name? ", System.out);
 * </pre>
 *
 * <p> Each input method takes two arguments:  a <code>String</code>
 * prompt and a <code>PrintStream</code> object handle. </p>
 *
 * <p> No API has been written for this class. </p>
 *
 * <p> Either fix defects you find and submit your changes to the author,
 * or else redirect your problems to /dev/null. </p>
 *
 * @author G.D.Thurman
 * @version 2000.01.18
 *
 * @see String
 * @see StringBuffer
 * @see System
 * @see Character
 * @see Integer
 * @see Double
 * @see PrintStream
 *
 * @created 1999.01.21
 */

public class DumbConsole {

   public static byte getByte(String prompt, PrintStream o) {
      return (byte)getInt(prompt, o);
   }

   public static short getShort(String prompt, PrintStream o) {
      return (short)getInt(prompt, o);
   }

   public static int getInt(String prompt, PrintStream o) {  
      issuePrompt(prompt, o);
      return Integer.parseInt(getLine(true).trim()); 
   } 

   public static long getLong(String prompt, PrintStream o) {  
      issuePrompt(prompt, o);
      return Long.parseLong(getLine(true).trim()); 
   } 

   public static float getFloat(String prompt, PrintStream o) {  
      return (float)getDouble(prompt, o);
   }

   public static double getDouble(String prompt, PrintStream o) {  
      issuePrompt(prompt, o);
      String numberStr = getLine(true).trim();
      return Double.valueOf(numberStr).doubleValue(); 
   }

   public static char getChar(String prompt, PrintStream o) {
      issuePrompt(prompt, o);
      return getLine(false).charAt(0);
   }

   public static String getString(String prompt, PrintStream o) {
      issuePrompt(prompt, o);
      return getLine(true);
   }

   public static boolean ynPrompt(String prompt, PrintStream o) {
      return readYN(prompt + "? (y/n): ", o);
   }

   public static String getWord() {
      return readString("\t \n", true);
   }

   public static String getLine(boolean noLeadingWhiteSpace) {
      return readString("\n", noLeadingWhiteSpace);
   }

   public static void issuePrompt(String p, PrintStream o) {
      o.print(p); 
      //o.flush();
   }

   /**
    * The <code>main()</code> method is used to test the methods defined
    * in the <code>DumbConsole</code> class. 
    */
   public static void main(String[] argv) {
      PrintStream o = System.out;
      PrintStream e = System.err;
      do {
         byte rating = getByte("Enter 1 for lowest, 10 for highest: ", o);
         char grade = getChar("What grade do you want? ", o);
         short year = getShort("Enter a year: ", e);
         int yymmdd = getInt("Enter a date (YYMMDD): ", o);
         long yyyymmdd = getLong("Enter a date (YYYYMMDD): ", e);
         float price = getFloat("Enter a price (99.99): ", o);
         double worldDebt = getDouble("Enter world debt (99.99): ", o);
         String address = getString("What's your address? ", o);
         issuePrompt("Enter your name: ", o);
         String firstName = getWord();
         String restOfLine = getLine(false);
         o.println("Rating: " + rating);
         o.println("Grade: " + grade);
         o.println("Year: " + year);
         o.println("YYMMDD: " + yymmdd);
         o.println("YYYYMMDD: " + yyyymmdd);
         o.println("Price: " + price);
         o.println("World Debt: " + worldDebt);
         o.println("Address: " + address);
         o.println("First Name: " + firstName);
         o.println("Rest Of Line: " + restOfLine);
      } while (ynPrompt("Do this again", e));
   }

   /* 
    * here is the private stuff...
    */

	// don't allow DumbConsole objects to be instantiated
	private DumbConsole() {}

   private final static int BUFSIZ = 256;

   private static String readString(String delim, 
                                    boolean noLeadingWhiteSpace) {  
      StringBuffer outputBuff = new StringBuffer("");
      char ch = '?';
      while (true) {    
         try {  ch = (char)System.in.read();  }
         catch(java.io.IOException e) {  break; }
         if (noLeadingWhiteSpace && 
             (ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r'))
            continue;
         if (!Character.isDefined(ch) || delim.indexOf(ch) >= 0)
            break;
         outputBuff.append(ch);
         noLeadingWhiteSpace = false;
      }
      /*
       * <defect>
       * We do not always want to trim the input.
       * </defect>
       */
      return new String(outputBuff).trim();
   }

   /*
    * <defect>
    * If user enters a string like "yellow", then that
    * is considered a "yes" response.  If the user enters
    * the String "nerd", then that is considered "no".
    * </defect>
    */
   private static boolean readYN(String p, PrintStream o) {
      char ch = '?';
      do {
         issuePrompt(p, o);
         try {  ch = (char)System.in.read();  }
         catch (java.io.IOException e) { /*ignore*/ }
         eatLine();
         if (ch == 'y' || ch == 'Y') return true;
         if (ch == 'n' || ch == 'N') return false;
         System.out.println("*** invalid response ***");
      } while (true);
   }

   private static void eatLine() {
      char ch = '?';
      do {
         try {  ch = (char)System.in.read();  }
         catch (java.io.IOException e) { /*ignore*/ }
         if (ch == '\n') break;
      } while (true);
   }
}

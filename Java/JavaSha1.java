import java.math.BigInteger;
import java.security.MessageDigest;

/**

adapted from code by Damian Steer.
hash the mailto: part as well as the text of the email address
lowercases all the email address

*/

public class JavaSha1{

  public static void main (String args[]){

	String tohash;

	if(args.length>0){
		tohash=args[0];
	}else{
		tohash="mailto:someone@example.com";

	}

    System.out.println(sha1Hash(tohash));

  }


  public static String sha1Hash(String string)
  {

    string=string.toLowerCase();

    try
      {
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(string.getBytes());
        byte[] digest = md.digest();  
        BigInteger integer = new BigInteger(1, digest);
        return integer.toString(16);
      }
    catch (Exception e)
      {
        return null;
      }
  }

}

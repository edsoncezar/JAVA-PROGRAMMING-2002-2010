import java.io.*;

public class SecurityManagerTest {
    public static void main(String[] args) throws Exception {

        BufferedReader buffy = new BufferedReader(
				   new InputStreamReader(System.in));

        try {
            System.setSecurityManager(
			new PasswordSecurityManager("Booga Booga", buffy));
        } catch (SecurityException se) {
            System.err.println("SecurityManager already set!");
        }

        BufferedReader in = new BufferedReader(new FileReader("inputtext.txt"));
        PrintWriter out = new PrintWriter(new FileWriter("outputtext.txt"));
        String inputString;
        while ((inputString = in.readLine()) != null)
            out.println(inputString);
        in.close();
        out.close();
    }
}

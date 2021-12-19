import java.io.*;

public class SecurityManagerTest {
    public static void main(String[] args) throws Exception {
        try {
            System.setSecurityManager(new PasswordSecurityManager("Booga Booga"));
        } catch (SecurityException se) {
            System.err.println("SecurityManager already set!");
        }

        DataInputStream in = new DataInputStream(new FileInputStream("inputtext.txt"));
        DataOutputStream out = new DataOutputStream(new FileOutputStream("outputtext.txt"));
        String inputString;
        while ((inputString = in.readLine()) != null) {
            out.writeBytes(inputString);
            out.writeByte('\n');
        }
        in.close();
        out.close();
    }
}

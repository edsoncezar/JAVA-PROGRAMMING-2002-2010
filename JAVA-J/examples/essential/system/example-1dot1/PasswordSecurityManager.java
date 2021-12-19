import java.io.*;

public class PasswordSecurityManager extends SecurityManager {

    private String password;
    private BufferedReader buffy;

    public PasswordSecurityManager(String p, BufferedReader b) {
        super();
        this.password = p;
	this.buffy = b;
    }

    private boolean accessOK() {
        int c;
        String response;

        System.out.println("What's the secret password?");
        try {
            response = buffy.readLine();
            if (response.equals(password))
                return true;
            else
                return false;
        } catch (IOException e) {
            return false;
        }
    }
    public void checkRead(FileDescriptor filedescriptor) {
        if (!accessOK())
            throw new SecurityException("Not a Chance!");
    }
    public void checkRead(String filename) {
        if (!accessOK())
            throw new SecurityException("No Way!");
    }
    public void checkRead(String filename, Object executionContext) {
        if (!accessOK())
            throw new SecurityException("Forget It!");
    }
    public void checkWrite(FileDescriptor filedescriptor) {
        if (!accessOK())
            throw new SecurityException("Not!");
    }
    public void checkWrite(String filename) {
        if (!accessOK())
            throw new SecurityException("Not Even!");
    }
    public void checkPropertyAccess(String s) { }
    public void checkPropertiesAccess() { }
}

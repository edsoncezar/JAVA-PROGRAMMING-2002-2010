import java.io.*;

public class PasswordSecurityManager extends SecurityManager {

    private String password;

    public PasswordSecurityManager(String password) {
        super();
        this.password = password;
    }

    private boolean accessOK() {
        int c;
        DataInputStream in = new DataInputStream(System.in);
        String response;

        System.out.println("What's the secret password?");
        try {
            response = in.readLine();
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
}

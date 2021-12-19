import java.io.*;
import java.net.*;


public class InstructorNotifier2 {

    public static void sendEmail (String addressee,
                                      String sender,
                                      String messageSubject,
                                      String messageBody) throws IOException {
            Socket mrSock;
            DataInputStream dis;
            PrintStream ps;

            mrSock = new Socket ("localhost", 25);
            dis = new DataInputStream (mrSock.getInputStream());
            ps = new PrintStream (mrSock.getOutputStream());

            ps.println ("mail from: " + sender);
            System.out.println (dis.readLine());
            ps.println ("rcpt to: " + addressee);
            System.out.println (dis.readLine());
            ps.println ("data");
            System.out.println (dis.readLine());
            ps.println ("Subject: " + messageSubject);
            System.out.println (dis.readLine());
            ps.println (messageBody);
            ps.println (".");
            System.out.println (dis.readLine());
            ps.flush();
            mrSock.close();
        }


        /**Sends the professor an email to grade.
         */

    public void notifyEmail (String username,
                             String comments,
                             String address) {
        try{
            sendEmail(address, username += "@grinnell.edu", "lab", comments);
    }
        catch (IOException e) {
        }
    }
    

}// end class

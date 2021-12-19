/*
 * 1.1 version.
 */

import java.io.*;
import java.net.*;
import java.util.*;

class TalkServerThread extends Thread {
    public Socket socket;
    public BufferedReader is;
    public BufferedWriter os;
    TalkServer server;
    boolean DEBUG = false;

    public String toString() {
        return "TalkServerThread: socket = " + socket
               + "; is = " + is
               + "; os = " + os;
    }

    TalkServerThread(Socket socket, TalkServer server) throws IOException {
        super("TalkServer");

        is = new BufferedReader(
		 new InputStreamReader(socket.getInputStream()));
        os = new BufferedWriter(
		 new OutputStreamWriter(socket.getOutputStream()));

        if (is == null) {
            System.err.println("TalkServerThread: Input stream seemed "
                               + "to be created successfully, but it's null.");
            throw new IOException();
        }

        if (os == null) {
            System.err.println("TalkServerThread: Output stream seemed "
                               + "to be created successfully, but it's null.");
            throw new IOException();
        }

        this.socket = socket;
        this.server = server;
    }

    public void run() {
        while (socket != null) {
            try {
                //Read data.
                String str = is.readLine();

                //Pass it on.
                if (str != null) {
                    server.forwardString(str, this);
                }
            } catch (EOFException e) { //No more data on this socket...
                server.forwardString("SERVER SAYS other applet disconnected",
                                     this);
                cleanup();
                return;
            } catch (NullPointerException e) { //Socket doesn't exist...
                server.forwardString("SERVER SAYS no socket to other applet",
                                     this);
                cleanup();
                return;
            } catch (IOException e) { //Read problem..
                server.forwardString("SERVER SAYS socket trouble with other applet",
                                     this);
                cleanup();
                return;
            } catch (Exception e) { //Unknown exception. Complain and quit.
                System.err.println("Exception on is.readLine():");
                e.printStackTrace();
                cleanup();
                return;
            }
        }
    }

    protected void finalize() {
        cleanup();
    }

    void cleanup() {
        try {
            if (is != null) {
                is.close();
                is = null;
            }
        } catch (Exception e) {} //Ignore errors.

        try {
            if (os != null) {
                os.close();
                os = null;
            }
        } catch (Exception e) {} //Ignore errors.

        try {
            if (socket != null) {
                socket.close();
                socket = null;
            }
        } catch (Exception e) {} //Ignore errors.
    }

}

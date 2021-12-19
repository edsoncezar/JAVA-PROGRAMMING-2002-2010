import java.io.*;
import java.net.*;

class ChatHandler extends Thread {

  /* The Chat Handler class is called from the Chat Server:
   * one thread for each client coming in to chat.
   */

  private BufferedReader in;
  private PrintWriter out;
  private Socket toClient;
  private String name;

  ChatHandler(Socket s) {
    toClient = s;
  }

  public void run() {
    try {
      /* Create i-o streams through the socket we were
       * given when the thread was instantiated
       * and welcome the new client.
       */

      in = new BufferedReader(new InputStreamReader(
        toClient.getInputStream()));
      out = new PrintWriter(toClient.getOutputStream(), true);
      out.println("*** Welcome to the Chatter ***");
      out.println("Type BYE to end");
      out.print("What is your name? ");
      out.flush();
      String name = in.readLine();
      ChatServer.broadcast(name+" has joined the discussion.",
        "Chatter");

      // Read lines and send them off for broadcasting.
      while (true) {
        String s = in.readLine().trim();

        if (s.equals("BYE")) {
          ChatServer.broadcast(name+" has left the discussion.",
          "Chatter");
          break;
        }
        ChatServer.broadcast(s, name);
      }
      ChatServer.remove(toClient);
      toClient.close();
    }
    catch (Exception e) {
    System.out.println("Chatter error: "+e);
  }
  }

}

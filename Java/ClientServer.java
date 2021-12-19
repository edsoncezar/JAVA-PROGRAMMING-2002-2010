//
// ClientServer.java:
//      A simple Client <-> Server chat program.
//
//          (c) 2001-2 Edgar Troudt
//                     Department of Computer Science
//                     Queens College / CUNY
//

import java.io.*;
import java.net.*;

// ===============================================================
class Client {
    private static int    port;
    private static String address;

    // -----------------------------------------------------------
    public Client() {
        try {
            BufferedReader br = new BufferedReader(
                                new InputStreamReader (
                                                    System.in ) );

            Socket connection = new Socket( address, port );
            DataOutputStream dos = new DataOutputStream (
                                   connection.getOutputStream() );
            DataInputStream  dis = new DataInputStream (
                                    connection.getInputStream() );

            System.out.print( "Enter a string to be sent: " );
            String input = br.readLine();

            dos.writeUTF( input );
            System.out.println( "String sent." );

            input = dis.readUTF();
            System.out.println( "The server responds: " + input);
        } // end TRY
        catch ( Exception exc ) {
            System.out.println
                    ( "A data transmission error has occurred." );
        } // end CATCH
    } // end CONSTRUCTOR

    // -----------------------------------------------------------
    public static void main ( String args[] ) {
        if ( args.length != 2 ) {
            System.out.println
                  ( "You must enter a machine name and a port." );
            System.exit(1);
        } // end IF

        port = Integer.parseInt( args[1] );
        address = args[0];
        new Client();
    } // end METHOD main
} // end CLASS Client


// ===============================================================
class Server {
    private static int    port;
    private static String address;

    // -----------------------------------------------------------
    public Server () {
        try {
            ServerSocket server = new ServerSocket( port, 2 );

            System.out.println
                ("Server established.  Start client as follows:");

            InetAddress myAddress = InetAddress.getLocalHost();
            System.out.println( "\tjava Client " +
                                myAddress.getHostAddress() +
                                " " + port );

            Socket connection = server.accept();
            DataOutputStream dos = new DataOutputStream
                                 ( connection.getOutputStream() );
            DataInputStream  dis = new DataInputStream
                                 ( connection.getInputStream() );


            String result = dis.readUTF();
            System.out.println( "The client sends: " + result );

            System.out.print( "Enter a response: " );
            BufferedReader br = new BufferedReader(
                                new InputStreamReader (
                                                    System.in ) );
            String input = br.readLine();

            dos.writeUTF( input );
            System.out.println( "String sent." );

        } // end TRY
        catch ( Exception exc ) {
            System.out.println
                    ( "A data transmission error has occurred." );
        } // end CATCH
    } // end CONSTRUCTOR

    // -----------------------------------------------------------
    public static void main ( String args[] ) {
        port = ((int) ( 5000.0 * Math.random() )) + 5000;
        new Server();
    } // end METHOD main
} // end CLASS Server
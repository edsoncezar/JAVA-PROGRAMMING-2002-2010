import java.io.*;
import java.util.*;
import java.net.*;

public class Debug extends OutputStream implements Runnable {

    // Instance variables
    int		 port;		  // The port we listen on
    ServerSocket server;	  // ServerSocket listening on the port
    boolean      active;	  // Are we actively awaiting connections?
    Socket       client;	  // Our debugging client
    OutputStream clientStream;    // The OutputStream to the client
    Thread       listener;	  // The thread used to listen for connections
    PrintStream  old;		  // The original System.out

    // Create a Debug OutputStream listening on the specified port

    public Debug(int port) {
	this.port = port;

	try {
	    server = new ServerSocket(port);
	} catch (IOException e) {
	    System.out.println("could not create server");
	} 
    }

    // Returns true if the debug stream is currently active

    public boolean isActive() {
	return active;
    } 

    // Activate the debug stream by redirecting System.out

    public void startServer() {
	if (!active) {
	    old = System.out;

	    System.setOut(new PrintStream(this));

	    active = true;
	    listener = new Thread(this);

	    listener.start();
	    System.out.println("debug server started");
	} 
    } 

    // Stop the debug stream by directing System.out back to the original stream

    public void stopServer() {
	active = false;

	System.setOut(old);
	System.out.println("debug server stopping");

	if (client != null) {
	    try {
		client.close();
	    } catch (IOException e) {}
	} 
    } 

    // Debug implements Runnable; run() listens for socket connections and if
    // no-one is already connected directs debugging output to the client

    public void run() {
	Socket localSocket = null;

	try {
	    while (active) {
		localSocket = server.accept();

		if (client == null) {
		    client = localSocket;
		    clientStream = client.getOutputStream();

		    new PrintStream(clientStream).println("Welcome to the Debug Server");
		} else {
		    PrintWriter second = 
			new PrintWriter(localSocket.getOutputStream());

		    second.print("already connected");
		    localSocket.close();
		} 
	    } 

	    System.out.println("debug server stopped");
	} catch (IOException e) {
	    System.out.println("debug server crashed");
	    System.out.println(e.getMessage());

	    active = false;
	} 
	finally {
	    if (server != null) {
		try {
		    server.close();
		} catch (IOException e) {}
	    } 
	} 
    } 

    // Disconnect a client if communication with it goes wrong - called from
    // write() below in the event of an IOException

    protected void clearClient() {
	if (client != null) {
	    try {
		client.close();
	    } catch (IOException ioe) {}
	} 

	client = null;
	clientStream = null;
    } 

    // Override OutputStream.write() to direct output to both any remote
    // debugging client and the old System.out

    public void write(byte[] b) throws IOException {
	if (old != null) {
	    old.write(b);
	} 

	if (clientStream != null) {
	    try {
		clientStream.write(b);
	    } catch (IOException e) {
		clearClient();
	    } 
	} 
    } 

    public void write(byte[] b, int off, int len) throws IOException {
	if (old != null) {
	    old.write(b, off, len);
	} 

	if (clientStream != null) {
	    try {
		clientStream.write(b, off, len);
	    } catch (IOException e) {
		clearClient();
	    } 
	} 
    } 

    public void write(int b) throws IOException {
	if (old != null) {
	    old.write(b);
	} 

	if (clientStream != null) {
	    try {
		clientStream.write(b);
	    } catch (IOException e) {
		clearClient();
	    } 
	} 
    } 

}


/*
 * 1.1 version.
 */

import java.net.*;
import java.io.*;

class TalkServer {
    TalkServerThread[] tstList = new TalkServerThread[2];
    boolean DEBUG = false;

    public static void main(String[] args) {
        new TalkServer().start();
    }

    public void start() {
        ServerSocket serverRSocket = null;
        int numConnected = 0;

        try {
            serverRSocket = new ServerSocket(0);
            System.out.println("TalkServer listening on rendezvous port: "
                               + serverRSocket.getLocalPort());
        } catch (IOException e) {
            System.err.println("Server could not create server socket for rendezvous.");
            return;
        }

        while (true) {

            //Connect to two clients.
            while (numConnected < 2) {
                TalkServerThread tst;
                tst = connectToClient(serverRSocket);
                if (tst != null) {
                    numConnected++;
                    if (tstList[0] == null) {
                        tstList[0] = tst;
                    } else {
                        tstList[1] = tst;
                    }
                }
            } //end while (numConnected < 2) loop

            if (DEBUG) {
                try {
                    System.out.println("tst #0 = " + tstList[0]);
                } catch (Exception e) {}
                try {
                    System.out.println("tst #1 = " + tstList[1]);
                } catch (Exception e) {}
            }

            //If they're really OK, tell them to start writing.
            if (everythingIsOK(0) & everythingIsOK(1)) {
                for (int i = 0; i < 2; i++) {
                    writeToStream("START WRITING!\n----------------------"
                                  + "-------------", tstList[i].os);
                }
            } else {
                System.err.println("2 server threads created, but "
                                   + "not everything is OK");
            }

            while (numConnected == 2) {
                if (!everythingIsOK(0)) {
                    if (DEBUG) {
                        System.out.println("Applet #0 is hosed; disconnecting.");
                    }
                    numConnected--;
                    cleanup(tstList[0]);
                    tstList[0] = null;
                }
                if (!everythingIsOK(1)) {
                    if (DEBUG) {
                        System.out.println("Applet #1 is hosed; disconnecting.");
                    }
                    numConnected--;
                    cleanup(tstList[1]);
                    tstList[1] = null;
                }
                       try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                } 
            } //end while(numConnected==2) loop

            if (DEBUG) {
                try {
                    System.out.println("Number of connections = " + numConnected);
                    System.out.println("tst #0 = " + tstList[0]);
                    System.out.println("tst #1 = " + tstList[1]);
                } catch (Exception e) {}
            }

        } //end while (true) loop
    }

    protected TalkServerThread connectToClient(ServerSocket serverRSocket) {
        Socket rendezvousSocket = null;
        TalkServerThread tst = null;

        //Listen for client connection on the rendezvous socket.
        try {
            rendezvousSocket = serverRSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            e.printStackTrace();
            return null;
        }

        //Create a thread to handle this connection.
        try {
            tst = new TalkServerThread(rendezvousSocket, this);
            tst.start();
        } catch (Exception e) {
             System.err.println("Couldn't create TalkServerThread:");
            e.printStackTrace();
            return null;
        }

        writeToStream("Successful connection. "
                      + "Please wait for second applet to connect...",
                      tst.os);
        return tst;
    }

    boolean everythingIsOK(int tstNum) {
        TalkServerThread tst = tstList[tstNum];

        if (tst == null) {
            if (DEBUG) {
                System.out.println("TalkServerThread #" + tstNum
                                   + " is null");
            }
            return false;
        } else {
            if (tst.os == null) {
                if (DEBUG) {
                    System.out.println("TalkServerThread #" + tstNum
                                       + " output stream is null.");
                }
                return false;
            }
            if (tst.is == null) {
                if (DEBUG) {
                    System.out.println("TalkServerThread #" + tstNum
                                       + " input stream is null.");
                }
                return false;
            }
            if (tst.socket == null) {
                if (DEBUG) {
                    System.out.println("TalkServerThread #" + tstNum
                                       + " socket is null.");
                }
                return false;
            }
        }
        return true;
    }

    void cleanup(TalkServerThread tst) {
        if (tst != null) {
            try {
                if (tst.os != null) {
                    tst.os.close();
                    tst.os = null;
                }
            } catch (Exception e) {} //Ignore errors
            try {
                if (tst.is != null) {
                    tst.is.close();
                    tst.is = null;
                }
            } catch (Exception e) {} //Ignore errors
            try {
                if (tst.socket != null) {
                    tst.socket.close();
                    tst.socket = null;
                }
            } catch (Exception e) {} //Ignore errors
        }
    }

    public void forwardString(String string, TalkServerThread requestor) {
        BufferedWriter clientStream = null;

        if (tstList[0] == requestor) {
            if (tstList[1] != null) {
                clientStream = tstList[1].os;
            } else {
                if (DEBUG) {
                    System.out.println("Applet #0 has a string to forward, "
                                       + "but Applet #1 is gone...");
                }
                return;
            }
        } else {
            if (tstList[0] != null) {
                clientStream = tstList[0].os;
            } else {
                if (DEBUG) {
                    System.out.println("Applet #1 has a string to forward, "
                                       + "but Applet #0 is gone...");
                }
                return;
            }
        }

        if (clientStream != null) {
            writeToStream(string, clientStream);   
        } else if (DEBUG) {
            System.out.println("Can't forward string -- no output stream.");
        }
    }

    public void writeToStream(String string, BufferedWriter stream) {
        if (DEBUG) {
            System.out.println("TalkServer about to forward data: "
                               + string);
        }

        try { 
            stream.write(string);
	    stream.newLine();
            stream.flush();
            if (DEBUG) {
                System.out.println("TalkServer forwarded string.");
            }
        } catch (IOException e) {
            System.err.println("TalkServer failed to forward string:");
            e.printStackTrace();
            return;
        } catch (NullPointerException e) {
            System.err.println("TalkServer can't forward string "
                               + "since output stream is null.");
            return;
        }
    }

}

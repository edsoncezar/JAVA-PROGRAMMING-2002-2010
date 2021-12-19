// Filename ThreadDemo.java.
// Providing a demonstration of the initial Interactive Menu,
// showing its use with manifest values.
//
// Written for JFL book Chapter 6 see text.
// Fintan Culwin, v0.1, January 1997

import Counters.RoomMonitor;

public class ThreadDemo { 


public static void main( String argv[]) { 

RoomMonitor  aMonitor  = new RoomMonitor();
RoomFiller   aFiller   = new RoomFiller(  aMonitor);
RoomEmptyer  anEmptyer = new RoomEmptyer( aMonitor);
RoomReporter aReporter = new RoomReporter(  aMonitor);

    aFiller.start();
    anEmptyer.start();
    aReporter.start();

} // End main. 
  
} // End  ThreadDemo.

class CubbyHole {
  private int contents;
  private int available = 0;


//  public synchronized native int get(); 
  public native int get();
  public synchronized native void put(int value); 

  static {
   	try {
	   System.loadLibrary("threadsync");
	} catch (UnsatisfiedLinkError e) {
	   System.err.println("Can't find library for thread sync");
	   System.exit(-1);
	}
  }
} 

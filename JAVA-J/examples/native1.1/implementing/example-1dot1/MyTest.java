class MyTest {
	/*public native int getline(StringBuffer s, int len);*/
	// members
	int myInt = 1;
	double myDbl = 1.1;
	float myFlt;
	String myString;

	/* constructor */
	public MyTest(){
		System.out.println("In constructor function");
	}

	public native int getLine(String s, int len);
	public native int getChar(StringBuffer sb);
//	public native int[] getIntValue(int[] arr);
	public native int getIntValue(int[] arr, double[] darr);

  // string method(s)
  public native int createString(String s, int len);

	// create an array
	public native int[] createArray(int[] arr);

	// get information
	public native int getInfo();
	// test error handling
	public native int handleError2() throws IllegalArgumentException;
	public native int handleError(int ivalue);

	// java methods
	public void displayHello() {
	   System.out.println("Hello World! ");
	}

	public int doCalc(int i, int n) {
		int j;
		System.out.println("Start of doCalc; i= " + i);
		if (i==0) {
		   i = n;
		}
		for (j=1;j<5;j++) {
		   i = i + j;
		}
		System.out.println("Ready to exit doCalc; i= " + i);
		return(i);
	}

	static {
		System.loadLibrary("mytest");
	}
}

class Main {
	static MyTest mytest;
	public static void main(String[] args) {

		int length = 15;
		int ret;
		int i;
		String teststring = "This is a test";
		int len = 14;

		int[] arrayOfInts = new int[10];
		int[] newArray;
		double[] arrayOfDbls = new double[5];

		mytest = new MyTest();

		//test getLine routine
		StringBuffer sb = new StringBuffer("Testing getLine routine.");
		String s = new String();
		s = sb.toString();
		ret = mytest.getLine(s, sb.length());
		if (ret == -1) {
		   System.out.println("Error in getLine routine");
		}	
		ret = mytest.getLine(teststring, teststring.length());
		if (ret == -1) {
		   System.out.println("Error in getLine routine");
		}	
		System.out.println ("Return value from getLine: " + ret);

		// test array routine 
		for (i = 0; i< arrayOfInts.length;i++) {
		   arrayOfInts[i] = i;
		}
		for (i=0; i<arrayOfDbls.length; i++) {
		   arrayOfDbls[i] = 3.33 * i;
		}
		ret = 0;
		ret = mytest.getIntValue(arrayOfInts, arrayOfDbls);
		System.out.println("Return value ret: " + ret);

// create new array to size of arrayOfInts

		newArray = mytest.createArray(arrayOfInts);
		
	        for (i = 0; i < newArray.length; i++) {
		   System.out.println("new array: " + newArray[i]);
		}

		// call native method to get info
		i = mytest.getInfo();
		System.out.println("Value of i: " + i);

		ret = mytest.myInt;
		System.out.println("Value of myInt: " + ret);
		System.out.println("Value of myDbl: " + mytest.myDbl);
		System.out.println("Value of myFlt: " + mytest.myFlt);

		ret = mytest.createString(teststring, len);

		System.out.println("Check for exceptions");
		ret = 0;
		for (i = 98; i<102; i++) {
		   try {
			ret = mytest.handleError(i);
		   } catch (IllegalArgumentException e) {
			System.out.println("illegal arg. exception occurred");
			System.out.println("Message: " + e.getMessage());
		   } catch (Throwable e) {
			System.out.println("Caught an error");
			System.out.println("Message: " + e.getMessage());
		   }
		}
		ret = 0;
		try {
		   	ret = mytest.handleError2();
			System.out.println("Return value from error2: "
					+ ret);
		} catch (Throwable e) {
			System.out.println("Caught error in second routine");
			System.out.println("Return value: " + ret);
		}
	}
}

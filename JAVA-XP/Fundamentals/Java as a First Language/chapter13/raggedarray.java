// Filename RaggedArray.java.
// Illustrates the construction and use of a 
// two dimensional ragged array.
//
// Written for JFL Book Chapter 13.
// Fintan Culwin, V 0.1, Jan 1997.

class RaggedArray {  

private int salaries[][];

   public RaggedArray() { 
   
      salaries = new int[ 2] [];
      
      salaries[ 0] = new int[ 4];
      salaries[ 0] [ 0] = 12345;  salaries[ 0] [ 1] = 23456;
      salaries[ 0] [ 2] = 34567;  salaries[ 0] [ 3] = 45678;
      
      salaries[ 1] = new int[ 2];
      salaries[ 1] [ 0] = 56789;  salaries[ 1] [ 1] = 67890;
   } // End RaggedArray default constructor.


   public String toString() {  
   
   StringBuffer    theBuffer = new StringBuffer();
   int thisIndex, anotherIndex;
   
      for ( thisIndex = 0; 
            thisIndex < salaries.length;
            thisIndex++) { 
         for( anotherIndex = 0;
              anotherIndex < salaries[ thisIndex].length;
              anotherIndex++) { 
            theBuffer.append( Integer.toString( 
                                 salaries[ thisIndex] [ anotherIndex]) + 
                                 "    "); 
         } // End for anotherIndex.
         theBuffer.append("\n");
      } // End for thisIndex.
      return theBuffer.toString();
   } // End toString.   
   
} // End class RaggedArray.

// Filename StackDemo.java.
// Demonstration harness for the stack data structure, 
// pushing a sequence of digits onto a stack and 
// subsequently popping them and converting into 
// an integer value.
//
// Written for JFL book, chapter 14.
// Fintan Culwin, v0.1, Jan 1997.

import Generics.Stack;
import Generics.GenericException;

import java.lang.Character;
import java.io.*;

class StackDemo {

  public static void main(String args[]) {
    
  Character demoChar   = new Character( 'a');
  char      aCharacter = '0';  
  Stack     demoStack = new Stack( demoChar.getClass());
  int       multiplier;
  int       value;
  
  
     System.out.println( "\tStack Demonstration Program\n\n");
     System.out.print( "Please enter an integer value :");
     System.out.flush();
          
     while ( Character.isDigit( aCharacter)) { 
        try { 
          aCharacter = (char) System.in.read();
          if (  Character.isDigit( aCharacter)) {
             demoChar = new Character( aCharacter); 
             demoStack.push( demoChar);
          } // end if.
        } catch (IOException exception) { 
          aCharacter = ' ';
        } // End try/ catch.  
     } // End while.

     if ( demoStack.isEmpty()) { 
        System.out.println( "That is not an integer!");
     } else {      
        System.out.println( "Thank you, the stack contains");
        System.out.println( demoStack);
      
        System.out.println( "\nConverting to integer and outputing"); 
        multiplier = 1;
        value      = 0;
      
        while ( !demoStack.isEmpty()){
           demoChar = (Character) demoStack.pop();
           value += (demoChar.charValue() - '0') * multiplier;
           multiplier *= 10;
           System.out.println( "Popped " + demoChar +
                            " value so far " + value + ".");   
        } // End while.
     } // End if.   
  } // End main.
      
} // End Queue Demo.  


// Filename Menus/BasicMenu.java.
// Providing an initial Interactive Menu.
//
// Written for JFL book Chapter 6 see text.
// Fintan Culwin, v0.1, January 1997 .
//
// This version jan 2000. 

package menus;

import java.io.*;

public class BasicMenu extends Object { 

private String  theTitle;
private String  theOptions[];
private String  thePrompt = "Please enter your choice :";
private char    minimumOption;
private char    maximumOption;

private java.io.BufferedReader keyboard 
                       = new java.io.BufferedReader( 
                                  new InputStreamReader(System.in));


   public BasicMenu( String title,
                     String options[],
                     String prompt) { 
                                          
   int thisString;                     

     if ( title.length() > 0 ) { 
        theTitle = new String( title);
     } // End if.
     
     theOptions = new String[ options.length];
     for ( thisString = 0; 
           thisString < options.length;
           thisString++                  ) { 
        theOptions[ thisString] = new String( options[ thisString]);  
     } // End for.      
                                                    
     if ( prompt.length() > 0) { 
        thePrompt = new String( prompt);
     } // End if.                                               
  } // End BasicMenu   
  

  public char offerMenuAsChar() { 
  
  char validatedResponse;
  
     this.showMenu();
     validatedResponse = getValidatedMenuChoice();
     return validatedResponse;
  } // End offerMenu
  
  
  public int offerMenuAsInt() { 
  
  char responseAsChar  = offerMenuAsChar();

       return (responseAsChar - 'A') + 1;
  } // End offerMenu


  private void showMenu( ){
   
  int  thisString;
  char thisOption = 'A';                     

     setMinimumOption( thisOption);

     showTitle();
     
     for ( thisString = 0; 
           thisString < theOptions.length;
           thisString++  ) { 
         showMenuLine( thisOption,  thisString); 
         thisOption++;
     } // End for  
     setMaximumOption( --thisOption);     
  } // End showMenu  
  

  protected void showTitle(){  
     if ( theTitle != null ) {
           System.out.println( "\t" + theTitle + "\n");
     }   
  } // end fun showMenuOption.


  protected void showMenuLine( char menulabel, int menuText){  
     System.out.println( menulabel + ".   " + theOptions[ menuText]);   
  } // end fun showMenuLine.


  protected void setMinimumOption( char setTo ){ 
     minimumOption = setTo;
  } // end fun setMinimumOption.

  
  protected void setMaximumOption( char setTo ){ 
     maximumOption = setTo;
  } // end fun setMinimumOption.
    

  protected char getValidatedMenuChoice(){

  String  fromKeyboard      = new String( "");
  char    possibleResponse  = ' ';
  boolean isNotGoodResponse = true;  
  
     System.out.print( "\n" + thePrompt + " "); 
     System.out.flush();
     
     while ( isNotGoodResponse ) { 
        try { 
           // possibleResponse = keyboard.readChar();
           fromKeyboard =  new String( keyboard.readLine());
           if ( fromKeyboard.length() > 0 ) { 
              possibleResponse  = fromKeyboard.charAt( 0);
           } else { 
              possibleResponse  = ' ';
           } 
        } catch( java.io.IOException exception) {
           // do something
        } // End try/catch.

        possibleResponse = Character.toUpperCase( possibleResponse);
           
        isNotGoodResponse = ( (possibleResponse < minimumOption) ||
                              (possibleResponse > maximumOption) );

        if ( isNotGoodResponse ) { 
           System.out.println( "\n Please enter a response between " + 
                               minimumOption + " and " +  
                               maximumOption + ".");
           System.out.print( "\n" + thePrompt + " "); 
           System.out.flush();                                 
        } // End if                 
     } // End while.
     return possibleResponse;    
  } // End fun getValidatedMenuChoice .
  
} // End BasicMenu

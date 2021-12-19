// Filename Menus/AdaptingMenu.java.
// Providing an extended menu which can change 
// the options which it presents.
//
// Written for JFL book Chapter 7 see text.
// Fintan Culwin, v0.1, January 1997.

package Menus;

public class AdaptingMenu extends Menus.BasicMenu { 

boolean inUse[];
char    optionKey[];


   public AdaptingMenu( String title,
                        String options[],
                        String prompt) { 
                        

      super( title, options, prompt); 

      inUse      = new boolean[ options.length]; 
      optionKey  = new    char[ options.length]; 
   } // End AdaptingMenu constructor.
   

  public void enableMenuOption( int toEnable){ 
     inUse[ toEnable] = true;
  } // end fun enableMenuOption.

  public void disableMenuOption( int toDisable){ 
     inUse[ toDisable] = false;
  } // end fun enableMenuOption.



   
  public int offerMenuAsInt() { 
  
  char    responseAsChar;
  int     thisOne       = 0;
  boolean isNotFound = true;

       showMenu();
       responseAsChar = getValidatedMenuChoice();
       
       while ( isNotFound ) { 
          if ( optionKey[ thisOne] == responseAsChar ) { 
              isNotFound     = false;
          } else {     
              thisOne++;
          } // End if.
       } // End while.
       return thisOne;
  } // End offerMenuAsInt
   


  private void showMenu( ){
   
  int  thisPossibility;
  char thisOption = 'A';                     

     setMinimumOption( thisOption);

     showTitle();
     
     System.out.println();
     for ( thisPossibility = 0; 
           thisPossibility < optionKey.length;
           thisPossibility++  ) {
         if ( inUse[ thisPossibility]) {  
            showMenuLine( thisOption, thisPossibility);
            optionKey[ thisPossibility] = thisOption;
            thisOption++; 
         } else { 
            optionKey[ thisPossibility] = ' ';  
         } // End if.
         
     } // End for 
     setMaximumOption( --thisOption); 
  } // End showMenu      
  


} // End AdaptingMenu

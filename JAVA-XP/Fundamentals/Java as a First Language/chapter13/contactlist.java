// Filename ContactListDemonstration.java.
// Introduces the Vector class by storing 
// a list of people's details.
//
// Contains three versions of the showOption action.
//   a primitive version using Vector toString (uncommneted).
//   a version using Vector facilities (commented out).
//   a version using Enumeration facilities (commented out).
//
// Written for JFL book Chapter 13.
// Fintan Culwin, v 0.1, Jan 1997.

import Persons.*;
import Menus.BasicMenu;
import ValidatedInput;

import java.util.Vector;
import java.util.Enumeration;
import java.io.*;

class ContactList { 

private static final char ADD_OPTION    = 'A';
private static final char REMOVE_OPTION = 'B';
private static final char SHOW_OPTION   = 'C';
private static final char EXIT_OPTION   = 'D';

private static final char PART_OPTION   = 'A';
private static final char FULL_OPTION   = 'B';

private static DataInputStream theKeyboard 
                   = new DataInputStream( System.in);    

   public static void main(String args[]) {
   
   

   String mainOptions[] =  { "Add to contact list.",
                             "Remove from contact list",
                             "Show contact list.",
                             "Exit"};
                             
   String subOptions[] =   { "Add name and e-mail",
                             "Add name, e-mail and phone number"};
                             
   BasicMenu mainMenu = new BasicMenu( "Contact list main menu",
                                       mainOptions,
                                       "");
                                       
   BasicMenu subMenu  = new BasicMenu( "Add contact sub menu",
                                       subOptions,
                                       "");                                       
   Vector theList = new Vector();
   char mainMenuChoice = ' ';    
   
      while ( mainMenuChoice != EXIT_OPTION ) { 
         System.out.println( "\n\n");
         mainMenuChoice = mainMenu.offerMenuAsChar();
         System.out.println( "\n");
      
         switch ( mainMenuChoice) { 
            
            case  ADD_OPTION:   
               addOption( theList, subMenu);
               break;
            // End case ADD_OPTION.
         
            case  REMOVE_OPTION:
               removeOption( theList);
               break;
            // End case REMOVE_OPTION.
           
            case  SHOW_OPTION:
               showOption( theList);
               break;
            // End case SHOW_OPTION.         
         
            case  EXIT_OPTION:
               System.out.println( "Have a nice day");
               break;
            // End case SHOW_OPTION.                          
         } // End switch.      
      } // End while.
   } // End main.
   

   static private void addOption( Vector    aList,
                                  BasicMenu theMenu) { 
   
   char        subMenuChoice;
   EmailPerson aPerson; 
     
      subMenuChoice = theMenu.offerMenuAsChar();
      if ( subMenuChoice == PART_OPTION) {            
         aPerson = new NamedPerson();
      } else {              
         aPerson = new PhonePerson();
      } // End if. 
      aPerson.read();
      aList.addElement( aPerson);            
   } // end addOption.


   static private void removeOption( Vector aList) {
                       
   int     toRemove = 0;   
   
      if ( aList.size() == 0) { 
         System.out.println( "The list is empty!" + 
                " So it is not possible to remove a person.");
      } else { 
         toRemove = (int) ValidatedInput.readLong( 
                             "Enter location of information to remove " + 
                             "(Or 0 to abandon) : ",
                             0, aList.size());
         if ( toRemove != 0) { 
            aList.removeElementAt( toRemove -1);
         } // End if.
      } // End if.
   } // End removeOption.            
            
              
   static private void showOption( Vector aList) {                        
      System.out.println( aList);   
   } // End showOption.
  
//   static private void showOption( Vector aList) { 
//      
//   int thisElement;
//   NamedPerson fromList;
//   
//       for( thisElement = 0; thisElement < aList.size(); thisElement++){
//          fromList = (NamedPerson) aList.elementAt( thisElement);
//          System.out.println( thisElement + "  " + fromList);       
//       } // End for.         
//   } // End showOption.
    
//   static private void showOption( Vector aList) {  
//   
//   int         thisElement = 0;
//   NamedPerson fromList;
//   Enumeration contacts    = aList.elements();
//   
//      while( contacts.hasMoreElements() ) { 
//         fromList = (NamedPerson) contacts.nextElement();      
//         thisElement++;
//         System.out.println( thisElement + "  " + fromList);       
//      } // End while;   
//   } // End showOption.    
      
} // End class ContactList.

// Filename RoomMonitorMenuDemonstration.java.
// Providing a demonstration of the Adapting Menu.
//
// Written for JFL book Chapter 7 see text.
// Fintan Culwin, v0.1, January 1997.

import Menus.AdaptingMenu;
import Counters.RoomMonitor;

public class RoomMonitorMenuDemonstration { 


private static final int ENTER_OPTION  = 0;
private static final int LEAVE_OPTION  = 1;
private static final int STATUS_OPTION = 2;
private static final int EXIT_OPTION   = 3;
private static final int MAX_OPTIONS   = 4;

private static final int ROOM_LIMIT    = 3;

public static void main( String argv[]) { 

   String roomMenuOptions[] =  { "Enter the room",
                                 "Leave the room",
                                 "Show room status",
                                 "Exit program"};
                             

   AdaptingMenu roomMenu = new AdaptingMenu( "Room Monitor menu",
                                             roomMenuOptions,
                                             "" );
   RoomMonitor roomMonitor = new RoomMonitor( ROOM_LIMIT);                                             
                                                

   int  usersChoice = ' ';  
        
      System.out.println( "\t Room menu demonstration client \n");
      
      while ( usersChoice != EXIT_OPTION ) { 
      
         prepareMenu( roomMenu, roomMonitor);
      
         usersChoice = roomMenu.offerMenuAsInt();
         
         switch ( usersChoice ){ 
            
            case ENTER_OPTION :
               roomMonitor.enterRoom();
               break;
               
            case LEAVE_OPTION :
               roomMonitor.exitRoom();
               break;
               
            case STATUS_OPTION :
               System.out.println( roomMonitor);
               break;                 
               
            case EXIT_OPTION :
               break;
               
         } // End switch.
       } // End while.   
         
      
      System.out.println( "\n\tHave a nice day! \n"); 
   } // End main.
   
   
   private static void prepareMenu( AdaptingMenu theMenu, 
                                    RoomMonitor  theMonitor) {
                                    
      theMenu.enableMenuOption( ENTER_OPTION);
      theMenu.enableMenuOption( LEAVE_OPTION);                              
      theMenu.enableMenuOption( STATUS_OPTION);
      theMenu.enableMenuOption( EXIT_OPTION);
      
      
      if ( theMonitor.numberCurrentlyInRoomIs() == 0) { 
          theMenu.disableMenuOption( LEAVE_OPTION);         
      } else { 
         theMenu.disableMenuOption( EXIT_OPTION);
         if ( theMonitor.numberCurrentlyInRoomIs() == ROOM_LIMIT) { 
             theMenu.disableMenuOption( ENTER_OPTION);
         } // End if.      
      } // End if.                                     
                                    
   } // End prepareMenu.                                   
} // RoomMonitorMenuDemonstration.
                         

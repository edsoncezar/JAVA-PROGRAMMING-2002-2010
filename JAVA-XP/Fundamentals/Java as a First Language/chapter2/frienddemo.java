// Filename FriendDemo.java.
// Second trivial Java object written for 
// the JFL book chapter 2 - see text.
//
// Fintan Culwin, V0.1, January 1997.

import Friend;

public class FriendDemo { 

   public static void main( String args[] ) { 

   Friend myFriend      = new Friend( "Arthur Smith", 32);
   Friend myOtherFriend = new Friend( "Ford Prefect", 45);   
      
      System.out.println( "\n\t\tThis is the Friend Demo Client\n");
          
      System.out.print( "Illustrating the friendsNameIs() ");
      System.out.println( "action of the myFriend instance.");
      System.out.print( "The name should be Arthur Smith ... ");    
      System.out.print(  myFriend.friendsNameIs());             
      System.out.println( ".");
     

      System.out.print( "\nIllustrating the friendsNameIs() ");
      System.out.println( "action of the myOtherFriend instance.");
      System.out.print( "The name should be Ford Prefect ... ");    
      System.out.print(  myOtherFriend.friendsNameIs());             
      System.out.println( ".");
   
      System.out.print( "\nIllustrating the friendsAgeIs() ");
      System.out.println( "action of the myFriend instance.");
      System.out.println( "Arthur Smith should be 32 years old ... ");    
      System.out.print(   myFriend.friendsNameIs() + 
                          " is "                   +
                          myFriend.friendsAgeIs());             
      System.out.println( " years old.");
               
      System.out.print( "\nIllustrating the friendsAgeIs() ");
      System.out.println( "action of the myOtherFriend instance.");
      System.out.println ( "Ford Prefect should be 45 years old ... ");    
      System.out.print(   myOtherFriend.friendsNameIs() + 
                          " is "                   +
                          myOtherFriend.friendsAgeIs());             
      System.out.println( " years old.");       

      System.out.print( "\nIllustrating the birthday() actions");
      System.out.println( "of both instances.");   
      myFriend.birthday(); 
      myOtherFriend.birthday();   
      System.out.print( "The ages should now be 33 and 235 ...");
      System.out.print( myFriend.friendsAgeIs() + 
                        "  "                    +
                        myOtherFriend.friendsAgeIs()); 
      System.out.println( "."); 
      
      System.out.println( "\nIllustrating the class wide numberOfFriendsIs()");
      System.out.println( "action, called via the myFriend instance.");  
      System.out.print( "The value should be 2 ... ");
      System.out.print(  myFriend.numberOfFriendsIs());
      System.out.println( "."); 

      System.out.println( "\nIllustrating the class wide numberOfFriendsIs()");
      System.out.println( "action, called via the Friend class.");  
      System.out.print( "The value should be 2 ... ");
      System.out.print(  Friend.numberOfFriendsIs());
      System.out.println( ".");                    

      System.out.println( "\nIllustrating the class wide greetingIs()");
      System.out.println( "action, called via the Friend class.");  
      System.out.print( "The  greeting is ... ");
      System.out.print(  Friend.greetingIs());
      System.out.println( ".");                                               
   } // End main.

} // End FriendDemo;

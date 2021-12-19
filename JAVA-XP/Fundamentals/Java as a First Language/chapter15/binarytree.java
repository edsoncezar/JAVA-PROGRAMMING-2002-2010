// Filename BinaryTree.java.
// Class to implement the OrderedStructure interface
// making use of the a single linked list. 
//
// Written for JFL book, chapter 14.
// Fintan Culwin, v0.1, Jan 1997.


package Generics;

import Generics.GenericStructure;
import Generics.GenericException;
import Generics.OrderedStructure;
import AlreadyPresentException;

import java.util.Vector;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.lang.ArrayIndexOutOfBoundsException;

public class BinaryTree extends     GenericStructure
                        implements  OrderedStructure{ 

private TreeNode theTree;
private int      numberOfElements = 0;


   public BinaryTree( Class theElementClass) { 
      super( theElementClass);     
   } // end LinkedList constructor.
   

   public void add( Keyable toAdd) {
      if ( theTree == null) {  
           theTree = new TreeNode( toAdd);
           numberOfElements++;
      } else { 
           addNode( theTree, toAdd.keyValueIs(), toAdd); 
      } // End if. 
   } // End add.


   private void addNode( TreeNode  addHere,
                         Orderable keyToAdd,
                         Keyable   toAdd) {
                         
     if ( keyToAdd.keyIsEqualTo( 
                    addHere.dataIs().keyValueIs())) { 
        throw new AlreadyPresentException();                
     } else if ( keyToAdd.keyIsLessThan( 
                    addHere.dataIs().keyValueIs())) { 
         if ( addHere.leftTreeIs() == null) { 
            addHere.setLeftTree( new TreeNode( toAdd));
            numberOfElements++;
         } else {                   
            addNode( addHere.leftTreeIs(), keyToAdd, toAdd);
         } // End if.                                       
      } else {
         if ( addHere.rightTreeIs() == null) { 
            addHere.setRightTree( new TreeNode( toAdd));
            numberOfElements++;
         } else {              
            addNode( addHere.rightTreeIs(),keyToAdd, toAdd);
         } // End if.
      } // End if.                                   
   } // End addNode.                    
   
      
   public Keyable obtain( Orderable keyToObtain) {    
     return obtainFromTree( theTree, keyToObtain);
   } // End obtain.  
   
   private Keyable obtainFromTree( TreeNode  thisNode,
                                   Orderable toObtain) {    
      if ( thisNode == null) { 
         throw  new NoSuchElementException();
      } else if ( toObtain.keyIsEqualTo( 
                    thisNode.dataIs().keyValueIs())){  
         return thisNode.dataIs();  
      } else if ( toObtain.keyIsLessThan( 
                    thisNode.dataIs().keyValueIs())){ 
          return obtainFromTree( thisNode.leftTreeIs(), toObtain); 
      } else { 
          return obtainFromTree( thisNode.rightTreeIs(), toObtain); 
      } // End if.    
   } // End obtainFromTree.
   

   public Keyable remove( Orderable keyToRemove) { 

   Keyable  holdObject;
   int      holdCount;

     holdObject = removeFromTree( theTree, keyToRemove); 
     holdCount  = numberOfElements;
     trimTree( theTree);     
     numberOfElements = --holdCount;
     return holdObject; 
   } // End remove.  
   

   private Keyable removeFromTree( TreeNode  thisNode,   
                                   Orderable toRemove) {                                   
                                  
      if ( thisNode == null) { 
         throw  new NoSuchElementException();
      } else if ( toRemove.keyIsEqualTo( 
                    thisNode.dataIs().keyValueIs())){ 
         thisNode.setToRemove();
         return thisNode.dataIs();  
      } else if ( toRemove.keyIsLessThan( 
                    thisNode.dataIs().keyValueIs())){ 
          return removeFromTree( thisNode.leftTreeIs(), toRemove); 
      } else { 
          return removeFromTree( thisNode.rightTreeIs(), toRemove); 
      } // End if.                                       
   } // End if.  
   

   private void trimTree( TreeNode aTree) {

   TreeNode holdLeftTree;
   TreeNode holdRightTree;

      if ( aTree != null) {
         if ( ( aTree == theTree)    && 
              ( aTree.toBeRemoved()) ){  
            holdLeftTree  = aTree.leftTreeIs();
            holdRightTree = aTree.rightTreeIs();
            theTree = null;
            mergeTree( holdLeftTree);
            mergeTree( holdRightTree);              
         } else if ( (aTree.leftTreeIs() != null) &&
                     (aTree.leftTreeIs().toBeRemoved()) ){
            holdLeftTree  = aTree.leftTreeIs().leftTreeIs();
            holdRightTree = aTree.leftTreeIs().rightTreeIs();
            aTree.setLeftTree( null);
            mergeTree( holdLeftTree);
            mergeTree( holdRightTree);
         } else if( (aTree.rightTreeIs() != null) &&
                 (aTree.rightTreeIs().toBeRemoved()) ){
            holdLeftTree  = aTree.rightTreeIs().leftTreeIs();
            holdRightTree = aTree.rightTreeIs().rightTreeIs();
            aTree.setRightTree( null);
            mergeTree( holdLeftTree);
            mergeTree( holdRightTree);          
         } else { 
            trimTree(  aTree.leftTreeIs());
            trimTree(  aTree.rightTreeIs());
         } // End if.
      } // End if.
   } // End trimTree.

      
   private void mergeTree( TreeNode aTree) { 
   
      if ( aTree != null) { 
        mergeTree( aTree.leftTreeIs());
        add( aTree.dataIs());
        mergeTree( aTree.rightTreeIs());      
      } // End if.
   } // End mergeTree.                                 


                                              
   
   
   public  boolean isPresent( Orderable keyToCheck){   
      return isKeyPresent( theTree, keyToCheck);               
   } // End isPresent.    
   
   public  boolean isKeyPresent( TreeNode  thisNode,
                                 Orderable toCheck){ 
      if ( thisNode == null) { 
         return false;
      } else if ( toCheck.keyIsEqualTo( 
                    thisNode.dataIs().keyValueIs())){  
         return true;  
      } else if ( toCheck.keyIsLessThan( 
                    thisNode.dataIs().keyValueIs())){ 
          return isKeyPresent( thisNode.leftTreeIs(), toCheck); 
      } else { 
          return isKeyPresent( thisNode.rightTreeIs(), toCheck); 
      } // End if.    
   } // End isKeyPresent;
   
        
   public Enumeration elements() {    
     return new BinaryTreeEnumeration( theTree);
   } // End elements.
   

   public int size() { 
      return numberOfElements;
   } // End size.
   
   
   public boolean isEmpty() { 
      return theTree == null;
   } // End isEmpty.
   
   
   private int treeString( TreeNode     thisNode,
                           StringBuffer addTo,
                           int          soFar) { 
   
      if ( thisNode == null) { 
         return soFar;
      } else { 
         soFar = treeString( thisNode.leftTreeIs(), addTo, soFar);
         addTo.append( ++soFar + " " + thisNode.dataIs() + "\n");
         soFar =  treeString( thisNode.rightTreeIs(), addTo, soFar);
         return soFar;
      } // End if.   
   } // End if.
   
//   public String toString(){ 
//   
//   StringBuffer buffer = new StringBuffer();;
//   int theCount = 0;
//
//      theCount = treeString( theTree, buffer, 0);                            
//      return buffer.toString();
//   } // End toString.    


   public String toString(){ 

   StringBuffer buffer      = new StringBuffer();
   int          thisElement = 0;
   Enumeration  theElements = this.elements();
   
      while ( theElements.hasMoreElements() ) { 
         buffer.append( ++thisElement + "  " + 
                        theElements.nextElement() + ".\n");                             
      } // end while;
      return buffer.toString();   
   } // End toString.    

} // End BinaryTree.


/////////////////////////////////////////////////////////
// Private TreeNode class, providing the nodes which   //
// are linked together to implement the linkedList.    //
///////////////////////////////////////////////////////// 


class TreeNode { 

Keyable  theData;
TreeNode leftSubTree;
TreeNode rightSubTree;
boolean  removeThisNode;

    protected TreeNode( Keyable  toStore){ 
       theData        = toStore.copy();
       leftSubTree    = null;
       rightSubTree   = null;
       removeThisNode = false;  
    } // End ListNode constructor.

    
    protected Keyable dataIs(){ 
       return theData.copy();
    } // End dataIs.
    

    protected TreeNode leftTreeIs(){ 
       return leftSubTree;
    } // End nextIs. 
    

    protected TreeNode rightTreeIs(){ 
       return rightSubTree;
    } // End nextIs. 
    
    protected void setLeftTree( TreeNode setTo) { 
       leftSubTree  = setTo;
    } // End setLeftTree.
    
    protected void setRightTree( TreeNode setTo) { 
       rightSubTree  = setTo;
    } // End setLeftTree.  
    
    protected void setToRemove(){
       removeThisNode = true;
    } // End setToRemove.  
        
    protected boolean toBeRemoved(){ 
       return removeThisNode;
    } // End removeThisNode.
    
} // End class TreeNode.



/////////////////////////////////////////////////////////
// Private LinkedListEnumeration class, implementing   //
// an Enumeration to traverse the list.                //
///////////////////////////////////////////////////////// 

class BinaryTreeEnumeration implements Enumeration { 


Vector theElements = new Vector();

   public BinaryTreeEnumeration( TreeNode startNode){ 
        makeList( startNode);             
   } // End BinaryTreeEnumeration constructor.


   private void makeList( TreeNode thisNode){ 

      if ( thisNode != null) { 
         makeList( thisNode.leftTreeIs());
         theElements.addElement( thisNode.dataIs());          
         makeList( thisNode.rightTreeIs());
      } // End if.   
   } // makeList;

   public boolean hasMoreElements(){ 
       return !theElements.isEmpty();
   } // end hasMoreElements.
   

   public Object nextElement(){ 
   
   Object hold;
   
      if ( theElements.isEmpty()) { 
         throw new NoSuchElementException();
      } else { 
         hold = theElements.firstElement();
         theElements.removeElementAt( 0);
         return hold;
      } // End if.
   } // End nextElement.

} // End class BinaryTreeEnumeration;
 

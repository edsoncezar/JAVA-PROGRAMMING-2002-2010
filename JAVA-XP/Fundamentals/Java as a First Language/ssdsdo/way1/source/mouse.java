// Filename : Mouse.java
// Purpose  : Part of wayPoint1 assessment
// By       : P. Campbell
// Date     : v0.1 24/10/01
// note     : This file is not as well commented as it should be
//            because of the nature of the assessment
//---------------------------------------------------
// DO NOT CHANGE THIS FILE.
// JUST COMPILE AND USE ELSEWHERE
//---------------------------------------------------
public class Mouse extends Object{

   private int count;

   public Mouse(){
	  this(1);
   }

   public Mouse( int num){
	  count = num;
   }

   public void setCount( int num){
	  count = num;
   }  //  End setCount();


   public String toString(){
	  String result;

	  if( count > 8){
		result = new String("Too many mice, call the council");
	  }
	  else{
		result = new String("");
		for( int num=0; num < count; num++)
		  result= result + "squeak ";
	  }  // End if

	  return result + "(" +count + ")";
  }  // End toString()

} // End class Noise

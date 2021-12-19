<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template match="hangman">
   <HTML>

	 <BODY BGCOLOR="#0860A7" TEXT="YELLOW" LINK="WHITE">

		<FONT FACE="Lucida,San-Serif,Arial,Helvetica" SIZE="5">
		<CENTER>
		Welcome to the wAppearances
		<BR />
		<STRONG>Game of Hangman</STRONG>
		</CENTER>
	    </FONT>
      <xsl:apply-templates/>
   </BODY>
   </HTML>
  </xsl:template>

  <xsl:template match="Screen_Hangman">
     <SCRIPT LANGUAGE="JavaScript">
	      <xsl:apply-templates />
	 </SCRIPT>
  </xsl:template>
  <xsl:template match="Screen_Hangman_Enter">
	      <xsl:apply-templates />
  </xsl:template>
  <xsl:template match="Screen_Hangman_Vars">
		var result="<xsl:value-of select="result" />";
		var word="<xsl:value-of select="word" />";
		var answer="<xsl:value-of select="answer" />";
		var score=0;
  </xsl:template>
  <xsl:template match="Screen_Hangman_Options">

	<![CDATA[
	function NewWord(){
		window.navigate("http://localhost:8080/ch22/Hangman.jsp?action=getword");
    }

	function matchChar(guess) {
	   var resultold = result;
		var _result = "";

	   for (var i = 0; i < word.length; i++) {
		 if (guess == word.charAt(i)) {
		   _result = _result + guess;
		 }
		 else {
		   _result = _result + resultold.charAt(i);
		 }
	   }
	   score += 1;
	   result = _result;
	 }

	function Guess(){
	  var _answer;
	  _answer = prompt("Enter your guess for the word:", "");

	  if (_answer != null) { //i.e., if the cancel button is pressed
	    if (_answer == word){
	      alert("You Won! You guessed the word in " + score + " tries!");
	    }else{
	      alert("Your guess " + _answer + " is incorrect.  Keep playing...");
	    }
	  }
	}

	function GuessLetter(){
	  var guess;

	  guess = prompt("Enter Your Guess\nThe word so far is: "+result, "");
	  if (guess != null){ //i.e., user did not Cancel
	    matchChar(guess);
	  }
	}
	]]>

  </xsl:template>
  <xsl:template match="Screen_Play">
      <xsl:apply-templates />
  </xsl:template>
  <xsl:template match="Screen_Play_Enter">
	      <xsl:apply-templates />
  </xsl:template>
  <xsl:template match="Screen_Play_OnGuess">
	    <xsl:apply-templates />
  </xsl:template>
  <xsl:template match="Screen_Play_Content">
	   <P>
	   <CENTER>
	     <INPUT TYPE="BUTTON" VALUE="New Word" onclick="NewWord()"></INPUT>
	     <INPUT TYPE="BUTTON" VALUE="Guess A Letter" onclick="GuessLetter()"></INPUT>
	     <INPUT TYPE="BUTTON" VALUE="Guess The Word" onclick="Guess()"></INPUT>
	   </CENTER>
	   </P>
  </xsl:template>
  <xsl:template match="Screen_Answer">
      <xsl:apply-templates />
  </xsl:template>
  <xsl:template match="Screen_Answer_OnGuess">
      <xsl:apply-templates />
  </xsl:template>
</xsl:stylesheet>

<?xml version="1.0"?>


  <xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


    <xsl:output doctype-public="-//WAPFORUM.COM//DTD WML 1.1//EN"
     doctype-system="http://www.wapforum.org/dtd/wml_1.1.xml" />

  <xsl:template match="hangman">
   <wml>
    <template>
     <do type="options" label="Answer">
      <go href="#Answer" />
     </do>
  </template>
      <xsl:apply-templates/>
   </wml>
  </xsl:template>

  <xsl:template match="Screen_Hangman">
    <card id="Hangman">
      <xsl:apply-templates />
    </card>
  </xsl:template>

  <xsl:template match="Screen_Hangman_Enter">
    <onevent type="onenterforward">
      <refresh>
        <xsl:apply-templates />
      </refresh>
    </onevent>
  </xsl:template>

  <xsl:template match="Screen_Hangman_Vars">
		<setvar name="result" value="{result}" />
		<setvar name="word" value="{word}" />
		<setvar name="answer" value="{answer}" />
		<setvar name="score" value="{score}" />
  </xsl:template>

  <xsl:template match="Screen_Hangman_Options">
    <p>
    <select multiple="false" >
	  <option onpick="http://localhost:8080/ch22/Hangman.jsp?action=getword">New Word</option>
	  <option onpick="#Play">Play the Game!</option>
	</select>
    </p>
  </xsl:template>

  <xsl:template match="Screen_Play">
    <card id="Play">
      <xsl:apply-templates />
    </card>
  </xsl:template>

  <xsl:template match="Screen_Play_Enter">
    <onevent type="onenterforward">
      <refresh>
        <setvar name="guess" value ="" />
        <setvar name="answer" value="" />
      </refresh>
    </onevent>
	      <xsl:apply-templates />
  </xsl:template>

  <xsl:template match="Screen_Play_OnGuess">
    <do type="accept" label="Guess">
      <go href="./wmls/hangman.wmls#matchChar()" />
    </do>
    <p>
	    <xsl:apply-templates />
        <input name="guess" size="1" maxlength="1" emptyok="false" />
    </p>                              
  </xsl:template>

  <xsl:template match="Screen_Play_Content">
    $(result)<br />
    Guess a Letter:
  </xsl:template>

  <xsl:template match="Screen_Answer">
    <card id="Answer">
      <xsl:apply-templates />
    </card>
  </xsl:template>

  <xsl:template match="Screen_Answer_OnGuess">
    <do type="accept" label="Guess Word">
      <go href="./wmls/hangman.wmls#guessWord('$(answer)')" />
    </do>
    <p>
	  <xsl:apply-templates />
      <input name="answer" type="text" />
    </p>
  </xsl:template>

  <xsl:template match="Screen_Answer_Content">
    Guess the Word:
  </xsl:template>

</xsl:stylesheet>

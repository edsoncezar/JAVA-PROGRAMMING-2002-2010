<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="Hangman_WordFileParser" %>

<%
    String fileName = "xml/words.xml";
    String action = request.getParameter("action");
    String word = null;
    StringBuffer encWord = new StringBuffer();
    int nWordIndex;

    response.setContentType("text/plain");

    // Parse words.xml file and extract a word at random
    if ( (action != null) && (action.equals("getword")) ){
      nWordIndex = (int)(java.lang.Math.random()*10)+1;
      Hangman_WordFileParser parser = new Hangman_WordFileParser(nWordIndex);
      InputStream ios = application.getResourceAsStream(fileName);
      parser.parseWordFile(ios);
      word = parser.getWord();
      if ( (word == null) || (word.equals("")) ){
        return;
      }
      for (int i = 0; i < word.length(); i++) {
        encWord.append("*");
      }
    }

    // Write out the Hangman XML
%>
    <hangman>
      <Screen_Hangman>
        <Screen_Hangman_Enter>
          <Screen_Hangman_Vars>
            <%
              out.println("<result>" + encWord + "</result>\r\n");
              if (word != null)
                out.println("<word>" + word + "</word>\r\n");
              else
                out.println("<word></word>\r\n");
            %>
            <answer></answer>
            <score>0</score>
          </Screen_Hangman_Vars>
        </Screen_Hangman_Enter>
        <Screen_Hangman_Options />
      </Screen_Hangman>

      <Screen_Play>
        <Screen_Play_Enter>
          <Screen_Play_OnGuess>
            <Screen_Play_Content />
          </Screen_Play_OnGuess>
        </Screen_Play_Enter>
      </Screen_Play>

      <Screen_Answer>
        <Screen_Answer_OnGuess>
          <Screen_Answer_Content />
        </Screen_Answer_OnGuess>
      </Screen_Answer>

    </hangman>

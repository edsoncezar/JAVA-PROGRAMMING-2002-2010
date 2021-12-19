import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


/***
 * Class Hangman_WordFileParser extracts a random word from words.xml
 */

public class Hangman_WordFileParser {

  public String word;  //Randomly selected word
  public int index;    //Random index of word to select


  public Hangman_WordFileParser(
        int _index
        ) {

        index = _index;
  }

  //Accessor method for returning randomly selected word
  public String getWord() { return (word); }

  public void parseWordFile(InputStream uri) {

        ContentHandler contentHandler = new WordContentHandler();
        ErrorHandler errorHandler = new WordErrorHandler();

        try {
            // Instantiate a parser
            XMLReader parser =
                XMLReaderFactory.createXMLReader(
                    "org.apache.xerces.parsers.SAXParser");

            // Register the content handler
            parser.setContentHandler(contentHandler);

            // Register the error handler
            parser.setErrorHandler(errorHandler);

            // Parse the document
			word = null;
            parser.parse(new InputSource(uri));

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());

        } catch (SAXException e) {
            System.out.println("Error in parsing: " + e.getMessage());
        }
    }

    class WordContentHandler implements ContentHandler {

    /** Hold onto the locator for location information
	 * (not used in Hangman)
	 */
    private Locator locator;
    private int currentIndex;  //Index of word being parsed
    private boolean bExtract;

    /**
     * Provide reference to <code>Locator</code> which provides
     * information about where in a document callbacks occur.
     */
    public void setDocumentLocator(Locator locator) {
        this.locator = locator;
    }

    /**
	 * Start of document
     */
    public void startDocument() throws SAXException {
        currentIndex = 0;
    }

    /**
	 * End of document
     */
    public void endDocument() throws SAXException {
    }

    /**
	 * Processing instruction callback
     */
    public void processingInstruction(String target, String data)
        throws SAXException {
    }

    /**
	 * Start of a namespace prefix mapping
     */
    public void startPrefixMapping(String prefix, String uri) {
    }

    /**
	 * End of a namespace prefix mapping
     */
    public void endPrefixMapping(String prefix) {
    }

    /**
	 * Start of a document element / tag
     */
    public void startElement(String namespaceURI, String localName,
                                           String rawName, Attributes atts)
        throws SAXException {

		if ( (localName.equals("word")) && (currentIndex == index) ){
            bExtract = true;
		}
        else{
            bExtract = false;
            currentIndex++;
        }
    }

    /**
	 * End of a document element / tag
     */
    public void endElement(String namespaceURI, String localName,
                                          String rawName)
        throws SAXException {
    }

    /**
	 * Element / tag content characters
     */
    public void characters(char[] ch, int start, int end)
        throws SAXException {

		if (bExtract){
		    if ( (word == null) && (ch != null) ){
				word = new String(ch, start, end);
                bExtract = false;
			}
		}
    }

    /**
	 * Skip over whitespace
     */
    public void ignorableWhitespace(char[] ch, int start, int end)
        throws SAXException {

        String s = new String(ch, start, end);
    }

    /**
	 * report any skipped elements, attributes, etc.
     */
    public void skippedEntity(String name) throws SAXException {
    }
}

class WordErrorHandler implements ErrorHandler {

    /**
     *   This reports a warning that has occurred.
     */
    public void warning(SAXParseException exception)
        throws SAXException {

        throw new SAXException("Warning encountered");
    }

    /**
     *   This will report an error that has occurred.
     */
    public void error(SAXParseException exception)
        throws SAXException {

        throw new SAXException("Error encountered");
    }

    /**
     *   This reports fatal errors.
     */
    public void fatalError(SAXParseException exception)
        throws SAXException {

        throw new SAXException("Fatal Error encountered");
    }
}
}

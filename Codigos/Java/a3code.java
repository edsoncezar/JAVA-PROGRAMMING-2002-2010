/*
   Starter file for assignment 3

   Cut files at indicated lines.
   Uncomment package and import statements as necessary.
   
*/

/* File A3.java ************************************************************** */


//package csc241.a3;

import java.io.*;
import java.awt.*;
import java.applet.*;
import java.net.*;
import java.util.NoSuchElementException;
import java.util.Enumeration;

public class A3  extends Applet {

  // all recognized html tags, separated into paired vs unpaired

  static final String[] PAIRED = { 
    "a", "address", "applet", "b", "big", "blink", "blockquote",
    "body", "caption", "center", "cite", "code", "dfn", "dir", "div",
    "dl", "em", "font", "form", "frameset", "h1", "h2", "h3", "h4", "h5",
    "h6", "head", "html", "i", "kbd", "map", "menu", "multicol", "nobr",
    "noframes", "ol", "pre", "s", "samp", "script", "select", "small",
    "strike", "strong", "style", "sub", "sup", "table", "td", "textarea",
    "th", "title", "tr", "tt", "u", "ul", "var" 
  };


  static final String[] UNPAIRED = { 
    "area", "base", "basefont", "br", "dd", "doctype", "dt", "embed",
    "frame", "hr", "img", "input", "isindex", "li", "meta", "option", "p",
    "spacer", "wbr"
  };

  // all known html attributes

  static final String[] ATTRIBUTES = { 
    "access", "accesskey", "action", "align", "alink", "alt", "axes",
    "axis", "background", "bgcolor", "border", "button", "cellpadding",
    "cellspacing", "center", "char", "charoff", "checkbox", "checked",
    "circle", "class", "classid", "clear", "code", "codebase", "codetype",
    "color", "cols", "colspan", "compact", "coords", "data", "declare",
    "dir", "disc", "download", "enctype", "face", "file", "for", "frame",
    "height", "hidden", "href", "hspace", "id", "image", "ismap",
    "justify", "lang", "language", "left", "link", "maxlength", "method",
    "multiple", "name", "nohref", "noshade", "notab", "nowrap", "onblur",
    "onchange", "onclick", "onfocus", "onload", "onmouseout",
    "onmouseover", "onselect", "onsubmit", "onunload", "password",
    "prompt", "radio", "rel", "reset", "rev", "right", "rows", "rowspan",
    "rules", "shape", "shapes", "size", "span", "square", "src",
    "standby", "start", "style", "submit", "tabindex", "text", "title",
    "type", "usemap", "value", "valuetype", "vlink", "vspace", "width",
  };

  // some not-great default choices for widgets

  TextArea statusDisplay_ = new TextArea(10, 40);
  TextField urlField_ = new TextField(50);
  List suspiciousAttributeList_ = new List(6, false);
  Button go_ = new Button("go");

  // Required data structures 
  Stack stack_ = new  StackImplementation();
  Set pairedTags_ = new  SetImplementation();
  Set unpairedTags_ = new  SetImplementation();
  Set attributes_ = new   SetImplementation();
  

  public A3() {
    urlField_.setEditable(true);
    // workaround for apparent bug in Netscape List item sizing
    for (int i = 0; i < 10; ++i) suspiciousAttributeList_.addItem("             ");
  }

  public void init() {
    add(go_);
    add(urlField_);
    add(statusDisplay_);
    add(suspiciousAttributeList_);
  }

  public void start() {
    // 2nd part of Netscape bug workaround
    suspiciousAttributeList_.clear();

    // load tables
    for (int i = 0; i < PAIRED.length; ++i) 
      pairedTags_.include(PAIRED[i]);

    for (int i = 0; i < UNPAIRED.length; ++i) 
      unpairedTags_.include(UNPAIRED[i]);

    for (int i = 0; i < ATTRIBUTES.length; ++i) 
      attributes_.include(ATTRIBUTES[i]);

  }


  public boolean action(Event evt, Object arg) {
    if (evt.target == go_) {
      processText();
      return true;
    }
    return super.action(evt, arg);
  }


  void processText() {

    // catch exceptions trying to open url ...

    HtmlExtractor ex = null;
    try {
      ex = new HtmlExtractor(urlField_.getText());
    }
    catch (MalformedURLException e) {
      statusDisplay_.appendText("Sorry, that is not a valid URL\n");
      return ;
    }
    catch (SecurityException e) {
      statusDisplay_.appendText("Sorry, cannot open foreign URL\n");
      return;
    }
    catch (IOException e) {
      statusDisplay_.appendText("Sorry, cannot open URL.\n");
      return;
    }

    // replace the following with your checking code

    try {  // just some sample code to show tokens on console
      for (;;) {
        HtmlToken t = ex.next();
        if (t == null)
          break;
        System.out.print(t.toString());
        System.out.print(t.string());
        if (t instanceof HtmlTag) {
          HtmlTag tt = (HtmlTag)t;
          System.out.print(":");
          if (tt.attributes() != null) {
            for (Enumeration k = tt.attributes().keys(); k.hasMoreElements(); ) {
              Object x = k.nextElement();
              System.out.print(x +"=");
              System.out.print(tt.attributes().at(x).toString());
            }
          }
        }
        System.out.print("\n");
          
      }
    }
    catch (IOException e) {
      statusDisplay_.appendText("IOException\n");
      return;
    }
    
  }

}


/* File HtmlToken.java ******************************************************** */

//package csc241.a3;

/**
 * Base class for extracted Html tokens
 * 
 * Each kind of token has a string representation of its contents
**/
  
public  class HtmlToken {
  protected String string_;

  public HtmlToken(String s) { string_ = s; }

  /** return the string holding contents **/
  public String string() { return string_; }
}


/* File HtmlTag.java *********************************************************** */

/**
 * Class representing  Html tags and their attributes
**/

//package csc241.a3;

public class HtmlTag extends HtmlToken {
  public static final String NOVALUE = "(none)";
 
  boolean end_ = false;
  Map attributes_ = null; // be default, no attributes
  public HtmlTag(String s) { super(s.toLowerCase()); }
  public HtmlTag(String s, boolean isEnd) { super(s.toLowerCase()); end_ = isEnd; }
 
  /** return true if this is an `end' tag of form </tag> **/
  public boolean isEnd() { return end_; }

  /** return attributes, if any, else null */
  public Map attributes() { return attributes_; }

  /** add an attribute to the map **/
  public void addAttribute(String nm, String val) {
    if (attributes_ == null) // if this is first one, make the map
      attributes_ = new  MapImplementation();
    if (val == null)
      attributes_.putAt(nm.toLowerCase(), NOVALUE);
    else
      attributes_.putAt(nm.toLowerCase(), val);
  }
}

/* File HtmlTypeTag.java ******************************************************** */

/**
 * Class representing Html type tags
**/

//package csc241.a3;

public class HtmlTypeTag extends HtmlToken {
  public HtmlTypeTag(String s) { super(s);; }
}

/* File HtmlComment.java ******************************************************** */

/**
 * Class representing Html comments
**/

//package csc241.a3;

public class HtmlComment extends HtmlToken {
  public HtmlComment(String s) { super(s);; }
}

/* File HtmlError.java ********************************************************** */

//package csc241.a3;

/**
 * Class representing unparsable Html fragments
**/

public class HtmlError extends HtmlToken {
  public HtmlError(String s) { super(s);; }
}



/* File Collection.java ******************************************************* */

//package csc241.a3;
//import java.util.NoSuchElementException;
//import java.util.Enumeration;
public interface Collection {
  public boolean isEmpty();
  public Enumeration elements();
}

/* File Set.java ************************************************************** */

//package csc241.a3;
//import java.util.NoSuchElementException;
//import java.util.Enumeration;
public interface Set extends Collection {
  public boolean includes(Object x);
  public void include(Object x);
  public void exclude(Object x);
}

/* File SetImplementation.java ************************************************* */

//package csc241.a3;
//import java.util.NoSuchElementException;
//import java.util.Enumeration;

public class SetImplementation implements Set {
  
  // just stubbed out
  public boolean isEmpty() { return true; };
  public Enumeration elements() { return null; }
  public boolean includes(Object x) { return false; }
  public void include(Object x) {}
  public void exclude(Object x) {}
}



/* File Map.java ************************************************************** */

//package csc241.a3;
//import java.util.NoSuchElementException;
//import java.util.Enumeration;
public interface Map extends Collection {
  public void putAt(Object key, Object contents);
  public Object at(Object key) throws NoSuchElementException;
  public void removeAt(Object key);
  public Enumeration keys();
}


/* File MapImplementation.java ************************************************* */

//package csc241.a3;
//import java.util.NoSuchElementException;
//import java.util.Enumeration;

public class MapImplementation implements Map {
  
  // just stubbed out
  public boolean isEmpty() { return true; };
  public Enumeration elements() { return null; }

  public void putAt(Object key, Object contents) {}
  public Object at(Object key) throws NoSuchElementException {
    throw new NoSuchElementException();
  }
  public void removeAt(Object key) {}
  public Enumeration keys() { return null; }
}


/* File Stack.java ************************************************************** */

//package csc241.a3;
//import java.util.NoSuchElementException;
//import java.util.Enumeration;

public interface Stack extends Collection {
  public void push(Object x);
  public Object top() throws NoSuchElementException;
  public void pop() throws NoSuchElementException;
}

/* File StackImplementation.java ************************************************ */

//package csc241.a3;
//import java.util.NoSuchElementException;
//import java.util.Enumeration;

public class StackImplementation implements Stack {

  // just stubbed out
  public boolean isEmpty() { return true; };
  public Enumeration elements() { return null; }
  public void push(Object x) { }
  public Object top() throws NoSuchElementException {
    throw new NoSuchElementException();
  }
  
  public void pop() throws NoSuchElementException {
    throw new NoSuchElementException();
  }
}

/* File HtmlExtractor.java ******************************************************* */

//package csc241.a3;
//import java.io.*;
//import java.net.*;

/**
 * An HtmlExtractor extracts structured elements form HTML files
 *
 * create one with a string repreenting a url, and call next() until
 * it is all parsed
**/

public class HtmlExtractor {

  InputStream in_; // the input stream corresponding to the URL
  int lastch_ = 0; // remember last character read, when necessary
  HtmlExtractorBuffer buf_ = new HtmlExtractorBuffer(); 

  /** 
   * Construct an extractor for the given url
   * 
   * Throws 
   *   MalformedURLException if addr is not in proper url format
   *   SecurityException, if url is on a foriegn machine
   *   IOException, for any other failure upon attempting to open
  **/

  public HtmlExtractor(String addr) throws MalformedURLException,
   SecurityException, IOException {
    URL  url = new URL(addr);
    in_ = url.openStream();
  }

  /** Utility method to detect white space **/
  public static boolean isWhite(int c) {
    return c == ' ' || c == '\t' || c == '\n' || c == '\r';
  }

  /** 
   * Return next HtmlToken (or one of its subclasses),
   *  or null if there are no more
   * Propagates any IOExceptions encountered
  **/

  public HtmlToken next() throws IOException {
    int ch = lastch_;          // if saved last char, get it
    if (ch == 0)
      ch = in_.read();         // else read it

    if (ch == -1) return null; // must already be at eof;
    lastch_ = 0;

    for (;;) {
      if (ch == -1) {           // eof
        if (!buf_.isEmpty()) {  // flush any remaining chars
          lastch_ = ch;
          return new HtmlToken(buf_.string());
        }
        else 
          return null;
      }
      if (ch != '<') {         // not in tag, just collect chars
        buf_.append(ch);
        ch = in_.read();
      }

      else {                   // tag or type or comment

        if (!buf_.isEmpty()) { // flush previous non-tag chars
          lastch_ = ch;
          return new HtmlToken(buf_.string());
        }

        ch = in_.read();      // detect tag vs type vs comment
        if (ch == -1) {       //  if hit eof first it was just a stray <
          lastch_ = ch; 
          return new HtmlToken("<");
        }
        else if (ch != '!')   // must be a tag
          return readHtmlTag(ch);
        else {                // must be type or comment
          ch = in_.read();
          if (ch == -1) {     // if hit eof first it was a stray <!
            lastch_ = ch; 
            return new HtmlToken("<!"); 
          }
          else if (ch != '-') // must be type
            return readHtmlTypeTag(ch);
          else { 
            ch = in_.read();  // need one more dash to be a legal comment
            if (ch == -1) {   // if hit eof first, it was a stray <!-
              lastch_ = ch; 
              return new HtmlToken("<!-");  
            }
            else if (ch != '-') { // not a comment; parse as type
              buf_.append('-');
              return readHtmlTypeTag(ch);
            }
            else                // must be a comment
              return readHtmlComment();
          }
        }
      }
    }
  }

// utility methods
  protected boolean readToEndOfQuote() throws IOException {
    for (;;) {
      int ch = in_.read();
      if (ch == -1) 
        return false;
      else if (ch == '"')
        return true;
      else
        buf_.append(ch);
    }
  }

  protected HtmlToken readHtmlTypeTag(int firstchar) throws IOException {
    buf_.append(firstchar);
    for (;;) {
      int ch = in_.read();
      if (ch == -1) {
        lastch_ = ch;
        return null;
      }
      else if (ch == '>') {
        lastch_ = 0;
        return new HtmlTypeTag(buf_.string());
      }
      else {
        buf_.append(ch);
        if (ch == '"') {
          if (!readToEndOfQuote()) {
            lastch_ = 0;
            return new HtmlError(buf_.string());
          }
          buf_.append('"');
        }
      }
    }
  }

  protected HtmlToken readHtmlComment() throws IOException {
    int dashCount = 0;
    for (;;) {
      int ch = in_.read();
      if (ch == -1) { 
        lastch_ = ch; 
        return new HtmlError(buf_.string());
      }
      else {
        buf_.append(ch);
        if  (ch == '-') 
          ++dashCount;
        else if (ch == '>') {
          if (dashCount == 2) {
            buf_.backup(3);
            lastch_ = 0;
            return new HtmlComment(buf_.string());
          }
          else 
            dashCount = 0;
        }
        else 
          dashCount = 0;
      }
    }
  }

  protected HtmlToken readHtmlTag(int firstch) throws IOException {
    int ch = firstch;
    boolean isEnd = false;
    if (ch == '/') {
      isEnd = true;
      ch = in_.read();
      if (ch == -1) { lastch_ = ch; return null; }
    }
    
    HtmlTag tag = null;
    for (;;) {
      if (ch == -1) {
        lastch_ = ch; 
        return new HtmlToken(buf_.string());
      }
      else if (ch == '>') {
        lastch_ = 0;
        return new HtmlTag(buf_.string(), isEnd);
      }
      else if (!isWhite(ch)) 
        buf_.append(ch);
      else if (!buf_.isEmpty()) {
        tag = new HtmlTag(buf_.string(), isEnd);
        break;
      }
      ch = in_.read();
    }
    
    String nm = null;
    String val = null;
    
    while (isWhite(ch)) {
      ch = in_.read();
      if (ch == -1) {
        lastch_ = ch;
        return tag;
      }
    }
    buf_.clear();
    
    boolean seenEq = false;
    
    for (;;) {
      if (ch == -1) {
        lastch_ = ch;
        return new HtmlError(tag.string());
      }
      else if (isWhite(ch)) {
        if (!buf_.isAllWhite()) {
          if (!seenEq) {
            if (nm != null) 
              tag.addAttribute(nm, val);
            nm = buf_.string();
            val = null;
          }
          else {
            val = buf_.string();
            tag.addAttribute(nm, val);
            nm = null;
            val = null;
            seenEq = false;
          }
        }
        buf_.clear();
      }
      else if (ch == '=') {
        if (!buf_.isAllWhite())
          nm = buf_.string();
        buf_.clear();
        val = null;
        seenEq = true;
      }
      else if (ch == '>') {
        if (!buf_.isAllWhite()) {
          if (nm == null) 
            nm = buf_.string();
          else if (val == null)
            val = buf_.string();
        }
        if (nm != null) 
          tag.addAttribute(nm, val);
        lastch_ = 0;
        buf_.clear();
        return tag;
      }
      else {
        buf_.append(ch);
        if (ch == '"') {
          if (!readToEndOfQuote()) {
            lastch_ = 0;
            return new HtmlError(buf_.string());
          }
          buf_.append('"');
        }
      }
      ch = in_.read();
    }
  }

}         
    

/**
 * Helper class for Extractor
 * Buffers characters while being parsed, before being turned into strings
 **/
         
  
class HtmlExtractorBuffer {
  static final int INIT_BUFSIZE = 1024;
  protected char[] buf_ = new char[INIT_BUFSIZE];
  protected int bufPos_ = 0;

  boolean isEmpty() { return bufPos_ == 0; }

  /** Reset buffer to be empty **/
  void clear() { bufPos_ = 0; }

  /** add character to end of buffer **/
  void append(int ch) {
    if (bufPos_ >= buf_.length) {
      int newCap = 2 * (buf_.length + 1);
      char[] newBuf = new char[newCap];
      for (int i = 0; i < buf_.length; ++i) newBuf[i] = buf_[i];
      buf_ = newBuf;
    }
    buf_[bufPos_++] = (char)ch;
  }

  /** return true if only contains whitespace chars **/
   boolean isAllWhite() { 
    for (int i = 0; i < bufPos_; ++i) 
      if (!HtmlExtractor.isWhite(buf_[i])) return false;
    return true;
  }

  /** back up the buffer position in order to ignore given number of chars **/
  void backup(int offset) { bufPos_ -= offset; }

  /** Extract string, resetting buffer to be empty **/
  String string() {
    String s =  new String(buf_, 0, bufPos_);
    clear();
    return s;
  }
}


import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import coreservlets.SearchSpec;

/** An applet that reads a value from a TextField,
 *  then uses it to build three distinct URLs with embedded
 *  GET data: one each for Google, Infoseek, and Lycos.
 *  The browser is directed to retrieve each of these
 *  URLs, displaying them in side-by-side frame cells.
 *  Note that standard HTML forms cannot automatically
 *  perform multiple submissions in this manner.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class SearchApplet extends Applet
                          implements ActionListener {
  private TextField queryField;
  private Button submitButton;

  public void init() {
    setFont(new Font("Serif", Font.BOLD, 18));
    add(new Label("Search String:"));
    queryField = new TextField(40);
    queryField.addActionListener(this);
    add(queryField);
    submitButton = new Button("Send to Search Engines");
    submitButton.addActionListener(this);
    add(submitButton);
  }

  /** Submit data when button is pressed <B>or</B>
   *  user presses Return in the TextField.
   */
  
  public void actionPerformed(ActionEvent event) {
    String query = URLEncoder.encode(queryField.getText());
    SearchSpec[] commonSpecs = SearchSpec.getCommonSpecs();
    // Omitting HotBot (last entry), as they use JavaScript to
    // pop result to top-level frame. Thus the length-1 below.
    for(int i=0; i<commonSpecs.length-1; i++) {
      try {
        SearchSpec spec = commonSpecs[i];
        // The SearchSpec class builds URLs of the
        // form needed by some common search engines.
        URL searchURL = new URL(spec.makeURL(query, "10"));
        String frameName = "results" + i;
        getAppletContext().showDocument(searchURL, frameName);
      } catch(MalformedURLException mue) {}
    }
  }
}

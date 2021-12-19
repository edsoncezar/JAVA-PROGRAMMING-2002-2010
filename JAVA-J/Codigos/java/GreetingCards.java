import com.reportmill.foundation.*;
import com.reportmill.graphics.*;
import com.reportmill.text.*;
import com.reportmill.shape.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class GreetingCards extends HttpServlet {
    static Map borderImages;
    static Map messageImages;
    static Random random = new Random();
    
    static final String keys[] = { "Message", "Font", "Color", "Size", "TextEffect", "Picture",
        "Border", "Effect", "Tagline", "Soundtrack" };
    static final String defaults[] = { "Your Message Here!", "Arial", "Black", "18", "None", "Beach",
        "None", "None", "This Just In!", "None" };

public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
{
    // if "resource" specified, get that resource from the session and return it
    String resource = request.getParameter("resource"); loadImages();
    if(resource != null) {
        byte bytes[] = (byte[])request.getSession().getAttribute(resource);
        response.setContentType("application/x-shockwave-flash");
        response.setContentLength(bytes.length);
        response.getOutputStream().write(bytes);
        response.getOutputStream().close();
        return;
    }
    
    // Get userInfo Map from session (allocate and add to session if absent)
    Map userInfo = (Map)request.getSession().getAttribute("UserInfo");
    if(userInfo==null) {
        userInfo = new Hashtable();
        for(int i=0; i<keys.length; i++)
            userInfo.put(keys[i], defaults[i]);
        request.getSession().setAttribute("UserInfo", userInfo);
    }
    
    // Get requests and update userInfo
    for(int i=0; i<keys.length; i++)
        if(request.getParameter(keys[i])!=null)
            userInfo.put(keys[i], request.getParameter(keys[i]));
            
    // Get color for userInfo color string
    String colorString = (String)userInfo.get("Color");
    RMColor color = RMColor.black;
    if(colorString.equals("Red"))
        color = RMColor.red;
    else if(colorString.equals("Green"))
        color = RMColor.green;
    else if(colorString.equals("Blue"))
        color = RMColor.blue;
    else if(colorString.equals("Orange"))
        color = new RMColor(1, .5f, 0);
    else if(colorString.equals("Purple"))
        color = new RMColor(.5f, 0, 1);
        
    // Get Font for userInfo Font and Size strings
    String fontName = (String)userInfo.get("Font");
    float fontSize = RMUtils.floatValue(userInfo.get("Size"));
    RMFont font = RMFont.getFont(fontName, fontSize);
    
    // Get XString for userInfo font and color
    RMXString message = new RMXString(userInfo.get("Message").toString());
    message.addAttribute(font);
    message.addAttribute(color);
    userInfo.put("message", message);

    // Get template and first page
    RMDocument doc = new RMDocument(getServletContext().getResourceAsStream("GreetingCard.rpt")); 
    RMPage page = doc.getPage(0);
    
    // Get and process message effect selection
    String messageEffect = (String)userInfo.get("TextEffect");
    if(!messageEffect.equals("None")) {
        // get the animator and the message shape
        RMAnimator animator = doc.getAnimator(0);
        RMShape messageShape = page.getChildWithName("message");
        
        if(messageEffect.equals("Fade In")) {
            RMShape shape = new RMShape(messageShape);
            page.addChild(shape);
            shape.addChild(messageShape);
            messageShape.setXY(0, 0);
            new RMAgentFadeIn(shape);
        }
        
        else if (messageEffect.equals("Flying Letters"))
            new RMAgentFadeIn(messageShape);
        
        else if (messageEffect.equals("Typewriter"))
            new RMAgentTypewriter(messageShape);
        
        else if (messageEffect.equals("Gravity")) {
            RMShape shape = new RMShape(messageShape);
            page.addChild(shape);
            shape.addChild(messageShape);
            messageShape.setXY(0, -messageShape.height());
            RMAnimAgent agent = new RMAgentGravity(messageShape);
        }
    }

    // Get content image and border image and load into userInfo map
    userInfo.put("image", messageImages.get(userInfo.get("Picture").toString()));
    userInfo.put("border", borderImages.get(userInfo.get("Border").toString()));
    
    // Handle effect
    String effect = (String)userInfo.get("Effect");
    if(!effect.equals("None")) {
    
        // put tagline in userInfo dictionary
        String tagline = (String)userInfo.get("Tagline");
        userInfo.put("tagline", tagline);
        
        // get effect document and embed it
        RMDocument animDoc = null;
        try { animDoc = new RMDocument(getServletContext().getResourceAsStream(effect + ".rpt")); }
        catch(Exception e) { System.err.println("GreetingCards: couldn't find anim document " + effect); }
        RMEmbeddedDocument animShape = new RMEmbeddedDocument(animDoc);
        animShape.setXY(20, 0);
        animShape._delay = 1;
        
        // add animShape to page & adjust maxTime of page animation
        page.addChild(animShape);
        float maxTime = Math.max(page.getChildAnimator(true).getMaxTime(),
            animShape.getChildAnimator(true).getMaxTime() + 1 + 3);
        page.getChildAnimator().setMaxTime(maxTime);                
    }
    
    // Handle soundtrack
    String soundFile = (String)userInfo.get("Soundtrack");
    if(!soundFile.equals("None")) try {
        String soundFilePath = getServletContext().getResource("/"+soundFile).getPath();
        RMSound sound = new RMSound(soundFilePath);
        page.addChild(sound);
    }
    catch(Exception e) { e.printStackTrace(); }
    
    // Generate flash and load into session
    RMDocument report = doc.generateReport(null, userInfo, false);
    request.getSession().setAttribute("Card.swf", report.flashBytes());
    
    // Get html bytes for the flash data and load into session
    String html = report.htmlString(request.getRequestURI() + "?id=" + random.nextInt() + "&" + "resource=Card.swf");
    html = StringUtils.replace(html, "<center>", "");
    request.getSession().setAttribute("flashHtml", html);
    
    // Forward AdHoc.jsp
    try {
        getServletConfig().getServletContext().getRequestDispatcher("/GreetingCards.jsp").forward(request, response);   
    } catch(Exception e) { e.printStackTrace(); }
}

// method for loading our border and message images into static hashtables
public void loadImages() {
    if(borderImages==null) try {
    
        // determine the base URL for the images
        String baseURL = getServletContext().getRealPath("images");
        
        // get URLs of border images and cache them
        borderImages = new Hashtable();
        borderImages.put("Blue Glass", baseURL + "/GCBorder-BlueGlass.jpg");
        borderImages.put("Crumple", baseURL + "/GCBorder-Crumple.jpg");
        borderImages.put("Metal", baseURL + "/GCBorder-Metal.jpg");
        borderImages.put("Mosaic", baseURL + "/GCBorder-Mosaic.jpg");
        borderImages.put("None", baseURL + "/GCBorder-None.jpg");
        borderImages.put("Sky", baseURL + "/GCBorder-Sky.jpg");
        borderImages.put("Wood", baseURL + "/GCBorder-Wood.jpg");
        
        // get URLs of message images and cache them
        messageImages = new Hashtable();
        messageImages.put("Astronaut", baseURL + "/GCImage-astronaut.jpg");
        messageImages.put("Beach", baseURL + "/GCImage-beach.jpg");
        messageImages.put("Bear", baseURL + "/GCImage-bear.jpg");
        messageImages.put("Cabo", baseURL + "/GCImage-cabo.jpg");
        messageImages.put("Ducky", baseURL + "/GCImage-ducky.jpg");
        messageImages.put("Dune", baseURL + "/GCImage-dune.jpg");
        messageImages.put("Liberty", baseURL + "/GCImage-liberty.jpg");
        messageImages.put("London", baseURL + "/GCImage-london.jpg");
        messageImages.put("Waterfall", baseURL + "/GCImage-waterfall.jpg");
        
    } catch(Exception e) { e.printStackTrace(); }
}

public static String fonts[] = { "Arial Bold", "Comic Sans MS", "Copperplate", "Marker Felt Wide", "Times Bold", "Zapfino" };
public static String fontHTML(HttpServletRequest request) {
    return selectTagHTML("Font", fonts, reqGet(request, "Font")); }

public static String colors[] = { "Black", "Red", "Green", "Blue", "Orange", "Purple" };
public static String colorHTML(HttpServletRequest request) {
    return selectTagHTML("Color", colors, reqGet(request, "Color")); }

public static String sizes[] = { "18", "24", "36", "48", "72", "124" };
public static String sizeHTML(HttpServletRequest request) {
    return selectTagHTML("Size", sizes, reqGet(request, "Size")); }

public static String textEffects[] = { "None", "Fade In", "Flying Letters", "Typewriter", "Gravity" };
public static String textEffectHTML(HttpServletRequest request) {
    return selectTagHTML("TextEffect", textEffects, reqGet(request, "TextEffect")); }

public static String pictures[] = { "Beach", "Bear", "Dune", "Liberty", "London", "Astronaut", "Ducky", "Waterfall" };
public static String pictureHTML(HttpServletRequest request) {
    return selectTagHTML("Picture", pictures, reqGet(request, "Picture")); }

public static String borders[] = { "None", "Blue Glass", "Crumple", "Metal", "Mosaic", "Sky", "Wood" };
public static String borderHTML(HttpServletRequest request) {
    return selectTagHTML("Border", borders, reqGet(request, "Border")); }

public static String effects[] = { "None", "PlaneAnim", "NewspaperAnim" };
public static String effectHTML(HttpServletRequest request) {
    return selectTagHTML("Effect", effects, reqGet(request, "Effect")); }

public static String soundtracks[] = { "None", "Sound_Sentimental.wav" };
public static String soundtrackHTML(HttpServletRequest request) {
    return selectTagHTML("Soundtrack", soundtracks, reqGet(request, "Soundtrack")); }

public static String reqGet(HttpServletRequest request, String aName)
{
    Map userInfo = (Map)request.getSession().getAttribute("UserInfo");
    return (String)userInfo.get(aName);
}

public static String selectTagHTML(String aName, String options[], String selectedName)
{
    // Get new StringBuffer and add SELECT tag to it
    StringBuffer sb = new StringBuffer();
    sb.append("<SELECT ");
    sb.append("name=\""); sb.append(aName); sb.append("\" ");
    sb.append("size=\"1\" id=\""); sb.append(aName); sb.append("\">\n");
    
    // Add options
    for(int i=0; i<options.length; i++) {
        sb.append("<OPTION value=\""); sb.append(options[i]); sb.append('"');
        sb.append(options[i].equals(selectedName)? " selected>" : ">");
        sb.append(options[i]);
        sb.append("</OPTION>\n");
    }
    
    sb.append("</SELECT>\n");
    return sb.toString();
}

}
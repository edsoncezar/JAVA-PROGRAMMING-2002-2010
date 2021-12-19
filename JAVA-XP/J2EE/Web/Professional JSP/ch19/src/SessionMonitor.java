import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SessionMonitor extends HttpServlet {

  // Process a client request: Give option to add a TestBean to the session.
  // Then, if a bean name was specified display that bean's details, otherwise
  // list all beans in the session

  public void doGet(HttpServletRequest req, HttpServletResponse res)
              throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();
    HttpSession session = req.getSession(true);
    out.println("<HTML><HEAD><TITLE>session monitor</title></head>");
    out.println("<BODY><H1>SessionMonitor</H1>");
    out.println("This form allows us to add new string values to the ");
    out.println("current session to check out this servlet");
    out.println("<FORM>add string key <INPUT TYPE=\"text\" ");
    out.println("NAME=\"key\"><br/>");
    out.println("add string value<INPUT TYPE=\"text\" NAME=\"value\"><br/>");
    out.println("<INPUT TYPE=\"submit\"></FORM><P>");
    
    testInit(req,session);
    String beanName = req.getParameter("name");
    if (beanName == null) {
      showBeanList(req, session, out);
    } else {
      showSingleInstance(beanName, session, out);
    }
    out.println("</BODY></HTML>");
  }


  // Store a TestBean in the session with the specified name and first value

  private void testInit(HttpServletRequest req, HttpSession session) {
    String newKey = req.getParameter("key");
    String newValue = req.getParameter("value");
    if ((newKey !=null) && (newValue != null)){
      TestBean test= new TestBean();
      test.setValue1(newValue);
      test.setValue2("fixed text");
      test.setValue3(newKey+"-->"+newValue);
      session.setAttribute(newKey, test); 
    }
  }


  // Display a list of beans stored in the session, and links back to this
  // Servlet to give details of each one

  private void showBeanList(HttpServletRequest req,
                            HttpSession session,
                            PrintWriter out) {
    String URI = req.getRequestURI();
    Enumeration names = session.getAttributeNames();

    while (names.hasMoreElements()) {
      String attributeName= (String) names.nextElement();
      out.print("<A HREF=");
      out.print(URI);
      out.print("?name=");
      out.print(attributeName);
      out.print(">");
      out.println(attributeName);
      out.print("</A><BR />");
    }
  }


  // Display the details of the bean stored in the session under the name
  // beanName. Introspection used to display each of its fields.

  private void showSingleInstance(String beanName,
                                  HttpSession session,
                                  PrintWriter out) {
    Object check = session.getAttribute(beanName);
    out.println("<H2> Checking object ");
    out.println(beanName);
    out.println("</H2><UL>");
    try {
      Class checkClass = check.getClass();
      Field[] fields = checkClass.getFields();
      for (int i = 0; i < fields.length; i++) {
        out.println("<LI>");
        out.println(fields[i].getName());
        out.println(" (");
        out.println(fields[i].getType().toString());
        out.println("): ");
        try {
          out.println(fields[i].get(check).toString());  
        } catch (Exception e) {
          out.println(" ! Cannot be displayed !");
        }
      }
    } catch (NullPointerException e) {
      out.println("null pointer Exception");
    }
  }

  // A sample bean we can put into the session to test this servlet
  private class TestBean {
    public String value1;
    public String value2;
    public String value3;
    public String getValue1() {
      return value1;
    }
    public void setValue1(String value) {
      value1 = value;
    }
    public String getValue2() {
      return value2;
    }
    public void setValue2(String value) {
      value2 = value;
    }
    public String getValue3() {
      return value3;
    }
    public void setValue3(String value) {
      value3 = value;
    }
  }
}

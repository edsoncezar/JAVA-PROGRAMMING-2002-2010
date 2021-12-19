package com.wrox.projsp.ch11.jsp;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import org.apache.oro.text.perl.*;
import java.util.*;
import org.apache.struts.util.*;
    
/** Maps urls to pagebeans and responses. 

<p>The mapping uses perl style regular expressions and allows insertion of match 
groups into the target. Match groups are defined by a pattern in 
parantheses in the url and referred to in the target by $1, $2, etc.</p>

<p>The servlet instantiates the bean of the class given in the mapping, and 
adds it as an attribute of the given name to the given scope 
(request, session, ...). Use the bean on a page with <pre><code>

    <jsp:usebean name="givenName" scope="givenScope">

</code></pre>. </p>

<p>It then initializes it with the properites specified in the mapping after it
has performed the substitution of $1, $2, ... with the first, second, ... match 
group in the URL.</p>

<p>The servlet maps all request parameters to properties either by calling
setX(y) or, if the bean implements AutoBean and setX(String) doesn't exist,
it calls set("x", y).</p>

<p>The servlet then calls map the request parameters tries to find action for all
request parameters and calls them if it finds them. The actions are methods of
the form doX(String y).</p>

If the bean doesnt specify a fowarding page (by setting its forward property
to it) the reqest is forwarded to the page given in the mapping.</p>

<p> Example: </p>

For a mapping with <pre>

    inPattern = /confirmRegistration/(.*)/(.*).do
    forwardPattern = 
    scope = session
    bean = com.mycompany.ProfileBean
    name = profile
    properties = userId=$1;confirmId=$2.txt
    </pre>
    
If a request matching the inPattern comes in then a bean of the class 
com.mycompany.ContentBean will be instantiated and added to the session 
attributes under the name "contentBean". For any request parameters x=y
the method setX(y) will be called if it exists. If not the method set("x", y) 
will be called. The same will be done for the properties given in the mapping 
(userId and conformId). Thereafter, for any request paramters x=y the servlet 
will try to find a method doX(String) and call it with y as an argument. 
Next if the bean has set its forward property the request is forwarded to the 
it's value. Else the request is forwarded to the value given in the mapping.
    
*/

public class DispatchServlet extends HttpServlet {
    
    static {
      System.out.println(" Loading class Dispatchservlet");
    }
    
    protected PrintStream accessLog;

    Mapping[] mappings = new Mapping[] {
        new Mapping(
            "/discuss.do",                        // incoming path
            "/discuss.jsp",                       // to be invoked 
            "session",                            // page bean scope
            "com.wrox.projsp.ch11.jsp.DiscussionBean",     // page bean class
            "discussion",                         // page bean attribute, scripting variable 
            ""),                                  // initial page bean properties 
            
        new Mapping("(.*)", "$1", "","", "", "")  // default 
    };    
      
    public Mapping findMapping(String inPath) {
        for(int i = 0; i < mappings.length; i++) 
            if(mappings[i].matches(inPath)) {
                 System.out.println("using mapping no " + i);
                 return mappings[i];
            }
        throw new RuntimeException("Missing default mapping");
    }	

    public void init() throws ServletException {
        try {
            //accessLog = new PrintStream(new FileOutputStream(getServletConfig().getInitParameter("accessLog"), true));
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }    
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        dispatch(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        dispatch(request, response);
    }

    protected void dispatch(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        logAccess(request);
        String path = request.getServletPath();
        System.out.println("DispatchServlet.dispatch(): path=" + path);
        Mapping mapping = findMapping(path);
        String forward = "";
        boolean invalidateSession = false;
        PageBean pageBean = getPageBean(mapping, request);
        if(pageBean != null) {
            //Map properties = mapping.getProperties(request.getServletPath());
            pageBean.setRequest(request);
            pageBean.setResponse(response);
            //addRequestParametersToMap(request, properties);
            //ReflectionUtil.callStringMethods("set", pageBean, properties);
            //ReflectionUtil.callStringMethods("do", pageBean, properties);
            populate(pageBean, mapping, request);
            pageBean.afterPopulate();
            activate(pageBean, mapping, request);
            forward = pageBean.get("forward");
            invalidateSession = "true".equals(pageBean.get("invalidateSession"));
        }
        if("".equals(forward))
            forward = mapping.getForward(path);
        System.out.println("DispatchServlet.dispatch(): forward=" + forward);
        RequestDispatcher r = getServletContext().getRequestDispatcher(forward);
        r.forward(request, response);
        if(invalidateSession)
            request.getSession().invalidate();
    }
    
    protected PageBean getPageBean(Mapping mapping, HttpServletRequest request) 
    throws ServletException {
        PageBean pageBean;
        HttpSession session = request.getSession();
        if("request".equals(mapping.scope)) 
            pageBean = (PageBean) request.getAttribute(mapping.name);
        else
            pageBean = (PageBean) session.getAttribute(mapping.name);
        if(pageBean == null) {
            try {
                System.out.println("mapping.bean=" + mapping.bean);
                pageBean = (PageBean) Class.forName(mapping.bean).newInstance();
            } catch(Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
            if("request".equals(mapping.scope)) 
                request.setAttribute(mapping.name, pageBean);
            else
                session.setAttribute(mapping.name, pageBean);
        }
        return pageBean;
    }		
    
    /** call any setX(y) or set("x", y) where x=y is in the request header */
    protected void populate(Object bean, Mapping mapping, HttpServletRequest request) {
        try {
            BeanUtils.populate(bean, mapping.getProperties(request.getServletPath()));
            BeanUtils.populate(bean, request);
        } catch(Exception e) { 
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
    
    /** call any doX(y) where x=y is in the request header */
    protected void activate(Object bean, Mapping mapping, HttpServletRequest request) {
        try {
            BeanUtils.activate(bean, mapping.getProperties(request.getServletPath()));
            BeanUtils.activate(bean, request);
        } catch(Exception e) { 
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public static void addRequstParametersToMap(HttpServletRequest request, Map map) {
        Enumeration names = request.getParameterNames();
        while(names.hasMoreElements()) {
            String name = (String) names.nextElement();
            map.put(name, request.getParameter(name));
        }
    }
    
    protected void logAccess(HttpServletRequest request) {
        if("localhost:8080".equals(request.getHeader("Host")))
            return;
        accessLog.println("");
        accessLog.println("$access=");
        accessLog.println("time=" + new Date());
        java.util.Enumeration e = request.getHeaderNames();
        while(e.hasMoreElements()) {
            String h = (String) e.nextElement();
            accessLog.println(h + "=" + request.getHeader(h));
        }
    }
        
    public static class Mapping {
        private static Perl5Util perl = new Perl5Util();
        public String inPattern;
        public String forwardPattern;
        public String scope;
        public String bean;
        public String name;
        public String properties;
        
        public Mapping(String inPattern, String forwardPattern, String scope, String bean, String name, String properties) {
            this.inPattern = inPattern;
            this.forwardPattern = forwardPattern;
            this.scope = scope;
            this.bean = bean;
            this.name = name;
            this.properties = properties; 
        }
        
        public boolean matches(String inPath) {
            return perl.match("m|^" + inPattern + "$|", inPath);
        }
        
        public String getForward(String inPath) {
             return perl.substitute("s|" + inPattern + "|" + forwardPattern + "|g", inPath);
        }
        
        public Hashtable getProperties(String inPath) {
            String p = perl.substitute("s|" + inPattern + "|" + properties + "|g", inPath);
            Properties pr = new Properties();
            try {
                pr.load(new ByteArrayInputStream(p.getBytes())); 	
            } catch(Exception e) {
                throw new RuntimeException(e.getMessage());
            }
            return pr;
        }
    }
}

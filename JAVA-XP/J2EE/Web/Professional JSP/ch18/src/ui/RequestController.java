
package ui;

import java.io.Serializable;
import javax.swing.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

// Import the NamveValue model
import jspstyle.*;

import java.util.HashMap;

/**
*	Class to provide page flow logic for the Data Source Explorer.
*	All requests go to this class. This class is responsible for
*	instantiating the beans required by the system.
*   This class uses reflection to instantiate request handlers as
*   required.
*/
public class RequestController implements Serializable {

    //---------------------------------------------------------------------
	// Class data and methods
	//---------------------------------------------------------------------
    /** 
    *   URL of the system's login page.
    *   We'll redirect users to this if they don't have a connection
    */
    public static final String LOGIN_PAGE = "login.html";
    
    /** Package from which to attempt to load RequestHandler objects */
    private static final String REQUEST_HANDLER_PACKAGE = "ui.requesthandlers";
    
	/**
	*	Name of the session bean that will be used for this application.
	*	This must be matched by the useBean actions in the JSPs.
	*/
	private static final String SESSION_BEAN_NAME = "browseSession";
    
    /**
	*	Convenience method for RequestHandler implementations, allowing Java classes to access
	*	the session bean easily. Unlike checkSessionBeanIsAvailable(), this method will
    *   throw an exception if the bean has not already been instantiated.
	*	@return the session bean, which must have been instantiated already.
	*/
    public static BrowseSession FindSessionBean(PageContext pageContext, RequestHandler rh) throws BrowseException {
      BrowseSession session = (BrowseSession) pageContext.getAttribute(RequestController.SESSION_BEAN_NAME, PageContext.SESSION_SCOPE);
		if (session == null)
			throw new BrowseException("Internal error: illegal state. Session bean shouldn't be null handling action " + rh.getClass());
       return session;
    }

    //---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
    /**
    *   Hash table of RequestHandler instances, keyed by class name.
    *	This is used for performance optimization, to avoid the need
    *   to load a class by name to process each request.
    */
    private HashMap handlerHash = new HashMap();

    
    //---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------
	/**
    *   This is the key method through which all request to the
    *   application are sent.
	*	Process a request, updating the session and
	*	returning the URL of the resulting JSP view
	*/
	public String getNextPage(PageContext pageContext, HttpServletRequest request) throws BrowseException {
		String action = request.getParameter("action");
        // If no action was specified, the user must first log in
		if (action == null || action.equals(""))
			return LOGIN_PAGE;
        
        // If we have an action parameter, obtain the RequestHandler that implements
        // the action. This method will throw a BrowseException if no handler is
        // associated with the action.
        RequestHandler requestHandler = getHandlerInstance(action);
        
        // Create the session bean if it doesn't exist
        checkSessionBeanIsAvailable(pageContext);
        
        // Return the URL of the view the RequestHandler instance 
        // chooses after doing the work of processing the request
        return requestHandler.handleRequest(pageContext, request);
	}	// getNextPage


	/**
	*	Locate and return the RequestHandler instance for this action.
	*	Instances are stored in a hash table once instantiated, so after the initial
	* 	use of Class.forName() this method is very fast and does not rely on
	*	reflection.
	*/
    private RequestHandler getHandlerInstance(String action) throws BrowseException {
        String handlerName = REQUEST_HANDLER_PACKAGE + "." + action;
        RequestHandler requestHandler = (RequestHandler) handlerHash.get(handlerName);
        if (requestHandler == null) {
             // We don't have a handler instance associated with this action,
             // so we need to instantiate one and put in in our hash table
             try {
                System.out.println("Loading handler instance...");
                // Use reflection to load the class by name
                Class handlerClass = Class.forName(handlerName);
                // Check the class we obtained implements the RequestHandler interface
                if (!RequestHandler.class.isAssignableFrom(handlerClass))
                    throw new BrowseException("Class " + handlerName + " does not implement the RequestHandler interface " );
                // Instantiate the request handler object
                requestHandler = (RequestHandler) handlerClass.newInstance();
                // Save the instance so we don't have to load it dynamically to process
                // further requests from this user
                handlerHash.put(handlerName, requestHandler);
            }
            catch (ClassNotFoundException ex) {                
                throw new BrowseException("No handler for action [" + handlerName 
                    + "]: class " + handlerName + " could not be loaded. " + ex);
            }
            catch (InstantiationException ex) {
                // It probably doesn't have a no-argument constructor
                throw new BrowseException("Class "+ handlerName + " could not be instantiated. " 
                    + " Is it a bean? " + ex);
            }
            catch (IllegalAccessException ex) {
                throw new BrowseException("Class " + handlerName + " could not be instantiated. "
                    + "Does it have a public constructor? " + ex);
            }
        }
        
        // If we get to here, we have a valid RequestHandler instance,
        // whether it came from the hash table or from dynamical class loading
        return requestHandler;
    }   // getHandlerInstance


    //---------------------------------------------------------------------
	// Implementation methods
	//---------------------------------------------------------------------
	/**
	*	Return the user's session bean. Instantiate a new session bean and
	*	put it in the session if none is found.
	*/
	private void checkSessionBeanIsAvailable(PageContext pageContext) {
		BrowseSession session = (BrowseSession) 
            pageContext.getAttribute(SESSION_BEAN_NAME, PageContext.SESSION_SCOPE);
		if (session == null) {
			System.out.println("RequestController: creating session object");
			session = new BrowseSession();
            // Place the session in the PageContext, so it will be accessible
            // to JSPs as a session bean
			pageContext.setAttribute(SESSION_BEAN_NAME, session, PageContext.SESSION_SCOPE);
		}
	}

}	// class RequestController
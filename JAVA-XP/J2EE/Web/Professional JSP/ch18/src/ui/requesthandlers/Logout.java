
package ui.requesthandlers;

import javax.servlet.http.*;
import javax.servlet.jsp.*;

import ui.*;


public class Logout implements RequestHandler {
    
    private static final String LOGOUT_PAGE = "logout.jsp";

    public String handleRequest(PageContext pageContext, HttpServletRequest request) {
		// We can rely on the session object to clean up after itself:
		// all we need to do is invalidate the session so that it knows it should
		pageContext.getSession().invalidate();
		return LOGOUT_PAGE;
	}

}
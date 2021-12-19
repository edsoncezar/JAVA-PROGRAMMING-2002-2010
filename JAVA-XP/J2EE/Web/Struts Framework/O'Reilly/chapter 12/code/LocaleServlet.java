import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Locale;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Prints out information about a user's preferred locales
 */
public class LocaleServlet extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html";

  /**
   * Initialize the servlet
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

	/**
   * Process the HTTP Get request
   */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();

    out.println("<html>");
    out.println("<head><title>The Example Locale Servlet</title></head>");
    out.println("<body>");

		// Retrieve and print out the user's preferred locale
		Locale preferredLocale = request.getLocale();
		out.println("<p>The user's preffered Locale is " + preferredLocale + "</p>");

    // Retrieve all of the supported locales of the user
		out.println("<p>A list of preferred Locales in descreasing order</p>");
		Enumeration allUserSupportedLocales = request.getLocales();
		out.println("<ul>");
		while( allUserSupportedLocales.hasMoreElements() ){
		  Locale supportedLocale = (Locale)allUserSupportedLocales.nextElement();
			out.println("<li>Locale: " + supportedLocale + "</li>");
		}
		out.println("</ul>");

		// Get the container's default locale
		Locale servletContainerLocale = Locale.getDefault();
		out.println("<p>The containers Locale " + servletContainerLocale + "</p>");
    out.println("</body></html>");
  }
  /**
   * Clean up the servlet
   */
  public void destroy() {
  }
}

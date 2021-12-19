package WlTagLibs;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

// *************************************************
//
// The EmailMe tag displays an email gif image
// with an email address.  The image and text are
// linked to the email address.
//
// Attributes:
// align     - optional left/center/right alignment
// style     - optional stylesheet class
// multiline - optional breaks the image and text onto separate lines
//             (default is on the same line)
//
// *************************************************

public class EmailMe extends TagSupport
{

	// The alignment of the output
	private String align = "";

	// The css class for the output text
	private String style = "";

	// Should the gif and text be on multiple lines?
	// Default is the same line
	private boolean multiline = false;


	public int doStartTag() throws JspException
	{


		try
		{

			// Get the JspWriter so we can output our HTML
			JspWriter out = pageContext.getOut();

			// Get the session attribute urlRoot to build the link
			String urlRoot = (String)(pageContext.getSession().getAttribute("urlRoot"));

			// Add alignment if asked for
			if (align.length() > 0)
			{
				out.print("<div align=\"" + align + "\">");
			}

			// Add the href text
			out.print("<a href=\"mailto:mike@mkellstrand.com\"");

			// If a style class has been specified, then add it
			if (style.length() > 0)
			{
				out.print(" class=\"" + style + "\"");
			}

			// Add the email image
			out.print("><img src=\"http://www.mkellstrand.com/images/mail.gif\" width=\"32\" height=\"32\" alt=\"Email me\" border=\"0\" align=\"center\" />");

			// Add some spacing
			out.print("<img src=\"http://www.mkellstrand.com/images/dot_clear.gif\" width=\"6\" height=\"1\" border=\"0\" />");

			// If the gif and text should be on different lines, then add a break
			if (multiline)
			{
				out.print("<br />");
			}

			// Add the text and close the anchor
			out.print("mike@mkellstrand.com</a>");

			// Close off alignment if asked for
			if (align.length() > 0)
			{
				out.print("</div>");
			}


		}
		catch(Exception e)
		{
			throw new JspTagException("EmailMe: " + e.getMessage());
		}
		return  SKIP_BODY;
	}


	public int doEndTag()
	{
		return EVAL_PAGE;
	}



	// The alignment of the output
	public void setAlign(String a)
	{
		align = a;
	}

	// The css style for the output text
	public void setStyle(String s)
	{
		style = s;
	}

	// Sets the line mode for the output
	// Default is the gif and text apear on the same line
	public void setMultiline(String m)
	{
		multiline = true;
	}


}
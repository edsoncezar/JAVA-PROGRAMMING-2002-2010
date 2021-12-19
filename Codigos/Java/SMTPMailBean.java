package com.davidreilly.mail;

import java.net.*;
import java.io.*;
import java.util.Date;

//
//
// SMTPMailBean
//
// Non-visual JavaBean proving simple mail-transfer protocol capability
// Non-visual component work well under Sun's BeanBox, however a 
// changes to make the component visual are required for Borland JBuilder.
//
// To make these changes, add the following line to each constructor
//
//		setText ("SMTPMailBean");
//
// add the import statement
//		import java.awt.*;
//
// and change the class declaration line to
//
// public final class SMTPMailBean extends Label implements Serializable
//
// and set the visible property to false.
//
// File creation date     : January 9, 1998
// Last modification date : June 02, 1998
// Modified by            : David Reilly
//
//
public final class SMTPMailBean implements Serializable
{
	// Constants

	/** Port on which the simple mail transfer protocol opperates */
	public static final int SMTPPort = 25;

	/** Successful status code prefix */
	public static final char successPrefix = '2';

	/** More data required code prefix */
	public static final char morePrefix = '3';

	/** Failure code prefix */
	public static final char failurePrefix = '4';
	
	// Private member variables

	/** Carriage return/linefeed - println gives different results on different platforms */
	private static final String CRLF = "\r\n";

	/** Mail message sender */
	private String mailFrom = "";

	/** Mail message receiver */
	private String mailTo = "";

	/** Mail message subject */
	private String messageSubject = "";

	/** Mail message body */
	private String messageBody = "";

	/** Mail server for routing SMTP messages through */
	private String mailServer = "";

	/** Creates an instance of a SMTPMailBean object */
	public SMTPMailBean ()
	{
		// Call parent constructor
		super();
	}

	/** Creates an instance of a SMTPMailBean object */
	public SMTPMailBean ( String serverName)
	{
		// Call parent constructor
		super();

		// Assign to private member variable
		mailServer = serverName;
	}

	/** Accessor method (GET) for message From property */
	public String getFrom()
	{
		return mailFrom;
	}

	/** Accessor method (GET) for message To property */
	public String getTo()
	{
		return mailTo;
	}

	/** Accessor method (GET) for message Subject property */
	public String getSubject()
	{
		return messageSubject;
	}

	/** Accessor method (GET) for message body property */
	public String getMessage()
	{
		return messageBody;
	}

	/** Accessor method (GET) for SMTP mail server property */
	public String getMailServer()
	{
		return mailServer;
	}

	/** Accessor method (SET) for message From property */
	public void setFrom( String from )
	{
		// Assign to member variable
		mailFrom = from;
	}

	/** Accessor method (SET) for message To property */
	public void setTo ( String to )
	{
		// Assign to member variable
		mailTo = to;
	}

	/** Accessor method (SET) for message Subject property */
	public void setSubject ( String subject )
	{
		// Assign to member variable
		messageSubject = subject;
	}

	/** Accessor method (SET) for message body property */
	public void setMessage ( String body )
	{
		// Assign to member variable
		messageBody = body;
	}

	/** Accessor method (SET) for SMTP mail server property */
	public void setMailServer ( String server )
	{
		// Assign to member variable
		mailServer = server;
	}

	/** Checks an SMTP response, returning a boolean if status indicates a valid response */
	private boolean responseValid( String response )
	{
		// pre : response is in form +ERR xxxxxx or +OK xxxxxx
		
		// Check to see if line has a space separator
		if (response.indexOf(" ") == -1)
			// No... signal failure
			return false;

		// Chop response code from line
		String code = response.substring( 0, response.indexOf(" "));

		// Convert code to uppercase, in the unlikely event the server returns code in lowercase
		code = code.toUpperCase();

		// Check prefix of code
		if (( code.charAt(0) == successPrefix ) || 
		    ( code.charAt(0) == morePrefix )  )
			// Yes... signal success
				return true;
			else
				// Some kind of failure, or unknown code
				return false;
		}

	/** Sends the current mail message, throwing an IOException if the mail could not be sent */
	public void sendMail()// throws IOException
	{
		try {
		String response;

		// Open a connection to our assigned SMTP server
		Socket mailSock = new Socket (mailServer, SMTPPort);
        
		// Obtain the required input and output readers/writers
		BufferedReader bin = new BufferedReader ( new InputStreamReader(mailSock.getInputStream()));
		PrintWriter pout = new PrintWriter ( new OutputStreamWriter(mailSock.getOutputStream()));

		// Get server response
		response = bin.readLine();

		// Check response code
		if ( !responseValid(response) )
			throw new IOException("ERR - " + response);

		// Send a HELO command
		try
		{
			InetAddress addr = InetAddress.getLocalHost();

			String localHostname = addr.getHostName();
			// Send a HELO request with a valid hostname
			pout.print ("HELO " + localHostname + CRLF);
		}
		catch (UnknownHostException uhe)
		{
			// Send a HELO request without a valid hostname
			pout.print ("HELO myhostname"  + CRLF);
		}

		// Flush buffer
		pout.flush();

		// Get server response
		response = bin.readLine();

		// Check response code
		if ( !responseValid(response) )
			throw new IOException("ERR - " + response);

		// Send mail field
		pout.println ("MAIL From:<" + mailFrom + ">");

		// Flush buffer
		pout.flush();

		// Get server response
		response = bin.readLine();

		// Check response code
		if ( !responseValid(response) )
			throw new IOException("ERR - " + response);

		// Send rcpt field
		pout.println ("RCPT To:<" + mailTo + ">");

		// Flush buffer
		pout.flush();

		// Get server response
		response = bin.readLine();

		// Check response code
		if ( !responseValid(response) )
			throw new IOException("ERR - " + response);

		// Send data command
		pout.println ("DATA");

		// Flush buffer
		pout.flush();

		// Get server response
		response = bin.readLine();

		// Check response code
		if ( !responseValid(response) )
			throw new IOException("ERR - " + response);

		// Send full mail message               
		pout.println ("Date: " + new Date().toGMTString() );
		pout.println ("From: " + mailFrom);
		pout.println ("To: " + mailTo);
		pout.println ("Subject: " + messageSubject);

		// Leave a blank line for message
		pout.println ();

		// Send message
		pout.println (messageBody);

		// Terminate message
		pout.println (".\n\r");

		// Flush buffer
		pout.flush();

		// Get server response
		response = bin.readLine();

		// Check response code
		if ( !responseValid(response) )
			throw new IOException("ERR - " + response);

		// Quit
		pout.println ("QUIT");

		// Flush buffer
		pout.flush();

		// Close socket connection
		mailSock.close();
		}
		catch (IOException ioe)
		{

		}
	}
}
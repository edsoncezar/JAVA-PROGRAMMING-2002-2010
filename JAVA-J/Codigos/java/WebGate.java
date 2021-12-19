package edu.vt.marian.WebGate;

import java.net.*;
import java.io.*;
import java.util.*;

import edu.vt.marian.common.*;
import edu.vt.marian.uip.*;


/** Class name: WebGate
	Class description: this is a module of the MARIAN system, it's responsible to 
		communicate with MARIAN server and CGI to provide a web interface to user
		two major different between this WebGate (in JAVA) and the previous one
		(in C/C++): 1. the new WebGate support multiple distributed MARIAN servers
		and 2. We use user in sted of session so that the query history is 
		metained for long time, also user can set some preferences.  
	Author: Jianxin Zhao
	Finished time: ????, 1998
	Known bugs: none
	Platform: jdk1.1.5 under UNIX
*/

public class WebGate 
{
	/** this is just for debugging
	*/
	static Debug debug = null;

	/** those are used to measure the performance of the system
	*/
/*	public static int WEBGATE_TOTAL_TIME;
	public static int READ_REQUEST_TIME;
	public static int CREATE_RESPONSE_TIME;
	public static int WRITE_RESPONSE_TIME;
	public static int LOG_TIME;
*/   
	/** store the configuration of this module
	*/
	Configuration	config;		

	/** log file manager, may evlove to a database and log analizer in the future	   	
	*/
	log_manager	logm;		

	/** manage rpc calls to and from multiple MARIAN 
		servers
	*/
	uip_manager	uipm;		
	
	/** manage the user database
	*/
	user_manager userm;		
	
	/** responsible for process MARIAN server call back
	*/
	call_back_processor cbp;	

    
	/** empty constructor
	*/
	public WebGate()  
	{
	}


    /** this is the main method of the whole system
	*/
	public static void main (String argv[]) 
	{
		// Create a default Debug that will get things off the ground.
		Debug tempDebug = new Debug("config" + File.separator + "debug.cfg", "trace", false);

		// create various system measurements
/*		WebGate.WEBGATE_TOTAL_TIME = debug.pm.add_measurement("total time spend in WebGate");
		WebGate.READ_REQUEST_TIME = debug.pm.add_measurement("time spend in read out a request from formit");
		WebGate.CREATE_RESPONSE_TIME = debug.pm.add_measurement("time spend in creating a response from a request");
		WebGate.WRITE_RESPONSE_TIME = debug.pm.add_measurement("time spend in writing a response to formit");
		WebGate.LOG_TIME = debug.pm.add_measurement("time spend in logginh a request response pair to file");
*/
		//*CHANGE by RKF 24Oct00:  Command line argument now specifies config file, rather than
		//  only port.  That way we can run several WebGates from the same directory.
		String ConfigFilename;
		if (argv.length != 0) 
		{
			ConfigFilename = argv[0];
		}
		else
		{
			ConfigFilename = new String("config" + File.separator + "webgate.cfg");
		}
		// load configuration object
		Configuration config = new Configuration(ConfigFilename, tempDebug); 

		// Now create the real Debug for this server.
		String DebugConfigFilename = config.get_binding("debug_config");
		String DebugTraceFilename = config.get_binding("debug_trace");
		debug = new Debug(DebugConfigFilename, DebugTraceFilename, false);
		
		// create the user manager object to upload our user database
		user_manager userm = new user_manager(config.get_dir("user_manager"), debug);

		// create a super user who has the right to manage the system
		//**NOTE:  This is effectively a back door.  Perhaps it should be removed?
		user u = userm.get_user_by_name("worker");
		if (u == null)
		{
			u = userm.new_user("worker", "Uh... Clem", "Dr.Memory");
			if (u == null)
			{
				debug.dumpTrace("creating super user failed");
			}
		}
		u.set_priority("super user");
		
		// create the MARIAN server call back processor
		webgate_call_back_processor cbp = new webgate_call_back_processor("webgate call back processor",
			userm, debug);

		// create uip manager object
		uip_manager uipm = new uip_manager(config.get_dir("uip_manager"), cbp, debug);

		// let call back processor know uip manager
		cbp.set_uip_manager(uipm);

		// create a log manager object to record request response pairs for us
		log_manager logm = new log_manager(config.get_dir("log_manager"), debug);
		
		try 
		{	
			// listen for connections on specified port using a ServerSocket 
			// instance
			ServerSocket connection = new ServerSocket(config.get_port(), 100);
						
			// multithread implemented here
			while (! logm.get_stop_flag()) 
			{
				// we got a request from CGI, create a thread to handle it 
				request_response thread = new request_response(connection.accept(), 
					uipm, userm, logm, debug);
				thread.start();
			}

			// first dump all the measurement result to a file
			//**REMOVED by RKF for non-measurement version:  debug.ts.dump_final_results("final", false);

			// super user request to stop the system, gracefully store information
			// and exit
			logm.exit("0");
			uipm.exit("0");
			userm.exit("0");
		
		}
		catch (IOException e) 
		{
			debug.dumpTrace("Could not bind to port " + config.get_dir("Port"));
		}
	}
}

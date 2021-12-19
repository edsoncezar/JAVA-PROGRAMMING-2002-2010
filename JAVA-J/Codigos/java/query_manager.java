package edu.vt.marian.WebGate;

import java.io.*;
import java.net.*;
import java.util.*;
import edu.vt.marian.common.*;




/** Class name: query_manager
	Class description: this class manages a buntch of queries for a user, it can
		envolve to a database in the future, but that's hided to outside, same 
		service will still be available after that.
	Author: Jianxin Zhao
	Finished time: ????, 1998
	Known bugs: none
	Platform: jdk1.1.5 under UNIX
*/

public class query_manager
{
	/** this is just for debuging
	*/
	Debug debug;

	/** currently, we use a vector to store queries performed by a user
	*/
	private Vector queries;

	/** this the the directory from which this object is created
	*/
	private String query_manager_dir;


	/** this constructor will create a query manager object, the onject is empty
		upon creation
	*/
	public query_manager(Debug debug)
	{
		// first initialize data members
		init();

		this.debug = debug;
	}


	/** this constructor will create a query manager object from the specified 
		directory, the directory probably contains all the query information
		about a user
	*/
	public query_manager(String dir, Debug debug)
	{
		// first initialize data members
		init();

		this.debug = debug;
		query_manager_dir = new String(dir);

		File file = new File(dir);
		if (! file.exists())
		{
			// this is the first time this object is created, do nothing
			// except create the directory
			try
			{
				file.mkdir();
			}
			catch (SecurityException e)
			{
				debug.dumpTrace("class query_manager: can not create directory");
			}

			return;
		}

		
		// there is something in the directory, probably queries created before
		// read them out
		File queries_config = new File(dir + File.separator + "index");
		BufferedReader br;
		
		try
		{
			FileReader fr = new FileReader(queries_config);
			br = new BufferedReader(fr);

			// first read out number of queries 
			int number_queries = Integer.parseInt(br.readLine());

			// then read out each query
			int i;
			query q;
			for (i = 0; i < number_queries; i++)
			{
				// read out the file name of this query
				q = new query(dir + File.separator + br.readLine(), debug); 
				queries.addElement(q);
			}
			br.close();
		}
		catch (IOException e3) 
		{
			debug.dumpTrace("error reading data from " +  queries_config.getAbsolutePath());
		}
	}


	/** this constructor will create a query manager object from a stream, this might
		be helpful for rmote manage in the future. (not implemented yet)
	*/
	public query_manager(DataInputStream dis, Debug debug)
	{
	}


	/** this method will add a new query with the specified query data to the
		query data base
	*/
	public synchronized String submit_query(query_data qd)
	{
		if (qd == null)
		{
			// we don't handle null query data
			return null;
		}

		// currently, we use index as query number, but this may be changed
		// in the future
		query q = new query(Integer.toString(queries.size()), qd, 
			query_manager_dir + File.separator + Integer.toString(queries.size()), 
			debug);
		queries.addElement(q);
		return q.get_number();
	}
	
	
	/**
	 * This method will add an old query to the query database.
	 * (Implemented by Richard Baker on 4/25/2000)
	 */
	public String add_query(query q)
	{
		queries.addElement(q);
		return "OK";
	}


	/** this method will return the total number of queries in this object
	*/
	public String get_number_queries()
	{
		return Integer.toString(queries.size());
	}


	/** this method will return the number of queries the submitted before
		the specified time. (not implemented yet)
	*/
	public String get_number_queries_before(String time)
	{
		return null;  // not implemented yet
	}


	/** this method will return the number of queries the submitted after
		the specified time. (not implemented yet)
	*/
	public String get_number_queries_after(String time)
	{
		return null;  // not implemented yet
	}


	/** this method will return the number of queries the submitted from
		the start time to the end time. (not implemented yet)
	*/
	public String get_number_queries_between(String start_time, String end_time)
	{
		return null;  // not implemented yet
	}


	/** this method will return the number of queries in this object which
		are matched with the specified query data. (not implemented yet)
	*/
	public String get_number_queries(general_query_data gqd)
	{
		return null;  // not implemented yet
	}


	/** this method will return a query of this object, the query is identified
		by the parameter query_number, we use string for it since this allow
		the queries performed by a user exceed the integer limit (more than 2
		billion in java I believe), though this is unlikely.
	*/
	public query get_query(String query_number)
	{
		int i;
		query q;
		for (i = 0; i < queries.size(); i++)
		{
			q = (query) queries.elementAt(i);
			if (q.get_number().equals(query_number))
			{
				// we found a match
				return q;
			}
		}

		// no match was found
		return null;
	}

	
	/** this method will return all the queries the submitted before the
		specified time. (not implemented yet)
	*/
	public Vector get_queries_before(String time)
	{
		return null;  // not implemented yet
	}


	/** this method will return all the queries submitted from the
		specified time to now. (not implemented yet)
	*/
	public Vector get_queries_after(String time)
	{
		return null;  // not implemented yet
	}


	/** this method will return all the queries submitted from the
		start time to the end time. (not implemented yet)
	*/
	public Vector get_queries_between(String start_time, String end_time)
	{
		return null;  // not implemented yet
	}


	/**
	 * This method will return the Vector of queries.
	 */
	public Vector get_queries()
	{
		return queries;
	}
	
	
	/** this method will return all the queries in this object which are 
		matched with the specified query data. (not implemented yet)
	*/
	public Vector get_queries(general_query_data gqd)
	{
		return null;  // not implemented yet
	}


	/** this method will delete the query with the specified query number
		from this object. (implemented by Richard Baker on 4/25/2000)
	*/
	public String delete_query(String query_number)
	{
		query q;
		my_directory md;		
		if (query_number != null)
		{			for (int i = 0; i < queries.size(); i++)			{
				q = (query)queries.elementAt(i);				if (q.get_number().equals(query_number))				{	
					md = new my_directory(q.get_dir(), debug);	
					md.delete();					queries.removeElementAt(i);					queries.trimToSize();
					return "OK";				}			}
		}				return null;
	}

	
	/** this method will delete all the queries from this object which are
		matched with the specified query data. (not implemented yet)
	*/
	public String delete_queries(general_query_data gqd)
	{
		return null;  // not implemented yet
	}


	/** this method will unload those queries which have not been accessed for the 
		specified time
	*/
	public String unload(long time)
	{
		int i;
		query q;

		for (i = 0; i < queries.size(); i++)
		{
			q = (query) queries.elementAt(i);
			q.unload(time);
		}

		return "ok";
	}


	/** this method will print the content of this object to a stream
		this might be useful for remote management in the future.
		(not implemented yet)
	*/
	public String to_stream(DataOutputStream dos)
	{
		return null;  // not implemented yet
	}


	/** this method will save this object to the specified directory
	*/
	public String save(String dir)
	{
		// create the directory
		File file = new File(dir);
		try
		{
			file.mkdir();
		}
		catch (SecurityException e)
		{
			debug.dumpTrace("class query_manager: can not create directory");
		}

		// write query index file
		FileOutputStream fos = null;
        try
        {
			fos = new FileOutputStream(dir + File.separator + "index", false);             
        }
        catch (IOException e3)
        {
			debug.dumpTrace("error opening query index file to write");
        }
		PrintWriter pw = new PrintWriter(fos);
        
		// write number of queries
		pw.println(queries.size());

		// write each query
		int i;
		query q;
		for (i = 0; i < queries.size(); i++) 
		{ 
			q = (query) queries.elementAt(i);

			// write the file name of this query to index file
			pw.println(q.get_number());

			// save this query to the file
			q.save(dir + File.separator + q.get_number());
		}
		pw.flush();
		pw.close();
		return "OK";
	}


	/** this method will initialize the data members of this object
	*/
	private void init()
	{
		debug = null;
		queries = new Vector();
		query_manager_dir = null;
	}
}
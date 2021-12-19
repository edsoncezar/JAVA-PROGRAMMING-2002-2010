package edu.vt.marian.common;

import java.io.*;
import java.net.*;
import java.util.*;

import edu.vt.marian.uip.rpc_function;


/**
 *	A Information Description is a description of a digital information object
 *		in context.  In MARIAN representation terms, we will understand this
 *		as a node within a web of links.
 *	<P>
 *	Specifically, an InfoDesc is composed of a node description plus any number
 *		of link descriptions.  For the purposes of Java, we are declaring
 *		both node and link descriptions to be of class Object, so that
 *		several disparate types of descriptions can be accomodated.  Only
 *		certain kinds of Objects will actually do in either case, whence the
 *		if-else if-else statements in setNodeDesc() and addLinkDesc().  These
 *		statements are deliberately designed to be extensible, so that other
 *		description types can be added in future.
 *	<P>
 *	The function of a node description is to describe a (subset) of nodes within
 *		some class.  Any class of nodes can define one or more <I>matching
 *		functions</I>.  In addition, all node classes support match by classID,
 *		(including subclass ID) and by exact FullID.  Most also support match
 *		by exact object.  Any further match is to be specified by an
 *		encoded function, which the class manager will apply to its collection
 *		of instances.
 *	<P>
 *	At the moment, we are only allowing a link description to consist of an exact
 *		description of a single link.  Future implementations may allow link
 *		variables and link operators.  A single link is fully described by
 *		specifying its type and describing the nodes at source end and sink
 *		end.  In the current context, we know the description of the node at
 *		one end (the "target" end):  that is the node described in the node
 *		portion of this InfoDesc.  The node at the other end (the "key" end)
 *		is described by a further InfoDesc.  Thus each member of the linkDesc
 *		Vector is composed of three elements:
 *		<UL><LI>	linkClassID -- the class of the link traversed
 *		</LI><LI>	direction -- whether the target is source or sink
 *		</LI><LI>	keyDesc -- the description of the key node
 *		</LI></UL>
 *
 *	@author	Robert France.
 *	@see	LinkDesc
 *	@see	ClassManager
 */
public class InfoDesc
{
	/** Just for debugging	*/
	protected Debug debug;

	/* Node description.	*/
	protected Object nodeDesc;

	/* Link descriptions.	*/
	protected Vector linkDescs;

	/** return values for methods of this class	*/
	public final static int OK = 0;
	public final static int BAD_PARAMS = -2;


	/**
		Create a new null InfoDesc.
		@param	d	the eternal Debug object.
	*/
	public InfoDesc(Debug d)
	{
		nodeDesc = null;
		linkDescs = new Vector();
		debug = d;
	}

	/**
		Create an InfoDesc from a specific nodeDesc.
		@param	node	A node description:  either classID, FullID, or Function.
		@param	d	the eternal Debug object.
	*/
	public InfoDesc(Object node, Debug d)
	{
		if ( setNodeDesc(node) != OK )
			nodeDesc = null;
		linkDescs = new Vector();
		debug = d;
	}
		
	/**
		Create an InfoDesc from a specific nodeDesc.
		@param	node	A node description:  either ClassID, FullID, or Function.
		@param	d	the eternal Debug object.
	*/
	public InfoDesc(Object node, Vector links, Debug d)
	{
		if ( setNodeDesc(node) != OK )
			nodeDesc = null;
		linkDescs = new Vector();
		if ( links == null )
		{
			debug.dumpTrace("InfoDesc [constructor 3]:  null links vector.");
		}
		else
		{
			Enumeration e = links.elements();
			while ( e.hasMoreElements() )
			{
				addLinkDesc( e.nextElement() );	// Ignore failures.
			}
		}
		debug = d;
	}
		
	/**
		Either the nodeDesc or the linkDescs (or both) must be valid,
		although either can also be null (but not both!).
	*/
	public boolean isValid()
	{
		return( (nodeDesc != null) || (linkDescs.size() > 0) );
	}


	/**
		Set the node description part.
		@param	node	A node description:  either ClassID, FullID, or Function.
	*/
	public int setNodeDesc(Object node)
	{
		if ( node instanceof ClassID )
		{
			nodeDesc = node;
			return( OK );
		}
		else if ( node instanceof FullID )
		{
			nodeDesc = node;
			return( OK );
		}
		else if ( node instanceof String )
		{
			nodeDesc = node;
			return( OK );
		}
		else if ( node instanceof rpc_function )	//**DEVEL:  Someday
		{						//  consider creating
			nodeDesc = node;			//  a more general function
			return( OK );				//  type.  Also consider
		}						//  using java.lang.Method.
		else
		{
			debug.dumpTrace("InfoDesc.setNodeDesc():  cannot handle object " +
				node.toString() + ":  only ClassIDs, FullIDs or Functions currently accepted.");
			return( BAD_PARAMS );
		}
	}


	/**
		Get the node description part.
	*/
	public Object getNodeDesc()
	{
		return( nodeDesc );
	}


	/**
		Add another link description to the set of links.
		@param	link	A link description:  currently, only LinkDesc allowed.
	*/
	public int addLinkDesc(Object ld)
	{
		if ( ld instanceof LinkDesc )
		{
			linkDescs.addElement(ld);
			return( OK );
		}
		else
		{
			debug.dumpTrace("InfoDesc.addLinkDesc():  cannot handle object " +
				ld.toString() + ":  only LinkDescs currently accepted.");
			return( BAD_PARAMS );
		}
	}

	public int numLinkDescs()
	{
		return( linkDescs.size() );
	}

	/**
		Return an enumeration through the link description set.
	*/
	public Enumeration enumLinkDescs()
	{
		return( linkDescs.elements() );
	}
}

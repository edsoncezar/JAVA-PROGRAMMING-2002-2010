package edu.vt.marian.common;

import java.io.*;
import java.net.*;
import java.util.*;


/**
  *	At the moment, we are only allowing a link description to consist of an exact
 *		description of a single link.  Future implementations may allow link
 *		variables and link operators.  A single link is fully described by
 *		specifying its type and describing the nodes at source end and sink
 *		end.  In the current context, we know the description of the node at
 *		one end (the "target" end):  that is the node described in the node
 *		portion of some InfoDesc.  The node at the other end (the "key" end)
 *		is described by a further InfoDesc.  Thus each member of the linkDesc
 *		Vector is composed of three elements:
 *		<UL><LI>	linkClassID -- the class of the link traversed
 *		</LI><LI>	direction -- whether the target is source or sink
 *		</LI><LI>	keyDesc -- the description of the key node
 *		</LI></UL>
 *
 *	@author	Robert France.
 *	@see	InfoDesc
 */
public class LinkDesc
{
	/** Just for debugging	*/
	protected Debug debug;

	/* Link class:  note can be a superclass of disjoint subclassed.	*/
	protected int classID;

	/* Direction:  is the (missing) target node the source or the sink?	*/
	protected int direction = INVALID_DIRECTION;

	/* "Key" node description.	*/
	protected InfoDesc keyDesc;

	/** The possible directions.	*/
	public final static int INVALID_DIRECTION = 0;
	public final static int SOURCE_TO_SINK = 1;	// Target is source; key sink.
	public final static int SINK_TO_SOURCE = 2;	// Target is sink; key source.
	public final static int ANY_DIRECTION = 3;

	/**
		Create a new null LinkDesc.
		@param	d -- the eternal Debug object.
	*/
	public LinkDesc(Debug d)
	{
		debug = d;
		classID = ClassIDs.CLASS_ABSURD;
	}

	/**
		Create a new LinkDesc from full specifications.
		@param	linkClID	(possibly super-) class for matching links.
		@param	dir	direction for links to run.
		@param	key	InfoDesc for node at other end of link.
		@param	d	the eternal Debug object.
	*/
	public LinkDesc(int linkClID, int dir, InfoDesc key, Debug d)
	{
		classID = linkClID;
		direction = dir;
		keyDesc = key;
		debug = d;
	}
		
	public boolean isValid()
	{
		// Note that null is not a valid keyDesc, although a keyDesc with
		//  null nodeDesc is valid.
		return( (classID != ClassIDs.CLASS_ABSURD) && (direction > 0)  &&
			(direction <= 3) && (keyDesc != null) );
	}

	/**
	 *  A little utility.
	 */
	public static boolean isValidDirection(int dir)
	{
		return( (dir > 0)  && (dir <= 3) );
	}


	public void setClassID(int linkClID)
	{
		classID = linkClID;
	}

	public int getClassID()
	{
		return( classID );
	}

	public void setDirection(int dir)
	{
		direction = dir;
	}

	public int getDirection()
	{
		return( direction );
	}
	public void setKeyDesc(InfoDesc key)
	{
		keyDesc = key;
	}

	public InfoDesc getKeyDesc()
	{
		return( keyDesc );
	}


}

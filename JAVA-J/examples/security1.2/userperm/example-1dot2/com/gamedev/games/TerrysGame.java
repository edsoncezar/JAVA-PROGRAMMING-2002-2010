package com.gamedev.games;

import java.io.*;
import java.security.*;
import java.util.Hashtable;
import com.scoredev.scores.*;

public class TerrysGame
{
    public static void main(String args[])
	throws Exception 
    {
	HighScore hs = new HighScore("TerrysGame");

	if (args.length == 0)
	    usage();

	if (args[0].equals("set")) {
	    hs.setHighScore(Integer.parseInt(args[1]));
	} else if (args[0].equals("get")) {
	    System.out.println("score = "+ hs.getHighScore());
	} else {
	    usage();
	}
    }

    public static void usage()
    {
	System.out.println("TerrysGame get");
	System.out.println("TerrysGame set <score>");
	System.exit(1);
    }
}

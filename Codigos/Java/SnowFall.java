/*
SnowFall
Visual Mining, Inc.

This program demonstrates the use of the Netcharts API to create,
populate and display a Netcharts chart.

The program demonstrates the use of a common model for integrating
charts into applications.  It factors the chart definition into two
components - a static component and a dynamic component.

The static component, often referred to as the chart template, contains 
that part of the chart definition which does not change from instance 
to instance.  It includes attributes such as chart style, layout, colors, 
fonts and sizes.  Chart templates can be quickly created with Visual Mining's
Chart Designer, a graphical desktop tool that allows point-and-click creation 
of chart definitions.  Chart templates can also be created manually.
Chart Templates can be stored in separate disk files, or inlined as
string variables. 

The dynamic component of a chart definition contains the attributes which
change between invocations.  Typically this is the actual data to be plotted,
axis bounds and titles.   This data is fetched or computed at runtime and
added to the chart through the set() api. 

The program can be compiled with a command similar to:
d:\jdk1.3\bin\javac -classpath "d:\Program Files\Visual Mining\NetCharts 3.7.1\NetCharts\classes;." SnowFall.java

The program can be run with a command similar to:
d:\jdk1.3\bin\java -classpath "d:\Program Files\Visual Mining\NetCharts 3.7.1\NetCharts\classes;." SnowFall
*/

import java.io.*;
import java.awt.*;
import java.util.*;
import netcharts.apps.*;
import netcharts.util.*;
import netcharts.graphics.*;
import java.util.Vector;
import javax.swing.*;

public class SnowFall { 

	static {
	    NFLicense2.setKey("NetCharts3.7 EXPIRATION=31-AUG-2001 KEY=AYYYKHGJGXBZFHCNCHKXDXHMBBHBYDEY");
	    // uncomment to activate debug output
	     NFDebug.set("ALL");	    
  	}

	static String chartTemplate = 
		"ChartName       = \"Snowfall Probability Chart\";"+
		"Background      = (white,NONE,4,null,TILE,black);" +
		"GraphType       = STACK;"+
		"GraphLayout     = HORIZONTAL;"+
		"AntiAlias       = ON;"+
		"BarWidth        = 61;"+
		"Bar3DDepth      = 0;"+
		"Header          = (\"Snow Fall in US Cities\nProbabilities vs. Actuals\n 2000\",black,\"Helvetica\",16,0);"+
		"HeaderBox       = (null,NONE,5,null,TILE,black);"+
		"DataAxis        = (BOTTOM,LEFT),(BOTTOM,LEFT);"+
		"Grid            = (gainsboro,white,white,null,TILE);"+
		"Legend          = (\"Probability\",black,\"Helvetica\",10,0);"+
		"LeftScale       = (null,null,null);"+
		"LeftTics        = (\"ON\",black,\"Helvetica\",12,0,null);"+
		"LeftColor       = white;"+
		"BottomTics      = (\"ON\",grey,\"Helvetica\",10,0,null);"+
		"BottomScale     = (0,100.5,10);"+
		"BottomAxisTitle = (\"Accumulated Inches of Snow\",dimgrey,\"Helvetica\",12,0);"+
		"BottomColor     = grey;"+
		"LineStyle       = (NONE,1,blue,null);"+
		"LineSymbol      = (TRIANGLEDOWN,12,BOTH,black,0,null);"+
		"DataSets        = (\"-2 std deviation\",x008b87,BAR,4,FILLED),(\"+-1 std deviation\",x99cccc,BAR,4,FILLED),(\"+2 std deviation\",xe2ebf0,BAR,4,FILLED);"+
		"LineSets        = (\"Actual Snowfall\",white,CIRCLE,4,FILLED,NONE,1,null,null);"+
		"LineLabels1     = (\"? inches\",null,null),(\"? inches\",null,null),(\"?inches\",null,null),(\"? inches\",null,null);"+
		"DwellLabel      = (,black,\"Helvetica\",12,0);";
	
public void go(){

	netcharts.apps.JComboChartApp chart;

	JFrame f;
	int chartWidth = 600;
	int chartHeight = 350;

	try {
		// create an initially empty ComboChart Applet
		chart = new netcharts.apps.JComboChartApp();
   
		chart.init();
		chart.start();

		// there are 6 different signatures for NFChart.set().  
		// See the API documenation on NFChart.set() for details 
		// and examples of each signature.  This program demonstrates 
		// the use of a variety of the set() methods as it loads the
		// dynamic portions of the chart definition into the bean.
		
		// load the static component of the chart (the chart template)
		// into the combo chart.  This chart template could be read
		// in from a disk file or from a string variable as show here.
		chart.set(chartTemplate);
				
		// Set the width and height of the chart
		chart.set("ChartWidth" , new Integer(chartWidth));
		chart.set("ChartHeight", new Integer(chartHeight));
		
		// At this point the program would likely go off and
		// fetch the dyanmic portions of the chart definition.
		// For this example we'll hardcode that data into
		// arrays to simulate returned data.
		String[] barLabelData = {"Raleigh, NC","Washinton, DC","Philadelphia, PA","Buffalo, NY"};
		int[] minus2devData = {15,30,35,55};
		int[] plusminus1devData = {40,35,25,30};
		int[] plus2devData = {45,35,40,15};
		int[] snowfallData = {10,30,75,50};

		// load the bottom labels into the chart from a vector
		Vector barLabels = new Vector();
		for (int i=0; i<barLabelData.length; i++) {
			barLabels.addElement(barLabelData[i]);
		}
		chart.set("BarLabels", barLabels);
						
		// load DataSet1 using a vector or values
		Vector minus2dev = new Vector();
		for (int i=0; i<minus2devData.length; i++) {
			minus2dev.addElement(new Integer(minus2devData[i]));
		}
		chart.set("DataSet1", minus2dev);

		// load DataSet2 using a vector or values
		Vector plusminus1dev = new Vector();
		for (int i=0; i<plusminus1devData.length; i++) {
			plusminus1dev.addElement(new Integer(plusminus1devData[i]));
		}
		chart.set("DataSet2", plusminus1dev);

		// load DataSet3 using a vector or values
		Vector plus2dev = new Vector();
		for (int i=0; i<plus2devData.length; i++) {
			plus2dev.addElement(new Integer(plus2devData[i]));
		}
		chart.set("DataSet3", plus2dev);
		
		// Load the actual snowfall data
		// In a combo chart the X coordinates are limited to the 
		// number of bar set groups (in this case 0-3).  
		Vector snowfall = new Vector();
		Vector dataPoint;
		for (int i=0; i<4; i++) {
			dataPoint = new Vector(2);
			dataPoint.addElement(new Double(snowfallData[i]));
			dataPoint.addElement(new Integer(i));
			snowfall.addElement(dataPoint);
		}
		chart.set("LineSet1",snowfall);
		
		// customize pop-up labels for the snowfall amounts
		for (int i=0; i<4; i++) {
			chart.set("LineLabels1",i,0,new Double(snowfallData[i]).toString() + " inches");
		}
		

		// draw the chart
		chart.display();

		// (Debug) print the graph CDL as finally loaded
		System.out.println("Chart CDL:\n");
		System.out.println(chart.getCDL());
		System.out.println("\n\n");
		
		// Create the Frame that will contain the chart
		f = new JFrame("Visual Mining ComboChart Example");
		f.setSize(chartWidth,chartHeight);

		// Add the chart to the Frame.
		f.getContentPane().add("Center", chart.graph);
		f.setVisible(true);
	}	
	catch(Exception e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
	}		
}


public static void main(String[] args)
{
	SnowFall sf = new SnowFall();
	sf.go();
}
}

/*
NCBSimpleExample
Visual Mining, Inc.

This program demonstrates the use of the Netcharts API to create,
populate and display a Netcharts bean.

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
added to the chart through the NetCharts set() api. 

The program can be compiled with a command similar to:
d:\jdk1.3\bin\javac -classpath "d:\NetChartsBeans.jar;." NCBSimpleExample.java

The program can be run with a command similar to:
d:\jdk1.3\bin\java -classpath "d:\NetChartsBeans.jar;." NCBSimpleExample
*/
package ncbexamples;

import java.io.*;
import java.awt.*;
import java.util.*;
import netcharts.util.*;
import netcharts.graphics.NFLicense2;
import chartworks.cb.beans.*;

public class NCBSimpleExample { 

	static {
		// Temporary license keys to be replaced with your valid license keys
	        netcharts.graphics.NFLicense2.setKey("NetChartsBeans4.0 EXPIRATION=31-DEC-2002 KEY=XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	        netcharts.graphics.NFLicense2.setKey("NetCharts4.0 EXPIRATION=31-DEC-2002 BANNER=NO KEY=XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

		// set the application mode.
		chartworks.cb.editors.NFEditor.setBeanContainer(chartworks.cb.editors.NFEditor.USERAPP);

	        // uncomment to activate debug output
		//NFDebug.set("ALL");	    
  	}

	private String chartTemplate = 
		"ChartName       = \"NetCharts Beans Example1\";"+
		"Background      = (white,NONE,4,null,TILE,black);" +
		"AntiAlias       = ON;"+
		"BarWidth        = 61;"+
		"Bar3DDepth      = 0;"+
		"Header          = (\" \",black,\"Helvetica\",16,0);"+
		"HeaderBox       = (null,NONE,5,null,TILE,black);"+
		"DataAxis        = (BOTTOM,LEFT);"+
		"Grid            = (gainsboro,white,white,null,TILE);"+
		"Legend          = (\"Support Level   \",black,\"Helvetica\",10,0);"+
		"LegendLayout    = (HORIZONTAL,BOTTOM,0,0,TOPLEFT,-1);"+
		"LegendBox       = (,BOX,1,,TILE,grey);"+
		"RightTitle       = (\"       \",black,\"Helvetica\",12,0);"+
		"LeftTitle       = (\"Number\n of \nCalls\n (000's) \",black,\"Helvetica\",12,0);"+
		"LeftTitleBox    = (null,NONE,0,null,TILE,black);"+
		"LeftScale       = (null,null,null);"+
		"LeftTics        = (\"ON\",grey,\"Helvetica\",10,0,null);"+
		"LeftColor       = grey;"+
		"BottomTics      = (\"ON\",grey,\"Helvetica\",10,0,null);"+
		"BottomColor     = grey;"+
		"DataSets        = (\"Evaluation\",x008b87,BAR,4,FILLED),(\"Standard\",x99cccc,BAR,4,FILLED),(\"Platinum\",xe2ebf0,BAR,4,FILLED);"+
		"DwellLabel      = (,black,\"Helvetica\",12,0);";
	
public void go(){

	NFBarchart chart;

	int chartWidth = 600;
	int chartHeight = 350;

	try {
		// create a new Barchart bean with the given chart template
		chart = new NFBarchart(chartTemplate);
		
		// there are 6 different signatures for NFChart.set().  
		// See the API documenation on NFChart.set() for details 
		// and examples of each signature.  This program demonstrates 
		// the use of a variety of the set() methods as it loads the
		// dynamic portions of the chart definition into the bean.
		
		// Set the width and height of the chart
		chart.set("ChartWidth" , new Integer(chartWidth));
		chart.set("ChartHeight", new Integer(chartHeight));
		
		// At this point the program would likely go off and
		// fetch the dynamic portions of the chart definition.
		// For this example we'll hardcode that data into
		// arrays to simulate data returned from some source
		String titleData = "Eastern Region Call Centers";
		String titleData2 = "Q2 Call Volume by Type";
		String[] barLabelData = {"Atlanta","Washington","Philadelphia","Boston"};
		int[] level1Data = {15,30,35,55};  
		int[] level2Data = {40,35,25,30};
		int[] level3Data = {45,35,40,15};

		// load the bottom labels into the chart from a vector
		Vector barLabels = new Vector();
		for (int i=0; i<barLabelData.length; i++) {
			barLabels.addElement(barLabelData[i]);
		}
		chart.set("BarLabels", barLabels);
						
		// load level 1 support data bar values
		Vector level1 = new Vector();
		for (int i=0; i<level1Data.length; i++) {
			level1.addElement(new Integer(level1Data[i]));
		}
		chart.set("DataSet1", level1);

		// load level2 support data bar values
		Vector level2 = new Vector();
		for (int i=0; i<level2Data.length; i++) {
			level2.addElement(new Integer(level2Data[i]));
		}
		chart.set("DataSet2", level2);

		// load level3 support data bar values
		Vector level3 = new Vector();
		for (int i=0; i<level3Data.length; i++) {
			level3.addElement(new Integer(level3Data[i]));
		}
		chart.set("DataSet3", level3);
				
		// customize title for chart
		chart.set("Header",0,titleData + "\n" + titleData2);
		
		// draw the chart
		chart.display();

		// (Debug) print the graph CDL as finally loaded
		System.out.println("Chart CDL:\n");
		System.out.println(chart.getCDL());
		System.out.println("\n\n");
		
		// Create the Frame that will contain the chart
		Frame f;
		f = new Frame("Visual Mining Barchart Bean Example");
		f.setSize(chartWidth,chartHeight);
		f.add(chart);
		f.setVisible(true);
	}	
	catch(Exception e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
	}		
}

public static void main(String[] args)
{
	NCBSimpleExample example = new NCBSimpleExample();
	example.go();
}
}

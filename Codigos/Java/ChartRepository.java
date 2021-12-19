package ncbexamples.drilldown;

/*
ChartRepository
Visual Mining, Inc.

This ChartRepository simulates a repository to store and fetch
Chart Templates.  In a production environment, the getChartTemplate
call would probably connect to an external data source to retrieve
the chart template.  This data source could be a database, a file
system, a web service, etc.
*/

public class ChartRepository
{
  public ChartRepository()
  {
  }

  public String getChartTemplate()
  {
    String chartTemplate =
      "ChartType         = BARCHART;" +
      "GraphType         = STACK;" +
      "ChartWidth        = 500;" +
      "ChartHeight       = 275;" +
      "StackLabel        = TOTAL;" +
      "Background        = (xe3edf7,BOX,1,null,TILE,black);" +
      "Bar3DDepth        = 3;" +
      "BarWidth          = 40;" +
      "BarBorder         = (NONE,1,black);" +
      "AntiAlias         = \"ON\";" +
      "DataAxis          = (BOTTOM,LEFT),(BOTTOM,LEFT);" +
      "DwellLabel        = (\"ON\", black, SansSerif, 10);" +
      "DwellBox          = (white, SHADOW, 3);" +
      "Header 	         = (\"Acme Widgets Sales Trends\n\", black, SansSerif, 14);" +
      "HeaderBox         = (null,NONE,2,null,TILE,black);" +
      "BottomTics        = (\"ON\", x666666, \"SansSerif\", 10);" +
      "LeftTics          = (\"ON\", x666666, \"SansSerif\", 10);" +
      "LeftTitle         = (\"Sales, $K\",black,\"SansSerif\",11,90);" +
      "LeftFormat        = (INTEGER,\"$%,6.0f\",,);" +
      "LeftColor         = black;" +
      "ActiveClicks      = 1;" +
      "Grid              = (xf0f0f0,white,slategrey,,TILE),(white,null,,null);" +
      "GridLine          = (HORIZONTAL,BAR,1),(HORIZONTAL,SOLID,1);" +
      "Grid3DDepth       = 3;" +
      "RightAxisTitle    = (\"                   \", x333333, SansSerif, 14);" +
      "TopAxisTitle      = (\"Sales by Year\", x333333, SansSerif, 12);" +
      "Footer            = (\"Go Back\",white,\"SansSerif\",8,0);" +
      "FooterActiveLabel = (\"\",\"\",);" +
      "FooterBox         = (grey,RAISED,1,,CENTER,black);" +
      "BarFillPattern    = (GRADIENTVERTICAL, xF4F0DE, xDACE98);" +
      "DataSets          = (\"Year\",xDACE98,BAR,4,FILLED);";

    return chartTemplate;
  }
}
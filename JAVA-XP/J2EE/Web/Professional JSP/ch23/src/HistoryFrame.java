import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.JFrame;

import org.w3c.dom.*;

public class HistoryFrame extends JFrame {

  private Element root;

  public HistoryFrame(Element root) {
    this.root = root;
  }

  public void paint(Graphics g) {
 
    // cast to a Graphics2D object and get size
    Graphics2D g2 = (Graphics2D) g;
    Dimension dim = this.getSize();
 
    // make sure to fill in the background color
    g2.setColor(Color.white);
    g2.fillRect(0,0,dim.width,dim.height);

    // find the largest y value, to make the scale properly
    double maxClose = findMax(root, "Close");
 
    // calculate the max Y, multiple of 50.
    long yMax = maxClose==0 ? 50 : Math.round((maxClose+25.0)/50.0) * 50;

    // calculate the X offset.
    int numberPeriods = root.getElementsByTagName("Period").getLength();
    double xOffset = numberPeriods<2 ? 0 : (dim.width - 80.0) / (numberPeriods - 1.0);
 
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                        RenderingHints.VALUE_ANTIALIAS_ON);
 
    // draw the x and y axis
    g2.setPaint(Color.black);
    g2.setStroke(new BasicStroke(2.0f));
    g2.draw(new Line2D.Double(40,40, 40,dim.height-60));
    g2.draw(new Line2D.Double(40,dim.height-60, 
                              dim.width-40,dim.height-60));
    
    // draw the y axis scale
    g2.setPaint(Color.lightGray);
    g2.setStroke(new BasicStroke(1.0f));
    for (int i=0; i <= 5; i++) {
      g2.draw(new Line2D.Double(40,dim.height-60-(i*60), 
                                dim.width-40, dim.height-60-(i*60)));
    }

    //draw the title
    g2.setPaint(Color.black);
    String title = "Stock History for: " + root.getAttribute("company");
    Font font = g2.getFont().deriveFont(12.0f);
    g2.setFont(font);
    FontMetrics metrics = g2.getFontMetrics();
    g2.drawString(title,
                  dim.width/2-metrics.stringWidth(title)/2, 20);
 
    //draw the y axis labels
    font = g2.getFont().deriveFont(10.0f);
    g2.setFont(font);
    metrics = g2.getFontMetrics();
    long yDivisions = yMax / 5;
    for (int i = 0; i <= 5; i++) {
      // calculate the x position based on width of label
      String ylabel = String.valueOf(yDivisions*i);
      g2.drawString(ylabel, 
                    35-metrics.stringWidth(ylabel), dim.height-60-(i*60));
    }

    //draw the x axis labels
    NodeList nl = root.getElementsByTagName("Date");
    g2.drawString(getValue(nl.item(0)), 
                  25, dim.height-45);
    g2.drawString(getValue(nl.item(nl.getLength()-1)), 
                  dim.width-60, dim.height-45);

    //draw the graph
    g2.setPaint(Color.red);
    g2.setStroke(new BasicStroke(2.0f));
    GeneralPath path = new GeneralPath(GeneralPath.WIND_EVEN_ODD, numberPeriods);

    // loop through all of the "Close" elements, filling
    // in path, scaling in relation to yMax
    nl = root.getElementsByTagName("Close");
    for (int i = 0; i < nl.getLength(); i++) {
      double yValue = Double.parseDouble(getValue(nl.item(i)));
      if (i == 0) {
        // if first item, do a moveTo, not a lineTo
        path.moveTo(40, Math.round(dim.height-60 - 
                                   ((yValue / yMax) * (dim.height-100))));
      } else {
        path.lineTo(Math.round(i * xOffset)+40,
                    Math.round(dim.height-60 - 
                               ((yValue / yMax) * (dim.height-100))));
      }
    }
    g2.draw(path);
  }

  private double findMax(Element element, String name) {
    double max = 0.0;

    NodeList nl = element.getElementsByTagName(name);

    double current;
    for (int i = 0; i < nl.getLength(); i++) {
      try {
        current = Double.parseDouble(getValue(nl.item(i)));
        if (current > max) {
          max = current;
        }
      } catch (NumberFormatException nfe) {
        System.out.println ("Error converting to double: " + 
                            nl.item(i).getNodeValue());
      }
    }
    return max;
  }

  private String getValue(Node node) {
    node.normalize();
    String value = node.getFirstChild().getNodeValue();
    return value;
  }
}

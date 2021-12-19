package com.macmillan.nmeyers;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * WaterFall: Display a typographic waterfall chart, with and without
 * advanced Graphics2D processing.
 *
 * Usage: WaterFall <font> <style> <min_size> <max_size> <increment>
 *
 * Author: Nathan Meyers, nmeyers@javalinux.net
 * $Id: WaterFall.java,v 1.7 1999/11/07 22:35:20 nathanm Exp $
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * with this program. If not, the license is available from the
 * GNU project, at http://www.gnu.org.
 */

class WaterFall extends JFrame
{
    String fontname, style;
    String testString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    int size1, size2, sizeInc;
    static int frameCount = 0;
    RenderingHints renderingHints;
    WaterFall(String fn, String st, int s1, int s2, int si,
	      RenderingHints hints)
    {
	fontname = fn;
	style = st;
	size1 = s1;
	size2 = s2;
	sizeInc = si;
	renderingHints = hints;
	getContentPane().add(new WaterFallComponent());
	pack();
	setVisible(true);
	incFrameCount();
	addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent ev)
	    {
		dispose();
		decFrameCount();
	    }
	});
    }
    static void incFrameCount()
    {
	frameCount++;
    }
    static void decFrameCount()
    {
	if (--frameCount == 0) System.exit(0);
    }
    private class WaterFallComponent extends JComponent
    {
	WaterFallComponent()
	{
	    setBackground(Color.white);
	    setForeground(Color.black);
	}
	public void paintComponent(Graphics g)
	{
	    final Graphics2D g2 = (Graphics2D)g;
	    int offset = 0;
	    Rectangle bounds = g2.getClipBounds();
	    g2.setColor(getBackground());
	    g2.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
	    g2.setColor(getForeground());
	    if (renderingHints != null) g2.addRenderingHints(renderingHints);
	    if (size1 > 0 && sizeInc > 0)
	    {
		for (int i = size1; i <= size2; i += sizeInc)
		{
		    Font font = Font.decode(fontname + "-" + style + "-" + i);
		    g2.setFont(font);
		    FontMetrics metrics = g2.getFontMetrics();
		    offset += metrics.getAscent();
		    g2.drawString(testString, 0, offset);
		    offset += metrics.getDescent();
		}
	    }
	}
	public Dimension getPreferredSize()
	{
	    Graphics g = getGraphics();
	    FontMetrics m1 = g.getFontMetrics(
		Font.decode(fontname + "-" + style + "-" + size1));
	    FontMetrics m2 = g.getFontMetrics(
		Font.decode(fontname + "-" + style + "-" + size2));
	    int totalAscentPlusDescent =
		(m1.getAscent() + m1.getDescent() +
		 m2.getAscent() + m2.getDescent()) *
		 ((size2 - size1) / sizeInc + 1) / 2;
	    int width = m2.stringWidth(testString);
	    return new Dimension(width, totalAscentPlusDescent);
	}
    }
    public static void main(String[] argv)
    {
	String fontname = "";
	String style = "";
	int size1 = 0, size2 = 0, sizeInc = 0;
	if (argv.length != 5)
	{
	    System.err.println("Usage: WaterFall <font> <style> <min_size> " +
			       "<max_size> <increment>");
	    System.exit(1);
	}
	try
	{
	    fontname = argv[0];
	    style = argv[1];
	    size1 = Integer.parseInt(argv[2]);
	    size2 = Integer.parseInt(argv[3]);
	    sizeInc = Integer.parseInt(argv[4]);
	}
	catch (NumberFormatException e)
	{
	    System.err.println("Usage: WaterFall <font> <min_size> " +
			       "<max_size> <increment>");
	    System.exit(1);
	}

	GraphicsEnvironment.getLocalGraphicsEnvironment().
	    getAvailableFontFamilyNames();
	new WaterFall(fontname, style, size1, size2, sizeInc, null);

	RenderingHints renderingHints = new RenderingHints(
	    RenderingHints.KEY_TEXT_ANTIALIASING,
	    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	renderingHints.put(RenderingHints.KEY_FRACTIONALMETRICS,
			   RenderingHints.VALUE_FRACTIONALMETRICS_ON);

	new WaterFall(fontname, style, size1, size2, sizeInc, renderingHints);
    }
}

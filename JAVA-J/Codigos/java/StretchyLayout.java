
//=======================================================================
// Author:         Jamie Cansdale
// Email:          jamie@obsolete.com
// Date:           19/03/97
// Comments:
//
// Is is important that you initialize StretchyLayout *after* you've
// sized and added your components.  This allows it to work with the
// Visual Cafe compiler.  It doesn't matter if Visual Cafe sets
// the Layout to null before StretchyLayout has been initialized.
//
// StretchyLayout should be set with the following command...
//
// setLayout(new StretchyLayout(this));
//
// If you're using it with a Visual Cafe 'null' layout, this line should
// be after {{INIT_CONTROLS ... }} in your classes initializor.
//
// Feel free to mail me with comments or suggstions... 
//
//=======================================================================
// Update:         Kent L. Smotherman
// Email:          kents@tconl.com
// Date:           September 30, 1997
// Comments:
//
// Fixed StretchyComponent to handle insets properly.
//=======================================================================
// Update:         Kent L. Smotherman
// Email:          kents@tconl.com
// Date:           Octoboer 8, 1997
// Comments:
//
// Updated StretchyComponent to provide automatic font scaling.
// Updated StretchyLayout with API to query the current X/Y scaling
//         relative to the initial design sizes.
//=======================================================================

import java.awt.*;
import java.util.*;

//=================================================================

public class StretchyLayout implements LayoutManager
{
    //The scales represent the scale percentage*10 to avoid using float
    //but retain .1% accuracy
    int scaleX=1000, scaleY=1000;
    Rectangle oldBounds;

    public int getScaleX() {
        return scaleX;
    }
    
    public int getScaleY() {
        return scaleY;
    }

//-----------------------------------------------------------------

	public StretchyLayout(Container cont)
	{
		Component[] components = cont.getComponents();
		oldBounds = cont.getBounds();
		for(int count=0;count<components.length;count++)
		{
			addLayoutComponent(null, components[count]);
		}
	}

//-----------------------------------------------------------------

	Hashtable stretchyComps = new Hashtable();

	public void addLayoutComponent(String name, Component comp)
	{
		StretchyComponent stretchyComp = new StretchyComponent(comp);
		stretchyComps.put(comp, stretchyComp);
	}

//-----------------------------------------------------------------

	public void layoutContainer(Container parent)
	{
		Enumeration enum = stretchyComps.elements();
		Rectangle newBounds = parent.getBounds();
		Font f;
		scaleX = (newBounds.width*1000)/oldBounds.width;
		scaleY = (newBounds.height*1000)/oldBounds.height;
		while(enum.hasMoreElements())
		{
			StretchyComponent stretchyComp = (StretchyComponent)enum.nextElement();
  			stretchyComp.resize(this);
		}
	}

//-----------------------------------------------------------------

	public Dimension minimumLayoutSize(Container parent)
	{
		return parent.size();
	}

//-----------------------------------------------------------------

	public Dimension preferredLayoutSize(Container parent)
	{
		return parent.size();
	}

//-----------------------------------------------------------------

	public void removeLayoutComponent(Component comp)
	{
		stretchyComps.remove(comp);
	}

//-----------------------------------------------------------------

}

//=================================================================

class StretchyComponent
{

//-----------------------------------------------------------------

	Component thisComp;
	Rectangle oldContRect;
	Rectangle oldContBounds;
	Rectangle oldCompRect;
	Insets oldInsets;
    Font    oldFont;
    static Hashtable fonts = new Hashtable();

	StretchyComponent(Component comp)
	{
		thisComp = comp;
		oldInsets = comp.getParent().getInsets();
		oldContRect = comp.getParent().bounds();
		oldCompRect = comp.bounds();
	}

//-----------------------------------------------------------------

	void resize(StretchyLayout sl)
	{
	    if (thisComp == null) return;
	    if (thisComp.getParent() == null) return;
		Insets insets = thisComp.getParent().insets();
		Rectangle newContRect = thisComp.getParent().bounds();

//		Insets insets = thisComp.getParent().insets();
//		Rectangle newContRect = thisComp.getParent().bounds();

		int x = oldCompRect.x - oldInsets.left;
		int y = oldCompRect.y - oldInsets.top;
		int w = oldCompRect.width;
		int h = oldCompRect.height;

		int old_dx = oldContRect.width  - oldInsets.left - oldInsets.right;
		int old_dy = oldContRect.height - oldInsets.top  - oldInsets.bottom;
		int new_dx = newContRect.width  - insets.left - insets.right;
		int new_dy = newContRect.height - insets.top  - insets.bottom;

		x = (x * new_dx) / old_dx;
		y = (y * new_dy) / old_dy;
		w = (w * new_dx) / old_dx;
		h = (h * new_dy) / old_dy;

		x += insets.left;
		y += insets.top;

		thisComp.reshape(x, y, w, h);

		Font f;
		String hash;
		int fontsize;
		int scale,temp,round;
		
        if (oldFont == null)
            oldFont = thisComp.getFont();
        f = oldFont;
        if (sl.scaleX < sl.scaleY)
            scale = sl.scaleX;
        else
            scale = sl.scaleY;
        temp = f.getSize()*scale;
        round = (temp/100)%10;
        fontsize = temp/1000;
        if (round >= 5)
            fontsize++;
        hash = f.getName()+fontsize+f.getStyle();
        if (!fonts.containsKey(hash)) {
            f = new Font(f.getName(),f.getStyle(),fontsize);
            fonts.put(hash,f);
        }
        else
            f = (Font)fonts.get(hash);
        thisComp.setFont(f);
	}

//-----------------------------------------------------------------

}

//=================================================================

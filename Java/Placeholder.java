/*******************************************************************************
*                                                                              *
*  File:        Placeholder.java                        Revision:  1.0         *
*                                                                              *
*  Contents:    a "dummy" applet which acts as a placeholder in HTML documents *
*                                                                              *
*  Creation:    10.09.2000                     Last Modification:  10.09.2000  *
*                                                                              *
*  Platform:    IBM-compatible PC running Windows 98SE                         *
*                                                                              *
*  Environment: Java 1.2                                                       *
*                                                                              *
*  Author:      Andreas Rozek           Phone:  ++49 (711) 6770682             *
*               Kirschblütenweg 15      Fax:    -                              *
*             D-70569 Stuttgart         EMail:  Andreas.Rozek@T-Online.De      *
*               Germany                                                        *
*                                                                              *
*  URL:         http://www.Andreas-Rozek.de/                                   *
*                                                                              *
*  Copyright:   this software is published under the "GNU Public License" (see *
*               "http://www.gnu.org/copyleft/gpl.html" for details)            *
*                                                                              *
*  Comments:    as soon as more than one applet is running within the same     *
*               browser window the "paint" methods of all these applets have   *
*               to be synchronized!                                            *
*                                                                              *
*******************************************************************************/

import java.applet.*;                                    // basic applet classes
import java.awt.*;                           // basic AWT classes (& interfaces)
import java.awt.image.*;                             // image processing classes

public class Placeholder extends Applet {

/*******************************************************************************
*                                                                              *
*                          Non-Public Class Constants                          *
*                                                                              *
*******************************************************************************/

/**** information about author, version and copyright of this applet ****/

  final static String AppletInfo =
    "Placeholder - a \"dummy\" applet which acts as a placeholder in HTML documents\n" +
    "\n" +
    "Andreas Rozek\n" +
    "Kirschblütenweg 15\n" +
    "D-70569 Stuttgart\n" +
    "Germany\n" +
    "\n" +
    "Phone: ++49 (711) 6770682\n" +
    "Fax: -\n" +
    "EMail: Andreas.Rozek@T-Online.De\n" +
    "URL: http://www.Andreas-Rozek.de";

/**** information about foreseen applet parameters ****/

  final static String[][] ParameterInfo = {
    {"Background", "relative URL", "relative URL of a GIF or JPEG file containing a background texture"},
    {"XOffset",    "integer",      "horizontal texture start offset in pixels"},
    {"YOffset",    "integer",      "vertical texture start offset in pixels"}
  };

/*******************************************************************************
*                                                                              *
*                         Non-Public Instance Variables                        *
*                                                                              *
*******************************************************************************/

/**** applet parameters ****/

  Image   Texture;                                         // background texture
  int     XOffset, YOffset;                // horizontal/vertical texture offset

/**** working variables ****/

  boolean TextureAvailable;    // indicates that "Texture" was completely loaded
  int     TextureWidth, TextureHeight;                     // texture dimensions

/*******************************************************************************
*                                                                              *
*                                 Constructor                                  *
*                                                                              *
*******************************************************************************/

  public Placeholder () {
    super();                                              // just to be complete

  /**** initialize internal variables ****/

    Texture = null;
    XOffset = YOffset = 0;

    TextureAvailable = false;                           // no texture loaded yet
    TextureWidth = TextureHeight = 0;
  };

/*******************************************************************************
*                                                                              *
*                           Public Instance Methods                            *
*                                                                              *
*******************************************************************************/

/*******************************************************************************
*                                                                              *
* getAppletInfo       provides information about author, version and copyright *
*                                                                              *
*******************************************************************************/

  public String getAppletInfo () {
    return AppletInfo;               // see above for definition of "AppletInfo"
  };

/*******************************************************************************
*                                                                              *
* getParameterInfo       provides information about foreseen applet parameters *
*                                                                              *
*******************************************************************************/

  public String[][] getParameterInfo () {
    return ParameterInfo;         // see above for definition of "ParameterInfo"
  };

/*******************************************************************************
*                                                                              *
* imageUpdate             gets called while loading an image within the applet *
*                                                                              *
*******************************************************************************/

  public boolean imageUpdate (Image Bitmap, int Flags, int X, int Y, int Width, int Height) {
    if (Flags == ImageObserver.ALLBITS) {
      TextureAvailable = true;

      XOffset = XOffset % Width;  if (XOffset < 0) XOffset += Width;
      YOffset = YOffset % Height; if (YOffset < 0) YOffset += Height;

      TextureWidth  = Width;
      TextureHeight = Height;

    /**** "repaint" applet as texture is now available ****/

      super.imageUpdate(Bitmap, Flags, X,Y, Width,Height);
    };

    return !TextureAvailable;
  };

/*******************************************************************************
*                                                                              *
* init                 initializes an applet each time it's loaded or reloaded *
*                                                                              *
*******************************************************************************/

  public void init () {
    String ParameterValue = getParameter("Background");
    if (ParameterValue != null) {
      ParameterValue = ParameterValue.trim();
      if (ParameterValue.equals("")) {
        System.out.println(
          "Applet \"" + getClass().getName() + "\": missing background image URL"
        );
      } else {
        Texture = getImage(getDocumentBase(), ParameterValue);
        Toolkit.getDefaultToolkit().prepareImage(Texture, -1,-1, this);
      };
    };

    ParameterValue = getParameter("XOffset");
    if (ParameterValue != null) {
      ParameterValue = ParameterValue.trim();
      if (ParameterValue.equals("")) {
        System.out.println(
          "Applet \"" + getClass().getName() + "\": missing \"XOffset\" value"
        );
      } else {
        try {
          XOffset = Integer.parseInt(ParameterValue);
        } catch (NumberFormatException Signal) {
          System.out.println(
            "Applet \"" + getClass().getName() + "\": illegal \"XOffset\" value (" +
            ParameterValue + ")"
          );
        };
      };
    };

    ParameterValue = getParameter("YOffset");
    if (ParameterValue != null) {
      ParameterValue = ParameterValue.trim();
      if (ParameterValue.equals("")) {
        System.out.println(
          "Applet \"" + getClass().getName() + "\": missing \"YOffset\" value"
        );
      } else {
        try {
          YOffset = Integer.parseInt(ParameterValue);
        } catch (NumberFormatException Signal) {
          System.out.println(
            "Applet \"" + getClass().getName() + "\": illegal \"YOffset\" value (" +
            ParameterValue + ")"
          );
        };
      };
    };
  };

/*******************************************************************************
*                                                                              *
* paint                                              (re)draws applet contents *
*                                                                              *
*******************************************************************************/

  public synchronized void paint (Graphics Graf) {
    int Width  = getWidth();
    int Height = getHeight();

  /**** fill background with tiled copies of "Texture" (if available) ****/

    if (TextureAvailable) {
      for (int Y = -YOffset; Y < Height; Y += TextureHeight) {
        for (int X = -XOffset; X < Width; X += TextureWidth) {
          Graf.drawImage(Texture, X,Y, null);
        };
      };
    };
  };
};

/*******************************************************************************
*                                                                              *
*  File:        Rhino_23.java                           Revision:  1.0         *
*                                                                              *
*  Contents:    a class and two interfaces to be instantiated and implemented  *
*               by JavaScript objects                                          *
*                                                                              *
*  Creation:    03.11.2001                     Last Modification:  03.11.2001  *
*                                                                              *
*  Platform:    IBM-compatible PC running Windows 98SE                         *
*                                                                              *
*  Environment: Java 1.3                                                       *
*                                                                              *
*  Author:      Andreas Rozek           Phone:  ++49 (711) 6770682             *
*               Kirschblütenweg 15      Fax:    -                              *
*             D-70569 Stuttgart         EMail:  Andreas.Rozek@T-Online.De      *
*               Germany                                                        *
*                                                                              *
*  URL:         http://www.Andreas-Rozek.de/                                   *
*                                                                              *
*  Copyright:   this software is published under the  "GNU Lesser General Pub- *
*               lic  License"  (see  "http://www.fsf.org/copyleft/lesser.html" *
*               for additional information)                                    *
*                                                                              *
*  Comments:    (none)                                                         *
*                                                                              *
*******************************************************************************/

import org.mozilla.javascript.*;                   // Rhino distribution classes

public class Rhino_23 {
  public int a = 1;                                   // just any local variable

  public void printA () {
    System.out.println("  a = " + a);
  };


  public static interface Interface_1 {
    public void printB();
  };

  public static interface Interface_2 {
    public void printC();
  };
};

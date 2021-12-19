/*******************************************************************************
*                                                                              *
*  File:        NullValueException.java                 Revision:  1.0         *
*                                                                              *
*  Purpose:     indicates the occurance of a "null" value, e.g., in an expres- *
*               sion or elsewhere during script execution                      *
*                                                                              *
*  Creation:    02.09.2001                     Last Modification:  02.09.2001  *
*                                                                              *
*  Platform:    IBM-compatible PC running Windows 98SE                         *
*                                                                              *
*  Environment: Java 1.3, Rhino 1.5                                            *
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

package sunda.rhino;

public class NullValueException extends RhinoException {
  public NullValueException () {
    super("");                                            // just to be complete
  };

  public NullValueException (String Message) {
    super(Message);                                       // just to be complete
  };
};

/**
  *
  * For files not on the acl.read or acl.write list, applets cannot
  *
  *    1 - check for the existence of the file
  *    2 - check the file type
  *    3 - check if the file is a directory
  *    4 - check the timestamp when the file was last modified
  *    5 - check the file's size
  *    6 - create a directory
  *    7 - rename the file
  *    8 - list the files in this file (as if it were a directory)
  *    9 - check read
  *   10 - check write
  *
  * Before: 
  *   Assumes /var/mail/root is a file
  *   Assumes there isn't a directory named /var/mail/testdir
  *
  * After:
  *   The directory /var/mail/testdir may have been created,
  *   if you ran this test from the appletviewer with /var/mail
  *   on your acl.write list.   If that's the case, then you 
  *   should manually delete the test directory /var/mail/testdir 
  *   before running this test again. 
  * 
  * @version JDK 1.0 beta
  * @author  Marianne Mueller
  */

import java.awt.*;
import java.io.*;
import java.lang.*;
import java.applet.*;

public class fileInfo extends Applet {
    String myFile = "/var/mail/root";
    File f = new File(myFile);
    long n;
    int y = 10;
    int spacing = 17;

    // The paint routine drives the 8 above tests
    public void paint(Graphics g) {
	try {
	    if (f.exists())
	        g.drawString("1. Success on File.exists", 10, y);
	}
	catch (SecurityException e) {
	    g.drawString("1. Caught security exception on File.exists", 10, y);
        }
	y = y + spacing;
	try {
	    if (f.isFile())
	        g.drawString("2. Success on File.type", 10, y);
	}
	catch (SecurityException e) {
	    g.drawString("2. Caught security exception on File.isFile", 10, y);
        } 
	y = y + spacing;
	try {
	    File isd = new File("/var/mail");
	    if (isd.isDirectory())
	        g.drawString("3. Success on IsDirectory", 10, y);
	}
	catch (SecurityException e) {
	    g.drawString("3. Caught security exception on File.isDirectory", 10, y);
        }
	y = y + spacing;
	try {
	    n = f.lastModified();	
	    g.drawString("4. Success on timestamp: " + n, 10, y);
	}
	catch (SecurityException e) {
	    g.drawString("4. Caught security exception on File.lastModified", 10, y);
        }
	y = y + spacing;
	try {
	    n = f.length();
	    g.drawString("5. Success on File.length: " + n, 10, y);
	}
	catch (SecurityException e) {
		g.drawString("5. Caught security exception on File.length", 10, y);
        }
	y = y + spacing;
	try {
	    File td = new File("/var/mail/testdir");
	    if (td.mkdir())
	        g.drawString("6. Success on mkdir", 10, y);
	}
	catch (SecurityException e) {
	    g.drawString("6. Caught security exception on File.mkdir", 10, y);
        }
	y = y + spacing;
	try {
	    File nf = new File("/var/mail/bar");
	    f.renameTo(nf);
	    g.drawString("7.  Success on File.renameTo " + nf.toString(), 10, y);
	    nf.renameTo(f);
	}
	catch (SecurityException e) {
	    g.drawString("7. Caught security exception on File.renameTo", 10, y);
        }
	y = y + spacing;
	try {
	    File d = new File("/var/mail");
	    String files[] = null;
	    files = d.list();
	    g.drawString("8. Success on File.list, found " + files.length + " files in " + d.toString(), 10, y);
/*	    for (int i = 0; i < files.length; i++) {
		y = y + spacing;
	        g.drawString(files[i], 10, y);
	    }*/
	}
	catch (SecurityException e) {
	    g.drawString("8. Caught security exception on File.list", 10, y);
        }
	y = y + spacing;
	try {
	    if (f.canWrite())
	        g.drawString("9.  Success on File.canWrite()", 10, y);
	} catch (SecurityException e) {
	    g.drawString("9. Caught security exception on File.canWrite", 10, y);
	}
	y = y + spacing;
	try {
	    if (f.canRead())
	        g.drawString("10. Success on File.canRead()", 10, y);
	} catch (SecurityException e) {
	    g.drawString("10. Caught security exception on File.canRead", 10, y);
	}
    }
}

		


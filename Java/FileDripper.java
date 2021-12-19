package com.enete.caffeinecafe;
import java.io.*;
import com.enete.caffeinecafe.*;

/***************************************************************************

    FileDripper -- This processes one Web File Request

    This service uses the system wide defaults to attach a document base
    path to the beginning of the requested document.<p>

    * @author    Noel Enete
    * @version   1.1, 12/03/97

****************************************************************************/

    public class FileDripper extends Dripper
    {
    final String sTag = "Copyright (c) 1998 Noel Enete";

/***************************************************************************/


    public void get (SConnection conn, String[] args)
      {
      FileInputStream fisIn = null;
      byte[] abBuffer = new byte[20000];
      int iBytesRead;
      String sFileName;
      String sResourceName;
      String sContentType;


/*    Find the file resource.
      =======================  */

      sFileName = conn.getResObject ();
      sResourceName = conn.getDefaults().getBaseDocDir() + sFileName;

      try
        {
        fisIn = new FileInputStream (sResourceName);
        }
      catch (Exception e)
        {
        System.out.println ("Drip: Can't find " + sResourceName);
        e.printStackTrace ();
        conn.putMime (404, "text/html");
        conn.put ("Can't find " + sFileName);
        System.out.println ("Drip: Exception handled");
        return;
        }
      System.out.println( "Drip: File " + sResourceName + " found" );


/*    From the filename determine the type of data in the file.
      =========================================================  */

      if (sResourceName.endsWith (".html"))
        {
        sContentType = "text/html";
        }
      else if (sResourceName.endsWith (".gif"))
        {
        sContentType = "image/gif";
        }
      else if ((sResourceName.endsWith (".txt"))
      || (sResourceName.endsWith (".bat"))
      || (sResourceName.endsWith (".java"))
      || (sFileName.indexOf (".") == -1))
        {
        sContentType = "text/plain";
        }
      else
        {
        sContentType = "application/octet-stream";
        }


/*    Send the file data back to the client.
      ======================================  */

      try
        {
        conn.putMime (200, sContentType);

        while ((iBytesRead = fisIn.read (abBuffer)) >= 0)
          {
          conn.put (abBuffer, 0, iBytesRead);
          }

        fisIn.close ();
        }

      catch (Exception eReadWrite)
        {
        System.out.println ("Drip: Can't read " + sResourceName);
        eReadWrite.printStackTrace ();
        conn.put ("Can't read " + sFileName);
        System.out.println ("Drip: Can't read exception handled");
        return;
        }
      }

    }


/*==========================(  End of Source  )============================*/

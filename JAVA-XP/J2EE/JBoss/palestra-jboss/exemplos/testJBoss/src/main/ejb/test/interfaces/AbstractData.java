/*
* JBoss, the OpenSource J2EE webOS
*
* Distributable under LGPL license.
* See terms of license at gnu.org.
*/
package test.interfaces;

import java.io.Serializable;

/**
 * Base Data Container for all other Value Objects
 *
 * @author Andreas Schaefer
 * @version $Revision: 1.1 $
 **/
public abstract class AbstractData
   implements Cloneable, Serializable
{

   /**
    * Returns a copy of itself. Is necessary because this
    * method is protected within java.lang.Object.
    *
    * @return Copy of this instance
    **/
   public Object clone()
   {
      try
      {
         return super.clone();
      }
      catch( CloneNotSupportedException cnse )
      {
         // This never happens
         return null;
      }
   }

}

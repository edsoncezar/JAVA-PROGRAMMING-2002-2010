package org.jboss.docs.cmp2.crimeportal;

import org.apache.log4j.Category;
import java.util.Collection;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;

public abstract class OrganizationBean implements EntityBean
{
   private Category log = Category.getInstance(getClass());
   private EntityContext ctx;

   public String ejbCreate(String name, String description) 
      throws CreateException
   {
      log.info("Creating organization " + name + ", " + description);
      setName(name);
      setDescription(description);
      return null;
   }

   public void ejbPostCreate(String name, String description)
   {
   }

   // CMP Field Accessors -----------------------------------------------------
   public abstract String getName();
   public abstract void setName(String param);

   public abstract String getDescription();
   public abstract void setDescription(String param);

   // CMR Field Accessors -----------------------------------------------------
   public abstract Set getMemberGangsters();
	public abstract void setMemberGangsters(Set gangsters);

   public abstract Gangster getTheBoss();
   public abstract void setTheBoss(Gangster theBoss);

   // ejbSelect methods -------------------------------------------------------
   public abstract Collection ejbSelectOperatingZipCodes_declaredsql(
         String name) throws FinderException;

   // ejbHome methods ---------------------------------------------------------
   public Collection ejbHomeSelectOperatingZipCodes_declaredsql(String name)
         throws FinderException {

      return ejbSelectOperatingZipCodes_declaredsql(name.trim().toLowerCase());
   }

   // EJB callbacks -----------------------------------------------------------
   public void setEntityContext(EntityContext context)
   {
      ctx = context;
   }

   public void unsetEntityContext()
   {
      ctx = null;
   }

   public void ejbActivate()
   {
   }

   public void ejbPassivate()
   {
   }

   public void ejbRemove()
   {
      log.info("Removing " + getName());
   }

   public void ejbStore()
   {
   }

   public void ejbLoad()
   {
   }
}

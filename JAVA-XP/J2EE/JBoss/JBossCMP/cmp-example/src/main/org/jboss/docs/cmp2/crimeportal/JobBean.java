package org.jboss.docs.cmp2.crimeportal;

import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import org.apache.log4j.Category;

public abstract class JobBean implements EntityBean
{
   private EntityContext ctx;
   private Category log = Category.getInstance(getClass());

   public String ejbCreate(String name)
      throws CreateException
   {
      log.info("Creating Job " + name);
      setName(name);
      return null;
   }

   public void ejbPostCreate(String name)
   {
   }

   // CMP field accessors -----------------------------------------------------
   public abstract String getName();
   public abstract void setName(String name);

   public abstract double getScore();
   public abstract void setScore(double param);


   public abstract double getSetupCost();
   public abstract void setSetupCost(double setupCost);

   // CMR field accessors -----------------------------------------------------
   public abstract Set getGangsters();
	public abstract void setGangsters(Set gangsters);
   
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

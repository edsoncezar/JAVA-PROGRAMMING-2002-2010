package org.jboss.docs.cmp2.crimeportal;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import org.apache.log4j.Category;

public abstract class GangsterBean implements EntityBean
{
   private EntityContext ctx;
   private Category log = Category.getInstance(getClass());

   public Integer ejbCreate(Integer id, String name, String nickName)
         throws CreateException
   {
      log.info("Creating Gangster " + id + " '" + nickName + "' "+ name);
      setGangsterId(id);
      setName(name);
      setNickName(nickName);
      return null;
   }

   public void ejbPostCreate(Integer id, String name, String nickName) { }

   public Integer ejbCreate(
         Integer id,
         String name, 
         String nickName, 
         int badness, 
         Organization organization) throws CreateException
   {
      log.info("Creating Gangster " + id + " '" + nickName + "' "+ name);
      setGangsterId(id);
      setName(name);
      setNickName(nickName);
      setBadness(badness);
      return null;
   }

   public void ejbPostCreate(
         Integer id,
         String name, 
         String nickName, 
         int badness,
         Organization organization)
   {
      setOrganization(organization);
   }

   // CMP field accessors -----------------------------------------------------
   public abstract Integer getGangsterId();
   public abstract void setGangsterId(Integer gangsterId);

   public abstract String getName();
   public abstract void setName(String name);

   public abstract String getNickName();
   public abstract void setNickName(String nickName);

   public abstract int getBadness();
   public abstract void setBadness(int badness);

   public abstract ContactInfo getContactInfo();
   public abstract void setContactInfo(ContactInfo contactInfo);

   // CMR field accessors -----------------------------------------------------
   public abstract Organization getOrganization();
	public abstract void setOrganization(Organization org);

   public abstract Set getJobs();
	public abstract void setJobs(Set jobs);

   public abstract Set getEnemies();
   public abstract void setEnemies(Set enemies);

   public abstract Location getHangout();
   public abstract void setHangout(Location hangout);

   // ejbSelect methods -------------------------------------------------------
   public abstract Set ejbSelectBoss_ejbql(String name) throws FinderException;
   public abstract Set ejbSelectBoss_declaredsql(String name)
         throws FinderException;

   public abstract Set ejbSelectAccomplices(Gangster g) throws FinderException;

   public abstract Set ejbSelectGeneric(String jbossQl, Object[] arguments)
         throws FinderException;

   // ejbHome methods ---------------------------------------------------------
   public Set ejbHomeSelectBoss_ejbql(String name) throws FinderException {
      return ejbSelectBoss_ejbql(name.trim());
   }

   public Set ejbHomeSelectBoss_declaredsql(String name)
         throws FinderException {
      return ejbSelectBoss_declaredsql(name.trim().toLowerCase());
   }
   
   public Set ejbHomeSelectInStates(Set states) throws FinderException {
      // generate JBossQL query
      StringBuffer jbossQl = new StringBuffer();
      jbossQl.append("SELECT OBJECT(g) ");
      jbossQl.append("FROM gangster g ");
      jbossQl.append("WHERE g.hangout.state IN (");
      for(int i = 0; i < states.size(); i++) {
         if(i > 0) {
            jbossQl.append(", ");
         }
         jbossQl.append("?").append(i+1);
      }
      jbossQl.append(") ORDER BY g.name");

      // pack arguments into an Object[]
      Object[] args = states.toArray(new Object[states.size()]);

      // call dynamic-ql query
      return ejbSelectGeneric(jbossQl.toString(), args);
   }

   // ejbFind methods ---------------------------------------------------------
   public Collection ejbFindByPrimaryKeys(Collection keys) {
      return keys;
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

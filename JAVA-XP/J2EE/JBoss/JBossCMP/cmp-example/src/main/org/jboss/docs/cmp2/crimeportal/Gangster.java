package org.jboss.docs.cmp2.crimeportal;

import java.util.Set;
import javax.ejb.EJBLocalObject;

public interface Gangster extends EJBLocalObject
{
	Integer getGangsterId();
   
	String getName();

   String getNickName();
   void setNickName(String nickName);

   int getBadness();
   void setBadness(int badness);

   ContactInfo getContactInfo();
   void setContactInfo(ContactInfo contactInfo);

   Organization getOrganization();

   Set getJobs();

   Set getEnemies();

   Location getHangout();
   void setHangout(Location hangout);
}

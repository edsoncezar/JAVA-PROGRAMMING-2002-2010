package com.titan.customer;

import com.titan.address.*;
import com.titan.phone.*;

import javax.naming.InitialContext;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.naming.NamingException;
import java.util.Date;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

public abstract class CustomerBean implements javax.ejb.EntityBean 
{

   public Integer ejbCreate(Integer id)
      throws CreateException
   {
      this.setId(id);
      return null;
   }

   public void ejbPostCreate(Integer id)
   {
   }

   // business methods

   public Name getName() 
   {
      Name name = new Name(getLastName(),getFirstName());
      return name;
   }
   public void setName(Name name) 
   {
      setLastName(name.getLastName());
      setFirstName(name.getFirstName());
   }


   public void addPhoneNumber(String number, byte type)
      throws NamingException, CreateException 
   {
      InitialContext jndiEnc = new InitialContext();
      PhoneHomeLocal phoneHome = (PhoneHomeLocal)(jndiEnc.lookup("PhoneHomeLocal")); 

      PhoneLocal phone = phoneHome.create(number,type);
        
      Collection phoneNumbers = this.getPhoneNumbers( );
      phoneNumbers.add(phone);

   }

   public void removePhoneNumber(byte typeToRemove) 
   {
      Collection phoneNumbers = this.getPhoneNumbers();
      Iterator iterator = phoneNumbers.iterator();

      while(iterator.hasNext())
      {
         PhoneLocal phone = (PhoneLocal)iterator.next();
         if (phone.getType() == typeToRemove) 
         {
            phoneNumbers.remove(phone);
            break;
         }

      }
   }

   public void updatePhoneNumber(String number, byte typeToUpdate) 
   { 
      Collection phoneNumbers = this.getPhoneNumbers();
      Iterator iterator = phoneNumbers.iterator();
      while(iterator.hasNext())
      {
         PhoneLocal phone = (PhoneLocal)iterator.next();
         if (phone.getType() == typeToUpdate) 
         {
            phone.setNumber(number);
            break;
         }
      }
   }

   public Vector getPhoneList() 
   {
      Vector vv = new Vector();
      Collection phoneNumbers = this.getPhoneNumbers();
      Iterator iterator = phoneNumbers.iterator();
      while(iterator.hasNext()) {
         PhoneLocal phone = (PhoneLocal)iterator.next();
         String ss = "Type="+phone.getType()+"  Number="+phone.getNumber();
         vv.add(ss);
      }
      return vv;
   }

   // persistent relationships

   public abstract AddressLocal getHomeAddress();
   public abstract void setHomeAddress(AddressLocal address);

   public abstract CreditCardLocal getCreditCard();
   public abstract void setCreditCard(CreditCardLocal card);

   public abstract Collection getPhoneNumbers( );
   public abstract void setPhoneNumbers(Collection phones);

   public abstract Collection getReservations();
   public abstract void setReservations(Collection reservations);

   // abstract accessor methods

   public abstract Integer getId();
   public abstract void setId(Integer id);
 
   public abstract String getLastName();
   public abstract void setLastName(String lname);

   public abstract String getFirstName();
   public abstract void setFirstName(String fname);

   public abstract boolean getHasGoodCredit();
   public abstract void setHasGoodCredit(boolean flag);

   // ejbSelect methods

   public abstract Collection ejbSelectLastNames()
      throws FinderException;

   public abstract Collection ejbSelectCreditCards()
      throws FinderException;

   public abstract Collection ejbSelectCities()
      throws FinderException;

   public abstract Collection ejbSelectCreditCompanyAddresses()
      throws FinderException;

   public abstract Collection ejbSelectCreditCompanyCities()
      throws FinderException;

   public abstract Collection ejbSelectReservations()
      throws FinderException;

   public abstract Collection ejbSelectCruises()
      throws FinderException;

   public abstract Collection ejbSelectShips()
      throws FinderException;

   

   // query methods, these wrap ejbSelect methods in home interface
   public Collection ejbHomeQueryLastNames()
      throws FinderException
   {
      return ejbSelectLastNames();
   }

   public Collection ejbHomeQueryCreditCards()
      throws FinderException
   {
      return ejbSelectCreditCards();
   }

   public Collection ejbHomeQueryCities()
      throws FinderException
   {
      return ejbSelectCities();
   }

   public Collection ejbHomeQueryCreditCompanyAddresses()
      throws FinderException
   {
      return ejbSelectCreditCompanyAddresses();
   }

   public Collection ejbHomeQueryCreditCompanyCities()
      throws FinderException
   {
      return ejbSelectCreditCompanyCities();
   }

   public Collection ejbHomeQueryReservations()
      throws FinderException
   {
      return ejbSelectReservations();
   }

   public Collection ejbHomeQueryCruises()
      throws FinderException
   {
      return ejbSelectCruises();
   }

   public Collection ejbHomeQueryShips()
      throws FinderException
   {
      return ejbSelectShips();
   }

   // standard call back methods

   public void setEntityContext(EntityContext ec){}
   public void unsetEntityContext(){}
   public void ejbLoad(){}
   public void ejbStore(){}
   public void ejbActivate(){}
   public void ejbPassivate(){}
   public void ejbRemove(){}

}

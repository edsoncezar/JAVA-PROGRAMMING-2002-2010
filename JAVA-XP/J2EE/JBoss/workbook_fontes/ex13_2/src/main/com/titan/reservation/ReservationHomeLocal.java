package com.titan.reservation;

import com.titan.cruise.CruiseLocal;
import com.titan.customer.CustomerRemote;
import com.titan.cabin.CabinLocal;

import java.util.Collection;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

// Reservation EJB's local home interface
//
public interface ReservationHomeLocal extends javax.ejb.EJBLocalHome
{
   
   public ReservationLocal create (CruiseLocal cruise, Collection customers)
      throws javax.ejb.CreateException;
   
   public ReservationLocal create (CustomerRemote customer, CruiseLocal cruise,
                                   CabinLocal cabin, double price, 
                                   Date dateBooked)
      throws javax.ejb.CreateException;
   
   public ReservationLocal findByPrimaryKey (Integer primaryKey)
      throws javax.ejb.FinderException;
   
   // used to remove all beans to prepare example environment
   //
   public java.util.Collection findAll ()
   throws FinderException;   
}

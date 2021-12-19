package com.wrox.store.ejb.mail;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.rmi.RemoteException;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import com.wrox.store.ejb.customer.Customer;

/**
 * Our implementation of the Mail interface as a session bean.
 *
 * @author    Simon Brown
 */
public class MailBean extends com.wrox.store.ejb.AbstractSessionBean {

  public static final String SMTP_HOST = "smtp.yourdomain.com";

  public static final String SENDER_NAME = "Order confirmations";
  public static final String SENDER_EMAIL_ADDRESS = "confirmations@yourdomain.com";

  /**
   * Sends an e-mail to the customer as a confirmation of their order.
   *
   * @param customer    the Customer
   */
  public void sendConfirmation(Customer customer) throws RemoteException {
    StringBuffer message = new StringBuffer();

    message.append("Hello ");
    message.append(customer.getName());
    message.append(" and thank you for your order. ");
    message.append("It will be dispatched in the next 24 hours.");

    sendMessage(customer.getEmailAddress(), "Confirmation of your order", message.toString());
  }

  /**
   * Encapsulates the interaction with JavaMail needed to send an e-mail
   * via SMTP.
   *
   * @param recipient   the
   */
  private void sendMessage(String recipient, String subject, String message) {

    // setup the e-mail session for this bean
    Properties props = new Properties();
    props.put("mail.smtp.host", SMTP_HOST);
      Session session = Session.getDefaultInstance(props, null);

    try {
      // create a message and try to send it
      Message msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress(SENDER_EMAIL_ADDRESS, SENDER_NAME));

      msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

      msg.setSubject(subject);
      msg.setSentDate(new Date());
      msg.setText(message);

      Transport.send(msg);
    } catch (UnsupportedEncodingException uee) {
      System.out.println(uee);
    } catch (MessagingException me) {
      System.out.println(me);
    }
  }

}
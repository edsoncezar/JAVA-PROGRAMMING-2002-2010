package com.wrox.pjsp2.struts.servlets;


import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.MissingResourceException;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.digester.Digester;
import org.apache.struts.util.BeanUtils;
import org.apache.struts.util.MessageResources;
import org.apache.struts.action.Action;
import com.wrox.pjsp2.struts.common.Category;
import com.wrox.pjsp2.struts.common.CD;
import com.wrox.pjsp2.struts.common.Constants;
import com.wrox.pjsp2.struts.common.User;
import com.wrox.pjsp2.struts.common.OptionLabelValue;

public final class DatabaseServlet
   extends HttpServlet {


   private HashMap userTable = null;
   private HashMap categoryTable = null;
   private HashMap cdTable = null;

   private int debug = 0;
   private String pathname = null;
   private MessageResources messages = null;
   private ServletContext servletContext = null;

   public void destroy() {
      if(debug >= 1) {
         log("Finalizing database servlet");
      }

      // Remove the database tables from our application attributes
      servletContext.removeAttribute(Constants.USER_TABLE_KEY);
      servletContext.removeAttribute(Constants.CATEGORY_TABLE_KEY);
      servletContext.removeAttribute(Constants.CD_TABLE_KEY);

   } //end destroy


   public void init() throws ServletException {
   
      // Process our servlet initialization parameters
      String value = getServletConfig().getInitParameter("debug");
      
      servletContext = getServletContext();
      
      // Get MessageResources from the Servlet Context 
      // returns null if ActionServlet not loaded first, see web.xml file.
      messages = (MessageResources) 
         servletContext.getAttribute(Action.MESSAGES_KEY);
   
      try {
         debug = Integer.parseInt(value);
      } catch (Throwable t) {
         debug = 0;
      }
      if(debug >= 1) {
         log("Initializing database servlet");
      }
   
      // Load our database from persistent storage
      try {
         load();
      } catch(Exception ex) {
         log("Database load exception", ex);
         throw new UnavailableException
            ("Cannot load database from '" + pathname + "'");
      }
      setCreditCardTypes();
      setMonthValues();
      setYearValues();
      setTitleValues();
   } //end init

   public void setCreditCardTypes() {
      ArrayList ccTypes = new ArrayList(5);
      ccTypes.add(
            new OptionLabelValue(messages.getMessage("title.option.selectOne"),
                                 messages.getMessage("option.unknown"))
                );

      String i8nLabelValue = messages.getMessage("cc.option.visa");
      ccTypes.add(new OptionLabelValue(i8nLabelValue,i8nLabelValue));

      i8nLabelValue = messages.getMessage("cc.option.mc");
      ccTypes.add(new OptionLabelValue(i8nLabelValue,i8nLabelValue));

      i8nLabelValue = messages.getMessage("cc.option.amex");
      ccTypes.add(new OptionLabelValue(i8nLabelValue,i8nLabelValue));

      i8nLabelValue = messages.getMessage("cc.option.discover");
      ccTypes.add(new OptionLabelValue(i8nLabelValue,i8nLabelValue));

      servletContext.setAttribute(Constants.CCTYPES_ARRAY_KEY, ccTypes);
   }  //end setCreditCardTypes

   public void setMonthValues() {
      ArrayList months = new ArrayList(12);
      for(int i=0; i<12; i++) {
         StringBuffer sb = new StringBuffer();
         if(i<9) {
            sb.append("0");
         }
         sb.append(i+1);
         months.add(new OptionLabelValue(sb.toString(),sb.toString()));
      }
      servletContext.setAttribute(Constants.MONTHS_ARRAY_KEY, months);
   } //end setMonthValues
      
   public void setYearValues() {
      ArrayList years = new ArrayList(12);
      for(int i=0; i<12; i++) {
         StringBuffer sb = new StringBuffer();
         sb.append("20");
         if(i<9) {
            sb.append("0");
         }
         sb.append(i+1);
         years.add(new OptionLabelValue(sb.toString(),sb.toString()));
      }
      servletContext.setAttribute(Constants.YEARS_ARRAY_KEY, years);
   } //end setYearValues
      
   public void setTitleValues() {
      ArrayList titles = new ArrayList(6);
      titles.add(
            new OptionLabelValue(messages.getMessage("title.option.selectOne"),
                                 messages.getMessage("option.unknown"))
                );
      
      String i8nLabelValue = messages.getMessage("title.option.mr");
      titles.add(new OptionLabelValue(i8nLabelValue,i8nLabelValue));
      
      i8nLabelValue = messages.getMessage("title.option.mrs");
      titles.add(new OptionLabelValue(i8nLabelValue,i8nLabelValue));
      
      i8nLabelValue = messages.getMessage("title.option.miss");
      titles.add(new OptionLabelValue(i8nLabelValue,i8nLabelValue));
      
      i8nLabelValue = messages.getMessage("title.option.ms");
      titles.add(new OptionLabelValue(i8nLabelValue,i8nLabelValue));
      
      i8nLabelValue = messages.getMessage("title.option.dr");
      titles.add(new OptionLabelValue(i8nLabelValue,i8nLabelValue));
      
      servletContext.setAttribute(Constants.TITLE_ARRAY_KEY, titles);
   } //end setTitleValues

   public void addUser(User user) {
      userTable.put(user.getUserName(), user);
   } //end addUser

   public void addCategory(Category category) {
      categoryTable.put(new Integer(category.getCategoryId()), category);
   } //end addCategory

   public void addCD(CD cd) {
      cdTable.put(new Integer(cd.getTitleId()), cd);
   } //end addCategory

   public int getDebug() {
      return debug;
   } //end getDebug


private synchronized void load() throws Exception {

   // Initialize our tables
   userTable = new HashMap();
   categoryTable = new HashMap();
   cdTable = new HashMap();

   // Acquire an input stream to our database file
   if(debug >= 1) {
      log("Loading database from '" + pathname() + "'");
   }
   FileInputStream fis = null;
   try {
      fis = new FileInputStream(pathname());
   } catch (FileNotFoundException e) {
      log("No persistent database to be loaded");
      return;
   }
   BufferedInputStream bis = new BufferedInputStream(fis);

   // Construct a digester to use for parsing
   Digester digester = new Digester();
   digester.push(this);
   digester.setDebug(debug);
   digester.setValidating(false);

   // rule to create instance of User class
   digester.addObjectCreate("database/user",
                              "com.wrox.pjsp2.struts.common.User");
   // rule to get properties attributes on user tag
   digester.addSetProperties("database/user");
   // rule to call addUser method on this class
   digester.addSetNext("database/user", "addUser");

   // rule to create instance of Address class for userAddress data
   digester.addObjectCreate("database/user/userAddress",
                              "com.wrox.pjsp2.struts.common.Address",
                              "userAddress");
   // rule to get properties from attributes on userAddress tag
   digester.addSetProperties("database/user/userAddress");
   // rule to call setUserAddress method on the User class
   digester.addSetNext("database/user/userAddress",
                        "setUserAddress",
                        "com.wrox.pjsp2.struts.common.Address");

   // rule to create instance of Address class for billingAddress data
   digester.addObjectCreate("database/user/billingAddress",
                              "com.wrox.pjsp2.struts.common.Address",
                              "billingAddress");
   // rule to get properties from attributes on billingAddress tag
   digester.addSetProperties("database/user/billingAddress");
   // rule to call setBillingAddress method on the User class
   digester.addSetNext("database/user/billingAddress",
                        "setBillingAddress",
                        "com.wrox.pjsp2.struts.common.Address");

   // rule to create instance of Category class
   digester.addObjectCreate("database/category",
                              "com.wrox.pjsp2.struts.common.Category");
   // rule to get properties from attributes on category tag
   digester.addSetProperties("database/category");

   // rule to call addCategory method on this class
   digester.addSetNext("database/category", "addCategory");

   // rule to create instance of CD class
   digester.addObjectCreate("database/CD",
                              "com.wrox.pjsp2.struts.common.CD");
   // rule to get properties from attributes on cd tag
   digester.addSetProperties("database/CD");

   // rule to call addCD method on this class
   digester.addSetNext("database/CD", "addCD",
                        "com.wrox.pjsp2.struts.common.CD");

   // Parse the input stream to populate hashMaps within this servlet.
   digester.parse(bis);
   bis.close();
   
   servletContext.setAttribute(Constants.CATEGORY_TABLE_KEY, categoryTable);      
   servletContext.setAttribute(Constants.CD_TABLE_KEY, cdTable);      
   servletContext.setAttribute(Constants.USER_TABLE_KEY, userTable);      

} //end load


   private String pathname() {
      if(pathname != null) {
         return pathname;
      } else {
         return (getServletContext().getRealPath("/") +
                  "/WEB-INF/database.xml");
      }
   } //end pathname

} //end DatabaseServlet class

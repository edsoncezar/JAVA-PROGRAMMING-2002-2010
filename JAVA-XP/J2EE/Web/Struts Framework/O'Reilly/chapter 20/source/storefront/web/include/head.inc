<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
  <tr><td colspan="11"><html:img height="6" alt="" page="/images/spacer.gif" width="1" border="0"/></td></tr>
  <tr>
    <td width="10"><html:img page="/images/spacer.gif" width="10" height="1" border="0" alt=""/></td>
    <td width="125"><a href="javascript:popUpMenu('http://jakarta.apache.org/struts','Struts')">
    <html:img page="/images/logo.gif" width="125" height="85" border="0" alt="Powered by Struts"/></a></td>
    <td width="110"><html:img page="/images/spacer.gif" width="110" height="1" border="0" alt=""/></td>
    <td width="240" align="right" valign="bottom" nowrap>
      <table width="240" border="0" cellspacing="0" cellpadding="0">
	<tr>
 	 <td colspan="2" align="left" valign="top" nowrap>
	   <font face="Verdana, Arial, Helvetica" size="2"><span class="H1">	   
	   <logic:notPresent name="UserContainer" property="userView" scope="session">
	      <b>Would you like to <html:link page="/action/viewsignin">sign in?</html:link></b>	
	   </logic:notPresent>
	   
	   <logic:present name="UserContainer" property="userView" scope="session">
	     <b>Hello <bean:write name="UserContainer" property="userView.firstName" scope="session"/>
	      <html:link page="/action/signoff"> sign off?</html:link></b>	
	   </logic:present>	    
	    
	   </span></font>
	 </td>
        </tr>
	<tr>
	 <td align="left" valign="top" nowrap>
	 <font face="Verdana, Arial, Helvetica" size="2"><span class="H1Description">
	   Items in shopping cart: <b>
	   
	   <logic:present name="UserContainer" scope="session">
	    <b><bean:write name="UserContainer" property="cart.size" scope="session"/></b>
	    <br>
	    Current Total: <b>$<bean:write name="UserContainer" format="#,##0.00" property="cart.totalPrice" scope="session"/></b><br>
	   </logic:present>	    
	   
	   <logic:notPresent name="UserContainer" scope="session">
	     <b>0</b>
	     <br>
	     Current Total: <b>$0.00</b><br>
	   </logic:notPresent>	   
	   
	 </span></font>
	</td>
	<td width="35" align="right" valign="bottom">
	  <html:link page="/action/cart?method=view">
	    <html:img page="/images/cart.gif" alt="Check Your Cart" width="35" height="20" border="0"/>
	  </html:link>
	  <br>
	</td>
       </tr>
	</table>
   </td>		
    <td width="78"></td>		
    <td width="100%"></td>
	</tr>
</table>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


<html:html>
<head>
 <html:base/>
 <title><bean:message key="global.title"/></title>
 <script language=javascript src="include/scripts.js"></script>
 <link rel="stylesheet" href="../stylesheets/format_win_nav_main.css" type="text/css">
</head>

<body topmargin="0" leftmargin="0" bgcolor="#FFFFFF">

<!-- Header Page Information -->
<%@ include file="../include/head.inc"%>

<!-- Nav Bar -->
<%@ include file="../include/menubar.inc"%>

<table cellSpacing=0 cellPadding=0 width=480 border=0>
  <tbody>
   <tr class="RED">
     <td></td>
     <td><html:errors/></td>
   </tr>   
   <tr>
     <td vAlign=top width=20><html:img height="1" alt="" page="/images/spacer.gif" width="20" border="0"/></td>
       <td vAlign=top width=460>       
          <html:img height="20" alt="" page="/images/spacer.gif" width="1" border="0"/><br>
          <html:img height="35" alt="Sign In" page="/images/signin.gif" width="460" border="0"/><br>
          <html:img height="10" alt="" src="/images/spacer.gif" width="1" border="0"/><br>
          <font face="Verdana, Arial, Helvetica" size=2>
          <span class=body-text-red><font face="Verdana, Arial, Helvetica"size=2>
           <span class=body-text><bean:message key="signin.instructions"/><br><br></span></font>
            <table cellSpacing=4 cellPadding=2 border=0>
              <tbody>
              <tr>
                <td vAlign=top><font face="Verdana, Arial, Helvetica" size=2><span class=feature-text>
                  <html:form onsubmit="return validateSigninForm(this)" action="signin" method="post">
                  <font face="Verdana, Arial, Helvetica" size=2>
                   <span class="feature-text"><B>1.</B> </span></font></span></font>
                </td>
                <td><font face="Verdana, Arial, Helvetica" size=2><span class=feature-text>
                  <B><bean:message key="label.email"/>:</B><br><html:text size="22" property="email" value="foo@foobar.com"/></span></font>
                 </td>
               </tr>
              <tr>
                <td vAlign=top><font face="Verdana, Arial, Helvetica" size=2><span class=feature-text><B>2.</B> </span></font></td>
                <td><font face="Verdana, Arial, Helvetica" size=2><span class=feature-text><B><bean:message key="label.password"/>:</B><br>
                  <table cellSpacing=0 cellPadding=0>
                    <tbody>
                    <tr>
                      <td><font face="Verdana, Arial, Helvetica" size=2>
                        <span class=feature-text><input type=radio value="N" name="custype"></span></font>
                      </td>
                      <td><html:img height="1" alt="" page="/images/spacer.gif" width="2" border="0"/> </td>
                      <td><font face="Verdana, Arial, Helvetica" size=2><span class=feature-text>
                        <B>New customer</B>, create an account. </span></font>
                      </td>
                    </tr>
                    <tr>
                      <td><font face="Verdana, Arial, Helvetica" size=2>
                        <span class=feature-text><input type=radio CHECKED value="R" name=custype></span></font>
                      </td>
                      <td><html:img height="1" alt="" page="/images/spacer.gif" width="2" border="0"/></td>
                      <td><font face="Verdana, Arial, Helvetica" size=2>
                        <span class=feature-text>
                        <B>Returning customer</B>, enter your password and sign in securely.</span></font>
                      </td>
                    </tr>
                    <tr>
                      <td><html:img height="1" alt="" page="/images/spacer.gif" width="4" border="0"/></td>
                      <td><html:img height="1" alt="" page="/images/spacer.gif" width="4" border="0"/></td>
                      <td><font face="Verdana, Arial, Helvetica" size=2>
                        <span class=feature-text>
                         <html:password size="22" property="password" value="foobar"/>
                          <br>Forgot your password?<br>Enter your E-mail address in step one and we'll
                          <a href="javascript:sendemail()" name=email>send it to you</a>.<br><br></span></font>
                      </td></tr></tbody></table></span></font></td>
                    </tr>
                    <tr>
                      <td vAlign=top><font face="Verdana, Arial, Helvetica" size=2>
                       <span class=feature-text><B>3.</B> </span></font></td>
                      <td><html:image alt="Continue" page="/images/continue.gif" border="0"/>
                </td>
               </html:form></tr></tbody>
              </table>

<%@ include file="../include/copyright.inc"%>

</body>
</html:html>

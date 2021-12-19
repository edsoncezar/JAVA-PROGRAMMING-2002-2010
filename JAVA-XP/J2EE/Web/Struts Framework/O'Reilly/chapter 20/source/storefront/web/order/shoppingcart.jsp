<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<html:html>
<head>
<title>Virtual Shopping with Struts</title>
<html:base/>
<script language=javascript src="include/scripts.js"></script>
<link rel="stylesheet" href="../stylesheets/format_win_nav_main.css" type="text/css">
</head>

<body topmargin="0" leftmargin="0" bgcolor="#FFFFFF">

<!-- Header Page Information -->
<%@ include file="../include/head.inc"%>

<!-- Nav Bar -->
<%@ include file="../include/menubar.inc"%>

<logic:lessEqual scope="session" name="UserContainer" property="cart.size" value="0">
  <%@ include file="emptycart.jsp" %>
</logic:lessEqual>

<logic:greaterThan scope="session" name="UserContainer" property="cart.size" value="0">  

  <TABLE cellSpacing=0 cellPadding=0 width=480 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top width=20><IMG height=1 alt="" 
            src="../images/spacer.gif" width=20 
            border=0>
          </TD>
          <TD vAlign=top width=460>
          <!-- MAIN BODY CONTENT-->
          
      <form action="../action/cart?method=update" method="post">
            <TABLE width=460 border=0>
              <TBODY>
              <TR>
                <TD width=260 rowSpan=2><IMG height=20 alt="" 
                  src="../images/spacer.gif" width=1 
                  border=0><BR>
                  
                  <html:img height="35" alt="Shopping Cart" page="/order/images/cart.gif"  border="0"/>
                  <IMG height=6 alt="" src="../images/spacer.gif" width=1 
                  border=0><BR></TD>
                <TD vAlign=bottom align=right width=200><FONT 
                  face="Verdana, Arial, Helvetica" size=2><SPAN 
                  class=body-text>
                  <IMG height=9 alt="" src="Your BestBuy_com Shopping Cart_files/cart_leftarrow.gif" width=5 border=0>
                  <html:link page="/action/home">Continue Shopping</html:link><BR><BR><BR></SPAN></FONT></TD></TR></TBODY></TABLE>
                  
            <TABLE cellSpacing=0 cellPadding=3 width=460 border=0>             
              <TBODY>
              <!-- Header Row -->
              <TR bgColor=#cccccc>
                <TD noWrap><FONT face="Verdana, Arial, Helvetica" size=2><SPAN 
                  class=feature-text><B>Delete</B></SPAN></FONT></TD>
                <TD noWrap><FONT face="Verdana, Arial, Helvetica" size=2><SPAN 
                  class=feature-text><B>Description</B></SPAN></FONT></TD>
                <TD noWrap align=middle><FONT face="Verdana, Arial, Helvetica" 
                  size=2><SPAN class=feature-text><B>Qty.</B></SPAN></FONT></TD>
                <TD noWrap align=middle><FONT face="Verdana, Arial, Helvetica" 
                  size=2><SPAN class=feature-text><B>Unit 
                Price</B></SPAN></FONT></TD>
                <TD noWrap align=middle><FONT face="Verdana, Arial, Helvetica" 
                  size=2><SPAN 
              class=feature-text><B>Total</B></SPAN></FONT></TD></TR>
              <!-- /Header Row -->
              
                                          
              
              <logic:iterate id="cartItem" scope="session" name="UserContainer" property="cart.items">
              
              <!-- Item row -->
              <TR bgColor=#ffffff>
                
                <!--Delete checkbox for item -->
                <TD vAlign=top align=middle>
                 <input type="checkbox" value="<bean:write name="cartItem" property="id"/>" name="deleteCartItem"></td>
                
                <!-- name and description for item -->
                <TD vAlign=top align=left><FONT 
                  face="Verdana, Arial, Helvetica" size=2><SPAN 
                  class=feature-text><B>
                  
                  <html:link page="/action/viewitemdetail" paramName="cartItem" paramId="id" paramProperty="id">
                    <bean:write name="cartItem" property="name"/>
                  </html:link>
                  </B><BR>In Stock - 
                  Usually ships in 2-3 business days<BR>Available for pickup at 
                  most stores      
                  
                  <BR><BR></SPAN></FONT></TD>
                 
                <!-- Qty input box -->
                <TD vAlign=top noWrap align=middle>
                  <input type="maxlength="3" size="3" text name="qty_<bean:write name="cartItem" property="id"/>" value="<bean:write name="cartItem" property="quantity"/>">                  
                </TD>
                 
                <!-- Unit price for item -->
                <TD vAlign=top noWrap align=middle><FONT 
                  face="Verdana, Arial, Helvetica" size=2><B><SPAN 
                  class=price-text>$<bean:write name="cartItem" format="#,##0.00" property="unitPrice"/><BR></SPAN></B></FONT></TD>
                  
                <!-- Extended price for item -->
                <TD vAlign=top align=right>
                  <TABLE cellSpacing=2 cellPadding=3 width="100%" border=0>
                    <TBODY>
                    <TR>
                      <TD align=right bgColor=#ffcc00><FONT 
                        face="Verdana, Arial, Helvetica" size=2><B><SPAN 
                        class=price-text>$<bean:write name="cartItem" format="#,##0.00" property="extendedPrice"/><BR></SPAN></B></FONT>
                      </TD>
                    </TR>
                   </TBODY>
                  </TABLE>
                 </TD>                 
                 
                </TR>    
                
              </logic:iterate>          
                
                
                
              
              <TR>
                <TD colSpan=5>
                  <TABLE cellSpacing=0 cellPadding=0 width="100%" 
                  bgColor=#cccccc>
                    <TBODY>
                    <TR>
                      <TD><IMG height=1 alt="" src="../images/spacer.gif" width=1 border=0></TD></TR></TBODY></TABLE></TD>
                    </TR>
                <!-- /Grey Separator Row -->
                
              </TBODY></TABLE></TD>
             </TR>
            
              <!-- End Of Items -->
              
              
              <INPUT type=hidden value=1 name=ShowItemNum>
              
              <!-- Ending Separator Row -->
              <TR>
                <TD colSpan=5>
                  <TABLE cellSpacing=0 cellPadding=0 width="100%" 
                  bgColor=#cccccc border=0>
                    <TBODY>
                    </TBODY></TABLE></TD></TR><!-- /Ending Separator Row -->
                    
                  
              <TR>
                <TD colSpan=5>
                  <TABLE cellSpacing=0 cellPadding=0 width="100%" 
                  bgColor=#c0c0c0 border=1>
                    <TBODY>
                    <TR>
                      <TD align=left width=107>
                       
                       <html:image alt="Delete Checked Items" page="/order/images/deleteitems.gif" border="0"/></TD>
                      <TD align=left width=131><IMG height=1 alt="" 
                        src="../images/spacer.gif" 
                        width=131 border=0></TD>
                      <TD align=right width=66>
                      
                       <html:image alt="Update Quantity" page="/order/images/updateqty.gif" border="0"/></TD>
                      <TD vAlign=center align=right width=150 
                        bgColor=#ffffff><FONT face="Verdana, Arial, Helvetica" 
                        size=2><B><SPAN class=price-text>SubTotal* $<bean:write scope="session" format="#,##0.00" name="UserContainer" property="cart.totalPrice"/>
                        <IMG height=1 alt="" 
                        src="../images/spacer.gif" 
                        width=3 
                border=0></SPAN></B></FONT></TD></TR></TBODY></TABLE></TD>
              <TR>
                <TD align=right colSpan=5>                
                  <TABLE cellSpacing=0 cellPadding=0 width=186 border=0>
                    <TBODY>
                    <TR>
                      <TD vAlign=top width=93>
                        <html:link page="/action/home">
                          <html:img alt="Continue Shopping" page="/order/images/continueshopping.gif" border="0"/>
                        </html:link>
                       </TD>
                      <TD vAlign=top width=93>
                        <html:link page="/action/begincheckout">
                          <html:img alt="Begin Checkout" page="/order/images/checkout.gif" border="0"/>
                        </html:link>
                      </TD></TR></TBODY></TABLE></TD></TR>
              <TR>
                <TD colSpan=5>
                  <TABLE cellSpacing=0 cellPadding=0 width="100%" 
                  bgColor=#cccccc>
                    <TBODY>
                    <TR>
                      <TD><IMG height=1 alt="" 
                        src="../images/spacer.gif" 
                        width=1 border=0></TD></TR></TBODY></TABLE></TD></TR><!-- /Grey Separator Row --><!-- Disclaimers Row -->
              <TR>
                <TD align=right colSpan=5>
                  <TABLE cellSpacing=0 cellPadding=0>
                    <TBODY>
                    <TR>
                      <TD><FONT face="Verdana, Arial, Helvetica" 
                        size=2><B><SPAN class=price-text>*SubTotal does not 
                        include tax or shipping/handling charge. 
                        </SPAN></B></FONT></TD></TR></TBODY></TABLE></TD></TR><!-- /Disclaimers Row --><!-- /Buy Pallet --></TBODY></TABLE><BR>
            <DIV class=body-text align=center>
            <TABLE cellSpacing=0 cellPadding=0 width="100%">
              <TBODY>
              <TR>
                <TD bgColor=#cccccc><IMG height=1 alt="" 
                  src="../images/spacer.gif" width=1 
                  border=0></TD>
                <TD><IMG height=1 alt="" 
                  src="../images/spacer.gif" width=5 
                  border=0></TD>
                <TD bgColor=#cccccc><IMG height=1 alt="" 
                  src="../images/spacer.gif" width=1 
                  border=0></TD>
                <TD><IMG height=1 alt="" 
                  src="../images/spacer.gif" width=5 
                  border=0></TD>
                <TD bgColor=#cccccc><IMG height=1 alt="" 
                  src="../images/spacer.gif" width=1 
                  border=0></TD></TR>
              <TR>
                <TD vAlign=top align=middle>
                  <TABLE cellSpacing=0 cellPadding=0 width=150 
                    bgColor=#ffffcc><TBODY>
                   </TBODY></TABLE></TD>
                <TD><IMG height=1 alt="" 
                  src="../images/spacer.gif" width=5 
                  border=0></TD>
                <TD vAlign=top align=middle>
                  <TABLE cellSpacing=0 cellPadding=0 width=150 
                    bgColor=#ffffcc><TBODY>
                    </TBODY></TABLE></TD>
                <TD><IMG height=1 alt="" 
                  src="../images/spacer.gif" width=5 
                  border=0></TD>
                <TD vAlign=top align=middle>
                  <TABLE cellSpacing=0 cellPadding=0 width=150 
                    bgColor=#ffffcc><TBODY>
                   </TBODY></TABLE></TD></TR></TBODY></TABLE>
            
            
            </TD></TR></TBODY></TABLE></FORM>
                  </td>
                  </tr>
                  </tbody>
                  </table>

</logic:greaterThan>

<%@ include file="../include/copyright.inc"%>
</body>


</html:html>




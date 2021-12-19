<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<tt:definition name="bookstore" screen="<%= (String)request.getAttribute(\"selectedScreen\") %>">


       <tt:screen id="/enter">


                <tt:parameter name="title" value="Duke's Bookstore" direct="true"/>


                <tt:parameter name="banner" value="/banner.jsp" direct="false"/>


                <tt:parameter name="body" value="/bookstore.jsp" direct="false"/>


        </tt:screen>


        <tt:screen id="/catalog">


                <tt:parameter name="title" value="<%=messages.getString(\"TitleBookCatalog\")%>" direct="true"/>


                <tt:parameter name="banner" value="/banner.jsp" direct="false"/>


                <tt:parameter name="body" value="/catalog.jsp" direct="false"/>


        </tt:screen>


        <tt:screen id="/bookdetails">


                <tt:parameter name="title" value="<%=messages.getString(\"TitleBookDescription\")%>" direct="true"/>


                <tt:parameter name="banner" value="/banner.jsp" direct="false"/>


                <tt:parameter name="body" value="/bookdetails.jsp" direct="false"/>


        </tt:screen>


        <tt:screen id="/showcart">


                <tt:parameter name="title" value="<%=messages.getString(\"TitleShoppingCart\")%>" direct="true"/>


                <tt:parameter name="banner" value="/banner.jsp" direct="false"/>


                <tt:parameter name="body" value="/showcart.jsp" direct="false"/>


        </tt:screen>


        <tt:screen id="/cashier">


                <tt:parameter name="title" value="<%=messages.getString(\"TitleCashier\")%>" direct="true"/>


                <tt:parameter name="banner" value="/banner.jsp" direct="false"/>


                <tt:parameter name="body" value="/cashier.jsp" direct="false"/>


        </tt:screen>


        <tt:screen id="/receipt">


                <tt:parameter name="title" value="<%=messages.getString(\"TitleReceipt\")%>" direct="true"/>


                <tt:parameter name="banner" value="/banner.jsp" direct="false"/>


                <tt:parameter name="body" value="/receipt.jsp" direct="false"/>


        </tt:screen>


</tt:definition>

<%-- imports --%>
<%@ page import="java.util.*" %>

<%@ page import="com.wrox.store.ejb.product.*" %>

<jsp:useBean id="productManager" class="com.wrox.store.ejb.product.ProductManager" scope="session"/>
<%
	Collection coll = productManager.findAll();
	Product product;
	Iterator it = coll.iterator();
	while (it.hasNext()) {
		product = (Product)it.next();
%>
		<TR>
		<TD><%= product.getName() %></TD>
		<TD ALIGN="RIGHT">£<%= product.getPrice() %></TD>
		<TD ALIGN="RIGHT"><INPUT TYPE="TEXT" SIZE="2" MAXLENGTH="2" NAME="quantity_<%= product.getId()%>" VALUE=""></TD>
		</TR>
<%
	}
%>

<INPUT TYPE="HIDDEN" NAME="numberOfProducts" VALUE="<%= coll.size() %>">

<%-- imports --%>
<%@ page import="java.util.*" %>

<%@ page import="javax.naming.*" %>
<%@ page import="javax.rmi.PortableRemoteObject" %>

<%@ page import="com.wrox.store.ejb.product.*" %>

<%
	// create a new context and perform the lookup
	InitialContext ctx = new InitialContext();
	ProductHome home = (ProductHome)PortableRemoteObject.narrow(
		ctx.lookup("store/ProductHome"), ProductHome.class);

	// find all the products and iterate, displaying each in the table
	Product product;
	Collection coll = home.findAll();
	Iterator it = coll.iterator();
	while (it.hasNext()) {
		product = (Product)it.next();
%>
		<TR>
		<TD><%= product.getName() %></TD>
		<TD ALIGN="RIGHT">£<%= product.getPrice() %></TD>
		<TD ALIGN="RIGHT"><INPUT TYPE="TEXT" SIZE="2" MAXLENGTH="2"
			NAME="quantity_<%= product.getId()%>" VALUE=""></TD>
		</TR>
<%
	}
%>

<INPUT TYPE="HIDDEN" NAME="numberOfProducts" VALUE="<%= coll.size() %>">
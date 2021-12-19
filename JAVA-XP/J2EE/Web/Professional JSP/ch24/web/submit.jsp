<%-- imports --%>
<%@ page import="java.util.*" %>

<%@ page import="javax.ejb.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.rmi.PortableRemoteObject" %>

<%@ page import="com.wrox.store.ejb.cart.*" %>
<%@ page import="com.wrox.store.ejb.customer.*" %>
<%@ page import="com.wrox.store.ejb.product.*" %>
<%@ page import="com.wrox.store.exception.*" %>

<%@ include file="header.jsp" %>

<P>
<%
	// create a new customer object amd set the properties
	InitialContext ctx = new InitialContext();
	CustomerHome customerHome = (CustomerHome)PortableRemoteObject.narrow(ctx.lookup("store/CustomerHome"), CustomerHome.class);

	Customer customer = customerHome.create();

	customer.setName(request.getParameter("name"));
	customer.setAddress(request.getParameter("address"));
	customer.setZipCode(request.getParameter("zipCode"));
	customer.setEmailAddress(request.getParameter("emailAddress"));
	customer.setCreditCardNumber(request.getParameter("creditCardNumber"));
	
	try {
		// now submit order ... this method call wraps up the order
		// submission process such as e-mailing the customer with
		// a confirmation
		cart.submitOrder(customer);
%>
		Thank you for your order, please click here to return to the <A HREF="index.jsp">catalog</A>.
<%
	} catch (AuthorizationException ae) {
%>
		Sorry ... you're credit card could not be authorized.
<%
	}
%>
</P>

<%@ include file="footer.jsp" %>
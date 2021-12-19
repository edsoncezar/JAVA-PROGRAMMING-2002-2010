<%-- imports --%>
<%@ page import="java.util.*" %>

<%@ page import="javax.ejb.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.rmi.PortableRemoteObject" %>

<%@ page import="com.wrox.store.ejb.cart.*" %>
<%@ page import="com.wrox.store.ejb.product.*" %>

<%@ include file="header.jsp" %>

<P>
The contents of your shopping cart are as follows.

<FORM ACTION="submit.jsp" METHOD="POST">
<%
	InitialContext ctx = new InitialContext();
	ProductHome home = (ProductHome)PortableRemoteObject.narrow (ctx.lookup("store/ProductHome"), ProductHome.class);
	Product product;
	int quantity;

	// find out how many products there were on the catalog page
	int numberOfProducts = 0;
	if (request.getParameter("numberOfProducts") != null) {
		numberOfProducts = Integer.parseInt(request.getParameter("numberOfProducts"));
	}

	// and loop over each of the quantity_ parameters, adding
	// those products to the cart where the quantity is greater
	// that zero
	String param;
	for (int i = 1; i <= numberOfProducts; i++) {
		try {
			param = request.getParameter("quantity_" + i);

			if (param == null || param.length() == 0) {
				param = "0";
			}

			quantity = Integer.parseInt(param);
		} catch (NumberFormatException nfe) {
			quantity = 0;
		}

		if (quantity > 0) {
			product = home.findByPrimaryKey("" + i);
			cart.add(product, quantity);
		}
	}
%>

<TABLE>
<TR><TD><B>Name</B></TD><TD><B>Price</B></TD><TD><B>Quantity</B></TD><TD><B>Total</B></TD></TR>

<%
	String id;
	Iterator it = cart.getProducts().iterator();
	while (it.hasNext()) {
		product = (Product)it.next();
		quantity = cart.getQuantity(product);
%>
		<TR>
		<TD><%= product.getName() %></TD>
		<TD ALIGN="RIGHT">£<%= product.getPrice() %></TD>
		<TD ALIGN="RIGHT"><%= quantity %></TD>
		<TD ALIGN="RIGHT">£<%= quantity * product.getPrice() %></TD>
		</TR>
<%
	}
%>

<TR><TD COLSPAN="4" ALIGN="RIGHT">£<%= cart.getTotal() %></TD></TR>

</TABLE>
</P>

<P>
Next, please fill out details below and click the "Submit" button.
</P>

<P>
<TABLE>

<TR><TD>Name : </TD><TD><INPUT TYPE="TEXT" NAME="name" VALUE="" SIZE="48"></TD></TR>
<TR><TD>Address : </TD><TD><INPUT TYPE="TEXT" NAME="address" VALUE="" SIZE="48"></TD></TR>
<TR><TD>Zip code : </TD><TD><INPUT TYPE="TEXT" NAME="zipCode" VALUE="" SIZE="10"></TD></TR>
<TR><TD>E-mail address : </TD><TD><INPUT TYPE="TEXT" NAME="emailAddress" VALUE="" SIZE="48"></TD></TR>
<TR><TD>Credit card number : </TD><TD><INPUT TYPE="PASSWORD" NAME="creditCardNumber" VALUE="" SIZE="19"></TD></TR>

<TR><TD COLSPAN="2" ALIGN="RIGHT"><INPUT TYPE="SUBMIT" VALUE="Submit"></TD></TR>

</TABLE>

</FORM>
</P>

<%@ include file="footer.jsp" %>
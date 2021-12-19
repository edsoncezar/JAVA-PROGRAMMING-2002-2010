<%@ include file="header.jsp" %>

<%
	// which JSP should we include to generate the table/catalog of products
	String catalogPage = request.getParameter("catalogPage");
	if (catalogPage == null || catalogPage.length() == 0) {
		catalogPage = "scriptlet.jsp";
	}
	
	System.out.println("Using " + catalogPage + " to render the catalog");
%>

<P>
Please enter a quantity against the books you would like to purchase and click the "Add to Cart" button below.

<FORM ACTION="cart.jsp" METHOD="POST">
<TABLE>
<TR><TD><B>Name</B></TD><TD><B>Price</B></TD><TD><B>Quantity</B></TD></TR>

<jsp:include page="<%= catalogPage %>"/>

<TR><TD>View my <A HREF="cart.jsp">cart</A></TD><TD COLSPAN="2" ALIGN="RIGHT"><INPUT TYPE="SUBMIT" VALUE="Add to Cart"></TD></TR>

</TABLE>
</FORM>
</P>

<%@ include file="footer.jsp" %>
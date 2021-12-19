<%-- import the tag library --%>
<%@ taglib uri="http://www.wrox.com/taglib" prefix="chap20" %> 

<jsp:useBean id="productManager" class="com.wrox.store.ejb.product.ProductManager" scope="page"/>

<chap20:iterator id="product" className="com.wrox.store.ejb.product.Product" iterator="<%= productManager.findAll().iterator() %>">
	<TR>
	<TD><jsp:getProperty name="product" property="name"/></TD>
	<TD ALIGN="RIGHT">£<jsp:getProperty name="product" property="price"/></TD>
	<TD ALIGN="RIGHT"><INPUT TYPE="TEXT" SIZE="2" MAXLENGTH="2" NAME="quantity_<jsp:getProperty name="product" property="id"/>" VALUE=""></TD>
	</TR>
</chap20:iterator>

<INPUT TYPE="HIDDEN" NAME="numberOfProducts" VALUE="<%= productManager.findAll().size() %>">

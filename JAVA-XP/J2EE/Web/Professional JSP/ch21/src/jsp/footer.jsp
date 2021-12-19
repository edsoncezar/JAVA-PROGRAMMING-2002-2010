<hr>
<table cellspacing="2" cellpadding="2" border="0">
<tr>
    <td>
      <html:link page="/mainMenu.jsp">
        <bean:message key="link.mainMenu"/>
      </html:link>
    </td>
</tr>
<tr>
    <td>
    <%
      if(omitCheckoutLink == false) {
    %>
      <html:link page="/checkout.do?action=displayCart">
        <bean:message key="link.checkout"/>
      </html:link>
    <%
      } else {
    %>
      &nbsp;
    <%
      }
    %>
    </td>
</tr>
</table>


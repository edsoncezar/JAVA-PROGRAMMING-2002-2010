<table width="700" border="0" cellspacing="2" cellpadding="2">
  <tr>
    <td width="470"><IMG src="images/cdsgalore.gif"></td>
    <td width="120">
      <table cellspacing="2" cellpadding="2" border="0">
        <tr>
            <td><IMG src="images/cart.gif"></td>
        </tr>
        <tr>
    <%
      Locale locale = (Locale) session.getAttribute(Action.LOCALE_KEY);
      if (locale == null) {
        locale = java.util.Locale.getDefault();
      }

      ShoppingCart cart = (ShoppingCart) session.getAttribute(Constants.SHOPPING_CART_KEY);
      if(cart == null) {
        log("cart was null.");
        cart = new ShoppingCart();
      }
      cart.setLocale(locale);
    %>
            <td>Items: <%= cart.getItemCount() %></td>
        </tr>
        <tr>
            <td>Total: <%= cart.getTotal() %></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<hr>

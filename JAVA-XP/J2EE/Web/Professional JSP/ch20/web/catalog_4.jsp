<%@page import="java.text.*"%>
<html>
  <head>
    <title>Widget Industries</title>
    <link rel="stylesheet" media="all" title="Default Styles" 
          href="/ch20/widget.css">
  </head>
  <body>

  <p align="center" class="title">
  Here are the widgets we produce and distribute:
  </p>
  
  <p align="center">
  <table class="catalog_table" cellspacing="1" cellpadding="4">
    <tr class="catalog_header">
      <th>
        Widget ID
      </th>
      <th>
        Widget Name
      </th>
      <th>
        Widget Description
      </th>
      <th>
        Date Available
      </th>
    </tr>

    <jsp:useBean id="localWidgets" class="com.wrox.projsp.ch20.WidgetData" 
                 scope="request"/>
    <% while (localWidgets.next()) { %>
    <tr class="catalog_items">
      <td>
        D<%= localWidgets.getWidgetID() %>
      </td>
      <td>
        <%= localWidgets.getWidgetName() %>
      </td>
      <td>
        <%= localWidgets.getWidgetDescription() %>
      </td>
      <td>
        <%= localWidgets.getWidgetDate() %>
      </td>
    </tr>
    <% } localWidgets.closeWidget(); %>

    <jsp:useBean id="foreignWidgets" class="com.wrox.projsp.ch20.WidgetData"
                 scope="request"/>
    <% while (foreignWidgets.next()) { %>
    <tr class="catalog_items">
      <td>
        F<%= foreignWidgets.getWidgetID() %>
      </td>
      <td>
        <%= foreignWidgets.getWidgetName() %>
      </td>
      <td>
        <%= foreignWidgets.getWidgetDescription() %>
      </td>
      <td>
        <%= foreignWidgets.getWidgetDate() %>
      </td>
    </tr>
    <% } foreignWidgets.closeWidget(); %>

  </table>
  </p>
  
  </body>
</html>

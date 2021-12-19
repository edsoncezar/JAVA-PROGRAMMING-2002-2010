<%@page import="java.text.*"%>

<% System.out.println("index.jsp"); %>
<html>
  <head>
    <title>Widget Industries</title>
    <link rel="stylesheet" media="all" title="Default Styles"
          href="widget.css">
  </head>
  <body leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0">
  
    <%
      DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);
    %>

    <table width="100%" height="100%" border="0" 
           cellpadding="10" cellspacing="0">
      <tr>
        <td class="sidebar" align="left" valign="top">
          &nbsp;
          <br>
          <b>ABOUT US</b><br>
          <hr>
          <b>Widget&nbsp;Industries</b>&nbsp;was 
          founded by Abraham O. Ault in
          1855, when he and life-long friends
          Russell Newman and Mark Walter discovered
          the famous "Lost Norseman" widget mine
          in central Texas.
          <p>
          While today's widgets are artificially
          made, Mr. Ault's attention to detail
          and quality are very much present in
          our manufacturing facilities. <i>Our
          widget quality is unimpeachable.</i>
          <p>
          In recent years, Widget Industries
          has supplemented their leading position
          in widget sales with outsourcing
          and distribution services. Do you need
          some custom widgets made? We'll make them
          for you. Do you need a distribution
          channel for your own widgets? We'll
          put them in front of the top
          widget buyers in the world.
          <p>
          We hope you enjoy your stay here
          at our virtual headquarters!
          
        </td>
        <td align="center" width="100%" height="100%" 
                           valign="top" class="normal">
          <span class="title">Welcome to Widget Industries!</span>
          <br>
          <span class="quote">"If it can be fabricated,
                               we'll make it up, honest!"
          <p>
          <%= df.format(new java.util.Date()) %></span>
          <p>
          <hr>
          <br>
          This website has been created for our channel partners
          to view our current widget availability and ID numbers.
          <p>
          In subsequent months, we'll be upgrading our website
          to enable on-line ordering of widgets ("e-commerce")
          and two-way support messaging.
          <p>
          To order widgets, call us: <b>1-800-WIDGETS</b>
          <p>
          For technical inquiries, e-mail us: 
            <a href="mailto:webmaster@widgets.com">webmaster@widgets.com</a>
          <p>
          <table border="2" width="200" height="50"><tr><td align="center">
<a href="catalog.jsp"><b>Browse Our Catalog (catalog.jsp)</b></a>  <br>
<a href="catalog_2.jsp"><b>Browse Our Catalog (catalog_2.jsp)</b></a>  <br>
<a href="catalog_3.jsp"><b>Browse Our Catalog (catalog_3.jsp)</b></a>  <br>
<a href="widget/catalog_4.jsp"><b>Browse Our Catalog (catalog_4.jsp)</b></a>  <br>
<a href="widgetpool/catalog_5.jsp"><b>Browse Our Catalog (catalog_5.jsp)</b></a>  

          </td>
          </tr>
          </table>
        </td>
      </tr>
    </table>

  </body>
</html>

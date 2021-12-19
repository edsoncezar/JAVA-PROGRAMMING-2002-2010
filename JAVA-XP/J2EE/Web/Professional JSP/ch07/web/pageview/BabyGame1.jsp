<html>
<head><title>Baby Game - Your Guesses</title></head>
<body bgcolor="#FFFFFF">

<%@ page import="java.util.*,java.io.*" %>

<%
   String guesser = request.getParameter("guesser");

   String gender  = request.getParameter("gender");

   String pounds  = request.getParameter("pounds");
   String ounces  = request.getParameter("ounces");

   String month   = request.getParameter("month");
   String day     = request.getParameter("day");

   String length  = request.getParameter("length");

   if (guesser == null || gender == null || pounds == null || ounces == null
      || month == null || day == null || length == null)
   { %>

     <br>There where some choices that were not selected.<br><br>
     Sorry, but you must complete all selections to play.<br>
     <font size=-1>(Please hit the browser 'back' button to continue)</font><br>

<% } else { 

     //Store guess info and display to user
     Properties p = new Properties();
     p.setProperty("guesser", guesser);
     p.setProperty("gender", gender);
     p.setProperty("pounds", pounds);
     p.setProperty("ounces", ounces);
     p.setProperty("month", month);
     p.setProperty("day", day);
     p.setProperty("length", length);

     FileOutputStream outer = new FileOutputStream(guesser);
     p.store(outer, "Baby Game -- "+guesser+"'s guesses");
     outer.flush();
     outer.close();

%>

     <br><%= guesser %>, your choices have been stored.<br>
     Here they are:<br>

     <table border cols=5 width="75%" >
       <caption></caption>
       <tr>
         <td><%= gender %></td>
         <td><%= pounds %> lbs <%= ounces %> oz</td>
         <td><%= month %> <%= day %></td>
         <td><%= length %> inches</td>
       </tr>
     </table>

     <br>
<% } %>

</body>
</html>

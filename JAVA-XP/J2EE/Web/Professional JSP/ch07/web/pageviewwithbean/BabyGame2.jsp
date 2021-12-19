<html>
<head><title>Baby Game - Your Guesses</title></head>
<body bgcolor=FFFFFF>

<%@ page language="java" buffer="8k" %>

<jsp:useBean id="worker1" class="com.wrox.projsp.ch07.BabyGameWorker1"
 scope="request" />

<%-- Populate the JavaBean properties from the Request parameters. --%>
<%-- The semantics of the '*' wildcard is to copy all similarly-named --%>
<%-- request parameters into worker1 properties. --%>

<jsp:setProperty name="worker1" property="*" />

<% if(worker1.validate()) { 
     worker1.store();
%>
     <br> 
     <jsp:getProperty name="worker1" property="guesser" />, your choices have
     been stored.<br>  Here they are:<br>

     <table BORDER COLS=5 WIDTH="75%" >
       <caption></caption>
       <tr>
         <td><jsp:getProperty name="worker1" property="gender" /></td>
         <td><jsp:getProperty name="worker1" property="pounds" /> lbs
             <jsp:getProperty name="worker1" property="ounces" /> oz</td>
         <td><jsp:getProperty name="worker1" property="month" />
             <jsp:getProperty name="worker1" property="day" /></td>
         <td><jsp:getProperty name="worker1" property="length" /> inches</td>
       </tr>
     </table>

     <br>

<% } else { %>

     <br> There where some choices that were not selected.<br><br>
     Sorry, but you must complete all selections to play.<br>
     <font size=-1>(Please hit the browser 'back' button to continue)</font><br>

%>

<% } %>
</body>
</html>


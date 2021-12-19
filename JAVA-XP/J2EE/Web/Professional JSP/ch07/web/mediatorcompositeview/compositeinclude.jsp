<jsp:useBean id="worker2" class="com.wrox.projsp.ch07.BabyGameWorker2" scope="request" />

<%@ include file="nestedheader.html" %>

<% if(worker2.validate()) { 
     worker2.store();
%>
     <br> 
     <jsp:getProperty name="worker2" property="guesser" />, your choices have
     been stored.<br>  Here they are:<br>

     <table border cols=5 width="75%" >
       <caption></caption>
       <tr>
         <td><jsp:getProperty name="worker2" property="gender" /></td>
         <td><jsp:getProperty name="worker2" property="pounds" /> lbs
             <jsp:getProperty name="worker2" property="ounces" /> oz</td>
         <td><jsp:getProperty name="worker2" property="month" />
             <jsp:getProperty name="worker2" property="day" /></td>
         <td><jsp:getProperty name="worker2" property="length" /> inches</td>
       </tr>
     </table>

     <br>

<% } else { %>

     <br> There where some choices that were not selected.<br><br>
     Sorry, but you must complete all selections to play.<br>
     <font size=-1>(Please hit the browser 'back' button to continue)</font><br>

%>

<% } %>

<%@ include file="/mediatorcompositeview/nestedfooter.html" %>

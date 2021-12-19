<html>
<head><title>Baby Game - Your Guesses</title></head>
<body bgcolor="#FFFFFF">
<%@ page errorPage="errorPageSW.jsp" %>

<jsp:useBean id="SWworker" class="com.wrox.projsp.ch07.BabyGameWorkerSW"
 scope="request" />
<jsp:setProperty name="SWworker" property="*" />

<% SWworker.validationGuard(); %>

<br> 
<jsp:getProperty name="SWworker" property="guesser" />, here are your
previously stored choices:<br>

<table border cols=4 width="75%" >
  <caption></caption>
  <tr>
    <td><jsp:getProperty name="SWworker" property="gender" /></td>
    <td><jsp:getProperty name="SWworker" property="pounds" /> lbs
        <jsp:getProperty name="SWworker" property="ounces" /> oz</td>
    <td><jsp:getProperty name="SWworker" property="month" />
        <jsp:getProperty name="SWworker" property="day" /></td>
    <td><jsp:getProperty name="SWworker" property="length" /> inches</td>
  </tr>
</table>

<br>

</body>
</html>

<%
  String event = request.getParameter("EVENT");
  if (event == null) {
    event = "ENTER_RECORD";
  } else if (event.equals ("")) {
    event = "ENTER_RECORD";
  } else if (event.equals ("ADMIN")) {
%>
    <jsp:forward page="admin.jsp"/>
<%
  }
%>

<jsp:include page="header.jsp" flush="true" >
  <jsp:param name="title" value="Time Entry System" />
  <jsp:param name="heading" value="<%= event %>" />
</jsp:include>

<form action=time.jsp method=POST>

<%
  // main dispatch center
  if (event.equals ("ENTER_RECORD")) {
%>
    <jsp:include page="enterRecord.jsp" flush="true"/>
<%
  } else if (event.equals ("NEW_RECORD")) {
%>
    <jsp:include page="newRecord.jsp" flush="true"/>
<%
  } else if (event.equals ("SUMMARY")) {
%>
    <jsp:include page="summary.jsp" flush="true"/>
<%
  } else {
           throw new JspException ("illegal event of " + event);
  }
%>

</form>

<hr>
<form action=time.jsp method=POST>
    <input type=hidden name=EVENT value=SUMMARY>
    <input type=submit value=Summary>
</form>

</body>
</html>

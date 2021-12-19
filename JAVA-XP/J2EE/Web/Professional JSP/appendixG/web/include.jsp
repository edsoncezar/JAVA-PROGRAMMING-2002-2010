<%! java.util.Random random =
   new java.util.Random(System.currentTimeMillis()); %>
<% if (random.nextInt() % 2 != 0) { %>
<jsp:include page="time.jsp" flush="true"/>
<% } else { %>
<jsp:include page="string.jsp" flush="true"/>
<% } %>

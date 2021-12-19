<html>
<head><title>Baby Game - Your Guesses</title></head>
<body bgcolor="#FFFFFF">

<%@ include file="gameheader.html" %>

<%@ page language="java" buffer="8k" %>
<jsp:useBean id="worker2" class="com.wrox.projsp.ch07.BabyGameWorker2" scope="request" />
<jsp:setProperty name="worker2" property="*" />

<jsp:include page="/mediatorcompositeview/compositeinclude.jsp" flush="true" />

<%@ include file="/mediatorcompositeview/gamefooter.html" %>

</body>
</html>

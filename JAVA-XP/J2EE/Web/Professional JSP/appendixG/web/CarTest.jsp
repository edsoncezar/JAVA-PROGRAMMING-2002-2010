<html>
<head>
<title>
Bean Test
</title>
</head>

<body>

<jsp:useBean id="carBean" scope="session" 
             class="com.wrox.projsp.appG.carbeans.CarCost" />

Setting the Car cost and the Car Type:<p>

<% carBean.setCar("Ford");
   carBean.setCost(2000);
%>
Bean values have been set <p>

Getting the Car cost and the Car Type:<p>
State is: <%= carBean.getCar() %> <br>
Rate is: <%= carBean.getCost() %>

</body>
</html>

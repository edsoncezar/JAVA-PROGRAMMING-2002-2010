<?xml version="1.0" ?>
<%@ page language="java" import="com.wrox.projsp.ch15.travelbase.*"  %>
<%!
public DealsFinder myFinder;
public void jspInit() {
         myFinder = new DealsFinder();
         myFinder.init();
}
%>
<% 
   String myStr = myFinder.locateDealsXML();
 %>
<selloff>
<%= myStr %>
</selloff>

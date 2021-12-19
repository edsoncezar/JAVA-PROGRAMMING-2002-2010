<%@ taglib uri="/WEB-INF/xpath.tld" prefix="xpath" %>
<jsp:useBean id="discussion" scope="session" class="com.wrox.projsp.ch11.jsp.DiscussionBean"/>
<html>
<head> <title> Discussion - Reply </title> </head>
<body>
  <form name="discussion" action="discuss.do" method="post">
    <input 
      type="hidden" 
      name="submitReplyForm" 
      value="<%= discussion.get("requestReplyForm") %>" >
    author: 
    <input 
        type="text" name="author" 
        value=" <%= discussion.get("author") %>"><br>
    email: 
    <input 
        type="text" 
        name="email" 
        value=" <%= discussion.get("email") %>"><br>
    subject: 
    <input 
        type="text" 
        name="subject" 
        value=" <%= discussion.get("subject") %>"><br>
    message: 
    <input 
        type="text" 
        name="body" 
        value=" <%= discussion.get("body") %>"><br>
    <input 
        type="submit" 
        name="perform" 
        value="submit">
  </form>
</body>
<html>

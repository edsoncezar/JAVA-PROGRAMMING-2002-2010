<%@ taglib uri="/WEB-INF/xpath.tld" prefix="xpath" %>
<jsp:useBean id="discussion" scope="session" 
  class="com.wrox.projsp.ch11.jsp.DiscussionBean"/>
<html>
  <head>
    <title> Discussion </title>
  </head>
  <body>
  
    <%-- a form to specify min no of nodes for messages to appear open --%>
    <form name="discussion" action="discuss.do" method="post">
        Pre-open messages with  
        <input type="text" name="minVotes" 
            value=" <%= discussion.get("minVotes") %>" 
            size="3"> or more votes 
        <input type="submit" name="changeMinVote" value="ok">
    </form>
    
    <%-- the messages --%>
    <xpath:nodes 
        id='message'
        context='<%= discussion.getDiscussion() %>' 
        xpath='<%= "//message[@id > 0]" %>'> 
  
      <% String id = message.getPathValue("@id"); %>
      
      <%-- the header line --%>
      <% int level = Integer.parseInt(message.getPathValue("@level")); %>
      <% while(level-- > 0){ %>
           &nbsp;&nbsp;&nbsp;&nbsp;
      <% } %>
      <xpath:value xpath='@author' /> : 
      <a href='discuss.do?openMessage="<%= id %>"'>
          <xpath:value xpath='<%= "subject/text()" %>' />
      </a> · 
      <xpath:value xpath='@votes'/> votes · 
      <xpath:value xpath='@date' /><br>
      
      <%-- the conditional body --%>
      <xpath:nodes 
          id='body' 
          context='<%= message.getNode() %>' 
          xpath='<%= 
              "self::node()[" +
                  "@votes >= " + discussion.get("minVotes") + 
                  " or @id = " + discussion.get("openMessage") + 
               "]/body" %>'>
        <table bgColor="yellow" width="70%" align="center"> <tr><td><br>
          <xpath:value xpath='<%= "text()" %>' /><p>
          <a href='discuss.do?requestReplyForm=<%= id %>'>reply</a> | 
          <a href='discuss.do?vote=<%= id %>' >vote!</a>
        <br></td></tr></table>
      </xpath:nodes>
    </xpath:nodes>
  </body>   
</html>   


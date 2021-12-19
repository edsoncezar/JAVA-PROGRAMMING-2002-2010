<html> 
<head> 
<title>Feedback Results</title> 
</head> 
  <body>

    <h2>Hey, my favourite word is 
    <% 
    // Get form fields passed in from the form submission 
    String word = request.getParameter("faveWord"); 
    out.println(word); 
    %> 
    too...how strange </h2> 

  </body> 
</html> 

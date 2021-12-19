    //**************************************
    //     
    // Name: AcMakerWml
    // Description:making accounts with wml 
    //     &html
    // By: Bogomil Shopov
    //
    //This code is copyrighted and has    // limited warranties.Please see http://
    //     www.Planet-Source-Code.com/xq/ASP/txtCod
    //     eId.326/lngWId.8/qx/vb/scripts/ShowCode.
    //     htm    //for details.    //**************************************
    //     
    
    ****************** 
    config.php 
    ****************** 
    <? 
    $server="DBhost"; 
    $user="DBUSER"; 
    $pass="DBpassword"; 
    $dbname="DBNAME"; 
    ?> 
    ****************** 
    create_db.php 
    ****************** 
    <? 
    require("config.php"); 
    error_reporting(15); 
    $connect_db=mysql_connect($server,$user,$pass); 
    $create_db=mysql_create_db($dbname); 
    mysql_query($create_db); 
    mysql_select_db($dbname); 
    $create_tbl=("CREATE TABLE logs (id INT (3) not null AUTO_INCREMENT, 
    user VARCHAR (21) not null , 
    pass VARCHAR (21) not null , 
    name VARCHAR (35) not null , 
    email VARCHAR(42) not null , 
    PRIMARY KEY (id))"); 
    $rezult=mysql_query($create_tbl); 
    ?> 
    ******************* 
    register.html 
    ******************* 
    <!doctype html public "-//W3C//DTD HTML 4.0 //EN"> 
    <html> 
    <head> 
    <title>Register! 
    </title> 
    </head> 
    <body> 
    <form action = register.php method=post> 
    <center> 
    ID:<br> 
    <input type= text name=username size=14> 
    <br>Password:<br> 
    <input type= password name=password size=14> <br> 
    Name:<br> 
    <input type=text name=name size=22><br> 
    E-mail:<br> 
    <input type =text name=email size=22><br> 
    <input type= submit value="Register..!"> 
    </center> 
    </body> 
    </html> 
    ******************* 
    register.php 
    ******************* 
    <? 
    require("config.php"); 
    mysql_connect($server,$user,$pass); 
    mysql_select_db($dbname); 
    $insert_data="INSERT INTO logs (user,pass,name,email) 
    VALUES ('$username','$password','$name','$email')"; 
    mysql_query($insert_data); 
    mail($email,"Registration","You are registered in our database \n user:$username \n pass:$password "); 
    echo"<center>Thank you!<br><a href=login.html>Login!</a>"; 
    ?> 
    ************** 
    login.html 
    ************** 
    <!doctype html public "-//W3C//DTD HTML 4.0 //EN"> 
    <html> 
    <head> 
    <title>login! 
    </title> 
    </head> 
    <body> 
    <form action =login.php method=post> 
    <center> 
    ID:<br> 
    <input type=text name=username size=14> 
    <br>Password:<br> 
    <input type=password name=password size=14> <br> 
    <input type=submit value="Login..!"> 
    </center> 
    </body> 
    </html> 
    **************** 
    login.php 
    **************** 
    <? 
    mysql_connect($server,$user,$pass); 
    mysql_select_db($dbname); 
    $select_data=("SELECT name FROM logs WHERE pass='$password'AND user='$username' "); 
    $ver=mysql_query($select_data); 
    $string=mysql_fetch_array($ver); 
    if ($string==0){ 
    echo"<center>You are not in database<br> 
    <a href=register.html>Register</a></center> 
    "; 
    } 
    else 
    echo"<center><font color=blue size=+2>Welcome $string[0]</font></center>"; 
    ?> 
    ********************** 
    register.wml 
    ********************** 
    <?xml version="1.0"?> 
    <!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.3//EN" "http://www.wapforum.org/DTD/wml13.dtd"> 
    <wml> 
    <template> 
    <do type="prev"><prev/></do> 
    </template> 
    <card id="card1" title="Register" newcontext="true"> 
    <p align="center"> 
    ID:<input name="username" size="15" /> 
    Password:<input name="password" type="password" /> 
    Name:<input name="name" size="10" /> 
    E-mail:<input name="email" size="10" /> 
    <do type="Send" label="Send"> 
    <go href="register.php" method="post" accept-charset="unknown"> 
    <postfield name="username" value="$username"/> 
    <postfield name="email" value="$email"/> 
    <postfield name="password" value="$password"/> 
    <postfield name="name" value="$name"/> 
    </go> 
    </do> 
    </p> 
    </card> 
    </wml>

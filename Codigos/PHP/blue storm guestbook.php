//**************************************
    //     
    // Name: blue storm guestbook
    // Description:by chujian
    // By: PHP Code Exchange
    //**************************************
    //     
    
    <html> 
    <head> 
    <title>蓝色风暴流言本</title> 
    <style> 
    <!-- 
    A:link {text-decoration: none ; color:0000ff} 
    A:visited {text-decoration: none; color:004080} 
    A:active {text-decoration: none} 
    A:hover {text-decoration: underline; color:ff0000} 
    --> 
    </style> 
    <body bgcolor=#FFFFCC> 
    <center><IMG SRC="images/1(1).gif"></center> 
    <div align="center"> 
    <center> 
    <table border="1" width="39%" height="19" cellspacing="0" cellpadding="0" bordercolorlight="#000000"> 
    <tr> 
    <td width="100%" colspan="2" height="16"> 
    <p align="center"><b><font color="#00FF00" size="4">填写你的留言</font></b></td> 
    </tr> 
    <tr> 
    <td width="41%" height="19" valign="bottom"> 
    <p align="center"><font size="2" color="#00FF00">姓名</font></td> 
    </center> 
    <td width="81%" height="19" rowspan="4"> 
    <form method="POST" action="2.php"> 
    <p align="left"><font color="#00FF00"><input type="text" name="name" size="20"><br> 
    <input type="text" name="email" size="20"><br> 
    <select size="1" name="bq"> 
    <option value="">请选择你的留言表情</option> 
    <option value="images/m12.gif">正常表情</option> 
    <option value="images/m1.gif">欢笑</option> 
    <option value="images/m10.gif">伤脑筋</option> 
    <option value="images/m11.gif">我好气呀</option> 
    <option value="images/m13.gif">好可怕哦</option> 
    <option value="images/m14.gif">让我想想</option> 
    <option value="images/m15.gif">好可笑哦</option> 
    <option value="images/m16.gif">愤怒</option> 
    <option value="images/m3.gif">好可怜呀</option> 
    <option value="images/m4.gif">唱歌</option> 
    <option value="images/m5.gif">大哭</option> 
    <option value="images/m6.gif">O K</option> 
    <option value="images/m7.gif">好消息</option> 
    <option value="images/m8.gif">我好火</option> 
    <option value="images/m9.gif">避开</option> 
    <option value="images/m2.gif">看我的</option> 
    </select><br> 
    <textarea rows="4" name="message" cols="28"></textarea><br> 
    <input type="submit" value="好了" name="B1">                 
    <input type="reset" value="重写" name="B2"></font></p> 
    </form> 
    </td> 
    </tr> 
    <center> 
    <tr> 
    <td width="41%" height="13" valign="bottom"> 
    <p align="center"><font size="2" color="#00FF00">依妹儿</font></td> 
    </tr> 
    <tr> 
    <td width="41%" height="7" valign="bottom"> 
    <p align="center"><font size="2" color="#00FF00">表情</font></td> 
    </tr> 
    <tr> 
    <td width="41%" height="96" valign="top"> 
    <p align="center"><b><font color="#00FF00"><br> 
    留<br> 
    言<br> 
    内<br> 
    容</font></b></td> 
    </tr> 
    </table> 
    </center> 
    </div> 
    <hr> 
    <? 
    $fp = fopen( "guest.txt", "r"); 
    $print = fread($fp,filesize( "guest.txt")); 
    fclose($fp); 
    print "$print"; 
    ?> 
    <center><b><font color=red>Copyright? 2000</font> by <a href="mailto:chujian@990.net">bigmouse</a></center></b> 
    </body> 
    </html>

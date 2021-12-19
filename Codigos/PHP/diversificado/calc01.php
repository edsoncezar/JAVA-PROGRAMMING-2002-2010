<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0058)http://www.thickbook.com/srccode/phpessentials/calc01.phps -->
<HTML><HEAD>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META content="MSHTML 6.00.2600.0" name=GENERATOR></HEAD>
<BODY><CODE><FONT 
color=#000000>&lt;!DOCTYPE&nbsp;HTML&nbsp;PUBLIC&nbsp;"-//W3C//DTD&nbsp;HTML&nbsp;3.2//EN"&gt; 
<BR>&lt;HTML&gt; <BR>&lt;HEAD&gt; 
<BR>&lt;TITLE&gt;Calculate&nbsp;Values&lt;/TITLE&gt; <BR>&lt;/HEAD&gt; 
<BR>&lt;BODY&gt; <BR><BR><FONT color=#0000bb>&lt;?php 
<BR><BR>$price&nbsp;</FONT><FONT color=#007700>=&nbsp;</FONT><FONT 
color=#0000bb>10.00</FONT><FONT color=#007700>; <BR></FONT><FONT 
color=#0000bb>$sales_tax&nbsp;</FONT><FONT color=#007700>=&nbsp;</FONT><FONT 
color=#0000bb>.0825</FONT><FONT color=#007700>; <BR></FONT><FONT 
color=#0000bb>$quantity&nbsp;</FONT><FONT color=#007700>=&nbsp;</FONT><FONT 
color=#0000bb>4</FONT><FONT color=#007700>; <BR><BR></FONT><FONT 
color=#0000bb>$sub_total&nbsp;</FONT><FONT color=#007700>=&nbsp;</FONT><FONT 
color=#0000bb>$price&nbsp;</FONT><FONT color=#007700>*&nbsp;</FONT><FONT 
color=#0000bb>$quantity</FONT><FONT color=#007700>; <BR><BR></FONT><FONT 
color=#0000bb>$sales_tax_amount&nbsp;</FONT><FONT 
color=#007700>=&nbsp;</FONT><FONT color=#0000bb>$sub_total&nbsp;</FONT><FONT 
color=#007700>*&nbsp;</FONT><FONT color=#0000bb>$sales_tax</FONT><FONT 
color=#007700>; <BR><BR></FONT><FONT 
color=#0000bb>$grand_total&nbsp;</FONT><FONT color=#007700>=&nbsp;</FONT><FONT 
color=#0000bb>$sub_total&nbsp;</FONT><FONT color=#007700>+&nbsp;</FONT><FONT 
color=#0000bb>$sales_tax_amount</FONT><FONT color=#007700>; 
<BR><BR>echo&nbsp;</FONT><FONT 
color=#dd0000>"&lt;P&gt;You&nbsp;ordered&nbsp;$quantity&nbsp;bags&nbsp;of&nbsp;coffee.&lt;/p&gt;"</FONT><FONT 
color=#007700>; <BR>echo&nbsp;</FONT><FONT 
color=#dd0000>"&lt;P&gt;Bags&nbsp;of&nbsp;coffee&nbsp;are&nbsp;$price&nbsp;each.&lt;/p&gt;"</FONT><FONT 
color=#007700>; <BR>echo&nbsp;</FONT><FONT 
color=#dd0000>"&lt;P&gt;Your&nbsp;subtotal&nbsp;is&nbsp;$sub_total.&lt;/p&gt;"</FONT><FONT 
color=#007700>; <BR>echo&nbsp;</FONT><FONT 
color=#dd0000>"&lt;P&gt;Sales&nbsp;tax&nbsp;is&nbsp;$sales_tax&nbsp;in&nbsp;this&nbsp;location.&lt;/p&gt;"</FONT><FONT 
color=#007700>; <BR>echo&nbsp;</FONT><FONT color=#dd0000>"&lt;P&gt;</FONT><FONT 
color=#007700>\$</FONT><FONT 
color=#dd0000>$sales_tax_amount&nbsp;has&nbsp;been&nbsp;added&nbsp;to&nbsp;your&nbsp;order.&lt;/p&gt;"</FONT><FONT 
color=#007700>; <BR>echo&nbsp;</FONT><FONT 
color=#dd0000>"&lt;P&gt;You&nbsp;owe&nbsp;$grand_total&nbsp;for&nbsp;your&nbsp;coffee.&lt;/p&gt;"</FONT><FONT 
color=#007700>; <BR><BR></FONT><FONT color=#0000bb>?&gt; 
<BR></FONT><BR><BR>&lt;/BODY&gt; <BR>&lt;/HTML&gt; 
<BR></FONT></CODE></BODY></HTML>

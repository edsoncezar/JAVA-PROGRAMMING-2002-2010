 #!/usr/local/bin/perl5 -T
    push(@INC, "/cgi-bin");
    require ("cgi-lib.pl");
    # First, get the data from the user's form.
    &ReadParse(*input);
    # Next, read in the correct answers from the appropriate 
    # answer file.
    unless (open(ANSWERFILE, "$input{'answerfile'}")) {
    die("cannot open input file $input{'answerfile'}\n\n");
    }
    @answers = <ANSWERFILE>;
    chop(@answers);
    # $cmehours = the number of CME credits this particular 
    # CME unit is good for
    # $coursename = the name of this particular CME course
    # $qnumber = the number of questions on this particular 
    # CME test -- it will be used to calculate
    # the user's grade on the test
    $cmehours = $input{'cmehours'};
    $coursename = $input{'coursename'};
    $qnumber = $input{'qnumber'};
    # Now, compare the user's answers to the correct ones 
    # and calculate the score.
    $count = 0;
    if ($input{'q1'} eq $answers[0]) {
    $count++;
    }
    if ($input{'q2'} eq $answers[1]) {
    $count++;
    }
    if ($input{'q3'} eq $answers[2]) {
    $count++;
    }
    if ($input{'q4'} eq $answers[3]) {
    $count++;
    }
    if ($input{'q5'} eq $answers[4]) {
    $count++;
    }
    if ($input{'q6'} eq $answers[5]) {
    $count++;
    }
    if ($input{'q7'} eq $answers[6]) {
    $count++;
    }
    if ($input{'q8'} eq $answers[7]) {
    $count++;
    }
    if ($input{'q9'} eq $answers[8]) {
    $count++;
    }
    if ($input{'q10'} eq $answers[9]) {
    $count++;
    }
    $score = ($count * 100 / $qnumber);
    # If they passed, give them a passing grade and let 
    #them register for their CME credits. 
    #Otherwise, ask them to take the test again.
    if ($score < 80) {
    print &PrintHeader;
    print <<"print_tag";
    <html>
    <head>
    <title>Online CME Exam </title>
    </head>
    <body>
    <h1 align=center> 
    Online CME Exam 
    </h1>
    <hr size=7>
    <h4>
    I'm sorry, but your score was only $score %. 
    A score of 80 % or higher is required to pass.
    <P>
    Please review the online CME materials and 
    retake the test.
    </h4>
    <hr size=7>
    <P>
    <CENTER><B>
    Please send questions or feedback to: 
    Michael Richardson, M.D.</B><br>
    <A HREF="mailto:mrich@u.washington.edu">
    mrich@u.washington.edu</A></CENTER>
    </body>
    </html>
    print_tag
    }
    else{
    print &PrintHeader;
    print <<"print_tag";
    <html>
    <head>
    <title>Online CME Exam </title>
    </head>
    <body>
    <h1 align=center> 
    Online CME Exam 
    </h1>
    <hr size=7>
    <CENTER><h2>
    Thank you very much for participating in our online 
    CME program!</H2></CENTER>
    <P>
    <CENTER>
    <h3>
    Congratulations! You got $count questions right, 
    for a score of $score %.
    </h3>
    </CENTER>
    <hr size=7>
    <P>
    <FORM ACTION=
    "http://weber.u.washington.edu/~mrich/cgi-bin/fxcertificate.cgi" 
    ENCTYPE=x-www-form-encoded METHOD=POST>
    <H3><CENTER>
    To register for $cmehours Category I CME credits 
    and print your CME certificate online, please 
    submit the following registration 
    information to RSNA.</CENTER></H3>
    <INPUT TYPE="hidden" NAME="coursename" VALUE="$coursename">
    <INPUT TYPE="hidden" NAME="cmehours" VALUE="$cmehours">
    <INPUT TYPE="hidden" NAME="score" VALUE="$score">
    <P><CENTER>
    <TABLE WIDTH="450" BORDER="1" CELLSPACING="2" 
    CELLPADDING="0">
    <TR>
    <TD WIDTH="33%"> <B>Name</B>:</TD>
    <TD WIDTH="67%"> <INPUT NAME="name" TYPE="text" 
    SIZE="43"></TD></TR>
    <TR>
    <TD> <B>Address</B>:</TD>
    <TD> <INPUT NAME="address" TYPE="text" 
    SIZE="43"></TD></TR>
    <TR>
    <TD> <B>City</B>:</TD>
    <TD> <INPUT NAME="city" TYPE="text" 
    SIZE="43"></TD></TR>
    <TR>
    <TD> <B>State / Province</B>:</TD>
    <TD> <INPUT NAME="state" TYPE="text" 
    SIZE="30"></TD></TR>
    <TR>
    <TD><B> Postal code:</B></TD>
    <TD> <INPUT NAME="zip" TYPE="text" 
    SIZE="30"></TD></TR>
    <TR>
    <TD> <B>Country</B>:</TD>
    <TD> <INPUT NAME="country" TYPE="text" 
    SIZE="30"></TD></TR>
    <TR>
    <TD><B> Day phone:</B></TD>
    <TD> <INPUT NAME="phone" TYPE="text" 
    SIZE="22"></TD></TR>
    <TR>
    <TD> <B>Fax</B>:</TD>
    <TD> <INPUT NAME="fax" TYPE="text" 
    SIZE="22"></TD></TR>
    <TR>
    <TD><B> e-mail address:</B></TD>
    <TD> <INPUT NAME="email" TYPE="text" 
    SIZE="43"></TD></TR>
    </TABLE>
    </CENTER></P>
    <P><CENTER><HR><FONT SIZE=+2>
    CMEJ Evaluation<
    /FONT><HR></CENTER></P>
    <P><CENTER><TABLE BORDER="1" 
    CELLSPACING="2" CELLPADDING= "2">
    <TR>
    <TD><B>
    Please select one choice for each question
    </B></TD>
    <TD><P><CENTER><B>
    Strongly Agree</B></CENTER></TD>
    <TD><P><CENTER><B>
    Agree</B></CENTER></TD>
    <TD><P><CENTER><B>
    Disagree</B></CENTER></TD>
    <TD><P><CENTER><B>
    Strongly Disagree</B></CENTER></TD></TR>
    <TR>
    <TD HEIGHT="30">The learning objectives for the 
    article were clearly stated.</TD>
    <TD><P><CENTER><INPUT TYPE="radio" 
    VALUE="strongly agree" NAME="clear" CHECKED="true">
    </CENTER></TD>
    <TD><P><CENTER><INPUT TYPE="radio" 
    VALUE="agree" NAME="clear"></CENTER></TD>
    <TD><P><CENTER><INPUT TYPE="radio" 
    VALUE="disagree" NAME="clear"></CENTER></TD>
    <TD><P><CENTER><INPUT TYPE="radio" 
    VALUE="strongly disagree" NAME="clear">
    </CENTER></TD></TR>
    <TR>
    <TD HEIGHT="17">
    The article met its learning objectives.</TD>
    <TD><P><CENTER><INPUT TYPE="radio" 
    VALUE="strongly agree" NAME="metobjectives"
    CHECKED="true"></CENTER></TD>
    <TD><P><CENTER><INPUT TYPE="radio" 
    VALUE="agree" NAME="metobjectives"></CENTER></TD>
    <TD><P><CENTER><INPUT TYPE="radio" 
    VALUE="disagree" NAME="metobjectives"></CENTER></TD>
    <TD><P><CENTER><INPUT TYPE="radio" 
    VALUE="strongly disagree" NAME="metobjectives">
    </CENTER></TD></TR>
    <TR>
    <TD HEIGHT="17">The content was presented in 
    a well-organized manner.</TD>
    <TD><P><CENTER><INPUT TYPE="radio" 
    VALUE="strongly agree" NAME="organized" 
    CHECKED="true"></CENTER></TD>
    <TD><P><CENTER><INPUT TYPE="radio" 
    VALUE="agree" NAME="organized"></CENTER></TD>
    <TD><P><CENTER><INPUT TYPE="radio" 
    VALUE="disagree" NAME="organized"></CENTER></TD>
    <TD><P><CENTER><INPUT TYPE="radio" 
    VALUE="strongly disagree" NAME="organized">
    </CENTER></TD></TR>
    <TR>
    <TD HEIGHT="17">The content was balanced, objective, 
    and scientifically rigorous.</TD>
    <TD><P><CENTER><INPUT TYPE="radio" 
    VALUE="strongly agree" NAME="balanced" 
    CHECKED="true"></CENTER></TD>
    <TD><P><CENTER><INPUT TYPE="radio" 
    VALUE="agree" NAME="balanced"></CENTER></TD>
    <TD><P><CENTER><INPUT TYPE="radio" 
    VALUE="disagree" NAME="balanced"></CENTER></TD>
    <TD><P><CENTER><INPUT TYPE="radio" 
    VALUE="strongly disagree" NAME="balanced">
    </CENTER></TD></TR>
    <TR>
    <TD HEIGHT="17">The information was current and 
    clnically relevant.</TD>
    <TD><P><CENTER><INPUT TYPE="radio" 
    VALUE="strongly agree" NAME="current" 
    CHECKED="true"></CENTER></TD>
    <TD><P><CENTER><INPUT TYPE="radio" 
    VALUE="agree" NAME="current"></CENTER></TD>
    <TD><P><CENTER><INPUT TYPE="radio" 
    VALUE="disagree" NAME="current"></CENTER></TD>
    <TD><P><CENTER><INPUT TYPE="radio" 
    VALUE="strongly disagree" NAME="current">
    </CENTER></TD></TR>
    <TR>
    <TD HEIGHT="17">Topics I would like to see 
    addressed in the future:</TD>
    <TD COLSPAN="4"><P><CENTER><TEXTAREA 
    NAME="topics" ROWS="3" COLS="47" WRAP=PHYSICAL>
    </TEXTAREA></CENTER></TD></TR>
    </TABLE>
    </CENTER></P>
    <P><HR ALIGN=LEFT>
    <P><CENTER><INPUT NAME="submit" 
    TYPE="submit" VALUE="Send registration information">
    <INPUT NAME="reset" TYPE="reset" VALUE="Clear form">
    </CENTER>
    </FORM>
    <P>
    <hr size=7>
    <P>
    <CENTER><B>Please send questions or feedback 
    to: Michael Richardson, M.D.</B><br>
    <A HREF="mailto:mrich@u.washington.edu">
    mrich@u.washington.edu</A>
    </CENTER>
    </body>
    </html>
    print_tag
    }


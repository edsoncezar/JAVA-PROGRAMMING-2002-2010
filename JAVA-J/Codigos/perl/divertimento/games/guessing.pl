 =**************************************
    = Name: Guessing Game
    = Description:This is a Guessing game wr
    =     itten in Perl. It is fairly simple if yo
    =     u have a concept of Perl and html. Enjoy
    =     , please rate me!
    = By: Jason DeLuca
    =
    = Side Effects:May need to change addres
    =     s to Perl on first line
    =
    =This code is copyrighted and has    = limited warranties.Please see http://w
    =     ww.Planet-Source-Code.com/vb/scripts/Sho
    =     wCode.asp?txtCodeId=378&lngWId=6    =for details.    =**************************************
    
    #!C:\Perl\bin\perl.exe
    # Description: Guessing Game
    use CGI qw(:standard);
    print header;
    print start_html('A Guessing Game'),
    h1('A Guessing Game'),
    	h3('Pick A Number, If Your Right, you Win!'),
    	h4('1-50'),
    start_form,
    "What's your Guess? ",textfield('num'),
    p,
    submit,
    end_form,
    hr;
    if (param()) {
    $num = param('num');
    	$ans = int(rand(50));
    	$result = abs($ans-$num);
    if ($num == $ans) {
    		print "You Got It!"
    		}
    	else {
    	print 
    	"The Answer was ",$ans,
    	p,
    	"Your Guess Was ",$num,
    	p,
    	"You Were ",$result," Off",
    	hr;
    	}
    }
    print end_html;


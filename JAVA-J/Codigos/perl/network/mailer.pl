 use Tk;
    use Tk::FileDialog;
    use Tk::DialogBox;
    use Mail::Sender;
    my $templ_directory = "templates";
    ## creo la finestra
    my $mw = new MainWindow;
    $mw->configure(-title=>'The smtp client');
    $mw->Label(-text => 'To:',)->pack;
    $e = $mw->Entry(-textvariable => \$to)->pack;
    $mw->Label(-text => 'Subject:', )->pack;
    $e = $mw->Entry(-textvariable => \$subj)->pack;
    $f = $mw->Frame->pack(-side => 'top', -fill => 'x');
    $mw->Label(-textvariable => \$infoalleg, -relief => 'ridge')-> ## barra di stato
    pack(-side => 'bottom', -fill => 'x');
    ## area testo
    $t = $mw->Scrolled("Text")->pack(-side => 'bottom',
    -fill => 'both', -expand => 1);
    $t->configure(-font => 'Tk::Font=SCALAR(0x234d8d8)'); ## ingrandisco la font di default
    ## bottone "invia"
    $f->Button(-text => "Send", -command => \&invia_mail)->
    pack(-side => 'right', -anchor => 'e');
    ## bottone "Allega"
    $f->Button(-text => "Attachment", -command => \&allega_file)->
    pack(-side => 'right', -anchor => 'e');
    ## bottone "Opzioni"
    $f->Button(-text => "Options", -command => \&options_win)->
    pack(-side => 'left', -anchor => 'e');
    ## bottone "About"
    $f->Button(-text => "About", -command => \&about_win)->
    pack(-side => 'left', -anchor => 'e');
    get_profile();## inizializzo $smtp e $email
    MainLoop;
    sub get_profile### inizializzo le variabili $email e $smtp dal file 'info' per la comunicazione con il server --------------------------------------
    {
    		if (open(FH,'info.txt'))
    		{
    			while(<FH>)
    			{
    				($key,$value) = split(/=/,$_);
    				if ($key eq 'email')
    				{
    					chomp($value);
    					$email = $value;
    					next;
    				}
    				if ($key eq 'smtp')
    				{
    					chomp($value);
    					$smtp = $value;
    					next;
    				}
    			}
    		}
    		else
    		{
    			$email = "";
    			$smtp = "";
    			$mw->withdraw();
    			options_win();
    		}
    }
    sub allega_file
    {
    $fname = $mw->getOpenFile;
    if (!open(FH, "$fname"))
    {
    	return;
    }
    $infoalleg = "ALLEGATO: $fname";
    close (FH);
    }
    sub invia_mail ## invio email
    {
    		$_ = $to;
    		if ($to eq "" || !(/\./) || !(/@/))
    		{
    			$dlgbox = $mw->DialogBox(-title=>'Error');
    		$dlgbox->add('label', "You have to fill the \"to:\" field correctly");
    		$dlgbox->Show;
    		return;
    		}
    		$sender = new Mail::Sender {smtp => $smtp, from => $email};
    		if($fname eq "" || (!open(FH,$fname)) )		## nessun allegato
    		{
    			$sender->MailMsg({to => $to, subject => $subj, msg => $t->get("1.0", "end")});
    		}
    		else										## c'é l'allegato
    		{
    			$sender->MailFile({to => $to,
    							subject => $subj,
    							msg => $t->get("1.0","end"),
    							file => $fname});
    		}
    }
    sub options_win		## finestra delle opzioni
    {
    		$optwin = $mw->Toplevel(-relief=>'raised', -borderwidth=>5);
    			$optwin->title("Configuration");
    			$optwin->geometry('+300+300');
    			$optwin->minsize( qw(250 100) );
    		$optwin->Label(-text =>"Your profile\n\n")->pack(-side => 'top');
    			$optwin->Label(-text =>'Your e-mail address')->pack(-side => 'top', -fill => 'x');
    			$entry_email = $optwin->Entry(-textvariable => \$email)->pack(-side => 'top');
    			$optwin->Label(-text =>"\nSmtp server address")->pack(-side => 'top', -fill => 'x');
    			$entry_smtp = $optwin->Entry(-textvariable => \$smtp)->pack(-side => 'top');
    			$optwin->Button(-text => "Save", -command => sub{&change_prof})->pack(-side=>'bottom');
    }
    sub change_prof		## salvo i cambiamenti dalla finestra di configurazione al file "info.txt"
    {
    		$_ = $email;
    	if ($email eq "" || !(/\./) || !(/@/) )
    	{
    		$dlgbox = $mw->DialogBox(-title=>'Error');
    		$dlgbox->add('label', 'Insert a correct e-mail address');
    		$dlgbox->Show;
    		return;
    	}
    	$_ = $smtp;
    	if ($smtp eq "" || !(/\./) )
    	{
    		$dlgbox = $mw->DialogBox(-title=>'Error');
    		$dlgbox->add('label', "Insert a correct smtp server address (ex: smtp.domain.com)");
    		$dlgbox->Show;
    		return;
    	}
    		open (FH, ">info.txt");
    		print FH "email=$email\nsmtp=$smtp\n";
    		close FH;
    		$optwin->destroy;
    		$mw->deiconify();
    }
    sub about_win
    {
    	my $about = $mw->Toplevel(-relief=>'raised', -borderwidth=>10);
    			$about->title("About Me");
    			$about->geometry('+300+300');
    	my $label = $about->Label(-text=>"The Simple Mailer
    By: Francesco Gasparetto
    frenkolo\@yahoo.it
    It acts as a smtp client
    without any POP3 session, that means you
    don\'t receive, you send. Shows how to use:
    Mail::Sender
    Tk::FileDialog
    Tk::DialogBox");
    $label->pack();
    }
    


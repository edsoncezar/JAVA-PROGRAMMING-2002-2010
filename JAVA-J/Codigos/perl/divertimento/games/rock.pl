 =**************************************
    = Name: Rock, Paper, Scissors w/ GUI
    = Description:This program allows the us
    =     er to play a simple, random game of Rock
    =     Paper Scissors against the computer, hav
    =     e their score kept, and get a message ap
    =     propriate to their score (read: canned i
    =     nsult) at the end of the game.
    = By: Kurt Rudolph
    =
    = Inputs:User must have Perl::Tk module 
    =     installed
    =
    = Returns:One word: Lots of fun.
    =
    = Assumes:To reset a game without gettin
    =     g your score, go to File->New Game. T
    =     o stop the current game, go to File->
    =     End Current Game.
    =
    = Side Effects:Crying, with both joy and
    =     pain
    =
    =This code is copyrighted and has    = limited warranties.Please see http://w
    =     ww.Planet-Source-Code.com/vb/scripts/Sho
    =     wCode.asp?txtCodeId=550&lngWId=6    =for details.    =**************************************
    
    #!/usr/bin/perl
    use Tk;
    $main = new MainWindow(-height=>1000, -width=>1000, -background=>"black");
    $menuBar = $main->Frame(-relief=>"raised",
    		-background=>"light blue");
    $menuBar->pack(-side=>"top", -fill=>"x");
    $mainFrame=$main->Frame(-relief=>"sunken", 
    	-height=>400, 
    	-width=>600, 
    	-background=>"white");
    $mainFrame->pack(-expand=>1, -fill=>"both", -anchor=>"sw");
    $button1 = $mainFrame->Button(-height=>2,
    	-width=>10,
    	-text=>"Rock",
    	-justify=>"left",
    	-foreground=>"white",
    	-command=>\&pick_rock);
    $button1->place(-relx=>0.0, -rely=>1.0, -anchor=>"sw");
    $button2 = $main->Button(-height=>2,
    	-width=>10,
    	-text=>"Paper",
    	-justify=>"left",
    	-foreground=>"white",
    	-command=>\&pick_paper);
    $button2->place(-relx=>0.5, -rely=>1.0, -anchor=>"s");
    $button3 = $main->Button(-height=>2,
    	-width=>10,
    	-text=>"Scissors",
    	-justify=>"left",
    	-foreground=>"white",
    	-command=>\&pick_scissors);
    $button3->place(-relx=>1.0, -rely=>1.0, -anchor=>"se");
    	
    $filebutton=$menuBar->Menubutton(-text=>"File",
    	-underline=>3,
    	-foreground=>"white");
    $fileMenu = $filebutton->Menu();
    $filebutton->configure(-menu=>$fileMenu);
    $fileMenu->command(-command=>\&new_game,
    	-label=>"New Game",
    	-foreground=>"white",
    	-activeforeground=>"light blue");
    $fileMenu->command(-command=>\&end_game,
    	-label=>"End Current Game",
    	-foreground=>"white",
    	-activeforeground=>"light blue");
    $fileMenu->separator;
    $fileMenu->command(-command=>\&exit,
    	-label=>"Exit",
    	-foreground=>"white",
    	-activeforeground=>"light blue");
    $filebutton->pack(-side=>"left");
    $menuBar->pack(-side=>"top", -fill=>"x");
    $text=$mainFrame->Text(-background=>"light blue", 
    	-relief=>"sunken", 
    	-wrap=>"word",
    	-height=>20, 
    	-width=>80);
    $text->place(-relx=>0.5, -rely=>0.5, -anchor=>"center");
    $win_disp=$mainFrame->Text(-height=>2, 
    	-width=>3, 
    	-relief=>"raised",
    	-background=>"gold", 
    	-foreground=>"maroon", 
    	-font=>"times-24");
    $win_disp->insert('0.0', "0");
    $win_disp->place(-relx=>'0.08', -rely=>'0.0', -anchor=>'n');
    $win_label=$mainFrame->Label(-height=>1, 
    	-width=>4.2, 
    	-background=>"white", 
    	-foreground=>"black", 
    	-text=>"Wins:");
    $win_label->place(-relx=>'0.0', -rely=>'0.0', -anchor=>'nw');
    $loss_disp=$mainFrame->Text(-height=>2, 
    	-width=>3, 
    	-relief=>"raised",
    	-background=>"red", 
    	-foreground=>"dark blue", 
    	-font=>"times-24");
    $loss_disp->insert('0.0', "0");
    $loss_disp->place(-relx=>'0.37', -rely=>'0.0', -anchor=>'n');
    $loss_label=$mainFrame->Label(-height=>1, 
    	-width=>6, 
    	-background=>"white", 
    	-foreground=>"black", 
    	-text=>"Losses:");
    $loss_label->place(-relx=>'0.3', -rely=>'0.0', -anchor=>'n');
    $draw_disp=$mainFrame->Text(-height=>2, 
    	-width=>3, 
    	-relief=>"raised",
    	-background=>"green", 
    	-foreground=>"blue", 
    	-font=>"times-24");
    $draw_disp->insert('0.0', "0");
    $draw_disp->place(-relx=>'0.62', -rely=>'0.0', -anchor=>'n');
    $draw_label=$mainFrame->Label(-height=>1, 
    	-width=>5.5, 
    	-background=>"white", 
    	-foreground=>"black", 
    	-text=>"Draws:");
    $draw_label->place(-relx=>'.55', -rely=>'0.0', -anchor=>'n');
    $node = {
    DATA => $value,
    NEXT => \$next,
    LAST => \$last,
    };
    createList();
    $text->insert('0.0', 'Click on Rock Paper or Scissors to start each turn.');
    $p=$head;
    $over=0;
    MainLoop;
    sub pick_rock{
    	$yourChoice = "Rock";
    	MAIN_FCN();
    	GAME();
    }
    sub pick_paper{
    	$yourChoice = "Paper";
    	MAIN_FCN();
    	GAME();
    }
    sub pick_scissors{
    	$yourChoice = "Scissors";
    	MAIN_FCN();
    	GAME();
    }
    sub exit{
    	$main->destroy;}
    sub MAIN_FCN{
    	srand;
    	$num = int (rand 3); # get a random integer, 1 through 3
    	if($num == 0){$compChoice = "Rock";}
    	elsif($num == 1){$compChoice = "Paper";}
    	elsif($num == 2){$compChoice = "Scissors";}
    	$over=0;
    	return;
    }
    sub GAME{
    	while($over == 0 && $yourChoice ne "exit")
    	{
    		if($yourChoice eq $p->{DATA})
    		{
    			if($p->{NEXT}->{DATA} eq $compChoice)
    			{
    				$text->insert('0.0', "Computer chose $p->{NEXT}->{DATA}. You lose.\n\n");
    				$losses++;
    				$loss_disp->delete('0.0', 'end');
    				$loss_disp->insert('0.0', $losses);
    				$over = 1;
    			}
    			elsif($p->{LAST}->{DATA} eq $compChoice)
    			{
    				
    				$text->insert('0.0', "Computer chose $p->{LAST}->{DATA}. You win!\n\n");
    				$wins++;
    				$win_disp->delete('0.0', 'end');
    				$win_disp->insert('0.0', $wins);
    				$over = 1;
    			}
    			else
    			{
    				$text->insert('0.0', "Computer chose $p->{DATA}. It's a Draw!\n\n");	
    				$draws++;
    				$draw_disp->delete('0.0', 'end');
    				$draw_disp->insert('0.0', $draws);
    				$over = 1;
    			}
    		}
    		elsif($yourChoice ne "Rock" && $yourChoice ne "Paper" && $yourChoice ne "Scissors")
    		{
    			$text->insert('0.0', "Please enter 'Rock', 'Paper', or 'Scissors' next time\n");
    			last;
    		}
    		else
    		{
    			$p = $p->{NEXT};
    		}
    	}
    }
    sub end_game{
    	$total = $wins+$losses+$draws;
    	$percent = ($wins/$total)*100;
    	$adjustedScore = $percent * $total;
    	$points = (($wins*3)+$draws)-($losses*2);
    	calcMessage();
    	my $button = $main->messageBox(-type =>'OK',
    		-title => 'Message',
    	-message=>"You racked up a $message $points points.\nBut your amazing rock, paper, scissors prowess aside, you$taunt\n");
    	new_game();
    }
    sub createList
    {
    	$head = $node->{DATA};
    	$curr = \$head;
    	$head->{DATA} = "Rock";
    	$head->{NEXT} = $node->{DATA};
    	$head->{NEXT}->{DATA} = "Paper";
    	$curr = $head->{NEXT};
    	$curr->{LAST} = $head;
    	$curr->{NEXT} = $node->{DATA};
    	$curr->{NEXT}->{DATA} = "Scissors";
    	$curr = $curr->{NEXT};
    	$curr->{LAST} = $head->{NEXT};
    	$curr->{NEXT} = $head;
    	$head->{LAST} = $curr;
    }
    sub calcMessage
    {
    	if($points < 25){$message = "pathetic";
    	$taunt = "'re a bedwetter";}
    	elsif($points>26 && $points<50){$message = "quietly dignified";
    	$taunt = "r mom still breast feeds you";}
    	elsif($points>51 && $points<100){$message = "unbearably smug";
    	$taunt = " are a chronic masturbator";}
    	else{$message = "contemptibly competent";
    	$taunt = " captain the SS Anal Avenger as with your first mate Hot Karl";}
    }
    sub new_game{
    	$wins = 0;
    	$losses = 0;
    	$draws = 0;
    	$text->delete('0.0', 'end');
    	$win_disp->delete('0.0', 'end');
    	$loss_disp->delete('0.0', 'end');
    	$draw_disp->delete('0.0', 'end');
    	$loss_disp->insert('0.0', $losses);
    	$draw_disp->insert('0.0', $draws);
    	$win_disp->insert('0.0', $wins);
    }
 
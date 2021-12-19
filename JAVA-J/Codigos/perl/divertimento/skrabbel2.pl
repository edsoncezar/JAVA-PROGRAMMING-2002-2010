use strict;
use Tk;
use Tk::DialogBox;
use Tk::FileDialog;
use Tk::Checkbutton;
use Tk::OptionMenu;
use File::Glob;


#My custom class.
use Skrabbel;


#Our Important Globals
my $MW; #MainWindow
my $VERSION = '0.1';
my $scrabble; #Our class object that maintains the game
my $board_frame; #Frame for Scrabble Board
my $entry_frame; #Frame for all entries
my @square_frames; #1-D Array of Frames
my @squares; #2-D Array of Labels (15x15)
my $entry_word; #Word to enter onto board
my $x_pt; #X coordinate point on board
my $y_pt; #Y coordinate point on board
my $orientation; #Direction of new word (Horizontal,Vertical)
my $my_letters; #Current letters you have to play
my $num_results; #Number of results we want when finding best
my $value; #current value of word being shown
my $best_moves; #reference to array of best moves
my $preview_showing; #which preview in array we are showing
my $search_time; #time it took to run search


#Set up the mainwindow
$MW = MainWindow->new(
	-title => "Scrabbler - v$VERSION"
	);
$MW->resizable(0,0);
$MW->withdraw();


#Set up our board_frame
$board_frame = $MW->Frame(
	)->pack(
		-side => 'left',
		);
my $top_axis_frame = $board_frame->Frame(
	)->pack(
		-side => 'top',
		-fill => 'x'
		);
$top_axis_frame->Label(
	-text => " # ",
	-width => 2,
	-font => [
		-weight => 'bold',
		-size => 12
		],
	-relief => 'flat',
	-borderwidth => 1
	)->pack(
		-side => 'left',
		-padx => 1
		);
for (my $x = 0; $x < 15; $x++) {
	$top_axis_frame->Label(
		-text => " $x ",
		-width => 2,
		-font => [
			-weight => 'bold',
			-size => 12
			],
		-relief => 'flat',
		-borderwidth => 1
		)->pack(
			-side => 'left',
			-padx => 1,
			-pady => 1
			);
}
for (my $x = 0; $x < 15; $x++) {
	$square_frames[$x] = $board_frame->Frame(
	)->pack(
		-side => 'top',
		-fill => 'x',
		-expand => 1
		);
	$square_frames[$x]->Label(
		-text => " $x ",
		-width => 2,
		-font => [
			-weight => 'bold',
			-size => 12
			],
		-relief => 'flat',
		-borderwidth => 1
		)->pack(
			-side => 'left',
			-padx => 1
			);
	for (my $y = 0; $y < 15; $y++) {
		$squares[$x][$y] = $square_frames[$x]->Label(
			-text => "   ",
			-width => 2,
			-font => [
				-weight => 'bold',
				-size => 12
				],
			-relief => 'sunken',
			-borderwidth => 1
			)->pack(
				-side => 'left',
				-padx => 1
				);
	}
}


#Color our squares on board_frame
#make the 3W (Triple Word) squares
foreach my $x (0,7,14) {
	foreach my $y (0,7,14) {
		$squares[$x][$y]->configure(
			-background => 'red'
			);
	}
}
#make the 2W (Double Word) squares
foreach my $x (1,2,3,4,7) {
	$squares[$x][$x]->configure(
			-background => 'orange'
			);
	$squares[$x][14 - $x]->configure(
			-background => 'orange'
			);
	$squares[14 - $x][$x]->configure(
			-background => 'orange'
			);
	$squares[14 - $x][14 - $x]->configure(
			-background => 'orange'
			);
}
#make the 3L (Triple Letter) squares
foreach my $x (5,9) {
	foreach my $y (5,9) {
		$squares[$x][$y]->configure(
			-background => 'blue'
			);
	}
}
foreach my $x (1,13) {
	foreach my $y (1,5) {
		$squares[$x][$y]->configure(
			-background => 'blue'
			);
		$squares[$x][14 - $y]->configure(
			-background => 'blue'
			);
	}
}
#make the 2L (Double Letter) squares
foreach my $x (0,7,14) {
	foreach my $y (3,11) {
		$squares[$x][$y]->configure(
			-background => 'yellow'
			);
		$squares[$y][$x]->configure(
			-background => 'yellow'
			);
	}
}
foreach my $x (2,12,6,8) {
	foreach my $y (6,8) {
		$squares[$x][$y]->configure(
			-background => 'yellow'
			);
		$squares[$y][$x]->configure(
			-background => 'yellow'
			);
	}
}


#set our Entries frame
$entry_frame = $MW->Frame(
	)->pack(
		-side => 'right',
		-fill => 'both',
		-expand => 1
		);


#Our "Place Move" title
$entry_frame->Label(
	-text => "Place Move",
	-font => [
		-size => 12,
		-weight => 'bold',
		-underline => 1
		]
	)->pack(
		-side => 'top'
		);


#Our Word-Entry frame
my $word_frame = $entry_frame->Frame(
	)->pack(
		-side => 'top',
		-fill => 'x',
		-pady => 5
		);
my $word_entry = $word_frame->Entry(
	-textvariable => \$entry_word,
	-width => 18,
	-font => [
		-size => 10,
		-weight => 'bold'
		],
	)->pack(
		-side => 'right'
		);
$word_frame->Label(
	-text => "Word: ",
	-font => [
		-size => 10,
		-weight => 'bold'
		],
	)->pack(
		-side => 'right'
		);


#Our Coordinate Entry Frame
my $location_frame = $entry_frame->Frame(
	)->pack(
		-side => 'top',
		-fill => 'x',
		-pady => 5
		);
$location_frame->Entry(
	-textvariable => \$x_pt,
	-width => 2,
	-font => [
		-size => 10,
		-weight => 'bold'
		],
	)->pack(
		-side => 'right'
		);
$location_frame->Label(
	-text => " col:",
	-font => [
		-size => 10
		]
	)->pack(
		-side => 'right'
		);
$location_frame->Entry(
	-textvariable => \$y_pt,
	-width => 2,
	-font => [
		-size => 10,
		-weight => 'bold'
		],
	)->pack(
		-side => 'right'
		);
$location_frame->Label(
	-text => "row:",
	-font => [
		-size => 10
		]
	)->pack(
		-side => 'right'
		);
$location_frame->Label(
	-text => "Starting Point: ",
	-font => [
		-size => 10,
		-weight => 'bold'
		],
	)->pack(
		-side => 'right'
		);


#Our Direction Entry Frame
my $direction_frame = $entry_frame->Frame(
	)->pack(
		-side => 'top',
		-fill => 'x',
		-pady => 5
		);
$direction_frame->Optionmenu(
	-variable => \$orientation,
	-options => [
		"Horizontal",
		"Vertical"
		],
	-font => [
		-size => 10,
		-weight => 'bold'
		]
	)->pack(
		-side => 'right'
		);
$direction_frame->Label(
	-text => "Orientation: ",
	-font => [
		-size => 10,
		-weight => 'bold'
		]
	)->pack(
		-side => 'right'
		);


#Our button to activate place-move
$entry_frame->Button(
	-text => "Add Word",
	-font => [
		-size => 10,
		-weight => 'bold'
		],
	-command => [\&add_word],
	-background => 'orange',
	-activebackground => 'green'
	)->pack(
		-side => 'top',
		-anchor => 'e',
		-pady => 5
		);



#A little separator
$entry_frame->Frame(
	)->pack(
		-side => 'top',
		-fill => 'x',
		-pady => 10
		);


#Our "Find Best Move" title
$entry_frame->Label(
	-text => "Find Best Move(s)",
	-font => [
		-size => 12,
		-weight => 'bold',
		-underline => 1
		],
	)->pack(
		-side => 'top'
		);


#our "My Letters" frame
my $letters_frame = $entry_frame->Frame(
	)->pack(
		-side => 'top',
		-fill => 'x',
		-pady => 5
		);
$letters_frame->Entry(
	-textvariable => \$my_letters,
	-width => 15,
	-font => [
		-size => 10,
		-weight => 'bold'
		]
	)->pack(
		-side => 'right',
		-padx => 5
		);
$letters_frame->Label(
	-text => "My Letters: ",
	-font => [
		-size => 10,
		-weight => 'bold'
		]
	)->pack(
		-side => 'right',
		-padx => 5
		);

#Our "Number of Results" frame
my $results_frame = $entry_frame->Frame(
	)->pack(
		-side => 'top',
		-fill => 'x',
		-pady => 5
		);
$results_frame->Entry(
	-textvariable => \$num_results,
	-width => 3,
	-font => [
		-size => 10,
		-weight => 'bold'
		]
	)->pack(
		-side => 'right',
		-padx => 5
		);
$results_frame->Label(
	-text => "Num of Results: ",
	-font => [
		-size => 10,
		-weight => 'bold'
		]
	)->pack(
		-side => 'right',
		-padx => 5
		);


#Our frame for controlling scrolling results
my $scroll_frame = $entry_frame->Frame(
	)->pack(
		-side => 'top',
		-fill => 'x',
		-pady => 5
		);
$scroll_frame->Button(
	-text => "next",
	-font => [
		-size => 10,
		-weight => 'bold'
		],
	-activebackground => 'green',
	-command => sub {
			$preview_showing++ if ($preview_showing < scalar(@{$best_moves}) - 1);
			preview($$best_moves[$preview_showing]);
		}
	)->pack(
		-side => 'right',
		-padx => 5
		);
$scroll_frame->Button(
	-text => "prev",
	-height => .5,
	-font => [
		-size => 10,
		-weight => 'bold'
		],
	-activebackground => 'green',
	-command => sub {
			$preview_showing-- if ($preview_showing > 0);
			preview($$best_moves[$preview_showing]);
		}
	)->pack(
		-side => 'right',
		-padx => 5
		);
$scroll_frame->Label(
	-text => "Value: ",
	-font => [
		-size => 10,
		-weight => 'bold',
		]
	)->pack(
		-side => 'left',
		-padx => 2
		);
$scroll_frame->Label(
	-textvariable => \$value,
	-font => [
		-size => 10,
		-weight => 'bold'
		]
	)->pack(
		-side => 'left'
		);


#Our buttons for finding or clearing results
my $clear_frame = $entry_frame->Frame(
	)->pack(
		-side => 'top',
		-fill => 'x',
		-pady => 5
		);
my $find_button = $clear_frame->Button(
	-text => "Find",
	-font => [
		-size => 10,
		-weight => 'bold'
		],
	-activebackground => 'green',
	-command => [\&find_results]
	)->pack(
		-side => 'right',
		-padx => 5
		);
$clear_frame->Button(
	-text => "Clear",
	-font => [
		-size => 10,
		-weight => 'bold'
		],
	-activebackground => 'green',
	-command => [\&clear_results]
	)->pack(
		-side => 'right',
		-padx => 5
		);
$clear_frame->Label(
	-text => "Time:",
	-font => [
		-size => 10,
		-weight => 'bold'
		]
	)->pack(
		-side => 'left',
		-padx => 3
		);
$clear_frame->Label(
	-textvariable => \$search_time,
	-font => [
		-size => 10,
		-weight => 'bold',
		],
	-width => 6
	)->pack(
		-side => 'left',
		-padx => 3
		);


#our bottom button frame
my $button_frame = $entry_frame->Frame(
	)->pack(
		-side => 'bottom',
		-fill => 'x',
		-pady => 5
		);
$button_frame->Button(
	-text => "Save Board",
	-command => [\&save_select],
	-font => [
		-size => 10,
		-weight => 'bold'
		],
	-background => 'orange',
	-activebackground => 'green'
	)->pack(
		-side => 'right',
		-padx => 5
		);
$button_frame->Button(
	-text => "Load Board",
	-command => [\&load_select],
	-font => [
		-size => 10,
		-weight => 'bold'
		],
	-background => 'orange',
	-activebackground => 'green'
	)->pack(
		-side => 'right',
		-padx => 5
		);


#configure file browsing object
my $dictionary_window = $MW->FileDialog(
	-Title => 'Select Word List',
	-SelHook => \&start,
	-ShowAll => 1,
	-Create => 0
	);
my $load_window = $MW->FileDialog(
	-Title => 'Select Board to Load',
	-SelHook => \&load_board,
	-ShowAll => 1,
	-Create => 0
	);
my $save_window = $MW->FileDialog(
	-Title => 'Where to Save Board',
	-SelHook => \&save_board,
	-ShowAll => 1,
	-Create => 1
	);


#Pop up our window to get word list
$dictionary_window->Show();


#This function creates our scrabble class
#and gets things started
sub start($) {
	my ($word_list) = @_;
	my $dir = $word_list;
	while (chop($dir) ne '/') {}


	#bring down this window
	$dictionary_window->destroy();
	$load_window->configure(
		-Path => $dir
		);
	$save_window->configure(
		-Path => $dir
		);



	#load module and board
	unless ($scrabble = Skrabbel->new($word_list)) {
		pop_message("Constructor Failed",1);
	}
	$MW->geometry('+100+100');
	$MW->Popup();
	MainLoop();
	exit;
}


#This just updates the values of our displayed board
#based on the 2-D array-ref argument
sub update_board($) {
	my ($new_board) = @_;
	for (my $x = 0; $x < 15; $x++) {
		for (my $y = 0; $y < 15; $y++) {
			my $txt = $$new_board[$x][$y];
			$squares[$x][$y]->configure(
				-text => "$txt"
				);
		}
	}
}


#This function previews an additional word on our board
sub preview($) {
	my ($move) = @_;
	my $val_buffer = $$move{'value'};
	$value = $val_buffer;


	#breaking word into array of chars
	my @word_array = split(//,uc($$move{'word'}));


	#first update to actual board
	update_board($scrabble->curr_board());
	#if horiz we preview here
	if($$move{'orientation'} eq "Horizontal") {
		for(my ($x,$tmp_x) = (0,$$move{'x_pt'}); $x < length($$move{'word'}); $x++,$tmp_x++) {
			$squares[$$move{'y_pt'}][$tmp_x]->configure(
				-text => "$word_array[$x]"
				);
		}
	}
	#if vertical then here
	elsif($$move{'orientation'} eq "Vertical") {
		for(my ($y,$tmp_y) = (0,$$move{'y_pt'}); $y < length($$move{'word'}); $y++,$tmp_y++) {
			$squares[$tmp_y][$$move{'x_pt'}]->configure(
				-text => "$word_array[$y]"
				);
		}
	}
}


#this function just clears all our fields
#and resets our board
sub clear_results() {
	$num_results = '';
	$my_letters = '';
	$value = '';
	update_board($scrabble->curr_board());
}


#This function gathers the data in entry_frame
#And tries to add a word to our board!
sub add_word() {
	#Some basic error-checking
	if ($x_pt =~ /\D/ || $y_pt =~ /\D/ || $x_pt > 14 || $x_pt < 0 ||
		$y_pt > 14 || $y_pt < 0 || $y_pt eq "" || $x_pt eq "") {
		pop_message("Coordinates Are Improper",0);
		return 0;
	}


	#have our object add this word
	if($scrabble->add_word($entry_word, $x_pt, $y_pt, $orientation)) {
		update_board($scrabble->curr_board());
		return 1;
	}
	else {
		pop_message("New Word Won't Fit On Board",0);
		return 0;
	}
}


#Here we pop our filedialog to select file to load
sub load_select() {
	$load_window->raise();
	$load_window->Show();
}


#Here we load the board in the functions first and only argument
sub load_board($) {
	my ($file) = @_;


	#let's have our filedialog pop up in this dir next time
	my $dir = $file;
	while (chop($dir) ne "/") {};
	$load_window->configure(
		-Path => $dir
		);


	#Now we call our class and get it done
	if($scrabble->load_board($file)) {
		update_board($scrabble->curr_board());
		return 1;
	}
	else {
		pop_message("Couldn't Load Board At: $file",0);
		return 0;
	}
}


#Here we pop up our filedialog to select file to save board to
sub save_select() {
	$save_window->raise();
	$save_window->Show();
}


#Here we save our board to the file in the functions first
#and only argument
sub save_board($) {
	my ($file) = @_;


	#let's have our filedialog pop up in this dir next time
	my $dir = $file;
	while (chop($dir) ne "/") {};
	$save_window->configure(
		-Path => $dir
		);


	#Now we call our class and get it done
	if($scrabble->save_board($file)) {
		pop_message("Board Saved Successfully",0);
		return 1;
	}
	else {
		pop_message("Couldn't Save Board To: $file",0);
		return 0;
	}
}


#Here we ask our object to do it's thing and we find
#the top results.
sub find_results() {
	my $time = time();
	$best_moves = $scrabble->find_results($my_letters, $num_results);
	$time = time() - $time;
	my $min = int($time/60);
	my $sec = $time - ($min * 60);
	$search_time = "$min" . "m " . "$sec" . "s";
	if($best_moves) {
		preview($$best_moves[0]);
		$preview_showing = 0;
		pop_message("Move Search Complete",0);
	}
	else {
		pop_message("Find Request Failed",0);
	}
}


#This function replaces our Win32 MsgBox's.  We pop
#up message and then whether it's a program ender
#or just a message.
sub pop_message($$) {
	my ($msg,$death) = @_;


	#Create our toplevel object
	my $PW = $MW->Toplevel(
		-title => "Message For Ya",
		-takefocus => 1
		);
	$PW->resizable(0,0);
	$PW->protocol('WM_DELETE_WINDOW',sub {;});
	$PW->withdraw();


	#If it's the end we take away MW
	if ($death) {
		$MW->withdraw();
	}


	#Now make our label and button
	$PW->Label(
		-text => "$msg",
		-font => [
			-size => 10,
			-weight => 'bold'
			]
		)->pack(
			-side => 'top',
			-pady => 5
			);
	my $button = $PW->Button(
		-text => "Ok",
		-font => [
			-size => 10,
			-weight => 'bold'
			],
		-activebackground => 'green',
		-width => 10,
		-command => [ sub {
				if ($death) {
					$MW->destroy();
				}
				else {
					$PW->destroy();
				}
			} ]
		)->pack(
			-side => 'top',
			-pady => 5
			);


	#Pop up our window
	$PW->Popup();
	$button->focus();
}

##</code><code>##

package Skrabbel;


use strict;


#our letter values
my %letter_values = (
	"A" => 1,
	"B" => 3,
	"C" => 3,
	"D" => 2,
	"E" => 1,
	"F" => 4,
	"G" => 2,
	"H" => 4,
	"I" => 1,
	"J" => 8,
	"K" => 5,
	"L" => 1,
	"M" => 3,
	"N" => 1,
	"O" => 1,
	"P" => 3,
	"Q" => 10,
	"R" => 1,
	"S" => 1,
	"T" => 1,
	"U" => 1,
	"V" => 4,
	"W" => 4,
	"X" => 8,
	"Y" => 4,
	"Z" => 10
	);


#our premium squares
my @premium_squares;
for(my $x = 0; $x < 15; $x++) {
	for(my $y = 0; $y < 15; $y++) {
		$premium_squares[$x][$y] = "  ";
	}
}


#make the 3W (Triple Word) squares
foreach my $x (0,7,14) {
	foreach my $y (0,7,14) {
		$premium_squares[$x][$y] = "3W";
	}
}


#make the 2W (Double Word) squares
foreach my $x (1,2,3,4,7) {
	$premium_squares[$x][$x] = "2W";
	$premium_squares[$x][14 - $x] = "2W";
	$premium_squares[14 - $x][$x] = "2W";
	$premium_squares[14 - $x][14 - $x] = "2W";
}


#make the 3L (Triple Letter) squares
foreach my $x (5,9) {
	foreach my $y (5,9) {
		$premium_squares[$x][$y] = "3L";
	}
}
foreach my $x (1,13) {
	foreach my $y (1,5) {
		$premium_squares[$x][$y] = "3L";
		$premium_squares[$x][14 - $y] = "3L";
	}
}


#make the 2L (Double Letter) squares
foreach my $x (0,7,14) {
	foreach my $y (3,11) {
		$premium_squares[$x][$y] = "2L";
		$premium_squares[$y][$x] = "2L";
	}
}
foreach my $x (2,12,6,8) {
	foreach my $y (6,8) {
		$premium_squares[$x][$y] = "2L";
		$premium_squares[$y][$x] = "2L";
	}
}
1;


#################################################
#CLIENT FUNCTIONS
#################################################

#Our constructor.  Takes a file location
#and loads the word-list in it
sub new($$) {
	my ($self, $word_file) = @_;


	#Load our word list, if we have one
	my @word_list = ();
	if($word_file) {
		open(LST, '<', $word_file)
			or return 0;
		@word_list = <LST>;
		close(LST);
	}
	for(my $x = 0; $x < scalar(@word_list); $x++) {
		chomp($word_list[$x]);
		$word_list[$x] =~ s/\s+//g;
		$word_list[$x] = lc($word_list[$x]);
	}


	#Create our current board
	my @current_board;
	for(my $x = 0; $x < 15; $x++) {
		for(my $y = 0; $y < 15; $y++) {
			$current_board[$x][$y] = ' ';
		}
	}


	#Instantiate our object
	bless {
		word_list => \@word_list,
		current_board => \@current_board
	}, $self;
}


#This function takes a file-location
#and loads the formatted board in it
sub load_board($$) {
	my ($self, $file) = @_;
	open(BRD, '<', $file)
		or return 0;
	chomp(my @current_board = <BRD>);
	close(BRD);
	for(my $x = 0; $x < 15; $x++) {
		$current_board[$x] = [ split('-',$current_board[$x]) ];
	}
	$self->{current_board} = \@current_board;


	return 1;
}


#This function saves our curr_board to the file
#location in argument.
sub save_board($$) {
	my ($self,$file) = @_;
	my $current_board = $self->curr_board();


	#Open our file
	open(BRD, '>', $file)
		or return 0;


	#Now we print it out
	for(my $y = 0; $y < 14; $y++) {
		for(my $x = 0; $x < 14; $x++) {
			print BRD $$current_board[$y][$x],"-";
		}
		print BRD $$current_board[$y][14],"\n";
	}
	for(my $x = 0; $x < 14; $x++) {
		print BRD $$current_board[14][$x],"-";
	}
	print BRD $$current_board[14][14];


	#close our file
	close(BRD);


	#and return our success
	return 1;
}


#This function takes all data for adding
#a new word to the board
sub add_word($$$$$) {
	my ($self, $new_word, $x_pt, $y_pt, $orientation) = @_;
	my $current_board = $self->curr_board();


	#Properly format our word
	$new_word = uc($new_word);
	$new_word =~ s/\s+//g;


	#Break our word up into an array of chars.
	my @word_array = split(//,$new_word);


	#checking that new addition is a valid move
	my $connection = 0; #whether the word is touching another
	#if true it must be our first move
	if ($self->board_empty()) {
		$connection = 1;
	}
	elsif($orientation eq "Horizontal") {
		for(my ($x,$tmp_x) = (0,$x_pt); $x < scalar(@word_array); $x++,$tmp_x++) {
			return 0 if ($tmp_x > 14);
			if($$current_board[$y_pt][$tmp_x] =~ /\S/) {
				return 0 if ($$current_board[$y_pt][$tmp_x] ne $word_array[$x]);
				$connection = 1;
			}
		}
	}
	elsif($orientation eq "Vertical") {
		my $tmp_y = $y_pt;
		for(my ($y,$tmp_y) = (0,$y_pt); $y < scalar(@word_array); $y++,$tmp_y++) {
			return 0 if ($tmp_y > 14);
			if($$current_board[$tmp_y][$x_pt] =~ /\S/) {
				return 0 if ($$current_board[$tmp_y][$x_pt] ne $word_array[$y]);
				$connection = 1;
			}
		}
	}
	else {
		return 0;
	}
	return 0 unless ($connection);

	#If we're still here than it must be valid so we add it
	if($orientation eq "Horizontal") {
		for(my ($x,$tmp_x) = (0,$x_pt); $x < scalar(@word_array); $x++,$tmp_x++) {
			$$current_board[$y_pt][$tmp_x] = $word_array[$x];
		}
	}
	elsif($orientation eq "Vertical") {
		for(my ($y,$tmp_y) = (0,$y_pt); $y < scalar(@word_array); $y++,$tmp_y++) {
			$$current_board[$tmp_y][$x_pt] = $word_array[$y];
		}
	}
	else {
		return 0;
	}


	#Got here without an error so we return success!
	return 1;
}


#This master function tries to find the best moves possible
sub find_results($$$) {
	my ($self, $letters, $num) = @_;
	$letters = uc($letters);
	$letters =~ s/\s//g;


	#Verify our arguments to be valid
	unless($letters =~ /\S/) {
		return 0;
	}
	unless($num =~ /\d/) {
		return 0;
	}


	#Here's our array of hash-references of best moves
	#which we'll fill, sort, shorten, and finally return
	my @best_moves;


	#Let's look for horizontal entry positions first
	for(my $y = 0; $y < 15; $y++) {
		unless($self->row_empty($y)) {
			my $tmp_letters = $letters . $self->row_letters($y);
			my $words = $self->words_with($tmp_letters);
			foreach my $word (@{$words}) {
				for(my $x = 0; $x < 15; $x++) {
					my $tmp_move = $self->validate($letters, $word, $y, $x, "Horizontal");
					if($tmp_move) {
						my $value = $self->value($word, $y, $x, "Horizontal");
						my %tmp_move = (
							"word" => $word,
							"orientation" => "Horizontal",
							"value" => $value,
							"y_pt" => $y,
							"x_pt" => $x
							);
						push(@best_moves, \%tmp_move);
					}
				}
			}
			@best_moves = sort {$$b{"value"} <=> $$a{"value"}} @best_moves;
			while(scalar(@best_moves) > $num) {
				pop(@best_moves);
			}
		}
	}
	#now lets look for vertical entry positions
	for(my $x = 0; $x < 15; $x++) {
		unless($self->col_empty($x)) {
			my $tmp_letters = $letters . $self->col_letters($x);
			my $words = $self->words_with($tmp_letters);
			foreach my $word (@{$words}) {
				for(my $y = 0; $y < 15; $y++) {
					my $tmp_move = $self->validate($letters, $word, $y, $x, "Vertical");
					if($tmp_move) {
						my $value = $self->value($word, $y, $x, "Vertical");
						my %tmp_move = (
							"word" => uc($word),
							"orientation" => "Vertical",
							"value" => $value,
							"y_pt" => $y,
							"x_pt" => $x
							);
						push(@best_moves, \%tmp_move);
					}
				}
			}
			@best_moves = sort {$$b{"value"} <=> $$a{"value"}} @best_moves;
			while(scalar(@best_moves) > $num) {
				pop(@best_moves);
			}
		}
	} close(BUG);


	return \@best_moves;
}


#This function returns the value of the move
#given in the arguments
sub value($$$$$) {
	my ($self, $word, $y_pt, $x_pt, $orientation) = @_;
	my $current_board = $self->curr_board();
	my $word = uc($word);


	#Time to break our word into array of chars.
	my @word_array = split(//,$word);


	#Here's our full value we're going to add on to
	my $full_value = 0;
	my $double = 0; #whether to double our final value
	my $triple = 0; #whether to triple our final value
	my $letters_used = 0; #if 7 we can add all letters bonus


	#Time to value if it's horizontal
	if($orientation eq "Horizontal") {
		for(my ($x,$tmp_x) = (0,$x_pt); $x < scalar(@word_array); $x++,$tmp_x++) {
			#if letter already here we just add it's normal value
			if($$current_board[$y_pt][$tmp_x] =~ /\S/) {
				$full_value += $letter_values{$word_array[$x]};
				next;
			}
			else{
				if($premium_squares[$y_pt][$tmp_x] eq "  ") {
					$full_value += $letter_values{$word_array[$x]};
				}
				else{
					if($premium_squares[$y_pt][$tmp_x] eq "2L") {
						$full_value += ($letter_values{$word_array[$x]}) * 2;
					}
					elsif($premium_squares[$y_pt][$tmp_x] eq "3L") {
						$full_value += ($letter_values{$word_array[$x]}) * 3;
					}
					elsif($premium_squares[$y_pt][$tmp_x] eq "2W") {
						$full_value += $letter_values{$word_array[$x]};
						$double = 1;
					}
					elsif($premium_squares[$y_pt][$tmp_x] eq "3W") {
						$full_value += $letter_values{$word_array[$x]};
						$triple = 1;
					}
				}
				$letters_used++;
			}
			#now if there's a letter above or below we want to add that
			if(($$current_board[$y_pt-1][$tmp_x] =~ /\S/ && $y_pt > 0)
			|| ($$current_board[$y_pt+1][$tmp_x] =~ /\S/ && $y_pt < 14)) {
				my $p = $y_pt - 1;
				while($$current_board[$p][$tmp_x] =~ /\S/ && $p >= 0) {
					$p--;
				}
				$p++;
				while($p < $y_pt) {
					$full_value += $letter_values{$$current_board[$p][$tmp_x]};
					$p++;
				}
				$full_value += $letter_values{$word_array[$x]};
				$p++;
				while($$current_board[$p][$tmp_x] =~ /\S/ && $p < 15) {
					$full_value += $letter_values{$$current_board[$p][$tmp_x]};
					$p++;
				}
			}
		}
	}
	#now time to do it vertically
	elsif($orientation eq "Vertical") {
		for(my ($y,$tmp_y) = (0,$y_pt); $y < scalar(@word_array); $y++,$tmp_y++) {
			#if letter already here we just add it's normal value
			if($$current_board[$tmp_y][$x_pt] =~ /\S/) {
				$full_value += $letter_values{$word_array[$y]};
				next;
			}
			else{
				 if($premium_squares[$tmp_y][$x_pt] eq "  ") {
					$full_value += $letter_values{$word_array[$y]};
				}
				else{
					if($premium_squares[$tmp_y][$x_pt] eq "2L") {
						$full_value += ($letter_values{$word_array[$y]}) * 2;
					}
					elsif($premium_squares[$tmp_y][$x_pt] eq "3L") {
						$full_value += ($letter_values{$word_array[$y]}) * 3;
					}
					elsif($premium_squares[$tmp_y][$x_pt] eq "2W") {
						$full_value += $letter_values{$word_array[$y]};
						$double = 1;
					}
					elsif($premium_squares[$tmp_y][$x_pt] eq "3W") {
						$full_value += $letter_values{$word_array[$y]};
						$triple = 1;
					}
				}
				$letters_used++;
			}
			#now if there's a letter behind or in front we add this too
			if(($$current_board[$tmp_y][$x_pt-1] =~ /\S/ && $x_pt > 0)
			|| ($$current_board[$tmp_y][$x_pt+1] =~ /\S/ && $x_pt < 14)) {
				my $p = $x_pt - 1;
				while($$current_board[$tmp_y][$p] =~ /\S/ && $p >= 0) {
					$p--;
				}
				$p++;
				while($p < $x_pt) {
					$full_value += $letter_values{$$current_board[$tmp_y][$p]};
					$p++;
				}
				$full_value += $letter_values{$word_array[$y]};
				$p++;
				while($$current_board[$tmp_y][$p] =~ /\S/ && $p < 15) {
					$full_value += $letter_values{$$current_board[$tmp_y][$p]};
					$p++;
				}
			}

		}
	}


	#now whether to double or triple or boost our results
	$full_value *= 2 if ($double);
	$full_value *= 3 if ($triple);
	$full_value += 50 if ($letters_used == 7);


	#and now to return our result
	return $full_value;
}

#This function decides whether a move is legal
#Using the current word-list.
sub validate($$$$$$) {
	my ($self, $letters, $word, $y_pt, $x_pt, $orientation) = @_;
	my $current_board = $self->curr_board();
	$word = uc($word);
	$letters = uc($letters);


	#Break our word up into an array of chars.
	my @word_array = split(//,$word);


	my $connection = 0; #whether the word is touching another
	my $use_letter = 0; #whether we use any of our letters
	#time to validate if horizontal
	if($orientation eq "Horizontal") {
		return 0 if ($x_pt > (15 - length($word)));
		#now to see if we made a runon-word with one behind or in front of us
		return 0 if (($$current_board[$y_pt][$x_pt-1] =~ /\S/ && $x_pt > 0) ||
		($$current_board[$y_pt][$x_pt+length($word)] =~ /\S/ && $x_pt < (15 - length($word))));
		for(my ($x,$tmp_x) = (0,$x_pt); $x < scalar(@word_array); $x++,$tmp_x++) {
			#if there's a letter here we make sure it matches
			if($$current_board[$y_pt][$tmp_x] =~ /\S/) {
				return 0 if ($$current_board[$y_pt][$tmp_x] ne $word_array[$x]);
				$connection = 1;
			}
			#if no letter here we make sure we have this letter to place
			else {
				return 0 unless ($letters =~ s/$word_array[$x]//);
				$use_letter = 1;
			}
		}
		return 0 unless ($connection && $use_letter);
		#now we look up and down to see if we're touching, at each spot
		for(my ($x,$tmp_x) = (0,$x_pt); $x < scalar(@word_array); $x++,$tmp_x++) {
			next if ($$current_board[$y_pt][$tmp_x] =~ /\S/);
			if(($$current_board[$y_pt-1][$tmp_x] =~ /\S/ && $y_pt > 0)
			|| ($$current_board[$y_pt+1][$tmp_x] =~ /\S/ && $y_pt < 14)) {
				my $vert_word;
				my $p = $y_pt - 1;
				while($$current_board[$p][$tmp_x] =~ /\S/ && $p >= 0) {
					$p--;
				}
				$p++;
				while($p < $y_pt) {
					$vert_word .= $$current_board[$p][$tmp_x];
					$p++;
				}
				$vert_word .= $word_array[$x];
				$p++;
				while($$current_board[$p][$tmp_x] =~ /\S/ && $p < 15) {
					$vert_word .= $$current_board[$p][$tmp_x];
					$p++;
				}
				return 0 unless $self->word_in_dictionary($vert_word);
			}
		}
	}
	#now time to validate if vertical
	elsif($orientation eq "Vertical") {
		return 0 if ($y_pt > (15 - length($word)));
		#now to see if we made a runon-word with one below or above us
		return 0 if (($$current_board[$y_pt-1][$x_pt] =~ /\S/ && $y_pt > 0) ||
		($$current_board[$y_pt+length($word)][$x_pt] =~ /\S/ && $y_pt < (15 - length($word))));
		for(my ($y,$tmp_y) = (0,$y_pt); $y < scalar(@word_array); $y++,$tmp_y++) {
			#if there's a letter here we make sure it matches
			if($$current_board[$tmp_y][$x_pt] =~ /\S/) {
				return 0 if ($$current_board[$tmp_y][$x_pt] ne $word_array[$y]);
				$connection = 1;
			}
			#if no letter here we make sure we have this letter to place
			else {
				return 0 unless ($letters =~ s/$word_array[$y]//);
				$use_letter = 1;
			}
		}
		return 0 unless ($connection && $use_letter);
		#now we look left and right to see if we're touching, at each spot
		for(my ($y,$tmp_y) = (0,$y_pt); $y < scalar(@word_array); $y++,$tmp_y++) {
			next if ($$current_board[$tmp_y][$x_pt] =~ /\S/);
			if(($$current_board[$tmp_y][$x_pt-1] =~ /\S/ && $x_pt > 0)
			|| ($$current_board[$tmp_y][$x_pt+1] =~ /\S/ && $x_pt < 14)) {
				my $horiz_word;
				my $p = $x_pt - 1;
				while($$current_board[$tmp_y][$p] =~ /\S/ && $p >= 0) {
					$p--;
				}
				$p++;
				while($p < $x_pt) {
					$horiz_word .= $$current_board[$tmp_y][$p];
					$p++;
				}
				$horiz_word .= $word_array[$y];
				$p++;
				while($$current_board[$tmp_y][$p] =~ /\S/ && $p < 15) {
					$horiz_word .= $$current_board[$tmp_y][$p];
					$p++;
				}
				return 0 unless $self->word_in_dictionary($horiz_word);
			}
		}
	}
	else {
		return 0;
	}


	#Well it must be good then, so we return true
	return 1;
}


#This function returns a reference to an array
#of all words with given letters in our dictionary
sub words_with($$) {
	my ($self, $letters) = @_;
	my $word_list = $self->word_list();


	#now to build our array to return
	my @words;
	foreach my $word (@{$word_list}) {
		next unless (length($word) > 2);
		if ($self->match($letters, $word)) {
			push(@words, $word);
		}
	}


	#Now to return reference to our array
	return \@words;
}


#This bool function returns whether the second argument
#string can be built with the first arguments letters
sub match($$$) {
	my ($self, $letters, $word) = @_;
	return 0 if (length($word) > length($letters));
	$letters = lc($letters);
	$word = lc($word);


	#Now we build our hashes
	my (%letter_hash);
	while($letters) {
		$letter_hash{chop($letters)}++;
	}
	while($word) {
		$letter_hash{chop($word)}--;
	}


	#Now to analyze
	my ($key,$value);
	while(($key,$value) = each(%letter_hash)) {
		return 0 if ($value < 0);
	}


	#Must be good
	return 1;
}


#This bool function just returns whether
#given word is in dictionary.
sub word_in_dictionary($$) {
	my ($self, $new_word) = @_;
	$new_word = lc($new_word);
	my $word_list = $self->word_list();


	#now to check each word for equality
	foreach my $word (@{$word_list}) {
		return 1 if ($new_word eq $word);
	}
	return 0;
}


#This bool function returns whether or not
#the given row on the board is empty.
sub row_empty($$) {
	my ($self, $row) = @_;
	my $current_board = $self->curr_board();


	#Now to check this row
	for(my $x = 0; $x < 15; $x++) {
		return 0 if ($$current_board[$row][$x] =~ /\S/);
	}


	#Well if they were all empty we return true
	return 1;
}


#This bool function returns whether or not
#the given column on the board is empty.
sub col_empty($$) {
	my ($self, $col) = @_;
	my $current_board = $self->curr_board();


	#Now to check this column
	for(my $y = 0; $y < 15; $y++) {
		return 0 if ($$current_board[$y][$col] =~ /\S/);
	}


	#Well if all empty we return true
	return 1;
}


#This function returns all the letters on a given row
sub row_letters($$) {
	my ($self, $row) = @_;
	my $current_board = $self->curr_board();


	#So let's get all our letters
	my $row_letters;
	for(my $x = 0; $x < 15; $x++) {
		if($$current_board[$row][$x] =~ /\S/) {
			$row_letters .= $$current_board[$row][$x];
		}
	}


	#And we return our letters
	return $row_letters;
}


#This function returns all the letters on a given column
sub col_letters($$) {
	my ($self, $col) = @_;
	my $current_board = $self->curr_board();


	#So let's get all our letters
	my $col_letters;
	for(my $y = 0; $y < 15; $y++) {
		if($$current_board[$y][$col] =~ /\S/) {
			$col_letters .= $$current_board[$y][$col];
		}
	}


	#And we return our letters
	return $col_letters;
}


#This bool function just tells whether board is empty
sub board_empty($) {
	my ($self) = @_;
	my $current_board = $self->curr_board();
	for(my $y = 0; $y < 15; $y++) {
		for(my $x = 0; $x < 15; $x++) {
			return 0 if ($$current_board[$y][$x] =~ /\S/);
		}
	}
	return 1;
}


#This function just returns our object's
#Current_board reference
sub curr_board($) {
	my ($self) = @_;
	return $self->{current_board};
}


#This just returns our word list
sub word_list($) {
	my ($self) = @_;
	return $self->{word_list};
}

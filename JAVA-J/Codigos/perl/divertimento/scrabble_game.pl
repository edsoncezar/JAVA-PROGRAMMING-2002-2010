use strict;
use Tk;
use Tk::DialogBox;
use Tk::FileDialog;
use Tk::Checkbutton;
use File::Glob;
use Win32;


#My custom class.
use Scrabble;


#Our Important Globals
my $MW; #MainWindow
my $scrabble; #Our class object that maintains the game
my $board_frame; #Frame for Scrabble Board
my $entry_frame; #Frame for all entries
my @square_frames; #1-D Array of Frames
my @squares; #2-D Array of Labels (15x15)
my $entry_word; #Word to enter onto board
my $x_pt; #X coordinate point on board
my $y_pt; #Y coordinate point on board
my $orientation; #Direction of new word (Horizontal,Vertical)


#Set up the mainwindow
$MW = MainWindow->new(
	-title => "Scrabbler"
	);
$MW->resizable(0,0);


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
	foreach my $y (1,5,9,13) {
		$squares[$x][$y]->configure(
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
$word_frame->Entry(
	-textvariable => \$entry_word,
	-width => 15,
	-font => [
		-weight => 'bold'
		],
	)->pack(
		-side => 'right'
		);
$word_frame->Label(
	-text => "Word: ",
	-font => [
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
		-weight => 'bold'
		],
	)->pack(
		-side => 'right'
		);
$location_frame->Label(
	-text => " col:"
	)->pack(
		-side => 'right'
		);
$location_frame->Entry(
	-textvariable => \$y_pt,
	-width => 2,
	-font => [
		-weight => 'bold'
		],
	)->pack(
		-side => 'right'
		);
$location_frame->Label(
	-text => "row:"
	)->pack(
		-side => 'right'
		);
$location_frame->Label(
	-text => "Starting Point: ",
	-font => [
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



#load module and board
unless ($scrabble = Scrabble->new("Not Used")) {
	print "Constructor Failed\n";
	exit(0);
}


MainLoop();
exit;


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


#This function gathers the data in entry_frame
#And tries to add a word to our board!
sub add_word() {
	#Some basic error-checking
	if ($x_pt =~ /\D/ || $y_pt =~ /\D/ || $x_pt > 14 || $x_pt < 0 ||
		$y_pt > 14 || $y_pt < 0 || $y_pt eq "" || $x_pt eq "") {
		Win32::MsgBox("Coordinates Are Improper",48,"Error");
		return 0;
	}


	#have our object add this word
	if($scrabble->add_word($entry_word, $x_pt, $y_pt, $orientation)) {
		update_board($scrabble->curr_board());
		return 1;
	}
	else {
		Win32::MsgBox("New Word Won't Fit On Board",48,"Error");
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
	$load_window->configure(-Path => $dir);


	#Now we call our class and get it done
	if($scrabble->load_board($file)) {
		update_board($scrabble->curr_board());
		return 1;
	}
	else {
		Win32::MsgBox("Couldn't Load Board At: $file",48,"Error");
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
	$save_window->configure(-Path => $dir);


	#Now we call our class and get it done
	if($scrabble->save_board($file)) {
		Win32::MsgBox("Board Saved Successfully",48,"All Good");
		return 1;
	}
	else {
		Win32::MsgBox("Couldn't Save Board To: $file",48,"Error");
		return 0;
	}
}






#Starting here put this in Scrabble.pm
package Scrabble;

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
	foreach my $y (1,5,9,13) {
		$premium_squares[$x][$y] = "3L";
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


	#Load our word list
	print "Now loading word_list...\n";
	#open(LST, '<', $word_file)
	#	or return 0;
	#chomp(my @word_list = <LST>);
	#close(LST);


	#Create our current board
	my @current_board;
	for(my $x = 0; $x < 15; $x++) {
		for(my $y = 0; $y < 15; $y++) {
			$current_board[$x][$y] = ' ';
		}
	}
	bless {
	#	word_list => \@word_list,
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


	#Properly format our word
	$new_word = uc($new_word);
	$new_word =~ s/\s//g;


	#Break our word up into an array of chars.
	my @word_array = ($new_word =~ /\w/g);


	#Get our current board
	my $current_board = $self->curr_board();


	#checking that new word fits and is non-trampling
	if($orientation eq "Horizontal") {
		for(my ($x,$tmp_x) = (0,$x_pt); $x < scalar(@word_array); $x++,$tmp_x++) {
			return 0 if ($tmp_x > 14);
			if($$current_board[$y_pt][$tmp_x] =~ /\S/) {
				return 0 if ($$current_board[$y_pt][$tmp_x] ne $word_array[$x]);
			}
		}
	}
	elsif($orientation eq "Vertical") {
		my $tmp_y = $y_pt;
		for(my ($y,$tmp_y) = (0,$y_pt); $y < scalar(@word_array); $y++,$tmp_y++) {
			return 0 if ($tmp_y > 14);
			if($$current_board[$tmp_y][$x_pt] =~ /\S/) {
				return 0 if ($$current_board[$tmp_y][$x_pt] ne $word_array[$y]);
			}
		}
	}
	else {
		return 0;
	}


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


#This function just returns our object's
#Current_board reference
sub curr_board($) {
	my ($self) = @_;
	return $self->{current_board};
}


#This function prints to STDOUT our board
sub print_board($) {
	my ($self) = @_;
	my $current_board = $self->{current_board};
	for(my $x = 0; $x < 15; $x++) {
		for (my $y = 0; $y < 15; $y++) {
			print "$$current_board[$x][$y]";
		}
		print "\n";
	}
	print "\n";
}


#This bool function just returns whether
#given word is in dictionary.
sub word_in_dictionary($$) {
	my ($self, $new_word) = @_;
	$new_word = lc($new_word);
	foreach my $word (@{$self->{word_list}}) {
		return 1 if ($word eq $new_word);
	}
	return 0;
}
#END of Scrabble.pm


#Here's a sample board-file.

A-S-P-I-R-I-N- - - - - - - - 
N- - - - - - - - - - - - - - 
A- - - - - - -L- - - - - - - 
L- - - - - -F-O-O-D- - - - - 
Y- - - - - - -N- - - - - - -F
Z- - - - - - -E- - - - - - -L
E-E-L- - - - -L- - - - - - -A
 - - - - - - -Y- - - - - - -N
 - - - - - - - - - - - - - -D
 - - - - - - - - - - - - - -E
 - - - - - - - - - - - - - -R
 - - - - - - - - - -B-L-I-S-S
 - - - - - - - - - - - - - - 
 - - - - - - - - - - - - - - 

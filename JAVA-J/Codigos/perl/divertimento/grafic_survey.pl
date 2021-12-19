#!/usr/bin/perl
use strict;

use Fcntl ':flock';  # import LOCK_* constants
use GD::Graph::bars3d;
use CGI qw/:standard/;
use CGI::Carp qw/fatalsToBrowser /;

my $q = CGI->new();

######### CONFIG #############
my $survey_folder_path ="/home/thesite/www/survey/";
my $image_URL = "http://www.thesite.com/survey/";
my $textclr = "gold";
my $transparent = "TRUE";
my $sort_it=1;  #0 for no sort
##############################
my (@col,@val,%data,$k,$v,$word,$total_per);
my (%data,@file,$total_votes,$percent,$admin, $delete_survey,@surveys,$new_survey);
my ($name,$input,$title,$xlabel,$ylabel,$menu_name,$question, @radio_vals, $choices);

referer_check();

my $meth = $q->request_method;
if ($meth ne 'POST' && $q->param()) {
	print $q->header,start_html,
			h1( "Please vote using our form",
			a( {-href=>url}, "here" ), ".",
			),
			end_html;
	exit;
}

if (!$q->param()) {
	choose_survey();
}

if ($q->param( 'new_survey' )) {
	new_survey();
}

if ($q->param( 'menu_name' )) {
	$menu_name = $q->param( 'menu_name' );
	make_survey();
}
if ($q->param( 'admin' )) {
	$admin = $q->param( 'admin' );
	admin_survey();
}

if ($q->param( 'delete_survey' )) {
	delete_survey();
}


get_data();
make_graph();
exit;

sub get_survey_list {
	my $data_file = $survey_folder_path."data.info";
	open (DH, "$data_file");
	while (<DH>) {
		chomp;
		my @line = split(/:/, join('', $_));
		$_=shift(@line);
		my $next =$_;
		push (@surveys,$_); #array of choices for popup menu
	}
	close DH;
	@surveys;
}

sub choose_survey {
	get_survey_list();
	print $q->header(-type=>"text/html"),
				start_html( {-bgcolor=>"black",-title=>"nolaFlash Survey Selector"} ),
				table( {-width=>'400'},
				Tr( {-align=>'CENTER'},
		 		td(
					font( {-color=>'gold', -face=>'Arial', -size=>'3'},"Please select a survey:"),
				start_form( {-action=>self_url} ),
				popup_menu( {-name=> "menu_name",-values=>\@surveys} ),
						p(  submit( {-value=>"Onward!"} ), ),
					end_form,
					), #td
				   ), #tr
				), #table
				end_html;
	exit;
}

sub make_survey {
	my $data_file = $survey_folder_path."data.info";
	open (DH, "$data_file") or die "where's the data file? : $!";
	while (<DH>) {
		chomp;
		my @line = split(/:/, join('', $_));
		$_ = shift(@line);
		if (/^$menu_name/) {               #get vars for form
		 	$name = 		shift(@line);
			$question = 	shift(@line);
			$title = 		shift(@line);
			$xlabel = 	shift(@line);
			$ylabel = 	shift(@line);
			@radio_vals = 	@line;
		}

	}

	close DH or die "Data file won't close : $!";

	print 	$q->	header,start_html( {-title=>"$title",-bgcolor=>"black",-text=>'gold'} ),
				start_form( {-method=>"post"} ), p( {-font=>'Arial',-size=>'3'},b( $question ), );

	my $end = @radio_vals;
	for (my $i=0; $i < $end; $i++) {
		print $q->radio_group( {-name=>"input",-value=>$radio_vals[$i], -default=>''},"$radio_vals[i]"),br;
		}
	print $q->hidden( {-name=>"name",-value=>$name} ),hidden( {-name=>"title",-value=>$title} ),
				hidden( {-name=>"xlabel",-value=>$xlabel} ),hidden( {-name=>"ylabel",
				-value=>$ylabel} ),submit( {-value=>"Vote!"} ),end_form,end_html;
	exit;
}

sub referer_check {
	my $referer = $ENV{HTTP_REFERER};
	my $hostname = quotemeta( $ENV{HTTP_HOST} || $ENV{SERVER_NAME} );
	if ( $referer !~ m|^http://$hostname/| ) {
		print $q->header,start_html( {-title=>"For Shame!"} ),
						div( {-align=>"center"},
							h1("What are you trying to pull here?"),
							p("Please use the form at",
							a({-href=>url}, "Our Site."),
							), #end_p
						), #end_div
					end_html;
		exit;
	}
}
sub get_data {
	$input = $q->param( "input" );
	$name = $q->param( "name" );
	$title = $q->param( "title" );
	$xlabel = $q->param( "xlabel" );
	$ylabel = $q->param( "ylabel" );
	my $results_file = "$survey_folder_path$name.poll";
	if (-e $results_file){
		open (FH, "+< $results_file") or die "where's the data file? : $!";
	} else {
		open (FH, "> $results_file") or die "where's the data file? : $!";
	}
	flock (FH,LOCK_EX) or die "Couldn't flock: $!";



	my @file = <FH>;
	chomp @file;
	%data = split(/:/, join('', @file));
	if ($input){
		$data{$input}++;
	}

	seek FH, 0, 0;
	truncate (FH,0) or die "Can't truncate: $!";

	my $file = join(":", %data);
	print FH $file;



	close FH or die "Data file won't close : $!";

	for (values %data) { $total_votes += $_ }

	#for (values %data) {            #make the hash values into percents
	#$_ = $_/$total_votes*100;
	#}
	if ($sort_it) {
		foreach $word (sort {lc($a) cmp lc($b)} keys %data) {
			push( @col , $word );
			push( @val ,  $data{ $word } );
		}
	} else {
		@col = (keys %data);
		@val = (values %data);
	}
}

sub make_graph {
	my $colors=qw(lgray gray dgray black lblue blue dblue gold lyellow yellow dyellow lgreen green dgreen lred red 	dred lpurple purple dpurple lorange orange pink dpink marine cyan lbrown dbrown white);

	if($#col != $#val){
	    print $q->header;
	    print "<b><h1>Error:  Parameters are not balanced</h1></b>";
	    exit;
	}
	my @data = ( [@col], [@val] );
	my $graph = new GD::Graph::bars3d(400,300);
	if($title ne ''){
	    $graph->set(title => "$title");
	}
	if($ylabel ne ''){
	    $graph->set(y_label => "$ylabel");
	}

	if($xlabel ne ''){
	    $graph->set(x_label => "$xlabel");
	}

	$_ = join('',@col);
	my $label_length = tr/a-zA-Z//;
	my $vertical_labels;

	if ($label_length > 40) {
		$vertical_labels = 1;
	} else {
		$vertical_labels = 0;
	}

	$graph->set(cycle_clrs => 'TRUE',
			  dclrs => [ qw(marine dgray dpurple dred dgreen gold lpurple dpink dbrown dblue) ],
			  transparent => "$transparent",
	            bar_spacing => '10',
	            legend_placement => 'CB',
	            bar_width => '15',
	            y_label_skip     => '1',
	            x_label_position => '0.5',
	            show_values => '1',
	            x_labels_vertical => "$vertical_labels" );
	$graph->set_text_clr("$textclr");
	$graph->set_values_font('ARIAL.TTF', 24);
	$graph->set_legend_font('ARIAL.TTF', 24);
	$graph->set_x_label_font('ARIAL.TTF', 24);
	$graph->set_y_label_font('ARIAL.TTF', 24);

	my $gd = $graph->plot( \@data );

	open(IMG, ">$survey_folder_path$name.png") or die $!;
	binmode IMG;
	print IMG $gd->png;
	close IMG;

	my $caption;
	if ($input) {
			$caption ="You cast vote number $total_votes.";
		} else {
			$caption ="There have been $total_votes votes, but you didn&#039;t cast one!";
		}

	print $q->header,start_html( {-title=>"Survey Results", -bgcolor=>"#000000" } ),
		 table({-width=>'400'},
		 	Tr( {-align=>'CENTER'},
		 		td(
		 		img( {-src=>"$image_URL$name.png",-heigth=>'300',-width=>'400'} ),
		 		font( {-color=>'gold', -face=>'Arial', -size=>'3'},$caption ),
		 		),
			), #tr
		), #table
	end_html;
}

sub admin_survey {
	get_survey_list();
	print $q->header,start_html( {-title=>"Survey Admin Page", -bgcolor=>"#FFFACD"} ),
			div( {-align=>'center'},
			font( { -face=>'Arial' }),  h1( "Survey Admin Page" ),
			table( {-width=>'80%', -border=>'1'},
				Tr(
					td( {-align=>'center'},
					font( {-face=>'Arial', -size=>'3'} ),
					b( "Delete a survey?" ),
					start_form( { -action=>url } ),
					popup_menu( {-name=> "delete_me",-values=>\@surveys} ),
					hidden( { -name=>"delete_survey",-value=>"true" } ),
					p( submit( {-value=>"Delete Survey!"} ), ),
					end_form,
					), #td
					td(
					font( {-face=>'Arial', -size=>'3'} ),
					h4( {-align=>'center'},"Add a survey?" ),
						start_form( { -action=>url } ),
					   table(
						Tr(
						td( {-align=>'right'},"Name for menu (this can contain spaces):" ),
						td( textfield( {-name=>"menu_name",-maxlength=>'20'} ), ),
						),
						Tr(
						td( {-align=>'right'},"Name for files (letters and numbers only):" ),
						td( textfield( {-name=>"name",-maxlength=>'20'} ), ),
						),
						Tr(
						td( {-align=>'right'},"Survey Question:" ),
						td( textfield( {-name=>"question",-maxlength=>'150',-size=>'40'} ), ),
						),
						Tr(
						td( {-align=>'right'},"Title of this survey (goes over graph):" ),
						td( textfield( {-name=>"title",-maxlength=>'150'} ), ),
						),
						Tr(
						td( {-align=>'right'},"Horizontal Label (i.e. 'Cheeses or Monks'):" ),
						td( textfield( {-name=>"xlabel",-maxlength=>'20'} ), ),
						),
						Tr(
						td ( {-align=>'right'},"Vertical Label (usually 'Votes'):", ),
						td( textfield( {-name=>"ylabel",-maxlength=>'20'} ), ),
						),
						Tr(
						td( {-colspan=>'2',-align=>'center'}, "List your choices seperated by commas:",
						textarea( {-name=>"choices", -cols=>'40',-maxlength=>'200'} ), ),
						),
						Tr(
						td( {-align=>'center'},submit(-value=>"Create Poll"), ),
						td( {-align=>'center'},reset(-value=>"Clear Form"), ),
						),
						), #table
					hidden( {-name=>"new_survey",-value=>'true'}), end_form,
					), #td
				   ), #tr
				), #table
				p( a( {-href=>url}, "Back To Surveys" ), ),
				), #div from top
				end_html;
	exit;
}

sub new_survey {
	$menu_name = $q->param( 'menu_name' ) || "Survey_$$";
	$name = $q->param( 'name' ) || "Survey_$$";
	$name =~ s/\W//g;
	$question	= $q->param( 'question' );
	$title = $q->param( 'title' );
	$xlabel = $q->param( 'xlabel' );
	$ylabel = $q->param( 'ylabel' );
	$choices = $q->param( 'choices' );
	if (!$choices) {
		print $q->header,start_html,h1("You can't have a survey with no choices!"),
				a( {-href=>url."?admin=1"}, "Try, try again!" ), end_html;
		exit;
	}
	my @choices = split( /,/ ,$choices );
	my @all = ($menu_name, $name, $question, $title, $xlabel, $ylabel, @choices);
	my $output = join( ":", @all);

	my $data_file = $survey_folder_path."data.info";
	if (-w $data_file) {
		open (DH, ">> $data_file") or die "where's the data file? : $!";
	} else {
		open (DH, "> $data_file") or die "Couldn't create the data file! : $!";
	}
	flock (DH,LOCK_EX) or die "Couldn't flock: $!";
	print DH "$output\n";
	close DH or die "Data file won't close : $!";

	my $info_output = join(":0:", @choices);
	$info_output = "$info_output:0";

	my $info_file= "$survey_folder_path$name.poll";
	open (INFO, "> $info_file") or die "Couldn't create the info file! : $!";
	print INFO $info_output;
	close INFO or die "Info file won't close : $!";

	print $q->redirect( url."?admin=1" );
}



sub delete_survey {
	my @line;
	my $delete_me = $q->param( 'delete_me' );
	my $data_file = $survey_folder_path."data.info";
	open (DH, "+< $data_file") or die "where's the data file? : $!";
	flock (DH,LOCK_EX) or die "Couldn't flock: $!";
	@line = grep {! /\Q$delete_me\E/} <DH>;
	seek DH, 0, 0;
	truncate (DH,0) or die "Can't truncate: $!";
	print DH @line;
	close DH or die "Data file won't close : $!";
	print $q->redirect( url."?admin=1" );
}

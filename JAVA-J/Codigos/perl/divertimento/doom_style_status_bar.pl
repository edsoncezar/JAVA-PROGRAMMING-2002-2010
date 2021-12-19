 ####################################
## FUNCTION: StatusBar ###############
##################################################
##                                              ##
##   Generates a statusbar that is of variable  ##
## width, and is very adaptable, optionally     ##
## displaying the percentage as well.           ##
##                                              ##
## ARGS:                                        ##
##      0) The text preceeding the bar          ##
##      1) The current value                    ##
##      2) The maximum value                    ##
##      3) The size of the bar (in characters)  ##
##      4) Display the percentage?              ##
##      5) The text following the bar           ##
## Returns:                                     ##
##      NULL                                    ##
##                                              ##
##################################################

sub StatusBar {
  my $level;
  my $numdots;
  my $numblanks;

  my $pre=$_[0];
  my $cur=int($_[1]);
  my $max=int($_[2]);
  my $size=int($_[3]);
  my $disp=$_[4];
  my $i;
  my $post=$_[5];

  $level = $cur/$max;
  
  $numdots = int($level * $size);

  $numblanks = $size - $numdots;

  print $pre . "\t";
  print " [";
  for($i = 0; $i < $numdots; $i++) {
    print ".";
  }
  for($i = 0; $i < $numblanks; $i++) {
    print " ";
  }
  print "]";
  if($disp ne "") {
    printf(" ($cur/$max, %3.2f%%)", (int($level*10000)/100));
  }
  print " $post\r";
}

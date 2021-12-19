#!/usr/bin/perl -w
#
# This was created to print out a list of all TLDs possable which are
# 3, 4, 5 and 6 characters long.
#
# This program will run for quite some time if you let it. I recommend
# hitting ^C after a short time. Espicially if you want to have it write to a file.
#
# I DO NOT recommend having it write to a file do the the huge dataset it creates.
# If you do have it write to a file, the fault is in no way mine. The author will not
# be held responsible for your actions.
#

# Letters and Numerals
@chars = qw(a b c d e f g h i j k l m n o p q r
		s t u v w x y z 0 1 2 3 4 5 6 7 8 9);

# Letters, Numerals and a null. If I don't have this, the program will only return
# TLDs with 6 characters. This way, it return TLDs with 3-6 characters.
@charsnull = (@chars,"");

foreach $char (@chars){
	$tld = $char;

	foreach $char (@chars){
		$tld2 = $tld . $char; 	# to see why I did it this way, run the program
						# so everything is appended to $tld and watch the output.
		foreach $char (@chars){
			$tld3 = $tld2 . $char;

			foreach $char (@charsnull){
				$tld4 = $tld3 . $char;

				foreach $char (@charsnull){
					$tld5 = $tld4 . $char;

					foreach $char (@charsnull){
						$tld6 = $tld5 . $char;
						print "$tld6\n"; 	# if you try the above comment, be sure
						#to change all the $tld(insert number here) vars, to just, $tld.
					}

				}

			}

		}

	}

}

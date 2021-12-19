sub shuffle {
	srand(time ^ ($$ + ($$ << 15))); # For pre-Perl 5.004.

	$r_deck = shift;
	$deck_l = scalar(@$r_deck);

	for ($i=0; $i<$deck_l; $i++) {
		$rand = int(rand($deck_l)); # Find random integer betwen
		                            # 0 and one less than the length
		                            # of the array (deck).

		($r_deck->[$i], $r_deck->[$rand]) = ($r_deck->[$rand], $r_deck->[$i]);
		# You gotta love Perl, don't you? #
		# Switch their values.            #
	}
}

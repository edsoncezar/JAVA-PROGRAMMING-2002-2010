 %table_no = (
    1001 => "0",
    1002 => "1",
    1003 => "2",
    1004 => "3",
    1005 => "4",
    1006 => "5",
    1007 => "6",
    1008 => "7",
    1009 => "8",
    1010 => "9",
    );
    %table_le = (
    1001 => "a",
    1002 => "b",
    1003 => "c",
    1004 => "d",
    1005 => "e",
    1006 => "f",
    1007 => "g",
    1008 => "h",
    1009 => "i",
    1010 => "j",
    1011 => "k",
    1012 => "l",
    1013 => "m",
    1014 => "n",
    1015 => "o",
    1016 => "p",
    1017 => "q",
    1018 => "r",
    1019 => "s",
    1020 => "t",
    1021 => "u",
    1022 => "v",
    1023 => "w",
    1024 => "x",
    1025 => "y",
    1026 => "z",
    1027 => "A",
    1028 => "B",
    1029 => "C",
    1030 => "D",
    1031 => "E",
    1032 => "F",
    1033 => "G",
    1034 => "H",
    1035 => "I",
    1036 => "J",
    1037 => "K",
    1038 => "L",
    1039 => "M",
    1040 => "N",
    1041 => "O",
    1042 => "P",
    1043 => "Q",
    1044 => "R",
    1045 => "S",
    1046 => "T",
    1047 => "U",
    1048 => "V",
    1049 => "W",
    1050 => "X",
    1051 => "Y",
    1052 => "Z",
    );
    %table_le_lower = (
    1001 => "a",
    1002 => "b",
    1003 => "c",
    1004 => "d",
    1005 => "e",
    1006 => "f",
    1007 => "g",
    1008 => "h",
    1009 => "i",
    1010 => "j",
    1011 => "k",
    1012 => "l",
    1013 => "m",
    1014 => "n",
    1015 => "o",
    1016 => "p",
    1017 => "q",
    1018 => "r",
    1019 => "s",
    1020 => "t",
    1021 => "u",
    1022 => "v",
    1023 => "w",
    1024 => "x",
    1025 => "y",
    1026 => "z",
    );
    %table_all = (
    1001 => "0",
    1002 => "1",
    1003 => "2",
    1004 => "3",
    1005 => "4",
    1006 => "5",
    1007 => "6",
    1008 => "7",
    1009 => "8",
    1010 => "9",
    1011 => "a",
    1012 => "b",
    1013 => "c",
    1014 => "d",
    1015 => "e",
    1016 => "f",
    1017 => "g",
    1018 => "h",
    1019 => "i",
    1020 => "j",
    1021 => "k",
    1022 => "l",
    1023 => "m",
    1024 => "n",
    1025 => "o",
    1026 => "p",
    1027 => "q",
    1028 => "r",
    1029 => "s",
    1030 => "t",
    1031 => "u",
    1032 => "v",
    1033 => "w",
    1034 => "x",
    1035 => "y",
    1036 => "z",
    1037 => "A",
    1038 => "B",
    1039 => "C",
    1040 => "D",
    1041 => "E",
    1042 => "F",
    1043 => "G",
    1044 => "H",
    1045 => "I",
    1046 => "J",
    1047 => "K",
    1048 => "L",
    1049 => "M",
    1050 => "N",
    1051 => "O",
    1052 => "P",
    1053 => "Q",
    1054 => "R",
    1055 => "S",
    1056 => "T",
    1057 => "U",
    1058 => "V",
    1059 => "W",
    1060 => "X",
    1061 => "Y",
    1062 => "Z",
    );
    %table_all_lower = (
    1001 => "0",
    1002 => "1",
    1003 => "2",
    1004 => "3",
    1005 => "4",
    1006 => "5",
    1007 => "6",
    1008 => "7",
    1009 => "8",
    1010 => "9",
    1011 => "a",
    1012 => "b",
    1013 => "c",
    1014 => "d",
    1015 => "e",
    1016 => "f",
    1017 => "g",
    1018 => "h",
    1019 => "i",
    1020 => "j",
    1021 => "k",
    1022 => "l",
    1023 => "m",
    1024 => "n",
    1025 => "o",
    1026 => "p",
    1027 => "q",
    1028 => "r",
    1029 => "s",
    1030 => "t",
    1031 => "u",
    1032 => "v",
    1033 => "w",
    1034 => "x",
    1035 => "y",
    1036 => "z",
    );
    print "\n\n";
    print 'Developed by D. Simpson & T. Threlfo (thomast@journalist.com) 2003';
    print "\n\n";
    print "\n\n\t::Welcome to PGen::\n\n";
    print "\nHow many random passwords do you want? ";
    $passwordsnum = <STDIN>;
    chomp($passwordsnum);
    print "\nHow many characters in each password? ";
    $length = <STDIN>;
    chomp($lenght);
    print "\n\nWhat type of passwords? \n
    \t1. Numerical\n
    \t2. Alphabetical\n
    \t3. Alphanumeral\n
    Enter which type you'd like (1-3) ";
    $type = <STDIN>;
    chomp ($type);
    if ($type == 1) {
    $which_table = %table_no;
    $range = 9;
    }
    elsif ($type == 2) {
    print "Would you like to use lowercase and uppercase letters, or just lowercase?\n
    \n1. Lowercase.\n
    \nWhich would you like? (1 for lowercase, or just press Enter for both): ";
    $upper_or_lower = <STDIN>;
    chomp ($upper_or_lower);
    if ($upper_or_lower == 1) {
    $which_table = %table_le_lower;
    $range = 25;
    }
    else {
    $which_table = %table_le;
    $range = 51;
    }
    }
    elsif ($type == 3) {
    print "\nWould you like to use lowercase and uppercase letters, or just lowercase?\n
    \n1. Lowercase.\n
    \nWhich would you like? (1 for lowercase, or just press Enter for both): ";
    $upper_or_lower = <STDIN>;
    chomp ($upper_or_lower);
    if ($upper_or_lower == 1) {
    $which_table = %table_all_lower;
    $range = 35;
    }
    else {
    $which_table = %table_all;
    $range = 61;
    }
    }
    else {
    print "You entered an incorrect option; Please enter 1-3 next time\n\n";
    exit(1);
    }
    print "\n";
    open FILE , ">passwords.txt";
    for ($j = 0; $j < $passwordsnum; $j++) {
    for($i = 0, ; $i < $length; $i++) {
    $ref = int(1001 + (($range)*rand()));
    if ($type == 1) {
    print FILE $table_no{$ref};
    print "$table_no{$ref}";
    }
    
    elsif ($type == 2) {
    print FILE $table_le{$ref};
    print "$table_le{$ref}";
    }
    
    elsif ($type == 3) {
    print FILE $table_all{$ref};
    print "$table_all{$ref}";
    }
    
    }
    print FILE "\n";
    print "\n";
    }
    print "\n\n$passwordsnum password(s) generated, and saved to passwords.txt\n";


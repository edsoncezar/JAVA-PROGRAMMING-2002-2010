#!/usr/bin/perl

my $fred = gen_char();
my $barney = gen_char();

print "fred:\n";
print "strength: $fred->{'strength'} +$fred->{'dam_bonus'} dam\n";
print "dexterity: $fred->{'dexterity'} +$fred->{'hit_bonus'} to hit\n";
print "endurance: $fred->{'endurance'}\n";
print "armor class: $fred->{'armor_class'}\n";
print "\nbarney:\n";
print "strength: $barney->{'strength'} +$barney->{'dam_bonus'} dam\n";
print "dexterity: $barney->{'dexterity'} +$barney->{'hit_bonus'} to hit\n";
print "endurance: $barney->{'endurance'}\n";
print "armor_class: $barney->{'armor_class'}\n";
print "\nlet the battle begin...\n";

until($fred->{'endurance'} <= 0 || $barney->{'endurance'} <= 0 ){
        sleep(1);
        $round++;
        print"round:$round\n";
        fight();
        print "fred: $fred->{'endurance'}\n";
        print "barney: $barney->{'endurance'}\n\n";
}
if ($fred->{'endurance'} > $barney->{'endurance'}){
        print "fred wins!!!\n";
}elsif($barney->{'endurance'} > $fred->{'endurance'}){
        print "barney wins!!!\n";
}else{
        print "tie!\n";
}
print "\ntotal rounds = $round\n";


sub gen_char{
        my %char;
        $char{'strength'} = roll(3, 6, 3);
        $char{'dexterity'} = roll(3, 6, 3);
        $char{'endurance'} = roll(3, 20, 6);
        $char{'armor_class'} = roll(1, 4, 3);
        if($char{'strength'} > 19){ $char{'dam_bonus'} = 3;
        }elsif($char{'strength'} <= 19 && $char{'strength'} > 17){
                $char{'dam_bonus'} = 2;
        }elsif($char{'strength'} <= 17 && $char{'strength'} > 15){
                $char{'dam_bonus'} = 1;
        }else{
                $char{'dam_bonus'} = 0;
        }
        if($char{'dexterity'} > 19){
                $char{'hit_bonus'} = 3;
        }elsif($char{'dexterity'} <= 19 && $char{'dexterity'} > 17){
                $char{'hit_bonus'} = 2;
        }elsif($char{'dexterity'} <= 17 && $char{'dexterity'} > 15){
                $char{'hit_bonus'} = 1;
        }else{
                $char{'hit_bonus'} = 0;
        }
        return \%char;
}

sub roll {
        my $number = $_[0];
        my $sides = $_[1];
        my $modifier = $_[2];
        my $total = 0;
        for($i = 0; $i < $number; $i++){
           $total += int(rand($sides)+1);
        }
        $total += $modifier;
        return $total;
}

sub fight {
        $barney_attack = roll($fred->{'armor_class'}, 6, $barney->{'hit_bonus'});
        $fred_attack = roll($barney->{'armor_class'}, 6, $fred->{'hit_bonus'});
        my $damage = 0;
        if ($fred_attack > $barney->{'dexterity'}){
            $damage = roll(1, 6, $fred->{'dam_bonus'});
            print "fred rolls $fred_attack, hits for $damage damage!\n";
            $barney->{'endurance'} = $barney->{'endurance'} -  $damage;
        }else{
           print "fred rolls $fred_attack, misses\n";
        }
        if ($barney_attack > $fred->{'dexterity'}){
            $damage = roll(1, 6, $barney->{'dam_bonus'});
            print "barney rolls $barney_attack, hits for $damage damage!\n";
            $fred->{'endurance'} = $fred->{'endurance'} - $damage;
        }else{
            print "barney rolls $barney_attack, misses\n";
        }
}


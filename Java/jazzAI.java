import jm.JMC;
import jm.music.data.*;
import jm.midi.*;
import jm.util.*;
import java.util.*;
import java.io.*;

public final class jazzAI implements JMC {
    public static void main(String[] args) throws Exception{

        char menuOption;

        Chord[] cArray;                     // Chords of song being parsed
        int index = 0;                      // index for counting Chords
        cArray = new Chord[200];

        Chord songKey;                      // Key of song being parsed
        int Tval;                           // Transpose value to put Song into C
        String[] SongArray = new String[103];// array of songs
        int songIndex = 0;                  // index for couting songs

        StringTokenizer[] s = new StringTokenizer[103];
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int[][][] trigramArray = new int[108][108][108];    // holds chord data
        int x,y,z;                          // indices for trigramArray
        
        //______________________________________________________________________
        fillTestArray(SongArray);

        //______________________________________________________________________
        while (SongArray[songIndex] != "") {
            s[songIndex] = new StringTokenizer(SongArray[songIndex]);
            songKey = new Chord(s[songIndex].nextToken());    // read key to calc transpose val
            Tval = songKey.key;        
            s[songIndex].nextToken();                  // remove separator
            index = 0;        
            while (s[songIndex].hasMoreTokens()) {
//                System.out.println(index);
                cArray[index] = new Chord(s[songIndex].nextToken());
//                System.out.println(temp);
                index++;
            }

            for (int i=0; i<index; i++) {
                cArray[i].Transpose(Tval);
            System.out.println(cArray[i].key+" "+cArray[i].type+"  "+cArray[i].toString());
                // add to trigram base
                if (i>=2) {
                    x = cArray[i-2].key * 9 + cArray[i-2].type;
                    y = cArray[i-1].key * 9 + cArray[i-1].type;
                    z = cArray[i].key * 9 + cArray[i].type;
//                    System.out.println(x+" "+y+" "+z);
//                    if ((cArray[i-2].type != -1) && (cArray[i-1].type != -1) && (cArray[i].type != -1))
                       trigramArray[x][y][z]++;
                }
            }
            songIndex++;
            System.out.println("-----------------"+songIndex);
        } 

        
        while (true) {
            // Main Loop
            System.out.println("-----------------------------------");
            System.out.println("Welcome to the JazzAI music helper");
            System.out.println("select an option");
            System.out.println();
            System.out.println("1: Chord Chooser");
            System.out.println("2: Song Auto Generator");
            System.out.println("q: Quit");

            menuOption = 'D';               // set to some nonvalid option
            while (menuOption != 'q' && menuOption != '1' && menuOption != '2') {
                menuOption = (char) System.in.read();
            }
            switch (menuOption) {
                case 'q':
                    System.exit(0);
                case '1':
                    ChordPicker(trigramArray, in);
                case '2':
                    SongGenerator(trigramArray, in);
                default:
            }                              
        
        }        
        //______________________________________________________________________

    }

//__________________________________________________________________________________    
    public static void ChordPicker(int[][][] trigramArray, BufferedReader in) throws Exception {
        String inputKey = "";               // Key Sig for piece
        String input1, input2;                 // user input
        char Again;
        boolean Done = false;
        Chord c1 = new Chord();             // user inputted chord 1
        Chord c2 = new Chord();             // user inputted chord 2
        Chord c3 = new Chord();             // suggested chord
        int x,y,z;                          // indices for trigramArray

        //______________________________________________________________________        
            System.out.print("Pick a Key: ");
            while (inputKey.equals("")) {
                inputKey = in.readLine();
            }

            songKey = new Chord(s[songIndex].nextToken());    // read key to calc transpose val
            Tval = songKey.key;        

        while (!Done) {
            input1 = "";
            input2 = "";
            
            System.out.print("Chord1: ");
            while (input1.equals("")) {
               input1 = in.readLine();
            }
            System.out.print("Chord2: ");
            input2 = in.readLine();
            c1.setChord(input1);
            c2.setChord(input2);

            x = c1.key * 9 + c1.type;
            y = c2.key * 9 + c2.type;

            System.out.print("Suggested Chords: ");
            for (int i=0; i<108; i++){
                if (trigramArray[x][y][i] > 0) {
                    c3.setChord(i/9,i%9);
                    System.out.print(c3+" "+trigramArray[x][y][i]+"    ");
                    
                }
            }
            System.out.println();
                System.out.println("Another? (y/n)");
                Again = (char)System.in.read();
                if (Again=='n')
                   Done = true;
        }
    }

//__________________________________________________________________________________    
    public static void SongGenerator(int[][][] trigramArray, BufferedReader in) throws Exception {
        String inputKey = "";               // Key Sig for piece
        String input1 = "";               // Key Sig for piece
        int SongLength = 0;
        int NonConformity = -1;              // user input
        int i, walk, walkindex = 0;
        int nextChord = 0;
        char Again;
        String NewSong = "";
        Chord[] cArray = new Chord[200];

        boolean Done = false;
        Chord c1 = new Chord();             // user inputted chord 1
        Chord c2 = new Chord();             // user inputted chord 2
        Chord c3 = new Chord();             // suggested chord
        int x = 0;
        int y = 0;
        int z = 0;                          // indices for trigramArray

        //______________________________________________________________________        
            System.out.print("Pick a Key: ");
            while (inputKey.equals("")) {
                inputKey = in.readLine();
            }
            
            System.out.print("How many changes in the song?"); 
            while (SongLength == 0) {
                input1 = in.readLine();
                SongLength = Integer.decode(input1).intValue();
            }

            System.out.println(SongLength);
            
            System.out.print("NonConformity Index (0-10)"); 
            while (NonConformity == -1) {
                input1 = in.readLine();
                NonConformity = Integer.decode(input1).intValue();
            }
        cArray[0] = new Chord(0,0);
        for (i = 1; i < SongLength; i++) {
            if (i>1) {
                 x = cArray[i-2].key * 9 + cArray[i-2].type;
                 y = cArray[i-1].key * 9 + cArray[i-1].type;
            }
            
            if (((int)(Math.random()*10)) > NonConformity) { // do the normal thing
                walkindex = 0;
                walk = (int)(Math.random()*108);
//                System.out.println(walk);
                while ((trigramArray[x][y][walk] == 0) && (walkindex<108)) {
                    walk = (walk + 1) % 108;
                    walkindex++;
                }

                if (walkindex == 108) {
//                    System.out.println("i'm here.  in sparseland.");
                    cArray[i] = new Chord(0,0);             
//                    System.out.println(cArray[i]+"  "+i);
                }
                else {
                    nextChord = walk;
                    cArray[i] = new Chord(nextChord/9, nextChord%9);
                }
           }
           else {
                nextChord = (int)(Math.random()*107);
                cArray[i] = new Chord(nextChord/9, nextChord%9);
           }
        }
        
        for (i=1; i < SongLength; i++){
            System.out.println(cArray[i]);
        }

    }

//__________________________________________________________________________________    
    public static void fillTestArray(String[] SongArray) {
        for (int i=0; i<103; i++) {
            SongArray[i] = "";
        }
// Godchild p.135
// Ab Major
        SongArray[0] = "AbM : Eb7 Ab7 Dbm Gb7 Cm7 Bm7 Bbm7 A7 AbM7 Bm7 Bbm7 A7 Ebm11 Dm11 Db7#11 CM7 C#dim Dm7 G7 C Am7 Abm9 Db7 EbM7 Edim Fm7 E7 Bbm7 Bdim Bbm7 Eb7 A Eb7 Ab7 Dbm Gb7 Cm7 Bm7 Bbm7 A7 AbM7";

// Antigua p.30
// Eb Major
        SongArray[1] = "EbM : EbM9 Bb7b9 EbM9 Bb7b9 BM7 F#7b9 BM7 F#7b9 EBM9 Bb7b9 EbM7 EbM7b5 Gm7 Dbm Fm7 Dm7b5 G7 Cm7 F13b5 Fm7 EM7 EbM7 Bbm7 EbM7 Bbm7 Eb Eb5 Eb Eb5 Abm7 Ebm Abm7 F9 Bb13b9 EbM9 Fdim EbM9 Fdim BM7 C#dim BM7 C#dim EbM9 Fdim EbM7 EbM7b5 Gm9 Dbm Fm7 Abm7 Gm7 Gbm#7 Fm7 EM7 E7 EbM7 Fdim EbM9 Fdim";

// Before you go p.40
// F#minor 
        SongArray[2] = "F#m : Cm11b5 BM7#11 C#11 F#M7  Am11 Gm7 Bm11 E7 Cm7 EbM7 Cm7 Cm7 Am11 Dm7 BbM7 Gm9 F#M7 BbM7 F F#M7 G7#5 Cm7b5 D F Bbm7 BM7#11 C#11 F#M7 Am11 Gm7 Bm11 E77 Cm7 EbM7 Cm7 Cm7 Bb Am11 Dm7 BbM7 Gm9 F#M7 Bbm7 F F#M7 G7#5 CM7b5 D F Bbm7 BM7#11 C#11 F#M7 Am11 Gm7 Bm7 C3m7 F C# Dm7 EbM7 Gm7 Bm7 G#M7#5 C G F#M7 Fm7#5 BM7 Bbm7 Bbm7 Ab Gm7b5 Bbm7 F F#M7 G7#5 G#m11 AM7 Bbmll BM7 Cm7b5 D C# C# D C# D C# D C# F#m7";

// Broadway p.58 
// Eb MAjor
        SongArray[3] = "EbM : EbM7 Ab7 Fm7 Bb7 EbM7 Ab7 Fm7Bb7 EbM7 Bbm7 eb7 AbM7 Abm7 Db7 GbM7 FM7 Bb7 EbM7 Ab7 Fm7 Bb7 EbM7";

// Born to be Bad p.59
// C minor
        SongArray[4] = "Cm : Fm7 G7#9#5 Cm7 Fm9 G7#9#5 Cm7 Fm9 G7#9#5 CM7 Fm9 G7#9#5 Dm C Cm7 Dm C Cm7 Fm9 G7#9#5 Dm C Cm7 Dm11 DbM7 AbM7 G7#9#5 DbM7 Dm7b5 G7b9#5 Cm7 F7#11 F13b9 Bbm7 Dm7 Cm DbM7 F#m7 B13 BM7 Ebm7 Ab13 DbM7 Db Gb Cm7 Dm7 G7#11 Fm11 Cm11 Fm9 Cm11 Fm11 Cm11";

// Brazil p.61
// G Major 
        SongArray[5] = "GM : G G G6 G G G G6 G Am Am#5 Am6 F A Am Am#5 Am6 F A Am Am#5 Am6 Adim7 GM7 G7 Gb7 F7 E7 Dm F7 E7 Dm F7 Am Cm Cdim7 G9 GM7 G Bbdim Am7 Am Eb9 D7 D9 G G G6 G Am Am#5 Am6 Adim7 G G G6 G Am Am#5 Am6 D7 G6 G G G G6";

// C'est si Bon p.78
// Bb Major
        SongArray[6] = "BbM : Cm7 F7 BbM7 Cm7 F7 Bb6 F Cm7 F7 BbM7 Cm7 F7 Bb6 GbM7 Gm7 C7 cm7 F7 Cm7 F7BbM7 Cm7 F7 Dm7b5 G7b9 Cm7 Ebm6 BbM7 Bb Cm7 F7 Bb Gm7 F7 Bb Gm7 F7 Bb Gm7 F7 Bb Gm7 F7 Bb6";

// Dream a Little Dream of Me p.99
// G Major
        SongArray[7] = "GM : G Eb7 D7 G E7 Bm7b5 E7 Am7 Am7b5 F9 G A7 D7 G Eb7 D7 G E7 Bm7B5 E7 Am7 Am7b5 F9 G Eb7 D7 G Bb7 Eb Eb6 Bb7 Eb Eb6 Bb7 Eb D7 Am7 D7 G Eb7 D7 G E7 Bm7b5 E7 Am7 Am7b5 F9 G Eb7 D7 G Eb7 D7 G";

// EASY STREET P.113
// Eb Major
        SongArray[8] = "EbM : EbM7 C7 Fm7 G7#5 Cm7 Bbm7 Eb7Abm7 Bb7 Gm7 C7 Fm7 Bb7 EbM7 Cm7b9 Fm7 Bb7 EbM7 C7 Fm7 G7#5 Cm7 BbM7 Eb7 Abm7 Bb7 Gm7 C7 Fm7 Bb7 EbM7 Bbm7 Eb7 AbM7 Abm7 Db7 EbM7 Bbm7 Eb7 AbM7 Abm7 Db7 EbM7 F7 Fm7 Bb7#5 EbM7 C7 Fm7 G7#5 Cm7 Bbm7 Eb7 Abm7 Bb7 Gm7 C7 B7 Bb7#5 Ebm7 Ab7 EbM7";

// FRECKLE FACE P.128
// Bb Major
SongArray[9] = "BbM : Cm9 F7b9 BbM9 G7#5 Cm9 F9 F7b9 BbM9 BbDIM Cm9 F7b9 BbM9 G7#5 Cm9 F9 Eb F Bb Bb7 Edim Ebm6 Bb B7 F# Fm9 Bb7b9 Eb9 D7#9 D7b9 Gm#7 Gm7 C13 B9 C9 Cm7 Gb9#11 F7 Dbdim Cm9 F7b9 BbM7 Dbdim Cm7 Bdim Cm7 F7b9 Dm7b5 G7b9 C E Ebm7 Ebm7 Ebm7 Dm7 Bb6 Dbdim Cm7 Cm7 F Ebm6 F Bb7 Edim Ebm6 Bb6";
 
// GEORGIA ON MY MIND P.133
// F Major
SongArray[10] = "FM : F A7 Dm Dm C G B Bbm6 Am7 D7 Gm7 C7 Am7 D7b9 Gm7 C7 F A7 Dm Dm C G B Bbm6 Am7 D7 Gm7 G9 C13 F Eb9 F A7 Dm Gm6 Dm Bb7 Dm Gm6 Dm7 G7 Dm C#dim7 F6 C Bm7b5 E7 Am7 F#dim7 Gm7 C7 F A7 Dm Dm C G B Bbm6 Am7 D7 Gm7 G9 C13 F Dm7 Gm7 C7 C7#5 F C7#5 F6";

// GLAD TO BE UNHAPPY P.136
// F Major
SongArray[11] = "FM : Gm7 Eb7#11 Gm7 C7 Gm7 Eb7#11 Gm7 C7 F F7#5 BbM7 FM7 D7 Gm7 C7 F F7#5 BbM7 Bdim7 Am7 D7b9 Gm7 C7 F Gm7 Eb7#11 Gm7 C7 Am7 Dm7 Gm7 C7b9 F6";

// GREEN EYES P.141
// Eb Minor
SongArray[12] = "Ebm : Ebm7 Eb Eb6 EbM7 Eb Eb6 Edim7 Bb7 Bb7 Ab Bb Bb7 C C7 F7 Bb7 C C7 F7 Bb7 EbM7 Eb Eb6 EbM7 Edim7 C7 E C7 Fm C7 Fm Ab6 AbM7 Adim7 Eb Bb Bbm6 Db C7 F7 Bb7 Eb Eb6 Edim7 Bb7 Eb";

// I AM IN LOVE P.156
// F Major
SongArray[13] = "FM : F F6 F7 D7 Gm Gm6 Gm7 C7 C7b9 FM7 C F F6 F9 Am7b5 D7 Gm Gm6 Gm7 Gm Gm7 C9#5 F Ab7 Db Ebm7 Ab7 DbM7 F#7 BM7 Bm7 E7 A Bm7 E7 AM7 Am7 D9 G Db7#11 Gm7 C9#5 F F6 F Am7b5 D7 Gm7 G9 Bdim7 F6 C Cm7 F7 BbM7 Bbm6 F Bbm6 F";

// I GOT IT BAD AND THAT AIN'T GOOD P.157
// G Major 
SongArray[14] = "GM : G6 G6 G9 G9#11 Bm7 E7b9 Am7 D9 C9#11 Bm7 E7b9 Am7 D7#5 G6 C9#11 Bm7 E7b9 Am7 D9 G Dm7 G7#5 CM7 Am7 B7 E7 A7 Eb7 D7b9 G6 C9#11 G6 D6 A C13 B7b9 Em7 A13 D7 D7 GM7 Em7 A7 Am7 B7#5 E9 A7D7b9 G Em7 Am7 D7b5 GM7 Em7 A7 Am7 B7#5 E9 A7 D7b9 G Am7 Bbdim7 G B CM7 Cm6 F7 GM7 F7 BM7 E7 Am7 D7 GM7 Em7 A7 Am7 B7#5 E9 A7 D7 G Em7 Am7 D7 G Cm6 G";

// I REMEMBER YOU P.164
// G Major
SongArray[15] = "GM : GM7 C#m7b5 F#7 GM7 Dm7 G7 CM7 F7 Bm7 E7 Am7 D7 GM7 C#m7#5 F#7 GM7 Dm7 G7 CM7 F7 GM7 Dm7 G9 CM7 F#m7 B7 EM7 F#m7 B7 EM7 E6 Em7 A7 DM7 Am7 D7 GM7 C#m7b5 F#7 GM7 Bm7b5 E7b9 Am7 F7 GM7 A9 A#dim7 Bm7 E7 Am7 D7b9 GM7 Am7 D7 GM7";

// IN A SENTIMENTAL MOOD P.176
// F Major
SongArray[16] = "FM : Dm Dm#7 Dm7 Dm6 Gm Gm#7 Gm7 Bb7 A7 Dm7 D7 Gm7 Gb7 FM7 Em7b5 A7 Dm Dm#7 Dm7 Dm6 Gm Gm#7 Gm7 Bb7 A7 Dm7 D7 Gm7 Gb7 FM7 Ebm7 Ab7 DbM7 Bbm7 EbM7 Ab7 DbM7 Bb7 Eb7 Ab7 dBm7 Bbm7 Ebm7 Ab7 Gm7 C7 Em7b5 A7 Dm Dm#7 Dm7 Dm6 Gm Gm#7 Gm7 Bb7 A7 Dm7 D7 Gm7 Gb7 FM7";

// I'VE GOT MY LOVE TO KEEP ME WARM P.189
// Eb Major
SongArray[17] = "EbM : Eb Gbdim7 Fm7 Bb7EbM7 D7 Am7b5 D7b9 Gm7 C7 Fm7 Bb9 EbM7 Edim7 Fm7 Bb7 Eb6 Am7b5 D7b8 Gm7 Am7b5 D7 Gm7 C7 Fm7 Gm7 C7 Fm7 Bb7 Eb Gbdim7 Fm7 Bb7 EbM7 D7 Am7b5 D7b9 Gm7 C7 Fm7 Bb9 EbM7 Edim7 Fm7 Bb7 Eb6";

// LOVE ME OR LEAVE ME P.206
// Ab Major
SongArray[18] = "AbM : Fm7 Gm7 C7 Fm7 Gm7 C7 AbM7 Fm7 Bbm7 Eb7 AbM7 Gm7b5 C7 AbM7 Db7 AbM7 Cm7 F7 Bbm F7b9 Bbm Bbm7 Eb7 Cm7 Bdim7 BbM7 C7b9 Fm7 Gm7 C7 Fm7 Gm7 C7 AbM7 Fm7 Bbm7 Eb7 Ab";

// LULLABY OF BIRDLAND P.246         
// G Major
SongArray[19] = "GM : Em C#m7b5 F#7b9 B7b9 Em7 Am7 D9 GM7 Em7 Am7 D7b9 Gm7 F#m7b5 B7 Em C#M7B5 F#7b9 B7b9 Em7 Am7 D9 GM7 Em7 Am7 D7b9 GM D7 GM7 E9 E7b9 Am7 D9 D7b9 GM7 B7 Em C#m7b5 F#7b9 B7b9 Em7 Am7 D9 GM7 Em7 Am7 D7b9 GM7 F#m7b5 B7 GM7 Am7 D9 GM7 C9 Am7 Ab9 G6 G9";

// MY BABY JUST CARES FOR ME
// G Major
SongArray[20] = "GM : GM7 GM7 B Bbdim7 Am7 D7 Am7 B7 Em7 A7 D7 E7b9 E7 Am7 F#7 Bm7 E7 Am7 A7 D7 G";

// mY GIRL P.250
// F Major
SongArray[21] = "FM : F Bb F Bb F Bb F Bb F Gm Bb C F Gm BbC FM7 Gm7 C7 F Bb F Bb F Bb F Bb F Gm Bb C F Gm Bb C FM7 Gm7 C7 F Bb F Bb F Bb F Bb F Gm Bb C F Gm Bb C FM7 Gm7 C7 FM7 Gm7 C7 FM7 Gm7 C7 FM7 Gm7 C7 F";

// NICE PANTS P.260
// Eb Major
SongArray[22] = "EbM : Fm7 Bb7 Fm7 Bb7 Fm7 Bb7 Eb Ab Eb Ab7 Adim Ab7 Adim Eb A13 Ab7 Adim Eb Bb G7 B Cm7 F7 Bb7 Eb A13 Ab7 Adim Eb Bb G7 B Cm7 F7 Bb7 F7 Bb7 Eb";

// On Broadway p.265
// F Major 
SongArray[23] = "FM : F Eb F Eb F Eb F Eb F Eb F Eb F Eb F Eb F Bb Ab Bb Ab Bb Ab Bb C F Eb F  Eb F Eb F Eb F Eb F Eb F Eb F Eb F";

// PRELUDE TO A KISS P.282
// Ab Major
SongArray[24] = "AbM : D7 G7 Gm7 C7 FM7 B7 Bb7 A7 Dm7 Dm7 G7 CM7 Ebdim7 Dm7 G7b9 CM7 D7 G7 Gm7 C7 FM7 B7 Bb7 A7 Dm7 Dm7 G7 Cm7 Ebdim7 Dm7 G7 Cm7 F#7b5 B7 EM7 C#m7 F#m7 B7 Em7 A7 Dm7 Dm7 G7 Cm7 Ebdim7 Dm7 G7 CM7";

// REMEMBER P.290
// F Major
SongArray[25] = "FM : BbM7 Eb7#11 Dm7 G7 Gm7 C7b9 FM7 Cm7 F7 BbM7 Eb7#11 Dm7 G7 Gm7 C7b9 FM7 Cm7 F7 BbM7 Cm7 F7 BbM7 Eb7 Am7 D7 Gm7 A7#5 BbM7 Eb7 Dm7 G9 Gm7 C7 FM7 Cm7 F7 FM7";

// THE SINGLE PETAL OF A ROSE P.310
// Db Major 
SongArray[26] = "DbM : DbM7 GbM7 DbM7 Ebm7 Ab7 DbM7 GbM7 Eb7 DM7 BM7 DbM7 BM7 DbM7 DbM7 GbM7 DbM7 Ebm7 Ab7 DbM7 GbM7 Eb7 DM7 BM7 DbM7 BM7 DbM7 Bbm Bbm#5 Bbm6 Ab D7 DbM7 GbM7 DbM7 Ebm7 Ab7 DbM7 GbM7 Eb7 DM7 BM7 DbM7 BM7 DbM7";

// SIPPIN' AT BELLS P.311
// F Major
SongArray[27] = "FM : FM7 Bb7 FM7 Gbm7 Cb7 BbM7 BbM7 Eb7 Am7 Abm7 Gm7 C7 F Gm7 Gb7 F6";

// THE SONG IS ENDED P.321
// Eb Major
SongArray[28] = "Eb : Eb Bb7#5 Bbm9 Eb9 Ab Db9 Cm7 Gb13 F7 Bb7 Bb7#5 Eb Bb14 Eb Bb7#5 Bbm9 Eb9 Ab Db9 Cm7 Gb13 F7 Bb7 Bb7#5 Eb Gm Em7b5 Am7b5 D7 Gm7 C7 Fm Bb7 Eb Bb7#5 Bbm9 Eb9 AbM7 Abm6 Gm7 Cm7 F7 Bb7 Bb7#5 Eb Eb";

// sTEPPIN' OUT WITH MY BABY P.329
// D minor 
SongArray[29] = "Dm : Dm Dm C Gm Bb A7 Dm Dm C Gm Bb A7 Dm Dm C Gm Bb Em7b5 Dm Dm F Gm6 Bb7 A7 Dm6 Dm Dm C Gm Bb A7 Dm Dm C Gm Bb A7 Dm Dm C Gm Bb Em7b5 Dm Bb7 A7 Dm6 D DM7 D6 DM7 D DM7 D6 D Em7 Em7b5 A7 Dm C Gm Bb A7 Dm Dm C Gm Bb A7 Dm Dm C Gm Bb Em7b5 D F# Fdim7 Em7 Eb7 D6 Em7 Eb7 D6";

// WHY WAS I BORN P.368
// Eb Major
SongArray[30] = "EbM : EbM7 F#dim7 Fm7 Bb7 EbM7 Bbm7 Eb7 AbM7 Db7 EbM7 Cm7 Fm7 Bb7 BbM7 Eb7 AbM7 Db7 Gm7 C7 Fm7 Bb7 EbM7 F#dim7 Fm7 Bb7 EbM7 Bbm7 Eb7 AbM7 Db7 EbM7 Cm7 F7 Db7 EbM7 F#DIM7 Fm7 Bb7 EbM7";

// wHY DON'T YOU DO RIGHT P.369
// D minor
SongArray[31] = "Dm : Dm7 Bb7 A7 Dm7 Bb7 A7 Gm7 Bb7 A7 Dm7 Bb7 A7 Gm7 A7 Gm7 A7 Dm7 Bb7 A7 Dm7 Bb7 A7 Dm7 Bb7 A7 Gm7 Bb7 A7 Dm7 Bb7 A7 Gm7 A7 Gm7 A7 Dm7 Bb7 A7 Dm7 Bb7 A7 Dm7 Bb7 A7 Gm7 BB7 A7 Dm7 Bb7 A7 Gm7 A7 Gm7 A7 Gm7 A7 Dm7 Bb7 A7";

// Besame Mucho p.41
// D minor
SongArray[32] = "Dm : Dm Gm6 Dm G Gm Gm F#dim Gm A7 Dm A7 Dm D7 D7 D7b9 D G Gm Dm E7b9 A7 Dm Gm6 Dm Gm Dm A7 Gm6 Dm Gm Dm E7 Bb7 A7 Dm Gm6 Dm G Gm F#dim Gm A7 Dm A7 Dm D7 C E D7 D7b9 D G Gm Dm E7b9 A7 A7#5 Dm Bb9 A7 A7 Dm Gm6 Dm";

// BETWEEN THE DEVIL AND THE DEEP BLUE SEA P.44
// F Major
SongArray[33] = "FM : F Gm9 C7 F Gm9 C7 Cm7 F7 BbM7 Eb7 F C7 F C7 F E7 A F#m Bm7 E9 A F#m Bm7 E7 C Am Fm6 Ab7 G7 C7 F C7 F";

// A BLOSSOM FELL P.45
// Bb Major
SongArray[34] = "BbM : Bb6 Bdim7 Cm7 F7 F7#5 Bb BbM7 D C#dim7 Cm7 F7 Cm7 F7 F7#5 Bb Bdim7 Cm7 F7 Bb6 Bdim7 Cm7 F7 F7#5 Bb Bb7#5 Eb Bb Cm7 F7 Fm9 Bb7 Bb7#5 Eb Ebm6 Bb C#dim7 Cm7 F7 Bb F7 Bb";

// BLUE FLAME P.47
// Bb Major
SongArray[35] = "BbM : Bb7 Eb7 F7 Bb Bb7 Eb9 F7 Bb7 F7 Eb7 Bb7 F7 Bb7 Eb F7 Bb Bb7 Eb7 F7 Bb7 F7 Eb7 Bb7";

// BLUE STAR P.53
// Eb Major
SongArray[36] = "EbM : Bb7 Eb Ebdim Fm7 Bb13 Bb7#5 Eb G F#dim7 Fm7 Gm7b5 C7b9 Ab6 Am7b5 Fm7 Bb Bb7b9 Eb Ebdim7 Fm7 Bb Bb7#5 Eb G F#dim7 Fm7 Gm7b5 C7b9 F13 A Bb Abm6 Eb Fm7 Bb7 Eb";

// CUPCAKE P.95
// C# Major
SongArray[37] = "C#M : F#m7b5 C9 B7#9 F7b9 Em7 Em7 A Eb9 Eb7b9 Dm9 G13 C9 B9#11 Bb9#11 Bbm7 Eb7b9 D9 Am7 D7b9 Db9 Ab7 G13 C9 Ab13 Gm9 C7#9 F13 C9 F13 C7#9 G7b13 C7#9 F#m7b5 C9 B7#9 F7b9 Em7 Em7 A Eb9 Eb7b9 Dm9 G13 C9 B9#11 Bb9#11 G# C6 G D13 Am7 D7#11 Db13 D9 G7#11 C9 G# C6 G D13#11 Am7 D7#11 Db13 D9 G7#11 C9 Bb3 Em11 A7#11 Bbm9 Eb11#5 D13 D13 D7b13 Db13 G7b13 G13 C9 F#m7 B7b13 Em7 A7b13 GbM7 Ab FM9 G G7#9 CM7#11";

// DON'T WORRY 'BOUT ME P.97
// Ab Major
SongArray[38] = "AbM : Bbm7 Eb7b9 AbM7 Fm7 Bbm7 Eb7b9 Ab6 Bdim7 Bbm7 Db Eb Eb7 Bbm7b5 Eb7b9 C7b9 Fm7 Bb7 Bbm7 Cm7 F7b9 Bbm7 Eb7b9 AbM7 Fm7 Ebm7 Ab13 Ab7#5 DbM7 Bbm7 Gb7 Cm7 B7 Bbm7 Ebm7b9 Ab Fm Bbm7 Eb7 Ab";

// DON'T YOU KNOW I CARE P.98
// Eb Major
SongArray[39] = "EbM : EbM7 Bb7b9 Bbm7 Eb7 F7 Bb7 EbM7 Fm7 E7 EbM7 C7b9 Fm7 Bb7b9 EbM7 Fm7 Bb7 EbM7 Bb7b9 Bbm7 Eb7 F7 Bb7 EbM7 Fm7 E7 EbM7 Am7b5 D7 Gm7 Am7b5 D7 Gm7 F#7 BM7 Ebm7 Adim7 B7 Fm7 Bb7 EbM7 Bb7b9 Bbm7 Eb7 F7 Bb7 EbM7 Fm7 E7 EbM7 C7b9 Fm7 Bb7b9 Eb6";

// CURVES AHEAD P.98
// D minor
SongArray[40] = "Dm : BbM9 Cm11 BbM9 EbM7 BbM9 Cm11 BbM9 Cm9 F11 BbM9 Cm11 BbM9 EbM7 BbM9 Cm11 BbM9 EbM7 EbM9 Gm7 C E Cm11 EbM7 Gm7 C E Cm11 EbM7 Cm11 EbM7 GM7 C E Cm11 EbM7 F11 Gm7 F Bb C F Eb6 C11 Dm11 EbM7 Gm7 F Bb C F Eb6 C11 Dm11 EbM7";

// EMANCIPATION BLUES P.116 
// G Major
SongArray[41] = "GM : G G7 C7 G D9 C9 D9 C9 D9 C9 G";

// FOREVER IN LOVE P.128
// F Major
SongArray[42] = "FM : F Am Bb C F Am Bb C Bb C Dm A F Am Bb C F Am Bb C Am Bb C F Am Bb C F Am Bb C F Am Bb C F Am Bb C F Am Bb C F Am Bb C F";

// BLUES IN TIME P.55
// Bb Major
SongArray[43] = "BbM : Bb7 Eb7 Bb7 Eb7 Bb7 Dm7 G7 Cm7 F7 Bb7 G7 Cm7 F7 Bb7";

// GREENS P.140
// F Major
SongArray[44] = "FM : F F Eb D6 Eb Db F F C F9 B9#11 Bb9 Ab7 G7b9 Bb C F D13b9 G7 C7 F6 F F Eb Ddim Eb Db F13#11 Bb6 Cm7 Dbdim Bb9 F6 Gm7 Abdim Am7 A7b13 D7#9 Gm7 Ab C Gm C F6";

// HEEBIE JEEBIES P.146
// Ab Major
SongArray[45] = "AbM : Eb Eb7 Ab Db7 Ab Bdim7 Eb7 Bb7 F#dim7 Eb7 G Ab F7 Bb9 Eb7 Eb Eb7 Ab Db7 Ab Ab7 Db6 E7 Ab F7 Bb7 Eb7 Ab C7 F7 Bb7 Eb7 Ab Adim Ab Db7 Ab";

// HONEST I DO P.148
// F Major
SongArray[46] = "FM : F C7 F G#dim7 Gm7 F Bb C7 F G#dim7 Gm7 F Bb C7 F G#dim7 Gm7 F";

// I LET A SONG OUT OF MY HEART P.160
// Eb Major
SongArray[47] = "EbM : Eb Ab7 Eb Cm7 Gm7 C7 Gm7 C7 Ab6 Gm7 Fm7 F#dim7 Eb G Ab Eb Cm7 Fm7b5 Bb7 Eb Abm6 Eb Eb G F#dim7 Fm7 Bb7 EbM7 Eb6 Dm7 G7 Cm7 Dbm7 Gb7 B7 Bb9#5 Eb Ab7 Eb Cm7 Gm7 C7 Gm7 C7 Ab6 Gm7 Fm7 F#dim7 Eb G Ab Eb Bb7b9 Eb6 Bb7b9 Bb7 Eb6";

// I WISHED ON THE MOON P.168
// Bb Major
SongArray[48] = "BbM : Cm7b5 F7 BbM7 Dm7 G7 Cm7b5 F7 BbM7 Am7b5 D7 G7 C7 F7 Bb7 EbM7 Ab7 Cm7 F7 BbM7 Dm7 G7 Cm7b5 F7 BbM7 Dm7 G7 Cm7b5 F7 Bbm7 Am7b5 D7 G7 C7 F7 Bb7 EbM7 Ab7 Cm7b5 F7 BbM7 Dm7 G7";

// I'M A FOOL TO WANT YOU P.171
// E minor
SongArray[49] = "Em : Em E7b9 Am Am C Am Em C7 B7 B7b9 Em B7 Em Am Am7 D9 D9#5 G Am Am#7 Am7 D7 GM7 G6 F#m7b5 B7 B7b9 Em E7b9 Am Am C Bdim7 Am Em C7 Am F#m B7 B7b9 Em";

// I'M JUST A LUCKY SO AND SO P.173
// G Major
SongArray[50] = "GM : G6 CM7 G6 Dm7 G7 C7 A7 Am7 D9 G6 E7#9 Am7 D7 G6 CM7 G6 Dm7 G6 C7 A7 Am7 D9 G6 C7 G6 C9 GM7 C9 GM7 F#m7b5 B7 Em7 A7 Am7 D9 G6 CM7 G6 Dm7 G7 C7 A7 Am7 D9 G6 E7#9 Am7 D7 G6";

// I TOLD YA I LOVE YA NOW GET OUT P.166
// G Major
SongArray[51] = "GM : G9 C9 A9 Ab9 Bm7 Bbm7 Am7 AbM7 G Am F G9 Gm C9 A9 Ab9 Bm7 Bbm7 Am Am7 AbM7 G G9 C F7 Eb7 D7 Eb7 D7 G9 A9 Ab9 Bm7 Bbm7 Am7 AbM7 G A7#5 D9 Eb7 D9 G A7#5 D9 G F9 G6";

// IF YOU GO P.169
// C minor
SongArray[52] = "Cm : Cm Bb7 Ab D7 Fm6 Cm Ab7 D7 Ab7 G7 Ab7 G7 Cm Bb7 Ab D7 Fm6 Cm Ab7 D7 Ab7 G7 Ab Ab7 D7 Ab7 G G7b9 Cm Ab7 G7 Cm";

// I'LL CLOSE MY EYES P.172
// G Major
SongArray[53] = "GM : G Cm G F#m7 B7 F#m7 B7 Em7 Dm7 G7 CM7 Cm7 G F#7 Bm7 D7 G Bm7b5 E7 Am7 Cm6 D7b9 G6";

// IN THE COOL, COOL, COLL OF THE EVENING P.177
// F Major
SongArray[54] = "FM : F Fdim7 Gm7 C7 Gm7 C7b9 F6 C7 F Cm7 F7 BbM7 Eb7 F D7 Gm7 C7 F";

// LITTLE WHITE LIES P.203
// G Major
SongArray[55] = "GM : GM7 Cm7 F7 GM7 Cm7 F7 Bm7 E7 Am7 D7 Am7 D7 GM7 Am7 D7 GM7 Cm7 F7 GM7 Cm7 F7 Bm7 E7 Am7 D7 Am7 D7 GM7 C#m7b5 F#7 BM7 G#7 C#m7 F#7 BM7 G#7 C#m7 Em7 A7 DM7 B7 Em7 A7 DM7 Em7 A7 Am7 D7 GM7 Cm7 F7 GM7 Cm7 F7 Bm7 E7 Am7 D7 Am7 D7 G Am7 D D7 G";

// LOVE IS THE SWEETEST THING P.205
// D Major
SongArray[56] = "DM : D Bm7 Em7 A7 D Bm7 Em7 Em7 A7 Am7 D7 Gm7 C7 Bm7 E7 Em7 A7 A7 D Bm7 F#m7 B7 Em7 A7 DM7 C#m7b5 F#7 Bm7 E7 Em7 A7 D Bm7 Em7 A7 D Bm7 Em7 A7 Am7 D7 GM7 C7 Bm7 E7 A7 D";

// LEMON DROP P.215
// Bb Major
SongArray[57] = "BbM : Bb E7 Eb Edim7 Dm7 G7b9 Cm7 F7 Bb E7 Eb Dbdim Cm F7 Bb B7 Bb E7 Eb Edim7 Dm7 G7b9 Cm7 F7 Bb E7 Eb Edim7 Dm7 G7b9 Cm7 F7 Bb E7 Eb Dbdim Cm7 F7 Bb Bb7 Am7 D7 A G7 Gm7 C7 G F7 Bb E7 Eb Edim7 Dm7 G7b9 Cm7 F7 Bb E7 Eb Dbdim Cm7 F7 Bb";

// DAYS OF WINE AND ROSES P.226
// F Major
SongArray[58] = "FM : FM7 Eb7 D7b5 D9 Gm7 Bbm Eb7 Am7 Dm7 Gm7 C7 FM7 Eb7 D7b5 D9 Gm7 Bbm Eb7 Am7 Dm7 Bm7b5 Bb7 Am7 Dm7 Gm7 Gm7 C7 F6 Gm7 C7 F Gm7 C FM9";

// JUNE BUG P.194 
// Bb Major
SongArray[59] = "BbM : Bb7 Eb7 Bb7 F7 Eb7 Bb7 Eb7 Bb7 F7 Eb7 Bb7";

// JUNE IN JANUARY P.195
// Eb Major
SongArray[60] = "EbM : EbM7 C7 Fm7 Bb7 Gm7 C7 Fm7 Bb7#5 EbM7 C7 Fm7 Bb7 Bb7b9 EbM7 Dm7b5 G7 Ab7 Cm7 Dm7b5 G7 Cm7 F7 Fm7 Bb7 EbM7 C7 Fm7 Bb7 Fm7 Bb9 Eb6";

// LADY OF THE EVENING P.196
// F Major
SongArray[61] = "FM : F6 F F G9 G7b9 C7 Gm7 C7 FM7 Am7b5 D7 G9 G7 Dm7 G7 Gm7 C7 FM7 F7 Bbm6 Am7 Gm7 FM7 F7 Bbm6 FM7 Eb7 Am7b5 D7 Gm7 Bbm6 F C Dm7 G7 C7b9 F Abdim7 Gm7 C7 F";

// ME AND MY BABY P.222
// Ab Major
SongArray[62] = "AbM : Ab7 Db7 Ddim7 Ab Eb Bb7 Ebm7 Ab7b5 D7#9 Db7#9 C7#9 B7#9 Db7 Ddim Ab7 Ab7 Eb F9#5 Bb7 Eb11 Ab7 Eb F9#5 Bb7 Eb11";

// MOMENT'S NOTICE P.231
// Eb Major
SongArray[63] = "EbM : Em7 A7 Fm7 Bb7 EbM7 Abm7 Db7 Dm7 G7 Ebm7 Ab7 DbM7#11 Dm7 G7 Cm7 B7b9 Bbm7 Eb7 AbM7 Abm7 Db7 Gm7 C7b13 Abm7 Db7 Gb6 Fm7 Bb7 Gm7 C7#9 Fm7 Bb7 Eb6 Fm7 Gm7 Fm7 Eb6 Fm7 Gm7 Fm7 Eb";

// MY SILENT LOVE P.252
// Eb Major
SongArray[64] = "EbM : EbM7 Bb7#5 EbM7 Gm7b5 C7b9 Fm7 Db7 Bb7 Gm7 C7 Fm7 Bb7 EbM7 Bb7#5 EbM7 Gm7b5 C7b9 Fm7 Db7 Bb7 EbM7 Bbm7 Eb7 AbM7 Db7 EbM7 Bbm7 Eb7 AbM7 Db7 F7 Fm7 Bb7 EbM7 Bb7#5 EbM7 Gm7b5 C7b9 Fm7 Db7 Bb7 EbM7";

// NATURE BOY P.253
// E minor
SongArray[65] = "Em : Em Am6 Em Am6 Em Am6 Em Am6 Em F#7b9 B7b9 Em Am6 Em Am6 Em Am6 Em Am6 Em F#7b9 B7b9 Em F#m7b5 B7 F#7b9 B7b9 Em";

// NEVER LET ME GO P.255
// G minor
SongArray[66] = "Gm : Gm7 C7b9 Fm7 Bb7b9 EbM7 D7 DbM7 C7 CM7 F7b9 Bbm#7 AbM7 G7#5 CM7 Dm7 Em7 A7#5 D7b9 Gm7 C7b9 Fm7 Bb7b9 EbM7 D7 Gm7 C9 Cm7 F7b9 Bb6 A7#5 D7b9 Bb6";

// SEEMS LIKE OLD TIMES P.302
// F Major
SongArray[67] = "FM : D7 G7 Gm7 C7 F6 D7 G7 C7 D7#5 D7 G9 C7 F Bb7 F";

// SOFT LIGHTS AND SWEET MUSIC P.316
// F Major
SongArray[68] = "FM : F7 D7 G7 Bm7b5 Bb7 F F#dim C7 Gm7 D Ebdim C7 E C7 C7#5 F6 Gm7 G#dim F A F7 D7 G7 Bm7b5 Bb7 F Dm7 G7 C7 F Abdim Gm7 C7 F";

// WEST COAST BLUES P.363
// Bb Major
SongArray[69] = "BbM : Bb7 Ab7 Bb7 Bm7 E7 Eb7 Bb7 F7 Eb7 Bb7 BbM7 Abm7 Db7 GbM7 B7 B7 BbM7 Abm7 Db7 GbM7 B7 B7 BbM9#11";

// YOU'RE BLASE P.373
// F MajoR
SongArray[70] = "FM : FM7 Abdim7 Gm7 F#dim7 Gm7 F#dim7 Gm7 C7 C9 Am7 D7b9 Gm7 C7 FM7 Bb7 FM7 Cm7 F7 Bbm7 Eb7 Cm7 F7 Bbm7 Eb7 Am7 Abdim7 Gm7 C7 FM7 Abdim7 Gm7 F#dim7 Gm7 F#dim7 Gm7 C7 FM7";

// YOU COULDN'T BE CUTER P.378
// G Major
SongArray[71] = "GM : GM7 G6 G Em7 Am7 D7 Bm7 Em7 Am7 D7 GM7 G6 G Em7 Am7 D7 G6 G7 C6 Cm6 G GM7 G7 C6 Cm6 Cm7 Cm6 G Eb9 Am7 D7 GM7 G6 G Em7 Am7 D7 Bm7 Em7 CM7 Am7 D Bm7 Am7 D7 G6 Am7 D9 G6";

// YOU'VE MADE ME SO VERY HAPPY P.380
// G Major
SongArray[72] = "GM : Am7 Gm7 Am7 GM7 Am7 GM7 Am7 Bm7 Am7 Bm7 Am7 Bm7 Am7 D GM7 Bm7 E F C G F Em7 A Am7 D GM7 Bm7 E";

// BLUE PRELUDE P.53
// D minor 
SongArray[73] = "GM : Dm E7 A7 A7b9 Dm Bb7 A7 Dm6 Em7b5 A7 Dm E7 A7 A7b9 Dm Bb7 A7 Dm6 A7 Dm6 A7 Dm6 Fm Dm6 Em7b5 A7#5 Dm6 Bb7 A7 A7#5 Bb7 Dm7 Dm6 Bb7 A7 A7#5 Dm E7 A7 A7b9 Dm Bb7 A7 Dm Gm6 Dm6";

// BLUE TRAIN P.52
// Eb Major
SongArray[74] = "GM : Eb7#9 Ab7#11 Eb7#9 Bb7#9 Eb7#9 Eb7#9";

// CAKE WALKING BABIES FROM HOME P.71
// G Major
SongArray[75] = "GM : D7 G E7 A9 A7 C D D7 Gm D7 D9 Em G7 C6 Cm G G#dim7 D7 A9 D7 D7 G G#dim7 D9 D7 D7 G#dim7 D7 G#dim7 A9 D7 G D9 G D9 G";

// CHAMELEON P.77
// Bb Major
SongArray[76] = "BbM : BbM7 Eb7 Bbm7 Eb7 BbM7 Eb7 Bbm7 Eb7 BbM7 Eb7 Bbm7 Eb7 BbM7 Eb7 Bbm7 Eb7 BbM7 Eb7 Bbm7 Eb7 BbM7 Eb7 Bbm7 Eb7";

// CHANGING PARTNERS P.77
// F Major
SongArray[77] = "FM : F G#dim7 C7 Gm7 Bbm6 F A G#dim7 Gm11 C7 G#dim7 Gm7 C7 F6 F#dim7 Gm7 C9 F G#dim7 C7 Gm7 Bbm6 F A G#dim7 Gm11 C7 G#dim7 Gm7 C7 F6 Bbm7 Eb9 Ab Ab6 AbM7 Ab6 Ab Ab6 Gm7 BbM7 C C7b9 F G#dim7 C7 Gm7 Bbm6 F A Db Ab C7 G Cm6 Eb D7b9 Gm7 Gm7 C C7b9 F6 Gm7 C7b9 F6";

// COPENHAGEN P.89
// Bb Major
SongArray[78] = "BbM : EbM7 Ebm6 Bb Db G7 C9 C7 F9 Bb Bb7 Bb7#5 EbM7 Ebm6 Bb Db G7 C9 C7 F9 Bb F9 Bb Bbm Bb Bb Cm Ddim Bb Gb Bb F7 Bb F9 Bb F7#5 Bb F9 Bb Bb7";

// CRAZYOLOGY P.89
// Bb Major
SongArray[79] = "BbM : Bb Cm7 F7 Bb Bbdim Cm7 F7 Abm7 Db7 Gb Cm7 F7 Bb D7 Dm7 G7 C7 Cm7 F7 Bb Cm7 F7 Bb Bbdim Cm7 F7 Abm7 Db7 Gb Cm7 F7 Bb";

// DARLING, JE VOUS AIME BEAUCOUP P.91
// F Major
SongArray[80] = "FM : F Cm7 F7 Bb Bbm6 F Dm7 G7 C7 F C9 F D7 Gm7 C7 F Cm7 F7 Bb Bbm6 F Dm7 G7 C7 F C7 F Bb7 F F7 Bb Bb6 Bbm7 FM7 A7 Dm Dm#7 Dm7 G9 C7 F Cm7 F7 Bb Bbm F G7 C7 F C9 F C7#5 F C9 F C9 F";

// DOWN UNDER P.106 
// C minor
SongArray[81] = "Cm : Cm7 Ab7 G7Cm7 Ab7 G7 Cm7 Ab7 G7Cm7 Ab7 G7 Cm7 Ab7 G7Cm7 Ab7 G7 Cm7 Ab7 G7Cm7 Ab7 G7 Cm7 Fm7 Cm7 Fm7 G7 Cm7 Ab7 G7Cm7 Ab7 G7 Cm7 Ab7 G7Cm7 Ab7 G7 Cm7";

// DROP ME OFF IN HARLEM P.111
// Bb Major
SongArray[82] = "BbM : BbM7 Bdim7 Cm7 F7 Dm7b5 Db7 Cm7 F7 Am7b5 D7 Gm7 Fm7 Bb7 EbM7 Cm7 F7 BbM7 BbM7 Bdim7 Cm7 F7 Dm7b5 Db7 Cm7 F7 Am7b5 D7 Gm7 Fm7 Bb7 EbM7 Cm7 F7 BbM7 Fm7 Bb7#5 EbM7 Bb7#5 Eb7 Ab7 Db7 C7 F7 BbM7 Bdim7 Cm7 F7 Dm7b5 Db7 Cm7 F7 Am7b5 D7 Gm7 Fm7 Bb7 EbM7 Cm7 F7 BbM7";

// FOUR ON SIX P.124
// G minor
SongArray[83] = "Gm : Gm7 C7 Gm7 C7 Gm7 C7 Gm7 Cm9 F7#11 Bbm9 Eb7#11 Am9 D7#11 Ebm9 Ab9#11 Gm7 C7 Gm7 C7 Gm7 C7 Gm7 BbM7 Gm7 G#m7 Am7 D7#9 Gm";

// A HUNDRED YEARS FROM TODAY P.153
// Eb Major
SongArray[84] = "EbM : EbM7 Cm7 Fm7 Bb7 EbM7 Cm7 F7 Bb7#5 EbM7 Cm7 Fm7 Abm6 F9 Bb7 Bb7#5 F9 Bb7 Eb6 Bbm7 Eb7 Bbm7 Eb7 Ab6 Cm7 F7 Cm7 F7 Bb7 Ebm7 Bb Bb6 Bb7 Gm7b5 C7 F7 Bb7 Eb";

// I MEAN YOU P.163
// F Major
SongArray[85] = "FM : F6 Db7 D7 Gm7 C7 F6 Eb7 F6 Db7 C7 F6 Db7 D7 Gm7 C7 F6 Eb7 Eb9";

// JITTERBUG WALTZ P.191
// Eb Major 
SongArray[86] = "EbM : Eb6 Ab9 Bb7#5 Eb6 Gm7 C7 Gm7 C7 F9 Abm6 Db9#11 F7 Bb7 Gm7 C7 Fm7 Bb7 Fm7 Bb13 Eb6 Ab6 Eb6";

// JUST A GIGOLO
// G Major
SongArray[87] = "GM : G GM7 G6 Bbdim7 Am7 D7 Am7 D7 D7#5 GM7 G7 F7 E7 Am7 F7 GM7 A7 Am7 D7 G6 G6";

// LOVE LETTERS P.204
// G Major
SongArray[88] = "GM : GM7 Em7 Am7 Am7b5 D7 GM7 Em7 F#7 Bm7 E7 Am7 E7 Am7 D7b9 Gm7 Em7 Am7 Am7b5 D7 GM7 G9 C F7 G Bbdim7 Am7 D7b9 G";

// LAST RESORT
// F Major
SongArray[89] = "FM : Bb Bdim F C D7#9 G7b9 C7 F F7b5 B Bb Bdim F C D7 G7 C7 Em7b5 A7 DmD7 G7 AbM7 Db7 B7 Bb Bdim F C D7#9 G7b9 C7 F F7b5 B Bb Bdim F C D7 G7 C7 F";

// LITTLE WALTZ P.218
// F minor
SongArray[90] = "Fm : Fm C7 E Ebm7 DbM7 G7 C7 Fm C7 Fm C7 E Ebm7 DbM7 G7 C7 Fm C7 Fm Fm C7 E Fm Eb Dm7 G7 C7 F7 Bbm7 Eb7 Ab G7 C7 F C7 E Ebm7 DbM7 G7 C7 F";

// MOON OVER MIAMI P.244
// G Major
SongArray[91] = "GM : D7 G GM7 G#dim7 Am7 D7 G7 C Cm6 G G F#7 Bm Em Bm Bb9 Bm F#7 Bm Em Bm Gm6 D7 Eb7 Am7 D7 G GM7 G#dim7 Am7 D7 G7 C Cm6 G";

// MY HEART STILL STOOD P.248
// F Major
SongArray[92] = "FM : FM7 Abdim7 Gm7 C7 Fm7 Cm7 F7 BbM7 Eb7 Am7 D7 Gm7 C7 Am7 D7 Gm7 Fm7 Bb7 FM7 Fm7 Bb7 Db7 C7 Dm7b5 G7 Db7 C7 FM7 Abdim7 Gm7 C7 FM7 Cm7 F7 BbM7 Eb7 Am7 Abdim7 Gm7 C7 FM7";

// MY IDEAL P.252
// Eb Major
SongArray[93] = "EbM : EbM7 C7 Fm7 Db7 C7 F7 Fm7 Bb7 Dm7b5 G7#5 Cm7 F7 F#m7 B7 Fm7 Bb7 EbM7 C7 Fm7 Db7 C7 F7 Fm7 Abm7 Db7 EbM7 D7 Db7 C7 Fm7 Bb7 EbM7";

// NEARLY P.255
// E minor
SongArray[94] = "Em : G7 C7 EbM7 Cb9 A Bm9 Am9 B G7 Ebm F G7 C7 EbM7 Cb9 A Bm9 Am9 B G7 Ebm F G7 C7 EbM7 Cb9 A Bm9 Am9 B G7 Ebm F";

// P.S. I LOVE YOU P.285
// Eb Major
SongArray[95] = "EbM : Eb Gm7 C7 Fm7 Bb7 Gm7b5 C7 F7 Bb7 Eb6 F7 Fm7 Bb7 Eb Gm7 C7 Fm7 Bb7 Gm7b5 C7 F7 Bb7 Eb6 Db7 EbM7 Eb6 Eb7 Bbm7 Eb7 Bbm7 Eb7 AbM7 F7 Cm7 F7 Cm7 Bb7 C F7 Bb7 Eb Gm7 C7 Fm7 Bb7 Gm7 C7 F7 Bb7 Eb6 Db7 EbM7 Eb Fm7 Bb7 EbM7 Eb6";

// THE RAINBOW CONNECTION P.286
// G Major 
SongArray[96] = "GM : GM7 Em7 Am7 C D D Gm7 Em7 C C D D7 Gm7 Em7 Am7 C D GM7 Em7 CM7 Bm7 D E E9 Am7 D C D Bm7 E7 Am7 D7 G C G G C G G D F# Em7 G D C G B C D D7 G D F# Em7 G D CM7 D7 D7 G";

// REMIND ME P.289
// Eb Major
SongArray[97] = "EbM : EbM7 Bb7 EbM7 D7 G GM7 G7 CM7 Eb Bb7 EbM7 Eb Bb7 Eb6 Bb9 Ab Eb Fm7 Bb7 Bb7 EbM7 Fm7 Bb7 Bb7 EbM7 D7 Gm C7 Fm E7A7 Ab7 Dbm B/B7 Eb Ab7 Eb";

// ST.LOUIS BLUES P.297
// G Major
SongArray[98] = "GM : G7 C7 G7 C7 G7 D7 G7 C7 G7 C7 G7 D7 G7 Gm Cm D7 G D7 Gm7 Cm7 C#dim7 D7 Gm7 A7 D7 G C G C G C G7 C6 C7 G C G C G C G D D7 G G";

// TILL THE CLOUDS ROLL BY P.351
// Eb Major
SongArray[99] = "EbM : EbM7 C7b9 Fm7 Bb7 Eb6 Fm11 Bb7 EbM7 Bbm7 Eb7 AbM7 Fm7 Bb9 Eb Cm7 Fm7 F9 Fm7 Bb7 EbM7 C7b9 Fm7 Bb7 Eb6 Fm11 Bb7 EbM7 BbM7 Eb7 AbM7 Fm7 Bb9 Eb Db13 C7b9 F7 Bb7 Eb6 Fm7 Bb7 Eb";

// TIME WAS P.350
// G Major
SongArray[100] = "GM : G Bm Am7 D7 GM7 G7 CM7 Cm6 G B Gdim Bb Am7 D7 G6 E7 Am7 D7 G Bm Am7 D7 GM7 G7 CM7 Cm6 G B Gdim Bb Am7 D7 D7b9 G6 CM7 G6 F#7#9 B Bdim C#m7 F#7 B6 Cdim C#m7 F#7 D Cm6 Eb Em11 A7 Am7 D7 D7b9 G Bm Am7 D7 GM7 G7 CM7 Cm6 G B Gdim Bb Am7 D7 D7b9 G6 Em7 A7 D9 G6";

// TOPSY P.353
// Eb minor
SongArray[101] = "Ebm : Ebm Cb7 Bb7 Ebm Cb7 Bb7 Ebm Cb7 Bb7 Ebm Cb7 Bb7 Abm Fb7 Eb7 Abm Ebm Cb7 Bb7 Ebm Eb7 Ab7 Db7 Gb7 Cb7 Bb7 Ebm Cb7 Bb7 Ebm Cb7 Bb7 Ebm Cb7 Bb7 Ebm";
   }    
}


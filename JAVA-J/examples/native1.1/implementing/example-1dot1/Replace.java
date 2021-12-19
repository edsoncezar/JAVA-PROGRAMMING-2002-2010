class Replace {

    public static void Usage() {
        System.out.println("\nUsage:  java Replace char1 char2 inFile outFile");
    }

    public static void main(String[] args) {
        InputFile         in = null;
       OutputFile        out = null;
             char     former = 'A';
             char     latter = 'A';
           byte[]        buf;

        try {
               former = args[0].charAt(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            Usage();
            System.err.println("you must supply the character to replace\n");
            System.exit(-1);
        }

        try {
               latter = args[1].charAt(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            Usage();
            System.err.println("you must supply the new character\n");
            System.exit(-1);
        }

        try {
            in = new InputFile(args[2]);
        } catch (ArrayIndexOutOfBoundsException e) {
            Usage();
            System.err.println("you must supply the input replacement file\n");
            System.exit(-1);
        }

        try {
            out = new OutputFile(args[3]);
        } catch (ArrayIndexOutOfBoundsException e) {
            Usage();
            System.err.println("you must supply the output replacement file\n");
            System.exit(-1);
        }

        System.out.println("Replacing "+args[0]+" with "+args[1]+" from "+
                           args[2]+" to "+args[3]);

        if (in.open() == false) {
            System.out.println("Unable to open input file "+in.getFileName());
        }

        if (out.open() == false) {
            System.out.println("Unable to open output file "+out.getFileName());
        }

        buf = new byte[1];
        while (in.read(buf, 1) == 1) {
            if (buf[0] == former)
                buf[0] = (byte)latter;
            if (out.write(buf, 1) != 1) {
                System.out.println("Error writing to "+out.getFileName());
            }
        }
         in.close();
         out.close();
    }
}

import java.io.*;

public class CheckedIODemo {
    public static void main(String[] args) throws IOException {

       Adler32 inChecker = new Adler32();
       Adler32 outChecker = new Adler32();
       CheckedInputStream in = null;
       CheckedOutputStream out = null;

       try {
           in = new CheckedInputStream(
			   new FileInputStream("farrago.txt"),
			   inChecker);
           out = new CheckedOutputStream(
			    new FileOutputStream("outagain.txt"),
			    outChecker);
       } catch (FileNotFoundException e) {
           System.err.println("CheckedIODemo: " + e);
           System.exit(-1);
       } catch (IOException e) {
           System.err.println("CheckedIODemo: " + e);
           System.exit(-1);
       }

       int c;

       while ((c = in.read()) != -1)
          out.write(c);

       System.out.println("Input stream check sum: " +
			  inChecker.getValue());
       System.out.println("Output stream check sum: " +
			  outChecker.getValue());

       in.close();
       out.close();
    }
}

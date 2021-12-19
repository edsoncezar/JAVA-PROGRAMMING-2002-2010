import java.io.*;

public class CounterDemo {
    public static void main(String[] args) throws IOException {
        CountReader cr = new CountReader(new FileReader("farrago.txt"), 'e');
        CountWriter cw = new CountWriter(new FileWriter("outagain.txt"), 'e');
        int c = 0;
        while ((c = cr.read()) != -1) {
            cw.write(c);
        }
        System.out.println(cr.getCount());   
        System.out.println(cw.getCount());   
	cr.close();
	cw.close();
    }
}

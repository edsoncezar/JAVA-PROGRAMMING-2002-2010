import java.util.*;
import java.io.*;

public class Perm {
    public static void main(String[] args) {
        int minGroupSize = Integer.parseInt(args[1]);
 
        // Read words from file and put into simulated multimap
        Map m = new HashMap();
        try {
            BufferedReader in =
                   new BufferedReader(new FileReader(args[0]));
            String word;
            while((word = in.readLine()) != null) {
                String alpha = alphabetize(word);
                List l = (List) m.get(alpha);
                if (l==null)
                    m.put(alpha, l=new ArrayList());
                l.add(word);
            }
        } catch(IOException e) {
            System.err.println(e);
            System.exit(1);
        }

        // Print all permutation groups above size threshold
        for (Iterator i = m.values().iterator(); i.hasNext(); ) {
            List l = (List) i.next();
            if (l.size() >= minGroupSize)
                System.out.println(l.size() + ": " + l);
        }
    }

    private static String alphabetize(String s) {
        int count[] = new int[256];
        int len = s.length();
        for (int i=0; i<len; i++)
            count[s.charAt(i)]++;
        StringBuffer result = new StringBuffer(len);
        for (char c='a'; c<='z'; c++)
            for (int i=0; i<count[c]; i++)
                result.append(c);
        return result.toString();
    }
}

import java.util.*;
import java.io.*;

public class Perm2 {
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

        // Make a List of all permutation groups above size threshold
        List winners = new ArrayList();
        for (Iterator i = m.values().iterator(); i.hasNext(); ) {
            List l = (List) i.next();
            if (l.size() >= minGroupSize)
                winners.add(l);
	}

        // Sort permutation groups according to size
        Collections.sort(winners, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((List)o2).size() - ((List)o1).size();
            }
        });

        // Print permutation groups
        for (Iterator i=winners.iterator(); i.hasNext(); ) {
            List l = (List) i.next();
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

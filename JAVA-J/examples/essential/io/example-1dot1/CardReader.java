import java.io.*;

public class CardReader {
    public static void main(String[] args) {
	Card3 card = null;

	try {
	    FileInputStream in = new FileInputStream("card.out");
	    ObjectInputStream ois = new ObjectInputStream(in);
	    card = (Card3)(ois.readObject());
	} catch (Exception e) {
	    System.out.println("Problem serializing: " + e);
	}

	System.out.println("Card read is: " + card);

    }
}

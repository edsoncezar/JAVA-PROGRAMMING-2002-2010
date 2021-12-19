package database;

import java.sql.*;
import java.util.*;

public class BookDB {

    /* The database hashtable and dbByTitle vector are filled with
     * 10 objects of type BookDetails.  The hashtable is pretending to
     * be a database; the vector is pretending to be a view of the
     * database.
     */
    private HashMap database;
		private ArrayList al;
    private static BookDB onlyInstance = null;


    public BookDB () {
        BookDetails book;
        database = new HashMap();

        book = new BookDetails("201", "Duke", "",
                     "My Early Years: Growing up on *7",
                     (float)10.75, 1995, "What a cool book.");
        database.put(new Integer(201), book);

        book = new BookDetails("202", "Jeeves", "",
                     "Web Servers for Fun and Profit", (float)10.75,
                     1996, "What a cool book.");
        database.put(new Integer(202), book);

        book = new BookDetails("203", "Masterson", "Webster",
                     "Servlets: A Web Developers Dream Come True",
                     (float)17.75, 1995, "What a cool book.");
        database.put(new Integer(203), book);

        book = new BookDetails("204", "RealGood", "Coad",
                     "Moving from C++ to the Java(tm) Programming Language",
                     (float)10.75, 1996, "What a cool book.");
        database.put(new Integer(204), book);

        book = new BookDetails("205", "Novation", "Kevin",
                     "From Oak to Java: The Revolution of a Language",
                     (float)10.75, 1998, "What a cool book.");
        database.put(new Integer(205), book);

        book = new BookDetails("206", "Gosling", "James",
                     "Oak Intermediate Bytecodes", (float)10.75,
                     1998, "What a cool book.");
        database.put(new Integer(206), book);

        book = new BookDetails("207", "Thrilled", "Ben",
                     "The Green Project: Programming for Consumer Devices",
                     (float)10.75, 1998, "What a cool book");
        database.put(new Integer(207), book);

        book = new BookDetails("208", "Duke", "",
                     "100% Pure: Making Cross Platform Deployment a Reality",
                     (float)10.75, 1998, "What a cool book.");
        database.put(new Integer(208), book);

        book = new BookDetails("209", "Tru", "Itzal",
                     "Duke: A Biography of the Java Evangelist",
                     (float)10.75, 1998, "What a cool book.");
        database.put(new Integer(209), book);

        book = new BookDetails("210", "Sidence", "Cohen",
                     "I Think Not: Duke's Likeness to the Federation Insignia",
                     (float)10.75, 1998, "What a cool book.");
        database.put(new Integer(210), book);
			  al = new ArrayList(database.values());
				Collections.sort(al);
    }

    public static BookDB instance () {
      if (onlyInstance == null)
        onlyInstance = new BookDB();
      return onlyInstance;
   }


    public BookDetails getBookDetails(String bookId) {
        Integer key = new Integer(bookId);
        return (BookDetails)database.get(key);
    }

    public Collection getBooks() {
				return al;
    }

    public int getNumberOfBooks() {
        return database.size();
    }
}
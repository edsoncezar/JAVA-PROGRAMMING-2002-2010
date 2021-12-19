/*
 * Copyright (c) 1998 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Sun
 * Microsystems, Inc. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Sun.
 *
 * SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 * =
 * CopyrightVersion 1.0
 */

package database;

import java.util.*;

/**
 * Sets up the database with the appropriate things for our bookstore.
 */

class BookstoreDB {

    /* The database hashtable and dbByTitle vector are filled with
     * 10 objects of type BookDetails.  The hashtable is pretending to
     * be a database; the vector is pretending to be a view of the
     * database.
     */
    private Hashtable database;
    private BookDetails[] dbByTitle;
    private final int numberOfBooks = 10;

    BookstoreDB () {
        BookDetails book;
        database = new Hashtable();
        dbByTitle = new BookDetails[numberOfBooks];

        book = new BookDetails("201", "Duke", "",
                     "My Early Years: Growing up on *7",
                     (float)10.75, 1995, "What a cool book");
        database.put(new Integer(201), book);
        dbByTitle[5] = book;

        book = new BookDetails("202", "Jeeves", "",
                     "Web Servers for Fun and Profit", (float)10.75,
                     1996, "What a cool book");
        database.put(new Integer(202), book);
        dbByTitle[8] = book;

        book = new BookDetails("203", "Masterson", "Webster",
                     "Servlets: A Web Developers Dream Come True",
                     (float)17.75, 1995, "What a cool book");
        database.put(new Integer(203), book);
        dbByTitle[7] = book;

        book = new BookDetails("204", "RealGood", "Coad",
                     "Moving from C++ to the Java(tm) Programming Language",
                     (float)10.75, 1996, "What a cool book");
        database.put(new Integer(204), book);
        dbByTitle[4] = book;

        book = new BookDetails("205", "Novation", "Kevin",
                     "From Oak to Java: The Revolution of a Language",
                     (float)10.75, 1998, "What a cool book");
        database.put(new Integer(205), book);
        dbByTitle[1] = book;

        book = new BookDetails("206", "Gosling", "James",
                     "Oak Intermediate Bytecodes", (float)10.75,
                     1998, "What a cool book");
        database.put(new Integer(206), book);
        dbByTitle[6] = book;

        book = new BookDetails("207", "Thrilled", "Ben",
                     "The Green Project: Programming for Consumer Devices",
                     (float)10.75, 1998, "What a cool book");
        database.put(new Integer(207), book);
        dbByTitle[2] = book;

        book = new BookDetails("208", "Duke", "",
                     "100% Pure: Making Cross Platform Deployment a Reality",
                     (float)10.75, 1998, "What a cool book");
        database.put(new Integer(208), book);
        dbByTitle[9] = book;

        book = new BookDetails("209", "Tru", "Itzal",
                     "Duke: A Biography of the Java Evangelist",
                     (float)10.75, 1998, "What a cool book");
        database.put(new Integer(209), book);
        dbByTitle[0] = book;

        book = new BookDetails("210", "Sidence", "Cohen",
                     "I Think Not: Duke's Likeness to the Federation Insignia",
                     (float)10.75, 1998, "What a cool book");
        database.put(new Integer(210), book);
        dbByTitle[3] = book;
    }

    BookDetails getBookDetails(String bookId) {
        Integer key = new Integer(bookId);

        return (BookDetails)database.get(key);
    }

    BookDetails[] getBooksSortedByTitle() {
        return dbByTitle;
    }

    int getNumberOfBooks() {
        return numberOfBooks;
    }
}




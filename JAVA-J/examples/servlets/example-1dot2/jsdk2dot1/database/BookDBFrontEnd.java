/*
Copyright (c) 1998 Sun Microsystems, Inc. All Rights Reserved.

This software is the confidential and proprietary information of Sun
Microsystems, Inc. ("Confidential Information").  You shall not
disclose such Confidential Information and shall use it only in
accordance with the terms of the license agreement you entered into
with Sun.

SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR ANY DAMAGES
SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
THIS SOFTWARE OR ITS DERIVATIVES.

CopyrightVersion 1.0
*/

package database;

import java.io.*;

/**
 *  Object that gets information from the Duke's Bookstore database.
 */
public class BookDBFrontEnd {

  private BookStoreDB books;
  private static BookDBFrontEnd onlyInstance = null;

  /* Private no-args constructor */
  private BookDBFrontEnd() {
      init();
  }

  /** Static factory method that makes a single instance of this
   *  class. */
  public static BookDBFrontEnd instance () {
    if (onlyInstance == null)
        onlyInstance = new BookDBFrontEnd();

    return onlyInstance;
  }

  /** Initialize the book database, and store it in this. */
  public void init()  {
    // Load the database to prepare for requests
    books = new BookStoreDB();
  }

  /** Set the database to null so that it can be garbage collected. */
  public void destroy() {
    books = null;
  }

  /** Return information about the book associated with the given
   *  book identifier.  */
  public BookDetails getBookDetails(String bookId) {
    return books.getBookDetails(bookId);
  }

  /** Return information about all the books in the bookstore.
   *  Sort the books into alphabetical order using their title
   *  as the key. */
  public BookDetails[] getBooksSortedByTitle() {
    return books.getBooksSortedByTitle();
  }

  /** Return the number of books in the bookstore database. */
  public int getNumberOfBooks() {
    return books.getNumberOfBooks();
  }
}

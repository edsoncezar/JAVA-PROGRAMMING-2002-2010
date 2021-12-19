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
 *
 * CopyrightVersion 1.0
 */

package database;

public class BookDetails {
    private String bookId = null;
    private String title = null;
    private String firstName = null;
    private String surname = null;
    private float price = 0.0F;
    private int year = 0;
    private String description = null;
    
    public BookDetails(String bookId, String surname, String firstName, 
                       String title, float price, int year,
                       String description) {
        this.bookId = bookId;
        this.title = title;
        this.firstName =  firstName;
        this.surname = surname;
        this.price = price;
        this.year = year;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }
    
    public float getPrice() {
        return price;
    }

    public int getYear() {
        return year;
    }

    public String getDescription() {
        return description;
    }

    public String getBookId() {
        return this.bookId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getSurname() {
        return this.surname;
    }
}


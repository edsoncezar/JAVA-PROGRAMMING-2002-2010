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

package cart;

import java.util.*;
import database.BookDetails;

public class ShoppingCart {
    Hashtable items = null;
    int numberOfItems = 0;

    public ShoppingCart() {
        items = new Hashtable();
    }

    public void add(String bookId, BookDetails book) {
        if(items.containsKey(bookId)) {
            ShoppingCartItem scitem = (ShoppingCartItem) items.get(bookId);
            scitem.incrementQuantity();
        } else {
            ShoppingCartItem newItem = new ShoppingCartItem(book);
            items.put(bookId, newItem);
        }

        numberOfItems++;
    }

    public void remove(String bookId) {
        if(items.containsKey(bookId)) {
            ShoppingCartItem scitem = (ShoppingCartItem) items.get(bookId);
            scitem.decrementQuantity();

            if(scitem.getQuantity() <= 0)
                items.remove(bookId);

            numberOfItems--;
        }
    }

    public Enumeration getItems() {
        return items.elements();
    }

    protected void finalize() throws Throwable {
        items.clear();
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void clear() {
        items.clear();
        numberOfItems = 0;
    }
}


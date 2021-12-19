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

import java.io.*;
import java.util.*;
import java.text.*;
import database.BookDetails;


public class Cashier {

    private static final double SalesTaxRate = 0.0825;

    ShoppingCart cart;

    public Cashier(ShoppingCart cart) {
        this.cart = cart;
    }

    public double getAmount() {
        double amount = 0.0;

        for(Enumeration e = cart.getItems(); e.hasMoreElements(); ) {
            ShoppingCartItem item = (ShoppingCartItem) e.nextElement();
            BookDetails bookDetails = (BookDetails) item.getItem();

            amount += item.getQuantity() * bookDetails.getPrice();
        }
        return roundOff(amount);
    }

    public double getTax() {
        return roundOff(getAmount() * SalesTaxRate);
    }

    public double getTotal() {
        return roundOff(getAmount() + getTax());
    }

    private double roundOff(double x) {
        long val = Math.round(x*100); // cents
        return val/100.0;
    }

    public static String format(double d) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return nf.format(d);
    }
}


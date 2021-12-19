package com.oreilly.struts.storefront.businessobjects;

/**
 * Contains static utility methods.
 */
public class BOUtils {

 public static Double getRoundedDouble( double unroundedValue ){
    // Get the integer portion of the value
    double intPortion = Math.floor( unroundedValue );

    // Get the unrounded decimal portion
    double unroundedDecimalPortion = ( unroundedValue - intPortion );

    /* ALERT - This next code interferes with I18N. We eventually need   */
    /*         to change this not assume only round to 2 decimal places. */

    /* Multiply the decimal places by 100, which shitfs the decimal point
    /* two places to the left. Then round it so that we get a round to
    /* two decimal places. For example, if we started with 17.655 and stripped
    /* off the int portion, which left .655. After we shift, we are left with
    /* 65.5. Then a round will gives us 66. We can then put it all back
    /* together */
    double roundedDecimalPortion = Math.round( unroundedDecimalPortion * 100 );

    // Shift the decimal point back two places to the right
    roundedDecimalPortion = roundedDecimalPortion / 100;

    // Add the int portion and decimal portions back together
    double total = intPortion + roundedDecimalPortion;

    return new Double( total );
  }
}
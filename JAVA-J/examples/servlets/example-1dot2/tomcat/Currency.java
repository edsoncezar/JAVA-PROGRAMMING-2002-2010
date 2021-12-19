import java.text.NumberFormat;
import java.util.*;

public class Currency {
    public static String format(double d, Locale locale) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        return nf.format(d);
    }
}



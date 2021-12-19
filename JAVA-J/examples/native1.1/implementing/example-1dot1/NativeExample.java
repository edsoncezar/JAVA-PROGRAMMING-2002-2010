public class NativeExample {

    private int myValue;

    static {
        Runtime.getRuntime().loadLibrary("example");
    }

    NativeExample(int v) {
        myValue = v;
    }

    native static String quote(int index) throws IllegalArgumentException;

    native int twoTimes();

    native NativeExample doubleUp();

    public static void main(String[] args) {
        String s = quote(2);
        System.out.println("Testing quote(): \"" + s + "\"");

        NativeExample ne = new NativeExample(13);
        System.out.println("Testing twoTimes() " + ne.twoTimes() + 
                           " (should be 26)");

        ne = new NativeExample(24);
        NativeExample ne2 = ne.doubleUp();
        System.out.println("Testing doubleUp() " + ne2 + " (should be 48)");
    }
}

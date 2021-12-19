public class DataTypePrintTest {
    public static void main(String[] args) {

        Thread objectData = new Thread();
        String stringData = "Java Mania";
        char[] charArrayData = { 'a', 'b', 'c' };
        int integerData = 4;
        long longData = Long.MIN_VALUE;
        float floatData = Float.MAX_VALUE;
        double doubleData = Math.PI;
        boolean booleanData = true;

        System.out.println(objectData);
        System.out.println(stringData);
        System.out.println(charArrayData);
        System.out.println(integerData);
        System.out.println(longData);
        System.out.println(floatData);
        System.out.println(doubleData);
        System.out.println(booleanData);
    }
}

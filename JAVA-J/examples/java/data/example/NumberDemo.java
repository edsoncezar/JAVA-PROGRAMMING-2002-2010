public class NumberDemo {
    public static void main(String args[]) {
        Float one = new Float(14.78f - 13.78f);
        Float oneAgain = Float.valueOf("1.0");
        Double doubleOne = new Double(1.0);

        int difference = one.compareTo(oneAgain);

	if (difference == 0) {
            System.out.println("one is equal to oneAgain.");
        } else if (difference < 0) {
            System.out.println("one is less than oneAgain.");
        } else if (difference > 0) {
            System.out.println("one is greater than oneAgain.");
        }

        System.out.println("one is "
                           + ((one.equals(doubleOne)) ? "equal" : "not equal")
                           + " to doubleOne.");

    }
}

/*
 * 1.1 version.
 */

public class Unit {
    String description;
    double multiplier;

    Unit(String description, double multiplier) {
        super();
        this.description = description;
        this.multiplier = multiplier;
    }

    public String toString() {
        String s = "Meters/" + description + " = " + multiplier;
        return s;
    }
}

import java.util.*;

public class Teste {
    public static void main(String[] args) {
        System.out.println("Fatorial de 5: " + fatorial(5) + "\n");
        System.out.println("Fatorial de 10: " + fatorial(10) + "\n");
        System.out.println("Fatorial de 15: " + fatorial(15) + "\n");
        System.out.println("Fatorial de 15: " + fatorial(15) + "\n");
    }

    public static long fatorial(int n) {
        if (n == 0) {
            return 1;
        } else {
            return n * fatorial(n-1);
        }
    }
}

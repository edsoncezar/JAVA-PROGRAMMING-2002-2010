public class Mensageiro {
    public static void entregar(String mensagem) {
        System.out.println(mensagem);
    }

    public static void entregar(String pessoa, String mensagem) {
        System.out.println(pessoa + ", " + mensagem);
    }
}

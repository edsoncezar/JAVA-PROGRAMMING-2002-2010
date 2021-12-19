public class Logico {
    
    /** Creates a new instance of Logico */
    public Logico() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int a=4;
        int b=-16;
        int c=12;
        float d;
        double raiz1, raiz2;
        
        if (a==0 && b==0) {
            System.out.println("Isso nao e uma equacao.");
        }
        else if (a==0 && b!=0){
            if (c==0) {
                System.out.println("A raiz eh 0.");
            }
            else {
                raiz1= -c/b; System.out.println("Existe uma raiz que eh: " + raiz1);
            }
        } // Fecha o if
        else {
            if (b==0 && c==0) {
                System.out.println("A raiz eh 0.");
            }
            else {
                d = (b*b) - (4*a*c);
                if (d==0) { raiz1 = (-b)/(2*a);	System.out.println("Raiz 1 eh igual a Raiz 2 que eh igual a: " + raiz1);
                }
                else if (d<0) {
                    System.out.println("Nao existem raizes reais.");
                }
                else {	raiz1 = ((-b) + Math.sqrt(d)) / (2*a);
                raiz2 = ((-b) - Math.sqrt(d)) / (2*a);
                System.out.println("Raiz 1 eh: " + raiz1 + " e Raiz 2 eh: " + raiz2);
                } // Fecha o else.
            } // Fecha o else.
        } // Fecha o else.
    } // Fecha o Main
} // Fecha a Classe


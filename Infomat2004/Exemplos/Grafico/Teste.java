import javax.swing.*;
public class Teste {
    
    /** Creates a new instance of Teste */
    public Teste() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JOptionPane.showMessageDialog(null,"Isto é um aviso!","Aviso", JOptionPane.PLAIN_MESSAGE);
        String x=JOptionPane.showInputDialog(null,"Entre com um nome: ","Entrada de Dados", JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(null,"O nome informado foi: "+x,"Aviso", JOptionPane.PLAIN_MESSAGE);
    }
    
}

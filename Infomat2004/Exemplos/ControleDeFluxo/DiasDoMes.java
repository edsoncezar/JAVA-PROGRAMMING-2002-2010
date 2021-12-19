public class DiasDoMes {
    
    /** Creates a new instance of DiasDoMes */
    public DiasDoMes() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int mes=Integer.parseInt(args[0]);
        DiasDoMes d = new DiasDoMes();
        d.mostraDias(mes);
    }
    
    public void mostraDias(int m){
        switch(m) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                System.out.println("Há 31 dias neste mês.");
                break;
            case 2:
                System.out.println("Há 28 dias neste mês.");
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                System.out.println("Há 30 dias neste mês.");
                break;
            default:
                System.out.println("Mês Inválido.");
                break;
        }
    }
}

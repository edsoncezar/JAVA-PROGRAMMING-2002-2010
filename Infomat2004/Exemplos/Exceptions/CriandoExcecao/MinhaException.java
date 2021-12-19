public class MinhaException extends Exception {
    
    private Exception execaoReal;
    
    /** Creates a new instance of MinhaException */
    public MinhaException() {
    }
    
    public MinhaException(String erro, Exception e) {
        super(erro);
        this.execaoReal = e;
        System.out.println(getRealException());
    }
    
    public Exception getRealException() {
        return(execaoReal);
    }
}

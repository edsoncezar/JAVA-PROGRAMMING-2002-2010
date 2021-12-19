/* 
 * This is the same in 1.0 as in 1.1.  
 */

class QuoteServer {
    public static void main(String[] args) {
        new QuoteServerThread().start();
    }
}

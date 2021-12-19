
package ui;

/**
*   Extension of BrowseException to handle recoverable errors.
*   This exception can tell the error page where the user can go
*   to resubmit the invalid data.
*/
public class InvalidInputException extends BrowseException {

    private String  retryURL;
    
    /**
     * Constructs an <code>InvalidInputException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public InvalidInputException(String msg, String retryURL) {
        super(msg);
        this.retryURL = retryURL;
    }
    
    public String getRetryURL() {
        return retryURL;
    }
}


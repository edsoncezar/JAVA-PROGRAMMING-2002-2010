package  sample.standard;

/**
 * Standard MBean interface for class Basic.
 * This is the management interface. There are three attributes:
 *
 * 1. TraceOn (boolean) - READ-ONLY
 * 2. DebugOn (boolean) - READ-ONLY
 * 3. NumberOfResets (int) - READ-ONLY
 *
 * and five operations:
 *
 * 1. enableTracing()
 * 2. disableTracing()
 * 3. enableDebugging()
 * 4. disableDebugging()
 * 5. reset() (abstract - must be implemented by subclasses)
*/
public interface BasicMBean {

    // attributes
    public boolean isTraceOn ();
    public boolean isDebugOn ();
    public int getNumberOfResets ();

    // operations
    public void enableTracing ();
    public void disableTracing ();
    public void enableDebugging ();
    public void disableDebugging ();
    public void reset ();
}




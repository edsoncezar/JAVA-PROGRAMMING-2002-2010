package  sample.standard;
/**
 * Base class for the sample application.
 * Implemented as a standard MBean. There are three
 * attributes:
 *
 * 1. TraceOn (boolean)
 * 2. DebugOn (boolean)
 * 3. NumberOfResets (int)
 *
 * and five operations:
 *
 * 1. enableTracing()
 * 2. disableTracing()
 * 3. enableDebugging()
 * 4. disableDebugging()
 * 5. reset() (abstract - must be implemented by subclasses)
 *
 * @author Steve Perry
 */
public abstract class Basic implements BasicMBean {
    
    // ATTRIBUTES
    private boolean _traceOn;
    public boolean isTraceOn () {
        return  _traceOn;
    }

    private boolean _debugOn;
    public boolean isDebugOn () {
        return  _debugOn;
    }

    private int _numberOfResets;
    public int getNumberOfResets () {
        return  _numberOfResets;
    }
    public void setNumberOfResets (int value) {
        _numberOfResets = value;
    }

    // OPERATIONS
    public void enableTracing () {
        _traceOn = true;
    }
    public void disableTracing () {
        _traceOn = false;
    }
    public void enableDebugging () {
        _debugOn = true;
    }
    public void disableDebugging () {
        _debugOn = false;
    }
    public abstract void reset ();
}




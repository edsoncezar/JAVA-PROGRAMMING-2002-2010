package com.waldura.util;

// URLs point to resource files
import java.net.URL;

// we use the log4j package for logging
import org.apache.log4j.*;
import org.apache.log4j.xml.DOMConfigurator;


/**
 * This class implements the Facade pattern for a logging
 * system. It provides a unified interface to a set of interfaces to a
 * subsystem, by defining a higher-level interface that makes the subsystem
 * (in this case, the logging subsystem) easier to use.
 * Requests made to the <code>Logger</code> class (the Facade) are forwarded
 * to the actual logging library. Doing this allows us to decouple the code
 * base at large from a specific logging facility.
 * <p>
 * Use the factory method {@link #newInstance(Class)} to get a new
 * <code>Logger</code> instance.
 * <p>
 * At this time Jakarta's Log4J has been chosen to be the actual logging
 * library. We may want to later switch to the official Sun logging
 * package <code>java.util.logging</code>, still in development at the time
 * of this writing.
 * <p>
 * I declared this class <code>final</code> because I don't see any
 * good reason why anyone would want to extend it. If you need more
 * functionality than this class provides, either use Log4J directly
 * or modify the Facade itself to include that functionality.
 *
 * @author Renaud Waldura, 6/11/01
 */

public final class Logger
{
    /**
     * The name of the default configuration file for this object.
     * It is looked up in this classloader's resources using
     * {@link java.lang.Class#getResource Class.getResource}.
     */
    private static final String DEFAULT_CONFIG_FILE = "/Logger.xml";

    // has the logging sub-system been configured yet?
    private static boolean configured = false;

    // this class's name
    private static final String classname = Logger.class.getName();

    // the log4j object used to log
    private final Category cat;

    /**
     * Factory method to create a <code>Logger</code> object instance,
	 * used for logging.
     */
    public static Logger newInstance( Class c )
    {
        // configure the logging sub-system if needed
        configure();

        return new Logger( Category.getInstance(c) );
    }

    /**
     * Factory method to create a <code>Logger</code> object instance,
	 * used for logging.
     * Always prefer {@link #newInstance(Class)} over this
     * method. Use this method only when no class is available in the context
     * (e.g. inside a JSP).
     */
    public static Logger newInstance( String s )
    {
        // configure the logging sub-system if needed
        configure();

        return new Logger( Category.getInstance(s) );
    }

    /**
     * This initializer configures the logging sub-system with
     * the {@link #DEFAULT_CONFIG_FILE DEFAULT_CONFIG_FILE}.
     */
    public static synchronized void configure()
    {
        if (!configured)
        {
            configure(DEFAULT_CONFIG_FILE);
            configured = true;
        }
    }

    /**
     * Configure the logging sub-system from the given file.
     * The file is looked up in the same directory as the class
     * file.
     *
     * @param configFile the name of the configuration file; if not found
     *                   logging isn't initialized.
     */
    public static void configure( String configFile )
    {
        URL u = Logger.class.getResource(configFile);

        // only configure with a valid file
        if (u != null)
        {
            configure(u);
        }
    }

    /**
     * Configure the logging sub-system from the given URL.
     * It is advised you use
     * {@link #configure(String) configure(String)}
     * instead.
     *
     * @param u a URL pointing to the configuration file
     */
    public static void configure( URL u )
    {
        if (u == null)
            throw new IllegalArgumentException("Invalid URL for logging configuration");

        DOMConfigurator.configure( u );
    }

    /**
     * Private constructor prevents instantiation.
     */
    private Logger( Category cat )
    {
        this.cat = cat;
    }

    /*
     * Logging methods.
     */
    public void debug(Object message) { debug(message, null); }
    public void  info(Object message) {  info(message, null); }
    public void  warn(Object message) {  warn(message, null); }
    public void error(Object message) { error(message, null); }
    public void fatal(Object message) { fatal(message, null); }

    public void debug(Object message, Throwable t) { cat.log(classname, Priority.DEBUG, message, t); }
    public void  info(Object message, Throwable t) { cat.log(classname, Priority.INFO,  message, t); }
    public void  warn(Object message, Throwable t) { cat.log(classname, Priority.WARN,  message, t); }
    public void error(Object message, Throwable t) { cat.log(classname, Priority.ERROR, message, t); }
    public void fatal(Object message, Throwable t) { cat.log(classname, Priority.FATAL, message, t); }
}

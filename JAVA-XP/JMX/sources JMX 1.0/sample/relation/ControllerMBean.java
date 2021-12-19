package  sample.relation;


/**
 * Management interface for the Controller in order
 * to instrument it as a standard MBean.
 */
public interface ControllerMBean extends sample.standard.BasicMBean
{
    public void createWorker (String role, int workFactor);
}




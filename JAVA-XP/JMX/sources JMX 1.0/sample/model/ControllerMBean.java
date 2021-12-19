package  sample.model;

/**
 * Management interface for the Controller in order
 * to instrument it as a standard MBean. There are
 * no attributes and two operations:
 *
 * 1. createWorker() - creates a new worker thread.
 * 2. reset() - resets the state of the controller.
 *    This method is inherited from BasicMBean.
 */
public interface ControllerMBean extends sample.standard.BasicMBean
{
    public void createWorker (String role, int workFactor);
}




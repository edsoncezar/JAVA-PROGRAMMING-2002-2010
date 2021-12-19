package  sample.loading;

import sample.standard.BasicMBean;
/**
 * Management interface for the Controller in order
 * to instrument it as a standard MBean.
 *
 * @author Steve Perry
 */
public interface ControllerMBean extends BasicMBean
{

    /**
     * Creates a new worker thread of the specified type
     * @param role String containing the type of worker
     *        thread to create.
     * @param workFactor int containing the work factor
     *        for the newly created thread.
     */
    public void createWorker (String role, int workFactor);
}




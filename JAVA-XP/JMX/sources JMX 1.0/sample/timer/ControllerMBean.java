package  sample.timer;

import  sample.standard.BasicMBean;


/**
 * Management interface for the Controller in order
 * to instrument it as a standard MBean.
 *
 * @author Steve Perry
 */
public interface ControllerMBean extends BasicMBean
{
    public void createWorker (String role, int workFactor);
}




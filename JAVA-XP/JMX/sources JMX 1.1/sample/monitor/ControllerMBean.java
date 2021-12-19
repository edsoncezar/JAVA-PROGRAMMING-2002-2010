package  sample.monitor;

import  sample.standard.BasicMBean;


/**
 * Management interface for the Controller in order
 * to instrument it as a standard MBean.
 *
 * @author Steve Perry
 */
public interface ControllerMBean extends BasicMBean
{

    public String getOperatorName ();
    public void setOperatorName (String operatorName);

    public void createWorker (String role, int workFactor);
}




package  sample.relation;

import  javax.management.relation.RelationSupportMBean;
import  java.util.List;


/**
 * The MBean interface for the external relation
 * ConsumerSupplierRelation. This demonstrates how to
 * create an external relation that contains additional
 * information beyond the RelationSupportMBean interface.
 *
 * CAUTION: you *must* make sure and extend RelationSupportMBean
 * or the relation service will report "The operation with name
 * getRelationId could not be found" and will leave you scratching
 * your head. The reason for this is that the relation service
 * uses the MBean server to invoke this method (which is implemented
 * on RelationSupport) through its RelationSupportMBean management
 * interface. If your external relation MBean implements its own
 * MBean interface, that MBean interface MUST extend RelationSupportMBean.
 */
public interface ConsumerSupplierRelationMBean extends RelationSupportMBean
{
    String getRelationTypeName ();
    String getRelationServiceObjName ();
    List retrieveRoleList ();
}




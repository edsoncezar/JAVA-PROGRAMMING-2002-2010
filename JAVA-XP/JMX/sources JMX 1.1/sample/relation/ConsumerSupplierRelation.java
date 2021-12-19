package  sample.relation;

import  javax.management.*;
import  javax.management.relation.*;
import  java.util.*;


/**
 * This class demonstrates how to write an external relation.
 * An external relation is a handy way to monitor a relation,
 * since it must be registered with the MBean server. An external
 * relation also allows more control over the implementation of
 * the relation.
 * The class RelationSupport contains the guts of the code to
 * maintain the consistency of the relation. Additional management
 * attributes are contained on the ConsumerSupplierRelationMBean
 * interface as needed.
 */
public class ConsumerSupplierRelation extends RelationSupport implements ConsumerSupplierRelationMBean {

    public static final String NAME = "ConsumerSupplierRelation_External";
    public static final String OBJECT_NAME = "UserDomain:name=" + NAME;
    private String _relationTypeName;

    public String getRelationTypeName () {
        return  _relationTypeName;
    }
    private String _relationServiceObjName;

    public String getRelationServiceObjName () {
        return  _relationServiceObjName;
    }
    private List _roleList;

    public List retrieveRoleList () {
        return  _roleList;
    }

    public String getRelationId () {
        return  NAME;
    }

    public ConsumerSupplierRelation (ObjectName relationServiceObjName, MBeanServer mbeanServer, 
                                     String relationTypeName, RoleList roleList) throws Exception
    {
        super(NAME, relationServiceObjName, mbeanServer, relationTypeName, roleList);
        init(relationServiceObjName, relationTypeName, roleList);
    }
    public ConsumerSupplierRelation (ObjectName relationServiceObjName, String relationTypeName, 
            RoleList roleList) throws Exception
    {
        super(NAME, relationServiceObjName, relationTypeName, roleList);
        init(relationServiceObjName, relationTypeName, roleList);
    }

    private void init(ObjectName relationServiceObjName, String relationTypeName, 
            RoleList roleList) {
        _relationTypeName = relationTypeName;
        _relationServiceObjName = relationServiceObjName.toString();
        _roleList = new ArrayList(roleList.size());
        _roleList.addAll(roleList);
    }
}




package  sample.relation;

import  javax.management.relation.*;


/**
 * This class represents a simple relationship between
 * consumer and supplier MBeans in the sample application.
 * This is an example of an external relation type, which
 * means the relation service won't manage this type, rather
 * we delegate that to the RelationTypeSupport class via
 * inheritance.
 */
public class ConsumerSupplierRelationType extends javax.management.relation.RelationTypeSupport {

    public ConsumerSupplierRelationType () {
        super("ConsumerSupplierRelationType_External");
        try {
            //
            // The roles involved in this type of relation are
            /// between 1-3 consumer MBeans and 1-3 supplier MBeans.
            //
            addRoleInfo(new RoleInfo("Consumer",                // role name
            "sample.standard.Consumer",         // class name
            true,               // role can be read
            false,              // role cannot be modified
            1,                  // must be at least one
            3,                  // no more than two
            "Consumer Role Information"         // description
            ));
            addRoleInfo(new RoleInfo("Supplier",                // role name
            "sample.standard.Supplier",         // class name
            true,               // role can be read
            false,              // role cannot be modified
            1,                  // must be at least one
            3,                  // no more than two
            "Supplier Role Information"         // description
            ));
        } catch (Exception e) {
            throw  new RuntimeException(e.getMessage());
        }
    }
}




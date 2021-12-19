/*
 * File: ./HelloApp/_HelloImplBase.java
 * From: Hello.idl
 * Date: Thu Sep  3 09:46:22 1998
 *   By: idltojava Java IDL 1.2 Nov 12 1997 12:23:47
 */

package HelloApp;
public abstract class _HelloImplBase extends org.omg.CORBA.DynamicImplementation implements HelloApp.Hello {
    // Constructor
    public _HelloImplBase() {
         super();
    }
    // Type strings for this class and its superclases
    private static final String _type_ids[] = {
        "IDL:HelloApp/Hello:1.0"
    };

    public String[] _ids() { return (String[]) _type_ids.clone(); }

    private static java.util.Dictionary _methods = new java.util.Hashtable();
    static {
      _methods.put("sayHello", new java.lang.Integer(0));
     }
    // DSI Dispatch call
    public void invoke(org.omg.CORBA.ServerRequest r) {
       switch (((java.lang.Integer) _methods.get(r.op_name())).intValue()) {
           case 0: // HelloApp.Hello.sayHello
              {
              org.omg.CORBA.NVList _list = _orb().create_list(0);
              r.params(_list);
              String ___result;
                            ___result = this.sayHello();
              org.omg.CORBA.Any __result = _orb().create_any();
              __result.insert_string(___result);
              r.result(__result);
              }
              break;
            default:
              throw new org.omg.CORBA.BAD_OPERATION(0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
       }
 }
}

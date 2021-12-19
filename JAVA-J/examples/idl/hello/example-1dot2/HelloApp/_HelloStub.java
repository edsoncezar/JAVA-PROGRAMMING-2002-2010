/*
 * File: ./HelloApp/_HelloStub.java
 * From: Hello.idl
 * Date: Thu Sep  3 09:46:22 1998
 *   By: idltojava Java IDL 1.2 Nov 12 1997 12:23:47
 */

package HelloApp;
public class _HelloStub
	extends org.omg.CORBA.portable.ObjectImpl
    	implements HelloApp.Hello {

    public _HelloStub(org.omg.CORBA.portable.Delegate d) {
          super();
          _set_delegate(d);
    }

    private static final String _type_ids[] = {
        "IDL:HelloApp/Hello:1.0"
    };

    public String[] _ids() { return (String[]) _type_ids.clone(); }

    //	IDL operations
    //	    Implementation of ::HelloApp::Hello::sayHello
    public String sayHello()
 {
           org.omg.CORBA.Request r = _request("sayHello");
           r.set_return_type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
           r.invoke();
           String __result;
           __result = r.return_value().extract_string();
           return __result;
   }

};

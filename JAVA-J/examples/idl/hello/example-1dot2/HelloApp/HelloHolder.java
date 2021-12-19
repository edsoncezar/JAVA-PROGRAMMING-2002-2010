/*
 * File: ./HelloApp/HelloHolder.java
 * From: Hello.idl
 * Date: Thu Sep  3 09:46:22 1998
 *   By: idltojava Java IDL 1.2 Nov 12 1997 12:23:47
 */

package HelloApp;
public final class HelloHolder
     implements org.omg.CORBA.portable.Streamable{
    //	instance variable 
    public HelloApp.Hello value;
    //	constructors 
    public HelloHolder() {
	this(null);
    }
    public HelloHolder(HelloApp.Hello __arg) {
	value = __arg;
    }

    public void _write(org.omg.CORBA.portable.OutputStream out) {
        HelloApp.HelloHelper.write(out, value);
    }

    public void _read(org.omg.CORBA.portable.InputStream in) {
        value = HelloApp.HelloHelper.read(in);
    }

    public org.omg.CORBA.TypeCode _type() {
        return HelloApp.HelloHelper.type();
    }
}

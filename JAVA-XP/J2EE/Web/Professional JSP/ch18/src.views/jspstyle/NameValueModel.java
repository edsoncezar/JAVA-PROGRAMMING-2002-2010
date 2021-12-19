
package jspstyle;

import javax.swing.ListModel;

/**
*	Extension of Swing ListModel interface to support
*	name-value mappings. The new getName() method will provide
*	the String name, while the ListModel getElementAt() method will
*	provide the object value.
*/
public interface NameValueModel extends ListModel {

	String getName(int i);

}

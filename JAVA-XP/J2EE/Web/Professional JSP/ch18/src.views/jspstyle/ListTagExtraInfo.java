
package jspstyle;

import javax.servlet.jsp.tagext.*;

/**
*
* 	Extra info defining variables for ListTag
*	@author Rod Johnson
*/
public class ListTagExtraInfo extends TagExtraInfo {

	public VariableInfo[] getVariableInfo(TagData data) {
		return new VariableInfo[] 	{
										new VariableInfo("value", "java.lang.Object", true, VariableInfo.NESTED),
									 	new VariableInfo("index", "Integer", true, VariableInfo.NESTED)
									};
	}

}

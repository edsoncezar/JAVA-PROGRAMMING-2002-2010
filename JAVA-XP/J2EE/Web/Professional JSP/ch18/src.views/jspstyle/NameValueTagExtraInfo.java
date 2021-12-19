
package jspstyle;

import javax.servlet.jsp.tagext.*;


public class NameValueTagExtraInfo extends TagExtraInfo {

	public VariableInfo[] getVariableInfo(TagData data) {
		return new VariableInfo[] {
									new VariableInfo("name", "java.lang.String", true, VariableInfo.NESTED),
									new VariableInfo("value", "java.lang.Object", true, VariableInfo.NESTED),
									new VariableInfo("index", "Integer", true, VariableInfo.NESTED)
								};
	}

}

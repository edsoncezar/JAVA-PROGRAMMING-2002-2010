
package jspstyle;

import javax.servlet.jsp.tagext.*;

public class HeadingTagExtraInfo extends TagExtraInfo {

	public VariableInfo[] getVariableInfo(TagData data) {
		return new VariableInfo[] {
									new VariableInfo("heading", "java.lang.String", true, VariableInfo.NESTED),
									new VariableInfo("column", "Integer", true, VariableInfo.NESTED)
									};
	}

}

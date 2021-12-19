
package jspstyle;

import javax.servlet.jsp.tagext.*;

public class CellTagExtraInfo extends TagExtraInfo {

	public VariableInfo[] getVariableInfo(TagData data) {
		return new VariableInfo[] {
									new VariableInfo("value", "java.lang.Object", true, VariableInfo.NESTED),
									new VariableInfo("column", "Integer", true, VariableInfo.NESTED)
									};
	}

}

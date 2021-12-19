
package jspstyle;

import javax.servlet.jsp.tagext.*;

public class RowsTagExtraInfo extends TagExtraInfo {

	public VariableInfo[] getVariableInfo(TagData data) {
		return new VariableInfo[] {
									 new VariableInfo("row", "Integer", true, VariableInfo.NESTED),
									};
	}

}

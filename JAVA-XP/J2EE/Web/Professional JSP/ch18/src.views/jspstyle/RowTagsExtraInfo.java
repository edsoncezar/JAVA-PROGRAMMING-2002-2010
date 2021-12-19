/*
 * HeadingTagExtraInfo.java
 *
 * Created on April 6, 2000, 2:38 PM
 */

package jspstyle;

import javax.servlet.jsp.tagext.*;

/**
 *
 * @author  johnsonr
 * @version
 */
public class RowTagsExtraInfo extends TagExtraInfo {

	public VariableInfo[] getVariableInfo(TagData data) {
		System.out.println("id is " + data.getId());
		return new VariableInfo[] {
									 new VariableInfo("row", "Integer", true, VariableInfo.NESTED),
									};
	}

}

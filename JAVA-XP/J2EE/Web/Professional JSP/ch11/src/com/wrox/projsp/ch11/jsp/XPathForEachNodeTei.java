package com.wrox.projsp.ch11.jsp;

import javax.servlet.jsp.tagext.*;

public class XPathForEachNodeTei extends TagExtraInfo {
    public VariableInfo[] getVariableInfo(TagData data) {
        return new VariableInfo[]{
            new VariableInfo(data.getAttributeString("id"), 
            "com.wrox.projsp.ch11.jsp.XPathForEachNodeTag", 
            true, 
            VariableInfo.NESTED)};
    }
}


package paulojeronimo.customtag;

import javax.servlet.jsp.tagext.TagSupport;

/**
 * Basic JSP tag.
 * 
 * @author     Rick Hightower 
 * @author     Paulo Jerônimo
 * @created    July 29, 2002
 * @version    1.0
 *
 * @jsp.tag name="BasicTag"
 *
 * @jsp.variable 	name-given="currentIter" 
 * 					class="java.lang.Integer" 
 * 					scope="NESTED"
 * 
 * @jsp.variable 	name-given="atBegin" 
 * 					class="java.lang.Integer" 
 * 					scope="AT_BEGIN"
 * 
 * @jsp.variable 	name-given="atEnd" 
 * 					class="java.lang.Integer" 
 * 					scope="AT_END"
 */
public class BasicTag extends TagSupport {
    
    
    /** Holds value of property includePage. */
    private boolean includePage;
    
    /** Holds value of property includeBody. */
    private boolean includeBody;
    
    /** Holds value of property iterate. */
    private int iterate;
   
    private int count;

    public int doStartTag() {
        pageContext.setAttribute("currentIter", new Integer(count = 0));
        pageContext.setAttribute("atBegin", new Integer(0));
        return this.includeBody ? EVAL_BODY_INCLUDE : SKIP_BODY;
    }
    
    public int doEndTag() {
        pageContext.setAttribute("atEnd", new Integer(count));
        return this.includePage ? EVAL_PAGE : SKIP_PAGE;
    }
    
    public int doAfterBody () {
        pageContext.setAttribute("currentIter", new Integer(++count));
        if (count >= iterate) return SKIP_BODY;
        else return EVAL_BODY_AGAIN;
    }
    
    /** Getter for property includePage. 
     * @return Value of property includePage.
     * @jsp.attribute	required="true" 
     *                  rtexprvalue="true"
     *                  description="The includePage attribute"
     */
    public boolean isIncludePage() {
        return this.includePage;
    }
    
    /** Setter for property includePage.
     * @param includePage New value of property includePage.
     * 
     */
    public void setIncludePage(boolean includePage) {
        this.includePage = includePage;
    }
    
    /** Getter for property includeBody.
     * @return Value of property includeBody.
     * @jsp.attribute	required="true" 
     *                  rtexprvalue="true"
     *                  description="The includeBody attribute"
     */
    public boolean isIncludeBody() {
        return this.includeBody;
    }
    
    /** Setter for property includeBody.
     * @param includeBody New value of property includeBody.
     */
    public void setIncludeBody(boolean includeBody) {
        this.includeBody = includeBody;
    }
    
    /** Getter for property iterate.
     * @return Value of property iterate.
     * @jsp.attribute	required="true" 
     *                  rtexprvalue="true"
     *                  description="The iterate attribute"
     */
    public int getIterate() {
        return this.iterate;
    }
    
    /** Setter for property iterate.
     * @param iterate New value of property iterate.
     */
    public void setIterate(int iterate) {
        this.iterate = iterate;
    }
}

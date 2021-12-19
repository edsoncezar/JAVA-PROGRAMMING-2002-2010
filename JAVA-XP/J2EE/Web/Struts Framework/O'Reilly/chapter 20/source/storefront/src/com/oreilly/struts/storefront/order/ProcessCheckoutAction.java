package com.oreilly.struts.storefront.order;

import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.actions.LookupDispatchAction;

import org.apache.struts.util.MessageResources;
import java.util.Iterator;

public class ProcessCheckoutAction extends LookupDispatchAction {

 public ActionForward execute (
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {


        // Identify the request parameter containing the method name
        String parameter = mapping.getParameter();
        if (parameter == null) {
            String message = messages.getMessage("dispatch.handler", mapping.getPath());
            throw new ServletException(message);
        }

        // Identify the string to lookup
        String name = request.getParameter(parameter);
        if (name == null) {
            String message =
                messages.getMessage("dispatch.parameter", mapping.getPath(), parameter);
            throw new ServletException(message);
        }

        if (lookupMap == null) {
            // Build the key lookup map
            lookupMap = new HashMap();
            MessageResources resources = (MessageResources)
                request.getAttribute(Action.MESSAGES_KEY);

            keyMethodMap = getKeyMethodMap();

            Iterator iter = keyMethodMap.keySet().iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                String text = resources.getMessage(key);
                if ((text != null) && !lookupMap.containsKey(text)) {
                    lookupMap.put(text, key);
                }
            }
        }

        // Find the key
        String key = (String) lookupMap.get(name);
        String methodName = (String) keyMethodMap.get(key);
        Object args[] = { mapping, form, request, response };
        return dispatchMethod(mapping, form, request, response, methodName);
    }

  protected Map getKeyMethodMap() {
    Map map = new HashMap();
    map.put("button.checkout", "checkout" );
    map.put("button.saveorder", "saveOrder" );
    return map;
  }


 public ActionForward checkout(ActionMapping mapping,
          ActionForm form,
          HttpServletRequest request,
          HttpServletResponse response)
          throws IOException, ServletException {
       // Fix me
       return null;
 }
}
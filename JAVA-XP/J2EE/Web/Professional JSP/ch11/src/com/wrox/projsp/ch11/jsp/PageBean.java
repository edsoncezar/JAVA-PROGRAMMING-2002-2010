package com.wrox.projsp.ch11.jsp;
import java.util.*;
import org.apache.oro.text.perl.*;
import javax.servlet.http.*;

public class PageBean implements Validator, AutoBean {

    // implementing Validator

    protected Perl5Util perl = new Perl5Util();
    private Map patterns = new Hashtable();
    private Map messages = new Hashtable();
    private Map errors = new Hashtable();
    private static Map namedPatterns = new Hashtable();
    private static Map namedMessages = new Hashtable();

    public void setPattern(String field, String pattern) {
        if(pattern == null  || pattern.equals(""))
            return;
        if(pattern.charAt(0) == '@')
            pattern = (String) namedPatterns.get(pattern);
        patterns.put(field, pattern);
    }
    
    public void setErrorMessage(String field, String message) {
        if(message == null || message.equals(""))
            return;
        if(message.charAt(0) == '@')
            message = (String) namedMessages.get(message);
        messages.put(field, message);
    }
    
    public boolean validate(javax.servlet.http.HttpServletRequest request) {
        Enumeration e = request.getParameterNames();
        while(e.hasMoreElements()) {
            String field = (String) e.nextElement();
            validateField(field, request.getParameter(field));
        }
        return errors.isEmpty();
    }

    private boolean validateField(String field, String value) {
        String pattern = (String) patterns.get(field);
        if(pattern == null)
            return true;
        if(perl.match(pattern, value)) 
            return true;
        errors.put(field, messages.get(field)); 
        return false;
    }
    
    public void setError(String name, String message) {
        errors.put(name, message);
    }
    
    public String getError(String name) {
        String result = (String) errors.get(name);
        if(result == null)
          return "";
        return result;
    }
    
    public void clearErrors() {
        errors.clear();
    }
    
    static {
        // Predefined patterns and messages
        namedPatterns.put("@amount", "/^[1-9][0-9]{0,8}(,[0-9]{1,2})?$/");
        namedPatterns.put("@account", "/^[0-9]{7,9}$/");
        namedPatterns.put("@personName", "/^[a-zA-Z ]{0,30}$/");
        namedPatterns.put("@natid", "/^[0-9]{6}\\-[0-9]{4}$/");
        namedPatterns.put("@tel", "/^[0-9 ]{0,30}$/");
        namedMessages.put("@amount", "Amount can have 8 digits plus 2 decimals");
        namedMessages.put("@account", "Account must have between 7 and 9 digits");
        namedMessages.put("@personName", "Name can have up to 30 alphabetic characters");
    }
    
    // implementing AutoBean
    
    protected Map properties = new Hashtable();
    
    public void set(String property, Object value) {
        // System.out.println(property + " = " + value);
        if(value instanceof String[])
            properties.put(property, ((String[])value)[0]);
        else
            properties.put(property, value);
    }
    
    public String get(String property) {
        Object result = properties.get(property);
        if(result != null) 
            return result.toString();
        return "";
    }
    
    public Object getObject(String property) {
        return properties.get(property);
    }
    
    public void afterPopulate() {
        clearErrors();
    }
    
    public int iLevel() {
        if("admin".equals(get("level"))) return 10;
        if("member".equals(get("level"))) return 9;
        if("user".equals(get("level"))) return 8;
        if("guest".equals(get("level"))) return 7;
        return 6;
    }
    
    protected HttpServletRequest request;
    
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    
    protected HttpServletResponse response;
    
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
    
}


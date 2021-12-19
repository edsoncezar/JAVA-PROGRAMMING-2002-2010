package com.oreilly.struts.storefront.framework.taglib;

import org.apache.struts.taglib.bean.WriteTag;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.Format;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

public class WriteOid extends WriteTag {

  protected String formatValue(Object valueToFormat) throws JspException {

    Format format = null;
    Object value = valueToFormat;
    Locale locale = RequestUtils.retrieveUserLocale( pageContext, this.localeKey );
    boolean formatStrFromResources = false;
        String formatString = formatStr;

        // Return String object as is.
        if ( value instanceof java.lang.String ) {
                return (String)value;
        } else {

            // Try to retrieve format string from resources by the key from formatKey.
            if( ( formatString==null ) && ( formatKey!=null ) ) {
                    formatString = retrieveFormatString( this.formatKey );
                    if( formatString!=null )
                            formatStrFromResources = true;
            }

            // Prepare format object for numeric values.
            if ( value instanceof Number ) {

                if( formatString==null ) {
                        if( ( value instanceof Byte )    ||
                            ( value instanceof Short )   ||
                            ( value instanceof Integer ) ||
                            ( value instanceof Long )    ||
                            ( value instanceof BigInteger ) )
                                formatString = retrieveFormatString( INT_FORMAT_KEY );
                        else if( ( value instanceof Float ) ||
                                 ( value instanceof Double ) ||
                                 ( value instanceof BigDecimal ) )
                                formatString = retrieveFormatString( FLOAT_FORMAT_KEY );
                        if( formatString!=null )
                                formatStrFromResources = true;
                }

                if( formatString!=null ) {
                        try {
                                format = NumberFormat.getNumberInstance( locale );
                                if( formatStrFromResources )
                                        ( ( DecimalFormat ) format ).applyLocalizedPattern( formatString );
                                else
                                        ( ( DecimalFormat ) format ).applyPattern( formatString );
                        } catch( IllegalArgumentException _e ) {
                                JspException e = new JspException(messages.getMessage("write.format", formatString));
                                RequestUtils.saveException(pageContext, e);
                                throw e;
                        }
                }

            } else if (  value instanceof java.util.Date ) {

                if( formatString==null ) {

                        if (  value instanceof java.sql.Timestamp ) {
                                formatString = retrieveFormatString( SQL_TIMESTAMP_FORMAT_KEY );
                        } else if (  value instanceof java.sql.Date ) {
                                formatString = retrieveFormatString( SQL_DATE_FORMAT_KEY );
                        } else if (  value instanceof java.sql.Time ) {
                                formatString = retrieveFormatString( SQL_TIME_FORMAT_KEY );
                        } else if (  value instanceof java.util.Date ) {
                                formatString = retrieveFormatString( DATE_FORMAT_KEY );
                        }

                        if( formatString!=null )
                                formatStrFromResources = true;

                }

                if( formatString!=null ) {
                        if( formatStrFromResources ) {
                                format = new SimpleDateFormat( formatString, locale );
                        } else {
                                format = new SimpleDateFormat( formatString );
                        }
                }

            }
        }

        if( format!=null )
                return format.format( value );
        else
                return value.toString();

    }
}
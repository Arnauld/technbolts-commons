/* $Id$ */
package org.technbolts.dto.domain.common;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.isEmpty;

import java.util.Date;

import com.thoughtworks.xstream.converters.SingleValueConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.technbolts.util.Adaptable;

/**
 * DateAsSecondsSinceEpochConverter.
 * 
 * convert a date into its numerical representation in seconds from epoch.
 * 
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class DateAsSecondsSinceEpochConverter implements Adaptable
{
    private static Logger logger = LoggerFactory.getLogger(DateAsSecondsSinceEpochConverter.class);
    
    private SingleValueConverter xstreamSingleValueConverter;

    @SuppressWarnings("unchecked")
    public <T> T getAdapter(Class<T> adapter)
    {
        if(adapter.equals(SingleValueConverter.class))
        {
            if(xstreamSingleValueConverter==null)
                xstreamSingleValueConverter = createXStreamSingleValueConverter ();
            return (T)xstreamSingleValueConverter;
        }
            
        return null;
    }

    private SingleValueConverter createXStreamSingleValueConverter()
    {
        return new SingleValueConverter () {
            @SuppressWarnings("unchecked")
            public boolean canConvert(Class type)
            {
                return type.equals(Date.class);
            }
            public Object fromString(String str)
            {
                return stringToDate (str);
            }
            public String toString(Object obj)
            {
                return dateToString ((Date)obj);
            }
        };
    }
    
    protected static String dateToString (Date date) {
        if(date==null)
            return null;
        return String.valueOf(date.getTime()/1000);
    }
    
    protected static Date stringToDate (String str) {
        if(isEmpty(str))
            return null;
        try
        {
            long value = Long.parseLong(str);
            Date date = new Date (value*1000L);
            if(logger.isDebugEnabled())
                logger.debug(format("Converting date from <%1$s> to <%2$tT %2$tD %2$tZ> i.e. <%3$d>", str, date, date.getTime()/1000));
            return date;
        }
        catch (NumberFormatException e)
        {
            if(logger.isWarnEnabled())
                logger.warn("Value is not a decimal integer '"+str+"' and cannot be converted to date", e);
        }
        return null;
    }
}

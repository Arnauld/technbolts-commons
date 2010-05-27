/* $Id$ */
package org.technbolts.dto.domain.common;

import java.util.Date;

import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.configuration.annotation.Converter;
import org.technbolts.dto.configuration.annotation.Since;

/**
 * DateRange
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class DateRange
{
    @Since(Version.V1)
    @Converter(clazz=DateAsSecondsSinceEpochConverter.class, since=Version.V1)
    private Date from;
    
    @Since(Version.V1)
    @Converter(clazz=DateAsSecondsSinceEpochConverter.class, since=Version.V1)
    private Date to;
    
    public DateRange()
    {
    }
    
    public DateRange(Date from, Date to)
    {
        this.from = from;
        this.to = to;
    }

    public Date getFrom()
    {
        return from;
    }

    public void setFrom(Date from)
    {
        this.from = from;
    }

    public Date getTo()
    {
        return to;
    }

    public void setTo(Date to)
    {
        this.to = to;
    }
    
    public boolean isWithin(Date date) {
        if(date==null)
            return true;
        if(to!=null && date.after(to))
            return false;
        if(from!=null && date.before(from))
            return false;
        return true;
    }
}

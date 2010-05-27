/* $Id$ */
package org.technbolts.dto.domain.common;

import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.configuration.annotation.Alias;
import org.technbolts.dto.configuration.annotation.Since;

/**
 * LongRange
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class LongRange
{
    @Since(Version.V1)
    @Alias(value="min", since=Version.V1)
    private Long min;
    
    @Since(Version.V1)
    @Alias(value="max", since= Version.V1)
    private Long max;
    
    /**
     * 
     */
    public LongRange()
    {
    }
    
    /**
     * @param min
     * @param max
     */
    public LongRange(Long min, Long max)
    {
        super();
        this.min = min;
        this.max = max;
    }

    /**
     * @return the min
     */
    public Long getMin()
    {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(Long min)
    {
        this.min = min;
    }

    /**
     * @return the max
     */
    public Long getMax()
    {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(Long max)
    {
        this.max = max;
    }
    
    /**
     * @param value
     * @return
     */
    public boolean isInRange(Long value) {
        if(value==null)
            return true;
        if(min!=null && value<min)
            return false;
        if(max!=null && value>max)
            return false;
        return true;
    }
}

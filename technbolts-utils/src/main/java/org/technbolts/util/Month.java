package org.technbolts.util;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

/**
 * Month enumeration.
 * 
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public enum Month {
    January(Calendar.JANUARY),
    February(Calendar.FEBRUARY),
    March(Calendar.MARCH),
    April(Calendar.APRIL),
    May(Calendar.MAY),
    June(Calendar.JUNE),
    July(Calendar.JULY),
    August(Calendar.AUGUST),
    September(Calendar.SEPTEMBER),
    October(Calendar.OCTOBER),
    November(Calendar.NOVEMBER),
    December(Calendar.DECEMBER);
    
    private int calendarValue;
    private Month(int calendarValue) {
        this.calendarValue = calendarValue;
    }
    
    /**
     * @return
     */
    public String toIdentifier () {
        return name().toLowerCase();
    }
    
    /**
     * @param identifier
     * @return
     */
    public static Month fromIdentifier(String identifier) {
        for(Month month : values())
            if(StringUtils.equalsIgnoreCase(identifier, month.toIdentifier()))
                return month;
        return null;
    }
    
    /**
     * Field number for get and set indicating the month. 
     * This is a calendar-specific value. 
     * The first month of the year is JANUARY which is 0; 
     * the last depends on the number of months in a year.
     * @return
     * @see java.util.Calendar#MONTH
     */
    public int getCalendarValue() {
        return calendarValue;
    }
}
/* $Id$ */
package org.technbolts.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * DateBuilder utility to build date.
 * 
 * @author <a href="mailto:arnauld.loyer@eptica.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class DateBuilder
{
    /**
     * Return a new DateBuilder with "GMT" as initial time zone.
     * @return
     * @see #newDateBuilder(java.util.TimeZone)
     */
    public static DateBuilder newDateBuilder () {
        return newDateBuilder (TimeZone.getTimeZone("GMT"));
    }
    
    /**
     * Return a new DateBuilder.
     * @param timeZone
     * @return
     */
    public static DateBuilder newDateBuilder (TimeZone timeZone) {
        return new DateBuilder().timeZone(timeZone);
    }

    private Calendar calendar = Calendar.getInstance();
    
    protected DateBuilder() {
    }
    
    /**
     * Create a date according to the current parameters set.
     * @return
     */
    public Date build () {
        return calendar.getTime();
    }
    
    /**
     * Shortcut for:
     * <pre>
     *  year(year).month(month).dayOfMonth(dayOfMonth)
     * </pre>
     * @param year
     * @param month
     * @param dayOfMonth
     * @return
     * @see #year
     * @see #month(Month)
     * @see #dayOfMonth(int)
     */
    public DateBuilder date(int year, Month month, int dayOfMonth) {
        return year(year).month(month).dayOfMonth(dayOfMonth);
    }
    
    /**
     * Shortcut for:
     * <pre>
     *  year(year).month(month).dayOfMonth(dayOfMonth)
     * </pre>
     * @param year
     * @param month
     * @param dayOfMonth
     * @return
     * @see #year
     * @see #month(Month)
     * @see #dayOfMonth(int)
     */
    public DateBuilder yymmdd(int year, Month month, int dayOfMonth) {
        return year(year).month(month).dayOfMonth(dayOfMonth);
    }
    
    /**
     * Set the year. 
     * @param year
     * @return
     * @see java.util.Calendar#YEAR
     */
    public DateBuilder year(int year) {
        calendar.set(Calendar.YEAR, year);
        return this;
    }
    
    /**
     * Set the month. 
     * @param month
     * @return
     * @see Month
     * @see Month#January
     * @see Month#February
     * @see Month#March
     * @see Month#April
     * @see Month#May
     * @see Month#June
     * @see Month#July
     * @see Month#August
     * @see Month#September
     * @see Month#October
     * @see Month#November
     * @see Month#December
     */
    public DateBuilder month(Month month) {
        return month(month.getCalendarValue());
    }
    
    /**
     * Set the month. 
     * This is a calendar-specific value. 
     * The first month of the year is JANUARY which is 0; the last depends on
     * the number of months in a year.
     * @param monthFieldId
     * @return
     */
    public DateBuilder month(int monthFieldId) {
        calendar.set(Calendar.MONTH, monthFieldId);
        return this;
    }
    
    /**
     * Field number for get and set indicating the day of the month. 
     * This is a synonym for DATE. The first day of the month has value 1.
     * 
     * @param dayOfMonth
     * @return
     * @see java.util.Calendar#DAY_OF_MONTH
     */
    public DateBuilder dayOfMonth(int dayOfMonth) {
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return this;
    }
    
    /**
     * Set indicating the hour of the day. 
     * HOUR_OF_DAY is used for the 24-hour clock. 
     * E.g., at 10:04:15.250 PM the HOUR_OF_DAY is 22.
     * 
     * @param hourOfDay
     * @return
     * @see java.util.Calendar#HOUR_OF_DAY
     */
    public DateBuilder hourOfDay(int hourOfDay) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        return this;
    }
    
    /**
     * Set the minute within the hour. 
     * E.g., at 10:04:15.250 PM the MINUTE is 4.
     *
     * @param minutes
     * @return
     * @see java.util.Calendar#MINUTE
     */
    public DateBuilder minutes(int minutes) {
        calendar.set(Calendar.MINUTE, minutes);
        return this;
    }
    
    /**
     * Set the second within the minute. 
     * E.g., at 10:04:15.250 PM the SECOND is 15.
     * @param seconds
     * @return
     * @see java.util.Calendar#SECOND
     */
    public DateBuilder seconds(int seconds) {
        calendar.set(Calendar.SECOND, seconds);
        return this;
    }
    
    /**
     * Sets the time zone.
     * @param timeZone
     * @return
     * @see java.util.Calendar#setTimeZone(java.util.TimeZone)
     */
    public DateBuilder timeZone(TimeZone timeZone) {
        calendar.setTimeZone(timeZone);
        return this;
    }
    
    /**
     * Shortcut for:
     * <pre>
     *  hourOfDay(hourOfDay).minutes(minutes).seconds(seconds)
     * </pre>
     * 
     * HOUR_OF_DAY is used for the 24-hour clock. 
     * 
     * @param hourOfDay
     * @param minutes
     * @param seconds
     * @return
     * @see #hourOfDay(int)
     * @see #minutes(int)
     * @see #seconds(int)
     */
    public DateBuilder timeOfDay(int hourOfDay, int minutes, int seconds) {
        return hourOfDay(hourOfDay).minutes(minutes).seconds(seconds);
    }
    
    /**
     * Shortcut for:
     * <pre>
     *  hourOfDay(hourOfDay).minutes(minutes).seconds(seconds)
     * </pre>
     * 
     * HOUR_OF_DAY is used for the 24-hour clock. 
     * 
     * @param hourOfDay
     * @param minutes
     * @param seconds
     * @return
     * @see #hourOfDay(int)
     * @see #minutes(int)
     * @see #seconds(int)
     */
    public DateBuilder hhmmss(int hourOfDay, int minutes, int seconds) {
        return hourOfDay(hourOfDay).minutes(minutes).seconds(seconds);
    }
    
    /**
     * Shortcut for:
     * <pre>
     *  timeZone(timeZone).hourOfDay(hourOfDay).minutes(minutes).seconds(seconds)
     * </pre>
     * 
     * HOUR_OF_DAY is used for the 24-hour clock. 
     * 
     * @param hourOfDay
     * @param minutes
     * @param seconds
     * @return
     * @see #timeZone(java.util.TimeZone)
     * @see #hourOfDay(int)
     * @see #minutes(int)
     * @see #seconds(int)
     */
    public DateBuilder zhhmmss(TimeZone timeZone, int hourOfDay, int minutes, int seconds) {
        return timeZone(timeZone).hourOfDay(hourOfDay).minutes(minutes).seconds(seconds);
    }
    
    
    /**
     * Set the millisecond within the second. 
     * E.g., at 10:04:15.250 PM the MILLISECOND is 250.
     * 
     * @param millis
     * @return
     * @see java.util.Calendar#MILLISECOND
     */
    public DateBuilder millis(int millis) {
        calendar.set(Calendar.MILLISECOND, millis);
        return this;
    }
}

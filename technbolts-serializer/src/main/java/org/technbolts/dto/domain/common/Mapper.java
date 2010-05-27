/* $Id$ */
package org.technbolts.dto.domain.common;

import org.technbolts.util.StringSelector;
import org.technbolts.util.functional.F1;
import org.technbolts.util.functional.F1Utils;

import java.util.Date;


/**
 * Mapper
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class Mapper
{
    public static F1.ToBoolean<Date> isInRange (final DateRange range, boolean defaultIfNull, final boolean nullInRange) {
        if(range==null)
            return F1Utils.always(defaultIfNull);
        
        final Date to   = range.getFrom();
        final Date from = range.getTo();
        return new F1.ToBoolean<Date> () {
            public boolean op(Date date)
            {
                if(date==null)
                    return nullInRange;
                if(to!=null   && date.after(to))
                    return false;
                if(from!=null && date.before(from))
                    return false;
                return true;
            }
        };
    }
    
    
    public static StringSelector.Mode toStringSelectorMode (SearchType searchType, StringSelector.Mode defaultIfNull) {
        if(searchType==null)
            return defaultIfNull;
        
        StringSelector.Mode mode = StringSelector.Mode.Equals;
        switch(searchType) {
            case Contains   : mode = StringSelector.Mode.Contains; break;
            case EndsWith   : mode = StringSelector.Mode.EndsWith; break;
            case Equals     : mode = StringSelector.Mode.Equals; break;
            case StartsWith : mode = StringSelector.Mode.StartsWith; break;
            case Wildcard   : mode = StringSelector.Mode.WildCard; break;
        }
        return mode;
    }
    
    private static boolean booleanValue(Boolean bool, boolean defaultIfNull) {
        if(bool==null)
            return defaultIfNull;
        return bool.booleanValue();
    }
    
    public static F1.ToBoolean<String> toF1Boolean (StringSearch stringSearch) {
        return toF1Boolean(stringSearch, false);
    }
    
    public static F1.ToBoolean<String> toF1Boolean (StringSearch stringSearch, boolean nullMatch) {
        SearchType searchType = stringSearch.getSearchType();
        if(searchType==SearchType.Empty) {
            return new F1.ToBoolean<String> () {
                public boolean op(String input)
                {
                    return (input==null)||(input.trim().length()==0);
                }
            };
        }
        
        final StringSelector selector = new StringSelector ();
        selector.setCaseSensitive(booleanValue(stringSearch.getCaseSensitive(),true));
        selector.setMode(toStringSelectorMode(searchType, StringSelector.Mode.Equals));
        selector.setNullMatch(nullMatch);
        selector.setPattern(stringSearch.getText());
        
        return new F1.ToBoolean<String> () {
            public boolean op(String input)
            {
                return selector.match(input);
            }
        };
    }
}

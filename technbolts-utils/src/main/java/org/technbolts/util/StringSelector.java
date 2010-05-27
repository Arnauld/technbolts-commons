/* $Id: StringSelector.java,v 1.1 2009-07-02 14:26:24 arnauld Exp $ */
package org.technbolts.util;

/**
 * StringSelector
 * 
 * 
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class StringSelector
{
    public enum Mode {
        //Regex,
        StartsWith,
        EndsWith,
        Contains,
        Equals,
        WildCard
    }
    
    private boolean isCaseSensitive;
    private Mode mode;
    private String pattern;
    private String lowerCasedPattern;
    private boolean nullMatch = false;
    
    public boolean match(String other) {
        if(other==null)
            return nullMatch;
        
        switch(mode) {
            case StartsWith : {
                if(isCaseSensitive)
                    return other.startsWith(pattern);
                else
                    return other.toLowerCase().startsWith(lowerCasedPattern);
            }
            case EndsWith : {
                if(isCaseSensitive)
                    return other.endsWith(pattern);
                else
                    return other.toLowerCase().endsWith(lowerCasedPattern);
            }
            case Contains : {
                if(isCaseSensitive)
                    return other.contains(pattern);
                else
                    return other.toLowerCase().contains(lowerCasedPattern);
            }
            case Equals : {
                if(isCaseSensitive)
                    return other.equals(pattern);
                else
                    return other.equalsIgnoreCase(pattern);
            }
            case WildCard : {
                if(isCaseSensitive)
                    return StringMatcher.wildCardMatching(other, pattern);
                else
                    return StringMatcher.wildCardMatching(other.toLowerCase(), lowerCasedPattern);
            }
        }
        return false;
    }
    
    /**
     * Indicate if the <code>match(String)</code> return <code>true</code> 
     * or <code>false</code> with a null value.
     */
    public boolean getNullMatch()
    {
        return nullMatch;
    }
    
    /**
     * Indicate if the <code>match(String)</code> return <code>true</code> 
     * or <code>false</code> with a null value.
     * @param nullMatch
     */
    public void setNullMatch(boolean nullMatch)
    {
        this.nullMatch = nullMatch;
    }
    
    public Mode getMode()
    {
        return mode;
    }
    public void setMode(Mode mode)
    {
        this.mode = mode;
    }
    public String getPattern()
    {
        return pattern;
    }
    public void setPattern(String pattern)
    {
        this.pattern = pattern;
        this.lowerCasedPattern = pattern.toLowerCase();
    }
    public void setCaseSensitive(boolean isCaseSensitive)
    {
        this.isCaseSensitive = isCaseSensitive;
    }
    
    public boolean isCaseSensitive () {
        return isCaseSensitive;
    }
}

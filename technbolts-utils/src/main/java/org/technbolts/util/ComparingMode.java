/* $Id$ */
package org.technbolts.util;

/**
 * ComparingMode
 * @author <a href="mailto:arnauld.loyer@eptica.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public enum ComparingMode
{
    EqualTo("="),
    GreaterThan(">"),
    GreaterThanOrEqualTo(">="),
    LowerThan("<"),
    LowerThanOrEqualTo("<=");
    
    private String symbol;
    private ComparingMode(String symbol)
    {
        this.symbol = symbol;
    }
    public String getSymbol()
    {
        return symbol;
    }
    public <T extends Comparable<T>> boolean isSatisfiedBy(T item1, T item2) {
        int compare = ComparatorUtils.compare(item1, item2);
        switch(this) {
            case EqualTo : {
                return compare==0;
            }
            case GreaterThan : {
                return compare>0;
            }
            case GreaterThanOrEqualTo : {
                return compare>=0;
            }
            case LowerThan : {
                return compare<0;
            }
            case LowerThanOrEqualTo : {
                return compare<=0;
            }
        }
        throw new UnsupportedOperationException("Unknown mode '"+this+"'");
    }
}

/* $Id$ */
package org.technbolts.util;

import java.util.Comparator;

/**
 * ComparatorUtils
 * @author <a href="mailto:arnauld.loyer@eptica.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class ComparatorUtils
{
    /**
     * Null-safe comparaison.
     * Null value are considered as lesser than not null.
     * @param <T>
     * @param o1
     * @param o2
     * @return
     * @see Comparable
     */
    public static <T extends Comparable<T>> int compare (T o1, T o2) {
        if(o1==o2)
            return 0;
        if(o1==null)
            return -1;
        if(o2==null)
            return +1;
        return o1.compareTo(o2);
    }
    
    /**
     * Same as <code>comparator ()</code>.
     * Type is provided for inference facility.
     * @param <T>
     * @param type
     * @return
     */
    public static <T extends Comparable<T>> Comparator<T> comparator (Class<T> type) {
        return comparator();
    }
    
    /**
     * Create a comparator that use the <code>Comparable</code> contract of 
     * the checked value to compare them.
     * Internally call the <code>compare (T o1, T o2)</code> to perform the
     * job. 
     * 
     * @param <T>
     * @return
     * @see #compare(Comparable, Comparable)
     */
    public static <T extends Comparable<T>> Comparator<T> comparator () {
        return new Comparator<T> () {
            public int compare(T o1, T o2)
            {
                return ComparatorUtils.compare(o1, o2);
            }
        };
    }

    /**
     * Reverse the comparator by return its result multiply by <code>-1</code>. 
     * @param comparator
     * @return
     */
    public static <T> Comparator<T> reverse(final Comparator<T> comparator)
    {
        return new Comparator<T>() {
            public int compare(T o1, T o2) {
                return -comparator.compare(o1, o2);
            }
        };
    }
    
    /**
     * Call the comparator by swaping its operands. 
     * <pre>
     *   int compare(T o1, T o2) {
     *     return comparator.compare(<i><b>o2</b></i>, <i><b>o1</b></i>);
     *   }
     * </pre>
     * @param comparator
     * @return
     */
    public static <T> Comparator<T> swapOperands(final Comparator<T> comparator)
    {
        return new Comparator<T>() {
            public int compare(T o1, T o2) {
                return comparator.compare(o2, o1);
            }
        };
    }
}

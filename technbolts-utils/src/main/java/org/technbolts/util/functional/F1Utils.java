/* $Id: F1Utils.java,v 1.1 2009-07-22 17:08:07 arnauld Exp $ */
package org.technbolts.util.functional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * F1Utils
 * @author <a href="mailto:arnauld.loyer@eptica.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class F1Utils
{
    
    /**
     * @param <K>
     * @param <V>
     * @param transform
     * @param filter
     * @return
     */
    public static <K,V> F1.ToBoolean<K> compose(final F1<K,V> transform, final F1.ToBoolean<V> filter) {
        return new F1.ToBoolean<K> () {
            public boolean op(K item)
            {
                return filter.op(transform.op(item));
            }
        };
    }
    
    /**
     * Returns a function which performs a map lookup. If the map does not
     * contains the specified key then <code>missingKeyValue</code> is returned.
     * This allow <code>null</code> value in the map to be returned. 
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @param defaultValue
     * @return
     */
    public static <K,V> F1<K,V> forMap(final Map<K,V> map, final V missingKeyValue) {
        return new F1<K, V> () {
            public V op(K key)
            {
                // do the key really has the 'null' value
                if(map.containsKey(key))
                {
                    return map.get(key);
                }
                return missingKeyValue;
            }
        };
    }
    
    /**
     * @param <T>
     * @param type
     * @return
     */
    public static <T extends Comparable<T>> F1.ToBoolean<T> greaterThan(final T value) {
        return new F1.ToBoolean<T> () {
            public boolean op(T param)
            {
                return value.compareTo(param)<0;
            }
        };
    }
    
    /**
     * @param <T>
     * @param type
     * @return
     */
    public static <T extends Comparable<T>> F1.ToBoolean<T> greaterThanOrEqualTo(final T value) {
        return new F1.ToBoolean<T> () {
            public boolean op(T param)
            {
                return value.compareTo(param)<=0;
            }
        };
    }
    
    /**
     * @param <T>
     * @param type
     * @return
     */
    public static <T extends Comparable<T>> F1.ToBoolean<T> lesserThan(final T value) {
        return new F1.ToBoolean<T> () {
            public boolean op(T param)
            {
                return value.compareTo(param)>0;
            }
        };
    }
    
    /**
     * @param <T>
     * @param type
     * @return
     */
    public static <T extends Comparable<T>> F1.ToBoolean<T> lesserThanOrEqualTo(final T value) {
        return new F1.ToBoolean<T> () {
            public boolean op(T param)
            {
                return value.compareTo(param)>=0;
            }
        };
    }
    
    private static final boolean areEquals(Object o1, Object o2) {
        if(o1==o2)
            return true;
        if(o1==null || o2==null)
            return false;
        return o1.equals(o2);
    }
    
    /**
     * Return a function that always return <code>value</code>.
     * @param <T>
     * @param type
     * @return
     */
    public static <T> F1.ToBoolean<T> always(final boolean value) {
        return new F1.ToBoolean<T> () {
            public boolean op(T param)
            {
                return value;
            }
        };
    }
    
    /**
     * Return a function that always return <code>true</code>.
     * @param <T>
     * @param type
     * @return
     */
    public static <T> F1.ToBoolean<T> alwaysTrue() {
        return always(true);
    }
    
    /**
     * Return a function that always return <code>true</code>.
     * 
     * Same as <code>alwaysTrue()</code> with a type to help with generic
     * type inference.
     * @param <T>
     * @param type
     * @return
     */
    public static <T> F1.ToBoolean<T> alwaysTrue(Class<T> type) {
        return always(true);
    }
    
    /**
     * Return a function that indicates if its argument
     * is an instance of the specified type.
     * @param <T>
     * @param type
     * @return
     */
    public static <T> F1.ToBoolean<Object> instanceOf(final Class<T> type) {
        return new F1.ToBoolean<Object> () {
            public boolean op(Object param)
            {
                return type.isInstance(param);
            }
        };
    }
    
    /**
     * Function that cast an argument into the specified type.
     * 
     * Same as <code>cast(Class)</code> with a from type to help with generic
     * type inference.
     * 
     * @param <P>
     * @param <T>
     * @param from
     * @param to
     * @return
     * @see #cast(Class)
     */
    public static <P,T> F1<P,T> cast(Class<P> from, Class<T> to) {
       return cast(to); 
    }
    
    /**
     * Function that cast an argument into the specified type.
     * 
     * The function is not protected against <code>ClassCastException</code>
     * 
     * @param <P>
     * @param <T>
     * @param to
     * @return
     * @throws ClassCastException 
     */
    public static <P,T> F1<P,T> cast(final Class<T> to) {
        return new F1<P, T> () {
            public T op(P t)
            {
                return to.cast(t);
            }
        };
    }
    
    /**
     * Function that cast an argument into the specified type.
     * 
     * The function should be protected against <code>ClassCastException</code>
     * 
     * @param <P>
     * @param <T>
     * @param to
     * @param defaultValue default value returned if the cast is not possible
     * 
     * @return
     * @throws ClassCastException 
     */
    public static <P,T> F1<P,T> castIfPossible(final Class<T> to, final T defaultValue) {
        return new F1<P, T> () {
            public T op(P t)
            {
                if(t==null)
                    return null;
                else if(to.isInstance(t))
                    return to.cast(t);
                else
                    return defaultValue;
            }
        };
    }
    
    /**
     * Function that cast an argument into the specified type.
     * 
     * Same as <code>castIfPossible(to,null)</code>
     * 
     * The function should be protected against <code>ClassCastException</code>
     * 
     * @param <P>
     * @param <T>
     * @param to
     * 
     * @return
     * @throws ClassCastException 
     * @see {@link #castIfPossible(Class, Object)}
     */
    public static <P,T> F1<P,T> castIfPossible(final Class<T> to) {
        return castIfPossible(to,null);
    }
    
    /**
     * Return a function that indicates if its argument
     * is not <code>null</code>.
     * 
     * 
     * Same as <code>notNull()</code> with a type to help with generic
     * type inference.
     * 
     * @param <T>
     * @param type
     * @return
     */
    public static <T> F1.ToBoolean<T> notNull(final Class<T> type) {
        return notNull();
    }
    
    /**
     * Return a function that indicates if its argument
     * is not <code>null</code>.
     * 
     * @param <T>
     * @param type
     * @return
     */
    public static <T> F1.ToBoolean<T> notNull() {
        return new F1.ToBoolean<T> () {
            public boolean op(T param)
            {
                return (param!=null);
            }
        };
    }
    
    /**
     * Return a function that return the type of if its argument
     * is not <code>null</code>.
     * 
     * @param <T>
     * @param type
     * @return
     */
    public static <T> F1<T, Class<?>> typeOf () {
        return new F1<T, Class<?>> () {
            public  Class<?> op(T param)
            {
                if(param==null)
                    return null;
                return param.getClass();
            }
        };
    }
    
    /**
     * Negates any results returns by the function.
     * Same as <code>negate(F1.ToBoolean)</code>.
     * @param <T>
     * @param function
     * @return
     */
    public static <T> F1.ToBoolean<T> not(final F1.ToBoolean<T> function) {
        return negate(function);
    }
    
    /**
     * Negates any results returns by the function.
     * Same as <code>not(F1.ToBoolean)</code>.
     * @param <T>
     * @param function
     * @return
     */
    public static <T> F1.ToBoolean<T> negate(final F1.ToBoolean<T> function) {
        return new F1.ToBoolean<T> () {
            public boolean op(T param)
            {
                return !function.op(param);
            }
        };
    }
    
    /**
     * Same as <code>not(oneOf)</code>.
     * 
     * @param <T>
     * @param items
     * @return
     */
    public static <T> F1.ToBoolean<T> except(final T... items) {
        return not(oneOf(items));
    }
    
    /**
     * Same as <code>not(oneOf)</code>.
     * @param <T>
     * @param items
     * @return
     */
    public static <T> F1.ToBoolean<T> except(final Iterable<T> items) {
        return noneOf(items);
    }
    
    /**
     * Same as <code>not(oneOf)</code>.
     * Inlined version for one element.
     * 
     * @param <T>
     * @param item
     * @return
     * @see #except(Object...)
     */
    public static <T> F1.ToBoolean<T> except(final T item) {
        return not(oneOf(item));
    }
    
    /**
     * Same as <code>not(oneOf)</code>.
     * Inlined version for two elements.
     * 
     * @param <T>
     * @param item
     * @return
     * @see #except(Object...)
     */
    public static <T> F1.ToBoolean<T> except(final T item1, final T item2) {
        return not(oneOf(item1,item2));
    }
    
    /**
     * Same as <code>not(oneOf)</code>.
     * Inlined version for three elements.
     * 
     * @param <T>
     * @param item
     * @return
     * @see #except(Object...)
     */
    public static <T> F1.ToBoolean<T> except(final T item1, final T item2, final T item3) {
        return not(oneOf(item1,item2,item3));
    }
    
    /**
     * Same as <code>not(oneOf)</code>.
     * 
     * @param <T>
     * @param item
     * @return
     * @see #except(Object...)
     */
    public static <T> F1.ToBoolean<T> noneOf(final T... items) {
        return not(oneOf(items));
    }
    
    /**
     * Same as <code>not(oneOf)</code>.
     * 
     * @param <T>
     * @param item
     * @return
     * @see #except(Object...)
     */
    public static <T> F1.ToBoolean<T> noneOf(final Iterable<T> items) {
        return not(oneOf(items));
    }
    
    /**
     * Same as <code>not(oneOf)</code>.
     * Inlined version for one element.
     * 
     * @param <T>
     * @param item
     * @return
     * @see #except(Object...)
     */
    public static <T> F1.ToBoolean<T> noneOf(final T item) {
        return not(oneOf(item));
    }
    
    /**
     * Same as <code>not(oneOf)</code>.
     * Inlined version for two elements.
     * 
     * @param <T>
     * @param item
     * @return
     * @see #except(Object...)
     */
    public static <T> F1.ToBoolean<T> noneOf(final T item1, final T item2) {
        return not(oneOf(item1,item2));
    }
    
    /**
     * Same as <code>not(oneOf)</code>.
     * Inlined version for three elements.
     * 
     * @param <T>
     * @param item
     * @return
     * @see #except(Object...)
     */
    public static <T> F1.ToBoolean<T> noneOf(final T item1, final T item2, final T item3) {
        return not(oneOf(item1,item2,item3));
    }
    
    /**
     * Return a function that will return <code>true</code> if its argument
     * is one of (according to the <code>equals</code> contract) the
     * specified items.
     * 
     * @param <T>
     * @param items
     * @return
     */
    public static <T> F1.ToBoolean<T> oneOf(final Iterable<T> items) {
        return new F1.ToBoolean<T> () {
            public boolean op(T item)
            {
                for(T other : items)
                    if(areEquals(other,item))
                        return true;
                return false;
            }
        };
    }
    
    /**
     * Return a function that will return <code>true</code> if its argument
     * is one of (according to the <code>equals</code> contract) the
     * specified items.
     * 
     * @param <T>
     * @param item
     * @return
     */
    public static <T> F1.ToBoolean<T> oneOf(final T... items) {
        // first try to inline
        if(items.length==1)
            return oneOf(items[0]);
        else if(items.length==2)
            return oneOf(items[0], items[1]);
        else if(items.length==3)
            return oneOf(items[0], items[1], items[2]);
        
        return new F1.ToBoolean<T> () {
            public boolean op(T item)
            {
                for(T other : items)
                    if(areEquals(other,item))
                        return true;
                return false;
            }
        };
    }
    
    /**
     * Same as <code>oneOf</code>.
     * Same as <code>equalTo</code>.
     * 
     * Inlined version for one element.
     * 
     * @param <T>
     * @param item
     * @return
     * @see #oneOf(Object...)
     * @see #matchOneOf(Comparable)
     */
    public static <T> F1.ToBoolean<T> oneOf(final T item) {
        return new F1.ToBoolean<T> () {
            public boolean op(T other)
            {
                if(areEquals(other,item))
                    return true;
                return false;
            }
        };
    }
    
    /**
     * Same as <code>oneOf</code>.
     * 
     * @param <T>
     * @param item
     * @return
     * @see #oneOf(Object...)
     * @see #matchOneOf(Comparable)
     */
    public static <T> F1.ToBoolean<T> equalTo(final T item) {
        return new F1.ToBoolean<T> () {
            public boolean op(T other)
            {
                if(areEquals(other,item))
                    return true;
                return false;
            }
        };
    }
    
    /**
     * Same as <code>oneOf</code>.
     * Inlined version for two elements.
     * 
     * @param <T>
     * @param item
     * @return
     * @see #oneOf(Object...)
     * @see #matchOneOf(Comparable, Comparable)
     */
    public static <T> F1.ToBoolean<T> oneOf(final T item1, final T item2) {
        return new F1.ToBoolean<T> () {
            public boolean op(T other)
            {
                if(areEquals(other,item1)||areEquals(other,item2))
                    return true;
                return false;
            }
        };
    }
    
    /**
     * Same as <code>oneOf</code>.
     * Inlined version for three elements.
     * 
     * @param <T>
     * @param item
     * @return
     * @see #oneOf(Object...)
     * @see #matchOneOf(Comparable, Comparable, Comparable)
     */
    public static <T> F1.ToBoolean<T> oneOf(final T item1, final T item2, final T item3) {
        return new F1.ToBoolean<T> () {
            public boolean op(T other)
            {
                if(areEquals(other,item1)||areEquals(other,item2)||areEquals(other,item3))
                    return true;
                return false;
            }
        };
    }
    
    /**
     * Return a function that will return <code>true</code> if its argument
     * is one of (according to the <code>compare</code> contract) the
     * specified items.
     * 
     * @param <T>
     * @param item
     * @return
     * @see #oneOf(Object...)
     */
    public static <T> F1.ToBoolean<T> matchOneOf(final Comparable<T>... matchers) {
        return new F1.ToBoolean<T> () {
            public boolean op(T item)
            {
                for(Comparable<T> other : matchers)
                    if(other.compareTo(item)==0)
                        return true;
                return false;
            }
        };
    }
    
    /**
     * Return a function that will return <code>true</code> if its argument
     * is one of (according to the <code>compare</code> contract) the
     * specified items.
     * 
     * @param <T>
     * @param item
     * @return
     * @see #oneOf(Object...)
     */
    public static <T> F1.ToBoolean<T> matchOneOf(final Iterable<Comparable<T>> matchers) {
        return new F1.ToBoolean<T> () {
            public boolean op(T item)
            {
                for(Comparable<T> other : matchers)
                    if(other.compareTo(item)==0)
                        return true;
                return false;
            }
        };
    }
    
    /**
     * Same as <code>matchOneOf</code>.
     * Inlined version for one element.
     * 
     * @param <T>
     * @param matcher
     * @return
     * @see #matchOneOf(Comparable...)
     * @see #oneOf(Object)
     */
    public static <T> F1.ToBoolean<T> matchOneOf(final Comparable<T> matcher) {
        return new F1.ToBoolean<T> () {
            public boolean op(T item)
            {
                if(matcher.compareTo(item)==0)
                    return true;
                return false;
            }
        };
    }
    
    /**
     * Same as <code>matchOneOf</code>.
     * Inlined version for two elements.
     * 
     * @param <T>
     * @param matcher
     * @return
     * @see #matchOneOf(Comparable...)
     * @see #oneOf(Object, Object)
     */
    public static <T> F1.ToBoolean<T> matchOneOf(final Comparable<T> matcher1, final Comparable<T> matcher2) {
        return new F1.ToBoolean<T> () {
            public boolean op(T item)
            {
                if(matcher1.compareTo(item)==0 || matcher2.compareTo(item)==0)
                    return true;
                return false;
            }
        };
    }
    
    /**
     * Same as <code>matchOneOf</code>.
     * Inlined version for three elements.
     * 
     * @param <T>
     * @param matcher
     * @return
     * @see #matchOneOf(Comparable...)
     * @see #oneOf(Object, Object, Object)
     */
    public static <T> F1.ToBoolean<T> matchOneOf(final Comparable<T> matcher1, final Comparable<T> matcher2, final Comparable<T> matcher3) {
        return new F1.ToBoolean<T> () {
            public boolean op(T item)
            {
                if(matcher1.compareTo(item)==0 || matcher2.compareTo(item)==0 || matcher3.compareTo(item)==0)
                    return true;
                return false;
            }
        };
    }
    
    public static <T> F1.ToBoolean<T> andChain (final F1.ToBoolean<T> filter) {
        return filter;
    }
    
    private static <T> boolean filterNotNullAndMatch(F1.ToBoolean<T> filter, T value) {
        if(filter==null)
            return false;
        return filter.op(value);
    }
    
    public static <T> F1.ToBoolean<T> andChain (final F1.ToBoolean<T> filterA, final F1.ToBoolean<T> filterB) {
        return new F1.ToBoolean<T> () {
            public boolean op(T param)
            {
                if(filterNotNullAndMatch(filterA,param) && filterNotNullAndMatch(filterB,param))
                    // all have accepted it 
                    return true;
                else
                    return false;
            }
        };
    }
    
    public static <T> F1.ToBoolean<T> andChain (final F1.ToBoolean<T> filterA, final F1.ToBoolean<T> filterB, final F1.ToBoolean<T> filterC) {
        return new F1.ToBoolean<T> () {
            public boolean op(T param)
            {
                if(filterNotNullAndMatch(filterA,param) && filterNotNullAndMatch(filterB,param) && filterNotNullAndMatch(filterC,param))
                    // all have accepted it 
                    return true;
                else
                    return false;
            }
        };
    }
    
    @SuppressWarnings("unchecked")
    public static <T> F1.ToBoolean<T> andChain (final List<F1.ToBoolean<T>> filters) {
        // first try to inline
        int size = filters.size();
        if(size==1)
            return andChain(filters.get(0));
        else if(size==2)
            return andChain(filters.get(0),filters.get(1));
        else if(size==3)
            return andChain(filters.get(0),filters.get(1),filters.get(2));
        
        final F1.ToBoolean<T>[] refs = filters.toArray(new F1.ToBoolean[filters.size()]);
        return new F1.ToBoolean<T> () {
            public boolean op(T param)
            {
                for(F1.ToBoolean<T> sub : refs)
                    if(filterNotNullAndMatch(sub,param)==false)
                        return false;
                
                // all have accepted it 
                return true;
            }
        };
    }
    
    public static <T> F1.ToBoolean<T> andChain (final F1.ToBoolean<T>[] filters) {
        // first try to inline
        if(filters.length==1)
            return andChain(filters[0]);
        else if(filters.length==2)
            return andChain(filters[0],filters[1]);
        else if(filters.length==3)
            return andChain(filters[0],filters[1],filters[2]);
        
        return new F1.ToBoolean<T> () {
            public boolean op(T param)
            {
                for(F1.ToBoolean<T> sub : filters)
                    if(filterNotNullAndMatch(sub,param)==false)
                        return false;
                
                // all have accepted it 
                return true;
            }
        };
    }
    
    public static <T> F1.ToBoolean<T> orChain (final F1.ToBoolean<T> filter) {
        return filter;
    }
    
    public static <T> F1.ToBoolean<T> orChain (final F1.ToBoolean<T> filterA, final F1.ToBoolean<T> filterB) {
        return new F1.ToBoolean<T> () {
            public boolean op(T param)
            {
                if(filterNotNullAndMatch(filterA,param) || filterNotNullAndMatch(filterB,param))
                    // at least one has accepted it 
                    return true;
                else
                    return false;
            }
        };
    }
    
    public static <T> F1.ToBoolean<T> orChain (final F1.ToBoolean<T> filterA, final F1.ToBoolean<T> filterB, final F1.ToBoolean<T> filterC) {
        return new F1.ToBoolean<T> () {
            public boolean op(T param)
            {
                if(filterNotNullAndMatch(filterA,param) || filterNotNullAndMatch(filterB,param) || filterNotNullAndMatch(filterC,param))
                    // at least one has accepted it 
                    return true;
                else
                    return false;
            }
        };
    }
    
    public static <T> F1.ToBoolean<T> orChain (final F1.ToBoolean<T>[] filters) {
        // first try to inline
        if(filters.length==1)
            return orChain(filters[0]);
        else if(filters.length==2)
            return orChain(filters[0],filters[1]);
        else if(filters.length==3)
            return orChain(filters[0],filters[1],filters[2]);
        
        return new F1.ToBoolean<T> () {
            public boolean op(T param)
            {
                for(F1.ToBoolean<T> sub : filters)
                    if(filterNotNullAndMatch(sub,param))
                        // at least one has accepted it 
                        return true;
                return false;
            }
        };
    }
    
    @SuppressWarnings("unchecked")
    public static <T> F1.ToBoolean<T> orChain (final List<F1.ToBoolean<T>> filters) {
        // first try to inline
        int size = filters.size();
        if(size==1)
            return orChain(filters.get(0));
        else if(size==2)
            return orChain(filters.get(0),filters.get(1));
        else if(size==3)
            return orChain(filters.get(0),filters.get(1),filters.get(2));
        
        final F1.ToBoolean<T>[] refs = filters.toArray(new F1.ToBoolean[filters.size()]);
        return new F1.ToBoolean<T> () {
            public boolean op(T param)
            {
                for(F1.ToBoolean<T> sub : refs)
                    if(filterNotNullAndMatch(sub,param))
                        // at least one has accepted it 
                        return true;
                return false;
            }
        };
    }
    
    /**
     * Identity function that return the passed argument without modification.
     * @param <T>
     * @return
     */
    public static <T> F1<T,T> identity()  {
        return new F1<T, T> () {
            public T op(T t) {
                return t;
            }
        };
    }
    
    public static <T> F1.ToBoolean<T> wrap(final C1<T> callback, final boolean returnValue)  {
        return new F1.ToBoolean<T> () {
            public boolean op(T t) {
                callback.op(t);
                return returnValue;
            }
        };
    }
    
    public static <T> F1ToBooleanCollector<T> newF1ToBooleanCollector()  {
        return new F1ToBooleanCollector<T> (true); 
    }
    
    /**
     * Implementation of <code>F1.ToBoolean</code> that add into its internal
     * list all items passed as argument and always return <code>booleanValue</code>.
     * @param <T>
     * @return
     */
    public static class F1ToBooleanCollector<T> implements F1.ToBoolean<T> {
        private final List<T> collected = new ArrayList<T> ();
        private final boolean booleanValue;
        public F1ToBooleanCollector()
        {
            this(true);
        }
        public F1ToBooleanCollector(boolean booleanValue)
        {
            this.booleanValue = booleanValue;
        }
        public boolean op(T val)
        {
            collected.add(val);
            return booleanValue;
        }
        /**
         * Returns the backed list containing all items that have been passed to
         * <code>op(T)</code>.
         * @return
         */
        public List<T> getCollected()
        {
            return collected;
        }
    }
}

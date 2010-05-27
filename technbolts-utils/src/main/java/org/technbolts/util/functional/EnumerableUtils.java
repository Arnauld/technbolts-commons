/* $Id: EnumerableUtils.java,v 1.4 2009-07-23 13:20:18 arnauld Exp $ */
package org.technbolts.util.functional;

import org.technbolts.util.New;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * EnumerableUtils
 * @author <a href="mailto:arnauld.loyer@eptica.com">Loyer Arnauld</a>
 * @version $Revision: 1.4 $
 */
public class EnumerableUtils
{
    
    /**
     * Wrap an array of items into a <code>Container</code>.
     * @param <T>
     * @param items
     * @return
     * @see Container
     */
    public static <T> Container<T> forAll(T...items) {
        return ContainerImpl.wrap (items);
    }
    
    /**
     * Wrap an iterable of items into a <code>Container</code>.
     * @param <T>
     * @param items
     * @return
     * @see Container
     */
    public static <T> Container<T> forAll(Iterable<T> items) {
        return ContainerImpl.wrap (items);
    }
    
    /**
     * Return an array of <code>count</code> lists in which the values of the
     * specified collection are spread, using the round-robin strategy.
     *
     * @param <T>
     * @param values
     * @param count
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T>[] roundRobin (Collection<T> values, int count) {
        List<T>[] elements = new List[count];
        for(int i=0;i<count;i++)
            elements[i] = new ArrayList<T> ((values.size()/count) +1);
        
        int index = 0;
        for(T item : values) {
            elements[index%count].add(item);
            ++index;
        }
        return elements;
    }
    
    
    /**
     * Iterate over all the elements and pass them to the callback.
     * @param <T>
     * @param elements
     * @param func
     */
    public static <T> void forAll(Iterable<T> elements, C1<T> func) {
        if(elements==null)
            return;
        
        for(T element : elements)
            func.op(element);
    }
        
    /**
     * Iterate over all the elements and all items that match the
     * filter are passed to the callback.
     * @param <T>
     * @param elements
     * @param filter
     * @param func
     */
    public static <T> void forAll(Iterable<T> elements, F1.ToBoolean<T> filter, C1<T> func) {
        if(elements==null)
            return;
        
        for(T element : elements)
            if(filter.op(element))
                func.op(element);
    }
    
    /**
     * Scan all items and pass them to the reader.
     * If the reader return <code>false</code> then the scan is stopped and
     * the method returns.
     */
    public static <T> void forAllUntil(Iterable<T> elements, F1.ToBoolean<T> reader) {
        if(elements==null)
            return;
        
        for(T element : elements)
            // do the reader want to stop iteration ?
            if(reader.op(element)==false)
                return;
    }
    
    /**
     * count all items that match the filter.
     */
    public static <T> int countAll(Iterable<T> elements, F1.ToBoolean<T> filter) {
        if(elements==null)
            return 0;
        
        int count = 0;
        for(T element : elements)
            // do the reader want to stop iteration ?
            if(filter.op(element))
                count++;
        return count;
    }
        
    /**
     * Return the first element that match the filter.
     * @param <T>
     * @param elements
     * @param filter
     * @return the first item that match the filter
     */
    public static <T> T selectFirst(Iterable<T> elements, F1.ToBoolean<T> filter) {
        if(elements==null)
            return null;
        
        for(T element : elements) {
            if(filter.op(element))
                return element;
        }
        return null;
    }
    
    /**
     * Return the first transformed element that match the filter.
     * @param <T>
     * @param elements
     * @param filter
     * @return the first item that match the filter
     */
    public static <T,R> R selectFirst(Iterable<T> elements, F1<T,R> transform, F1.ToBoolean<R> filter) {
        if(elements==null)
            return null;
        
        for(T element : elements) {
            R transformed = transform.op(element);
            if(filter.op(transformed))
                return transformed;
        }
        return null;
    }
    
    /**
     * Return the first element that match the filter.
     * @param <T>
     * @param elements
     * @param filter
     * @return the first item that match the filter
     */
    public static <T> T selectFirst(T[] elements, F1.ToBoolean<T> filter) {
        if(elements==null)
            return null;
        
        for(T element : elements) {
            if(filter.op(element))
                return element;
        }
        return null;
    }
    
    /**
     * Return the first transformed element that match the filter.
     * @param <T>
     * @param elements
     * @param filter
     * @return the first item that match the filter
     */
    public static <T,R> R selectFirst(T[] elements, F1<T,R> transform, F1.ToBoolean<R> filter) {
        if(elements==null)
            return null;
        
        for(T element : elements) {
            R transformed = transform.op(element);
            if(filter.op(transformed))
                return transformed;
        }
        return null;
    }
    
    private static boolean isTrue(Boolean bool) {
        return (bool!=null && bool.booleanValue());
    }
        
    /**
     * Return a list containing all items that satisfy the filter.
     * @param <T>
     * @param elements
     * @param filter filter used to filter the elements
     * @return
     */
    public static <T> List<T> selectAll(Iterable<T> elements, F1.ToBoolean<T> filter) {
        List<T> found = new ArrayList<T> ();
        if(elements==null)
            return found;

        for(T element : elements) {
            if(filter.op(element))
                found.add(element);
        }
        return found;
    }
    
    /**
     * Return a list containing all items that satisfy the filter.
     * @param <T>
     * @param elements
     * @param filter filter used to filter the elements
     * @return
     */
    public static <T> List<T> selectAll(T[] elements, F1.ToBoolean<T> filter) {
        List<T> found = new ArrayList<T> ();
        if(elements==null)
            return found;

        for(T element : elements) {
            if(filter.op(element))
                found.add(element);
        }
        return found;
    }
    
    /**
     * Return a list containing all items that satisfy the filter, the items
     * are then transformed using the transformer and then added into the
     * returned list. 
     * 
     * The items are checked against the filter and then transformed.
     * 
     * @param <T>
     * @param <R>
     * @param elements
     * @param filter filter used to filter the elements
     * @param transform transformation used to transform item
     * @return
     */
    public static <T,R> List<R> selectAll(T[] elements, F1.ToBoolean<T> filter, F1<T,R> transform) {
        List<R> found = new ArrayList<R> ();
        if(elements==null)
            return found;

        for(T element : elements) {
            if(filter.op(element))
                found.add(transform.op(element));
        }
        return found;
    }
    
    /**
     * Return a list containing all transformed items that satisfy the filter,
     * the transformed items are then added into the returned list.
     * 
     * The items are transformed and then checked against the filter.
     * 
     * @param <T>
     * @param <R>
     * @param elements
     * @param transform transformation used to transform item
     * @param filter filter used to filter the elements
     * @return
     */
    public static <T,R> List<R> selectAll(T[] elements, F1<T,R> transform, F1.ToBoolean<R> filter) {
        List<R> found = new ArrayList<R> ();
        if(elements==null)
            return found;
        
        for(T element : elements) {
            R transformed = transform.op(element);
            Boolean accepted = filter.op(transformed);
            if(isTrue(accepted))
                found.add(transformed);
        }
        return found;
    }
    
    /**
     * Return a list containing all items that satisfy the filter, the items
     * are then transformed using the transformer and then added into the
     * returned list. 
     * 
     * The items are checked against the filter and then transformed.
     * 
     * @param <T>
     * @param <R>
     * @param elements
     * @param filter filter used to filter the elements
     * @param transform transformation used to transform item
     * @return
     */
    public static <T,R> List<R> selectAll(Iterable<T> elements, F1.ToBoolean<T> filter, F1<T,R> transform) {
        List<R> found = new ArrayList<R> ();
        if(elements==null)
            return found;

        for(T element : elements) {
            if(filter.op(element))
                found.add(transform.op(element));
        }
        return found;
    }
    
    /**
     * Return a list containing all transformed items that satisfy the filter,
     * the transformed items are then added into the returned list.
     * 
     * The items are transformed and then checked against the filter.
     * 
     * @param <T>
     * @param <R>
     * @param elements
     * @param transform transformation used to transform item
     * @param filter filter used to filter the elements
     * @return
     */
    public static <T,R> List<R> selectAll(Iterable<T> elements, F1<T,R> transform, F1.ToBoolean<R> filter) {
        List<R> found = new ArrayList<R> ();
        if(elements==null)
            return found;
        
        for(T element : elements) {
            R transformed = transform.op(element);
            Boolean accepted = filter.op(transformed);
            if(isTrue(accepted))
                found.add(transformed);
        }
        return found;
    }
    
    /**
     * Return a list containing all the transformed item.
     * @param <T>
     * @param <R>
     * @param elements
     * @param transform
     * @return
     */
    public static <T,R> List<R> transformAll(Iterable<T> elements, F1<T,R> transform) {
        return transformAll(elements, transform, false);
    }
    
    /**
     * Return a list containing all the transformed item.
     * @param <T>
     * @param <R>
     * @param elements
     * @param transform
     * @return
     */
    public static <T,R> List<R> transformAll(Iterable<T> elements, F1<T,R> transform, boolean skipNull) {
        List<R> found = new ArrayList<R> ();
        if(elements==null)
            return found;
        
        for(T element : elements) {
            R transformed = transform.op(element);
            if(transformed!=null || skipNull==false)
                found.add(transformed);
        }
        return found;
    }
    
    /**
     * Return a list containing all the transformed item.
     * @param <T>
     * @param <R>
     * @param elements
     * @param transform
     * @return
     */
    public static <T,R> List<R> transformAll(T[] elements, F1<T,R> transform) {
        return transformAll(elements, transform, false);
    }
    
    /**
     * Return a list containing all the transformed item.
     * @param <T>
     * @param <R>
     * @param elements
     * @param transform
     * @return
     */
    public static <T,R> List<R> transformAll(T[] elements, F1<T,R> transform, boolean skipNull) {
        List<R> found = new ArrayList<R> ();
        if(elements==null)
            return found;
        
        for(T element : elements) {
            R transformed = transform.op(element);
            if(transformed!=null || skipNull==false)
                found.add(transformed);
        }
        return found;
    }
    
    /**
     * Return the item considered as the min element according to the
     * <code>Comparable</code> contract.
     * @param <T>
     * @param elements
     * @return
     * @see Comparable
     */
    public static <T extends Comparable<T>> T selectMin(Iterable<T> elements) {
        if(elements==null)
            return null;
        
        T minimum = null;
        for(T element : elements) {
            if(minimum==null || minimum.compareTo(element)>0)
                minimum = element;
        }
        return minimum;
    }
    
    /**
     * Return the item considered as the min element according to the
     * <code>Comparable</code> contract.
     * @param <T>
     * @param elements
     * @param comparator comparator used for comparing elements
     * @return
     * @see Comparable
     */
    public static <T> T selectMin(Comparator<T> comparator, Iterable<T> elements) {
        if(elements==null)
            return null;
        
        T minimum = null;
        for(T element : elements) {
            if(minimum==null || comparator.compare(minimum,element)>0)
                minimum = element;
        }
        return minimum;
    }
    
    /**
     * Return the item considered as the max element according to the
     * <code>Comparable</code> contract.
     * @param <T>
     * @param elements
     * @return
     * @see Comparable
     */
    public static <T extends Comparable<T>> T selectMax(Iterable<T> elements) {
        if(elements==null)
            return null;
        
        T minimum = null;
        for(T element : elements) {
            if(minimum==null || minimum.compareTo(element)<0)
                minimum = element;
        }
        return minimum;
    }
    
    /**
     * Return the item considered as the max element according to the
     * <code>Comparable</code> contract.
     * @param <T>
     * @param comparator comparator used for comparing elements
     * @param elements
     * @return
     * @see Comparable
     */
    public static <T extends Comparable<T>> T selectMax(Comparator<T> comparator, Iterable<T> elements) {
        if(elements==null)
            return null;
        
        T minimum = null;
        for(T element : elements) {
            if(minimum==null || comparator.compare(minimum,element)<0)
                minimum = element;
        }
        return minimum;
    }
    
    /**
     * Return the item considered as the min element according to the
     * <code>Comparable</code> contract.
     * @param <T>
     * @param elements
     * @return
     * @see Comparable
     */
    public static <T extends Comparable<T>> T selectMin(T... elements) {
        if(elements==null)
            return null;
        
        T minimum = null;
        for(T element : elements) {
            if(minimum==null || minimum.compareTo(element)>0)
                minimum = element;
        }
        return minimum;
    }
    
    /**
     * Return the item considered as the min element according to the
     * <code>Comparable</code> contract.
     * @param <T>
     * @param comparator comparator used for comparing elements
     * @param elements
     * @return
     * @see Comparable
     */
    public static <T extends Comparable<T>> T selectMin(Comparator<T> comparator, T... elements) {
        if(elements==null)
            return null;
        
        T minimum = null;
        for(T element : elements) {
            if(minimum==null || comparator.compare(minimum,element)>0)
                minimum = element;
        }
        return minimum;
    }
    
    /**
     * Return the item considered as the max element according to the
     * <code>Comparable</code> contract.
     * @param <T>
     * @param elements
     * @return
     * @see Comparable
     */
    public static <T extends Comparable<T>> T selectMax(T... elements) {
        if(elements==null)
            return null;
        
        T minimum = null;
        for(T element : elements) {
            if(minimum==null || minimum.compareTo(element)<0)
                minimum = element;
        }
        return minimum;
    }
    
    /**
     * Return the item considered as the max element according to the
     * <code>Comparable</code> contract.
     * @param <T>
     * @param comparator comparator used for comparing elements
     * @param elements
     * @return
     * @see Comparable
     */
    public static <T extends Comparable<T>> T selectMax(Comparator<T> comparator, T... elements) {
        if(elements==null)
            return null;
        
        T minimum = null;
        for(T element : elements) {
            if(minimum==null || minimum.compareTo(element)<0)
                minimum = element;
        }
        return minimum;
    }
    
    /**
     * Return the transformed item considered as the min element according to the
     * <code>Comparable</code> contract.
     * @param <T>
     * @param elements
     * @param transform transformation to transform the element before comparing them.
     * @return
     * @see Comparable
     */
    public static <T,R extends Comparable<R>> R selectMin(Iterable<T> elements, F1<T,R> transform) {
        if(elements==null)
            return null;
        
        R minimum = null;
        for(T element : elements) {
            R next = transform.op(element);
            if(minimum==null || minimum.compareTo(next)>0)
                minimum = next;
        }
        return minimum;
    }
    
    /**
     * Return the transformed item considered as the max element according to the
     * <code>Comparable</code> contract.
     * @param <T>
     * @param elements
     * @param transform transformation to transform the element before comparing them.
     * @return
     * @see Comparable
     */
    public static <T,R extends Comparable<R>> R selectMax(Iterable<T> elements, F1<T,R> transform) {
        if(elements==null)
            return null;
        
        R minimum = null;
        for(T element : elements) {
            R next = transform.op(element);
            if(minimum==null || minimum.compareTo(next)<0)
                minimum = next;
        }
        return minimum;
    }
    
    /**
     * Return the transformed item considered as the min element according to the
     * <code>Comparable</code> contract.
     * @param <T>
     * @param elements
     * @param transform transformation to transform the element before comparing them.
     * @return
     * @see Comparable
     */
    public static <T,R extends Comparable<R>> R selectMin(T[] elements, F1<T,R> transform) {
        if(elements==null)
            return null;
        
        R minimum = null;
        for(T element : elements) {
            R next = transform.op(element);
            if(minimum==null || minimum.compareTo(next)>0)
                minimum = next;
        }
        return minimum;
    }
    
    /**
     * Return the transformed item considered as the max element according to the
     * <code>Comparable</code> contract.
     * @param <T>
     * @param elements
     * @param transform transformation to transform the element before comparing them.
     * @return
     * @see Comparable
     */
    public static <T,R extends Comparable<R>> R selectMax(T[] elements, F1<T,R> transform) {
        if(elements==null)
            return null;
        
        R minimum = null;
        for(T element : elements) {
            R next = transform.op(element);
            if(minimum==null || minimum.compareTo(next)<0)
                minimum = next;
        }
        return minimum;
    }
    
    /**
     * Sort the list using the function as <code>Comparator</code>.
     * @param <T>
     * @param elements
     * @param func
     * @see java.util.Comparator
     */
    public static <T> void sort(List<T> elements, final F1<T,Integer> func) {
        if(elements==null)
            return ;
        
        Collections.sort(elements, new Comparator<T> () {
            public int compare(T t1, T t2)
            {
                int val1 = func.op(t1);
                int val2 = func.op(t2);
                return val1-val2;
            }
        });
    }
    
    /**
     * Combines the elements of this iterable object together using the binary
     * function f, from left to right, and starting with the value initialValue.
     * @param <T>
     * @param <R>
     * @param elements
     * @param initialValue
     * @return <tt>f(... (f(f(initialValue, a0), a1) ...), an) if the list is [a0, a1, ..., an].</tt>
     */
    public static <T,R> R foldLeft(List<T> elements, final F2<R,T,R> f, R initialValue) {
        if(elements==null)
            return initialValue;
        
        R previous = initialValue;
        for(T element : elements) {
            previous = f.op(previous, element);
        }
        return previous;
    }

    /**
     * Iterate over all the elements to create a list.
     * @param <T>
     * @param elements
     * @return
     */
    public static <T> List<T> toList(Iterable<T> elements)
    {
        List<T> list = New.arrayList ();
        if(elements==null)
            return list;
        for(T t : elements)
            list.add(t);
        return list;
    }
    
    /**
     * Iterate over all the elements to create a list.
     * @param <T>
     * @param elements
     * @return
     */
    public static <T> List<T> toList(T[] elements)
    {
        List<T> list = New.arrayList ();
        if(elements==null)
            return list;
        for(T t : elements)
            list.add(t);
        return list;
    }
    
    /**
     * @param <T>
     * @param elements
     * @return
     */
    public static <T> T[] toArray(T...elements)
    {
        return elements;
    }
    
    /**
     * Iterate over all the elements to create a list that is then sorted using
     * the comparator.
     * @param <T>
     * @param data
     * @param comparator
     * @return
     */
    public static <T> List<T> sort(Iterable<T> data, Comparator<T> comparator)
    {
        List<T> list = toList(data);
        Collections.sort(list, comparator);
        return list;
    }
}

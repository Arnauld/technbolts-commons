/* $Id$ */
package org.technbolts.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class contains static methods to construct commonly used generic objects
 * such as ArrayList, HashMap or Vector.
 * This helps to make usage of generics collection easier.
 * 
 * @version $Revision$
 */
public class New
{
    /**
     * Create a new ConcurrentHashMap.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @return
     */
    public static <K, V> ConcurrentHashMap<K, V> concurrentHashMap() {
        ConcurrentHashMap<K, V> map = new ConcurrentHashMap<K, V>();
        return map;
    }
    
    /**
     * Create a new HashMap initiated with the <code>(key, value)<code> pair.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param key 
     * @param value
     * @return
     */
    public static <K, V> HashMap<K, V> hashMap(K key, V value) {
        HashMap<K, V> map = new HashMap<K, V>();
        map.put(key, value);
        return map;
    }
    
    /**
     * Create a new HashMap initiated with the specified map.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @return
     */
    public static <K, V> HashMap<K, V> hashMap(Map<? extends K,? extends V> other) {
        HashMap<K, V> map = new HashMap<K, V>();
        map.putAll(other);
        return map;
    }
    
    /**
     * Create a new HashMap.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @return the object
     */
    public static <K, V> HashMap<K, V> hashMap() {
        return new HashMap<K, V>();
    }

    /**
     * Create a new HashMap.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param initialCapacity the initial capacity
     * @return the object
     */
    public static <K, V> HashMap<K, V> hashMap(int initialCapacity) {
        return new HashMap<K, V>(initialCapacity);
    }

    /**
     * Create a new HashSet.
     *
     * @param <T> the type
     * @return the object
     */
    public static <T> HashSet<T> hashSet() {
        return new HashSet<T>();
    }

    /**
     * Create a new HashSet.
     *
     * @param <T> the type
     * @param c the collection
     * @return the object
     */
    public static <T> HashSet<T> hashSet(Collection<T> c) {
        if(c==null)
            return hashSet();
        else
            return new HashSet<T>(c);
    }
    
    /**
     * Create a new HashSet.
     * <p>This method provides a convenient way to create new HashSet
     * initialized to contain several elements.</p>
     * @param <T>
     * @param items
     * @return
     */
    public static <T> HashSet<T> hashSet(T...items) {
        return (HashSet<T>)addAll( new HashSet<T>(), items);
    }
    
    /**
     * Create a new ArrayList.
     *
     * @param <T> the type
     * @return the object
     */
    public static <T> ArrayList<T> arrayList() {
        return new ArrayList<T>();
    }

    /**
     * Create a new ArrayList.
     *
     * @param <T> the type
     * @param c the collection
     * @return the object
     */
    public static <T> ArrayList<T> arrayList(Collection<T> c) {
        if(c==null)
            return arrayList();
        else
            return new ArrayList<T>(c);
    }

    /**
     * Create a new ArrayList.
     *
     * @param <T> the type
     * @param initialCapacity the initial capacity
     * @return the object
     */
    public static <T> ArrayList<T> arrayList(int initialCapacity) {
        return new ArrayList<T>(initialCapacity);
    }
    
    /**
     * Create a new ArrayList.
     * <p>This method provides a convenient way to create new ArrayList
     * initialized to contain several elements.</p>
     *
     * @param <T> the type
     * @param items the array by which the list will be backed.
     * @return the object
     */
    public static <T> ArrayList<T> arrayList(T...items) {
        return (ArrayList<T>)addAll( new ArrayList<T>(), items);
    }
    
    /**
     * Create a new Vector.
     *
     * @param <T> the type
     * @return the object
     */
    public static <T> Vector<T> vector() {
        return new Vector<T>();
    }

    /**
     * Create a new Vector.
     *
     * @param <T> the type
     * @param c the collection
     * @return the object
     */
    public static <T> Vector<T> vector(Collection<T> c) {
        if(c==null)
            return vector ();
        else
            return new Vector<T>(c);
    }

    /**
     * Create a new Vector.
     *
     * @param <T> the type
     * @param initialCapacity the initial capacity
     * @return the object
     */
    public static <T> Vector<T> vector(int initialCapacity) {
        return new Vector<T>(initialCapacity);
    }
    
    /**
     * Create a new Vector.
     * <p>This method provides a convenient way to create new Vector
     * initialized to contain several elements.</p>
     *
     * @param <T> the type
     * @param items the array by which the list will be backed.
     * @return the object
     */
    public static <T> Vector<T> vector(T...items) {
        return (Vector<T>)addAll( new Vector<T>(), items);
    }
    
    private static final <T> Collection<T> addAll(Collection<T> out, T...items) {
        for(T t : items)
            out.add(t);
        return out;
    }
    
    /**
     * Create a new <code>int[]</code> with the <code>size</code> capacity and
     * filled with <code>initialValue</code> as initial value.
     * @param size size of the array
     * @param initialValue initial value in the array
     * @return
     */
    public static int[] intArray(int size, int initialValue) {
       int[] values = new int[size];
       Arrays.fill(values, initialValue);
       return values;
    }
}

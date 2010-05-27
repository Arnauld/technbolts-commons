/* $Id$ */
package org.technbolts.util.functional;

import java.util.List;

/**
 * Container. Utility interface to simply operation performed on list or array.
 * Several implementation allow to parallelize some process if required,
 * simple implementation allow to iterate over all elements 
 * (<code>apply(C1<T>)</code>), 
 * over a filtered view (<code>filter(F1.ToBoolean<T>)</code>) of the elements or 
 * over a transformed view (<code>transform(F1<T,R>)</code>) of the elements.
 * 
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 * @param <T>
 */
public interface Container<T>
{
    /**
     * Return a filtered view of elements. One iterating over the filtered view, only
     * the filtered elements are passed to the callback.
     * 
     * @param filter
     * @return
     */
    Container<T> filter   (F1.ToBoolean<T> filter);
    
    /**
     * Return a transformed view of elements. One iterating over the transformed view,
     * the transformed elements are passed to the callback.
     * 
     * @param filter
     * @return
     */
    <R> Container<R> transform(F1<T,R> transform);
    
    /**
     * Return a list containing all the elements contained in the container.
     * @return
     */
    List<T> toList ();
    
    
    /**
     * Apply the callback for the elements contained in the container.
     * In order to stop the iteration when needed, use the <code>applyUntil(F1.ToBoolean)</code>
     * method instead.
     * @param callback
     */
    void apply(C1<T> callback);
    
    /**
     * Apply the callback for the elements contained in the container.
     * If the callback returns <code>true</code> the iteration continue
     * otherwise the iteration is stoped.
     * @param stopableCallback
     */
    void applyUntil(F1.ToBoolean<T> stopableCallback);
}

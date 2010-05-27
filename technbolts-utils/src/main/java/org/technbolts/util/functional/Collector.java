/* $Id: Collector.java,v 1.1 2009-07-22 12:48:34 arnauld Exp $ */
package org.technbolts.util.functional;

public interface Collector<T,R>
{
    public interface Factory<T,R> {
        Collector<T,R> newCollector ();
    }
    
    boolean collect(T item);
    R getCollected ();
}

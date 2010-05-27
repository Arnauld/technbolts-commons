/* $Id: Reducer.java,v 1.1 2009-07-22 12:48:34 arnauld Exp $ */
package org.technbolts.util.functional;

public interface Reducer<T>
{
    T reduce(T a, T b);
}

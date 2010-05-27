/* $Id: T2.java,v 1.1 2009-07-02 14:26:25 arnauld Exp $ */
package org.technbolts.util.functional;

/**
 * T2 tuple of two values.
 * 
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision: 1.1 $
 * @param <TYPE1>
 * @param <TYPE2>
 */
public interface T2<TYPE1,TYPE2> extends T1<TYPE1>
{
    TYPE2 getV2();
    void setV2(TYPE2 value);
}

/* $Id: T1.java,v 1.1 2009-07-02 14:26:25 arnauld Exp $ */
package org.technbolts.util.functional;

/**
 * T1 tuple of one value.
 * 
 * @author <a href="mailto:arnauld.loyer@eptica.com">Loyer Arnauld</a>
 * @version $Revision: 1.1 $
 * @param <TYPE>
 */
public interface T1<TYPE>
{
    TYPE getV1();
    void setV1(TYPE value);
}

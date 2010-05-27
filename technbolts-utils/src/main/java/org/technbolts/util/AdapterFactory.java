/* $Id$ */
package org.technbolts.util;

/**
 * AdapterFactory
 * @version $Revision$
 */
public interface AdapterFactory
{
    
    boolean isTypeSupported (Class<?> type);
    
    <T> T createAdapter(Object adaptee, Class<T> type);
}

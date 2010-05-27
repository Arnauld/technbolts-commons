/* $Id$ */
package org.technbolts.util;

public class AdaptableUtils
{
    /**
     * Attempt to adapt <code>o</code> into the specified adapter.
     * It first checks if <code>o</code> is not <code>null</code> and
     * an instance of <code>Adaptable</code>. Then it calls <code>getAdapter</code>
     * on it. Otherwise it simply returns.
     * 
     * @param <T>
     * @param o
     * @param adapter
     * @return
     */
    public static <T> T getAdapter(Object o, Class<T> adapter) {
        if(o==null)
            return null;
        else
        if(o instanceof Adaptable)
            return ((Adaptable)o).getAdapter(adapter);
        return null;
    }
    
    public static <T> T createAdapter(Object adaptee, Class<T> adapter, AdapterFactory factory) {
        if(factory==null)
            return null;
        
        else if(factory.isTypeSupported(adapter))
            return factory.createAdapter(adaptee, adapter);
        else
            return null;
    }
}

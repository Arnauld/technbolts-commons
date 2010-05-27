/* $Id$ */
package org.technbolts.util.functional;

/**
 * T1Impl
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 * @param <TYPE>
 */
public class T1Impl<TYPE> implements T1<TYPE>
{
    private TYPE value;
    
    public T1Impl()
    {
    }
    
    public T1Impl(TYPE value)
    {
        setV1(value);
    }
    
    public TYPE getV1()
    {
        return value;
    }
    
    public void setV1(TYPE value)
    {
        this.value = value;
    }
}

/* $Id$ */
package org.technbolts.util.functional;

/**
 * T2Impl
 * @author <a href="mailto:arnauld.loyer@eptica.com">Loyer Arnauld</a>
 * @version $Revision$
 * @param <TYPE1>
 * @param <TYPE2>
 */
public class T2Impl<TYPE1,TYPE2> extends T1Impl<TYPE1> implements  T2<TYPE1,TYPE2>
{
    public static <TYPE1,TYPE2> T2<TYPE1,TYPE2> t2(TYPE1 value1, TYPE2 value2) {
        return new T2Impl<TYPE1, TYPE2> (value1,value2);
    }
    
    private TYPE2 value2;
    
    public T2Impl()
    {
    }
    
    public T2Impl(TYPE1 value1, TYPE2 value2)
    {
        super(value1);
        setV2(value2);
    }
    
    public TYPE2 getV2()
    {
        return value2;
    }
    public void setV2(TYPE2 value2)
    {
        this.value2 = value2;
    }
}

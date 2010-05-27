/* $Id: C3.java,v 1.1 2009-07-22 12:48:34 arnauld Exp $ */
package org.technbolts.util.functional;

/**
 * Utility interface to define a procedure/callback taking three arguments one of type 
 * <code>PARAM1</code>, one of type <code>PARAM2</code> and the last of type <code>PARAM3</code>. 
 * 
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision: 1.1 $
 * @param <PARAM1>
 * @param <PARAM2>
 * @param <PARAM3>
 */
public interface C3<PARAM1,PARAM2,PARAM3>
{
    public interface Factory<PARAM1,PARAM2,PARAM3> { C3<PARAM1,PARAM2,PARAM3> newFunction (); }
    
    public void op(PARAM1 val1, PARAM2 val2, PARAM3 val3);
}

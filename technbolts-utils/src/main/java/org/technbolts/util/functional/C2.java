/* $Id: C2.java,v 1.2 2009-07-22 12:48:34 arnauld Exp $ */
package org.technbolts.util.functional;

/**
 * Utility interface to define a procedure/callback taking two arguments one of type 
 * <code>PARAM1</code> and the other of type <code>PARAM2</code>.
 * 
 * @author <a href="mailto:arnauld.loyer@eptica.com">Loyer Arnauld</a>
 * @version $Revision: 1.2 $
 * @param <PARAM1>
 * @param <PARAM2>
 */
public interface C2<PARAM1,PARAM2>
{
    public interface Factory<PARAM1,PARAM2> { C2<PARAM1,PARAM2> newFunction (); }
    
    public void op(PARAM1 val1, PARAM2 val2);
}

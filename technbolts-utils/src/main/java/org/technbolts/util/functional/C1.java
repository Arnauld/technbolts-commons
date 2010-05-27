/* $Id: C1.java,v 1.2 2009-07-22 12:48:34 arnauld Exp $ */
package org.technbolts.util.functional;


/**
 * Utility interface to define a procedure/callback taking one argument of type <code>PARAM</code>.
 * 
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision: 1.2 $
 * @param <PARAM>
 */
public interface C1<PARAM>
{
    public interface Factory<PARAM> { C1<PARAM> newFunction (); }
    
    public void op(PARAM val);
}

/* $Id: C0.java,v 1.1 2009-07-22 12:48:34 arnauld Exp $ */
package org.technbolts.util.functional;

/**
 * Utility interface to define a procedure/callback taking no argument.
 * 
 * @author <a href="mailto:arnauld.loyer@eptica.com">Loyer Arnauld</a>
 * @version $Revision: 1.1 $
 * @param <PARAM>
 */
public interface C0
{
    public interface Factory<PARAM> { C0 newFunction (); }
    
    void op ();
}

/* $Id: F0.java,v 1.1 2009-07-22 12:48:34 arnauld Exp $ */
package org.technbolts.util.functional;

/**
 * Utility interface to define a function taking no argument
 * and returning a result of type <code>RESULT</code>.
 * 
 * @author <a href="mailto:arnauld.loyer@eptica.com">Loyer Arnauld</a>
 * @version $Revision: 1.1 $
 * @param <RESULT> type of the function result
 */
public interface F0<RESULT>
{
    RESULT op();
    
    public interface ToBoolean {
        public interface Factory { ToBoolean newFunction (); }
        boolean op();
    }
}

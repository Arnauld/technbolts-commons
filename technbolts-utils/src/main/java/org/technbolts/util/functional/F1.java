/* $Id: F1.java,v 1.2 2009-07-22 12:48:34 arnauld Exp $ */
package org.technbolts.util.functional;

/**
 * Utility interface to define a function taking one argument of type <code>PARAM</code>
 * and returning a result of type <code>RESULT</code>.
 * 
 * @author <a href="mailto:arnauld.loyer@eptica.com">Loyer Arnauld</a>
 * @version $Revision: 1.2 $
 * @param <PARAM> type of the function parameter
 * @param <RESULT> type of the function result
 */
public interface F1<PARAM,RESULT>
{
    public interface Factory<PARAM,RESULT> { F1<PARAM,RESULT> newFunction (); }
    
    public RESULT op(PARAM t);
    
    public interface ToBoolean<PARAM> {
        public interface Factory<PARAM> { ToBoolean<PARAM> newFunction (); }
        boolean op(PARAM param);
    }
    public interface ToInt<PARAM>     { 
        public interface Factory<PARAM> { ToInt<PARAM> newFunction (); }
        int     op(PARAM param); 
    }
    public interface ToLong<PARAM>    {
        public interface Factory<PARAM> { ToLong<PARAM> newFunction (); }
        long    op(PARAM param); 
    }
    public interface ToFloat<PARAM>   { 
        public interface Factory<PARAM> { ToFloat<PARAM> newFunction (); }
        float   op(PARAM param); 
    }
    public interface ToDouble<PARAM>  { 
        public interface Factory<PARAM> { ToDouble<PARAM> newFunction (); }
        double  op(PARAM param); 
    }
}

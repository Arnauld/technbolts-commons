/* $Id: F1.java,v 1.2 2009-07-22 12:48:34 arnauld Exp $ */
package org.technbolts.util.functional;

/**
 * Utility interface to define a function taking one argument of type <code>PARAM1</code>,
 * one argument if type <code>PARAM2</code> and returning a result of type <code>RESULT</code>.
 * 
 * @author <a href="mailto:arnauld.loyer@eptica.com">Loyer Arnauld</a>
 * @version $Revision: 1.2 $
 * @param <PARAM1> type of the function first parameter
 * @param <PARAM2> type of the function second parameter
 * @param <RESULT> type of the function result
 */
public interface F2<PARAM1,PARAM2,RESULT>
{
    public interface Factory<PARAM1,PARAM2,RESULT> { F2<PARAM1,PARAM2,RESULT> newFunction (); }
    
    public RESULT op(PARAM1 t1, PARAM2 t2);
    
    public interface ToBoolean<PARAM1,PARAM2> {
        public interface Factory<PARAM1,PARAM2> { ToBoolean<PARAM1,PARAM2> newFunction (); }
        boolean op(PARAM1 t1, PARAM2 t2);
    }
    public interface ToInt<PARAM1,PARAM2>    { 
        public interface Factory<PARAM1,PARAM2> { ToInt<PARAM1,PARAM2> newFunction (); }
        int     op(PARAM1 t1, PARAM2 t2); 
    }
    public interface ToLong<PARAM1,PARAM2>    {
        public interface Factory<PARAM1,PARAM2> { ToLong<PARAM1,PARAM2> newFunction (); }
        long    op(PARAM1 t1, PARAM2 t2); 
    }
    public interface ToFloat<PARAM1,PARAM2>   { 
        public interface Factory<PARAM1,PARAM2> { ToFloat<PARAM1,PARAM2> newFunction (); }
        float   op(PARAM1 t1, PARAM2 t2); 
    }
    public interface ToDouble<PARAM1,PARAM2>  { 
        public interface Factory<PARAM1,PARAM2> { ToDouble<PARAM1,PARAM2> newFunction (); }
        double  op(PARAM1 t1, PARAM2 t2); 
    }

}

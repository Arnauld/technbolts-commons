/* $Id: FunctionUtils.java,v 1.3 2009-07-22 17:08:07 arnauld Exp $ */
package org.technbolts.util.functional;

import java.util.concurrent.Callable;

/**
 * FuntionUtils
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision: 1.3 $
 */
public class FunctionUtils
{
    
    public static <P,R> F1.ToBoolean<P> toF1Boolean (final Collector<P,R> collector) {
        return new F1.ToBoolean<P> () {
            public boolean op(P value)
            {
                return collector.collect(value);
            }
        };
    }
    
    public static <P,R> F1<P,R> toFunction (final C1<P> callback, final R result) {
        return new F1<P, R> () {
            public R op(P value)
            {
                callback.op(value);
                return result;
            }
        };
    }
    
    /**
     * Return a function that transform the parameter <code>P</code> of the
     * function into a callable.
     * The callable will invoke the <code>callback</code> with the parameter
     * <code>P</code> and then return the result <code>R</code>.
     * 
     * @param <P> type of the parameter of the callback
     * @param <R> type of the result of the <code>Callable</code>
     * @param callback callback called by the <code>Callable</code> built
     * @param result result of the <code>Callable</code> built
     * @return
     * @see #toCallable(C1, Object, Object)
     */
    public static <P,R> F1<P,Callable<R>> toCallableTransformation (final C1<P> callback, final R result) {
        return new F1<P, Callable<R>> () {
            public Callable<R> op(P param)
            {
                return toCallable(callback, param, result);
            }
        };
    }
    
    /**
     * Transform a callback into a Callable. <code>Callable#call()</code> delegates
     * to <code>callback.op(param)</code> and return <code>result</code> as result.
     * @param <P>
     * @param <R>
     * @param callback
     * @param param parameter that will be passed to the callback when the <code>Callable#call()</code>
     *    will be called.
     * @param result result that will be returned by <code>Callable#call()</code>
     * @return
     */
    public static <P,R> Callable<R> toCallable (final C1<P> callback, final P param, final R result) {
        return toCallable(toFunction(callback,result), param);
    }
    
    /**
     * Create a <code>Callable</code> whose method <code>call()</code> delegates
     * to <code>callback.op(param)</code>
     * @param <P>
     * @param <R>
     * @param function function that will be invoked by the <code>Callable</code>
     *  with the parameter <code>param</code>
     * @param param parameter that will be passed to the callback when the <code>Callable#call()</code>
     *    will be called.
     * @return
     */
    public static <P,R> Callable<R> toCallable (final F1<P,R> function, final P param) {
        return new Callable<R> () {
            public R call() throws Exception
            {
                return function.op(param);
            }
        };
    }
    
}

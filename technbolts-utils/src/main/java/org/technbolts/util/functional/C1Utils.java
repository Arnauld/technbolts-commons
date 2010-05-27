/* $Id$ */
package org.technbolts.util.functional;

import java.util.ArrayList;
import java.util.List;

/**
 * C1Utils
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class C1Utils
{
    
    /**
     * Return a function that take a callback as parameter and repeately call
     * it <code>n</code> times, and each times give him the current index in iteration.
     * 
     * <pre>
     * times(3).op(new C1<Integer>() {
     *      public void op(Integer index) {
     *          System.out.println("#"+index);
     *      }
     * });
     * </pre>
     * should display
     * <pre>
     *  #0
     *  #1
     *  #2
     * </pre>
     * 
     * @param times
     * @return
     */
    public static C1<C1<Integer>> times(final int n) {
        return new C1<C1<Integer>> () {
            public void op(C1<Integer> callback)
            {
                for(int i=0;i<n;i++)
                    callback.op(i);
            }
        };
    }
    
    /**
     * Return a callback that delegates to the specified callback all arguments
     * that fullfills the filter.
     * @param <T>
     * @param callback
     * @param filter
     * @return
     */
    public static <T> C1<T> filteredCallback (final  C1<T> callback, final F1.ToBoolean<T> filter) {
        return new C1<T> () {
            public void op(T val)
            {
                if(filter.op(val))
                    callback.op(val);
            }
        };
    }
    
    /**
     * Return a callback that delegates to the specified callback all transformed
     * arguments.
     * @param <T>
     * @param callback
     * @param transform
     * @return
     */
    public static <T,R> C1<T> transformedCallback (final  C1<R> callback, final F1<T,R> transform) {
        return new C1<T> () {
            public void op(T val)
            {
                callback.op(transform.op(val));
            }
        };
    }
    
    /**
     * Return a new instance of <code>C1Collector</code> that add into its internal
     * list all items passed as argument.
     * @param <T>
     * @return
     */
    public static <T> C1Collector<T> newC1Collector () {
        return new C1Collector<T> ();
    }
    
    /**
     * Implementation of <code>C1</code> that add into its internal
     * list all items passed as argument.
     * @param <T>
     * @return
     */
    public static class C1Collector<T> implements C1<T> {
        private List<T> collected = new ArrayList<T> ();
        public void op(T val)
        {
            collected.add(val);
        }
        /**
         * Returns the backed list containing all items that have been passed to
         * <code>op(T)</code>.
         * @return
         */
        public List<T> getCollected()
        {
            return collected;
        }
    }
}

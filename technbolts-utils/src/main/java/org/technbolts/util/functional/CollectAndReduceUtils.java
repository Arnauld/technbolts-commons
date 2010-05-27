/* $Id: CollectAndReduceUtils.java,v 1.1 2009-07-22 12:48:34 arnauld Exp $ */
package org.technbolts.util.functional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CollectAndReduceUtils
{
    public static <T,R> Collector<T,R> toCollector(C1<T> callback, R result) {
        return new C1Collector<T,R>(callback, result);
    }
    
    public static class ListMerger<T> implements Reducer<List<T>> {
        public List<T> reduce(List<T> a, List<T> b)
        {
            if(a==null)
                return b;
            if(b==null)
                return a;
            // create a new list in case of passed list are not mutable
            List<T> reduced = new ArrayList<T> (a.size()+b.size());
            reduced.addAll(a);
            reduced.addAll(b);
            return reduced;
        }
    }
    
    public static class ListCollector<T> implements Collector<T, List<T>> {
        private final List<T> items;
        private final F1.ToBoolean<T> filter;
        public ListCollector(F1.ToBoolean<T> filter) {
            this.filter = filter;
            this.items  = new ArrayList<T> ();
        }
        public List<T> getCollected() {
            return items;
        }
        public boolean collect(T item) {
            if(filter.op(item))
                items.add(item);
            return true;
        }
    }
    
    public static class ListAndTransformCollector<T,R> implements Collector<T, List<R>> {
        private final List<R> items;
        private final F1.ToBoolean<T> filter;
        private final F1<T,R> transformer;
        public ListAndTransformCollector(F1.ToBoolean<T> filter, F1<T,R> transformer) {
            this.filter = filter;
            this.transformer = transformer;
            this.items  = new ArrayList<R> ();
        }
        public List<R> getCollected() {
            return items;
        }
        public boolean collect(T item) {
            if(filter.op(item))
            {
                items.add(transformer.op(item));
            }
            return true;
        }
    }
    
    private static class C1Collector<T, R> implements Collector<T, R> {
        private final C1<T> callback;
        private final  R result;
        C1Collector(C1<T> callback, R result) {
            this.callback = callback;
            this.result   = result;
        }
        public R getCollected() {
            return result;
        }
        public boolean collect(T item) {
            callback.op(item);
            return true;
        }
    }
    
    public static <T> Collector<T,Integer> counter(final F1.ToBoolean<T> filter, boolean shouldStopOnFilterRejection) {
        return new Counter<T>(shouldStopOnFilterRejection) {
            @Override
            protected boolean value(T item)
            {
                return filter.op(item);
            }
        };
    }
    
    public static Reducer<Integer> addIntegerReducer() {
        return new Reducer<Integer> () {
            public Integer reduce(Integer a, Integer b)
            {
                if(a==null)
                    return b;
                else if(b==null)
                    return a;
                else
                    return Integer.valueOf(a.intValue()+b.intValue());
            }
        };
    }
    
    public static class Counter<T> implements Collector<T,Integer> {
        private final AtomicInteger counter = new AtomicInteger ();
        private final boolean shouldStopOnFilterRejection;
        public Counter(boolean shouldStopOnFilterRejection) {
            this.shouldStopOnFilterRejection = shouldStopOnFilterRejection;
        }
        public boolean collect(T item)
        {
            if(value(item))
                counter.incrementAndGet();
            else if(shouldStopOnFilterRejection)
                return false;
            
            // return always 'true' to scan all items
            return true;
        }
        
        protected boolean value(T item) {
            return true;
        }
        public Integer getCollected()
        {
            return counter.get();
        }
    }
}

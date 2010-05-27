/* $Id$ */
package org.technbolts.util.functional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * ContainerImpl
 * @author <a href="mailto:arnauld.loyer@eptica.com">Loyer Arnauld</a>
 * @version $Revision$
 * @param <T>
 */
public abstract class ContainerImpl<T> implements Container<T>
{
    
    public static <T> ContainerImpl<T> wrap (final T ... items) {
        return new ArrayContainer<T> (items);
    }
    
    public static <T> ContainerImpl<T> wrap (final Iterable<T> items) {
        return new IterableContainer<T> (items);
    }

    public abstract void apply(C1<T> callback);
    
    public <R> Container<R> transform(F1<T,R> transform)
    {
        return new TransformedContainer<T,R> (this, transform);
    }
    public Container<T> filter(F1.ToBoolean<T> filter)
    {
        return new FilteredContainer<T> (this, filter);
    }
    public List<T> toList()
    {
        C1Utils.C1Collector<T> collector = C1Utils.newC1Collector();
        apply(collector);
        return collector.getCollected();
    }
    
    public static class FileContainer extends ContainerImpl<String> {
        private File file;
        public FileContainer(File file)
        {
            this.file = file;
        }
        
        @Override
        public void apply(C1<String> callback)
        {
            applyUntil(F1Utils.wrap(callback, true));
        }
        public void applyUntil(F1.ToBoolean<String> stopableCallback)
        {
            FileReader fileReader = null;
            try
            {
                fileReader = new FileReader (file);
                BufferedReader reader = new BufferedReader (fileReader);
                String line = null;
                while((line=reader.readLine())!=null) {
                    if(stopableCallback.op(line)==false)
                        return;
                }
            }
            catch (FileNotFoundException e)
            {
                throw new IllegalStateException (e);
            }
            catch (IOException e)
            {
                throw new IllegalStateException (e);
            }
            finally
            {
                try
                {
                    if(fileReader!=null)
                        fileReader.close ();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static class ArrayContainer<T> extends ContainerImpl<T> {
        private T[] items;
        public ArrayContainer (T[] items) {
            this.items = items;
        }
        @Override
        public void apply(C1<T> callback)
        {
            if(items==null)
                return; //nothing to do
            
            for(T item : items)
                callback.op(item);
        }
        public void applyUntil(F1.ToBoolean<T> stopableCallback)
        {
            if(items==null)
                return; //nothing to do
            
            for(T item : items)
                if(stopableCallback.op(item)==false)
                    return;
        }
    }
    
    public static class IterableContainer<T> extends ContainerImpl<T> {
        private Iterable<T> items;
        public IterableContainer (Iterable<T> items) {
            this.items = items;
        }
        @Override
        public void apply(C1<T> callback)
        {
            if(items==null)
                return; //nothing to do
            
            for(T item : items)
                callback.op(item);
        }
        public void applyUntil(F1.ToBoolean<T> stopableCallback)
        {
            if(items==null)
                return; //nothing to do
            
            for(T item : items)
                if(stopableCallback.op(item)==false)
                    return;
        }
    }
    
    public static class FilteredContainer<T> extends ContainerImpl<T> {
        private Container<T> delegate;
        private F1.ToBoolean<T> filter ;
        public FilteredContainer(Container<T> delegate, F1.ToBoolean<T> filter)
        {
            this.delegate = delegate;
            this.filter   = filter;
        }
        
        @Override
        public void apply(final C1<T> callback)
        {
            delegate.apply(C1Utils.filteredCallback(callback, filter));
        }
        public void applyUntil(final F1.ToBoolean<T> stopableCallback)
        {
            delegate.applyUntil(new F1.ToBoolean<T> () {
                public boolean op(T param)
                {
                    if(filter.op(param))
                        return stopableCallback.op(param);
                    return true;
                }
            });
        }
    }
    
    public static class TransformedContainer<T,R> extends ContainerImpl<R> {
        private Container<T> delegate;
        private F1<T,R> transform ;
        public TransformedContainer(Container<T> delegate, F1<T,R> transform)
        {
            this.delegate = delegate;
            this.transform   = transform;
        }
        
        @Override
        public void apply(final C1<R> callback)
        {
            delegate.apply(C1Utils.transformedCallback(callback, transform));
        }
        public void applyUntil(final F1.ToBoolean<R> stopableCallback)
        {
            delegate.applyUntil(new F1.ToBoolean<T> () {
                public boolean op(T param)
                {
                    return stopableCallback.op(transform.op(param));
                }
            });
        }
    }
}

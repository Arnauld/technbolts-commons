/* $Id$ */
package org.technbolts.util;

/**
 * This class represents optional values. 
 * Instances of Option are either instances of case class Some or it is case
 * object None.
 * 
 * Inspired from scala language: 
 * <a href="http://www.scala-lang.org/docu/files/api/scala/Option.html">Scaladoc Option</a>
 * 
 * @version $Revision$
 * @param <T>
 */
public abstract class Option<T>
{
    
    /**
     * Return Some(...) if value is not <code>null</code> otherwise returns None.
     * @param <T>
     * @param value
     * @return
     */
    public static final <T> Option<T> someOrNone(T value) {
        if(value==null)
            return none ();
        else
            return some (value);
    }
    
    /**
     * @param <T>
     * @return
     */
    public static final <T> None<T> none() {
        return new None<T>();
    }
    
    /**
     * @param <T>
     * @param value
     * @return
     */
    public static final <T> Some<T> some(T value) {
        return new Some<T>(value);
    }
    
    private Option () {
    }
    
    /**
     * True if the option is a Some(...) false otherwise.
     * @return
     */
    public abstract boolean isDefined ();
    
    /**
     * get the value of this option.
     * @return
     */
    public abstract T get();
    
    /**
     * If the option is nonempty return its value, otherwise 
     * return the <code>defaultValue</code>.
     * @param defaultValue
     * @return
     */
    public abstract T getOrElse(T defaultValue);
    
    /**
     * Indicates if the value of this option is equal to the specified value.
     * 
     * It always returns <code>false</code> with None.
     * 
     * @param value
     * @return
     */
    public abstract boolean equalTo(T other);
    
    /**
     * This case object represents non-existent values.
     * @param <T>
     */
    public static class None<T> extends Option<T> {
        public None() {
        }
        @Override
        public boolean isDefined() {
            return false;
        }
        public T get() {
            return null;
        }
        public T getOrElse(T defaultValue) {
            return defaultValue;
        }
        /**
         * @see com.eptica.utils.runtime.Option#equalTo(Object)
         * @see #isDefined()
         */
        public boolean equalTo(T other) {
            return false;
        }
    }
    
    /**
     * Class Some<T> represents existing values of type T.
     * @param <T>
     */
    public static class Some<T> extends Option<T> {
        private final T value;
        public Some(T value) {
            this.value = value;
        }
        @Override
        public boolean isDefined() {
            return true;
        }
        public T get() {
            return value;
        }
        public T getOrElse(T defaultValue) {
            if(value==null)
                return defaultValue;
            else
                return value;
        }
        public boolean equalTo(T other) {
            if(value==other)
                return true;
            else
            if(value==null || other==null)
                return false;
            else
                return value.equals(other);
        }
    }
}

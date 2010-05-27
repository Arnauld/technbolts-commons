/* $Id: Regex.java,v 1.1 2008-07-02 13:44:06 arnauld Exp $ */
package org.technbolts.util;

import org.technbolts.util.functional.F0;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Utility class that allow a timeout on <code>java.util.regex.Pattern</code> match.
 *  
 *  Default implementation use the current thread and a shared interrupter thread.
 *  No additionnal thread is created in order to prevent the creation overhead
 *  and limit the number of living threads, thus should be faster.
 *  
 * 
 * @author <a href="mailto:arnauld.loyer@eptica.com">Loyer Arnauld</a>
 * @version $Revision: 1.1 $
 * @see java.util.regex.Pattern
 * @see #matches(CharSequence)
 * @see #matches(CharSequence, long)
 */
public class Regex
{
    /**
     * @see java.util.regex.Pattern#UNIX_LINES
     */
    public static final int UNIX_LINES = Pattern.UNIX_LINES;

    /**
     * @see java.util.regex.Pattern#CASE_INSENSITIVE
     */
    public static final int CASE_INSENSITIVE = Pattern.CASE_INSENSITIVE;

    /**
     * @see java.util.regex.Pattern#COMMENTS
     */
    public static final int COMMENTS = Pattern.COMMENTS;

    /**
     * @see java.util.regex.Pattern#MULTILINE
     */
    public static final int MULTILINE = Pattern.MULTILINE;

    /**
     * @see java.util.regex.Pattern#LITERAL
     */
    public static final int LITERAL = Pattern.LITERAL;

    /**
     * @see java.util.regex.Pattern#DOTALL
     */
    public static final int DOTALL = Pattern.DOTALL;

    /**
     * @see java.util.regex.Pattern#UNICODE_CASE
     */
    public static final int UNICODE_CASE = Pattern.UNICODE_CASE;

    /**
     * @see java.util.regex.Pattern#CANON_EQ
     */
    public static final int CANON_EQ = Pattern.CANON_EQ;
    
    /**
     * @param pattern pattern used for the <code>matches</code> calls.
     * @see java.util.regex.Pattern#compile(String)
     */
    public static Regex compile(String pattern) {
        return new Regex(pattern);
    }
    
    /**
     * @param pattern pattern used for the <code>matches</code> calls.
     * @see java.util.regex.Pattern#compile(String, int)
     * @see java.util.regex.Pattern#DOTALL
     * @see java.util.regex.Pattern#MULTILINE
     * @see java.util.regex.Pattern#UNICODE_CASE
     * @see java.util.regex.Pattern#UNIX_LINES
     * @see java.util.regex.Pattern#CASE_INSENSITIVE
     * @throws java.util.regex.PatternSyntaxException
     */
    public static Regex compile(String pattern, int flag) {
        return new Regex(pattern, flag);
    }
    
    private static ScheduledExecutorService sharedScheduler;
    
    /**
     * Return the shared scheduler.
     * The method is set to <code>private</code> to prevent long task
     * to be scheduled from outside.
     * @return
     */
    private static synchronized ScheduledExecutorService getSharedScheduler () {
        if(sharedScheduler==null)
            sharedScheduler = Executors.newScheduledThreadPool(1, new ThreadFactory () {
                public Thread newThread(Runnable r)
                {
                    Thread thread = new Thread (r);
                    thread.setDaemon(true);
                    return thread;
                }
            });
        return sharedScheduler;
    }
    
    private Pattern pattern;
    
    /**
     * @param pattern pattern used for the <code>matches</code> calls.
     * @see java.util.regex.Pattern#compile(String)
     * @throws java.util.regex.PatternSyntaxException
     */
    public Regex(String pattern)
    {
        this.pattern = Pattern.compile(pattern);
    }
    
    /**
     * @param pattern pattern used for the <code>matches</code> calls.
     * @see java.util.regex.Pattern#compile(String, int)
     * @see java.util.regex.Pattern#DOTALL
     * @see java.util.regex.Pattern#MULTILINE
     * @see java.util.regex.Pattern#UNICODE_CASE
     * @see java.util.regex.Pattern#UNIX_LINES
     * @see java.util.regex.Pattern#CASE_INSENSITIVE
     * @throws java.util.regex.PatternSyntaxException
     */
    public Regex(String pattern, int flags)
    {
        this.pattern = Pattern.compile(pattern, flags);
    }
    
    /**
     * @param pattern pattern used for the <code>matches</code> calls.
     */
    public Regex(Pattern pattern)
    {
        this.pattern = pattern;
    }
    
    /**
     * Returns the regular expression from which this pattern was compiled.
     * @return
     */
    public String pattern()
    {
        return pattern.pattern();
    }
    
    /**
     * Same as <code>pattern.matcher(input).matches()</code> with the
     * default timeout settings if set.
     * @param input
     * @return
     * @see java.util.regex.Pattern#matcher(CharSequence)
     */
    public boolean matches(CharSequence input) {
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
    
    /**
     * Same as <code>pattern.matcher(input).find()</code>
     * @param input
     * @return
     * @see java.util.regex.Pattern#matcher(CharSequence)
     */
    public boolean find(CharSequence input) {
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
    
    /**
     * Same as <code>pattern.matcher(input)</code> but returns a <code>Matcher</code>
     * that use timeout settings whenever possible.
     * @param input
     * @return
     * @see java.util.regex.Pattern#matcher(CharSequence)
     */
    public RMatcher matcher(CharSequence input, long timeout, TimeUnit timeoutUnit) {
        return new RMatcher (input, timeout, timeoutUnit);
    }
    
    /**
     * Same as <code>matches(input)</code> but
     * cancel the processing if timeout occurs.
     * @param input
     * @param timeoutMillis timeout in milliseconds
     * @return
     * @throws TimeoutException
     * @see #matches(CharSequence)
     */
    public boolean matches(CharSequence input, long timeoutMillis) throws TimeoutException {
        return matches(input, timeoutMillis, TimeUnit.MILLISECONDS);
    }
    
    /**
     * Same as <code>matches(input)</code> but
     * cancel the processing if timeout occurs.
     * @param input
     * @param timeout
     * @param timeoutUnit
     * @return
     * @throws TimeoutException
     * @see #matches(CharSequence)
     */
    public boolean matches(CharSequence input, long timeout, TimeUnit timeoutUnit) throws TimeoutException {
            
        AbortableCharSequence abortable = new AbortableCharSequence (input);
        
        ScheduledExecutorService scheduler = getSharedScheduler ();
        scheduler.schedule(abortable, timeout, timeoutUnit);
        
        try
        {
            return matches(abortable);
        }
        catch (AbortException e)
        {
            throw new TimeoutException ("Timeout while matching entry ["+input+"]");
        }
    }
    
    /**
     * Same as <code>matches(CharSequence, long, TimeUnit)</code> but
     * returning a <code>BooleanResult</code> instead of a boolean or exception.
     * @param expr
     * @param timeout
     * @param timeoutUnit
     * @return
     */
    public BooleanResult matchesResult(final CharSequence expr, final long timeout,
            final TimeUnit timeoutUnit)
    {
        try
        {
            if(matches(expr, timeout, timeoutUnit))
                return BooleanResult.True;
            else
                return BooleanResult.False;
        }
        catch (TimeoutException e)
        {
            return BooleanResult.Timeout;
        }
    }
    
    /**
     * Same as <code>find(input)</code> but
     * cancel the processing if timeout occurs.
     * @param input
     * @param timeoutMillis timeout in milliseconds
     * @return
     * @throws TimeoutException
     * @see #find(CharSequence)
     */
    public boolean find(CharSequence input, long timeoutMillis) throws TimeoutException {
        return find(input, timeoutMillis, TimeUnit.MILLISECONDS);
    }
    
    /**
     * Same as <code>find(input)</code> but
     * cancel the processing if timeout occurs.
     * @param input
     * @param timeout
     * @param timeoutUnit
     * @return
     * @throws TimeoutException
     * @see #find(CharSequence)
     */
    public boolean find(CharSequence input, long timeout, TimeUnit timeoutUnit) throws TimeoutException {
        
        AbortableCharSequence abortable = new AbortableCharSequence (input);
        
        ScheduledExecutorService scheduler = getSharedScheduler ();
        scheduler.schedule(abortable, timeout, timeoutUnit);
        
        try
        {
            return find(abortable);
        }
        catch (AbortException e)
        {
            throw new TimeoutException ("Timeout while matching entry ["+input+"]");
        }
    }
    
    /**
     * Same as <code>find(CharSequence, long, TimeUnit)</code> but
     * returning a <code>BooleanResult</code> instead of a boolean or exception.
     * 
     * @param expr
     * @param timeout
     * @param timeoutUnit
     * @return
     */
    public BooleanResult findResult(final CharSequence expr, final long timeout,
            final TimeUnit timeoutUnit)
    {
        try
        {
            if(find(expr, timeout, timeoutUnit))
                return BooleanResult.True;
            else
                return BooleanResult.False;
        }
        catch (TimeoutException e)
        {
            return BooleanResult.Timeout;
        }
    }
    
    public String[] split(final CharSequence input, final long timeout, final TimeUnit timeoutUnit) throws TimeoutException
    {
        AbortableCharSequence abortable = new AbortableCharSequence (input);
        
        ScheduledExecutorService scheduler = getSharedScheduler ();
        scheduler.schedule(abortable, timeout, timeoutUnit);
        
        try
        {
            return pattern.split(abortable);
        }
        catch (AbortException e)
        {
            throw new TimeoutException ("Timeout while spliting entry ["+input+"]");
        }
    }
    
    /**
     * RMatcher
     */
    public class RMatcher {
        private Matcher matcher;
        private AbortableCharSequence abortable;
        private long timeout;
        private TimeUnit timeoutUnit;
        private CharSequence input;
        
        private RMatcher (CharSequence input, long timeout, TimeUnit timeoutUnit) {
            this.input = input;
            this.abortable = new AbortableCharSequence (input);
            this.matcher   = pattern.matcher(abortable);
            this.timeout   = timeout;
            this.timeoutUnit = timeoutUnit;
        }
        
        public Matcher getUnderlying()
        {
            return matcher;
        }
        
        public BooleanResult find () {
            return evaluate (new F0.ToBoolean() {
                public boolean op() {
                    return matcher.find();
                }
            });
        }
        
        public BooleanResult find(final int cur) {
            return evaluate (new F0.ToBoolean() {
                public boolean op() {
                    return matcher.find(cur);
                }
            });
        }
        
        protected BooleanResult evaluate (F0.ToBoolean func) {
            final AtomicBoolean canAbort = scheduleAbortable();
            try
            {
                boolean found = func.op();
                if(found)
                    return BooleanResult.True;
                else
                    return BooleanResult.False;
            }
            catch (AbortException e)
            {
                return BooleanResult.Timeout;
            }
            finally
            {
                canAbort.set(false);
            }
        }
        
        protected AtomicBoolean scheduleAbortable() {
            final AtomicBoolean canAbort = new AtomicBoolean(true);
            getSharedScheduler ().schedule(new Runnable() {
                public void run()
                {
                    if(canAbort.get())
                        abortable.abort();
                }
            }, timeout, timeoutUnit);
            return canAbort;
        }
        
        /**
         * @param replacement
         * @return
         * @throws TimeoutException
         * @see {@link java.util.regex.Matcher#replaceAll(String)}
         */
        public String replaceAll(String replacement) throws TimeoutException
        {
            final AtomicBoolean canAbort = scheduleAbortable();
            try
            {
                return matcher.replaceAll(replacement);
            }
            catch (AbortException e)
            {
                throw new TimeoutException ("Timeout while replacing all in ["+input+"]");
            }
            finally
            {
                canAbort.set(false);
            }
        }
        
        public BooleanResult matches()
        {
            return evaluate (new F0.ToBoolean() {
                public boolean op() {
                    return matcher.matches();
                }
            });
        }
        
        public String group(int index) {
            return matcher.group(index);
        }
        public int groupCount()
        {
            return matcher.groupCount();
        }
        public int start(int i)
        {
            return matcher.start(i);
        }
        public int end(int i)
        {
            return matcher.end(i);
        }
        public int end()
        {
            return matcher.end();
        }
        public int start()
        {
            return matcher.start();
        }
        public String pattern()
        {
            return pattern.pattern();
        }
        public CharSequence input () {
            return input;
        }
    }
    
    /**
     * CharSequence that throws an <code>AbortException</code> if the sequence
     * is used after  <code>abort()</code> has been called.
     * Class implements <code>Runnable</code> in order to schedule the 
     * <code>abort()</code> call.
     * The abort() function shall cause abnormal process termination to occur.
     */
    class AbortableCharSequence implements CharSequence, Runnable {
        private CharSequence delegate;
        private AtomicBoolean aborted = new AtomicBoolean (false);
        
        AbortableCharSequence(CharSequence delegate) {
            this.delegate = delegate;
        }
        
        public char charAt(int index)
        {
            checkAbort ();
            return delegate.charAt(index);
        }
        public int length()
        {
            checkAbort ();
            return delegate.length();
        }
        public CharSequence subSequence(int start, int end)
        {
            checkAbort ();
            return new AbortableCharSequenceChild(this,delegate.subSequence(start, end));
        }
        
        /**
         * The abort() function shall cause abnormal process termination to occur
         */
        protected void checkAbort ()
        {
            if(aborted.get())
                throw new AbortException ();
        }
        /**
         * The abort() function shall cause abnormal process termination to occur.
         */
        public void abort ()
        {
            aborted.set(true);
        }
        
        @Override
        public String toString()
        {
            return delegate.toString();
        }
        
        /**
         * Call abort.
         * @see Runnable#run()
         * @see #abort()
         */
        public void run()
        {
            abort ();
        }
    }
    
    /**
     * Delegate to the <code>root</code> all <code>abort</code> method calls.
     * @see org.technbolts.util.Regex.AbortableCharSequence
     */
    class AbortableCharSequenceChild extends AbortableCharSequence {
        private AbortableCharSequence root;
        AbortableCharSequenceChild(AbortableCharSequence root, CharSequence delegate) {
            super(delegate);
            this.root     = root;
        }
        @Override
        protected void checkAbort()
        {
            root.checkAbort();
        }
        @Override
        public void abort()
        {
            super.abort();
            root.abort();
        }
    }
    
    private static class AbortException extends RuntimeException {
        private static final long serialVersionUID = 5995455670302167464L;
    }
    
    /**
     * Status of the regex result, meaning depends on the using method.
     */
    public enum BooleanResult {
        True,
        False,
        Timeout
    }
    
}

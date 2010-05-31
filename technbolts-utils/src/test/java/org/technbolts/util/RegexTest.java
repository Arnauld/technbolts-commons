/* $Id$ */
package org.technbolts.util;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.DOTALL;
import static java.util.regex.Pattern.MULTILINE;
import static java.util.regex.Pattern.UNICODE_CASE;
import static org.apache.commons.lang.CharEncoding.UTF_8;
import static org.junit.Assume.assumeTrue;
import static org.technbolts.util.Regex.BooleanResult.*;

import java.util.concurrent.*;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

public class RegexTest
{
    private ExecutorService executor;
    
    @Before
    public void setUp() throws Exception
    {
        executor = Executors.newSingleThreadExecutor();
    }

    @Test
    public void testConstantsConsistency () {
        assertEquals(Pattern.CANON_EQ,         Regex.CANON_EQ);
        assertEquals(Pattern.CASE_INSENSITIVE, Regex.CASE_INSENSITIVE);
        assertEquals(Pattern.COMMENTS,         Regex.COMMENTS);
        assertEquals(Pattern.DOTALL,           Regex.DOTALL);
        assertEquals(Pattern.LITERAL,          Regex.LITERAL);
        assertEquals(Pattern.MULTILINE,        Regex.MULTILINE);
        assertEquals(Pattern.UNICODE_CASE,     Regex.UNICODE_CASE);
        assertEquals(Pattern.UNIX_LINES,       Regex.UNIX_LINES);
    }

    @Test
    public void testFind_normalCaseEx1 () throws Exception {
        String pattern = "([a-zA-Z]{2})\\-([a-zA-Z]{6})";
        final Regex regex = new Regex(pattern, DOTALL+CASE_INSENSITIVE+UNICODE_CASE+MULTILINE);
        
        String input1 = "12aA-ABcccDEf45";
        assertTrue  (regex.find(input1));
        assertEquals(True, regex.findResult(input1, 50, TimeUnit.SECONDS));
        
        String input2 = "12aA-ABc1cDEf45";
        assertFalse (regex.find(input2));
        assertEquals(False, regex.findResult(input2, 50, TimeUnit.SECONDS));
    }

    @Test
    public void testMatches_normalCaseEx1 () throws Exception {
        String pattern = "([a-zA-Z]{2})\\-([a-zA-Z]{6})";
        final Regex regex = new Regex(pattern, DOTALL+CASE_INSENSITIVE+UNICODE_CASE+MULTILINE);
        
        String input1 = "12aA-ABcDEf45";
        assertFalse (regex.matches(input1));
        assertEquals(False, regex.matchesResult(input1, 50, TimeUnit.SECONDS));
        
        String input2 = "aA-ABcDEf";
        assertTrue  (regex.matches(input2));
        assertEquals(True, regex.matchesResult(input2, 50, TimeUnit.SECONDS));
    }

    @Test
    public void testRMatcher_findNormalCaseEx1 () throws Exception {
        String pattern = "([a-zA-Z]{2})\\-([a-zA-Z]{6})";
        final Regex regex = new Regex(pattern, DOTALL+CASE_INSENSITIVE+UNICODE_CASE+MULTILINE);
        
        Regex.RMatcher matcher1 = regex.matcher("12aA-ABcDEf45", 500, TimeUnit.MILLISECONDS);
        assertEquals(True,  matcher1.find());
        
        Regex.RMatcher matcher2 = regex.matcher("12aA-ABc1cDEf45", 500, TimeUnit.MILLISECONDS);
        assertEquals(False, matcher2.find());
    }

    @Test
    public void testRMatcher_matchesNormalCaseEx1 () throws Exception {
        String pattern = "([a-zA-Z]{2})\\-([a-zA-Z]{6})";
        final Regex regex = new Regex(pattern, DOTALL+CASE_INSENSITIVE+UNICODE_CASE+MULTILINE);
        
        Regex.RMatcher matcher1 = regex.matcher("aA-ABcDEf", 500, TimeUnit.MILLISECONDS);
        assertEquals(True,  matcher1.matches());
        
        Regex.RMatcher matcher2 = regex.matcher("12aA-ABcDEf45", 500, TimeUnit.MILLISECONDS);
        assertEquals(False, matcher2.matches());
    }

    @Test
    public void testRMatcher_replaceAllNormalCaseEx1 () throws Exception {
        String pattern = "([a-zA-Z]{2})\\-([a-z]{6})";
        final Regex regex = new Regex(pattern, DOTALL+MULTILINE);
        
        Regex.RMatcher matcher1 = regex.matcher("aA-abcdef,aA-abcdef", 500, TimeUnit.MILLISECONDS);
        assertEquals("?,?",  matcher1.replaceAll("?"));
        
        Regex.RMatcher matcher2 = regex.matcher("12aA-abcXdef,aA-abcdef", 500, TimeUnit.MILLISECONDS);
        assertEquals("12aA-abcXdef,?",  matcher2.replaceAll("?"));
        
        Regex.RMatcher matcher3 = regex.matcher("12aA-abcXdef45,aA-aXbcdef", 500, TimeUnit.MILLISECONDS);
        assertEquals("12aA-abcXdef45,aA-aXbcdef",  matcher3.replaceAll("?"));
    }
    
    private final String infinite_input   = "V0xL182zronique_CHATAIGNIER/Do/Paris/Cnav/FR <V0xL182zronique_CHATAIGNIER/Do/Paris/Cnav/FR@cram-nordpicardie.fr>";
    private final String infinite_pattern = "([a-z0-9]+([a-z0-9\\-\\+\\._\\=]*[a-z0-9]+)?)*@[a-z0-9]([a-z0-9\\-]*[a-z0-9]+)?(\\.[a-z0-9]+([a-z0-9\\-]+[a-z0-9]+)?)*";

    @Test
    public void testInfiniteLoopEx1 () throws Exception {

        final int    Timeout = 200;
        final Regex regex = new Regex(infinite_pattern, DOTALL|CASE_INSENSITIVE);
        
        // create a callable to spawn the regex into a dedicated
        // thread, to prevent infinite loop in the main thread
        Callable<Regex.BooleanResult> callable = new Callable<Regex.BooleanResult>()
        {
            public Regex.BooleanResult call() throws Exception
            {
                return regex.matchesResult(infinite_input, Timeout, TimeUnit.MILLISECONDS);
            }
        };
        Future<Regex.BooleanResult> future = executor.submit(callable);
        
        Regex.BooleanResult status = future.get(Timeout*2, TimeUnit.MILLISECONDS);
        assertEquals(Regex.BooleanResult.Timeout, status);
    }

    @Test
    public void testInfiniteLoopEx2 () throws Exception {
        final int    Timeout = 1000;
        final String input   = IOUtils.toString(getClass().getResourceAsStream("regex-content-ex1.txt"), UTF_8);
        final String pattern = "(re)?cherch.*\\s+.*\\s+(homme.?|femme.?)";
        final Regex regex = new Regex(pattern, DOTALL+CASE_INSENSITIVE+UNICODE_CASE+MULTILINE);
        
        // create a callable to spawn the regex into a dedicated
        // thread, to prevent infinite loop in the main thread
        Callable<Regex.BooleanResult> callable =  new Callable<Regex.BooleanResult>()
        {
            public Regex.BooleanResult call() throws Exception
            {
                return regex.findResult(input, Timeout, TimeUnit.MILLISECONDS);
            }
        };
        Future<Regex.BooleanResult> future = executor.submit(callable);
        
        Regex.BooleanResult status = future.get(Timeout*2, TimeUnit.MILLISECONDS);
        assertEquals(Regex.BooleanResult.Timeout, status);
    }

    @Test
    public void testMatcherInfiniteLoopEx1 () throws Exception {
        assumeTrue(Boolean.getBoolean("long-running-test"));

        final int    Timeout = 1000;
        final String input   = IOUtils.toString(getClass().getResourceAsStream("regex-content-ex1.txt"), UTF_8);
        final String pattern = "(re)?cherch.*\\s+.*\\s+(homme.?|femme.?)";
        final Regex regex = new Regex(pattern, DOTALL+CASE_INSENSITIVE+UNICODE_CASE+MULTILINE);
        
        // create a callable to spawn the regex into a dedicated
        // thread, to prevent infinite loop in the main thread
        Callable<Regex.BooleanResult> callable =  new Callable<Regex.BooleanResult>()
        {
            public Regex.BooleanResult call() throws Exception
            {
                Regex.RMatcher matcher = regex.matcher(input, Timeout, TimeUnit.MILLISECONDS);
                return matcher.find();
            }
        };
        Future<Regex.BooleanResult> future = executor.submit(callable);
        
        Regex.BooleanResult status = future.get(Timeout*2, TimeUnit.MILLISECONDS);
        assertEquals(Regex.BooleanResult.Timeout, status);
    }

    @Test
    public void testMatcherInfiniteLoopEx2 () throws Exception {
        assumeTrue(Boolean.getBoolean("long-running-test"));

        final int    Timeout = 1000;
        final String input   = infinite_input;
        final String pattern = infinite_pattern;
        final Regex regex = new Regex(pattern, DOTALL|CASE_INSENSITIVE);
        
        // create a callable to spawn the regex into a dedicated
        // thread, to prevent infinite loop in the main thread
        Callable<Regex.BooleanResult> callable =  new Callable<Regex.BooleanResult>()
        {
            public Regex.BooleanResult call() throws Exception
            {
                Regex.RMatcher matcher = regex.matcher(input, Timeout, TimeUnit.MILLISECONDS);
                return matcher.matches();
            }
        };
        Future<Regex.BooleanResult> future = executor.submit(callable);
        
        Regex.BooleanResult status = future.get(Timeout*2, TimeUnit.MILLISECONDS);
        assertEquals(Regex.BooleanResult.Timeout, status);
    }

    @Test
    public void testMatcherInfiniteLoopEx3 () throws Exception {
        assumeTrue(Boolean.getBoolean("long-running-test"));
        
        final int    Timeout = 1000;
        final String input   = infinite_input;
        final String pattern = infinite_pattern;
        final Regex regex = new Regex(pattern, DOTALL|CASE_INSENSITIVE);
        
        // create a callable to spawn the regex into a dedicated
        // thread, to prevent infinite loop in the main thread
        Callable<String> callable =  new Callable<String>()
        {
            public String call() throws Exception
            {
                Regex.RMatcher matcher = regex.matcher(input, Timeout, TimeUnit.MILLISECONDS);
                return matcher.replaceAll("zog");
            }
        };
        
        try
        {
            Future<String> future = executor.submit(callable);
            String replaced = future.get(Timeout*2, TimeUnit.MILLISECONDS);
            fail("A timeout should have occured replaced '"+replaced+"'");
        }
        catch (ExecutionException e)
        {
            assertThat(e.getCause(), is(TimeoutException.class));
        }
    }
    
}

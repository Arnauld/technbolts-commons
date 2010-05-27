package org.technbolts.util;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * User: loyer
 * Date: 26 mai 2010
 * Time: 15:23:57
 *
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class StringMatcherTest {

    @Test
    public void wildcardMatching_nullCases () {
        assertTrue (StringMatcher.wildCardMatching(null, null));
        assertFalse(StringMatcher.wildCardMatching(null, "po*otatoes"));
        assertFalse(StringMatcher.wildCardMatching("potatoes", null));
    }
    
    @Test
    public void wildcardMatching_basicMatchings () {
        assertTrue (StringMatcher.wildCardMatching("potatoes", "po*oes"));
        assertFalse(StringMatcher.wildCardMatching("potatoes", "po*otatoes"));
    }

    @Test
    public void wildcardMatching_edgeMatchings () {
        assertFalse(StringMatcher.wildCardMatching("potatoes", "o*o"));
        assertTrue (StringMatcher.wildCardMatching("ooo", "o*o"));
        assertTrue (StringMatcher.wildCardMatching("oo", "o*o"));
        assertTrue (StringMatcher.wildCardMatching("oo", "o**o"));
        assertFalse(StringMatcher.wildCardMatching("potatoes", "o*o"));
    }

    @Test
    public void wildcardMatching_edgeMatchings_startWith () {
        assertTrue (StringMatcher.wildCardMatching("potatoes", "pot*"));
        assertFalse(StringMatcher.wildCardMatching("potatoes", "ot*"));
        assertTrue (StringMatcher.wildCardMatching("potatoes", "*pot*"));
        assertTrue (StringMatcher.wildCardMatching("oo", "o*o"));
        assertTrue (StringMatcher.wildCardMatching("ooo", "o*o"));
        assertFalse(StringMatcher.wildCardMatching("o", "o*o"));
    }

    @Test
    public void wildcardMatching_edgeMatchings_endWith () {
        assertTrue (StringMatcher.wildCardMatching("potatoes", "*oe"));
        assertTrue (StringMatcher.wildCardMatching("potatoes", "*oes"));
        assertTrue (StringMatcher.wildCardMatching("potatoes", "*oes*"));
        assertTrue (StringMatcher.wildCardMatching("oo", "o*o"));
    }
}

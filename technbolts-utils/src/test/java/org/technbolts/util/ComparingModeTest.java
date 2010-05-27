/* $Id$ */
package org.technbolts.util;

import static org.technbolts.util.ComparingMode.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import junit.framework.TestCase;

/**
 * ComparingModeTest
 * @author <a href="mailto:arnauld.loyer@eptica.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class ComparingModeTest extends TestCase
{

    public void testEqualTo_withInteger () {
        assertThat(EqualTo.isSatisfiedBy( 0, 0), is(true));
        assertThat(EqualTo.isSatisfiedBy( 1, 1), is(true));
        assertThat(EqualTo.isSatisfiedBy( 1, 7), is(false));
        assertThat(EqualTo.isSatisfiedBy( 1,-1), is(false));
        assertThat(EqualTo.isSatisfiedBy(-1, 1), is(false));
    }
    
    public void testEqualTo_withString () {
        assertThat(EqualTo.isSatisfiedBy("",""), is(true));
        assertThat(EqualTo.isSatisfiedBy("zorglub","zorglub"), is(true));
        assertThat(EqualTo.isSatisfiedBy("zorglub","zorglab"), is(false));
        assertThat(EqualTo.<String>isSatisfiedBy(null,null), is(true));
        assertThat(EqualTo.isSatisfiedBy(null,  ""), is(false));
        assertThat(EqualTo.isSatisfiedBy("",  null), is(false));
    }
    
    public void testLowerThan_withInteger () {
        assertThat(LowerThan.isSatisfiedBy( 0, 0), is(false));
        assertThat(LowerThan.isSatisfiedBy( 1, 1), is(false));
        assertThat(LowerThan.isSatisfiedBy( 1, 7), is(true));
        assertThat(LowerThan.isSatisfiedBy( 1,-1), is(false));
        assertThat(LowerThan.isSatisfiedBy(-1, 1), is(true));
    }
    
    public void testLowerThan_withString  () {
        assertThat(LowerThan.isSatisfiedBy("",""), is(false));
        assertThat(LowerThan.isSatisfiedBy("zorglub","zorglub"), is(false));
        assertThat(LowerThan.isSatisfiedBy("zorglab","zorglub"), is(true));
        assertThat(LowerThan.isSatisfiedBy("zorglub","zorglab"), is(false));
        assertThat(LowerThan.<String>isSatisfiedBy(null,null), is(false));
        assertThat(LowerThan.isSatisfiedBy(null,  ""), is(true));
        assertThat(LowerThan.isSatisfiedBy("",  null), is(false));
    }
    
    public void testLowerThanOrEqualTo_withInteger () {
        assertThat(LowerThanOrEqualTo.isSatisfiedBy( 0, 0), is(true));
        assertThat(LowerThanOrEqualTo.isSatisfiedBy( 1, 1), is(true));
        assertThat(LowerThanOrEqualTo.isSatisfiedBy( 1, 7), is(true));
        assertThat(LowerThanOrEqualTo.isSatisfiedBy( 1,-1), is(false));
        assertThat(LowerThanOrEqualTo.isSatisfiedBy(-1, 1), is(true));
    }
    
    public void testLowerThanOrEqualTo_withString  () {
        assertThat(LowerThanOrEqualTo.isSatisfiedBy("",""), is(true));
        assertThat(LowerThanOrEqualTo.isSatisfiedBy("zorglub","zorglub"), is(true));
        assertThat(LowerThanOrEqualTo.isSatisfiedBy("zorglab","zorglub"), is(true));
        assertThat(LowerThanOrEqualTo.isSatisfiedBy("zorglub","zorglab"), is(false));
        assertThat(LowerThanOrEqualTo.<String>isSatisfiedBy(null,null), is(true));
        assertThat(LowerThanOrEqualTo.isSatisfiedBy(null,  ""), is(true));
        assertThat(LowerThanOrEqualTo.isSatisfiedBy("",  null), is(false));
    }
    
    public void testGreaterThan_withInteger () {
        assertThat(GreaterThan.isSatisfiedBy( 0, 0), is(false));
        assertThat(GreaterThan.isSatisfiedBy( 1, 1), is(false));
        assertThat(GreaterThan.isSatisfiedBy( 1, 7), is(false));
        assertThat(GreaterThan.isSatisfiedBy( 7, 1), is(true));
        assertThat(GreaterThan.isSatisfiedBy( 1,-1), is(true));
        assertThat(GreaterThan.isSatisfiedBy(-1, 1), is(false));
    }
    
    public void testGreaterThan_withString  () {
        assertThat(GreaterThan.isSatisfiedBy("",""), is(false));
        assertThat(GreaterThan.isSatisfiedBy("zorglub","zorglub"), is(false));
        assertThat(GreaterThan.isSatisfiedBy("zorglab","zorglub"), is(false));
        assertThat(GreaterThan.isSatisfiedBy("zorglub","zorglab"), is(true));
        assertThat(GreaterThan.<String>isSatisfiedBy(null,null), is(false));
        assertThat(GreaterThan.isSatisfiedBy(null,  ""), is(false));
        assertThat(GreaterThan.isSatisfiedBy("",  null), is(true));
    }
    
    public void testGreaterThanOrEqualTo_withInteger () {
        assertThat(GreaterThanOrEqualTo.isSatisfiedBy( 0, 0), is(true));
        assertThat(GreaterThanOrEqualTo.isSatisfiedBy( 1, 1), is(true));
        assertThat(GreaterThanOrEqualTo.isSatisfiedBy( 1, 7), is(false));
        assertThat(GreaterThanOrEqualTo.isSatisfiedBy( 7, 1), is(true));
        assertThat(GreaterThanOrEqualTo.isSatisfiedBy( 1,-1), is(true));
        assertThat(GreaterThanOrEqualTo.isSatisfiedBy(-1, 1), is(false));
    }
    
    public void testGreaterThanOrEqualTo_withString  () {
        assertThat(GreaterThanOrEqualTo.isSatisfiedBy("",""), is(true));
        assertThat(GreaterThanOrEqualTo.isSatisfiedBy("zorglub","zorglub"), is(true));
        assertThat(GreaterThanOrEqualTo.isSatisfiedBy("zorglab","zorglub"), is(false));
        assertThat(GreaterThanOrEqualTo.isSatisfiedBy("zorglub","zorglab"), is(true));
        assertThat(GreaterThanOrEqualTo.<String>isSatisfiedBy(null,null), is(true));
        assertThat(GreaterThanOrEqualTo.isSatisfiedBy(null,  ""), is(false));
        assertThat(GreaterThanOrEqualTo.isSatisfiedBy("",  null), is(true));
    }
}

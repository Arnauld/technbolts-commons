/* $Id$ */
package org.technbolts.util;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Comparator;

import junit.framework.TestCase;

public class ComparatorUtilsTest extends TestCase
{
    
    private static Integer One   = Integer.valueOf(1);
    private static Integer Seven = Integer.valueOf(7);
    private static Integer Thirteen = Integer.valueOf(13);
    private static Integer MinusThirteen = Integer.valueOf(-13);

    public void testCompareNullArguments () {
        Integer value1 = null;
        Integer value2 = null;
        assertThat(ComparatorUtils.compare(value1, value2), equalTo(0));
    }
    
    public void testCompareNullArgument () {
        assertThat(ComparatorUtils.compare(One, null), equalTo(+1));
        assertThat(ComparatorUtils.compare(null, One), equalTo(-1));
    }
    
    public void testCompareEx1 () {
        assertThat(ComparatorUtils.compare(One, Seven), equalTo(-1));
        assertThat(ComparatorUtils.compare(Seven, One), equalTo(+1));
        assertThat(ComparatorUtils.compare(MinusThirteen, MinusThirteen), equalTo(0));
        assertThat(ComparatorUtils.compare(Thirteen, MinusThirteen), equalTo(+1));
        assertThat(ComparatorUtils.compare(MinusThirteen, Thirteen), equalTo(-1));
    }
    
    public void testComparator () {
        Comparator<Integer> comparator = ComparatorUtils.comparator();
        
        assertThat(comparator.compare(One, One), equalTo(0));
        assertThat(comparator.compare(Thirteen, Thirteen), equalTo(0));
        assertThat(comparator.compare(Thirteen, One), equalTo(+1));
        assertThat(comparator.compare(Thirteen, MinusThirteen), equalTo(+1));
        assertThat(comparator.compare(MinusThirteen, Thirteen), equalTo(-1));
    }
}

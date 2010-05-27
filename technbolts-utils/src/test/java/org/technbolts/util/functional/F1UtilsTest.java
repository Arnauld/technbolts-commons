/* $Id$ */
package org.technbolts.util.functional;

import static org.technbolts.util.functional.EnumerableUtils.toArray;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class F1UtilsTest extends TestCase
{
    public void testGreaterThan7 () {
        F1.ToBoolean<Integer> greaterThan7 = F1Utils.greaterThan(7);
        assertTrue (greaterThan7.op(8));
        assertFalse(greaterThan7.op(7));
        assertFalse(greaterThan7.op(6));
    }
    
    public void testGreaterThanOrEqualTo7 () {
        F1.ToBoolean<Integer> greaterThanOrEqualTo7 = F1Utils.greaterThanOrEqualTo(7);
        assertTrue (greaterThanOrEqualTo7.op(8));
        assertTrue (greaterThanOrEqualTo7.op(7));
        assertFalse(greaterThanOrEqualTo7.op(6));
    }
    
    public void testLesserThan7 () {
        F1.ToBoolean<Integer> lesserThan7 = F1Utils.lesserThan(7);
        assertTrue (lesserThan7.op(6));
        assertFalse(lesserThan7.op(7));
        assertFalse(lesserThan7.op(8));
    }
    
    public void testLesserThanOrEqualTo7 () {
        F1.ToBoolean<Integer> lesserThanOrEqualTo7 = F1Utils.lesserThanOrEqualTo(7);
        assertTrue (lesserThanOrEqualTo7.op(6));
        assertTrue (lesserThanOrEqualTo7.op(7));
        assertFalse(lesserThanOrEqualTo7.op(8));
    }
    
    public void testNotNull () {
        F1.ToBoolean<Integer> filter = F1Utils.notNull();
        assertThat(filter.op(null),               equalTo(false));
        assertThat(filter.op(Integer.valueOf(1)), equalTo(true));
    }
    
    public void testAlwaysTrue () {
        F1.ToBoolean<Integer> filter = F1Utils.alwaysTrue();
        assertThat(filter.op(null),                equalTo(true));
        assertThat(filter.op(Integer.valueOf(-1)), equalTo(true));
        assertThat(filter.op(Integer.valueOf(+1)), equalTo(true));
    }
    
    public void testAlwaysTrueTyped () {
        F1.ToBoolean<Integer> filter = F1Utils.alwaysTrue(Integer.class);
        assertThat(filter.op(null),                equalTo(true));
        assertThat(filter.op(Integer.valueOf(-1)), equalTo(true));
        assertThat(filter.op(Integer.valueOf(+1)), equalTo(true));
    }
    
    public void testInstanceOfNull () {
        F1.ToBoolean<Object> filter = F1Utils.instanceOf(Number.class);
        assertThat(filter.op(null),                 equalTo(false));
        assertThat(filter.op((Number)null),         equalTo(false));
    }
    
    public void testInstanceOfNotNull () {
        F1.ToBoolean<Object> filter = F1Utils.instanceOf(Number.class);
        assertThat(filter.op(Integer.valueOf(-1)),  equalTo(true));
        assertThat(filter.op(Integer.valueOf(+1)),  equalTo(true));
        assertThat(filter.op(Double.valueOf(17.0)), equalTo(true));
        assertThat(filter.op("zogzog"),             equalTo(false));
    }
    
    public void testCast () {
        F1<Object,Number> cast = F1Utils.cast(Number.class);
        
        // actually one will use the java runtime checks here
        Number castedOne = cast.op(Integer.valueOf(17));
        assertEquals (17, castedOne.intValue());
        
        // actually one will use the java runtime checks here
        Number castedTwo = cast.op(Double.valueOf(17.0));
        assertEquals (17.0, castedTwo.doubleValue(), 1e-6);
        
        try
        {
            cast.op("can a jvm really cast a string into a number, i hope it can't");
            fail ("shouldn't be able to cast a string into a number");
        }
        catch(ClassCastException cce)
        {
            // normal case :)
        }
    }
    
    public void testNot () {
        F1.ToBoolean<Integer> greaterThan10 = new F1.ToBoolean<Integer> () {
            public boolean op(Integer param)
            {
                return param>10;
            }
        };
        // just make sure it works before negate it :)
        assertTrue (greaterThan10.op(13));
        assertFalse(greaterThan10.op(10));
        assertFalse(greaterThan10.op(7));
        
        F1.ToBoolean<Integer> lesserThanOrEqualTo10 = F1Utils.not(greaterThan10);
        assertFalse(lesserThanOrEqualTo10.op(13));
        assertTrue (lesserThanOrEqualTo10.op(10));
        assertTrue (lesserThanOrEqualTo10.op(7));
    }
    
    public void testNegate () {
        F1.ToBoolean<Integer> greaterThan10 = new F1.ToBoolean<Integer> () {
            public boolean op(Integer param)
            {
                return param>10;
            }
        };
        // just make sure it works before negate it :)
        assertTrue (greaterThan10.op(13));
        assertFalse(greaterThan10.op(10));
        assertFalse(greaterThan10.op(7));
        
        F1.ToBoolean<Integer> lesserThanOrEqualTo10 = F1Utils.negate(greaterThan10);
        assertFalse(lesserThanOrEqualTo10.op(13));
        assertTrue (lesserThanOrEqualTo10.op(10));
        assertTrue (lesserThanOrEqualTo10.op(7));
    }
    
    public void testOneOf1 () {
        F1.ToBoolean<Integer> seven = F1Utils.oneOf(7);
        assertFalse(seven.op(5));
        assertFalse(seven.op(17));
        assertFalse(seven.op(13));
        assertFalse(seven.op(11));
        assertTrue (seven.op(7));
        assertFalse(seven.op(23));
    }
    
    public void testOneOf2 () {
        F1.ToBoolean<Integer> seven = F1Utils.oneOf(7,13);
        assertFalse(seven.op(5));
        assertFalse(seven.op(17));
        assertTrue (seven.op(13));
        assertFalse(seven.op(11));
        assertTrue (seven.op(7));
        assertFalse(seven.op(23));
    }
    
    public void testOneOf3 () {
        F1.ToBoolean<Integer> seven = F1Utils.oneOf(7,13,11);
        assertFalse(seven.op(5));
        assertFalse(seven.op(17));
        assertTrue (seven.op(13));
        assertTrue (seven.op(11));
        assertTrue (seven.op(7));
        assertFalse(seven.op(23));
    }
    
    public void testOneOfMoreThan3 () {
        F1.ToBoolean<Integer> seven = F1Utils.oneOf(7,13,11,17);
        assertFalse(seven.op(5));
        assertTrue (seven.op(17));
        assertTrue (seven.op(13));
        assertTrue (seven.op(11));
        assertTrue (seven.op(7));
        assertFalse(seven.op(23));
    }
    
    public void testOneOfMoreThan3List () {
        F1.ToBoolean<Integer> seven = F1Utils.oneOf(asList(7,13,11,17));
        assertFalse(seven.op(5));
        assertTrue (seven.op(17));
        assertTrue (seven.op(13));
        assertTrue (seven.op(11));
        assertTrue (seven.op(7));
        assertFalse(seven.op(23));
    }
    
    public void testNoneOf1 () {
        F1.ToBoolean<Integer> seven = F1Utils.noneOf(7);
        assertTrue (seven.op(5));
        assertTrue (seven.op(17));
        assertTrue (seven.op(13));
        assertTrue (seven.op(11));
        assertFalse(seven.op(7));
        assertTrue (seven.op(23));
    }
    
    public void testNoneOf2 () {
        F1.ToBoolean<Integer> seven = F1Utils.noneOf(7,13);
        assertTrue (seven.op(5));
        assertTrue (seven.op(17));
        assertFalse(seven.op(13));
        assertTrue (seven.op(11));
        assertFalse(seven.op(7));
        assertTrue (seven.op(23));
    }
    
    public void testNoneOf3 () {
        F1.ToBoolean<Integer> seven = F1Utils.noneOf(7,13,11);
        assertTrue (seven.op(5));
        assertTrue (seven.op(17));
        assertFalse(seven.op(13));
        assertFalse(seven.op(11));
        assertFalse(seven.op(7));
        assertTrue (seven.op(23));
    }
    
    public void testNoneOfMoreThan3 () {
        F1.ToBoolean<Integer> seven = F1Utils.noneOf(7,13,11,17);
        assertTrue (seven.op(5));
        assertFalse(seven.op(17));
        assertFalse(seven.op(13));
        assertFalse(seven.op(11));
        assertFalse(seven.op(7));
        assertTrue (seven.op(23));
    }
    
    public void testNoneOfMoreThan3List () {
        F1.ToBoolean<Integer> seven = F1Utils.noneOf(asList(7,13,11,17));
        assertTrue (seven.op(5));
        assertFalse(seven.op(17));
        assertFalse(seven.op(13));
        assertFalse(seven.op(11));
        assertFalse(seven.op(7));
        assertTrue (seven.op(23));
    }
    
    public void testExcept1 () {
        F1.ToBoolean<Integer> seven = F1Utils.except(7);
        assertTrue (seven.op(5));
        assertTrue (seven.op(17));
        assertTrue (seven.op(13));
        assertTrue (seven.op(11));
        assertFalse(seven.op(7));
        assertTrue (seven.op(23));
    }
    
    public void testExcept2 () {
        F1.ToBoolean<Integer> seven = F1Utils.except(7,13);
        assertTrue (seven.op(5));
        assertTrue (seven.op(17));
        assertFalse(seven.op(13));
        assertTrue (seven.op(11));
        assertFalse(seven.op(7));
        assertTrue (seven.op(23));
    }
    
    public void testExcept3 () {
        F1.ToBoolean<Integer> seven = F1Utils.except(7,13,11);
        assertTrue (seven.op(5));
        assertTrue (seven.op(17));
        assertFalse(seven.op(13));
        assertFalse(seven.op(11));
        assertFalse(seven.op(7));
        assertTrue (seven.op(23));
    }
    
    public void testExceptMoreThan3 () {
        F1.ToBoolean<Integer> seven = F1Utils.except(7,13,11,17);
        assertTrue (seven.op(5));
        assertFalse(seven.op(17));
        assertFalse(seven.op(13));
        assertFalse(seven.op(11));
        assertFalse(seven.op(7));
        assertTrue (seven.op(23));
    }
    
    public void testExceptMoreThan3List () {
        F1.ToBoolean<Integer> seven = F1Utils.except(asList(7,13,11,17));
        assertTrue (seven.op(5));
        assertFalse(seven.op(17));
        assertFalse(seven.op(13));
        assertFalse(seven.op(11));
        assertFalse(seven.op(7));
        assertTrue (seven.op(23));
    }
    
    public void testMatchOneOf1 () {
        F1.ToBoolean<Number> seven = F1Utils.matchOneOf( data(7));
        assertFalse(seven.op(5));
        assertFalse(seven.op(17));
        assertFalse(seven.op(13));
        assertFalse(seven.op(11));
        assertTrue (seven.op(7));
        assertTrue (seven.op(Long.valueOf(7)));
        assertFalse(seven.op(23));
    }
    
    public void testMatchOneOf2 () {
        F1.ToBoolean<Number> seven = F1Utils.matchOneOf( data(7),data(13));
        assertFalse(seven.op(5));
        assertFalse(seven.op(17));
        assertTrue (seven.op(13));
        assertTrue (seven.op(Long.valueOf(13)));
        assertFalse(seven.op(11));
        assertTrue (seven.op(7));
        assertTrue (seven.op(Long.valueOf(7)));
        assertFalse(seven.op(23));
    }
    
    public void testMatchOneOf3 () {
        F1.ToBoolean<Number> seven = F1Utils.matchOneOf( data(7),data(13),data(11));
        assertFalse(seven.op(5));
        assertFalse(seven.op(17));
        assertTrue (seven.op(13));
        assertTrue (seven.op(Long.valueOf(13)));
        assertTrue (seven.op(11));
        assertTrue (seven.op(Long.valueOf(11)));
        assertTrue (seven.op(7));
        assertTrue (seven.op(Long.valueOf(7)));
        assertFalse(seven.op(23));
    }
    
    @SuppressWarnings("unchecked")
    public void testMatchOneOfMoreThan3 () {
        F1.ToBoolean<Number> seven = F1Utils.matchOneOf( data(7),data(13),data(11), data(17));
        assertFalse(seven.op(5));
        assertTrue (seven.op(17));
        assertTrue (seven.op(Long.valueOf(17)));
        assertTrue (seven.op(13));
        assertTrue (seven.op(Long.valueOf(13)));
        assertTrue (seven.op(11));
        assertTrue (seven.op(Long.valueOf(11)));
        assertTrue (seven.op(7));
        assertTrue (seven.op(Long.valueOf(7)));
        assertFalse(seven.op(23));
    }
    
    public void testMatchOneOfneOfMoreThan3List () {
        List<Comparable<Number>> matchers = new ArrayList<Comparable<Number>> ();
        for(Data data : asList( data(7),data(13),data(11), data(17)))
            matchers.add(data);
        F1.ToBoolean<Number> seven = F1Utils.matchOneOf( matchers );
        assertFalse(seven.op(5));
        assertTrue (seven.op(17));
        assertTrue (seven.op(Long.valueOf(17)));
        assertTrue (seven.op(13));
        assertTrue (seven.op(Long.valueOf(13)));
        assertTrue (seven.op(11));
        assertTrue (seven.op(Long.valueOf(11)));
        assertTrue (seven.op(7));
        assertTrue (seven.op(Long.valueOf(7)));
        assertFalse(seven.op(23));
    }
    
    public void testAndChain_arrayOneFilter () {
        F1.ToBoolean<Number> chain = 
            F1Utils.andChain(toArray(new LowerThan(Integer.valueOf(7))));
        
        assertTrue (chain.op( 5));
        assertFalse(chain.op(23));
    }
    
    public void testAndChain_arrayTwoFilters () {
        F1.ToBoolean<Number> chain = 
            F1Utils.andChain(toArray(
                    new LowerThan(Integer.valueOf(7)),
                    new LowerThan(Integer.valueOf(6))));
        
        assertTrue (chain.op( 5));
        assertFalse(chain.op( 6));
        assertFalse(chain.op(23));
    }
    
    public void testAndChain_arrayThreeFilters () {
        F1.ToBoolean<Number> chain = 
            F1Utils.andChain(toArray(
                    new LowerThan(Integer.valueOf(7)),
                    new LowerThan(Integer.valueOf(6)),
                    new LowerThan(Integer.valueOf(5))));
        
        assertTrue (chain.op( 4));
        assertFalse(chain.op( 5));
        assertFalse(chain.op( 6));
        assertFalse(chain.op(23));
    }
    
    public void testAndChain_arrayFourFilters () {
        F1.ToBoolean<Number> chain = 
            F1Utils.andChain(toArray(
                    new LowerThan(Integer.valueOf(7)),
                    new LowerThan(Integer.valueOf(6)),
                    new LowerThan(Integer.valueOf(5)),
                    new LowerThan(Integer.valueOf(4))));
        
        assertTrue (chain.op( 3));
        assertFalse(chain.op( 4));
        assertFalse(chain.op( 5));
        assertFalse(chain.op( 6));
        assertFalse(chain.op(23));
    }
    
    @SuppressWarnings("unchecked")
    public void testAndChain_listOneFilter () {
        F1.ToBoolean<Number> chain = 
            F1Utils.andChain(Arrays.<F1.ToBoolean<Number>>asList(
                    new LowerThan(Integer.valueOf(7))));
        
        assertTrue (chain.op( 5));
        assertFalse(chain.op(23));
    }
    
    @SuppressWarnings("unchecked")
    public void testAndChain_listTwoFilters () {
        F1.ToBoolean<Number> chain = 
            F1Utils.andChain(Arrays.<F1.ToBoolean<Number>>asList(
                    new LowerThan(Integer.valueOf(7)),
                    new LowerThan(Integer.valueOf(6))));
        
        assertTrue (chain.op( 5));
        assertFalse(chain.op( 6));
        assertFalse(chain.op(23));
    }
    
    @SuppressWarnings("unchecked")
    public void testAndChain_listThreeFilters () {
        F1.ToBoolean<Number> chain = 
            F1Utils.andChain(Arrays.<F1.ToBoolean<Number>>asList(
                    new LowerThan(Integer.valueOf(7)),
                    new LowerThan(Integer.valueOf(6)),
                    new LowerThan(Integer.valueOf(5))));
        
        assertTrue (chain.op( 4));
        assertFalse(chain.op( 5));
        assertFalse(chain.op( 6));
        assertFalse(chain.op(23));
    }
    
    @SuppressWarnings("unchecked")
    public void testAndChain_listFourFilters () {
        F1.ToBoolean<Number> chain = 
            F1Utils.andChain(Arrays.<F1.ToBoolean<Number>>asList(
                    new LowerThan(Integer.valueOf(7)),
                    new LowerThan(Integer.valueOf(6)),
                    new LowerThan(Integer.valueOf(5)),
                    new LowerThan(Integer.valueOf(4))));
        
        assertTrue (chain.op( 3));
        assertFalse(chain.op( 4));
        assertFalse(chain.op( 5));
        assertFalse(chain.op( 6));
        assertFalse(chain.op(23));
    }
    
    //
    public void testOrChain_arrayOneFilter () {
        F1.ToBoolean<Number> chain = 
            F1Utils.orChain(toArray(new LowerThan(Integer.valueOf(7))));
        
        assertTrue (chain.op( 5));
        assertFalse(chain.op(23));
    }
    
    public void testOrChain_arrayTwoFilters () {
        F1.ToBoolean<Number> chain = 
            F1Utils.orChain(toArray(
                    new LowerThan(Integer.valueOf(6)),
                    new LowerThan(Integer.valueOf(7))));
        
        assertTrue (chain.op( 5));
        assertTrue (chain.op( 6));
        assertFalse(chain.op(23));
    }
    
    public void testOrChain_arrayThreeFilters () {
        F1.ToBoolean<Number> chain = 
            F1Utils.orChain(toArray(
                    new LowerThan(Integer.valueOf(5)),
                    new LowerThan(Integer.valueOf(6)),
                    new LowerThan(Integer.valueOf(7))));
        
        assertTrue (chain.op( 4));
        assertTrue (chain.op( 5));
        assertTrue (chain.op( 6));
        assertFalse(chain.op(23));
    }
    
    public void testOrChain_arrayFourFilters () {
        F1.ToBoolean<Number> chain = 
            F1Utils.orChain(toArray(
                    new LowerThan(Integer.valueOf(4)),
                    new LowerThan(Integer.valueOf(5)),
                    new LowerThan(Integer.valueOf(6)),
                    new LowerThan(Integer.valueOf(7))
                    ));
        
        assertTrue (chain.op( 3));
        assertTrue (chain.op( 4));
        assertTrue (chain.op( 5));
        assertTrue (chain.op( 6));
        assertFalse(chain.op(23));
    }
    
    //--------------------------------------------------------------------------
    
    public static Data data(int value) {
        return new Data (value);
    }
    
    public static class LowerThan implements F1.ToBoolean<Number> {
        private Number value;
        public LowerThan(Number value)
        {
            this.value = value;
        }
        public boolean op(Number param)
        {
            return value.intValue()>param.intValue();
        }
    }
    
    public static class Data implements Comparable<Number> {
        private Integer value;
        public Data(int value)
        {
            this.value = value;
        }
        public int compareTo(Number o)
        {
            return value.compareTo(o.intValue());
        }
    }
}

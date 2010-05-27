/* $Id$ */
package org.technbolts.util.functional;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.technbolts.util.ComparatorUtils;
import org.technbolts.util.functional.C1Utils;
import org.technbolts.util.functional.Container;
import org.technbolts.util.functional.EnumerableUtils;
import org.technbolts.util.functional.F1;
import org.technbolts.util.functional.F1Utils;
import org.technbolts.util.functional.F2;

import junit.framework.TestCase;

public class EnumerableUtilsTest extends TestCase
{
    private static final Integer ONE   = Integer.valueOf(1);
    private static final Integer TWO   = Integer.valueOf(2);
    private static final Integer THREE = Integer.valueOf(3);
    private static final Integer FOUR  = Integer.valueOf(4);
    private static final Integer FIVE  = Integer.valueOf(5);
    private static final Integer SIX   = Integer.valueOf(6);
    private static final Integer SEVEN = Integer.valueOf(7);
    
    public void testSelectFirstWithNullArray () {
        assertNull(EnumerableUtils.selectFirst((String[])null, F1Utils.<String>alwaysTrue()));
    }
    
    public void testSelectFirstWithNullIterable () {
        assertNull(EnumerableUtils.selectFirst((Iterable<String>)null, F1Utils.<String>alwaysTrue()));
    }
    
    public void testSelectAllWithNullArray () {
        List<String> res = EnumerableUtils.selectAll((String[])null, F1Utils.<String>alwaysTrue());
        assertNotNull(res);
        assertEquals(0, res.size());
    }
    
    public void testSelectAllWithNullIterable () {
        List<String> res = EnumerableUtils.selectAll((Iterable<String>)null, F1Utils.<String>alwaysTrue());
        assertNotNull(res);
        assertEquals(0, res.size());
    }
    
    public void testForAllReturningAnArrayContainer() {
        List<Integer> integerList = asList(ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN);
        
        Container<Integer> container = EnumerableUtils.forAll(ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN);
        assertNotNull(container);
        assertEquals(integerList, container.toList());
    }
    
    public void testForAllReturningAnIterableContainer() {
        List<Integer> integerList = asList(ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN);
        
        Container<Integer> container = EnumerableUtils.forAll(asList(ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN));
        assertNotNull(container);
        assertEquals(integerList, container.toList());
    }
    
    public void testCountAllEx1 () {
        // count all items greater than 4
        int count = EnumerableUtils.countAll(asList(ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN), F1Utils.greaterThan(4));
        assertEquals(3, count);
    }
    
    public void testCountAllEx2 () {
        // count all items lesser than 4
        int count = EnumerableUtils.countAll(asList(TWO,FOUR,SEVEN,THREE,ONE,SIX,FIVE), F1Utils.lesserThanOrEqualTo(4));
        assertEquals(4, count);
    }

    public void testFoldLeft () {
        F2<Integer, Data, Integer> maxFold = new F2<Integer, Data, Integer> () {
            public Integer op(Integer t1, Data t2)
            {
                if(t1.intValue()<t2.value.intValue())
                    return t2.value;
                return t1;
            }
        };
        List<Data> dataList = asDataList(TWO,FOUR,SEVEN,THREE,ONE,SIX,FIVE);
        Integer max = EnumerableUtils.foldLeft(dataList, maxFold, Integer.MIN_VALUE);
        assertEquals(SEVEN, max);
    }
    
    public void testForAllIterable () {
        List<Integer> integerList = asList(TWO,FOUR,SEVEN,THREE,ONE,SIX,FIVE);
        C1Utils.C1Collector<Integer> collector = new C1Utils.C1Collector<Integer> ();
        EnumerableUtils.forAll(integerList, collector);
        assertEquals(integerList, collector.getCollected());
    }
    
    public void testForAllIterableWithFilter () {
        List<Integer> integerList = asList(TWO,FOUR,SEVEN,THREE,ONE,SIX,FIVE);
        C1Utils.C1Collector<Integer> collector = new C1Utils.C1Collector<Integer> ();
        EnumerableUtils.forAll(integerList, F1Utils.greaterThan(4),collector);
        assertEquals(asList(SEVEN,SIX,FIVE), collector.getCollected());
    }
    
    public void testForAllUntil () {
        List<Integer> integerList = asList(TWO,FOUR,SEVEN,THREE,ONE,SIX,FIVE);
        final List<Integer> collected = new ArrayList<Integer> ();
        EnumerableUtils.forAllUntil(integerList, new F1.ToBoolean<Integer> () {
            public boolean op(Integer param)
            {
                collected.add(param);
                if(param.intValue()>4)
                    return false;
                return true;
            }
        });
        assertEquals(asList(TWO,FOUR,SEVEN), collected);
    }
    
    public void testRoundRobinEx0 () {
        List<Integer> integerList   = asList();
        List<Integer>[] distributed = EnumerableUtils.roundRobin(integerList, 3);
        assertNotNull(distributed);
        assertEquals (3, distributed.length);
        assertTrue (distributed[0].isEmpty());
        assertTrue (distributed[1].isEmpty());
        assertTrue (distributed[2].isEmpty());
    }
    
    public void testRoundRobinEx1 () {
        List<Integer> integerList   = asList(TWO);
        List<Integer>[] distributed = EnumerableUtils.roundRobin(integerList, 3);
        assertNotNull(distributed);
        assertEquals (3, distributed.length);
        assertEquals (asList(TWO), distributed[0]);
        assertTrue (distributed[1].isEmpty());
        assertTrue (distributed[2].isEmpty());
    }
    
    public void testRoundRobinEx2 () {
        List<Integer> integerList   = asList(TWO,FIVE);
        List<Integer>[] distributed = EnumerableUtils.roundRobin(integerList, 3);
        assertNotNull(distributed);
        assertEquals (3, distributed.length);
        assertEquals (asList(TWO), distributed[0]);
        assertEquals (asList(FIVE), distributed[1]);
        assertTrue (distributed[2].isEmpty());
    }
    
    public void testRoundRobinEx3 () {
        List<Integer> integerList   = asList(TWO,FOUR,SEVEN,THREE,ONE,SIX,FIVE);
        List<Integer>[] distributed = EnumerableUtils.roundRobin(integerList, 3);
        
        assertNotNull(distributed);
        assertEquals (3, distributed.length);
        assertEquals (asList(TWO,THREE,FIVE), distributed[0]);
        assertEquals (asList(FOUR,ONE),       distributed[1]);
        assertEquals (asList(SEVEN,SIX),      distributed[2]);
    }
    
    public void testSelectAllIterable () {
        List<Integer> integerList = asList(TWO,FOUR,SEVEN,THREE,ONE,SIX,FIVE);
        List<Integer> selected    = EnumerableUtils.selectAll(integerList, F1Utils.greaterThan(4));
        assertEquals(asList(SEVEN,SIX,FIVE), selected);
    }
    
    public void testSelectAllIterableFilterAndTransform () {
        List<Data> dataList    = asDataList(TWO,FOUR,SEVEN,THREE,ONE,SIX,FIVE);
        List<Integer> selected = EnumerableUtils.selectAll(dataList, F1Utils.greaterThan(data(4)), dataToInteger());
        assertEquals(asList(SEVEN,SIX,FIVE), selected);
    }
    
    public void testSelectAllIterableTransformAndFilter () {
        List<Data> dataList    = asDataList(TWO,FOUR,SEVEN,THREE,ONE,SIX,FIVE);
        List<Integer> selected = EnumerableUtils.selectAll(dataList, dataToInteger(), F1Utils.greaterThan(4));
        assertEquals(asList(SEVEN,SIX,FIVE), selected);
    }
    
    public void testSelectAllArray () {
        Integer[] integerArray = asList(TWO,FOUR,SEVEN,THREE,ONE,SIX,FIVE).toArray(new Integer[7]);
        List<Integer> selected    = EnumerableUtils.selectAll(integerArray, F1Utils.greaterThan(4));
        assertEquals(asList(SEVEN,SIX,FIVE), selected);
    }
    
    public void testSelectFirst () {
        List<Integer> dataList = asList(TWO,FOUR,SEVEN,THREE,ONE,SIX,FIVE);
        Integer found          = EnumerableUtils.selectFirst(dataList, F1Utils.greaterThan(4));
        assertEquals(SEVEN, found);
    }
    
    public void testSelectMax () {
        List<Integer> dataList = asList(TWO,FOUR,SEVEN,THREE,ONE,SIX,FIVE);
        Integer found          = EnumerableUtils.selectMax(dataList);
        assertEquals(SEVEN, found);
    }
    
    public void testSelectMax_withComparator () {
        List<Integer> dataList = asList(TWO,FOUR,SEVEN,THREE,ONE,SIX,FIVE);
        Comparator<Integer> comparator = ComparatorUtils.comparator();
        comparator    = ComparatorUtils.reverse(comparator);
        Integer found = EnumerableUtils.selectMax(comparator, dataList);
        assertEquals(ONE, found);
    }
    
    public void testSelectMaxTransform () {
        List<Data> dataList = asDataList(TWO,FOUR,SEVEN,THREE,ONE,SIX,FIVE);
        Integer found       = EnumerableUtils.selectMax(dataList, dataToInteger());
        assertEquals(SEVEN, found);
    }
    
    public void testSelectMaxWithEmptyList () {
        List<Integer> empty = Collections.emptyList();
        assertEquals(null, EnumerableUtils.selectMax(empty));
    }
    
    public void testSelectMin () {
        List<Integer> dataList = asList(TWO,FOUR,SEVEN,THREE,ONE,SIX,FIVE);
        Integer found          = EnumerableUtils.selectMin(dataList);
        assertEquals(ONE, found);
    }
    
    public void testSelectMin_withComparator () {
        List<Integer> dataList = asList(TWO,FOUR,SEVEN,THREE,ONE,SIX,FIVE);
        Integer found          = EnumerableUtils.selectMin(dataList);
        assertEquals(ONE, found);
    }
    
    public void testSelectMinTransform () {
        List<Data> dataList = asDataList(TWO,FOUR,SEVEN,THREE,ONE,SIX,FIVE);
        Integer found       = EnumerableUtils.selectMin(dataList, dataToInteger());
        assertEquals(ONE, found);
    }
    
    public void testSelectMinWithEmptyList () {
        List<Integer> empty = Collections.emptyList();
        assertEquals(null, EnumerableUtils.selectMin(empty));
    }
    
    
    public void testTransformAll () {
        List<Data> dataList       = asDataList(TWO,FOUR,SEVEN,THREE,ONE,SIX,FIVE);
        List<Integer> transformed = EnumerableUtils.transformAll(dataList, dataToInteger());
        assertEquals(asList(TWO,FOUR,SEVEN,THREE,ONE,SIX,FIVE), transformed);
    }
    
    private static F1<Data,Integer> dataToInteger() {
        return new F1<Data, Integer> () {
            public Integer op(Data t)
            {
                return t.value;
            }
        };
    }
    
    private static List<Data> asDataList(int...values) {
        List<Data> dataList = new ArrayList<Data> ();
        for(int value : values)
            dataList.add(data(value));
        return dataList;
    }
    
    private static Data data (int value) {
        return new Data (value);
    }
    
    private static class Data implements Comparable<Data> {
        Integer value;
        Data(int value) { this.value = value; }
        public int compareTo(Data other)
        {
            return value.compareTo(other.value);
        }
    }
}

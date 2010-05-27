/* $Id: CollectAndReduceUtilsTest.java,v 1.1 2009-07-22 13:03:55 arnauld Exp $ */
package org.technbolts.util.functional;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class CollectAndReduceUtilsTest extends TestCase
{

    public void testListMergerEx1 () {
        CollectAndReduceUtils.ListMerger<Integer> reducer = new CollectAndReduceUtils.ListMerger<Integer> ();
        List<Integer> one = Arrays.asList(1,2,3);
        List<Integer> two = Arrays.asList(4,5,6);
        List<Integer> reduced = reducer.reduce(one, two);
        assertEquals(6, reduced.size());
        assertEquals(Arrays.asList(1,2,3,4,5,6), reduced);
    }
    
    public void testListMergerEx2 () {
        CollectAndReduceUtils.ListMerger<Integer> reducer = new CollectAndReduceUtils.ListMerger<Integer> ();
        List<Integer> two = Arrays.asList(4,5,6);
        List<Integer> reduced = reducer.reduce(null, two);
        assertEquals(3, reduced.size());
        assertEquals(Arrays.asList(4,5,6), reduced);
    }
    
    public void testListMergerEx3 () {
        CollectAndReduceUtils.ListMerger<Integer> reducer = new CollectAndReduceUtils.ListMerger<Integer> ();
        List<Integer> one = Arrays.asList(1,2,3);
        List<Integer> reduced = reducer.reduce(one, null);
        assertEquals(3, reduced.size());
        assertEquals(Arrays.asList(1,2,3), reduced);
    }
    
}

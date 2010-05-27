/* $Id$ */
package org.technbolts.util.functional;

import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.TestCase;

public class C1UtilsTest extends TestCase
{

    public void testTimes0 () {
        C1Utils.times(0).op(new C1<Integer> () {
            public void op(Integer val)
            {
                fail("Should not be called");
            }
        });
    }
    
    public void testTimes1 () {
        final AtomicInteger counter = new AtomicInteger ();
        C1Utils.times(1).op(new C1<Integer> () {
            public void op(Integer val)
            {
                assertNotNull(val);
                assertEquals(0, val.intValue());
                assertEquals("should be called only once", 0, counter.get());
                counter.incrementAndGet();
            }
        });
        assertEquals("should be called only once", 1, counter.get());
    }
    
    public void testTimes5 () {
        final AtomicInteger counter = new AtomicInteger ();
        C1Utils.times(5).op(new C1<Integer> () {
            public void op(Integer val)
            {
                assertNotNull(val);
                assertEquals(counter.get(), val.intValue());
                counter.incrementAndGet();
            }
        });
        assertEquals(5, counter.get());
    }
}

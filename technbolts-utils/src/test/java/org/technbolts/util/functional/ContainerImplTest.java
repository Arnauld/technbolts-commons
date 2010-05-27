/* $Id$ */
package org.technbolts.util.functional;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.technbolts.util.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ContainerImplTest extends TestSuite
{
    private static List<Integer> PredefinedRawValues = asList(13, 1, 3, 17, 11, 7, 2, 5);
    private static List<Data>    PredefinedDataList  = toDataList(PredefinedRawValues);
    private static Data[]        PredefinedDataArray = PredefinedDataList.toArray(new Data[PredefinedDataList.size()]);
    
    public static Test suite() {
        TestSuite suite = new TestSuite("Tests ContainerImpl");
        suite.addTestSuite(ArrayContainerPredefinedTests.class);
        suite.addTestSuite(IterableContainerPredefinedTests.class);
        return suite;
    }
    
    public static class ArrayContainerPredefinedTests extends ContainerPredefinedTests {
        public ArrayContainerPredefinedTests()
        {
            super (new ContainerImpl.ArrayContainer<Data>(PredefinedDataArray));
        }
    }
    
    public static class IterableContainerPredefinedTests extends ContainerPredefinedTests {
        public IterableContainerPredefinedTests()
        {
            super (new ContainerImpl.IterableContainer<Data>(PredefinedDataList));
        }
    }
    
    public static class ContainerPredefinedTests extends TestCase {
        private Container<Data> container;
        public ContainerPredefinedTests(Container<Data> container)
        {
            this.container = container;
        }
        public void testNotNull () {
            assertThat(container, notNullValue());
        }
        public void testToList () {
            assertThat(container.toList(), equalTo(PredefinedDataList));
        }
        public void testApply () {
            C1Utils.C1Collector<Data> collector = C1Utils.newC1Collector();
            container.apply(collector);
            assertThat(collector.getCollected(), equalTo(PredefinedDataList));
        }
        public void testApplyUntilEx1 () {
            F1Utils.F1ToBooleanCollector<Data> collector = F1Utils.newF1ToBooleanCollector();
            container.applyUntil(collector);
            assertThat(collector.getCollected(), equalTo(PredefinedDataList));
        }
        public void testApplyUntilEx2 () {
            final List<Data> collected = new ArrayList<Data> ();
            container.applyUntil(new F1.ToBoolean<Data> () {
                public boolean op(Data param)
                {
                    collected.add(param);
                    return (param.value!=17);
                }
            });
            assertThat(collected, equalTo(toDataList(13, 1, 3, 17)));
        }
        public void testFilterEx1 () {
            List<Data> filteredContent =
                container.filter(new F1.ToBoolean<Data> () {
                    public boolean op(Data param)
                    {
                        return (param.value<10);
                    }
                }).toList();
            assertThat(filteredContent, equalTo(asList(data(1), data(3), data(7), data(2), data(5))));
        }
        public void testFilterEx2 () {
            List<Data> filteredContent =
                container.filter(new F1.ToBoolean<Data> () {
                    public boolean op(Data param)
                    {
                        return (param.value>10);
                    }
                }).toList();
            assertThat(filteredContent, equalTo(asList(data(13), data(17), data(11))));
        }
        public void testFilterEx3 () {
            List<Data> filteredContent =
                container.filter(new F1.ToBoolean<Data> () {
                    public boolean op(Data param)
                    {
                        return (param.value>100);
                    }
                }).toList();
            assertThat(filteredContent.isEmpty(), equalTo(true));
        }
        public void testTransformEx1 () {
            List<Integer> transformedContent =
                container.transform(new F1<Data,Integer> () {
                    public Integer op(Data param)
                    {
                        return (param.value);
                    }
                }).toList();
            assertThat(transformedContent, equalTo(PredefinedRawValues));
        }
        public void testFilterAndTransformEx1 () {
            List<Integer> transformedContent =
                container.filter(new F1.ToBoolean<Data> () {
                                public boolean op(Data param)
                                {
                                    return (param.value<10);
                                }
                            })
                         .transform(new F1<Data,Integer> () {
                                public Integer op(Data param)
                                {
                                    return (param.value);
                                }
                            })
                         .toList();
            assertThat(transformedContent, equalTo(asList(1, 3, 7, 2, 5)));
        }
        public void testFilterAndTransformEx2 () {
            List<Integer> transformedContent =
                container.filter(new F1.ToBoolean<Data> () {
                                public boolean op(Data param)
                                {
                                    return (param.value>10);
                                }
                            })
                         .transform(new F1<Data,Integer> () {
                                public Integer op(Data param)
                                {
                                    return (param.value);
                                }
                            })
                         .toList();
            assertThat(transformedContent, equalTo(asList(13, 17, 11)));
        }
        public void testFilterAndTransformEx3 () {
            List<Integer> transformedContent =
                container.filter(new F1.ToBoolean<Data> () {
                                public boolean op(Data param)
                                {
                                    return (param.value>100);
                                }
                            })
                         .transform(new F1<Data,Integer> () {
                                public Integer op(Data param)
                                {
                                    return (param.value);
                                }
                            })
                         .toList();
            assertThat(transformedContent.isEmpty(), equalTo(true));
        }
        public void testTransformAndFilterEx1 () {
            List<Integer> transformedContent =
                container.transform(new F1<Data,Integer> () {
                                public Integer op(Data param)
                                {
                                    return (param.value);
                                }
                            })
                         .filter(new F1.ToBoolean<Integer> () {
                                public boolean op(Integer param)
                                {
                                    return (param<10);
                                }
                            })
                         .toList();
            assertThat(transformedContent, equalTo(asList(1, 3, 7, 2, 5)));
        }
        public void testTransformAndFilterEx2 () {
            List<Integer> transformedContent =
                container.transform(new F1<Data,Integer> () {
                                public Integer op(Data param)
                                {
                                    return (param.value);
                                }
                            })
                         .filter(new F1.ToBoolean<Integer> () {
                                public boolean op(Integer param)
                                {
                                    return (param>10);
                                }
                            })
                         .toList();
            assertThat(transformedContent, equalTo(asList(13, 17, 11)));
        }
        public void testTransformAndFilterEx3 () {
            List<Integer> transformedContent =
                container.transform(new F1<Data,Integer> () {
                                public Integer op(Data param)
                                {
                                    return (param.value);
                                }
                            })
                         .filter(new F1.ToBoolean<Integer> () {
                                public boolean op(Integer param)
                                {
                                    return (param>100);
                                }
                            })
                         .toList();
            assertThat(transformedContent.isEmpty(), equalTo(true));
        }
        public void testTransformAndTransform () {
            List<Data> transformedContent =
                container.transform(new F1<Data,Integer> () {
                                public Integer op(Data param)
                                {
                                    return (param.value);
                                }
                            })
                         .transform(new F1<Integer, Data> () {
                                public Data op(Integer param)
                                {
                                    return data(param);
                                }
                            })
                         .toList();
            assertThat(transformedContent, equalTo(PredefinedDataList));
        }
    }
    
    private static Data data(int value) {
        return new Data (value);
    }
    
    private static List<Data> toDataList(int...values) {
        List<Data> returned = new ArrayList<Data> ();
        for(int value : values)
            returned.add(data(value));
        return returned;
    }
            
    private static List<Data> toDataList(List<Integer> values) {
        List<Data> returned = new ArrayList<Data> ();
        for(Integer value : values)
            returned.add(data(value));
        return returned;
    }
    
    private static class Data {
        private Integer value;
        public Data (int value) {
            this.value = value;
        }
        @Override
        public boolean equals(Object obj)
        {
            if(obj==null || !(obj instanceof Data))
                return false;
            return value.equals(((Data)obj).value);
        }
        @Override
        public int hashCode()
        {
            return value;
        }
    }
}

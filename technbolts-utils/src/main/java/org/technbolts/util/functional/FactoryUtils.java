/* $Id: FactoryUtils.java,v 1.2 2009-07-22 17:08:07 arnauld Exp $ */
package org.technbolts.util.functional;

import java.util.ArrayList;
import java.util.List;

public class FactoryUtils
{
    public static class SingletonC1Factory<T> implements C1.Factory<T>
    {
        private C1<T> singleton;
        public SingletonC1Factory(C1<T> singleton) {
            this.singleton = singleton;
        }
        public C1<T> newFunction()
        {
            return singleton;
        }
    }
    
    public static class SingletonF1ToBooleanFactory<T> implements F1.ToBoolean.Factory<T>
    {
        private F1.ToBoolean<T> singleton;
        public SingletonF1ToBooleanFactory(F1.ToBoolean<T> singleton) {
            this.singleton = singleton;
        }
        public F1.ToBoolean<T> newFunction()
        {
            return singleton;
        }
    }
    public static class ComposableF1ToBooleanFactory<T> implements F1.ToBoolean.Factory<T>
    {
        private List<F1.ToBoolean.Factory<T>> factories;
        
        public ComposableF1ToBooleanFactory()
        {
            factories = new ArrayList<F1.ToBoolean.Factory<T>> ();
        }
        
        public void addFactory(F1.ToBoolean.Factory<T> factory) {
            factories.add(factory);
        }
        public void addSingletonFactory(F1.ToBoolean<T> function) {
            factories.add(new SingletonF1ToBooleanFactory<T>(function));
        }
        
        public F1.ToBoolean<T> newFunction()
        {
            if(factories.size()<1)
                return null;
            else{
                F1.ToBoolean<T> function = factories.get(0).newFunction();
                for(int i=1;i<factories.size();i++)
                {
                    function = F1Utils.andChain(function, factories.get(i).newFunction());
                }
                return function;
            }
        }

        public boolean isEmpty()
        {
            return factories.isEmpty();
        }

        public boolean isNotEmpty()
        {
            return isEmpty()==false;
        }
    }
}

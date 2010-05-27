/* $Id$ */
package org.technbolts.dto.configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.technbolts.util.functional.F1;

import static org.technbolts.util.functional.EnumerableUtils.forAll;
import static org.technbolts.util.functional.EnumerableUtils.selectAll;
import static org.technbolts.util.functional.F1Utils.andChain;
import static org.technbolts.util.functional.F1Utils.negate;

public class ClassFieldUtils
{
    public static List<ClassField> collectClassFields(Class<?> clazz) throws IllegalArgumentException, IllegalAccessException, AssertionError
    {
        Field[] fields = clazz.getDeclaredFields();
        return forAll(fields).filter(andChain(isClassField(),isStaticField())).transform(staticFieldToClassField()).toList();
    }

    public static void assertClassFields(Class<?> clazz) throws IllegalArgumentException, IllegalAccessException, AssertionError
    {
        assertClassFields(clazz, collectClassFields(clazz));
    }
    
    @SuppressWarnings("unchecked")
    public static void assertClassFields(Class clazz, List<ClassField> classFields) throws IllegalArgumentException, IllegalAccessException, AssertionError
    {
        Field[] fields = clazz.getDeclaredFields();
        List<String> fieldNames = selectAll(fields, negate(isClassField()), fieldName());
        
        for(ClassField clazzField : classFields) {
            String fieldName   = clazzField.getFieldName();
            Class<?> definedIn = clazzField.getDefinedIn();
            
            if(!fieldNames.contains(fieldName))
                throw new AssertionError("Unknown field got: "+fieldName+" expected one of: "+StringUtils.join(fieldNames, ", ")+".");
            if(!clazz.equals(definedIn))
                throw new AssertionError("Classes does not match got: "+definedIn+" expected: "+clazz+".");
        }
    }
    
    public static F1<Field, ClassField> staticFieldToClassField()
    {
        return new F1<Field, ClassField>() {
            public ClassField op(Field field)
            {
                try
                {
                    return (ClassField)field.get(null);
                }
                catch (IllegalArgumentException e)
                {
                    throw new RuntimeException (e);
                }
                catch (IllegalAccessException e)
                {
                    throw new RuntimeException (e);
                }
            }
        };
    }

    public static F1<Field,String> fieldName () {
        return new F1<Field,String>()
        {
            public String op(Field field)
            {
                return field.getName();
            }
        };
    }
    
    public static F1.ToBoolean<Field> isStaticField() {
        return new F1.ToBoolean<Field>()
        {
            public boolean op(Field field)
            {
                return Modifier.isStatic(field.getModifiers());
            }
        };
    }
    

    public static F1.ToBoolean<Field> isClassField () {
        return new F1.ToBoolean<Field>()
        {
            public boolean op(Field field)
            {
                return field.getType().equals(ClassField.class);
            }
        };
    }
}

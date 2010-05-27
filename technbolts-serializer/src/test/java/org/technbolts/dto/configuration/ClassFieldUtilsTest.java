/* $Id$ */
package org.technbolts.dto.configuration;

import static java.util.Arrays.asList;

import java.util.List;

import junit.framework.TestCase;

/**
 * ClassFieldTestUtils
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class ClassFieldUtilsTest extends TestCase
{

    /* **************************
     * ValidClassFieldSingleField
     * ***************************/
    
    public static class ValidClassFieldSingleField {
        @SuppressWarnings("unused")
        private String email;
        public static final ClassField emailField = ClassField.createOrFail(ValidClassFieldSingleField.class, "email");
    }
    
    public void testAssertClassFields_validClassFieldSingleField () throws IllegalArgumentException, IllegalAccessException {
        ClassFieldUtils.assertClassFields(ValidClassFieldSingleField.class);
    }
    
    public void testCollectClassFields_validClassFieldSingleField () throws IllegalArgumentException, IllegalAccessException {
        List<ClassField> clazzFields = ClassFieldUtils.collectClassFields(ValidClassFieldSingleField.class);
        assertNotNull(clazzFields);
        assertEquals(1, clazzFields.size());
        assertEquals("email", clazzFields.get(0).getFieldName());
        assertEquals(ValidClassFieldSingleField.class, clazzFields.get(0).getDefinedIn());
    }
    
    /* **************************
     * ValidClassFieldMultipleFields
     * ***************************/
    
    public static class ValidClassFieldMultipleFields {
        @SuppressWarnings("unused")
        private String email;
        public static final ClassField emailField = ClassField.createOrFail(ValidClassFieldMultipleFields.class, "email");
        
        @SuppressWarnings("unused")
        private String name;
        public static final ClassField nameField = ClassField.createOrFail(ValidClassFieldMultipleFields.class, "name");
    }
    
    public void testAssertClassFields_validClassFieldMultipleFields () throws IllegalArgumentException, IllegalAccessException {
        ClassFieldUtils.assertClassFields(ValidClassFieldMultipleFields.class);
    }
    
    public void testCollectClassFields_validClassFieldMultipleFields () throws IllegalArgumentException, IllegalAccessException {
        List<ClassField> clazzFields = ClassFieldUtils.collectClassFields(ValidClassFieldMultipleFields.class);
        assertNotNull(clazzFields);
        assertEquals(2, clazzFields.size());
        
        assertEquals("email", clazzFields.get(0).getFieldName());
        assertEquals(ValidClassFieldMultipleFields.class, clazzFields.get(0).getDefinedIn());
        assertEquals("name", clazzFields.get(1).getFieldName());
        assertEquals(ValidClassFieldMultipleFields.class, clazzFields.get(0).getDefinedIn());
    }
    
    public void testAssertClassFields_wrongFieldName () throws IllegalArgumentException, IllegalAccessException {
        try
        {
            ClassFieldUtils.assertClassFields(
                    ValidClassFieldMultipleFields.class, 
                    asList(new ClassField(ValidClassFieldMultipleFields.class, "nome")));
            fail("An assertion error should have been raised");
        }
        catch (AssertionError e)
        {
            assertTrue (e.getMessage().contains("Unknown field got"));
        }
    }
    
    public void testAssertClassFields_wrongClass () throws IllegalArgumentException, IllegalAccessException {
        try
        {
            ClassFieldUtils.assertClassFields(
                    ValidClassFieldMultipleFields.class,
                    asList(new ClassField(ValidClassFieldSingleField.class, "email")));
            fail("An assertion error should have been raised");
        }
        catch (AssertionError e)
        {
            assertTrue (e.getMessage().contains("Classes does not match"));
        }
    }
    
    public void testInvalidClassFieldWrongClassAndFieldName () throws IllegalArgumentException, IllegalAccessException {
        try
        {
            ClassFieldUtils.assertClassFields(
                    ValidClassFieldMultipleFields.class,
                    asList(new ClassField(String.class, "nome")));
            fail("An assertion error should have been raised");
        }
        catch (AssertionError e)
        {
            String msg = e.getMessage();
            assertTrue (msg.contains("Classes does not match")
                    ||  msg.contains("Unknown field got"));
        }
    }
}

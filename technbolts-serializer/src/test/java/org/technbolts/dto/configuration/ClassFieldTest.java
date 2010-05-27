/* $Id$ */
package org.technbolts.dto.configuration;

import org.technbolts.dto.configuration.ClassField.FieldNotFoundException;

import junit.framework.TestCase;

public class ClassFieldTest extends TestCase
{
    public static class Data {
        @SuppressWarnings("unused")
        private   String stringPrivate;
        protected String stringProtected;
        public    String stringPublic;
                  String stringDefault;
    }
    
    public void testCreateOrFail_nullClass () {
        try
        {
            ClassField.createOrFail(null, "fieldName");
            fail("Class should be mandatory");
        }
        catch (IllegalArgumentException e)
        {
            //e.printStackTrace();
        }
    }
    
    public void testCreateOrFail_nullFieldName () {
        try
        {
            ClassField.createOrFail(Data.class, null);
            fail("FieldName should be mandatory");
        }
        catch (IllegalArgumentException e)
        {
            //e.printStackTrace();
        }
    }
    
    public void testCreateOrFail_nullClassAndFieldName () {
        try
        {
            ClassField.createOrFail(null, null);
            fail("FieldName and class should be mandatory");
        }
        catch (IllegalArgumentException e)
        {
            //e.printStackTrace();
        }
    }
    
    public void testConstructor_nullClass () {
        try
        {
            new ClassField(null, "fieldName");
            fail("Class should be mandatory");
        }
        catch (IllegalArgumentException e)
        {
            //e.printStackTrace();
        }
    }
    
    public void testConstructor_nullFieldName () {
        try
        {
            new ClassField(Data.class, null);
            fail("FieldName should be mandatory");
        }
        catch (IllegalArgumentException e)
        {
            //e.printStackTrace();
        }
    }
    
    public void testConstructor_nullClassAndFieldName () {
        try
        {
            new ClassField(null, null);
            fail("FieldName and class should be mandatory");
        }
        catch (IllegalArgumentException e)
        {
            //e.printStackTrace();
        }
    }
    
    public void testCreateOrFail_caseValidPrivate () {
        parametrizedCreateOrFail_caseValid("stringPrivate");
    }
    
    public void testCreateOrFail_caseValidProtected () {
        parametrizedCreateOrFail_caseValid("stringProtected");
    }
    
    public void testCreateOrFail_caseValidPublic () {
        parametrizedCreateOrFail_caseValid("stringPublic");
    }
    
    public void testCreateOrFail_caseValidDefault () {
        parametrizedCreateOrFail_caseValid("stringDefault");
    }
    
    public void testCreateOrFail_invalidDefault () {
        try
        {
            ClassField.createOrFail(Data.class, "some_weird_name_for");
            fail("A unknown field should raise an exception");
        }
        catch (FieldNotFoundException e)
        {
            //e.printStackTrace();
        }
    }
    
    private void parametrizedCreateOrFail_caseValid(String fieldName) {
        ClassField field = ClassField.createOrFail(Data.class, fieldName);
        assertNotNull(field);
        assertEquals(field.getDefinedIn(), Data.class);
        assertEquals(field.getFieldName(), fieldName);
    }
}

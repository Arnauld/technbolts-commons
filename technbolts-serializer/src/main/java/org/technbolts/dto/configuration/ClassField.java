/* $Id$ */
package org.technbolts.dto.configuration;

import java.lang.reflect.Field;

import org.technbolts.dto.configuration.annotation.OmitClass;

/**
 * ClassField acts as a tuple to store a field name of a specific class.
 * 
 * <p>This facilitates meta-informations manipulation in the code, especially
 * when a field name is required such as in <code>ConfigurationHandler</code>.
 * </p>
 * 
 * <p>
 * Furthermore this allow a better control in case of field renaming, string
 * value may be out-dated while <code>createOrFail</code> will simply failed
 * and an exception will be raised.
 * </p>
 * 
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 * @see #createOrFail(Class, String)
 * @see ConfigurationHandler#aliasField(String, Class, String)
 * @see ConfigurationHandler#omitField(Class, String)
 */
@OmitClass
public class ClassField
{
    /**
     * Signals that the class doesn't have a field of a specified name.
     *
     * FieldNotFoundException runtime version of <code>NoSuchFieldException</code>.
     * @see NoSuchFieldException
     */
    @SuppressWarnings("serial")
    public static class FieldNotFoundException extends RuntimeException {
        /**
         * @param message
         * @param cause
         */
        public FieldNotFoundException(String message)
        {
            super(message);
        }
        /**
         * @param message
         * @param cause
         */
        public FieldNotFoundException(String message, Throwable cause)
        {
            super(message, cause);
        }
    }
    
    /**
     * Create a new <code>ClassField</code> by retrieving the field from
     * the specified class using its name. If the field is not found within 
     * the class an exception is raised.
     *  
     * @param definedIn - The class that should contain the field.
     * @param fieldName - The name of the field as it should be declared in the corresponding class.
     * @return
     * @throws FieldNotFoundException if the field cannot be found within the class.
     *           This may happen if the field has been renamed or is misspelled.
     * @throws IllegalArgumentException if either the class or the field name
     *           is empty.
     * @see FieldNotFoundException
     */
    public static ClassField createOrFail(Class<?> definedIn, String fieldName) {
        
        if(definedIn==null)
            throw new IllegalArgumentException("Clazz is mandatory");
        if(fieldName==null)
            throw new IllegalArgumentException("FieldName is mandatory");
        
        try
        {
            Field field = definedIn.getDeclaredField(fieldName);
            if(field==null)
                throw new FieldNotFoundException("No such field <"+fieldName+">");
            return new ClassField (definedIn, fieldName);
        }
        catch (SecurityException e)
        {
            throw new FieldNotFoundException("Failed to retrieve field <"+fieldName+"> from class: "+definedIn.getName(), e);
        }
        catch (NoSuchFieldException e)
        {
            throw new FieldNotFoundException("Failed to retrieve field <"+fieldName+"> from class: "+definedIn.getName(), e);
        }
    }
    
    private final Class<?> definedIn;
    private final String fieldName;
    
    /**
     * @param definedIn - The class that should contain the field.
     * @param fieldName - The name of the field as it should be declared in the corresponding class.
     */
    public ClassField(Class<?> definedIn, String fieldName)
    {
        if(definedIn==null)
            throw new IllegalArgumentException("Clazz is mandatory");
        if(fieldName==null)
            throw new IllegalArgumentException("FieldName is mandatory");
        this.definedIn = definedIn;
        this.fieldName = fieldName;
    }
    
    /**
     * The class that should contain the field.
     * @return
     */
    public Class<?> getDefinedIn()
    {
        return definedIn;
    }
    
    /**
     * The name of the field as it should be declared in the corresponding class.
     * @return
     */
    public String getFieldName()
    {
        return fieldName;
    }
    
    @Override
    public int hashCode()
    {
        return fieldName.hashCode()+17*definedIn.hashCode();
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if(obj==this)
            return true;
        if(obj==null || !(obj instanceof ClassField))
            return false;
        ClassField other = (ClassField)obj;
        return areClassDefinedEquals(other) && areFieldNameEquals(other);
    }
    
    public boolean areClassDefinedEquals(ClassField other) {
        return other.getDefinedIn().equals(getDefinedIn());
    }
    public boolean areFieldNameEquals(ClassField other) {
        return other.getFieldName().equals(getFieldName());
    }
}

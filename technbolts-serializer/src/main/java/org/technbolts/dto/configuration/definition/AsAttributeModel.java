/* $Id$ */
package org.technbolts.dto.configuration.definition;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * AsAttributeModel
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 * @see org.technbolts.dto.configuration.annotation.AsAttribute
 */
public class AsAttributeModel
{
    private Class<?> definedIn;
    private String fieldName;
    
    public AsAttributeModel(Class<?> definedIn, String fieldName)
    {
        this.definedIn = definedIn;
        this.fieldName = fieldName;
    }

    public Class<?> getDefinedIn()
    {
        return definedIn;
    }

    public void setDefinedIn(Class<?> definedIn)
    {
        this.definedIn = definedIn;
    }

    public String getFieldName()
    {
        return fieldName;
    }
    
    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        return EqualsBuilder.reflectionEquals(obj, this);
    }
    
    @Override
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}

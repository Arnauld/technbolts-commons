/* $Id$ */
package org.technbolts.dto.configuration.definition;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * AsAttributeModel
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 * @see org.technbolts.dto.configuration.annotation.AsAttribute
 */
public class AsAttributeModel
{
    private Class<?> type;
    private String fieldName;
    
    public AsAttributeModel(Class<?> type, String fieldName)
    {
        this.type  = type;
        this.fieldName = fieldName;
    }

    public Class<?> getType()
    {
        return type;
    }

    public void setType(Class<?> type)
    {
        this.type = type;
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

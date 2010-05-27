/* $Id$ */
package org.technbolts.dto.configuration.definition;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * ConverterModel
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 * @see org.technbolts.dto.configuration.annotation.Converter
 */
public class ConverterModel
{
    private Object converter;
    private Class<?> type;
    private String fieldName;
    
    
    public ConverterModel(Class<?> type, Object converter)
    {
        this.converter = converter;
        this.type  = type;
    }
    
    public ConverterModel(Class<?> type, String fieldName, Object converter)
    {
        this.converter = converter;
        this.fieldName = fieldName;
        this.type  = type;
    }
    
    public boolean isFieldConverter ()
    {
        return (fieldName!=null);
    }

    public Object getConverter()
    {
        return converter;
    }

    public void setConverter(Object converter)
    {
        this.converter = converter;
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

/* $Id$ */
package org.technbolts.dto.configuration.definition;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * AliasModel
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 * @see #com.eptica.dto.configuration.annotation.Alias
 */
public class AliasModel
{
    private String alias;
    private String fieldName;
    private Class<?> type;
    
    public AliasModel(String alias, Class<?> type)
    {
        this.alias = alias;
        this.type  = type;
    }
    
    public AliasModel(String alias, Class<?> type, String fieldName)
    {
        this.alias = alias;
        this.type  = type;
        this.fieldName = fieldName;
    }
    
    public boolean isFieldAlias () {
        return (fieldName!=null);
    }

    public String getAlias()
    {
        return alias;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    public String getFieldName()
    {
        return fieldName;
    }

    public void setFieldName(String fieldName)
    {
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
    
    @Override
    public String toString()
    {
        return type.getSimpleName()+"#"+alias+((fieldName==null)?"":("{"+fieldName+"}"));
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

/* $Id$ */
package org.technbolts.dto.configuration.definition;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * ImplicitModel
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 * @see org.technbolts.dto.configuration.annotation.Implicit
 */
public class ImplicitModel
{
    private Class<?> definedIn;
    private String fieldName;
    private String itemFieldName;
    private Class<?> itemType;
    
    public ImplicitModel() {
    }
    
    public ImplicitModel(Class<?> definedIn, String fieldName)
    {
        this.definedIn = definedIn;
        this.fieldName = fieldName;
    }
    
    public ImplicitModel(Class<?> definedIn, String fieldName,
            String itemFieldName, Class<?> itemType)
    {
        this.definedIn = definedIn;
        this.fieldName = fieldName;
        this.itemFieldName = itemFieldName;
        this.itemType = itemType;
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

    public String getItemFieldName()
    {
        return itemFieldName;
    }

    public void setItemField(String itemFieldName, Class<?> itemType)
    {
        this.itemFieldName = itemFieldName;
    }

    public Class<?> getItemType()
    {
        return itemType;
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

/* $Id$ */
package org.technbolts.dto.testmodel;

import org.technbolts.dto.configuration.annotation.Alias;
import org.junit.Ignore;

import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.configuration.annotation.Aliases;
import org.technbolts.dto.configuration.annotation.AsAttribute;

@Ignore
public class TestDataSubClass extends TestData
{
    @Aliases({
        @Alias(value="attribute", until=Version.V0),
        @Alias(value="subclass_attribute", since=Version.V1, until=Version.V1),
        @Alias(value="sub_attribute", since= Version.V2)})
    @AsAttribute(since=Version.V2)
    private Long subAttribute;
    
    public Long getSubAttribute()
    {
        return subAttribute;
    }
    
    public void setSubAttribute(Long subAttribute)
    {
        this.subAttribute = subAttribute;
    }
}

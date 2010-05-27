/* $Id$ */
package org.technbolts.dto.testmodel;

import org.technbolts.dto.configuration.Version;
import org.junit.Ignore;

import org.technbolts.dto.configuration.annotation.Alias;
import org.technbolts.dto.configuration.annotation.Aliases;
import org.technbolts.dto.configuration.annotation.AsAttribute;
import org.technbolts.dto.configuration.annotation.Since;

@Aliases({
    @Alias(value="test_data", until= Version.V1),
    @Alias(value="data",      since=Version.V2)})
@Ignore
public class TestData
{
    @Since(Version.V0)
    @Aliases({
        @Alias(value="equality", until=Version.V0),
        @Alias(value="equals",   since=Version.V1)
        })
    private int count;
    
    
    @Aliases({
        @Alias(value="name", until=Version.V1),
        @Alias(value="fullname", since=Version.V2)})
    private String name;

    @Aliases({
        @Alias(value="id", until=Version.V0),
        @Alias(value="identifier", since=Version.V1, until=Version.V1),
        @Alias(value="uuid", since=Version.V2)})
    @AsAttribute(since=Version.V2)
    private String id;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
    
    public int getCount()
    {
        return count;
    }
    
    public void setCount(int count)
    {
        this.count = count;
    }
}

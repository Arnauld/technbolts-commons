/* $Id$ */
package org.technbolts.dto.configuration;

import junit.framework.TestCase;

import org.technbolts.dto.testmodel.TestEnum;

public class EnumAliasHelperTest extends TestCase
{
    private EnumAliasHelper<TestEnum> helper;
    
    @Override
    protected void setUp() throws Exception
    {
        helper = new EnumAliasHelper<TestEnum>(TestEnum.class);
    }

    public void testFromStringV0 () {
        Version version = Version.V0;
        assertEquals(TestEnum.Equals,    helper.fromString("equality",   version));
        assertEquals(null,               helper.fromString("equals",     version));
        assertEquals(null,               helper.fromString("contains",   version));
        assertEquals(TestEnum.StartsWith,helper.fromString("startsWith", version));
        
    }
    
    public void testToStringV0 () {
        Version version = Version.V0;
        assertEquals("equality", helper.toString(TestEnum.Equals,     version));
        assertEquals(null,       helper.toString(TestEnum.Contains,   version));
        assertEquals("startsWith",helper.toString(TestEnum.StartsWith,version));
    }
    
    public void testFromStringV1 () {
        Version version = Version.V1;
        assertEquals(TestEnum.Equals,    helper.fromString("equals",     version));
        assertEquals(TestEnum.Contains,  helper.fromString("contains",   version));
        assertEquals(TestEnum.StartsWith,helper.fromString("startsWith", version));
    }
    
    public void testToStringV1 () {
        Version version = Version.V1;
        assertEquals("equals",    helper.toString(TestEnum.Equals,    version));
        assertEquals("contains",  helper.toString(TestEnum.Contains,  version));
        assertEquals("startsWith",helper.toString(TestEnum.StartsWith,version));
    }
    
    public void testFromStringV2 () {
        Version version = Version.V2;
        assertEquals(TestEnum.Equals,   helper.fromString("equals",    version));
        assertEquals(TestEnum.Contains, helper.fromString("contains",  version));
        assertEquals(null,              helper.fromString("startsWith",version));
    }
    
    public void testToStringV2 () {
        Version version = Version.V2;
        assertEquals("equals",   helper.toString(TestEnum.Equals,    version));
        assertEquals("contains", helper.toString(TestEnum.Contains,  version));
        assertEquals(null,       helper.toString(TestEnum.StartsWith,version));
    }
}

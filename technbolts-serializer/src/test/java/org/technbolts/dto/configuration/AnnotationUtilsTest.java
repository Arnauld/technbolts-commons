/* $Id$ */
package org.technbolts.dto.configuration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.reflect.Field;

import org.technbolts.dto.configuration.annotation.Alias;
import org.technbolts.dto.configuration.annotation.AnnotationUtils;
import junit.framework.TestCase;

import org.technbolts.dto.testmodel.TestData;
import org.technbolts.dto.testmodel.TestEnum;

public class AnnotationUtilsTest extends TestCase
{
    @Override
    protected void setUp() throws Exception
    {
    }
    
    public void testIsFieldDefinedV0 () throws Exception {
        Field fEquals     = TestEnum.class.getDeclaredField("Equals");
        Field fContains   = TestEnum.class.getDeclaredField("Contains");
        Field fStartsWith = TestEnum.class.getDeclaredField("StartsWith");
        
        assertTrue (AnnotationUtils.isFieldDefined(fEquals,     Version.V0));
        assertFalse(AnnotationUtils.isFieldDefined(fContains,   Version.V0));
        assertTrue (AnnotationUtils.isFieldDefined(fStartsWith, Version.V0));
    }
    
    public void testIsFieldDefinedV1 () throws Exception {
        Field fEquals     = TestEnum.class.getDeclaredField("Equals");
        Field fContains   = TestEnum.class.getDeclaredField("Contains");
        Field fStartsWith = TestEnum.class.getDeclaredField("StartsWith");
        
        assertTrue (AnnotationUtils.isFieldDefined(fEquals,     Version.V1));
        assertTrue (AnnotationUtils.isFieldDefined(fContains,   Version.V1));
        assertTrue (AnnotationUtils.isFieldDefined(fStartsWith, Version.V1));
    }
    
    public void testIsFieldDefinedV2 () throws Exception {
        Field fEquals     = TestEnum.class.getDeclaredField("Equals");
        Field fContains   = TestEnum.class.getDeclaredField("Contains");
        Field fStartsWith = TestEnum.class.getDeclaredField("StartsWith");
        
        assertTrue (AnnotationUtils.isFieldDefined(fEquals,     Version.V2));
        assertTrue (AnnotationUtils.isFieldDefined(fContains,   Version.V2));
        assertFalse(AnnotationUtils.isFieldDefined(fStartsWith, Version.V2));
    }
    
    public void testAliasEnumV0 () throws Exception {
        Field fEquals = TestEnum.class.getDeclaredField("Equals");
        
        Alias fEqualsAlias = AnnotationUtils.getAlias(fEquals, Version.V0, true);
        assertThat (fEqualsAlias, notNullValue());
        assertThat (fEqualsAlias.value(), equalTo("equality"));
        
        Field fContains = TestEnum.class.getDeclaredField("Contains");
        Alias fContainsAlias = AnnotationUtils.getAlias(fContains, Version.V0, true);
        assertThat (fContainsAlias, notNullValue());
        assertThat (fContainsAlias.value(), equalTo("contains"));
    }
    
    public void testAliasEnumV1 () throws Exception {
        Field[] fields = TestEnum.class.getFields();
        for(Field field : fields) {
            if(field.isEnumConstant()==false)
                continue;
            
            Alias alias = AnnotationUtils.getAlias(field, Version.V1, true);
            
            TestEnum value = (TestEnum)field.get(null);
            switch(value) {
                case Equals : {
                    assertThat (alias, notNullValue());
                    assertThat (alias.value(), equalTo("equals"));
                    break;
                }
                case Contains : {
                    assertThat (alias, notNullValue());
                    assertThat (alias.value(), equalTo("contains"));
                    break;
                }
            }
        }
    }
        
    public void testAliasDataV0 () throws Exception {
        Alias alias = AnnotationUtils.getAlias(TestData.class, Version.V0, true);
        assertThat (alias, notNullValue());
        assertThat (alias.value(), equalTo("test_data"));
    }
    
    public void testAliasDataFieldV0 () throws Exception {
        Alias alias = AnnotationUtils.getAlias(TestData.class.getDeclaredField("id"), Version.V0, true);
        assertThat (alias, notNullValue());
        assertThat (alias.value(), equalTo("id"));
    }
    
    public void testAliasDataV1 () throws Exception {
        Alias alias = AnnotationUtils.getAlias(TestData.class, Version.V1, true);
        assertThat (alias, notNullValue());
        assertThat (alias.value(), equalTo("test_data"));
    }
    
    public void testAliasDataFieldV1 () throws Exception {
        Alias alias = AnnotationUtils.getAlias(TestData.class.getDeclaredField("id"), Version.V1, true);
        assertThat (alias, notNullValue());
        assertThat (alias.value(), equalTo("identifier"));
    }
    
    public void testAliasDataV2 () throws Exception {
        Alias alias = AnnotationUtils.getAlias(TestData.class, Version.V2, true);
        assertThat (alias, notNullValue());
        assertThat (alias.value(), equalTo("data"));
    }
    
    public void testAliasDataFieldV2 () throws Exception {
        Alias alias = AnnotationUtils.getAlias(TestData.class.getDeclaredField("id"), Version.V2, true);
        assertThat (alias, notNullValue());
        assertThat (alias.value(), equalTo("uuid"));
    }
    
    public void testAliasDataVLast () throws Exception {
        Alias alias = AnnotationUtils.getAlias(TestData.class, Version.Last, true);
        assertThat (alias, notNullValue());
        assertThat (alias.value(), equalTo("data"));
    }
    
    public void testAliasDataFieldVLast () throws Exception {
        Alias alias = AnnotationUtils.getAlias(TestData.class.getDeclaredField("id"), Version.Last, true);
        assertThat (alias, notNullValue());
        assertThat (alias.value(), equalTo("uuid"));
    }
    
    public void testAliasParentDataV0 () throws Exception {
        
    }
}

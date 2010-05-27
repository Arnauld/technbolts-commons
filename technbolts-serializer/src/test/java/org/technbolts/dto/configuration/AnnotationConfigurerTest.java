/* $Id$ */
package org.technbolts.dto.configuration;

import static junit.framework.Assert.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIn.isIn;
import static org.hamcrest.text.StringContains.containsString;

import java.util.List;

import org.technbolts.dto.testmodel.TestData;
import org.technbolts.dto.testmodel.TestDataSubClass;
import org.technbolts.dto.configuration.annotation.VersionField;
import org.technbolts.dto.configuration.definition.AliasModel;
import org.technbolts.dto.configuration.definition.AsAttributeModel;
import org.junit.Before;
import org.junit.Test;

import org.technbolts.dto.domain.common.VersionConverter;

/**
 * AnnotationConfigurerTest
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class AnnotationConfigurerTest
{
    private ConfigurationHandlerAdapter conf;
    
    @Before
    public void setUp () {
        conf = new ConfigurationHandlerAdapter (ConfigurationHandlerAdapter.Mode.collectInformations);
    }

    @Test
    public void testTestDataV1 () throws InvalidConfigurationException {
        new AnnotationConfigurer (conf)
            .recursivelyProcessAnnotations(TestData.class, Version.V1);
        
        List<AliasModel> aliasModelList = conf.getAliasModelList();
        assertThat (new AliasModel("identifier", TestData.class, "id"),    isIn(aliasModelList));
        assertThat (new AliasModel("equals",     TestData.class, "count"), isIn(aliasModelList));
        assertThat (new AliasModel("name",       TestData.class, "name"),  isIn(aliasModelList));
    }
    
    @Test
    public void testTestDataSubClassV1 () throws InvalidConfigurationException {
        new AnnotationConfigurer(conf)
            .recursivelyProcessAnnotations(TestDataSubClass.class, Version.V1);
        
        List<AliasModel> aliasModelList = conf.getAliasModelList();
        assertThat (new AliasModel("subclass_attribute",TestDataSubClass.class, "subAttribute"),    isIn(aliasModelList));
        assertThat (new AliasModel("identifier",TestData.class, "id"),    isIn(aliasModelList));
        assertThat (new AliasModel("equals",    TestData.class, "count"), isIn(aliasModelList));
        assertThat (new AliasModel("name",      TestData.class, "name"),  isIn(aliasModelList));
    }
    
    @Test
    public void testValidVersionField () throws InvalidConfigurationException {
        final Class<?> clazz = VersionFieldValidStruct.class;
        new AnnotationConfigurer (conf)
            .recursivelyProcessAnnotations(clazz, Version.V1);
        
        final String field = "improbable_field_name";
        
        /*
         *  Annotation @VersionField should predefine all those informations
         */
        
        assertThat (new AliasModel("version",clazz, field), isIn(conf.getAliasModelList()));
        assertThat (new AsAttributeModel(clazz, field), isIn(conf.getAsAttributeList()));
        
        Object converter = conf.getConverter(clazz, field);
        assertThat(converter, notNullValue());
        assertThat(converter, is(VersionConverter.class));
    }
    
    @Test
    public void testInvalidVersionField () {
        
        try
        {
            final Class<?> clazz = VersionFieldInvalidStruct.class;
            new AnnotationConfigurer (conf)
                .recursivelyProcessAnnotations(clazz, Version.V1);
            fail("Type string should not be allowed on @VersionField annotation");
        }
        catch (InvalidConfigurationException e)
        {
            assertThat(e.getMessage(), containsString("Annotation @VersionField on invalid type"));
        }
    }
    
    public static class VersionFieldValidStruct
    {
        @SuppressWarnings("unused")
        @VersionField
        private Version improbable_field_name;
    }
    
    public static class VersionFieldInvalidStruct
    {
        @SuppressWarnings("unused")
        @VersionField
        private String improbable_field_name;
    }
}

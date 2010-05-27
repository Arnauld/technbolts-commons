/* $Id$ */
package org.technbolts.dto.configuration.annotation;

import java.lang.reflect.Field;

import org.technbolts.dto.configuration.Version;

import junit.framework.TestCase;

public class DefaultValuesIntegrityTest extends TestCase
{
    private Field annotatedField;
    
    @Override
    protected void setUp() throws Exception
    {
        annotatedField = FullyAnnotatedClass.class.getDeclaredField("fullyAnnotatedField");
    }

    public void testAlias () {
        Alias alias = annotatedField.getAnnotation(Alias.class);
        assertNotNull(alias);
        assertEquals(Version.V0, alias.since());
        assertEquals(Version.Last, alias.until());
    }
    
    public void testAliases () {
        Aliases aliases = annotatedField.getAnnotation(Aliases.class);
        assertNotNull(aliases);
        assertNotNull(aliases.value());
        assertEquals(0, aliases.value().length);
    }
    
    public void testAsAttribute () {
        AsAttribute asAttribute = annotatedField.getAnnotation(AsAttribute.class);
        assertNotNull(asAttribute);
        assertEquals(Version.V0, asAttribute.since());
        assertEquals(Version.Last, asAttribute.until());
    }
    
    public void testAsAttributes () {
        AsAttributes asAttributes = annotatedField.getAnnotation(AsAttributes.class);
        assertNotNull(asAttributes);
        assertNotNull(asAttributes.value());
        assertEquals(0, asAttributes.value().length);
    }
    
    public void testConverter () {
        Converter converter = annotatedField.getAnnotation(Converter.class);
        assertNotNull(converter);
        assertEquals(FullyAnnotatedClass.class, converter.clazz());
        assertEquals(Version.V0, converter.since());
        assertEquals(Version.Last, converter.until());
    }
    
    public void testConverters () {
        Converters converters = annotatedField.getAnnotation(Converters.class);
        assertNotNull(converters);
        assertNotNull(converters.value());
        assertEquals(0, converters.value().length);
    }
    
    public void testImplicit () {
        Implicit implicit = annotatedField.getAnnotation(Implicit.class);
        assertNotNull(implicit);
        assertEquals(Version.V0, implicit.since());
        assertEquals(Version.Last, implicit.until());
    }
    
    public void testImplicits () {
        Implicits implicits = annotatedField.getAnnotation(Implicits.class);
        assertNotNull(implicits);
        assertNotNull(implicits.value());
        assertEquals(0, implicits.value().length);
    }
    
    public void testOmitField () {
        OmitField omitField = annotatedField.getAnnotation(OmitField.class);
        assertNotNull(omitField);
        assertEquals(Version.V0, omitField.since());
        assertEquals(Version.Last, omitField.until());
    }
    
    public void testOmitFields () {
        OmitFields omitFields = annotatedField.getAnnotation(OmitFields.class);
        assertNotNull(omitFields);
        assertNotNull(omitFields.value());
        assertEquals(0, omitFields.value().length);
    }
    
    public void testRequire () {
        Require require = annotatedField.getAnnotation(Require.class);
        assertNotNull(require);
        assertEquals(Version.V1, require.value());
        assertEquals(Version.V0, require.since());
        assertEquals(Version.Last, require.until());
    }
    
    public void testRequires () {
        Requires requires = annotatedField.getAnnotation(Requires.class);
        assertNotNull(requires);
        assertNotNull(requires.value());
        assertEquals(0, requires.value().length);
    }
    
    public void testSince () {
        Since since = annotatedField.getAnnotation(Since.class);
        assertNotNull(since);
        assertEquals(Version.V1, since.value());
    }
    
    public void testUntil () {
        Until until = annotatedField.getAnnotation(Until.class);
        assertNotNull(until);
        assertEquals(Version.V2, until.value());
    }
    
    class FullyAnnotatedClass {
        @Alias(value="alias")
        @Aliases({})
        @AsAttribute
        @AsAttributes({})
        @Converter(clazz=FullyAnnotatedClass.class)
        @Converters({})
        @Implicit
        @Implicits({})
        @OmitField
        @OmitFields({})
        @Require(value=Version.V1)
        @Requires({})
        @Since(Version.V1)
        @Until(Version.V2)
        private String fullyAnnotatedField;
        
        public String getFullyAnnotatedField()
        {
            return fullyAnnotatedField;
        }
    }
}

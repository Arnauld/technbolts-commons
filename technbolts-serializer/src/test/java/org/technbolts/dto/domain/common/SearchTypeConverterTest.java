/* $Id$ */
package org.technbolts.dto.domain.common;

import org.technbolts.dto.configuration.Version;

import junit.framework.TestCase;

public class SearchTypeConverterTest extends TestCase
{
    public void testV0Compatibility () {
        assertEquals(SearchType.Equals, SearchTypeConverter.fromString("equality", Version.V0));
        assertEquals(null, SearchTypeConverter.fromString("equals", Version.V0));
        assertEquals(null, SearchTypeConverter.fromString("contains", Version.V0));
    }
    
    public void testV0ToStringCompatibility () {
        final Version version = Version.V0;
        assertEquals("equality", SearchTypeConverter.toString(SearchType.Equals, version));
        assertEquals(null,       SearchTypeConverter.toString(SearchType.Contains, version));
        assertEquals(null,       SearchTypeConverter.toString(SearchType.StartsWith, version));
        assertEquals(null,       SearchTypeConverter.toString(SearchType.EndsWith, version));
        assertEquals(null,       SearchTypeConverter.toString(SearchType.Empty, version));
        assertEquals(null,       SearchTypeConverter.toString(SearchType.Wildcard, version));
    }
    
    public void testV1FromStringCompatibility () {
        final Version version = Version.V1;
        assertEquals(SearchType.Equals,     SearchTypeConverter.fromString("equals", version));
        assertEquals(SearchType.Contains,   SearchTypeConverter.fromString("contains", version));
        assertEquals(SearchType.StartsWith, SearchTypeConverter.fromString("starts_with", version));
        assertEquals(SearchType.EndsWith,   SearchTypeConverter.fromString("ends_with", version));
        assertEquals(SearchType.Empty,      SearchTypeConverter.fromString("empty", version));
        assertEquals(SearchType.Wildcard,   SearchTypeConverter.fromString("wildcard", version));
    }
    
    public void testV1ToStringCompatibility () {
        final Version version = Version.V1;
        assertEquals("equals",      SearchTypeConverter.toString(SearchType.Equals, version));
        assertEquals("contains",    SearchTypeConverter.toString(SearchType.Contains, version));
        assertEquals("starts_with", SearchTypeConverter.toString(SearchType.StartsWith, version));
        assertEquals("ends_with",   SearchTypeConverter.toString(SearchType.EndsWith, version));
        assertEquals("empty",       SearchTypeConverter.toString(SearchType.Empty, version));
        assertEquals("wildcard",    SearchTypeConverter.toString(SearchType.Wildcard, version));
    }
}

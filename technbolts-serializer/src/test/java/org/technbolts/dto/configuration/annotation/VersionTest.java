/* $Id$ */
package org.technbolts.dto.configuration.annotation;

import org.technbolts.dto.configuration.Version;

import junit.framework.TestCase;

public class VersionTest extends TestCase 
{
    
    public void testIsInRange () {
        assertTrue (Version.V1.isInRange(Version.V0, Version.Last));
        assertTrue (Version.V1.isInRange(Version.V1, Version.Last));
        assertTrue (Version.V1.isInRange(Version.V0, Version.V1));
        assertTrue (Version.V1.isInRange(Version.V0, null));
        assertTrue (Version.V1.isInRange(Version.V1, null));
        
        assertFalse(Version.V0.isInRange(Version.V1, Version.Last));
        assertFalse(Version.V0.isInRange(Version.V1, null));
    }

    public void testIsYoungerThan () {
        assertFalse(Version.V0.isYoungerThan(Version.V0));
        assertTrue (Version.V0.isYoungerThan(Version.V1));
        assertTrue (Version.V0.isYoungerThan(Version.Last));
        
        assertFalse(Version.V1.isYoungerThan(Version.V1));
        
        assertFalse(Version.V1.isYoungerThan(Version.V0));
        assertFalse(Version.Last.isYoungerThan(Version.V0));
    }
    
    public void testIsYoungerThanOrEqualTo () {
        assertTrue (Version.V0.isYoungerThanOrEqualTo(Version.V0));
        assertTrue (Version.V0.isYoungerThanOrEqualTo(Version.V1));
        assertTrue (Version.V0.isYoungerThanOrEqualTo(Version.Last));
        
        assertTrue (Version.V1.isYoungerThanOrEqualTo(Version.V1));
        
        assertFalse(Version.V1.isYoungerThanOrEqualTo(Version.V0));
        assertFalse(Version.Last.isYoungerThanOrEqualTo(Version.V0));
    }
    
    public void testIsOlderThan () {
        assertFalse(Version.V0.isOlderThan(Version.V0));
        assertFalse(Version.V0.isOlderThan(Version.V1));
        assertFalse(Version.V0.isOlderThan(Version.Last));
        
        assertFalse(Version.V1.isOlderThan(Version.V1));
        
        assertTrue (Version.V1.isOlderThan(Version.V0));
        assertTrue (Version.Last.isOlderThan(Version.V0));
    }
    
    public void testIsOlderThanOrEqualTo () {
        assertTrue (Version.V0.isOlderThanOrEqualTo(Version.V0));
        assertFalse(Version.V0.isOlderThanOrEqualTo(Version.V1));
        assertFalse(Version.V0.isOlderThanOrEqualTo(Version.Last));
        
        assertTrue (Version.V1.isOlderThanOrEqualTo(Version.V1));
        
        assertTrue (Version.V1.isOlderThanOrEqualTo(Version.V0));
        assertTrue (Version.Last.isOlderThanOrEqualTo(Version.V0));
    }
}

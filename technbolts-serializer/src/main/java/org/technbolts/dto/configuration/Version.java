/* $Id$ */
package org.technbolts.dto.configuration;

import org.apache.commons.lang.StringUtils;

/**
 * Version to describe a ... version !
 * 
 * Note version age is determined using its <code>ordinal</code>, an older
 * version has a lesser a ordinal than a younger one.
 * Thus the declaration order is important in the current implementation.
 * 
 * <pre>
 *      V0.ordinal() < V1.ordinal ()
 * </pre>
 * 
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 * @see #ordinal()
 */
public enum Version
{
    V0("v0"),// mainly use for testing purpose
    V1("v1"),
    V2("v2"),
    V3("v3"),
    V4("v4"),
    
    Last("Last"),
    VFarIntoTheFuture ("far into the future"),// mainly use for testing purpose
    ;
    
    public static final String ATTRIBUTE_NAME = "version";

    private String description;
    private Version(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    
    public String toStringIdentifier () {
        return name().toLowerCase();
    }
    
    public static Version fromStringIdentifier (String id) {
        for(Version version : values())
            if(StringUtils.endsWithIgnoreCase(id, version.name()))
                return version;
        return null;
    }
    
    public boolean isYoungerThan (Version other) {
        return ordinal () < other.ordinal();
    }
    
    public boolean isYoungerThanOrEqualTo (Version other) {
        return ordinal () <= other.ordinal();
    }
    
    public boolean isOlderThan (Version other) {
        return ordinal () > other.ordinal();
    }
    
    public boolean isOlderThanOrEqualTo (Version other) {
        return ordinal () >= other.ordinal();
    }

    public boolean isInRange(Version since, Version until)
    {
        if(since!=null && isYoungerThan(since))
            return false;
        if(until!=null && isOlderThan(until))
            return false;
        return true;
    }
}

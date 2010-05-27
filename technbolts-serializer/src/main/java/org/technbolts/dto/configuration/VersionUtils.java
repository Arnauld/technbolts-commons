/* $Id$ */
package org.technbolts.dto.configuration;

/**
 * VersionUtils
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class VersionUtils
{
    
    /**
     * Attempt to specialize the <code>object</code> into the specific
     * version.
     * The <code>VersionSensitive</code> contract is used if the <code>object</code>
     * is not <code>null</code> and implements it.
     * 
     * @param object
     * @param version
     * @return
     * @see VersionSensitive
     */
    public static Object versionize(Object object, Version version) {
        if(object!=null && object instanceof VersionSensitive)
            return ((VersionSensitive)object).specializeFor(version);
        return object;
    }
}

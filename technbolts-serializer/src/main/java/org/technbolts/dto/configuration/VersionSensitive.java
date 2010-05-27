/* $Id$ */
package org.technbolts.dto.configuration;

/**
 * VersionSensitive allow an instance to be specialized into a specific version.
 * 
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public interface VersionSensitive
{
    Object specializeFor(Version version);
}

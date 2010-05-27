/* $Id$ */
package org.technbolts.dto.serializer;

/**
 * VersionSerializationStrategy
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public enum VersionSerializationStrategy
{
    None,
    OnlyOnRootNode,
    WhenerPossible;
    
    public boolean checkVersionableOnRoot () {
        return (this==OnlyOnRootNode || this==WhenerPossible);
    }
    
    public boolean checkVersionableOnNode () {
        return (this==WhenerPossible);
    }
}

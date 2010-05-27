package org.technbolts.dto.configuration;

/**
 * User: loyer
 * Date: 26 mai 2010
 * Time: 14:19:53
 *
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class VersionedType {
    private final Class<?> type;
    private final Version version;

    public VersionedType(Class<?> type, Version version) {
        this.type = type;
        this.version = version;
    }

    public Class<?> getType() {
        return type;
    }

    public Version getVersion() {
        return version;
    }
}

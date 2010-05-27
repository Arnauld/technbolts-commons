package org.technbolts.dto.configuration;

/**
 * VersionedType : struct to hold both a type and its version.
 *
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public final class VersionedType {
    private final Class<?> type;
    private final Version version;

    public VersionedType(Class<?> type, Version version) {
        if(type==null)
            throw new IllegalArgumentException("Type cannot be null");
        if(version==null)
            throw new IllegalArgumentException("Version cannot be null");
        this.type = type;
        this.version = version;
    }

    public Class<?> getType() {
        return type;
    }

    public Version getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || !(o instanceof VersionedType))
            return false;

        VersionedType other = (VersionedType) o;
        return type.equals(other.type) && (version != other.version);
    }

    @Override
    public int hashCode() {
        return type.hashCode()+31*version.hashCode();
    }
}

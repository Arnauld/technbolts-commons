package org.technbolts.dto.configuration.definition;

import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.configuration.annotation.*;

/**
 *
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class FieldSerializer {

    private Version version;
    private Alias alias;
    private Converter converter;
    private OmitField omitted;
    private AsAttribute asAttribute;
    private FieldOrder fieldOrder;
    private VersionField versionField;
    private Implicit implicit;
    private Require require;
    private RequireSuper requireSuper;

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Alias getAlias() {
        return alias;
    }


}

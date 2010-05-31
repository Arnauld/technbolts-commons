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
    private java.lang.reflect.Field field;
    private Alias alias;
    private Converter converter;
    private OmitField omitted;
    private AsAttribute asAttribute;
    private FieldOrder fieldOrder;
    private VersionField versionField;
    private Implicit implicit;
    private Require require;
    private RequireSuper requireSuper;

    public boolean isAliasDefined () {
        return (alias!=null);
    }
    
    public String alias () {
        if(alias!=null && alias.value()!=null)
            return alias.value();
        else
            return field.getName();
    }

}

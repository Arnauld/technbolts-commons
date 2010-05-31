package org.technbolts.dto.configuration.definition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.configuration.annotation.*;
import org.technbolts.util.Option;

import java.lang.reflect.Field;

/**
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class FieldSerializer {

    private static Logger logger = LoggerFactory.getLogger(FieldSerializer.class);

    private java.lang.reflect.Field field;
    //
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

    public FieldSerializer(Field field) {
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public boolean isAliasDefined() {
        return (alias != null);
    }

    public String getAliasOrFieldName() {
        if (alias != null && alias.value() != null)
            return alias.value();
        else
            return field.getName();
    }

    public Alias getAlias() {
        return alias;
    }

    public void setAlias(Alias alias) {
        this.alias = alias;
    }

    public boolean isVersionDefined() {
        return version != null;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public boolean isConverterDefined() {
        return converter != null && converter.clazz() != null;
    }

    private Option<Object> converterInstance;

    public Object getConverterInstance() {
        if (converterInstance == null)
            converterInstance = newConverterInstance();
        return converterInstance.get();
    }

    public Option<Object> newConverterInstance() {
        if (!isConverterDefined())
            return Option.none();

        try {
            return Option.some(converter.clazz().newInstance());
        }
        catch (InstantiationException e) {
            logger.error("Failed to instantiate converter", e);
        }
        catch (IllegalAccessException e) {
            logger.error("Failed to instantiate converter", e);
        }
        return Option.none();
    }

    public Converter getConverter() {
        return converter;
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    public boolean isOmitted () {
        return (omitted!=null);
    }

    public OmitField getOmitted() {
        return omitted;
    }

    public void setOmitted(OmitField omitted) {
        this.omitted = omitted;
    }

    public boolean asAttribute () {
        return (asAttribute!=null);
    }

    public AsAttribute getAsAttribute() {
        return asAttribute;
    }

    public void setAsAttribute(AsAttribute asAttribute) {
        this.asAttribute = asAttribute;
    }

    public boolean isFieldOrderDefined () {
        return (fieldOrder!=null);
    }

    public Integer getFieldOrderValue () {
        return fieldOrder.value();
    }

    public FieldOrder getFieldOrder() {
        return fieldOrder;
    }

    public void setFieldOrder(FieldOrder fieldOrder) {
        this.fieldOrder = fieldOrder;
    }

    public boolean isVersionField () {
        return isVersionFieldDefined ();
    }

    public boolean isVersionFieldDefined () {
        return (versionField!=null);
    }

    public VersionField getVersionField() {
        return versionField;
    }

    public void setVersionField(VersionField versionField) {
        this.versionField = versionField;
    }

    public boolean isImplicitDefined () {
        return implicit!=null;
    }

    public Implicit getImplicit() {
        return implicit;
    }

    public void setImplicit(Implicit implicit) {
        this.implicit = implicit;
    }

    public boolean isRequireDefined () {
        return require!=null;
    }

    public Version getRequireVersion () {
        if(isRequireDefined())
            return getRequire().value();
        return null;
    }

    public Require getRequire() {
        return require;
    }

    public void setRequire(Require require) {
        this.require = require;
    }

    public boolean isRequireSuperDefined () {
        return requireSuper!=null;
    }

    public Version getRequireSuperVersion () {
        if(isRequireSuperDefined())
            return getRequireSuper().value();
        return null;
    }

    public RequireSuper getRequireSuper() {
        return requireSuper;
    }

    public void setRequireSuper(RequireSuper requireSuper) {
        this.requireSuper = requireSuper;
    }
}

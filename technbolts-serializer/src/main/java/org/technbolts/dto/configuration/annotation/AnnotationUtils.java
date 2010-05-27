/* $Id$ */
package org.technbolts.dto.configuration.annotation;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.technbolts.dto.configuration.InvalidConfigurationException;
import org.technbolts.dto.configuration.Version;

/**
 * Set of utilities methods to extract annotation informations on <code>Field</code>
 * and <code>Class</code>.
 * 
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class AnnotationUtils
{
    private static Logger logger = LoggerFactory.getLogger(AnnotationUtils.class);
    
    /**
     * Extract the alias that match the specified version if any, otherwise
     * return <code>null</code>.
     * @param field
     * @param version
     * @return
     */
    public static Alias getAlias(Field field, Version version)  {
        try
        {
            return getAlias(field, version, false);
        }
        catch (InvalidConfigurationException e)
        {
            // should not happen since not in check mode
            if(logger.isWarnEnabled())
                logger.warn("Invalid annotation description on field "+field, e);
            
            return null;
        }
    }
    
    /**
     * Extract the <code>Alias</code> that match the specified version if any, otherwise
     * return <code>null</code>.
     * This methods also check that two  <code>Alias</code> definition don't overlaps within
     * the same versions range.
     * 
     * @param field
     * @param version
     * @param inCheckMode
     * @return
     * @throws InvalidConfigurationException
     */
    public static Alias getAlias(Field field, Version version, boolean inCheckMode) throws InvalidConfigurationException {
        Alias   alias   = field.getAnnotation(Alias.class);
        Aliases aliases = field.getAnnotation(Aliases.class);
        return getAlias(alias, aliases, version, inCheckMode);
    }
    
    /**
     * Extract the  <code>Alias</code> that match the specified version if any, otherwise
     * return <code>null</code>.
     * This methods also check that two  <code>Alias</code> definition don't overlaps within
     * the same versions range.
     * 
     * @param type
     * @param version
     * @param inCheckMode
     * @return
     * @throws InvalidConfigurationException
     */
    public static Alias getAlias(Class<?> type, Version version, boolean inCheckMode) throws InvalidConfigurationException {
        Alias   alias   = type.getAnnotation(Alias.class);
        Aliases aliases = type.getAnnotation(Aliases.class);
        return getAlias(alias, aliases, version, inCheckMode);
    }
    
    /**
     * Extract the  <code>Alias</code> that match the specified version if any, otherwise
     * return <code>null</code>.
     * This methods also check that two  <code>Alias</code> definition don't overlaps within
     * the same versions range.
     * 
     * @param alias
     * @param aliases
     * @param version
     * @param inCheckMode
     * @return
     * @throws InvalidConfigurationException
     */
    public static Alias getAlias(Alias alias, Aliases aliases, Version version, boolean inCheckMode) throws InvalidConfigurationException {
        
        boolean notInCheckMode = (inCheckMode==false);
        
        Alias found = null;
        if(alias!=null)
        {
            boolean versionInRange = version.isInRange(alias.since(), alias.until());
            
            if(logger.isTraceEnabled())
                logger.trace("Alias "+alias+" matching version "+version+" ? "+versionInRange);
            
            if(versionInRange)
            {
                if(notInCheckMode)
                    return alias;
                found = alias;
            }
        }
        if(aliases!=null)
        {
            for(Alias item : aliases.value())
            {
                boolean versionInRange = version.isInRange(item.since(), item.until());
                
                if(logger.isTraceEnabled())
                    logger.trace("Alias "+item+" matching version "+version+" ? "+versionInRange);
                
                if(versionInRange)
                {
                    if(notInCheckMode)
                        return item;
                    else if(found!=null)
                        throw new InvalidConfigurationException ("Alias version overlap "+found+" "+item);
                    found = item;
                }
            }
        }
        return found;
    }
    
    
    
    /**
     * Extract the <code>AsAttribute</code> that match the specified version if any, otherwise
     * return <code>null</code>.
     * This methods also check that two <code>AsAttribute</code> definition don't overlaps within
     * the same versions range.
     * 
     * @param field
     * @param version
     * @param inCheckMode
     * @return
     * @throws InvalidConfigurationException
     */
    public static AsAttribute getAsAttribute(Field field, Version version, boolean inCheckMode) throws InvalidConfigurationException {
        AsAttribute  asAttribute  = field.getAnnotation(AsAttribute.class);
        AsAttributes asAttributes = field.getAnnotation(AsAttributes.class);
        
        boolean notInCheckMode = (inCheckMode==false);
        
        AsAttribute found = null;
        if(asAttribute!=null)
        {
            if(version.isInRange(asAttribute.since(), asAttribute.until()))
            {
                if(notInCheckMode)
                    return asAttribute;
                found = asAttribute;
            }
        }
        if(asAttributes!=null)
        {
            for(AsAttribute item : asAttributes.value())
            {
                if(version.isInRange(item.since(), item.until()))
                {
                    if(notInCheckMode)
                        return item;
                    else if(found!=null)
                        throw new InvalidConfigurationException ("AsAttribute version overlap "+found+" "+item);
                    found = item;
                }
            }
        }
        return found;
    }
    
    /**
     * Extract the <code>Converter</code> that match the specified version if any, otherwise
     * return <code>null</code>.
     * This methods also check that two <code>Converter</code> definition don't overlaps within
     * the same versions range.
     * 
     * @param field
     * @param version
     * @param inCheckMode
     * @return
     * @throws InvalidConfigurationException
     */
    public static Converter getConverter(Field field, Version version, boolean inCheckMode) throws InvalidConfigurationException {
        Converter  converter  = field.getAnnotation(Converter.class);
        Converters converters = field.getAnnotation(Converters.class);
        return getConverter(converter, converters, version, inCheckMode);
    }
    
    /**
     * Extract the <code>Converter</code> that match the specified version if any, otherwise
     * return <code>null</code>.
     * This methods also check that two <code>Converter</code> definition don't overlaps within
     * the same versions range.
     * 
     * @param type
     * @param version
     * @param inCheckMode
     * @return
     * @throws InvalidConfigurationException
     */
    public static Converter getConverter(Class<?> type, Version version, boolean inCheckMode) throws InvalidConfigurationException {
        Converter  converter  = type.getAnnotation(Converter.class);
        Converters converters = type.getAnnotation(Converters.class);
        return getConverter(converter, converters, version, inCheckMode);
    }
    
    /**
     * Extract the <code>Converter</code> that match the specified version if any, otherwise
     * return <code>null</code>.
     * This methods also check that two <code>Converter</code> definition don't overlaps within
     * the same versions range.
     * 
     * @param converter
     * @param converters
     * @param version
     * @param inCheckMode
     * @return
     * @throws InvalidConfigurationException
     */
    public static Converter getConverter(Converter converter, Converters converters, Version version, boolean inCheckMode) throws InvalidConfigurationException {
        
        boolean notInCheckMode = (inCheckMode==false);
        
        Converter found = null;
        if(converter!=null)
        {
            if(version.isInRange(converter.since(), converter.until()))
            {
                if(notInCheckMode)
                    return converter;
                found = converter;
            }
        }
        if(converters!=null)
        {
            for(Converter item : converters.value())
            {
                if(version.isInRange(item.since(), item.until()))
                {
                    if(notInCheckMode)
                        return item;
                    else if(found!=null)
                        throw new InvalidConfigurationException ("Converter version overlap "+found+" "+item);
                    found = item;
                }
            }
        }
        return found;
    }
    
    /**
     * Indicate whether or not <code>Implicit</code> or <code>Implicits</code>
     * annotations have been set on the field.
     * 
     * @param field
     * @return
     * @throws InvalidConfigurationException
     */
    public static boolean hasImplicit(Field field) throws InvalidConfigurationException {
        Implicit  implicit  = field.getAnnotation(Implicit.class);
        Implicits implicits = field.getAnnotation(Implicits.class);
        
        return (implicit!=null) || (implicits!=null);
    }
    
    /**
     * Extract the <code>Implicit</code> that match the specified version if any, otherwise
     * return <code>null</code>.
     * This methods also check that two <code>Implicit</code> definition don't overlaps within
     * the same versions range.
     * 
     * @param field
     * @param version
     * @param inCheckMode
     * @return
     * @throws InvalidConfigurationException
     */
    public static Implicit getImplicit(Field field, Version version, boolean inCheckMode) throws InvalidConfigurationException {
        Implicit  implicit  = field.getAnnotation(Implicit.class);
        Implicits implicits = field.getAnnotation(Implicits.class);
        
        boolean notInCheckMode = (inCheckMode==false);
        
        Implicit found = null;
        if(implicit!=null)
        {
            if(version.isInRange(implicit.since(), implicit.until()))
            {
                if(notInCheckMode)
                    return implicit;
                found = implicit;
            }
        }
        if(implicits!=null)
        {
            for(Implicit item : implicits.value())
            {
                if(version.isInRange(item.since(), item.until()))
                {
                    if(notInCheckMode)
                        return item;
                    else if(found!=null)
                        throw new InvalidConfigurationException ("Implicit version overlap "+found+" "+item);
                    found = item;
                }
            }
        }
        return found;
    }
    
    /**
     * Extract the <code>OmitField</code> that match the specified version if any, otherwise
     * return <code>null</code>.
     * @param field
     * @param version
     * @return
     */
    public static OmitField getOmitField(Field field, Version version)  {
        try
        {
            return getOmitField(field, version, false);
        }
        catch (InvalidConfigurationException e)
        {
            // should not happen since not in check mode
            if(logger.isWarnEnabled())
                logger.warn("Invalid annotation description on field "+field, e);
            
            return null;
        }
    }
    
    /** 
     * Extract the <code>OmitField</code> that match the specified version if any, otherwise
     * return <code>null</code>.
     * This methods also check that two <code>OmitField</code> definition don't overlaps within
     * the same versions range.
     * 
     * @param field
     * @param version
     * @param inCheckMode
     * @return
     * @throws InvalidConfigurationException
     */
    public static OmitField getOmitField(Field field, Version version, boolean inCheckMode) throws InvalidConfigurationException {
        OmitField  omitField  = field.getAnnotation(OmitField.class);
        OmitFields omitFields = field.getAnnotation(OmitFields.class);
        
        boolean notInCheckMode = (inCheckMode==false);
        
        OmitField found = null;
        if(omitField!=null)
        {
            if(version.isInRange(omitField.since(), omitField.until()))
            {
                if(notInCheckMode)
                    return omitField;
                found = omitField;
            }
        }
        if(omitFields!=null)
        {
            for(OmitField item : omitFields.value())
            {
                if(version.isInRange(item.since(), item.until()))
                {
                    if(notInCheckMode)
                        return item;
                    else if(found!=null)
                        throw new InvalidConfigurationException ("OmitField version overlap "+found+" "+item);
                    found = item;
                }
            }
        }
        return found;
    }
    
    /**
     * Extract the <code>OmitClass</code> that match the specified version if any, otherwise
     * return <code>null</code>.
     * @param clazz
     * @param version
     * @return
     */
    public static OmitClass getOmitClass(Class<?> clazz, Version version)  {
        try
        {
            return getOmitClass(clazz, version, false);
        }
        catch (InvalidConfigurationException e)
        {
            // should not happen since not in check mode
            if(logger.isWarnEnabled())
                logger.warn("Invalid annotation description on field "+clazz, e);
            
            return null;
        }
    }
    
    /** 
     * Extract the <code>OmitClass</code> that match the specified version if any, otherwise
     * return <code>null</code>.
     * This methods also check that two <code>OmitClass</code> definition don't overlaps within
     * the same versions range.
     * 
     * @param clazz
     * @param version
     * @param inCheckMode
     * @return
     * @throws InvalidConfigurationException
     */
    public static OmitClass getOmitClass(Class<?> clazz, Version version, boolean inCheckMode) throws InvalidConfigurationException {
        OmitClass   omitClass   = clazz.getAnnotation(OmitClass.class);
        OmitClasses omitClasses = clazz.getAnnotation(OmitClasses.class);
        
        boolean notInCheckMode = (inCheckMode==false);
        
        OmitClass found = null;
        if(omitClass!=null)
        {
            if(version.isInRange(omitClass.since(), omitClass.until()))
            {
                if(notInCheckMode)
                    return omitClass;
                found = omitClass;
            }
        }
        if(omitClasses!=null)
        {
            for(OmitClass item : omitClasses.value())
            {
                if(version.isInRange(item.since(), item.until()))
                {
                    if(notInCheckMode)
                        return item;
                    else if(found!=null)
                        throw new InvalidConfigurationException ("OmitClass version overlap "+found+" "+item);
                    found = item;
                }
            }
        }
        return found;
    }
    
    /**
     * Extract the <code>Require</code> that match the specified version if any, otherwise
     * return <code>null</code>.
     * 
     * @param field
     * @param version
     * @return
     */
    public static Require getRequire(Field field, Version version)  {
        try
        {
            return getRequire(field, version, false);
        }
        catch (InvalidConfigurationException e)
        {
            // should not happen since not in check mode
            if(logger.isWarnEnabled())
                logger.warn("Invalid annotation description on field "+field, e);
            
            return null;
        }
    }
    
    /**
     * Extract the <code>Require</code> that match the specified version if any, otherwise
     * return <code>null</code>.
     * This methods also check that two <code>Require</code> definition don't overlaps within
     * the same versions range.
     * 
     * @param field
     * @param version
     * @param inCheckMode
     * @return
     * @throws InvalidConfigurationException
     */
    public static Require getRequire(Field field, Version version, boolean inCheckMode) throws InvalidConfigurationException {
        Require  require  = field.getAnnotation(Require.class);
        Requires requires = field.getAnnotation(Requires.class);
        return getRequire(require, requires, version, inCheckMode);
    }
    
    /**
     * Extract the <code>Require</code> that match the specified version if any, otherwise
     * return <code>null</code>.
     * This methods also check that two <code>Require</code> definition don't overlaps within
     * the same versions range.
     * 
     * @param type
     * @param version
     * @param inCheckMode
     * @return
     * @throws InvalidConfigurationException
     */
    public static Require getRequire(Class<?> type, Version version, boolean inCheckMode) throws InvalidConfigurationException {
        Require   require   = type.getAnnotation(Require.class);
        Requires requires = type.getAnnotation(Requires.class);
        return getRequire(require, requires, version, inCheckMode);
    }
    
    /**
     * Extract the <code>Require</code> that match the specified version if any, otherwise
     * return <code>null</code>.
     * This methods also check that two <code>Require</code> definition don't overlaps within
     * the same versions range.
     * 
     * @param require
     * @param requires
     * @param version
     * @param inCheckMode
     * @return
     * @throws InvalidConfigurationException
     */
    public static Require getRequire(Require require, Requires requires, Version version, boolean inCheckMode) throws InvalidConfigurationException {
        
        boolean notInCheckMode = (inCheckMode==false);
        
        Require found = null;
        if(require!=null)
        {
            if(version.isInRange(require.since(), require.until()))
            {
                if(notInCheckMode)
                    return require;
                found = require;
            }
        }
        if(requires!=null)
        {
            for(Require item : requires.value())
            {
                if(version.isInRange(item.since(), item.until()))
                {
                    if(notInCheckMode)
                        return item;
                    else if(found!=null)
                        throw new InvalidConfigurationException ("Require version overlap "+found+" "+item);
                    found = item;
                }
            }
        }
        return found;
    }
    
    /**
     * Extract the <code>RequireSuper</code> that match the specified version if any, otherwise
     * return <code>null</code>.
     * 
     * @param type
     * @param version
     * @return
     */
    public static RequireSuper getRequireSuper(Class<?> type, Version version, boolean inCheckMode) throws InvalidConfigurationException {
        RequireSuper   require   = type.getAnnotation(RequireSuper.class);
        RequireSupers  requireSupers = type.getAnnotation(RequireSupers.class);
        return getRequireSuper(require, requireSupers, version, inCheckMode);
    }
    
    /**
     * Extract the <code>RequireSuper</code> that match the specified version if any, otherwise
     * return <code>null</code>.
     * This methods also check that two <code>RequireSuper</code> definition don't overlaps within
     * the same versions range.
     * 
     * @param require
     * @param requires
     * @param version
     * @param inCheckMode
     * @return
     * @throws InvalidConfigurationException
     */
    public static RequireSuper getRequireSuper(RequireSuper require, RequireSupers requires, Version version, boolean inCheckMode) throws InvalidConfigurationException {
        
        boolean notInCheckMode = (inCheckMode==false);
        
        RequireSuper found = null;
        if(require!=null)
        {
            if(version.isInRange(require.since(), require.until()))
            {
                if(notInCheckMode)
                    return require;
                found = require;
            }
        }
        if(requires!=null)
        {
            for(RequireSuper item : requires.value())
            {
                if(version.isInRange(item.since(), item.until()))
                {
                    if(notInCheckMode)
                        return item;
                    else if(found!=null)
                        throw new InvalidConfigurationException ("RequireSuper version overlap "+found+" "+item);
                    found = item;
                }
            }
        }
        return found;
    }
    
    /**
     * Indicate whether or not the field is defined for the specific version.
     * It means wether or not the field usage is still active (Until) and
     * the field was already active (Since) for the specified version.
     * 
     * <code>
     *    (field.since &lt;=)? version (&lt;= field.until)?
     * </code>
     * 
     * @param field
     * @param version
     * @return
     * @see Since
     * @see Until
     */
    public static  boolean isFieldDefined(Field field, Version version)
    {
        Since since = field.getAnnotation(Since.class);
        if(since!=null && version.isYoungerThan(since.value()))
        {
            if(logger.isTraceEnabled())
                logger.trace(field.getName()+" version "+version+" since "+since.value()+" rejected!");
            return false;
        }
        
        Until until = field.getAnnotation(Until.class);
        if(until!=null && version.isOlderThan(until.value()))
        {
            if(logger.isTraceEnabled())
                logger.trace(field.getName()+" version "+version+" until "+until.value()+" rejected!");
            return false;
        }
        
        return true;
    }
}

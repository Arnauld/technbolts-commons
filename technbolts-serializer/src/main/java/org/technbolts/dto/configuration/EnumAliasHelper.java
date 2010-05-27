/* $Id$ */
package org.technbolts.dto.configuration;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.technbolts.dto.configuration.annotation.Alias;
import org.technbolts.dto.configuration.annotation.AnnotationUtils;

public class EnumAliasHelper<T extends Enum<T>>
{
    private static Logger logger = LoggerFactory.getLogger(EnumAliasHelper.class);
    
    private Class<T> enumClass;

    public EnumAliasHelper(Class<T> enumClass) {
        this.enumClass = enumClass;
    }
    
    public String toString (T item, Version version) {
        return getVersionNTypeToStringCache().get(getStringKey(version, item));
    }
    
    public T fromString (String aliasString, Version version) {
        return getVersionNStringToTypeCache().get(getStringKey(version, aliasString));
    }
    
    private Map<String, T> perVersionNStringToTypeCache;
    private synchronized Map<String, T> getVersionNStringToTypeCache () {
        if(perVersionNStringToTypeCache!=null)
            return perVersionNStringToTypeCache;
            
        perVersionNStringToTypeCache = initVersionNStringToTypeCache ();
        
        return perVersionNStringToTypeCache;
    }
    
    private Map<String, String> perVersionNTypeToStringCache;
    private synchronized Map<String, String> getVersionNTypeToStringCache () {
        if(perVersionNTypeToStringCache!=null)
            return perVersionNTypeToStringCache;
            
        perVersionNTypeToStringCache = initVersionNTypeToStringCache ();
        
        return perVersionNTypeToStringCache;
    }
    
    private String getStringKey(Version version, T value) {
        return version.ordinal()+value.name();
    }
    
    private String getStringKey(Version version, String aliasString) {
        return version.ordinal()+aliasString;
    }
    
    private Map<String, String> initVersionNTypeToStringCache ()
    {
        Map<String, String> perVersionMap = new HashMap<String, String> ();
        try
        {
            for(Field field : enumClass.getDeclaredFields())
            {
                if(field.isEnumConstant()==false)
                    continue;
                T value = getEnumValue(field);
                
                for(Version version : Version.values())
                {
                    String aliasString = value.name();
                    
                    if(AnnotationUtils.isFieldDefined(field, version)==false)
                        continue;
                    
                    Alias alias = AnnotationUtils.getAlias(field, version);
                    if(alias!=null)
                    {
                        aliasString = alias.value();
                    }
                    perVersionMap.put(getStringKey(version, value), aliasString);
                }
            }
        }
        catch (IllegalArgumentException e)
        {
            if(logger.isWarnEnabled())
                logger.warn("Unable to build enum map ", e);
        }
        catch (IllegalAccessException e)
        {
            if(logger.isWarnEnabled())
                logger.warn("Unable to build enum map ", e);
        }
        
        return perVersionMap;
    }
    
    @SuppressWarnings("unchecked")
    private T getEnumValue (Field field) throws IllegalAccessException {
        return (T)field.get(null);
    }

    private Map<String, T> initVersionNStringToTypeCache ()
    {
        Map<String, T> perVersionMap = new HashMap<String, T> ();
        try
        {
            for(Field field : enumClass.getDeclaredFields())
            {
                if(field.isEnumConstant()==false)
                    continue;
                T value = getEnumValue(field);
                
                for(Version version : Version.values())
                {
                    if(AnnotationUtils.isFieldDefined(field, version)==false)
                        continue;
                    
                    String aliasString = value.name();
                    
                    Alias alias = AnnotationUtils.getAlias(field, version);
                    if(alias!=null)
                    {
                        aliasString = alias.value();
                    }
                    perVersionMap.put(getStringKey(version,aliasString), value);
                }
            }
        }
        catch (IllegalArgumentException e)
        {
            if(logger.isWarnEnabled())
                logger.warn("Unable to build enum map ", e);
        }
        catch (IllegalAccessException e)
        {
            if(logger.isWarnEnabled())
                logger.warn("Unable to build enum map ", e);
        }
        
        return perVersionMap;
    }
    
    
}

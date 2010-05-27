/* $Id$ */
package org.technbolts.dto.serializer.xstream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.technbolts.dto.configuration.AnnotationConfigurer;
import org.technbolts.dto.configuration.ClassField;
import org.technbolts.dto.configuration.ConfigurationHandler;

import static org.technbolts.util.AdaptableUtils.getAdapter;

public class XStreamHandlerWrapper implements ConfigurationHandler
{
    private static Logger logger = LoggerFactory.getLogger(AnnotationConfigurer.class);
    
    public static XStreamHandlerWrapper wrap(XStream xstream) {
        return new XStreamHandlerWrapper (xstream);
    }
    
    private XStream handler;
    
    public XStreamHandlerWrapper(XStream xstream) {
        this.handler = xstream;
    }

    public void addImplicitCollection(Class<?> definedIn, String fieldName,
            String itemFieldName, Class<?> itemType)
    {
        handler.addImplicitCollection(definedIn, fieldName, itemFieldName,
                itemType);
    }

    public void addImplicitCollection(Class<?> definedIn, String fieldName)
    {
        handler.addImplicitCollection(definedIn, fieldName);
    }

    public void alias(String alias, Class<?> type)
    {
        handler.alias(alias, type);
    }

    public void aliasField(String alias, Class<?> definedIn, String fieldName)
    {
        handler.aliasField(alias, definedIn, fieldName);
    }

    public void omitField(Class<?> definedIn, String fieldName)
    {
        handler.omitField(definedIn, fieldName);
    }
    
    public void omitField(ClassField classField)
    {
        omitField(classField.getDefinedIn(), classField.getFieldName());
    }

    public void registerConverter(Class<?> definedIn, Object aConverter)
    {
        SingleValueConverter svc = getAdapter(aConverter, SingleValueConverter.class);
        if(svc!=null)
        {
            handler.registerConverter(svc);
        }
        else
        {
            Converter xstreamConverter = getAdapter(aConverter, Converter.class);
            if(xstreamConverter!=null)
                handler.registerConverter(xstreamConverter);
            else
            {
                // humpf, unable to find any suitable converter
                // continue, but give some feedback
                if(logger.isWarnEnabled())
                    logger.warn("Unable to find suitable converter for type: " + definedIn);
            }
        }
    }

    public void registerConverter(Class<?> definedIn, String fieldName, Object aConverter)
    {
        SingleValueConverter svc = getAdapter(aConverter, com.thoughtworks.xstream.converters.SingleValueConverter.class);
        if(svc!=null)
        {
            handler.registerLocalConverter(definedIn, fieldName, svc);
        }
        else
        {
            com.thoughtworks.xstream.converters.Converter xstreamConverter = getAdapter(aConverter, com.thoughtworks.xstream.converters.Converter.class);
            if(xstreamConverter!=null)
                handler.registerLocalConverter(definedIn, fieldName, xstreamConverter);
            else
            {
                // humpf, unable to find any suitable converter
                // continue, but give some feedback
                if(logger.isWarnEnabled())
                    logger.warn("Unable to find suitable converter for field: "+fieldName);
            }
        }
    }

    public void useAttributeFor(Class<?> definedIn, String fieldName)
    {
        handler.useAttributeFor(definedIn, fieldName);
    }
    
}

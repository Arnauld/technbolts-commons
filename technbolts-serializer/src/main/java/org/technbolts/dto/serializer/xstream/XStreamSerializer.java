/* $Id$ */
package org.technbolts.dto.serializer.xstream;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.technbolts.dto.configuration.*;
import org.technbolts.dto.domain.common.DTOError;
import org.technbolts.dto.domain.common.ErrorCode;
import org.technbolts.dto.serializer.Serializer;
import org.technbolts.dto.serializer.VersionSerializationStrategy;

import org.technbolts.dto.configuration.AnnotationConfigurer;
import org.technbolts.dto.configuration.InvalidConfigurationException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.DataHolder;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import com.thoughtworks.xstream.mapper.MapperWrapper;

/**
 * XStreamSerializer
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class XStreamSerializer implements Serializer
{
    private static Logger logger = LoggerFactory.getLogger(XStreamSerializer.class);
    
    private boolean activateCheckMode;
    private VersionSerializationStrategy versionSerializationStrategy = VersionSerializationStrategy.OnlyOnRootNode;
    private XStream xstream;
    private List<DTOError> errors = new ArrayList<DTOError> ();
    
    public XStreamSerializer()
    {
        activateCheckMode = true;
        
        XppDriver xppDriver = new XppDriver(new XmlFriendlyReplacer("-", "_"));
        xstream = new XStream (xppDriver) {
            @Override
            protected MapperWrapper wrapMapper(MapperWrapper next)
            {
                return new CustomMapperWrapper (next);
            }
            @Override
            public void marshal(Object object, HierarchicalStreamWriter writer, DataHolder dataHolder)
            {
                if(versionSerializationStrategy.checkVersionableOnNode())
                    handleVersionable(object);
                super.marshal(object, writer, dataHolder);
            }
        };
        xstream.setMode(XStream.NO_REFERENCES);
    }
    
    public void setActivateCheckMode(boolean activateCheckMode)
    {
        this.activateCheckMode = activateCheckMode;
    }
    
    public void initDefaultConverters () {
    }
    
    public XStream getXStream()
    {
        return xstream;
    }
    
    /* (non-Javadoc)
     * @see com.technbolts.dto.serializer.Serializer#toXml(java.lang.Object)
     */
    public String toXml(Object rootObject) throws IOException
    {
        if(versionSerializationStrategy.checkVersionableOnRoot())
            handleVersionable(rootObject);
        return getXStream().toXML(rootObject);
    }
    
    /* (non-Javadoc)
     * @see com.technbolts.dto.serializer.Serializer#toXml(java.lang.Object, java.io.Writer)
     */
    public void toXml(Object rootObject, Writer writer) throws IOException {
        if(versionSerializationStrategy.checkVersionableOnRoot())
            handleVersionable(rootObject);
        getXStream().toXML(rootObject, writer);
    }
    
    protected void handleVersionable(Object object) {
        if(object==null)
            return;
        
        Version version = getVersion(object.getClass());
        if(version!=null && object instanceof Versionable)
            ((Versionable)object).setVersion(version);
    }
    
    /* (non-Javadoc)
     * @see com.technbolts.dto.serializer.Serializer#fromXml(java.io.InputStream)
     */
    public Object fromXml(InputStream inputStream) throws IOException
    {
        return getXStream().fromXML(inputStream);
    }
    
    /* (non-Javadoc)
     * @see com.technbolts.dto.serializer.Serializer#fromXml(java.lang.String)
     */
    public Object fromXml(String xml) throws IOException {
        return getXStream().fromXML(xml);
    }
    
    /* (non-Javadoc)
     * @see com.technbolts.dto.serializer.Serializer#fromXml(java.io.Reader)
     */
    public Object fromXml(Reader reader) throws IOException {
        return getXStream().fromXML(reader);
    }
    
    /* (non-Javadoc)
     * @see com.technbolts.dto.serializer.Serializer#getConfigurationHandler()
     */
    public ConfigurationHandler getConfigurationHandler()
    {
        return XStreamHandlerWrapper.wrap(xstream);
    }
    
    /* (non-Javadoc)
     * @see com.technbolts.dto.serializer.Serializer#recursivelyProcessAnnotations(java.lang.Class<?>,Version)
     */
    public void recursivelyProcessAnnotations(Class<?> type, Version version) throws InvalidConfigurationException {
        getAnnotationConfigurer().recursivelyProcessAnnotations(type, version);
    }
    
    private AnnotationConfigurer config;
    protected AnnotationConfigurer getAnnotationConfigurer() {
        if(config==null) {
            config = new AnnotationConfigurer (getConfigurationHandler());
            config.setActivateCheckMode(activateCheckMode);
        }
        return config;
    }
    
    /* (non-Javadoc)
     * @see com.technbolts.dto.serializer.Serializer#getVersion(java.lang.Class)
     */
    public Version getVersion(Class<?> type) {
        final AnnotationConfigurer annotationConfigurer = getAnnotationConfigurer();
        
        Version version = annotationConfigurer.getVersion(type);
        while(version==null && !type.equals(Object.class)) {
            type = type.getSuperclass();
            version = annotationConfigurer.getVersion(type);
        }
        return version;
    }
    
    protected void unknownField(Class<?> definedIn, String fieldName, Throwable throwable)
    {
        errors.add(new DTOError (ErrorCode.INVALID_ELEMENT, fieldName));
        
        // if trace enabled : trace with the exception
        if(logger.isTraceEnabled())
            logger.info("Failed to find field informations : "+fieldName+" for class "+definedIn, throwable);
        
        // otherwise simply notify on info level
        else if(logger.isInfoEnabled())
            logger.info("Failed to find field informations : "+fieldName+" for class "+definedIn);
    }
    
    private class CustomMapperWrapper extends MapperWrapper {
        public CustomMapperWrapper(MapperWrapper next)
        {
            super(next);
        }
        @SuppressWarnings("unchecked")
        public boolean shouldSerializeMember(Class definedIn, String fieldName) {
            try
            {
                // this is done to prevent XStream crash when 
                // deserializing unknown fields.
                
                boolean definedInNotObject = definedIn != Object.class;
                if (definedInNotObject || realClass(fieldName) != null)
                {
                    return super.shouldSerializeMember(definedIn, fieldName);
                }
                else
                {
                    if(logger.isDebugEnabled())
                        logger.debug("Unknown field : "+fieldName);
                }
                return false;
            }
            catch(CannotResolveClassException cnrce)
            {
                unknownField(definedIn, fieldName, cnrce);
                return false;
            }
        }
    }
}

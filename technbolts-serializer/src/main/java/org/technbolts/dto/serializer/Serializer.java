/* $Id$ */
package org.technbolts.dto.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;

import org.technbolts.dto.configuration.ConfigurationHandler;
import org.technbolts.dto.configuration.InvalidConfigurationException;
import org.technbolts.dto.configuration.Version;

public interface Serializer
{
    void setActivateCheckMode(boolean activateCheckMode);
    String toXml(Object rootObject) throws IOException;
    void   toXml(Object rootObject, Writer writer) throws IOException;
    Object fromXml(String xml) throws IOException;
    Object fromXml(Reader reader) throws IOException;
    Object fromXml(InputStream inputStream) throws IOException;
    void recursivelyProcessAnnotations(Class<?> type, Version versionType) throws InvalidConfigurationException;
    
    /**
     * Indicate the version in which the type will be serialized according to
     * the current configuration.
     * @param type
     * @return
     * @see #recursivelyProcessAnnotations(Class, Version)
     */
    Version getVersion(Class<?> type);
    
    /**
     * Retrieve a handler to configure the serializer.
     * @return
     */
    ConfigurationHandler getConfigurationHandler ();
}

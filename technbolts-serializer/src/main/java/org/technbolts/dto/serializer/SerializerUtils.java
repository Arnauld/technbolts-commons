/* $Id$ */
package org.technbolts.dto.serializer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.technbolts.dto.configuration.InvalidConfigurationException;
import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.configuration.VersionedType;

/**
 * SerializerUtils
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class SerializerUtils
{
    public static final String UTF8 = "UTF8";

    public static void recursivelyProcessAnnotations (Serializer serializer, VersionedType...versionnedTypes) throws InvalidConfigurationException {
        for(VersionedType versionedType : versionnedTypes)
            serializer.recursivelyProcessAnnotations(versionedType.getType(), versionedType.getVersion());
    }
    
    public static Writer toUTF8Writer (OutputStream out) throws UnsupportedEncodingException {
        return new BufferedWriter(new OutputStreamWriter(out, UTF8));
    }
    
    public static void writeUTF8XmlDeclaration (Writer writer) throws IOException {
        writeXmlDeclaration(writer,UTF8);
    }
    
    public static void writeXmlDeclaration (Writer writer, String encoding) throws IOException {
        writer.write("<?xml version=\"1.0\" encoding=\""+encoding+"\" standalone=\"yes\"?>");
    }
    
}

/* $Id$ */
package org.technbolts.dto.serializer;

import org.technbolts.dto.serializer.xstream.XStreamSerializer;

public class SerializerFactory
{
    public static Serializer createDefaultSerializer () {
        return new XStreamSerializer();
    }
}

/* $Id$ */
package org.technbolts.dto.domain.common;

import org.technbolts.dto.configuration.Version;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import org.technbolts.util.Adaptable;

public class VersionConverter implements Adaptable
{
    private SingleValueConverter xstreamSingleValueConverter;

    @SuppressWarnings("unchecked")
    public <T> T getAdapter(Class<T> adapter)
    {
        if(adapter.equals(SingleValueConverter.class))
        {
            if(xstreamSingleValueConverter==null)
                xstreamSingleValueConverter = createXStreamSingleValueConverter ();
            return (T)xstreamSingleValueConverter;
        }
            
        return null;
    }

    private SingleValueConverter createXStreamSingleValueConverter()
    {
        return new SingleValueConverter () {
            @SuppressWarnings("unchecked")
            public boolean canConvert(Class type)
            {
                return type.equals(Version.class);
            }
            public Object fromString(String str)
            {
                return Version.fromStringIdentifier(str);
            }
            public String toString(Object obj)
            {
                Version version = (Version)obj;
                return version.toStringIdentifier();
            }
        };
    }

}

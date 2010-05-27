/* $Id$ */
package org.technbolts.dto.domain.common;

import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.configuration.VersionSensitive;
import org.apache.commons.lang.math.NumberUtils;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.technbolts.util.Adaptable;

/**
 * ErrorCodeConverter
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class ErrorCodeConverter implements Adaptable, VersionSensitive
{
    private Converter xstreamConverter;
    private final Version version;
    
    public ErrorCodeConverter()
    {
        this.version = null;
    }

    public ErrorCodeConverter(Version version)
    {
        this.version = version;
    }
    
    public Object specializeFor(Version version)
    {
        return new ErrorCodeConverter(version);
    }

    @SuppressWarnings("unchecked")
    public <T> T getAdapter(Class<T> adapter)
    {
        if(adapter.equals(Converter.class))
        {
            if(xstreamConverter==null)
                xstreamConverter = createXStreamConverter ();
            return (T)xstreamConverter;
        }
        return null;
    }
    
    protected Converter createXStreamConverter () {
        Version versionToUse = version;
        if(versionToUse==null)
            versionToUse = Version.V1;
            
        final String attrId   = "id";
        final String attrName = "label";
        return new Converter () {
            @SuppressWarnings("unchecked")
            public boolean canConvert(Class type)
            {
                return (type==ErrorCode.class);
            }
            public void marshal(Object source, HierarchicalStreamWriter writer,
                    MarshallingContext context)
            {
                ErrorCode error = (ErrorCode) source;
                writer.addAttribute(attrId, String.valueOf(error.getCode()));
                writer.addAttribute(attrName, error.getName());
            }
            public Object unmarshal(HierarchicalStreamReader reader,
                    UnmarshallingContext context)
            {
                ErrorCode error = null;
                
                String codeAsString = reader.getAttribute(attrId);
                int code = NumberUtils.toInt(codeAsString, -1);
                error = ErrorCode.fromCode(code);
                return error;
            }
        };
    }
    
}

/* $Id$ */
package org.technbolts.dto.domain.common;

import org.technbolts.dto.configuration.VersionSensitive;
import org.technbolts.dto.configuration.EnumAliasHelper;
import org.technbolts.dto.configuration.Version;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import org.technbolts.util.Adaptable;

/**
 * SearchTypeConverter
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class SearchTypeConverter implements Adaptable, VersionSensitive
{
    private static EnumAliasHelper<SearchType> helper = new EnumAliasHelper<SearchType> (SearchType.class);
    
    public static SearchType fromString(String serializationString, Version version) {
        if(serializationString==null)
            return null;
        return helper.fromString(serializationString, version);
    }
    
    public static String toString(SearchType searchType, Version version) {
        if(searchType==null)
            return null;
        return helper.toString(searchType, version);
    }
    
    
    private SingleValueConverter xstreamSingleValueConverter;
    
    // since the same converter can be used in different version
    // keep the version field final, so that a different version needs 
    // a new copy of the converter
    private final Version version;
    
    public SearchTypeConverter()
    {
        this.version = null;
    }
    
    public SearchTypeConverter(Version version)
    {
        this.version = version;
    }
    
    public Object specializeFor(Version version)
    {
        if(this.version==version)
            return this; // nothing to do already in the right version
        
        // return a copy in the wanted version
        return new SearchTypeConverter (version);
    }

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
                return type.equals(SearchType.class);
            }
            public Object fromString(String str)
            {
                return SearchTypeConverter.fromString(str, version);
            }
            public String toString(Object obj)
            {
                SearchType searchType = (SearchType)obj;
                return SearchTypeConverter.toString(searchType, version);
            }
        };
    }
}

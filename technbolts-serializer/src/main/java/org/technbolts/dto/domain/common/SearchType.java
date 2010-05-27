/* $Id$ */
package org.technbolts.dto.domain.common;

import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.configuration.annotation.*;

/**
 * SearchType
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
@Converters({
    @Converter(clazz=SearchTypeConverter.class, since=Version.V0)
})
public enum SearchType
{
    @Since(Version.V0)
    @Aliases({
        @Alias(value="equality", until=Version.V0),
        @Alias(value="equals",   since=Version.V1)
        })
    Equals,
    
    @Since(Version.V1)
    @Alias("contains")
    Contains,
    
    @Since(Version.V1)
    @Alias("starts_with")
    StartsWith,
    
    @Since(Version.V1)
    @Alias("ends_with")
    EndsWith,
    
    @Since(Version.V1)
    @Alias("empty")
    Empty,
    
    @Since(Version.V1)
    @Alias("wildcard")
    Wildcard;
    
}

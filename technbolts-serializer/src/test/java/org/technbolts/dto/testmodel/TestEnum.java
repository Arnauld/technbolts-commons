/* $Id$ */
package org.technbolts.dto.testmodel;

import org.junit.Ignore;

import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.configuration.annotation.*;
import org.technbolts.dto.configuration.annotation.Aliases;
import org.technbolts.dto.configuration.annotation.Since;
import org.technbolts.dto.configuration.annotation.Alias;

@Ignore
public enum TestEnum
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
    
    @Until(Version.V1)
    @Alias("startsWith")
    StartsWith,
}

/* $Id$ */
package org.technbolts.dto.testmodel;

import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.configuration.Versionable;
import org.technbolts.dto.configuration.annotation.Converter;
import org.technbolts.dto.configuration.annotation.Since;
import org.technbolts.dto.domain.common.VersionConverter;
import org.junit.Ignore;

import org.technbolts.dto.configuration.annotation.Alias;
import org.technbolts.dto.configuration.annotation.AsAttribute;

/**
 * TestVersionableData
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
@Alias(value="versionableData", since=Version.V0)
@Ignore
public class TestVersionableData implements Versionable
{
    @Since(Version.V0)
    @Alias(value="count", since=Version.V0)
    private int count;
    
    @Since(Version.V0)
    @Converter(clazz= VersionConverter.class)
    @AsAttribute
    private Version version;
    
    public void setCount(int count)
    {
        this.count = count;
    }
    
    public int getCount()
    {
        return count;
    }
    
    /* (non-Javadoc)
     * @see com.technbolts.dto.configuration.Versionable#setVersion(com.technbolts.dto.configuration.Version)
     */
    public void setVersion(Version version)
    {
        this.version = version;
    }
    
    public Version getVersion()
    {
        return version;
    }
}

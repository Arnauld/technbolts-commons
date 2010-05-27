/* $Id$ */
package org.technbolts.dto.testmodel;

import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.configuration.annotation.Alias;
import org.technbolts.dto.configuration.annotation.Require;
import org.junit.Ignore;

/**
 * TestLinkedVersionableData
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
@Alias("linked-versionable-data")
@Ignore
public class TestLinkedVersionableData
{
    @Alias("linked")
    @Require(Version.V2)
    private TestVersionableData linked;
    
    public void setLinked(TestVersionableData linked)
    {
        this.linked = linked;
    }
    
    public TestVersionableData getLinked()
    {
        return linked;
    }
}

/* $Id$ */
package org.technbolts.dto.testmodel;

import java.util.Date;

import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.configuration.annotation.Alias;
import org.technbolts.dto.configuration.annotation.Aliases;
import org.technbolts.dto.configuration.annotation.Since;
import org.junit.Ignore;

import org.technbolts.dto.configuration.annotation.Require;

@Aliases({
    @Alias(value="parent_data", until= Version.V1),
    @Alias(value="parentData",  since=Version.V2)})
@Ignore
public class TestParentData
{
    @Aliases({
        @Alias(value="creation_date",     since=Version.V0, until=Version.V1),
        @Alias(value="creation_datetime", since=Version.V2)
      })
    private Date creationDate;

    @Since(Version.V2)
    @Alias("extra")
    @Require(Version.V1)
    private TestData infos;

    public Date getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(Date creationDate)
    {
        this.creationDate = creationDate;
    }

    public TestData getInfos()
    {
        return infos;
    }

    public void setInfos(TestData infos)
    {
        this.infos = infos;
    }
}

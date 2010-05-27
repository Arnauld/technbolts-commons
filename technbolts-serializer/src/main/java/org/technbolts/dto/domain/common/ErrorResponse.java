/* $Id$ */
package org.technbolts.dto.domain.common;

import java.util.Arrays;
import java.util.List;

import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.configuration.Versionable;
import org.technbolts.dto.configuration.annotation.Alias;
import org.technbolts.dto.configuration.annotation.Implicit;
import org.technbolts.dto.configuration.annotation.Require;
import org.technbolts.dto.configuration.annotation.Since;
import org.technbolts.dto.configuration.annotation.VersionField;

/**
 * ErrorResponse
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
@Alias(value="errors", since= Version.V1)
public class ErrorResponse implements Versionable
{
    @Since(Version.V1)
    @Implicit(itemFieldName="error", since=Version.V1)
    @Require(value=Version.V1, since=Version.V1)
    private List<DTOError> errors;
    
    @Since(Version.V1)
    @VersionField
    private Version version;
    
    public ErrorResponse(DTOError... errors) {
        this.errors = Arrays.asList(errors);
    }
    
    public List<DTOError> getErrors() {
        return errors;
    }
    
    /* (non-Javadoc)
     * @see com.technbolts.dto.configuration.Versionable#setVersion(com.technbolts.dto.configuration.Version)
     */
    public void setVersion(Version version) {
        this.version = version;
    }
    
    public Version getVersion() {
        return version;
    }
}

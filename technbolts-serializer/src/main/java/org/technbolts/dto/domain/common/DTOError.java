/* $Id$ */
package org.technbolts.dto.domain.common;

import java.util.Arrays;
import java.util.List;

import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.configuration.annotation.*;
import org.technbolts.dto.configuration.annotation.Alias;
import org.technbolts.dto.configuration.annotation.Converter;
import org.technbolts.dto.configuration.annotation.Since;

/**
 * DTOError
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
@Alias(value="error", since=Version.V1)
public class DTOError
{
    @Since(Version.V1)
    @Converter(clazz=ErrorCodeConverter.class)
    @Alias(value="code", since=Version.V1)
    private ErrorCode code;

    @Since(Version.V1)
    @Implicit(itemFieldName="argument", since= Version.V1)
    private List<String> arguments;
    
    @Since(Version.V1)
    @Alias(value="object_id", since=Version.V1)
    private String objectId;
    
    public DTOError(ErrorCode errorCode, String... arguments)
    {
        this.code      = errorCode;
        this.arguments = Arrays.asList(arguments);
    }
    
    public void setObjectId(String objectId)
    {
        this.objectId = objectId;
    }
    
    public String getObjectId()
    {
        return objectId;
    }
    
    public ErrorCode getCode()
    {
        return code;
    }
    
    public List<String> getArguments()
    {
        return arguments;
    }
}

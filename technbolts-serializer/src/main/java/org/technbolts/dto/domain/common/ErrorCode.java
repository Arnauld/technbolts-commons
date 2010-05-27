/* $Id$ */
package org.technbolts.dto.domain.common;

/**
 * ErrorCode holds the enumeration of all error code that can be used.
 * 
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public enum ErrorCode
{
    INVALID_ELEMENT (100, "element_invalid"),
    
    INTERNAL_ERROR  (101, "internal_error"),
    
    MISSING_COMMAND (102, "missing_command"),
    
    UNKNOWN_COMMAND (103, "unknown_command")
    ;
    
    private int code;
    private String name;
    ErrorCode(int code, String name) {
        this.code = code;
        this.name = name;
    }
    
    public Integer getCode()
    {
        return code;
    }
    
    public String getName()
    {
        return name;
    }
    
    public static ErrorCode fromCode (int code) {
        for(ErrorCode errorCode : values()) {
            if(errorCode.getCode()==code)
                return errorCode;
        }
        return null;
    }
}

/* $Id$ */
/* DTOServiceException.java $Id$ */
package org.technbolts.dto.service;

/**
 * Default exception used by dto service layer.
 * 
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class DTOServiceRuntimeException extends RuntimeException {


    /**
     * 
     */
    private static final long serialVersionUID = -6442223480680224332L;

    /**
     * 
     */
    public DTOServiceRuntimeException() {
    }

    /**
     * @param message
     */
    public DTOServiceRuntimeException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public DTOServiceRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public DTOServiceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}

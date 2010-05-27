/* $Id$ */
/* DTOServiceException.java $Id$ */
package org.technbolts.dto.service;



/**
 * Default exception used by dto service layer.
 * 
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class DTOServiceException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 2107203454912900594L;
    
    /**
     * 
     */
    public DTOServiceException() {
    }

    /**
     * @param message
     */
    public DTOServiceException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public DTOServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public DTOServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}

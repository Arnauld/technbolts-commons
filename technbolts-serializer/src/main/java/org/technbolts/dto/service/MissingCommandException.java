/* $Id$ */
package org.technbolts.dto.service;


/**
 * MissingCommandException
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class MissingCommandException extends DTOServiceException {

    /**
     * 
     */
    private static final long serialVersionUID = -396475762892224644L;
    
    public MissingCommandException(String message) {
        super(message);
    }
    public MissingCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}

/* $Id$ */
package org.technbolts.dto.service;

/**
 * RethrowRuntimeExceptionHandler
 * 
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class RethrowRuntimeExceptionHandler implements ExceptionHandler {

    public void handleException(Exception cause) {
        throw new DTOServiceRuntimeException(cause);
    }
}

/* $Id$ */
package org.technbolts.dto.service;


/**
 * UnknownCommandException
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class UnknownCommandException extends DTOServiceException {

    /**
     * 
     */
    private static final long serialVersionUID = 6176989086931394306L;
    
    private String command;
    
    public UnknownCommandException(String command) {
        super("Unknown command '"+command+"'");
        this.command = command;
    }
    
    public UnknownCommandException(String command, String message) {
        super(message);
        this.command = command;
    }
    
    public UnknownCommandException(String command, String message, Throwable cause) {
        super(message, cause);
        this.command = command;
    }
    
    public String getCommand() {
        return command;
    }
}

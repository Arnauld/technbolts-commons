/* $Id$ */
package org.technbolts.dto.service;

import java.util.List;

public class DTOMessageBuilder {

    public String buildUnknownCommandMessage(String command, List<String> supportedCommands) {
        StringBuilder builder = new StringBuilder ();
        builder
            .append("Command is unknown or is not supported: '")
            .append(command)
            .append("'");
        
        appendSupportedCommands(builder, supportedCommands);
        
        return builder.toString();
    }
    
    public String buildMissingCommand(List<String> supportedCommands) {
        StringBuilder builder = new StringBuilder ();
        builder
            .append("Command is missing or cannot be extracted from input");
        
        appendSupportedCommands(builder, supportedCommands);
        
        return builder.toString();
    }
    
    private void appendSupportedCommands(StringBuilder message, List<String> supportedCommands) {
        if(supportedCommands==null || supportedCommands.isEmpty())
            return;
        
        message.append(". Supported commands are: ");
        for(String command : supportedCommands) {
            message.append('\'').append(command).append("', ");
        }
        // remove last ", "
        message.setLength(message.length()-2);
    }
}

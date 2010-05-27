/* $Id$ */
package org.technbolts.dto.service.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.domain.common.DTOError;
import org.technbolts.dto.domain.common.ErrorCode;
import org.technbolts.dto.serializer.Serializer;
import org.technbolts.dto.serializer.SerializerFactory;
import org.technbolts.dto.serializer.SerializerUtils;
import org.apache.commons.io.IOUtils;

import org.technbolts.dto.service.DTOMessageBuilder;
import org.technbolts.dto.service.*;
import org.technbolts.dto.service.ErrorResponseWriter;
import org.technbolts.dto.service.MissingCommandException;
import org.technbolts.dto.service.UnknownCommandException;
import org.technbolts.dto.service.DTOServiceException;
import org.technbolts.util.New;
import org.technbolts.util.Option;

public class XMLCall {
    private static Logger logger = LoggerFactory.getLogger(XMLCall.class);
    
    private DTOMessageBuilder messagebuilder = new DTOMessageBuilder();
    private Map<String,Handler> handlerMap;
    private InputStream xmlInput;
    private Writer output;
    //
    private String commandKey;
    private Option<Version> inputVersion;
    
    public XMLCall(InputStream xmlInput, Writer output) {
        this.xmlInput = xmlInput;
        this.setOutput(output);
        this.setHandlerMap(null);
    }
    
    public XMLCall(InputStream xmlInput, OutputStream output) {
        this.xmlInput = xmlInput;
        this.setOutput(output);
        this.setHandlerMap(null);
    }
    
    public void setHandlerMap(Map<String,Handler> handlerMap) {
        this.handlerMap = New.hashMap();
        if(handlerMap!=null)
            this.handlerMap.putAll(handlerMap);
    }
    
    /**
     * @return
     */
    public List<String> getSupportedCommands() {
        return New.arrayList(handlerMap.keySet());
    }
    
    private void setOutput(OutputStream output) {
        try {
            setOutput(SerializerUtils.toUTF8Writer(output));
        }
        catch (UnsupportedEncodingException e) {
            if(logger.isErrorEnabled())
                logger.error("We are in trouble UTF8 is not supported...: "+e.getMessage(),e);
        }
    }
    
    private void setOutput(Writer output) {
        this.output = output;
    }
    
    /**
     * @throws UnsupportedEncodingException
     */
    public void dispatch() {
        try {
            doDispatch();
        }
        catch (Exception e) {
            if(exceptionHandler!=null)
                exceptionHandler.handleException(e);
            else 
            if(logger.isErrorEnabled())
                logger.error("Error while processing lifecycle event data: "+e.getMessage(),e);
        }
    }
    
    /**
     * @throws Exception
     */
    public void doDispatch() throws Exception {
        XMLCommandReader commandReader = new XMLCommandReader();
        commandReader.processXmlInput(xmlInput);
        Option<String> command = commandReader.getCommand();
        InputStream reconstructedStream = commandReader.getReconstructedStream();
        
        if(!command.isDefined()) {
            dumpInputOnError(reconstructedStream);
            String message = messagebuilder.buildMissingCommand(getSupportedCommands());
            writeError(ErrorCode.MISSING_COMMAND, message);
            if(commandReader.hasException())
                throw new MissingCommandException(message, commandReader.getException());
            else
                throw new MissingCommandException(message);
        }
        
        commandKey    = command.get();
        inputVersion  = commandReader.getVersion();
        Handler handler = handlerMap.get(commandKey);
        if(handler!=null) {
            handler.handle(reconstructedStream, this);
            return;
        }
        else {
            dumpInputOnError(reconstructedStream);
            String message = messagebuilder.buildUnknownCommandMessage(commandKey, getSupportedCommands());
            writeError(ErrorCode.UNKNOWN_COMMAND, message);
            if(commandReader.hasException())
                throw new UnknownCommandException(commandKey, message, commandReader.getException());
            else
                throw new UnknownCommandException(commandKey, message);
        }
    }
    
    public String getCommandKey() {
        return commandKey;
    }
    
    public Option<Version> getInputVersion() {
        return inputVersion;
    }
    
    /**
     * @param xmlInput
     */
    private void dumpInputOnError(InputStream xmlInput) {
        if(!shouldDumpInputOnError || !logger.isDebugEnabled())
            return;
        
        try
        {
            String input = IOUtils.toString(xmlInput);
            logger.debug("Input received: ");
            logger.debug(input);
        }
        catch(Exception e) {
            logger.debug("Failed to dump input", e);
        }
    }

    /**
     * @param <T>
     * @param response
     * @param expectedType
     * @return
     * @throws org.technbolts.dto.service.DTOServiceException
     */
    public <T> T ensureConsistency (Object response, Class<T> expectedType) throws DTOServiceException {
        if(response==null) {
            String message = "Empty class deserialized, expected: "+expectedType;
            writeError(ErrorCode.UNKNOWN_COMMAND, message);
            throw new DTOServiceException(message);
        }
        else
        if(!expectedType.isAssignableFrom(response.getClass())) {
            String message = "Invalid class deserialized, got: "+response.getClass()+" expected: "+expectedType;
            writeError(ErrorCode.UNKNOWN_COMMAND, message);
            throw new DTOServiceException(message);
        }
        return expectedType.cast(response);
    }
    
    /**
     * @param serializer
     * @param response
     * @throws DTOServiceException
     */
    public void writeResponse(Serializer serializer, Object response) throws DTOServiceException{
        try {
            SerializerUtils.writeUTF8XmlDeclaration(output);
            serializer.toXml(response, output);
        }
        catch (UnsupportedEncodingException e) {
            throw new DTOServiceException("Failed to write response: "+e.getMessage());
        }
        catch (IOException e) {
            throw new DTOServiceException("Failed to write response: "+e.getMessage(), e);
        }
    }

    protected Serializer createSerializer() {
        return SerializerFactory.createDefaultSerializer();
    }
    
    public void writeError(ErrorCode code, String message) throws DTOServiceException {
        DTOError error = new DTOError(code, message);
        writeError(error);
    }
    
    public void writeError(DTOError error) throws DTOServiceException {
        writeError(output, error);
    }
    
    private void writeError(Writer output, DTOError error) throws DTOServiceException {
        try {
            new ErrorResponseWriter()//
                    .withOuput(output)//
                    .withSerializer(createSerializer())//
                    .withVersion(getErrorVersion())//
                    .write(error)
                    .flushUnderlying();
        }
        catch (Exception e) {
            throw new DTOServiceException("Failed to write errors...", e);
        }
    }

    private Version errorVersion = Version.V1;
    public void setErrorVersion(Version errorVersion) {
        this.errorVersion = errorVersion;
    }
    private Version getErrorVersion() {
        return errorVersion;
    }
    
    private ExceptionHandler exceptionHandler;
    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }
    
    private boolean shouldDumpInputOnError;
    public void setShouldDumpInputOnError(boolean shouldDumpInputOnError) {
        this.shouldDumpInputOnError = shouldDumpInputOnError;
    }
    
    public interface Handler {
        public void handle(InputStream reconstructedStream, XMLCall xmlCall) throws DTOServiceException;
    }
    
}

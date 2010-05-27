/* $Id$ */
package org.technbolts.dto.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.technbolts.dto.configuration.InvalidConfigurationException;
import org.technbolts.dto.domain.common.ErrorResponse;
import org.technbolts.dto.serializer.Serializer;
import org.technbolts.dto.serializer.SerializerUtils;

import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.domain.common.DTOError;

/**
 * ErrorResponseWriter
 * 
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class ErrorResponseWriter {
    private Logger logger = LoggerFactory.getLogger(ErrorResponseWriter.class);
    private Serializer serializer;
    private Writer underlying;
    private Version version = Version.V1;

    public ErrorResponseWriter() {
    }

    public Serializer getSerializer() {
        return serializer;
    }
    
    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }
    public ErrorResponseWriter withSerializer(Serializer serializer) {
        this.setSerializer(serializer);
        return this;
    }
    
    public Writer getUnderlying() {
        return underlying;
    }
    
    public void setOuput(Writer writer) {
        this.underlying = writer;
    }
    
    public ErrorResponseWriter withOuput(Writer writer) {
        this.setOuput(writer);
        return this;
    }
    
    public Version getVersion() {
        return version;
    }
    
    public void setVersion(Version version) {
        this.version = version;
    }
    
    public ErrorResponseWriter withVersion(Version version) {
        this.setVersion(version);
        return this;
    }
    
    public void setOuput(OutputStream stream) throws DTOServiceException {
        try {
            setOuput(SerializerUtils.toUTF8Writer(stream));
        }
        catch (UnsupportedEncodingException e) {
            throw new DTOServiceException("UTF8 is not supported", e);
        }
    }
    
    public ErrorResponseWriter withOuput(OutputStream stream) throws DTOServiceException {
        this.setOuput(stream);
        return this;
    }
    
    public ErrorResponseWriter write(List<DTOError> errors) throws DTOServiceException {
        return write(new ErrorResponse(toArray(errors)));
    }

    public ErrorResponseWriter write(DTOError... errors) throws DTOServiceException {
        return write(new ErrorResponse(errors));
    }

    private static DTOError[] toArray(List<DTOError> errors) {
        return errors.toArray(new DTOError[errors.size()]);
    }

    public ErrorResponseWriter write(ErrorResponse errorResponse) throws DTOServiceException {
        if(serializer==null)
            throw new DTOServiceException("No serializer defined");
        if(underlying==null)
            throw new DTOServiceException("No output defined");
        if(version==null) {
            if(logger.isDebugEnabled())
                logger.debug("No version provided: assuming V1");
            version = Version.V1;
        }
        
        try {
            serializer.recursivelyProcessAnnotations(ErrorResponse.class, getVersion());
            String xml = serializer.toXml(errorResponse);
            if(logger.isDebugEnabled())
                logger.debug("Response written: "+xml);
            underlying.write(xml);
            return this;
        }
        catch (InvalidConfigurationException e) {
            throw new DTOServiceException("Invalid dto configuration", e);
        }
        catch (IOException e) {
            throw new DTOServiceException("Failed to write output", e);
        }
    }
    
    public void flushUnderlying () {
        try {
            if(underlying!=null)
                underlying.flush();
        }
        catch (IOException e) {
            if (logger.isErrorEnabled())
                logger.error("Failure while flushing underlying output: " + e.getMessage(), e);
        }
    }
    
    public void closeUnderlying () {
        try {
            if(underlying!=null)
                underlying.close();
        }
        catch (IOException e) {
            if (logger.isErrorEnabled())
                logger.error("Failure while closing underlying output: " + e.getMessage(), e);
        }
    }
}

/* $Id$ */
package org.technbolts.dto.service.xml;

import static org.technbolts.util.Option.someOrNone;
import static org.technbolts.util.Option.none;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.serializer.SerializerUtils;

import org.technbolts.util.io.ReadAndKeepInputStream;
import org.technbolts.util.Option;
import com.thoughtworks.xstream.io.xml.XppReader;

/**
 * CommandReader
 * 
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class XMLCommandReader {
    private static Logger logger = LoggerFactory.getLogger(XMLCommandReader.class);

    private String encoding = SerializerUtils.UTF8;

    private Option<String> nodeName = none();
    private Option<Version> version = none();
    private InputStream reconstructedStream;
    private Exception exception;

    public XMLCommandReader() {
    }

    public void setEncoding(String encoding) {
        if (encoding == null)
            throw new IllegalArgumentException("Null encoding not allowed");
        this.encoding = encoding;
    }

    public String getEncoding() {
        return encoding;
    }

    public void processXmlInput(InputStream xmlInput) {
        try {
            doProcessXmlInput(xmlInput);
        }
        catch (UnsupportedEncodingException e) {
            // should not happen
            if (logger.isErrorEnabled())
                logger.error("Unsupported encoding: " + SerializerUtils.UTF8, e);
        }
    }
    
    public Exception getException() {
        return exception;
    }
    
    public boolean hasException () {
        return (exception!=null);
    }

    public InputStream getReconstructedStream() {
        return reconstructedStream;
    }

    public Option<String> getCommand() {
        return nodeName;
    }

    public Option<Version> getVersion() {
        return version;
    }

    protected void doProcessXmlInput(InputStream xmlInput) throws UnsupportedEncodingException {
        ReadAndKeepInputStream readAndKeepStream = new ReadAndKeepInputStream(xmlInput);
        try {
            XppReader reader =  createXppReader(readAndKeepStream, getEncoding());
            nodeName = someOrNone(reader.getNodeName());

            String nodeVersion = reader.getAttribute(Version.ATTRIBUTE_NAME);
            if (nodeVersion != null) {
                version = someOrNone(Version.fromStringIdentifier(nodeVersion));
            }
        }
        catch(Exception exception) {
            this.exception = exception;
        }
        finally {
            // in any case reconstruct the stream for reuse
            reconstructedStream = readAndKeepStream.reconstructInputStream();
        }
    }

    protected XppReader createXppReader(InputStream inputStream, String encoding) throws UnsupportedEncodingException {
        return new XppReader(new InputStreamReader(inputStream, encoding));
    }

}

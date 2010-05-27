/* $Id$ */
package org.technbolts.dto.domain.common.v1;

import static org.technbolts.util.xml.XPathUtils.*;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import org.technbolts.dto.domain.common.DTOError;
import org.technbolts.dto.domain.common.ErrorCode;
import org.technbolts.dto.domain.common.ErrorResponse;
import org.technbolts.dto.serializer.xstream.XStreamSerializer;
import junit.framework.TestCase;

import org.technbolts.util.xml.DOMHelperImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.technbolts.dto.configuration.Version;

public class ErrorResponseTest extends TestCase
{
    private XStreamSerializer serializer;
    private StringWriter stringWriter;
    
    @Override
    protected void setUp() throws Exception
    {
        stringWriter = new StringWriter ();
        serializer   = new XStreamSerializer ();
    }
    
    public void testMarshallEx1 () throws Exception {
        ErrorResponse response = new ErrorResponse(
                error(ErrorCode.INVALID_ELEMENT, "grounch"));
        
        serializer.recursivelyProcessAnnotations(ErrorResponse.class, Version.V1);
        serializer.toXml(response, stringWriter);
        String xml = stringWriter.toString();
        
        Document document = new DOMHelperImpl().toDocument(xml);
        assertThat(stringValue(document, "/errors/@version"), equalTo("v1"));
        
        NodeList nodeList = select(document, "/errors/error/*");
        assertThat (nodeList.getLength(), equalTo(2));
        
        Node nodeCode = nodeList.item(0);
        assertThat(stringValue(nodeCode, "@id"), equalTo("100"));
        assertThat(stringValue(nodeCode, "@label"), equalTo("element_invalid"));
        
        Node nodeArg = nodeList.item(1);
        assertThat(stringValue(nodeArg, "text()"), equalTo("grounch"));
    }
    
    public void testUnmarshallEx1 () throws Exception {
        serializer.recursivelyProcessAnnotations(ErrorResponse.class, Version.V1);
        
        Object o = serializer.fromXml(getResource ("ErrorResponseCase01.xml"));
        assertThat(o, notNullValue());
        assertThat(o, is(ErrorResponse.class));
        ErrorResponse errorResponse = (ErrorResponse)o;
        
        List<DTOError> errors = errorResponse.getErrors();
        assertThat(errors, notNullValue());
        assertThat(errors.size(), equalTo(1));
        
        DTOError error = errors.get(0);
        assertThat(error, notNullValue());
        assertThat(error.getCode(), equalTo(ErrorCode.INVALID_ELEMENT));
        
        List<String> arguments = error.getArguments();
        assertThat(arguments, notNullValue());
        assertThat(arguments.get(0), equalTo("grounch"));
    }
    
    public void testMarshallUnmarshallEx1 () throws Exception {
        ErrorResponse response = new ErrorResponse (
                error(ErrorCode.INVALID_ELEMENT, "grounch"));
        
        serializer.recursivelyProcessAnnotations(ErrorResponse.class, Version.V1);
        
        serializer.toXml(response, stringWriter);
        String xml = stringWriter.toString();
        
        // create a new one to be sure every thing is clean
        serializer = new XStreamSerializer ();
        serializer.recursivelyProcessAnnotations(ErrorResponse.class, Version.V1);
        Object unmarshalled = (ErrorResponse)serializer.getXStream().fromXML(xml);
        
        assertThat(unmarshalled, notNullValue());
        assertThat(unmarshalled, is(ErrorResponse.class));
        ErrorResponse unmarshalledResponse = (ErrorResponse)unmarshalled;
        assertThat(unmarshalledResponse.getErrors(), notNullValue());
        assertThat(unmarshalledResponse.getErrors().size(), equalTo(1));
        DTOError unmarshalledError = unmarshalledResponse.getErrors().get(0);
        assertThat(unmarshalledError, notNullValue());
        assertThat(unmarshalledError.getCode(), equalTo(ErrorCode.INVALID_ELEMENT));
        assertThat(unmarshalledError.getArguments(), equalTo(asList("grounch")));
        assertThat(unmarshalledError.getObjectId(), nullValue());
    }
    
    private static DTOError error(ErrorCode code, String...arguments) {
        return new DTOError(code, arguments);
    }
    
    private InputStream getResource(String resource)
    {
        return ErrorResponseTest.class.getResourceAsStream(resource);
    }
}

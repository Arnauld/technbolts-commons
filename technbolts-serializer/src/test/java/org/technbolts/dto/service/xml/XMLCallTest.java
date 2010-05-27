/* $Id$ */
package org.technbolts.dto.service.xml;

import static org.technbolts.util.xml.XPathUtils.stringValue;
import static org.apache.commons.io.IOUtils.toByteArray;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.technbolts.dto.domain.common.ErrorCode;
import org.technbolts.dto.service.MissingCommandException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.service.ExceptionHandler;
import org.technbolts.dto.service.UnknownCommandException;
import org.technbolts.util.New;
import org.technbolts.util.xml.DOMHelperImpl;
import org.w3c.dom.Document;

/**
 * XMLCallTest
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class XMLCallTest {
    
    private Map<String, XMLCall.Handler> handlerMap;
    private ExceptionHandler exceptionHandlerMock;
    private boolean shouldDumpInputOnError;
    private ByteArrayOutputStream bous;
    private XMLCall.Handler handlerMock;
    
    @Before
    public void setUp () {
        bous = new ByteArrayOutputStream();
        exceptionHandlerMock = Mockito.mock(ExceptionHandler.class);
        handlerMock = Mockito.mock(XMLCall.Handler.class);
    }

    @Test
    public void emptyXml () throws Exception {
        XMLCall xmlCall = createXMLCall(new ByteArrayInputStream(new byte[0]), bous);
        xmlCall.dispatch();
        
        ArgumentCaptor<Exception> exCaptor = ArgumentCaptor.forClass(Exception.class);
        verify(exceptionHandlerMock).handleException(exCaptor.capture());
        assertThat (exCaptor.getValue(), is(MissingCommandException.class));
        
        Document document = new DOMHelperImpl().toDocument(new ByteArrayInputStream(bous.toByteArray()));
        assertThat (stringValue(document, "/errors/error/code/@id"), equalTo(ErrorCode.MISSING_COMMAND.getCode().toString()));
    }
    
    @Test
    public void unknownXmlCommand () throws Exception {
        XMLCall xmlCall = createXMLCall(utf8Stream("<SomeWeirdXml/>"), bous);
        xmlCall.dispatch();
        
        ArgumentCaptor<Exception> exCaptor = ArgumentCaptor.forClass(Exception.class);
        verify(exceptionHandlerMock).handleException(exCaptor.capture());
        assertThat (exCaptor.getValue(), is(UnknownCommandException.class));
        
        Document document = new DOMHelperImpl().toDocument(new ByteArrayInputStream(bous.toByteArray()));
        assertThat (stringValue(document, "/errors/error/code/@id"), equalTo(ErrorCode.UNKNOWN_COMMAND.getCode().toString()));
    }
    
    @Test
    public void notXmlCommand () throws Exception {
        String xml = "some data that is not xml ...";
        
        XMLCall xmlCall = createXMLCall(utf8Stream(xml), bous);
        xmlCall.dispatch();
        
        ArgumentCaptor<Exception> exCaptor = ArgumentCaptor.forClass(Exception.class);
        verify(exceptionHandlerMock).handleException(exCaptor.capture());
        assertThat (exCaptor.getValue(), is(MissingCommandException.class));
        
        Document document = new DOMHelperImpl().toDocument(new ByteArrayInputStream(bous.toByteArray()));
        assertThat (stringValue(document, "/errors/error/code/@id"), equalTo(ErrorCode.MISSING_COMMAND.getCode().toString()));
    }
    
    @Test
    public void ioError () throws Exception {
        final String incompleteXml = "<SomeWeirdXml ";
        
        XMLCall xmlCall = createXMLCall(new InputStream() {
            int pos;
            byte[] data = utf8Array(incompleteXml);
            @Override
            public int read() throws IOException {
                if(pos<data.length)
                    return data[pos++];
                throw new IOException ("No more data !!!");
            }
        }, bous);
        xmlCall.dispatch();
        
        ArgumentCaptor<Exception> exCaptor = ArgumentCaptor.forClass(Exception.class);
        verify(exceptionHandlerMock).handleException(exCaptor.capture());
        assertThat (exCaptor.getValue(), is(MissingCommandException.class));
        assertThat (exCaptor.getValue().getCause(), notNullValue());
        
        Document document = new DOMHelperImpl().toDocument(new ByteArrayInputStream(bous.toByteArray()));
        assertThat (stringValue(document, "/errors/error/code/@id"), equalTo(ErrorCode.MISSING_COMMAND.getCode().toString()));
    }
    
    @Test
    public void knownXmlCommand_withVersionV1 () throws Exception {
        knownXmlCommand_withVersion("<MyCommand version=\"v1\" />", Version.V1);
    }
    
    @Test
    public void knownXmlCommand_withVersionV2 () throws Exception {
        knownXmlCommand_withVersion("<MyCommand version=\"v2\" />", Version.V2);
    }
    
    @Test
    public void knownXmlCommand_withInvalidVersion () throws Exception {
        knownXmlCommand_withVersion("<MyCommand version=\"vX\" />", null);
    }
    
    @Test
    public void knownXmlCommand_withoutVersion () throws Exception {
        knownXmlCommand_withVersion("<MyCommand name='zog' />", null);
    }
    
    private void knownXmlCommand_withVersion(String xmlInput, Version expectedVersion) throws Exception {
        String MY_COMMAND = "MyCommand";
        
        handlerMap = New.hashMap();
        handlerMap.put(MY_COMMAND, handlerMock);
        XMLCall xmlCall = createXMLCall(utf8Stream(xmlInput), bous);
        xmlCall.dispatch();
        
        ArgumentCaptor<InputStream> streamCaptor = ArgumentCaptor.forClass(InputStream.class);
        verify(handlerMock).handle(streamCaptor.capture(), Mockito.eq(xmlCall));
        
        InputStream reconstructedStream = streamCaptor.getValue();
        assertThat(toByteArray(reconstructedStream), equalTo(utf8Array(xmlInput)));
        assertThat(xmlCall.getCommandKey(), equalTo(MY_COMMAND));
        assertThat(xmlCall.getInputVersion(), notNullValue());
        
        if(expectedVersion==null)
            assertThat(xmlCall.getInputVersion().isDefined(), equalTo(false));
        else
            assertThat(xmlCall.getInputVersion().get(), equalTo(expectedVersion));
    }
    
    private static byte[] utf8Array(String data) throws UnsupportedEncodingException {
        return data.getBytes("UTF8");
    }
    
    private static InputStream utf8Stream(String data) throws UnsupportedEncodingException {
        return new ByteArrayInputStream(utf8Array(data));
    }
    
    private XMLCall createXMLCall(InputStream xmlInput, OutputStream outputStream) {
        XMLCall xmlCall = new XMLCall (xmlInput, outputStream);
        xmlCall.setErrorVersion(Version.V1);
        xmlCall.setShouldDumpInputOnError(true);
        xmlCall.setHandlerMap(handlerMap);
        xmlCall.setExceptionHandler(exceptionHandlerMock);
        xmlCall.setShouldDumpInputOnError(shouldDumpInputOnError);
        return xmlCall;
    }
}

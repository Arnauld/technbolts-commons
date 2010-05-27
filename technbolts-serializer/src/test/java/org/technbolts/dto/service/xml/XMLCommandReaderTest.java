/* $Id$ */
package org.technbolts.dto.service.xml;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.technbolts.dto.configuration.Version;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * XMLCommandReaderTest
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class XMLCommandReaderTest {
    
    private XMLCommandReader cmdReader;
    
    @Before
    public void setUp () {
        cmdReader = new XMLCommandReader();
    }

    @Test
    public void emptyXml () throws Exception {
        cmdReader.processXmlInput(new ByteArrayInputStream(new byte[0]));
        
        assertThat (cmdReader.getCommand(), notNullValue());
        assertThat (cmdReader.getCommand().isDefined(), equalTo(false));
        assertThat (cmdReader.getVersion(), notNullValue());
        assertThat (cmdReader.getVersion().isDefined(), equalTo(false));
        
        Exception exception = cmdReader.getException();
        assertThat (exception, notNullValue());
        
        byte[] reconstructedXml = IOUtils.toByteArray(cmdReader.getReconstructedStream());
        assertThat (reconstructedXml, equalTo(new byte[0]));
    }
    
    @Test
    public void notXml () throws Exception {
        final String notXml = "some data that is not xml ...";
        cmdReader.processXmlInput(utf8Stream(notXml));
        
        assertThat (cmdReader.getCommand(), notNullValue());
        assertThat (cmdReader.getCommand().isDefined(), equalTo(false));
        assertThat (cmdReader.getVersion(), notNullValue());
        assertThat (cmdReader.getVersion().isDefined(), equalTo(false));
        
        Exception exception = cmdReader.getException();
        assertThat (exception, notNullValue());
        
        byte[] reconstructedXml = IOUtils.toByteArray(cmdReader.getReconstructedStream());
        assertThat (reconstructedXml, equalTo(utf8Array(notXml)));
    }
    
    @Test
    public void ioError () throws Exception {
        final String incompleteXml = "<SomeWeirdXml ";
        cmdReader.processXmlInput(new InputStream() {
            int pos;
            byte[] data = utf8Array(incompleteXml);
            @Override
            public int read() throws IOException {
                if(pos<data.length)
                    return data[pos++];
                throw new IOException ("No more data !!!");
            }
        });
        
        assertThat (cmdReader.getCommand(), notNullValue());
        assertThat (cmdReader.getCommand().isDefined(), equalTo(false));
        assertThat (cmdReader.getVersion(), notNullValue());
        assertThat (cmdReader.getVersion().isDefined(), equalTo(false));
        
        Exception exception = cmdReader.getException();
        assertThat (exception, notNullValue());
        
        byte[] validData = utf8Array(incompleteXml);
        int read = cmdReader.getReconstructedStream().read(validData);
        assertThat (read, equalTo(validData.length));
        assertThat (validData, equalTo(utf8Array(incompleteXml)));
        
        try {
            cmdReader.getReconstructedStream().read();
        }catch(IOException ioe) {
            assertThat (ioe.getMessage(), equalTo("No more data !!!"));
        }
    }
    
    @Test
    public void xmlCommand () throws Exception {
        final String xml = "<SomeWeirdXml/>";
        cmdReader.processXmlInput(utf8Stream(xml));
        
        assertThat (cmdReader.getCommand(), notNullValue());
        assertThat (cmdReader.getCommand().isDefined(), equalTo(true));
        assertThat (cmdReader.getCommand().get(), equalTo("SomeWeirdXml"));
        assertThat (cmdReader.getVersion(), notNullValue());
        assertThat (cmdReader.getVersion().isDefined(), equalTo(false));
        
        byte[] reconstructedXml = IOUtils.toByteArray(cmdReader.getReconstructedStream());
        assertThat (reconstructedXml, equalTo(utf8Array(xml)));
    }
    
    @Test
    public void xmlCommand_withVersionV1 () throws Exception {
        final String xml = "<MyCommand version=\"v1\" />";
        cmdReader.processXmlInput(utf8Stream(xml));
        
        assertThat (cmdReader.getCommand(), notNullValue());
        assertThat (cmdReader.getCommand().isDefined(), equalTo(true));
        assertThat (cmdReader.getCommand().get(), equalTo("MyCommand"));
        assertThat (cmdReader.getVersion(), notNullValue());
        assertThat (cmdReader.getVersion().isDefined(), equalTo(true));
        assertThat (cmdReader.getVersion().get(), equalTo(Version.V1));
        
        byte[] reconstructedXml = IOUtils.toByteArray(cmdReader.getReconstructedStream());
        assertThat (reconstructedXml, equalTo(utf8Array(xml)));
    }
    
    @Test
    public void xmlCommand_withVersionV2 () throws Exception {
        final String xml = "<MyCommand version=\"v2\" />";
        cmdReader.processXmlInput(utf8Stream(xml));
        
        assertThat (cmdReader.getCommand(), notNullValue());
        assertThat (cmdReader.getCommand().isDefined(), equalTo(true));
        assertThat (cmdReader.getCommand().get(), equalTo("MyCommand"));
        assertThat (cmdReader.getVersion(), notNullValue());
        assertThat (cmdReader.getVersion().isDefined(), equalTo(true));
        assertThat (cmdReader.getVersion().get(), equalTo(Version.V2));
        
        byte[] reconstructedXml = IOUtils.toByteArray(cmdReader.getReconstructedStream());
        assertThat (reconstructedXml, equalTo(utf8Array(xml)));
    }
    
    @Test
    public void xmlCommand_withInvalidVersion () throws Exception {
        final String xml = "<MyCommand version=\"vXX\" />";
        cmdReader.processXmlInput(utf8Stream(xml));
        
        assertThat (cmdReader.getCommand(), notNullValue());
        assertThat (cmdReader.getCommand().isDefined(), equalTo(true));
        assertThat (cmdReader.getCommand().get(), equalTo("MyCommand"));
        assertThat (cmdReader.getVersion(), notNullValue());
        assertThat (cmdReader.getVersion().isDefined(), equalTo(false));
        
        byte[] reconstructedXml = IOUtils.toByteArray(cmdReader.getReconstructedStream());
        assertThat (reconstructedXml, equalTo(utf8Array(xml)));
    }
    
    private static byte[] utf8Array(String data) throws UnsupportedEncodingException {
        return data.getBytes("UTF8");
    }
    
    private static InputStream utf8Stream(String data) throws UnsupportedEncodingException {
        return new ByteArrayInputStream(utf8Array(data));
    }

}

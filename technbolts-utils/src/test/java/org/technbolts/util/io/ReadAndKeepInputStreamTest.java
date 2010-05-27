/* $Id$ */
package org.technbolts.util.io;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.technbolts.util.BytesUtils.bytes;
import static org.technbolts.util.BytesUtils.firstBytes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

/**
 * ReadAndKeepInputStreamTest
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class ReadAndKeepInputStreamTest
{
    private ReadAndKeepInputStream readAndKeep;
    
    @Test
    public void testReconstruct_readPartly () throws IOException {
        InputStream input  = new ByteArrayInputStream(bytes(0xCA, 0xFE, 0xBA, 0xBE, 0xBE, 0xEF));
        readAndKeep = new ReadAndKeepInputStream(input);
        byte[] buffer = new byte[3];
        assertThat(readAndKeep.read(buffer), equalTo(3));
        assertThat(buffer, equalTo(bytes(0xCA, 0xFE, 0xBA)));
        
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        InputStream inputStream = readAndKeep.reconstructInputStream();
        IOUtils.copy(inputStream, bytes);
        assertThat(bytes.toByteArray(), equalTo(bytes(0xCA, 0xFE, 0xBA, 0xBE, 0xBE, 0xEF)));
    }
    
    @Test
    public void testReconstruct_readFully () throws IOException {
        InputStream input  = new ByteArrayInputStream(bytes(0xCA, 0xFE, 0xBA, 0xBE, 0xBE, 0xEF));
        readAndKeep = new ReadAndKeepInputStream(input);
        byte[] buffer = new byte[10];
        assertThat(readAndKeep.read(buffer), equalTo(6));
        assertThat(firstBytes(buffer, 6), equalTo(bytes(0xCA, 0xFE, 0xBA, 0xBE, 0xBE, 0xEF)));
        
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        InputStream inputStream = readAndKeep.reconstructInputStream();
        IOUtils.copy(inputStream, bytes);
        assertThat(bytes.toByteArray(), equalTo(bytes(0xCA, 0xFE, 0xBA, 0xBE, 0xBE, 0xEF)));
    }
    
}

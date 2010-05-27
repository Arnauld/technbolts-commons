/* $Id$ */
package org.technbolts.util.io;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.technbolts.util.BytesUtils.bytes;
import static org.technbolts.util.BytesUtils.firstBytes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * ChainableInputStreamTest
 * @author <a href="mailto:arnauld.loyer@eptica.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class ChainableInputStreamTest
{
    private ChainableInputStream chainable;

    @Test
    public void testReadOneByOne_normalCase () throws IOException {
        
        InputStream first  = new ByteArrayInputStream(bytes(0xCA, 0xFE));
        InputStream second = new ByteArrayInputStream(bytes(0xBA, 0xBE));
        chainable = new ChainableInputStream(first, second);
        assertThat(chainable.read(), equalTo(0xCA));
        assertThat(chainable.read(), equalTo(0xFE));
        assertThat(chainable.read(), equalTo(0xBA));
        assertThat(chainable.read(), equalTo(0xBE));
        assertThat(chainable.read(), equalTo(-1));
    }
    
    @Test
    public void testReadOneByOne_firstEmpty () throws IOException {
        
        InputStream first  = new ByteArrayInputStream(bytes());
        InputStream second = new ByteArrayInputStream(bytes(0xBA, 0xBE));
        chainable = new ChainableInputStream(first, second);
        assertThat(chainable.read(), equalTo(0xBA));
        assertThat(chainable.read(), equalTo(0xBE));
        assertThat(chainable.read(), equalTo(-1));
    }
    
    @Test
    public void testReadOneByOne_secondEmpty () throws IOException {
        
        InputStream first  = new ByteArrayInputStream(bytes(0xCA, 0xFE));
        InputStream second = new ByteArrayInputStream(bytes());
        chainable = new ChainableInputStream(first, second);
        assertThat(chainable.read(), equalTo(0xCA));
        assertThat(chainable.read(), equalTo(0xFE));
        assertThat(chainable.read(), equalTo(-1));
    }
    
    @Test
    public void testReadByArray_normalCase_arrayIsMultipleOfFirst () throws IOException {
        
        InputStream first  = new ByteArrayInputStream(bytes(0xCA, 0xFE, 0xBA, 0xBE));
        InputStream second = new ByteArrayInputStream(bytes(0xFA, 0xCA, 0xDE));
        chainable = new ChainableInputStream(first, second);
        byte[] buffer = new byte[2];
        int read = chainable.read(buffer);
        assertThat(read,   equalTo(2));
        assertThat(buffer, equalTo(bytes(0xCA, 0xFE)));
        
        read = chainable.read(buffer);
        assertThat(read,   equalTo(2));
        assertThat(buffer, equalTo(bytes(0xBA, 0xBE)));
        
        read = chainable.read(buffer);
        assertThat(read,   equalTo(2));
        assertThat(buffer, equalTo(bytes(0xFA, 0xCA)));
        
        read = chainable.read(buffer);
        assertThat(read,      equalTo(1));
        assertThat(buffer[0], equalTo((byte)0xDE));
        
        read = chainable.read(buffer);
        assertThat(read, equalTo(-1));
    }
    
    @Test
    public void testReadByArray_normalCase_arrayIsNotMultipleOfFirst () throws IOException {
        
        InputStream first  = new ByteArrayInputStream(bytes(0xCA, 0xFE, 0xBA, 0xBE));
        InputStream second = new ByteArrayInputStream(bytes(0xFA, 0xCA, 0xDE, 0xBE, 0xEF));
        chainable = new ChainableInputStream(first, second);
        byte[] buffer = new byte[3];
        
        int read = chainable.read(buffer);
        assertThat(read,   equalTo(3));
        assertThat(buffer, equalTo(bytes(0xCA, 0xFE, 0xBA)));
        
        read = chainable.read(buffer);
        assertThat(read,   equalTo(1));
        assertThat(buffer[0], equalTo((byte)0xBE));
        
        read = chainable.read(buffer);
        assertThat(read,   equalTo(3));
        assertThat(buffer, equalTo(bytes(0xFA, 0xCA, 0xDE)));
        
        read = chainable.read(buffer);
        assertThat(read,      equalTo(2));
        assertThat(buffer[0], equalTo((byte)0xBE));
        assertThat(buffer[1], equalTo((byte)0xEF));
        
        read = chainable.read(buffer);
        assertThat(read, equalTo(-1));
    }
    
    @Test
    public void testReadByArray_firstIsEmpty () throws IOException {
        
        InputStream first  = new ByteArrayInputStream(bytes());
        InputStream second = new ByteArrayInputStream(bytes(0xFA, 0xCA, 0xDE, 0xBE, 0xEF));
        chainable = new ChainableInputStream(first, second);
        byte[] buffer = new byte[3];
        
        int read = chainable.read(buffer);
        assertThat(read,   equalTo(3));
        assertThat(buffer, equalTo(bytes(0xFA, 0xCA, 0xDE)));
        
        read = chainable.read(buffer);
        assertThat(read,      equalTo(2));
        assertThat(buffer[0], equalTo((byte)0xBE));
        assertThat(buffer[1], equalTo((byte)0xEF));
        
        read = chainable.read(buffer);
        assertThat(read, equalTo(-1));
    }
    
    @Test
    public void testReadByArray_secondIsEmpty () throws IOException {
        
        InputStream first  = new ByteArrayInputStream(bytes(0xCA, 0xFE, 0xBA, 0xBE));
        InputStream second = new ByteArrayInputStream(bytes());
        chainable = new ChainableInputStream(first, second);
        byte[] buffer = new byte[3];
        
        int read = chainable.read(buffer);
        assertThat(read,   equalTo(3));
        assertThat(buffer, equalTo(bytes(0xCA, 0xFE, 0xBA)));
        
        read = chainable.read(buffer);
        assertThat(read,   equalTo(1));
        assertThat(buffer[0], equalTo((byte)0xBE));
        
        read = chainable.read(buffer);
        assertThat(read, equalTo(-1));
    }
    
    @Test
    public void testReadByArray_biggerArray () throws IOException {
        
        InputStream first  = new ByteArrayInputStream(bytes(0xCA, 0xFE, 0xBA, 0xBE));
        InputStream second = new ByteArrayInputStream(bytes(0xFA, 0xCA, 0xDE, 0xBE, 0xEF));
        chainable = new ChainableInputStream(first, second);
        byte[] buffer = new byte[10];
        
        int read = chainable.read(buffer);
        assertThat(read,   equalTo(4));
        assertThat(firstBytes(buffer, 4), equalTo(bytes(0xCA, 0xFE, 0xBA, 0xBE)));
        
        read = chainable.read(buffer);
        assertThat(read,   equalTo(5));
        assertThat(firstBytes(buffer, 5), equalTo(bytes(0xFA, 0xCA, 0xDE, 0xBE, 0xEF)));
        
        read = chainable.read(buffer);
        assertThat(read, equalTo(-1));
    }
    
    @Test
    public void testReadByArray_bothEmpty () throws IOException {
        
        InputStream first  = new ByteArrayInputStream(bytes());
        InputStream second = new ByteArrayInputStream(bytes());
        chainable = new ChainableInputStream(first, second);
        byte[] buffer = new byte[10];
        
        int read = chainable.read(buffer);
        assertThat(read, equalTo(-1));
    }
}

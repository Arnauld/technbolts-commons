/* $Id$ */
package org.technbolts.util.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ReadAndKeepInputStream
 * @version $Revision$
 */
public class ReadAndKeepInputStream extends InputStream
{
    private ByteArrayOutputStream alreadyRead;
    private InputStream underlying;
    
    public ReadAndKeepInputStream(InputStream underlying)
    {
        this(underlying,32);
    }
    
    public ReadAndKeepInputStream(InputStream underlying, int initialKeepBufferSize)
    {
        this.underlying  = underlying;
        this.alreadyRead = new ByteArrayOutputStream(initialKeepBufferSize);
    }
    
    /**
     * Reconstruct an <code>InputStream</code> using the bytes already
     * read and the bytes remaining in the underlying <code>InputStream</code>.
     * @return
     */
    public InputStream reconstructInputStream () {
        return new ChainableInputStream(new ByteArrayInputStream(alreadyRead.toByteArray()), underlying);
    }

    @Override
    public int read() throws IOException
    {
        int read = underlying.read();
        if(read!=-1)
            alreadyRead.write(read);
        return read;
    }
    
    @Override
    public int read(byte[] b, int off, int len) throws IOException
    {
        int read = underlying.read(b, off, len);
        if(read>0)
            alreadyRead.write(b, off, read);
        return read;
    }
    
    @Override
    public int available() throws IOException
    {
        return underlying.available();
    }
}

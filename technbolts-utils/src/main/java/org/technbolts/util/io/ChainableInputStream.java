/* $Id$ */
package org.technbolts.util.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * ChainableInputStream
 * @version $Revision$
 */
public class ChainableInputStream extends InputStream
{
    private final InputStream first;
    private final InputStream second;
    private boolean firstReadCompleted;
    
    public ChainableInputStream(InputStream first, InputStream second)
    {
        this.first  = first;
        this.second = second;
    }
    
    @Override
    public void close() throws IOException
    {
        IOException err = null;
        try
        {
            first.close();
        }
        catch(IOException ioe) {
            // store the error, otherwise one would not attempt to close 'second'
            err = ioe;
        }
        
        try
        {
            second.close();
        }
        catch(IOException ioe) {
            throw ioe;
        }
        if(err!=null)
            throw err;
    }
    
    @Override
    public int read() throws IOException
    {
        if(!firstReadCompleted) {
            int val = first.read();
            if(val==-1)
                firstReadCompleted = true;
            else
                return val;
        }
        return second.read();
    }
    
    @Override
    public int read(byte[] b, int off, int len) throws IOException
    {
        checkInputIsValid (b, off, len);
        if (len == 0)
            return 0;
            
        if(!firstReadCompleted) {
            int read = first.read(b, off, len);
            if(read==-1)
                firstReadCompleted = true;
            else
                return read;
        }
        return second.read(b, off, len);
    }
    
    @Override
    public int available() throws IOException
    {
        if(!firstReadCompleted) {
            int available = first.available();
            if(available>0)
                return available;
        }
        return second.available();
    }

    private static void checkInputIsValid (byte[] b, int off, int len) throws IOException {
        if (b == null)
            throw new NullPointerException();
        else if (  (off < 0) 
                || (off > b.length) 
                || (len < 0) 
                || ((off + len) > b.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        }
    }
}

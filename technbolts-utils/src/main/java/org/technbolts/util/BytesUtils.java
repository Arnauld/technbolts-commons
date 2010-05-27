/* $Id$ */
package org.technbolts.util;

/**
 * BytesUtils
 * @author <a href="mailto:arnauld.loyer@eptica.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class BytesUtils
{
    /**
     * @param bytes
     * @param count
     * @return
     */
    public static byte[] firstBytes(byte[] bytes, int count) {
        byte[] sub = new byte[count];
        System.arraycopy(bytes, 0, sub, 0, count);
        return sub;
    }
    
    /**
     * @param ints
     * @return
     */
    public static byte[] bytes(int...ints) {
        byte[] bytes = new byte[ints.length];
        int pos = 0;
        for(int val:ints)
            bytes[pos++] = (byte)val;
        return bytes;
    }
}

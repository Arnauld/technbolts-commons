/* $Id$ */
package org.technbolts.util;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * BytesUtils
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class BytesUtils
{
    public static final int SIZE_OF_BYTE   = Byte.SIZE / Byte.SIZE;
    public static final int SIZE_OF_SHORT  = Short.SIZE / Byte.SIZE;
    public static final int SIZE_OF_INT    = Integer.SIZE / Byte.SIZE;
    public static final int SIZE_OF_LONG   = Long.SIZE / Byte.SIZE;
    public static final int SIZE_OF_FLOAT  = Float.SIZE / Byte.SIZE;
    public static final int SIZE_OF_DOUBLE = Double.SIZE / Byte.SIZE;


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
    public static byte[] toBytes(int...ints) {
        byte[] bytes = new byte[ints.length];
        int pos = 0;
        for(int val:ints)
            bytes[pos++] = (byte)(0xFF & val);
        return bytes;
    }

    /**
     * Compute the md5 hash of the input catching all the irritating exceptions
     * for you
     *
     * @param input The input to take the hash of
     * @return The MD5 hash of the input bytes
     */
    public static byte[] md5(byte[] input) {
        return getDigest("MD5").digest(input);
    }

    /**
     * Compute the sha1 hash of the input catching all the irritating exceptions
     * for you
     *
     * @param input The input to take the hash of
     * @return The sha1 hash of the input bytes
     */
    public static byte[] sha1(byte[] input) {
        return getDigest("SHA-1").digest(input);
    }

    public static final int MASK_00000000 = Integer.parseInt("00000000", 2);
    public static final int MASK_10000000 = Integer.parseInt("10000000", 2);
    public static final int MASK_11000000 = Integer.parseInt("11000000", 2);
    public static final int MASK_11100000 = Integer.parseInt("11100000", 2);
    public static final int MASK_10111111 = Integer.parseInt("10111111", 2);
    public static final int MASK_11011111 = Integer.parseInt("11011111", 2);
    public static final int MASK_01000000 = Integer.parseInt("10000000", 2);
    public static final int MASK_01100000 = Integer.parseInt("11000000", 2);
    public static final int MASK_01110000 = Integer.parseInt("11100000", 2);
    public static final int MASK_01011111 = Integer.parseInt("10111111", 2);
    public static final int MASK_01101111 = Integer.parseInt("11011111", 2);
    public static final int MASK_11111111 = Integer.parseInt("11111111", 2);
    public static final int MASK_01111111 = Integer.parseInt("01111111", 2);
    public static final int MASK_00111111 = Integer.parseInt("00111111", 2);
    public static final int MASK_00011111 = Integer.parseInt("00011111", 2);

    public static MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch(NoSuchAlgorithmException e) {
            throw new IllegalStateException("Unknown algorithm: " + algorithm, e);
        }
    }

    /**
     * Translate the given byte array into a hexidecimal string
     *
     * @param bytes The bytes to translate
     * @return The string
     */
    public static String toHexString(byte[] bytes) {
        StringBuilder buffer = new StringBuilder();

        for(byte b: bytes) {
            String hex = Integer.toHexString(b & 0xff);
            hex = hex.substring(0, Math.min(hex.length(), 2));
            if(hex.length() == 1) {
                buffer.append("0");
            }
            buffer.append(hex);
        }
        return buffer.toString();
    }

    /**
     * Translate the given byte array into a string of 1s and 0s
     *
     * @param bytes The bytes to translate
     * @return The string
     */
    public static String toBinaryString(byte[] bytes) {
        StringBuilder buffer = new StringBuilder();
        for(byte b: bytes) {
            String bin = Integer.toBinaryString(0xFF & b);
            bin = bin.substring(0, Math.min(bin.length(), 8));

            for(int j = 0; j < 8 - bin.length(); j++) {
                buffer.append('0');
            }

            buffer.append(bin);
        }
        return buffer.toString();
    }

    /**
     * Concatenate the given arrays
     *
     * @param array1 The arrays to concatenate
     * @param array2 The arrays to concatenate
     * @return A concatenated array
     */
    public static byte[] concat(byte[] array1, byte[] array2) {
        int size = 0;
        if(array1!=null)
            size += array1.length;
        if(array2!=null)
            size += array2.length;
        byte[] concat = new byte[size];
        int pos = 0;
        if(array1!=null) {
            System.arraycopy(array1, 0, concat, pos, array1.length);
            pos += array1.length;
        }
        if(array2!=null) {
            System.arraycopy(array2, 0, concat, pos, array2.length);
        }
        return concat;
    }

    /**
     * Concatenate the given arrays
     *
     * @param arrays The arrays to concatenate
     * @return A concatenated array
     */
    public static byte[] concat(byte[]... arrays) {
        int size = 0;
        for(byte[] a: arrays)
            if(a != null)
                size += a.length;
        byte[] concat = new byte[size];
        int pos = 0;
        for(byte[] a: arrays) {
            if(a != null) {
                System.arraycopy(a, 0, concat, pos, a.length);
                pos += a.length;
            }
        }

        return concat;
    }

    /**
     * Read a short from the byte array starting at the given offset
     *
     * @param bytes The byte array to read from
     * @param offset The offset to start reading at
     * @return The short read
     */
    public static short readShort(byte[] bytes, int offset) {
        return (short) ((bytes[offset] << 8) | (bytes[offset + 1] & 0xff));
    }

    /**
     * Read an int from the byte array starting at the given offset
     *
     * @param bytes The byte array to read from
     * @param offset The offset to start reading at
     * @return The int read
     */
    public static int readInt(byte[] bytes, int offset) {
        return (((bytes[offset + 0] & 0xff) << 24)//
                |((bytes[offset + 1] & 0xff) << 16)//
                |((bytes[offset + 2] & 0xff) << 8)//
                |((bytes[offset + 3] & 0xff)));
    }

    /**
     * Read an unsigned integer from the given byte array
     *
     * @param bytes The bytes to read from
     * @param offset The offset to begin reading at
     * @return The integer as a long
     */
    public static long readUnsignedInt(byte[] bytes, int offset) {
        return (((bytes[offset + 0] & 0xffL) << 24)//
                | ((bytes[offset + 1] & 0xffL) << 16)//
                | ((bytes[offset + 2] & 0xffL) << 8)//
                | ((bytes[offset + 3] & 0xffL)));
    }

    /**
     * Read a long from the byte array starting at the given offset
     *
     * @param bytes The byte array to read from
     * @param offset The offset to start reading at
     * @return The long read
     */
    public static long readLong(byte[] bytes, int offset) {
        return (((long) (bytes[offset + 0] & 0xff) << 56)//
                | ((long) (bytes[offset + 1] & 0xff) << 48)//
                | ((long) (bytes[offset + 2] & 0xff) << 40)//
                | ((long) (bytes[offset + 3] & 0xff) << 32)//
                | ((long) (bytes[offset + 4] & 0xff) << 24)//
                | ((long) (bytes[offset + 5] & 0xff) << 16)//
                | ((long) (bytes[offset + 6] & 0xff) << 8)//
                | ((long) bytes[offset + 7] & 0xff));
    }

    /**
     * Read a float from the byte array starting at the given offset
     *
     * @param bytes The byte array to read from
     * @param offset The offset to start reading at
     * @return The float read
     */
    public static float readFloat(byte[] bytes, int offset) {
        return Float.intBitsToFloat(readInt(bytes, offset));
    }

    /**
     * Read a double from the byte array starting at the given offset
     *
     * @param bytes The byte array to read from
     * @param offset The offset to start reading at
     * @return The double read
     */
    public static double readDouble(byte[] bytes, int offset) {
        return Double.longBitsToDouble(readUnsignedInt(bytes, offset));
    }

    /**
     * Read the given number of bytes into a long
     *
     * @param bytes The byte array to read from
     * @param offset The offset at which to begin reading
     * @param numBytes The number of bytes to read
     * @return The long value read
     */
    public static long readBytes(byte[] bytes, int offset, int numBytes) {
        int shift = 0;
        long value = 0;
        for(int i = offset + numBytes - 1; i >= offset; i--) {
            value |= (bytes[i] & 0xFFL) << shift;
            shift += 8;
        }
        return value;
    }

    /**
     * Write a short to the byte array starting at the given offset
     *
     * @param bytes The byte array
     * @param value The short to write
     * @param offset The offset to begin writing at
     */
    public static void writeShort(byte[] bytes, short value, int offset) {
        bytes[offset] = (byte) (0xFF & (value >> 8));
        bytes[offset + 1] = (byte) (0xFF & value);
    }

    /**
     * Write an int to the byte array starting at the given offset
     *
     * @param bytes The byte array
     * @param value The int to write
     * @param offset The offset to begin writing at
     */
    public static void writeInt(byte[] bytes, int value, int offset) {
        bytes[offset] = (byte) (0xFF & (value >> 24));
        bytes[offset + 1] = (byte) (0xFF & (value >> 16));
        bytes[offset + 2] = (byte) (0xFF & (value >> 8));
        bytes[offset + 3] = (byte) (0xFF & value);
    }

    /**
     * Write a long to the byte array starting at the given offset
     *
     * @param bytes The byte array
     * @param value The long to write
     * @param offset The offset to begin writing at
     */
    public static void writeLong(byte[] bytes, long value, int offset) {
        bytes[offset] = (byte) (0xFF & (value >> 56));
        bytes[offset + 1] = (byte) (0xFF & (value >> 48));
        bytes[offset + 2] = (byte) (0xFF & (value >> 40));
        bytes[offset + 3] = (byte) (0xFF & (value >> 32));
        bytes[offset + 4] = (byte) (0xFF & (value >> 24));
        bytes[offset + 5] = (byte) (0xFF & (value >> 16));
        bytes[offset + 6] = (byte) (0xFF & (value >> 8));
        bytes[offset + 7] = (byte) (0xFF & value);
    }

    /**
     * Write a float to the byte array starting at the given offset
     *
     * @param bytes The byte array
     * @param value The float to write
     * @param offset The offset to begin writing at
     */
    public static void writeFloat(byte[] bytes, float value, int offset) {
        writeInt(bytes, Float.floatToIntBits(value), offset);
    }

    /**
     * Write a double to the byte array starting at the given offset
     *
     * @param bytes The byte array
     * @param value The double to write
     * @param offset The offset to begin writing at
     */
    public static void writeDouble(byte[] bytes, double value, int offset) {
        writeLong(bytes, Double.doubleToLongBits(value), offset);
    }

    /**
     * Write the given number of bytes out to the array
     *
     * @param bytes The array to write to
     * @param value The value to write from
     * @param offset the offset into the array
     * @param numBytes The number of bytes to write
     */
    public static void writeBytes(byte[] bytes, long value, int offset, int numBytes) {
        int shift = 0;
        for(int i = offset + numBytes - 1; i >= offset; i--) {
            bytes[i] = (byte) (0xFF & (value >> shift));
            shift += 8;
        }
    }


}

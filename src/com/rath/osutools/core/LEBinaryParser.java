
package com.rath.osutools.core;

public class LEBinaryParser {
  
  /** The number of bytes in a C# int. */
  public static final int NUM_BYTES_INT = 4;
  
  /** The number of bytes in a C# long. */
  public static final int NUM_BYTES_LONG = 8;
  
  /** The number of bytes in a C# char. */
  public static final int NUM_BYTES_CHAR = 1;
  
  /** The number of bytes in a C# short. */
  public static final int NUM_BYTES_SHORT = 2;
  
  /**
   * Returns a String from a byte array.
   * 
   * @param dat the byte array.
   * @param offset the index the String begins at (counting the 0x0b).
   * @return the String data.
   */
  public static final String getString(final byte[] dat, final int offset) {
    
    // System.err.println(" Getting string at byte " + offset + ":");
    
    // Return the empty string if there is no entry
    if (getLEVal(dat, offset, NUM_BYTES_CHAR) == 0) return "";
    
    // Get the String's length (ULEB128 value)
    int strLen = 0;
    int byteCount = 0;
    int i = 1;
    while (true) {
      if (byteCount > 9) break;
      final int currVal = (int) getLEVal(dat, offset + i++, NUM_BYTES_CHAR);
      strLen |= (currVal & 0x0000007f) << (byteCount * 7);
      if (currVal < 128) break;
      byteCount++;
    }
    byteCount++;
    
    // Get the String's data
    final char[] strData = new char[(int) strLen];
    for (i = 0; i < strLen; i++) {
      strData[i] = getChar(dat, offset + byteCount + i + 1);
    }
    
    return new String(strData);
    
  }
  
  // TODO: Javadoc these
  /**
   * 
   * @param b
   * @param offset
   * @return
   */
  public static final int getInt(final byte[] b, final int offset) {
    return (int) getLEVal(b, offset, NUM_BYTES_INT);
  }
  
  /**
   * 
   * @param b
   * @param offset
   * @return
   */
  public static final int getShort(final byte[] b, final int offset) {
    return (int) getLEVal(b, offset, NUM_BYTES_SHORT);
  }
  
  /**
   * 
   * @param b
   * @param offset
   * @return
   */
  public static final char getChar(final byte[] b, final int offset) {
    return (char) getLEVal(b, offset, NUM_BYTES_CHAR);
  }
  
  /**
   * 
   * @param b
   * @param offset
   * @return
   */
  public static final long getLong(final byte[] b, final int offset) {
    return getLEVal(b, offset, NUM_BYTES_LONG);
  }
  
  /**
   * Returns an int representation of a Little-Endian byte range.
   * 
   * @param dat the byte array to parse.
   * @param offset the index to start at.
   * @param size the size of the value.
   * @return a converted int.
   */
  private static final long getLEVal(final byte[] dat, final int offset, final int size) {
    
    // Get the first byte
    long result = 0;
    
    // Shift and mask byte-by-byte until the desired length
    for (int i = 0; i < size; i++) {
      final long byteVal = Byte.toUnsignedLong(dat[offset + i]);
      // System.out.println(" Byte val: " + byteVal + "(" + (char) byteVal + ")");
      result |= (byteVal << (8 * i));
    }
    
    return result;
  }
  
  public static final double log2(final double n) {
    
    // WHY IS THIS FUNCTION NOT BUILT-IN!?
    return (Math.log(n) / Math.log(2));
  }
  
}

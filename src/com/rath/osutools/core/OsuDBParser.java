
package com.rath.osutools.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * This class handles parsing of the osu!.db file for beatmap hashes and metadata.
 * 
 * @author Tim Backus tbackus127@gmail.com
 *
 */
public class OsuDBParser {
  
  /** The number of bytes in a C# int. */
  private static final int NUM_BYTES_INT = 4;
  
  /** The number of bytes in a C# char. */
  private static final int NUM_BYTES_CHAR = 1;
  
  /**
   * Builds a table that maps from hash to beatmap metadata.
   * 
   * @param path the path to the osu!.db file.
   * @return a HashMap from String -> OsuDBData.
   */
  public static final HashMap<String, OsuDBData> parseOsuDB(String path) {
    
    final Path osuDBPath = Paths.get(path);
    byte[] dbData = null;
    try {
      dbData = Files.readAllBytes(osuDBPath);
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    if (dbData == null) return null;
    
    return parseOsuDB(dbData);
    
  }
  
  /**
   * Builds a mapping of beatmap hash to beatmap metadata from the osu!.db file.
   * 
   * @param dbData the beatmap data as a byte array.
   * @return a HashMap of type String -> OsuDBData.
   */
  private static final HashMap<String, OsuDBData> parseOsuDB(final byte[] dbData) {
    
    // System.err.println("Calculating start point.");
    int beatmapDataOffset = getNextString(dbData, 17).length() + 19;
    // System.err.println("Starting point: " + beatmapDataOffset);
    
    // Get the number of beatmaps we have data for
    final int beatmapCount = getLEVal(dbData, beatmapDataOffset, NUM_BYTES_INT);
    System.err.println("Data entry count: " + beatmapCount);
    beatmapDataOffset += NUM_BYTES_INT;
    
    // IT'S A LITERAL HASH MAP. GET IT!?
    final HashMap<String, OsuDBData> mapHashes = new HashMap<String, OsuDBData>();
    
    // Go through all beatmap data entries
    for (int i = 0; i < beatmapCount; i++) {
      int entryOffset = beatmapDataOffset;
      // System.err.println("Parsing entry at: " + entryOffset);
      
      // Get the data size (int)
      final int beatmapDataSize = getLEVal(dbData, entryOffset, NUM_BYTES_INT);
      entryOffset += NUM_BYTES_INT;
      // System.err.println(" Data size: " + beatmapDataSize);
      
      // Get the artist name (String)
      final String artistName = getNextString(dbData, entryOffset);
      // System.err.println(" Artist: " + artistName);
      entryOffset += artistName.length() + 2;
      
      // Check if next byte is 0x00, if it is, skip Unicode artist
      if (getLEVal(dbData, entryOffset, NUM_BYTES_CHAR) == 0
          && getLEVal(dbData, entryOffset + 1, NUM_BYTES_CHAR) == 11) {
        entryOffset++;
      } else {
        entryOffset += getNextString(dbData, entryOffset).length() + 2;
      }
      
      // Get the song title (String)
      final String songTitle = getNextString(dbData, entryOffset);
      // System.err.println(" Title: " + songTitle);
      entryOffset += songTitle.length() + 2;
      
      // Check if next byte is 0x00, if it is, skip Unicode title
      if (getLEVal(dbData, entryOffset, NUM_BYTES_CHAR) == 0
          && getLEVal(dbData, entryOffset + 1, NUM_BYTES_CHAR) == 11) {
        entryOffset++;
      } else {
        entryOffset += getNextString(dbData, entryOffset).length() + 2;
      }
      
      // Get the creator name (String)
      final String creatorName = getNextString(dbData, entryOffset);
      // System.err.println(" Creator: " + creatorName);
      entryOffset += creatorName.length() + 2;
      
      // Get the difficulty name (String)
      final String diffName = getNextString(dbData, entryOffset);
      // System.err.println(" Difficulty: " + diffName);
      entryOffset += diffName.length() + 2;
      
      // Get the audio file name (String, only used for length)
      final String audfn = getNextString(dbData, entryOffset);
      entryOffset += audfn.length() + 2;
      
      // Get the beatmap hash (String)
      final String beatmapHash = getNextString(dbData, entryOffset);
      // System.err.println(" Hash: " + beatmapHash);
      
      // Add the hash and map it to the beatmap's data struct
      final OsuDBData dbDat = new OsuDBData(artistName, songTitle, creatorName, diffName);
      mapHashes.put(beatmapHash, dbDat);
      
      System.out.println("Added " + beatmapHash + ":\n" + dbDat + "\n");
      
      // Set next data offset
      beatmapDataOffset += beatmapDataSize + NUM_BYTES_INT;
    }
    
    return null;
  }
  
  /**
   * Returns a String from a byte array.
   * 
   * @param dat the byte array.
   * @param offset the index the String begins at (counting the 0x0b).
   * @return the String data.
   */
  private static final String getNextString(final byte[] dat, final int offset) {
    
    // System.err.println(" Getting string at byte " + offset + ":");
    
    // Get the String's length
    final int len = getLEVal(dat, offset + 1, NUM_BYTES_CHAR);
    if (len == 0) return "";
    
    // System.err.println(" Length = " + len);
    
    // Get the String's data
    final char[] stringData = new char[len];
    for (int i = 0; i < len; i++) {
      stringData[i] = (char) getLEVal(dat, offset + i + 2, NUM_BYTES_CHAR);
    }
    
    return new String(stringData);
  }
  
  /**
   * Returns an int representation of a Little-Endian byte range.
   * 
   * @param dat the byte array to parse.
   * @param offset the index to start at.
   * @param size the size of the value.
   * @return a converted int.
   */
  private static final int getLEVal(final byte[] dat, final int offset, final int size) {
    
    // Get the first byte
    int result = 0;
    
    // Shift and mask byte-by-byte until the desired length
    for (int i = 0; i < size; i++) {
      final int byteVal = Byte.toUnsignedInt(dat[offset + i]);
      // System.err.println(" Byte val: " + byteVal + "(" + (char) byteVal + ")");
      result |= (byteVal << (8 * i));
    }
    
    return result;
  }
  
}

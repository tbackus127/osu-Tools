
package com.rath.osutools.replrename;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import com.rath.osutools.core.LEBinaryParser;

/**
 * This class handles parsing of the osu!.db file for beatmap hashes and metadata.
 * 
 * @author Tim Backus tbackus127@gmail.com
 *
 */
public class OsuDBParser {
  
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
    int beatmapDataOffset = LEBinaryParser.getString(dbData, 17).length() + 19;
    // System.err.println("Starting point: " + beatmapDataOffset);
    
    // Get the number of beatmaps we have data for
    final int beatmapCount = LEBinaryParser.getInt(dbData, beatmapDataOffset);
    // System.err.println("Data entry count: " + beatmapCount);
    beatmapDataOffset += LEBinaryParser.NUM_BYTES_INT;
    
    // IT'S A LITERAL HASH MAP. GET IT!?
    final HashMap<String, OsuDBData> mapHashes = new HashMap<String, OsuDBData>();
    
    // Go through all beatmap data entries
    for (int i = 0; i < beatmapCount; i++) {
      int entryOffset = beatmapDataOffset;
      // System.err.println("Parsing entry at: " + entryOffset);
      
      // Get the data size (int)
      final int beatmapDataSize = LEBinaryParser.getInt(dbData, entryOffset);
      entryOffset += LEBinaryParser.NUM_BYTES_INT;
      // System.err.println(" Data size: " + beatmapDataSize);
      
      // Get the artist name (String)
      final String artistName = LEBinaryParser.getString(dbData, entryOffset);
      // System.err.println(" Artist: " + artistName);
      if (artistName.length() == 0) {
        entryOffset++;
      } else {
        entryOffset += artistName.length() + LEBinaryParser.log2(artistName.length()) / 7 + 2;
      }
      
      // Get unicode artist (String, ignored)
      final String uniart = LEBinaryParser.getString(dbData, entryOffset);
      if (uniart.length() == 0) {
        entryOffset++;
      } else {
        entryOffset += uniart.length() + LEBinaryParser.log2(uniart.length()) / 7 + 2;
      }
      
      // Get the song title (String)
      final String songTitle = LEBinaryParser.getString(dbData, entryOffset);
      // System.err.println(" Title: " + songTitle);
      if (songTitle.length() == 0) {
        entryOffset++;
      } else {
        entryOffset += songTitle.length() + LEBinaryParser.log2(songTitle.length()) / 7 + 2;
      }
      
      // Unicode song title (String, ignored)
      final String unittl = LEBinaryParser.getString(dbData, entryOffset);
      if (unittl.length() == 0) {
        entryOffset++;
      } else {
        entryOffset += unittl.length() + LEBinaryParser.log2(unittl.length()) / 7 + 2;
      }
      
      // Get the creator name (String)
      final String creatorName = LEBinaryParser.getString(dbData, entryOffset);
      // System.err.println(" Creator: " + creatorName);
      if (creatorName.length() == 0) {
        entryOffset++;
      } else {
        entryOffset += creatorName.length() + LEBinaryParser.log2(creatorName.length()) / 7 + 2;
      }
      
      // Get the difficulty name (String)
      final String diffName = LEBinaryParser.getString(dbData, entryOffset);
      // System.err.println(" Difficulty: " + diffName);
      if (diffName.length() == 0) {
        entryOffset++;
      } else {
        entryOffset += diffName.length() + LEBinaryParser.log2(diffName.length()) / 7 + 2;
      }
      
      // Get the audio file name (String, only used for length)
      final String audfn = LEBinaryParser.getString(dbData, entryOffset);
      if (audfn.length() == 0) {
        entryOffset++;
      } else {
        entryOffset += audfn.length() + LEBinaryParser.log2(audfn.length()) / 7 + 2;
      }
      
      // Get the beatmap hash (String)
      final String beatmapHash = LEBinaryParser.getString(dbData, entryOffset);
      // System.err.println(" Hash: " + beatmapHash);
      
      // If the hash is valid (to prevent errors)
      // TODO: Fix parsing entries that don't follow this format
      if (beatmapHash.matches("[0-9a-f]{32}")) {
        
        // Add the hash and map it to the beatmap's data struct
        final OsuDBData dbDat = new OsuDBData(artistName, songTitle, creatorName, diffName);
        mapHashes.put(beatmapHash, dbDat);
        
        System.out.println("Added " + beatmapHash + ":\n" + dbDat + "\n");
      }
      
      // Set next data offset
      beatmapDataOffset += beatmapDataSize + LEBinaryParser.NUM_BYTES_INT;
    }
    
    return mapHashes;
  }
  
}

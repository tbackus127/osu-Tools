
package com.rath.osutools.replrename;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.rath.osutools.core.LEBinaryParser;

public class ReplayParser {
  
  public static final ReplayData parseReplay(final File replayFile) {
    
    final Path osuDBPath = Paths.get(replayFile.getAbsolutePath());
    byte[] replayData = null;
    try {
      replayData = Files.readAllBytes(osuDBPath);
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    if (replayData == null) return null;
    return parseReplay(replayData);
  }
  
  private static final ReplayData parseReplay(final byte[] dat) {
    
    int offset = 5;
    // System.out.println("Parsing at " + offset);
    
    // Extract beatmap hash
    final String hash = LEBinaryParser.getString(dat, offset);
    if (hash.length() == 0) {
      offset++;
    } else {
      offset += hash.length() + LEBinaryParser.log2(hash.length()) / 7 + 2;
    }
    // System.out.println("Hash: " + hash);
    
    // Extract player name
    final String player = LEBinaryParser.getString(dat, offset);
    if (player.length() == 0) {
      offset++;
    } else {
      offset += player.length() + LEBinaryParser.log2(player.length()) / 7 + 2;
    }
    // System.out.println("Player: " + player);
    
    // Skip replay hash (want map hash)
    String strIgn = LEBinaryParser.getString(dat, offset);
    if (strIgn.length() == 0) {
      offset++;
    } else {
      offset += strIgn.length() + LEBinaryParser.log2(strIgn.length()) / 7 + 2;
    }
    // System.out.println("RepHash: " + strIgn);
    
    // Skip constant-length data
    offset += 23;
    
    // Skip life bar graph
    strIgn = LEBinaryParser.getString(dat, offset);
    if (strIgn.length() == 0) {
      offset++;
    } else {
      offset += (strIgn.length() + (Math.floor(LEBinaryParser.log2(strIgn.length())) / 7) + 2);
    }
    // System.out.println("Lifebar: " + strIgn);
    
    // Extract Windows ticks
    // System.out.println("Getting ticks:");
    final long winTicks = LEBinaryParser.getLong(dat, offset);
    
    // System.out.println(String.format("Got data: %s,%s,%s\n\n", player, winTicks, hash));
    
    return new ReplayData(player, winTicks, hash);
  }
  
}

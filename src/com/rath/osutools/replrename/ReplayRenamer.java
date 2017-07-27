
package com.rath.osutools.replrename;

import java.io.File;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * This class goes through replay archives with hashed titles in the Data/r directory and renames them to the standard
 * replay format.
 * 
 * @author Tim Backus tbackus127@gmail.com
 *
 */
public class ReplayRenamer {
  
  /** The table of hashes to beatmap metadata. */
  private static HashMap<String, OsuDBData> beatmapHashMap;
  
  /** List of illegal characters in a file in Windows. */
  private static final char[] ILLEGAL_CHARS = { '/', '\\', '*', '?', ':', '<', '>', '\"', '|' };
  
  /**
   * Performs the renaming operation on all replay files in the given directory.
   * 
   * @param osudbPath the path to the user's osu!.db file.
   * @param replayPath the path to the directory containing the replay files.
   */
  public static final void renameReplays(final String osudbPath, final String replayPath) {
    
    // Build a table of all beatmap hashes to their metadata
    beatmapHashMap = OsuDBParser.parseOsuDB(osudbPath);
    
    final File replayDir = new File(replayPath);
    for (File replayFile : replayDir.listFiles()) {
      
      // System.out.println("Parsing replay: " + replayFile.getName());
      
      // Get the replay's date, player name
      final ReplayData replayData = ReplayParser.parseReplay(replayFile);
      final String player = replayData.getPlayerName();
      final long winTicks = replayData.getWinTicks();
      final String replayHash = replayData.getHash();
      
      // System.out.println("Winticks: " + winTicks);
      
      // Set the calendar after converting windows ticks to UNIX time.
      final Date dateTime = Date.from(Instant.ofEpochMilli(winTicksToJavaTime(winTicks)));
      final Calendar cal = Calendar.getInstance();
      cal.setTime(dateTime);
      
      // Extract the date/time information
      final int year = cal.get(Calendar.YEAR);
      final int month = cal.get(Calendar.MONTH);
      final int day = cal.get(Calendar.DAY_OF_MONTH);
      final int hours = cal.get(Calendar.HOUR_OF_DAY);
      final int minutes = cal.get(Calendar.MINUTE);
      final int seconds = cal.get(Calendar.SECOND);
      
      // Check if beatmap data exists
      final OsuDBData beatmapData = beatmapHashMap.get(replayHash);
      if (beatmapData != null) {
        
        // Unbox beatmap metadata
        final String artist = beatmapData.getArtistName();
        final String mapper = beatmapData.getBeatmapCreator();
        final String diff = beatmapData.getDifficultyName();
        final String title = beatmapData.getSongTitle();
        
        // Rename the replay file
        final String replayFilename = player + " - " + artist + " - " + title + " [" + diff + "] ("
            + mapper + ") " + String.format("(%04d-%02d-%02d@%02d.%02d.%02d)", year, month + 1,
                day + 1, hours, minutes, seconds)
            + ".osr";
        
        final String renamedFullPath = replayDir.getAbsolutePath() + "\\"
            + sanitizeFilename(replayFilename);
        
        System.out.println(
            "Renaming \"" + replayFile.getAbsolutePath() + "\" to \"" + renamedFullPath + "\"\n");
        replayFile.renameTo(new File(renamedFullPath));
      } else {
        System.out.println("Data for hash " + replayHash + " does not exist!\n");
      }
    }
    
  }
  
  private static final String sanitizeFilename(final String original) {
    String result = original;
    for (char c : ILLEGAL_CHARS) {
      result = result.replace(c, '_');
    }
    return result;
  }
  
  private static final long winTicksToJavaTime(final long ticks) {
    return (ticks - 621_355_968_000_000_000L) / 10_000L;
  }
}


package com.rath.osutools.replrename;

import java.util.HashMap;

import com.rath.osutools.core.OsuDBData;
import com.rath.osutools.core.OsuDBParser;

// TODO: Finish Javadocing.
/**
 * 
 * @author Tim Backus tbackus127@gmail.com
 *
 */
public class ReplayRenamer {
  
  private static HashMap<String, OsuDBData> beatmapHashMap;
  
  // 0: Path to osu!.db
  // 1: Path to data folder
  public static final void renameReplays(final String osudbPath, final String replayPath) {
    
    // Build a table of all beatmap hashes to their metadata
    beatmapHashMap = OsuDBParser.parseOsuDB(osudbPath);
    
    // TODO: Also get the replay's date, player name
  }
}

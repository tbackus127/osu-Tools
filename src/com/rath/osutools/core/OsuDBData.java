
package com.rath.osutools.core;

public class OsuDBData {
  
  private final String artistName;
  private final String songTitle;
  private final String beatmapCreator;
  private final String difficultyName;
  
  public OsuDBData(final String an, final String st, final String bc, final String dn) {
    this.artistName = an;
    this.songTitle = st;
    this.beatmapCreator = bc;
    this.difficultyName = dn;
  }
  
  public String getArtistName() {
    return artistName;
  }
  
  public String getSongTitle() {
    return songTitle;
  }
  
  public String getBeatmapCreator() {
    return beatmapCreator;
  }
  
  public String getDifficultyName() {
    return difficultyName;
  }
  
  @Override
  public String toString() {
    return artistName + " - " + songTitle + " [" + difficultyName + "] // " + beatmapCreator;
  }
}

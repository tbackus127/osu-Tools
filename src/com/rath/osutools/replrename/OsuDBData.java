
package com.rath.osutools.replrename;

/**
 * This class acts as a struct for a beatmap's metadata in the osu!.db file.
 * 
 * @author Tim Backus tbackus127@gmail.com
 *
 */
public class OsuDBData {
  
  /** The artist's name. */
  private final String artistName;
  
  /** The song title. */
  private final String songTitle;
  
  /** The mapper's username. */
  private final String beatmapCreator;
  
  /** The name of the map's difficulty. */
  private final String difficultyName;
  
  /**
   * Default constructor.
   * 
   * @param an the artist's name.
   * @param st the song title.
   * @param bc the mapper's username.
   * @param dn the difficulty name.
   */
  public OsuDBData(final String an, final String st, final String bc, final String dn) {
    this.artistName = an;
    this.songTitle = st;
    this.beatmapCreator = bc;
    this.difficultyName = dn;
  }
  
  /**
   * Gets the artist's name.
   * 
   * @return a String.
   */
  public String getArtistName() {
    return artistName;
  }
  
  /**
   * Gets the song title.
   * 
   * @return a String.
   */
  public String getSongTitle() {
    return songTitle;
  }
  
  /**
   * Gets the mapper's name.
   * 
   * @return a String.
   */
  public String getBeatmapCreator() {
    return beatmapCreator;
  }
  
  /**
   * Gets the difficulty name.
   * 
   * @return a String.
   */
  public String getDifficultyName() {
    return difficultyName;
  }
  
  /**
   * toString() method for printing.
   * 
   * @return a String representation of the beatmap in the standard format.
   */
  @Override
  public String toString() {
    return artistName + " - " + songTitle + " [" + difficultyName + "] // " + beatmapCreator;
  }
}

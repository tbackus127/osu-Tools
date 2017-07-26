
package com.rath.osutools.core;

/**
 * This class acts as a struct for a map's relevant metadata.
 * 
 * @author Tim Backus tbackus127@gmail.com
 */
public class MapMetadata {
  
  /** The artist name of the song. */
  private final String artist;
  
  /** The title of the song. */
  private final String title;
  
  /** The difficulty of the beatmap. */
  private final MapMetadata[] difficulties;
  
  /**
   * Default constructor.
   * 
   * @param ar the artist name as a String.
   * @param ti the title of the song as a String.
   * @param df the map's difficulty name as a String.
   */
  public MapMetadata(final String ar, final String ti, final MapMetadata[] df) {
    this.artist = ar;
    this.title = ti;
    this.difficulties = df;
  }
  
  /**
   * Gets the artist's name.
   * 
   * @return the song's artist name as a String.
   */
  public String getArtist() {
    return artist;
  }
  
  /**
   * Gets the title of the song.
   * 
   * @return the song's title as a String.
   */
  public String getTitle() {
    return title;
  }
  
  /**
   * Gets an array of the difficulty names.
   * 
   * @return an array of Strings.
   */
  public MapMetadata[] getDifficulties() {
    return difficulties;
  }
}


package com.rath.osutools.core;

public class DifficultyMetadata {
  
  private final String creator;
  private final String difficulty;
  private final double hpDrain;
  private final double circleSize;
  private final double accuracy;
  private final double approachRate;
  
  public DifficultyMetadata(final String cr, final String df, final double hp, final double cs,
      final double acc, final double ar) {
    this.creator = cr;
    this.difficulty = df;
    this.hpDrain = hp;
    this.circleSize = cs;
    this.accuracy = acc;
    this.approachRate = ar;
  }
}


package com.rath.osutools.replrename;

public class ReplayData {
  
  private final String playerName;
  private final long winTicks;
  private final String hash;
  
  public ReplayData(final String n, final long t, final String h) {
    this.playerName = n;
    this.winTicks = t;
    this.hash = h;
  }
  
  public final String getPlayerName() {
    return playerName;
  }
  
  public final long getWinTicks() {
    return winTicks;
  }
  
  public String getHash() {
    return hash;
  }
}

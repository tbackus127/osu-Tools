
package com.rath.osutools;

import java.io.File;

import com.rath.osutools.replrename.ReplayRenamer;

/**
 * This class serves as a top-level wrapper for the ReplayRenamer class.
 * 
 * @author Tim Backus tbackus127@gmail.com
 *
 */
public class ReplayArchiveRenamer {
  
  /**
   * Main method.
   * 
   * @param args runtime arguments: args[0]: path to the osu!.db file, args[1]: path to the replay directory.
   */
  public static void main(String[] args) {
    
    // Make sure everything is good before renaming
    if (checkRuntimeParams(args)) {
      ReplayRenamer.renameReplays(args[0], args[1]);
    }
  }
  
  /**
   * Ensures the two runtime parameters (path to osu!.db file, path to replay directory) are valid.
   * 
   * @param args the runtime parameters, as a String[].
   * @return true if valid, false if not.
   */
  private static final boolean checkRuntimeParams(final String[] args) {
    
    // Check that we have two arguments
    if (args.length != 2) {
      printUsage();
      return false;
    }
    
    // Check that the osu!.db file exists, is readable, and is not a directory
    final File dbFile = new File(args[0]);
    if (!dbFile.exists()) {
      System.out.println("ERROR: The file \"" + dbFile.getAbsolutePath() + "\" does not exist.");
      return false;
    }
    if (dbFile.isDirectory()) {
      System.out.println(
          "ERROR: The file \"" + dbFile.getAbsolutePath() + "\" is a directory (file needed).");
      return false;
    }
    if (!dbFile.canRead()) {
      System.out.println("ERROR: The file \"" + dbFile.getAbsolutePath() + "\" is unreadable.");
      return false;
    }
    
    System.out.println("OK: Found osu!.db file.");
    
    // Check that the replays folder exists and is a directory
    final File replayDir = new File(args[1]);
    if (!replayDir.exists()) {
      System.out
          .println("ERROR: The directory \"" + replayDir.getAbsolutePath() + "\" does not exist.");
      return false;
    }
    if (!replayDir.isDirectory()) {
      System.out.println(
          "ERROR: The directory \"" + replayDir.getAbsolutePath() + "\" is not a directory.");
      return false;
    }
    
    // Check that the replays folder contains at least one .osr file
    final File[] replayFiles = replayDir.listFiles();
    if (replayFiles.length < 1) {
      System.out.println("ERROR: The replay directory is empty.");
      return false;
    }
    for (int i = 0; i < replayFiles.length; i++) {
      if (replayFiles[i].getName().endsWith(".osr")) {
        System.out.println("OK: Found replays.");
        return true;
      }
    }
    
    // If we get here, there are no .osr files
    System.out.println("ERROR: No valid replay files (.osr) exist in the replay directory.");
    return false;
  }
  
  /**
   * Prints the usage message to the console.
   */
  private static final void printUsage() {
    System.out.println(
        "Usage: \"java ReplayArchiveRenamer <PATH_TO_osu!.db_FILE> <PATH_TO_REPLAY_DIRECTORY>\"");
  }
  
}
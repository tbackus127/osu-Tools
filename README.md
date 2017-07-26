# osu-Tools
A collection of tools for osu!.

## How to Use

(To be decided)

## List of Tools

### Replay Archive Renamer (WIP)

##### What it Does - Short Version

This program renames the replays in your Data/r folder to a normal file name.

##### What it Does - Detailed Version

If you look in "<YOUR osu! DIRECTORY>/Data/r", you should see a bunch of .osr files. The .osr files are replay files of all the plays you've made since osu! was installed. But there's just one problem: you can't figure out anything about the replay without loading it in osu!. However, you may notice that if you load two replays that start with the same 32 characters, it's a replay of the same map.

This 32-character label is called a "hash", and it's actually just a hexidecimal (base-16) number. Whenever any sort of data is hashed, it produces a unique ID. In this case, each beatmap's data hashes to one of these long IDs. If we only have just the hash, it is impossible to revert it back into the data it once was. Luckily, the osu!.db file contains beatmap information, and included in that information are hash values for each beatmap in your osu! song collection.

This program searches your osu!.db file for every beatmap's title, artist, mapper, difficulty name, and hash. It takes this data and renames all of the .osr files to the standard format: "<Player Name> - <Artist> - <Title> [Difficulty] (Date) <Game Type>.osu". All you have to do is tell it where your osu!.db file is, and what folder your replays are in.

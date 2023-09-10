![](https://i.imgur.com/alAG9uW.png)

# Information

Originally started as a fork from [Quackster/Kepler](https://github.com/Quackster/Kepler), this is a server created in Java designed to revive Habbo Hotel v31 from the 2009 era and its inception was in early 2018 as a side project. Havana is the most complete v31+ server to date, this was undertaken by various reverse engineering efforts of the Shockwave client throughout the years to achieve this.

**Join the Classic Habbo discord!** https://discord.gg/GEYbVpW

Havana has been an independent project, almost entirely developed by [myself](https://github.com/Quackster) for 4 years straight. This project means a lot to me, and was always going to be released as open-source work. I am a firm believer in open-source and free software for everybody.

Sulake used the Adobe/Macromedia Shockwave as its multimedia platform for their game (Habbo Hotel) from 2001-2009. In the last year, Habbo made the move to the Adobe Flash client, and then in 2020 made the switch to the Unity engine, while still maintaining their flash client.

Nowadays, the Shockwave client cannot be played in modern browsers as they have removed NPAPI support due to deprecation, end of life support and therefore must be played on forks of browsers that still have the NPAPI enabled.

The reason why Shockwave emulators exist is for multiple reasons, the first is that Habbo uses a virtual currency called credits which is spent using real money and makes it a pay to win game, our own faithful recreation of Habbo can make credits free for everybody. The second is the fact that modern Habbo still lacks features that were once available to the users that played during the Shockwave-era - and is thus, to be intended to be used for **educational purposes only** as a preservation effort for an old game.

# Features

### Server

- Handshake
  - Login via SSO ticket
  - Login via username/password
  - Diffie-Hellman two-way client/server encryption (v28+)
- Games
  - Battleball
  - Snowstorm
  - Wobble Squabble
  - Tic Tac Toe (available in Cunningfox Gamehall)
  - Battle Ships (available in Cunningfox Gamehall)
  - Chess (available in Cunningfox Gamehall)
- Catalogue
  - Main/sub category page support
  - Catalogue pages
  - Catalogue items
  - Purchasing from catalogues
  - Effect previews
  - Pixel rental previews
  - Automatic rotation of collectibles
  - Redemption of vouchers
- Effects
  - Purchasing effects
  - Effect expiry
  - Configurable effect duration
- Navigator
  - Recommended rooms
  - List public rooms
  - Room categories
  - Favourite rooms
  - Room search (including filtering with owner:)
- Rooms
  - Create private rooms
  - Edit private room settings
  - Enter private rooms 
  - Private room doorbell
  - Private room ratings (default expiry over 30 days)
  - Enter public rooms
    - Public room furniture
    - Pool ladders (swimming)
    - Pool diving deck (diving)
    - Public room bots (your classics such as Piers the Habbo Kitchen chef!)
    - All Infobus support
  - Show tags on user 
- Items
  - Inventory 
  - Item purchasing
  - Sit on chairs
  - Lay on beds
  - Trophies
  - Coin redeeming
  - Dice rolling
  - Wheel of fortune
  - Love randomizer
  - Scoreboard
  - Totem head/leg/planet interaction to gain special totem effects
  - Vending machine interaction
  - Teleporters
  - American idol voting system
  - Rollers
  - Gates
  - One-way gates
  - Photos
  - Song disks
  - Presents
  - Room dimmers
- Trax Machine
  - Create music
  - Save music
  - Delete music
  - Burn disk
- Jukebox
  - Play disks
  - Queue multiple disks
- Camera
  - Take photos
  - Load photos
- Messenger
  - Status update
  - Send friend request
  - Accept friend request
  - Send instant message
  - Offline messaging
  - Follow friend
  - Invite friends
- Trading
  - All safe trading features enabled
- Events
  - Users can host events, is integrated into website
- Groups
  - Display user favourited group when in-game
- Achievements
  - American Idol voting
  - Time online
  - Change looks
  - Game played (BattleBall and SnowStorm)
  - Habbo Club membership
  - Happy Hour
  - Consecutive logins
  - Friend referrals
  - Motto
  - Account age
  - Respect earnt
  - Respect given
  - Room entries to private rooms that aren't yours
  - Completing the tutorial
  - Adding tags
  - Trade pass
  - Guides
 Habbo Club
  - Monthly gifts
  - First gift club sofa
  - Exclusive Habbo Club items
  - Exclusive Habbo Club rooms
  - Habbo club clothing options enabled
- Ecotron
  - Recycle items
  - Ecotron rewards visible in catalogue
  - Ecotron rewards after recycling items
- Guides
  - Complete tutorial
  - After tutorial, search for guide
  - Guide must be part of the guide group to join
  - Guide badge progression

### Website 

- Login
- Register
- Community
- Groups
- Group discussions
- User referrals
- Homes
- Home customisation
- Housekeeping 
  - News
  - Users
  - Room entry badges
  - Infobus management
  - Ban management

(There's a good chance I missed a lot, the CMS itself is very complete)

# Screenshots

![image](https://user-images.githubusercontent.com/1328523/188254181-edc73039-bef7-4dc8-af0b-46541cec9b4c.png)

![image](https://user-images.githubusercontent.com/1328523/188254211-c9f9bf21-4c60-444f-b3b6-a8713c72d9b0.png)

![image](https://user-images.githubusercontent.com/1328523/188254197-30a5b3d3-7854-403c-a863-508c2300a086.png)

![image](https://user-images.githubusercontent.com/1328523/188258240-185c1233-0178-4ace-a9af-b3a22077e32d.png)

# Download

Download the latest development build from the [releases page](https://github.com/Quackster/Havana/releases).

### Requirements

To be honest, this server doesn't require much. I'd argue that the MariaDB server is more resource demanding than the emulator itself. 

- JDK >= 17
- MariaDB server
- At minimum 4 GB of RAM (to be safe)

If you aim to use this for yourself, I recommend setting up your own 2009 figure image renderer with the project I've created [here](https://github.com/Quackster/Minerva) to render Habbo looks on the website.

# Installation

Install MariaDB server, connect to the database server and import havana.sql (located in /tools/havana.sql).

Download the latest development build from the [releases page](https://github.com/Quackster/Havana/releases) and rename the files to remove the short build hash version, for convenience. 

Install any JDK version that is equal or above >= 17 to run the jar files.

Run both Havana-Server.jar and Havana-Web.jar at least once to generate the necessary configuration files, configure the MySQL attributes to connect to the MariaDB server.

Download the [havana_www_10092023.7z](https://www.mediafire.com/file/jy8a96vleuzebe0/havana_www_10092023.7z/file) file, and then extract it to /tools/www/ this directory is located where you ran Havana-Web.jar. 

*(This is the default directory for static content within the Havana-Web project, but the directory where it looks for static images can be configured in the Housekeeping settings).*

Start Havana-Web via start_web.sh (Unix/Linux distros) or start_web.bat (Windows)

Start Havana-Server via start_server.sh (Unix/Linux distros) or start_server.bat (Windows)

Your server should be up and running and accessible via http://localhost/

I highly recommend [this browser](https://forum.ragezone.com/f353/portable-browser-with-flash-shockwave-1192727/) to be able to play Adobe Shockwave movies in the present day.

❗ Once registered as an admin, I high recommend running [groups.sql](https://github.com/Quackster/Havana/blob/master/tools/groups.sql) against your database, it will create the Habbo Guides, SnowStorm, BattleBall, Wobble Squabble and Lido Diving gaming groups for the website.

And then make yourself admin by setting your ``rank`` to 8 in the ``users`` table.

### Important for Linux users

Install the font manager, to enable the captcha to work on the website.

```
apt-get install font-manager
```

## License

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

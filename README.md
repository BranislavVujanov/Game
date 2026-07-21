# X-Terminator

X-TERMINATOR is a simple, fast-paced, and addictively fun 2D Java game, created in just one week for a game jam as part of an internship selection process. The theme of the jam was **"3 Rules."**

Given the tight deadline, the game was originally developed in a rush. However, I later decided to revisit the project, polish the code, and upgrade the game — this is the result of those efforts.

The jam encouraged participants to have fun with their creations, so I added a comedic twist: I "Arnoldized" all in-game text — transforming the dialogue to mimic how Arnold Schwarzenegger might pronounce it.

This was my first-ever game, and I'm proud to share it. I hope you enjoy playing it as much as I enjoyed making it!

## The three rules

1. To win, you must pass three rounds.
2. To pass a round, exterminate all the X's.
3. Each round lasts ten seconds.

## Controls

- **Arrow keys** — move
- **Up / Down** — navigate menus
- **Enter** — confirm menu selection

Movement uses simple acceleration/friction physics rather than fixed-step grid movement, so the player character builds up speed and rebounds off the screen edges.

## Levels

- **Round 1** — open arena, no obstacles.
- **Round 2** — adds a wall in the center of the arena.
- **Round 3** — adds a wall and a lightning tile; touching the lightning slows down all remaining NPCs.

## Tech stack

- Java 17
- Java Swing/AWT for rendering, input, and the game loop
- `javax.sound.sampled` for music and sound effects
- Built with Apache NetBeans (Ant project)

## Project structure

```
src/
├── main/
│   ├── Main.java              # entry point, window setup
│   ├── GamePanel.java         # game loop, state machine, rendering dispatch
│   ├── Keybord.java           # keyboard input handling
│   ├── UI.java                # HUD, menus, and screen text for every game state
│   ├── CollisionChecker.java  # player-NPC, player-wall, player-lightning collision
│   ├── NpcSetter.java         # initial NPC placement
│   └── Sound.java             # music/sound effect playback
├── entity/
│   ├── Entity.java            # abstract base for Player and Npc
│   ├── Player.java            # player movement physics
│   ├── Npc.java                # NPC ("X") wandering behavior
│   └── Direction.java
├── tile/
│   └── Tile.java               # wall/lightning tiles
├── image/                      # sprites and screens
├── sound/                      # music and sound effects
└── font/                       # custom "Schwarzenegger" font used for in-game text
```

## Building and running

Requires JDK 17+.

**From NetBeans:** open the project folder and run it directly (main class is `main.Main`).

**From the command line (Ant):**

```bash
ant clean jar
java -jar dist/X-Terminator2D.jar
```

## Known limitations

This was built fast, for a jam, then cleaned up afterward — a few rough edges are still there on purpose rather than by oversight:

- Asset-loading failures (missing image/sound/font files) are logged to the console rather than shown to the player.
- Some values (screen positions for HUD text, timing thresholds) are hardcoded rather than derived from constants.

## Author

Branislav Vujanov

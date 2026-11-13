# Rose Runner

A Rose-Hulman themed 2D platformer made for the CSSE220 final project.
Run, jump, and ride moving platforms across six real campus locations,
collecting every Rose Point to clear each level as fast as you can.

## How to launch

You need **JDK 21 or newer**. Maven downloads JavaFX 21 for you.

**In IntelliJ:**
1. Open this folder as a Maven project.
2. Open the Maven panel (right side) and go to **Plugins > javafx > javafx:run**.
3. Double-click **javafx:run**. The game window opens.

**From a terminal** (with Maven installed):

```
mvn javafx:run
```

The main class is `app.RoseRunnerApp`.

## How to play

| Action | Keys |
|---|---|
| Move left / right | Left / Right arrows, or A / D |
| Jump | Space, W, or Up arrow |

1. Type your name on the welcome screen and press **Start** (or Enter).
2. Pick an unlocked level. Beating a level unlocks the next one.
3. Collect every Rose Point (spinning coin) to finish the level. The HUD shows the level, Rose Points collected, and time.
4. If you fall off the bottom, you go back to the start and all the Rose Points come back.
5. After the last level, the results screen shows your total time. Press **Play Again** to go again.

## Levels

| # | Level | What is new |
|---|---|---|
| 1 | Tutorial | Walking and small hops |
| 2 | Moench Hall | A staircase climb |
| 3 | Mussallem Union | Zig-zag jumps over a pit |
| 4 | White Chapel | First moving platform, over a safe floor |
| 5 | Speed Lake | Elevators and pits |
| 6 | Logan Library | Fast moving platforms, tiny ledges, and finals-week jokes |

Levels live in `src/main/resources/levels.txt`. The format is explained at the top of that file
(`LEVEL`, `SPAWN`, `PLATFORM`, `POINT`, `MOVING`, `LORE`).

## Project structure

- `app` : screens and screen switching (`SceneManager`, `WelcomeView`, `LevelSelectView`, `GameScene`, `EndView`)
- `entities` : game objects (`Player`, `Platform`, `MovingPlatform`, `Coin`, `SpriteAnimation`)
- `level` : level data and loading (`Level`, `LevelLoader`, `MovingPlatformLoader`, `LevelLore`, `LevelTimer`)
- `behaviors` : the `Collidable` interface
- UML: `Final Project UML.puml`

## Image credits

- Level and screen backgrounds (`Tutorial.png`, `Moench.png`, `Union.png`, `Chapel.png`, `SpeedLake.png`,
  `LoganLibrary.png`, `Welcome.png`, `End.png`): screenshots from the Rose-Hulman Institute of Technology
  virtual campus tour and website (rose-hulman.edu), used for this class project only.
- Player sprites (`images/player/`): TODO add source
- Coin sprites (`images/collectable/coin/`): TODO add source

## Final commit

Final commit: TODO fill in after the final pull request is merged into main.

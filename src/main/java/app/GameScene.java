package app;

import java.util.ArrayList;
import java.util.List;

import entities.Coin; // COIN HOOK
import entities.MovingPlatform; // MOVING PLATFORM HOOK
import entities.Platform;
import entities.Player;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label; // LORE HOOK
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import level.LevelTimer;
import level.Level;
import level.MovingPlatformLoader; // MOVING PLATFORM HOOK
import level.LevelLore; // LORE HOOK

public class GameScene {
    private final Pane root = new Pane();
    private final SceneManager manager;
    private final Level level;
    private final double h;

    private final Player player;
    private final List<Platform> platforms = new ArrayList<>();
    private final List<Coin> rosePoints = new ArrayList<>();
    private final List<MovingPlatform> movingPlatforms; // MOVING PLATFORM HOOK: also inside platforms, so the player lands on them
    private final LevelTimer timer = new LevelTimer();
    private final AnimationTimer gameLoop;

    // Fixed-rate game loop: update() runs 60 times a second on any screen
    private static final long NANOS_PER_UPDATE = 1_000_000_000L / 60; // MOVEMENT HOOK: one update = 1/60 of a second
    private static final int MAX_UPDATES_PER_FRAME = 5;               // MOVEMENT HOOK: catch-up limit after lag
    private long lastUpdateTime = 0;                                  // MOVEMENT HOOK: time of last update (0 = not started)
    private boolean levelFinished = false;                            // MOVEMENT HOOK: set by finishLevel()

    // LORE HOOK: a text box at the top that shows the level's story, then hides
    private static final int LORE_UPDATES = 600;      // LORE HOOK: 10 seconds at 60 updates a second
    private static final int FALL_JOKE_UPDATES = 150; // LORE HOOK: 2.5 seconds
    private final Label loreLabel = new Label();      // LORE HOOK
    private int loreUpdatesLeft = LORE_UPDATES;       // LORE HOOK: counts down to 0, then the box hides
    private boolean hasLore;                          // LORE HOOK: only levels with a LORE line show any text

    // Which movement keys are held down right now
    private boolean leftHeld = false;
    private boolean rightHeld = false;

    public GameScene(SceneManager manager, Level level, double w, double h) {
        this.manager = manager;
        this.level = level;
        this.h = h;

        root.getChildren().add(Assets.background(level.getFilePath(), w, h));

        // Platforms are Platform entities so the Player can land on them
        // TODO: Platform is red; change its color in Platform.java if we want Rose maroon again
        for (Rectangle2D p : level.getPlatforms()) {
            Platform platform = new Platform(p.getMinX(), p.getMinY(), p.getWidth(), p.getHeight());
            platforms.add(platform);
            root.getChildren().add(platform.getView());
        }
        // MOVING PLATFORM HOOK: read this level's MOVING lines (same file SceneManager loads)
        movingPlatforms = MovingPlatformLoader.loadForLevel("/levels.txt", level.getId());
        for (MovingPlatform mover : movingPlatforms) { // MOVING PLATFORM HOOK
            platforms.add(mover);                      // MOVING PLATFORM HOOK
            root.getChildren().add(mover.getView());   // MOVING PLATFORM HOOK
        }
        for (Point2D pt : level.getPoints()) {
            Coin coin = new Coin(pt.getX(), pt.getY()); // COIN HOOK: centered on the point
            rosePoints.add(coin);                        // COIN HOOK
            root.getChildren().add(coin.getView());      // COIN HOOK
        }

        // PLAYER HOOK: add the player at the level's spawn point and listen for keys
        Point2D spawn = this.level.getSpawn();
        player = new Player(manager.getPlayerName(), spawn.getX(), spawn.getY());
        root.getChildren().add(player.getView());
        root.setOnKeyPressed(this::handleKeyPressed);
        root.setOnKeyReleased(this::handleKeyReleased);

        // LORE HOOK: show the level name (underscores as spaces) and its story at the top
        String lore = LevelLore.loadForLevel("/levels.txt", level.getId());
        loreLabel.setText(level.getName().replace('_', ' ') + "\n" + lore);
        loreLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-padding: 10;"
                + " -fx-background-color: rgba(0,0,0,0.65); -fx-background-radius: 8;");
        loreLabel.setWrapText(true);
        loreLabel.setMaxWidth(w - 40);
        loreLabel.setLayoutX(20);
        loreLabel.setLayoutY(20);
        root.getChildren().add(loreLabel);
        hasLore = !lore.isEmpty();                    // LORE HOOK: no LORE line = no text box at all
        if (!hasLore) {
            loreLabel.setVisible(false);
            loreUpdatesLeft = 0;
        }

        // TODO: HUD here (player name, Rose Points collected, timer.getCurrentTime())

        // Game loop: JavaFX calls handle() once per screen refresh (60, 120, 144... times a second) // MOVEMENT HOOK
        // TODO: replace with the team's shared game loop if someone builds one
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdateTime == 0) {          // MOVEMENT HOOK: first frame, just start the clock
                    lastUpdateTime = now;
                    return;
                }
                int updates = 0;                    // MOVEMENT HOOK: run one update per full 1/60 second passed
                while (now - lastUpdateTime >= NANOS_PER_UPDATE
                        && updates < MAX_UPDATES_PER_FRAME
                        && !levelFinished) {
                    update();
                    lastUpdateTime += NANOS_PER_UPDATE; // MOVEMENT HOOK: move forward exactly one tick
                    updates++;
                }
                if (now - lastUpdateTime >= NANOS_PER_UPDATE) {
                    lastUpdateTime = now;           // MOVEMENT HOOK: still behind after 5, skip the rest of the lag
                }
            }
        };
        gameLoop.start();
        timer.start();
    }

    /** One frame: move the player, collect points, check for falling and finishing. */
    private void update() {
        if (leftHeld && !rightHeld) {
            player.moveLeft();
        } else if (rightHeld && !leftHeld) {
            player.moveRight();
        } else {
            player.stopMoving();
        }

        player.update(this.platforms);

        // MOVING PLATFORM HOOK: slide each moving platform and carry the player if riding it
        for (MovingPlatform mover : movingPlatforms) {
            mover.update(player);
        }

        // Collect any Rose Point the player touches
        // COIN HOOK: spin each coin that is still showing, and collect it if touched
        for (Coin coin : this.rosePoints) {
            if (coin.isVisible()) {
                coin.update();
                if (player.isColliding(coin)) {
                    coin.setVisible(false);
                    player.collectPoint();
                }
            }
        }

        // Fell off the bottom: back to the spawn point (the clock keeps running)
        if (player.getY() > h) {
            Point2D spawn = this.level.getSpawn();
            player.resetForLevel(spawn.getX(), spawn.getY()); // RESPAWN HOOK: back to spawn with 0 points
            for (Coin coin : this.rosePoints) {               // RESPAWN HOOK: every Rose Point comes back
                coin.setVisible(true);
            }
            if (hasLore) {                                    // LORE HOOK: a joke when you fall (library only)
                loreLabel.setText(LevelLore.randomFallJoke());
                loreLabel.setVisible(true);
                loreUpdatesLeft = FALL_JOKE_UPDATES;
            }
        }

        // LORE HOOK: count down and hide the text box when time is up
        if (loreUpdatesLeft > 0) {
            loreUpdatesLeft--;
            if (loreUpdatesLeft == 0) {
                loreLabel.setVisible(false);
            }
        }

        // Level is done once every Rose Point is collected
        // TODO: Moench and Union have no Rose Points yet in Levels.java, so they can't be finished
        if (player.getPointsCollected() == this.level.getPoints().size()) {
            finishLevel();
        }
    }

    private void finishLevel() {
        levelFinished = true; // MOVEMENT HOOK: stop any catch-up updates in this frame
        gameLoop.stop();
        double seconds = timer.getCurrentTime();  // read before stop(), which resets it to 0
        player.finishLevel(this.level.getId(), timer);
        timer.stop();
        manager.completeLevel(this.level.getId(), seconds);
    }

    private void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT, A -> leftHeld = true;
            case RIGHT, D -> rightHeld = true;
            case UP, W, SPACE -> player.jump();
            default -> { }
        }
    }

    private void handleKeyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT, A -> leftHeld = false;
            case RIGHT, D -> rightHeld = false;
            default -> { }
        }
    }

    public Pane getRoot() { return root; }
}

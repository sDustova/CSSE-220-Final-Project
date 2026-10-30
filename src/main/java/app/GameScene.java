package app;

import java.util.ArrayList;
import java.util.List;

import entities.Platform;
import entities.Player;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import level.LevelTimer;

public class GameScene {
    private final Pane root = new Pane();
    private final SceneManager manager;
    private final int levelIndex;
    private final Level level;
    private final double h;

    private final List<Platform> platforms = new ArrayList<>();
    private final List<Circle> rosePoints = new ArrayList<>(); // TODO: swap Circles for Coin (or RosePoint) entities once Coin has an image
    private final Player player;
    private final LevelTimer timer = new LevelTimer();
    private final AnimationTimer gameLoop;

    // Which movement keys are held down right now
    private boolean leftHeld = false;
    private boolean rightHeld = false;

    public GameScene(SceneManager manager, int levelIndex, double w, double h) {
        this.manager = manager;
        this.levelIndex = levelIndex;
        this.level = Levels.ALL.get(levelIndex);
        this.h = h;

        root.getChildren().add(Assets.background(level.backgroundFile(), w, h));

        // Platforms are Platform entities so the Player can land on them
        // TODO: Platform is red; change its color in Platform.java if we want Rose maroon again
        for (Rectangle2D p : level.platforms()) {
            Platform platform = new Platform(p.getMinX(), p.getMinY(), p.getWidth(), p.getHeight());
            platforms.add(platform);
            root.getChildren().add(platform.getView());
        }
        for (Point2D pt : level.rosePoints()) {
            Circle point = new Circle(pt.getX(), pt.getY(), 10, Color.GOLD);
            rosePoints.add(point);
            root.getChildren().add(point);
        }

        // PLAYER HOOK: add the player at the level's spawn point and listen for keys
        player = new Player(manager.getPlayerName(), level.spawn().getX(), level.spawn().getY());
        root.getChildren().add(player.getView());
        root.setOnKeyPressed(this::handleKeyPressed);
        root.setOnKeyReleased(this::handleKeyReleased);

        // TODO: HUD here (player name, Rose Points collected, timer.getCurrentTime())

        // Game loop: JavaFX calls handle() about 60 times a second
        // TODO: replace with the team's shared game loop if someone builds one
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
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

        player.update(platforms);

        // Collect any Rose Point the player touches
        for (Circle point : rosePoints) {
            if (point.isVisible() && player.getBounds().intersects(point.getBoundsInParent())) {
                point.setVisible(false);
                player.collectPoint();
            }
        }

        // Fell off the bottom: back to the spawn point (the clock keeps running)
        if (player.getY() > h) {
            player.moveTo(level.spawn().getX(), level.spawn().getY());
        }

        // Level is done once every Rose Point is collected
        // TODO: Moench and Union have no Rose Points yet in Levels.java, so they can't be finished
        if (!rosePoints.isEmpty() && player.getPointsCollected() == rosePoints.size()) {
            finishLevel();
        }
    }

    private void finishLevel() {
        gameLoop.stop();
        double seconds = timer.getCurrentTime();  // read before stop(), which resets it to 0
        player.finishLevel(levelIndex, timer);
        timer.stop();
        manager.completeLevel(levelIndex, seconds);
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

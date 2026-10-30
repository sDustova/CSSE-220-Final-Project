package scenes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleConsumer;

import entities.Platform;
import entities.Player;
import entities.RosePoint;
import game.Timer;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import level.Level;

/**
 * The screen where the level is played. Runs its own game loop.
 * Arrow keys or A/D move, Up/W/Space jumps.
 */
public class GameScene {
    public static final double WIDTH = 800;
    public static final double HEIGHT = 500;
    private static final double START_X = 40;
    private static final double START_Y = 400;

    private final Scene scene;
    private final Pane world = new Pane();
    private final Player player;
    private final Level level;
    private final int levelNumber;
    private final Timer timer = new Timer();
    private final DoubleConsumer onFinish; // gets the finish time in seconds
    private final AnimationTimer gameLoop;

    // TODO: these belong in Level (loadLevel / requiredPoints) once Evan expands it
    private final List<Platform> platforms = new ArrayList<>();
    private final List<RosePoint> rosePoints = new ArrayList<>();
    private int requiredPoints;

    // HUD (heads-up display) labels in the top-left corner
    private final Label nameLabel = new Label();
    private final Label pointsLabel = new Label();
    private final Label timeLabel = new Label();

    // Which movement keys are held down right now
    private boolean leftHeld = false;
    private boolean rightHeld = false;

    public GameScene(Player player, int levelNumber, DoubleConsumer onFinish) {
        this.player = player;
        this.levelNumber = levelNumber;
        this.onFinish = onFinish;
        this.level = new Level(levelNumber); // TODO: use level.loadLevel() once it exists

        buildLayout();
        player.resetForLevel(START_X, START_Y);
        world.getChildren().add(player.getView());

        VBox hud = new VBox(4, nameLabel, pointsLabel, timeLabel);
        hud.setLayoutX(10);
        hud.setLayoutY(10);
        world.getChildren().add(hud);
        world.setStyle("-fx-background-color: lightblue;");

        scene = new Scene(world, WIDTH, HEIGHT);
        scene.setOnKeyPressed(this::handleKeyPressed);
        scene.setOnKeyReleased(this::handleKeyReleased);

        // The game loop: JavaFX calls handle() once per screen refresh
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame();
            }
        };
        displayHUD();
    }

    /** Starts the clock and the game loop. Call after the scene is on screen. */
    public void start() {
        timer.reset();
        timer.start();
        gameLoop.start();
    }

    /** One frame of the game: move, collect points, check for a win, redraw the HUD. */
    public void updateGame() {
        if (leftHeld && !rightHeld) {
            player.moveLeft();
        } else if (rightHeld && !leftHeld) {
            player.moveRight();
        } else {
            player.stopMoving();
        }

        player.update(platforms);

        for (RosePoint point : rosePoints) {
            if (!point.isCollected() && player.isColliding(point)) {
                player.collectPoint(point);
            }
        }

        // Fell off the bottom: back to the start (the clock keeps running)
        if (player.getY() > HEIGHT) {
            player.moveTo(START_X, START_Y);
        }

        // TODO: replace with level.checkCompletion() once Level has requiredPoints
        if (player.getPointsCollected() >= requiredPoints) {
            finishLevel();
            return;
        }

        displayHUD();
    }

    /** Updates the HUD text with the latest name, points, and time. */
    public void displayHUD() {
        nameLabel.setText("Player: " + player.getName());
        pointsLabel.setText("Rose Points: " + player.getPointsCollected() + " / " + requiredPoints);
        timeLabel.setText(String.format("Time: %.2f s", timer.getCurrentTime()));
    }

    private void finishLevel() {
        gameLoop.stop();
        timer.stop();
        player.finishLevel(levelNumber, timer);
        onFinish.accept(timer.getCurrentTime());
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

    /**
     * Places the platforms and Rose Points for this level.
     * Platforms are {x, y, width, height}. Points are {centerX, centerY}.
     * TODO: move into Level.loadLevel() once Evan expands Level.
     */
    private void buildLayout() {
        double[][] platformData;
        double[][] pointData;

        if (levelNumber == 2) {
            platformData = new double[][] {
                {0, 460, 250, 40}, {550, 460, 250, 40}, // ground with a gap
                {300, 380, 100, 20}, {450, 300, 100, 20},
                {250, 220, 100, 20}, {450, 140, 120, 20}
            };
            pointData = new double[][] {
                {350, 350}, {500, 270}, {300, 190}, {510, 110}, {700, 430}
            };
        } else {
            platformData = new double[][] {
                {0, 460, 800, 40}, // ground
                {150, 370, 120, 20}, {330, 290, 120, 20}, {520, 220, 120, 20}
            };
            pointData = new double[][] {
                {210, 340}, {390, 260}, {580, 190}, {700, 430}
            };
        }

        for (double[] p : platformData) {
            Platform platform = new Platform(p[0], p[1], p[2], p[3]);
            platforms.add(platform);
            world.getChildren().add(platform.getView());
        }
        for (double[] p : pointData) {
            RosePoint point = new RosePoint(p[0], p[1]);
            rosePoints.add(point);
            world.getChildren().add(point.getView());
        }
        requiredPoints = rosePoints.size(); // collect them all to finish
    }

    public Scene getScene() {return scene;}

    public Level getLevel() {return level;}
}

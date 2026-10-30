package entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import behaviors.Collidable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import level.LevelTimer;

/**
 * The player character. Handles left/right movement, jumping, gravity,
 * and landing on platforms.
 *
 * Speeds are in pixels per frame. The game loop should call update()
 * once every frame (about 60 times a second with an AnimationTimer).
 */
public class Player implements Collidable {
    private static final double WIDTH = 30;
    private static final double HEIGHT = 50;
    private static final double MOVE_SPEED = 4;      // sideways speed
    private static final double JUMP_SPEED = 12;     // upward speed at the start of a jump
    private static final double GRAVITY = 0.6;       // added to downward speed each frame
    private static final double MAX_FALL_SPEED = 15; // so falling never gets too fast

    private final Group view;
    private final Rectangle body;
    private String name;
    private final Map<Integer, Double> fastestTimes = new HashMap<>(); // level number -> best time
    private int pointsCollected = 0;

    // Position is the top-left corner of the player
    private double x;
    private double y;
    private double velocityX = 0;
    private double velocityY = 0; // positive means moving down (JavaFX y grows downward)
    private boolean onGround = false;

    public Player(String name, double startX, double startY) {
        this.name = name;
        this.x = startX;
        this.y = startY;

        body = new Rectangle(x, y, WIDTH, HEIGHT);
        body.setFill(Color.MAROON);
        body.setStroke(Color.BLACK);
        body.setArcHeight(5);
        body.setArcWidth(5);

        view = new Group(body);
    }

    // ----- Controls (call these from key handlers) -----

    public void moveLeft() {
        velocityX = -MOVE_SPEED;
    }

    public void moveRight() {
        velocityX = MOVE_SPEED;
    }

    /** Call when the left/right key is released. */
    public void stopMoving() {
        velocityX = 0;
    }

    /** Only jumps when standing on something, so no double jumps. */
    public void jump() {
        if (onGround) {
            velocityY = -JUMP_SPEED;
            onGround = false;
        }
    }

    // ----- Called once per frame by the game loop -----

    /**
     * Applies gravity, moves the player, and lands on any platform
     * the player falls onto.
     */
    public void update(List<Platform> platforms) {
        double oldBottom = y + HEIGHT;

        // Gravity speeds up the fall a little every frame
        velocityY = Math.min(velocityY + GRAVITY, MAX_FALL_SPEED);

        x += velocityX;
        y += velocityY;

        onGround = false;
        for (Platform platform : platforms) {
            landOnPlatform(platform, oldBottom);
        }

        // Move the rectangle on screen to match the new position
        body.setX(x);
        body.setY(y);
    }

    /**
     * Stops the fall if the player was above this platform last frame
     * and has now reached or passed its top edge.
     */
    private void landOnPlatform(Platform platform, double oldBottom) {
        Bounds box = platform.getBounds();

        boolean fallingDown = velocityY >= 0;
        boolean overlapsSideways = x + WIDTH > box.getMinX() && x < box.getMaxX();
        boolean wasAbove = oldBottom <= box.getMinY();
        boolean reachedTop = y + HEIGHT >= box.getMinY();

        if (fallingDown && overlapsSideways && wasAbove && reachedTop) {
            y = box.getMinY() - HEIGHT; // stand right on top
            velocityY = 0;
            onGround = true;
        }
    }

    /** Puts the player at (newX, newY) standing still. Used for respawning. */
    public void moveTo(double newX, double newY) {
        x = newX;
        y = newY;
        velocityX = 0;
        velocityY = 0;
        onGround = false;
        body.setX(x);
        body.setY(y);
    }

    /** Gets the player ready for a new level: back to the start with 0 points. */
    public void resetForLevel(double startX, double startY) {
        moveTo(startX, startY);
        pointsCollected = 0;
    }

    // ----- Points and times -----

    /** Call when the player touches a Rose Point (check with isColliding). */
    public void collectPoint() {
        pointsCollected++;
    }

    /** Saves the level timer's time as this player's time for the level. */
    public void finishLevel(int levelNumber, LevelTimer timer) {
        recordTime(levelNumber, timer.getCurrentTime());
    }

    /** Saves the time only if it beats the old best for that level. */
    public void recordTime(int levelNumber, double seconds) {
        Double best = fastestTimes.get(levelNumber);
        if (best == null || seconds < best) {
            fastestTimes.put(levelNumber, seconds);
        }
    }

    /** Returns the best time for a level, or null if it was never finished. */
    public Double getFastestTime(int levelNumber) {
        return fastestTimes.get(levelNumber);
    }

    // ----- Getters -----

    public Group getView() {return view;}

    @Override
    public Bounds getBounds() {return view.getBoundsInParent();}

    /** True if this player's box overlaps the other object's box. */
    @Override
    public boolean isColliding(Collidable other) {
        return getBounds().intersects(other.getBounds());
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public int getPointsCollected() {return pointsCollected;}

    public double getX() {return x;}

    public double getY() {return y;}

    public boolean isOnGround() {return onGround;}
}

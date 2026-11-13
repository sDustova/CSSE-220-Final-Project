package entities;

import javafx.geometry.Bounds;
import javafx.scene.Node;

/**
 * A platform that slides back and forth between two spots.
 * It starts with its top-left corner at (x, y), travels (pathX, pathY)
 * pixels away, then turns around and comes back, forever.
 *
 * It is still a Platform, so Player.update() lands on it like any other.
 * Call update(player) once per game update to move it and carry the
 * player along if they are standing on it.
 */
public class MovingPlatform extends Platform {
    private static final double LAND_GAP = 0.01; // pixels; too small to see
    private final double pathX;      // how far it travels right (negative = left)
    private final double pathY;      // how far it travels down (negative = up)
    private final double pathLength; // straight-line length of the trip
    private final double speed;      // pixels moved per game update

    private final Node shape;        // the rectangle inside the Platform's Group
    private double distance = 0;     // how far along the trip it is right now
    private int direction = 1;       // 1 = going out, -1 = coming back

    public MovingPlatform(double x, double y, double width, double height,
                          double pathX, double pathY, double speed) {
        super(x, y, width, height);
        this.pathX = pathX;
        this.pathY = pathY;
        this.pathLength = Math.hypot(pathX, pathY);
        this.speed = speed;
        // Platform builds one Rectangle inside its Group. We slide that
        // rectangle with a translate so Platform.java does not need to change.
        this.shape = getView().getChildren().get(0);
    }

    /** Moves one step along the path and carries the player if needed. */
    public void update(Player player) {
        if (pathLength == 0) return;

        Bounds feet = player.getBounds();
        double oldTop = getBounds().getMinY();
        boolean overlapsSideways = feet.getMaxX() > getBounds().getMinX()
                && feet.getMinX() < getBounds().getMaxX();
        // Standing on this platform = on the ground with feet exactly on our top
        boolean riding = player.isOnGround() && overlapsSideways
                && Math.abs(feet.getMaxY() - oldTop) < 0.5;

        // Step along the path; turn around at either end
        double oldX = shape.getTranslateX();
        double oldY = shape.getTranslateY();
        distance += speed * direction;
        if (distance >= pathLength) {
            distance = pathLength;
            direction = -1;
        } else if (distance <= 0) {
            distance = 0;
            direction = 1;
        }
        shape.setTranslateX(pathX * distance / pathLength);
        shape.setTranslateY(pathY * distance / pathLength);
        double moveX = shape.getTranslateX() - oldX;
        double moveY = shape.getTranslateY() - oldY;
        double newTop = getBounds().getMinY();

        // Where the rider's feet go: a hair above the new top. Decimal rounding can
        // leave the feet a tiny fraction below the top, and then Player decides
        // it did not land. The small gap makes the landing check pass every time.
        double feetTarget = newTop - LAND_GAP;

        if (riding) {
            // Carry the rider sideways with the platform and set their feet on its top
            player.carry(moveX, feetTarget - feet.getMaxY());
        } else if (overlapsSideways && feet.getMaxY() <= oldTop && feet.getMaxY() > newTop) {
            // The platform rose up past the falling player's feet this step.
            // Put the feet back on top so the player lands instead of falling through.
            player.carry(0, feetTarget - feet.getMaxY());
        }
    }
}

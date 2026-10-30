package entities;

import behaviors.Collidable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * A collectible point. Disappears once the player collects it.
 */
public class RosePoint implements Collidable {
    private static final double RADIUS = 12;

    private final Group view;
    private boolean collected = false;

    /** (x, y) is the center of the point. */
    public RosePoint(double x, double y) {
        Circle circle = new Circle(x, y, RADIUS);
        circle.setFill(Color.CRIMSON);
        circle.setStroke(Color.GOLD);
        circle.setStrokeWidth(2);

        view = new Group(circle);
    }

    /** Marks this point as collected and hides it. */
    public void collect() {
        collected = true;
        view.setVisible(false);
    }

    public boolean isCollected() {return collected;}

    public Group getView() {return view;}

    @Override
    public Bounds getBounds() {return view.getBoundsInParent();}

    @Override
    public boolean isColliding(Collidable other) {
        return getBounds().intersects(other.getBounds());
    }
}

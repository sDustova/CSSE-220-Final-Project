package app;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GameScene {
    private final Pane root = new Pane();

    public GameScene(Level level, double w, double h) {
        root.getChildren().add(Assets.background(level.backgroundFile(), w, h));

        for (Rectangle2D p : level.platforms()) {
            Rectangle r = new Rectangle(p.getMinX(), p.getMinY(), p.getWidth(), p.getHeight());
            r.setFill(Color.web("#800000", 0.85));   // Rose maroon, visible on a photo
            root.getChildren().add(r);
        }
        for (Point2D pt : level.rosePoints()) {
            root.getChildren().add(new Circle(pt.getX(), pt.getY(), 10, Color.GOLD));
        }
        // later: add Player, Hud, AnimationTimer loop here
    }

    public Pane getRoot() { return root; }
}
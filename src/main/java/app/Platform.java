package app;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class Platform {
    private final Group view;
    private final Rotate turn = new Rotate(0, 30, 10);
    private Color color = Color.RED;

    public Platform() {
        Rectangle rect = new Rectangle(0, 0, 100, 20);
        rect.setFill(color);
        rect.setStroke(Color.BLACK);
        rect.setArcHeight(5);
        rect.setArcWidth(5);

        view = new Group(rect);
    }
    public Platform(double x, double y) {
        Rectangle rect = new Rectangle(x, y, 100, 20);
        rect.setFill(color);
        rect.setStroke(Color.BLACK);
        rect.setArcHeight(5);
        rect.setArcWidth(5);

        view = new Group(rect);
    }
    public Platform(double x, double y, double width, double height) {
        Rectangle rect = new Rectangle(x, y, width, height);
        rect.setFill(color);
        rect.setStroke(Color.BLACK);
        rect.setArcHeight(5);
        rect.setArcWidth(5);

        view = new Group(rect);
    }

    public Group getView() {return view;}
}

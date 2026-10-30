package app;

import java.util.List;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

public record Level(String name, String backgroundFile,
                    Point2D spawn,
                    List<Rectangle2D> platforms,
                    List<Point2D> rosePoints) {}
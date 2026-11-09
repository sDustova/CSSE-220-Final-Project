package level;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;

// TODO: team decide which Level to keep. The game currently uses app.Level (in Levels.java), not this one.
public record Level(int id,
                    String name,
                    String filePath,
                    Point2D spawn,
                    ArrayList<Rectangle2D> platforms,
                    ArrayList<Point2D> points) {}

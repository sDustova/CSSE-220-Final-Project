package level;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

public class Level {
    private String name;
    private String filePath;

    private Point2D spawn;
    private List<Rectangle2D> platforms;
    private List<Point2D> points;

    public Level(String name, String filePath) {
        this.name = name;
        this.filePath = filePath;

        this.spawn = null;
        this.platforms = new ArrayList<>();
        this.points = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public Point2D getSpawn() {
        return this.spawn;
    }

    public List<Rectangle2D> getPlatforms() {
        return platforms;
    }

    public List<Point2D> getPoints() {
        return points;
    }

    // Add spawn, platforms, and points

    public void addPoint(Point2D point) {
        this.points.add(point);
    }

    public void addPlatform(Rectangle2D platform) {
        this.platforms.add(platform);
    }

    public void setSpawn(Point2D spawn) {
        if (this.spawn == null) this.spawn = spawn;
    }
}
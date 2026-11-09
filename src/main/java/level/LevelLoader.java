package level;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LevelLoader {
    private final String levelFileLocation;
    private ArrayList<Level> levels;

    public LevelLoader(String levelFileLocation) {
        this.levelFileLocation = levelFileLocation;
        this.levels = new ArrayList<>();
    }

    public void loadLevels() {
        if (!this.levels.isEmpty()) return;

        ArrayList<String[]> parsedLines = this.parseLines();

        for (String[] line : parsedLines) {
            System.out.println(Arrays.toString(line));
        }
    }

    private ArrayList<String[]> parseLines() {
        InputStream in = LevelLoader.class.getResourceAsStream(this.levelFileLocation);   // import java.io.InputStream
        if (in == null) {
            throw new IllegalArgumentException("Cannot find " + levelFileLocation);
        }
        ArrayList<String[]> lines = new ArrayList<>();
        Scanner scanner = new Scanner(in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("#") || line.startsWith(" ")) continue;

            String[] parsedLine = line.split(" ");
            if (parsedLine.length <= 1) continue;
            lines.add(parsedLine);
        }
        scanner.close();
        return lines;
    }

    private Level createLevels(String[] parsedLevel) {
        String name = "";
        String filePath = "";
        Point2D spawn = new Point2D(0, 0);
        ArrayList<Rectangle2D> platforms = new ArrayList<>();
        ArrayList<Point2D> points = new ArrayList<>();

        return new Level(0, name, filePath, spawn, platforms, points);
    }

    public static void main(String[] args) {
        LevelLoader l = new LevelLoader("/levels.txt");

        l.loadLevels();
    }
}

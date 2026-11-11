package level;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class LevelLoader {
    private final String levelFileLocation;
    private ArrayList<Level> levels;

    public LevelLoader(String levelFileLocation) {
        this.levelFileLocation = levelFileLocation;
        this.levels = new ArrayList<>();
    }

    public ArrayList<Level> getLevels() {
        return this.levels;
    }

    public Level getLevel(int index) {
        return this.levels.get(index);
    }

    public String getLevelName(int index) {
        return this.levels.get(index).getName();
    }

    public void loadLevels() {
        if (!this.levels.isEmpty()) return;

        ArrayList<String[]> parsedLines = this.parseLines();
        this.createLevels(parsedLines);
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

    private void createLevels(ArrayList<String[]> parsedLines) {
        int currIndex = -1;

        for (String[] line : parsedLines) {
            switch (line[0]) {
                case "LEVEL" -> {
                    currIndex++;
                    this.levels.add(new Level(currIndex, line[1], line[2]));
                } case "SPAWN"-> {
                    int x = Integer.parseInt(line[1]);
                    int y = Integer.parseInt(line[2]);
                    this.levels.get(currIndex).setSpawn(new Point2D(x, y));
                } case "PLATFORM" -> {
                    int x = Integer.parseInt(line[1]);
                    int y = Integer.parseInt(line[2]);
                    int width = Integer.parseInt(line[3]);
                    int height = Integer.parseInt(line[4]);
                    this.levels.get(currIndex).addPlatform(new Rectangle2D(x, y, width, height));
                } case "POINT" -> {
                    int x = Integer.parseInt(line[1]);
                    int y = Integer.parseInt(line[2]);
                    this.levels.get(currIndex).addPoint(new Point2D(x, y));
                }
            }
        }
    }
}

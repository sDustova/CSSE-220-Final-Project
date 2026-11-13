package level;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entities.MovingPlatform;

/**
 * Reads the MOVING lines from the levels file.
 *
 * LevelLoader skips lines it does not know, so MOVING lines can live in
 * levels.txt without changing LevelLoader. This class reads the same file
 * with the same rules and counts LEVEL lines to know which level each
 * MOVING line belongs to (first level = 0, same as Level.getId()).
 *
 * Line format: MOVING x y width height pathX pathY speed
 */
public final class MovingPlatformLoader {
    private MovingPlatformLoader() {}

    /** Returns the moving platforms for one level (an empty list if it has none). */
    public static List<MovingPlatform> loadForLevel(String levelFileLocation, int levelId) {
        InputStream in = MovingPlatformLoader.class.getResourceAsStream(levelFileLocation);
        if (in == null) {
            throw new IllegalArgumentException("Cannot find " + levelFileLocation);
        }

        List<MovingPlatform> result = new ArrayList<>();
        int currIndex = -1;
        Scanner scanner = new Scanner(in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            // Skip the same lines LevelLoader skips, so the level numbers match
            if (line.startsWith("#") || line.startsWith(" ")) continue;
            String[] parts = line.split(" ");
            if (parts.length <= 1) continue;

            if (parts[0].equals("LEVEL")) {
                currIndex++;
            } else if (parts[0].equals("MOVING") && currIndex == levelId) {
                result.add(new MovingPlatform(
                        Double.parseDouble(parts[1]), Double.parseDouble(parts[2]),   // x, y
                        Double.parseDouble(parts[3]), Double.parseDouble(parts[4]),   // width, height
                        Double.parseDouble(parts[5]), Double.parseDouble(parts[6]),   // pathX, pathY
                        Double.parseDouble(parts[7])));                               // speed
            }
        }
        scanner.close();
        return result;
    }
}

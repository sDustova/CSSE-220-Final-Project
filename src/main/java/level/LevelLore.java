package level;

import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

/**
 * The game's story and jokes.
 *
 * Each level can have a LORE line in levels.txt, shown when the level starts.
 * LevelLoader skips LORE lines (it ignores words it does not know), so this
 * class reads them itself, using the same rules as LevelLoader so the level
 * numbers match (first level = 0, same as Level.getId()).
 *
 * Line format: LORE any text you want
 */
public final class LevelLore {
    private LevelLore() {}

    // Shown when the player falls off the level
    private static final String[] FALL_JOKES = {
            "You fell. The Rose Points went back to where they came from.",
            "Gravity: 1. You: 0. You still have a physics exam on Friday.",
            "Respawned. Your tuition did not.",
            "That is going on your transcript.",
            "The geese saw that.",
            "Pro tip: the platforms are the parts you stand on.",
            "Your lab partner would like to know where you went.",
    };
    private static final Random RANDOM = new Random();

    /** Returns the LORE text for one level, or "" if it has none. */
    public static String loadForLevel(String levelFileLocation, int levelId) {
        InputStream in = LevelLore.class.getResourceAsStream(levelFileLocation);
        if (in == null) {
            throw new IllegalArgumentException("Cannot find " + levelFileLocation);
        }

        String lore = "";
        int currIndex = -1;
        Scanner scanner = new Scanner(in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            // Skip the same lines LevelLoader skips, so the level numbers match
            if (line.startsWith("#") || line.startsWith(" ")) continue;
            if (line.split(" ").length <= 1) continue;

            if (line.startsWith("LEVEL ")) {
                currIndex++;
            } else if (line.startsWith("LORE ") && currIndex == levelId) {
                lore = line.substring("LORE ".length()); // everything after the word LORE
            }
        }
        scanner.close();
        return lore;
    }

    /** Returns a random joke to show when the player falls. */
    public static String randomFallJoke() {
        return FALL_JOKES[RANDOM.nextInt(FALL_JOKES.length)];
    }
}

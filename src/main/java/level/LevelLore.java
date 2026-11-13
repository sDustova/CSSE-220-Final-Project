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

    // Shown when the player falls off a level that has LORE (right now, only Logan Library)
    private static final String[] FALL_JOKES = {
            "SHHHHH! Falling is a LOUD activity. Signed, the Librarian.",
            "Gravity was on the exam. You did not study gravity.",
            "Respawning... please wait while your GPA recalculates.",
            "Somewhere, your group project partner felt a disturbance and did nothing about it.",
            "You dropped all your Rose Points and your last good pencil. Only one of those respawns.",
            "The book you landed on is now 3 years overdue. That will be $40.",
            "Your professor saw that and is curving this level. Down.",
            "The geese saw that. The geese are in the library now. Nobody knows how.",
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

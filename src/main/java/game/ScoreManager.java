package game;

import java.util.HashMap;
import java.util.Map;

/**
 * Keeps every player's fastest time on every level.
 * TODO: take a Level instead of a level number once Level has getLevelNumber().
 */
public class ScoreManager {
    // level number -> (player name -> that player's best time in seconds)
    private final Map<Integer, Map<String, Double>> fastestTimes = new HashMap<>();

    /** Saves the time only if it beats that player's old best on that level. */
    public void saveTime(String playerName, int levelNumber, double time) {
        Map<String, Double> levelTimes = fastestTimes.get(levelNumber);
        if (levelTimes == null) {
            levelTimes = new HashMap<>();
            fastestTimes.put(levelNumber, levelTimes);
        }

        Double oldBest = levelTimes.get(playerName);
        if (oldBest == null || time < oldBest) {
            levelTimes.put(playerName, time);
        }
    }

    /** Returns the fastest time anyone has on this level, or null if nobody finished it. */
    public Double getBestTime(int levelNumber) {
        Map<String, Double> levelTimes = fastestTimes.get(levelNumber);
        if (levelTimes == null) {
            return null;
        }

        Double best = null;
        for (double time : levelTimes.values()) {
            if (best == null || time < best) {
                best = time;
            }
        }
        return best;
    }
}

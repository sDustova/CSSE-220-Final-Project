package level;

public class LevelManager {
    private Level currentLevel = null;
    private final LevelTimer timer = new LevelTimer();

    public void setCurrentLevel(int id) {
        this.currentLevel = new Level(id);
        this.timer.start();
    }

    public LevelTimer getTimer() {
        return this.timer;
    }

    public void exitCurrentLevel() {
        this.currentLevel = null;
    }
}

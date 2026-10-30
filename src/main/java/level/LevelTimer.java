package level;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.util.Duration;

public class LevelTimer {
    // Timeline delay, 20 milliseconds
    private final int UPDATE_TIME_MILLISECONDS = 20;

    private final Timeline timeline;
    private final DoubleProperty currentTime = new SimpleDoubleProperty(0.0);

    public LevelTimer() {
        this.timeline = new Timeline();

        // Initialize the Timeline with the defined delay; runs indefinitely (unless stopped or paused)
        timeline.getKeyFrames().add(
                new KeyFrame(
                        new Duration(this.UPDATE_TIME_MILLISECONDS),
                        event -> this.update(this.timeline.getCurrentRate())
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    public DoubleProperty getCurrentTimeProperty() {
        return this.currentTime;
    }
    public double getCurrentTime() {
        return this.currentTime.get();
    }

    /**
     * Start the timer when the Level is entered
     */
    public void start() {
        this.timeline.play();
    }

    /**
     * Pause the timer
     */
    public void pause() {
        this.timeline.pause();
    }

    /**
     * Stops the timer completely
     */
    public void stop() {
        this.timeline.stop();
        this.currentTime.set(0.0);
    }

    /**
     * Updates the current time DoubleProperty from the Timeline
     * @param dt The current delay of the timer, typically 20ms
     */
    private void update(double dt) {
        double nextTime = this.currentTime.get() + this.UPDATE_TIME_MILLISECONDS / 1000.0;
        this.currentTime.set(Math.round(100 * nextTime) / 100.0);
    }
}
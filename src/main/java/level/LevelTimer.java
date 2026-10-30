package level;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.util.Duration;


public class LevelTimer {
    private final int UPDATE_TIME_MILLISECONDS = 20;

    private final Timeline timeline;
    private final DoubleProperty currentTime = new SimpleDoubleProperty(0.0);

    public LevelTimer() {
        this.timeline = new Timeline();

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


    public void start() {
        this.timeline.play();
    }

    public void pause() {
        this.timeline.pause();
    }

    public void stop() {
        this.timeline.stop();
        this.currentTime.set(0.0);
    }

    private void update(double dt) {
        double nextTime = this.currentTime.get() + this.UPDATE_TIME_MILLISECONDS / 1000.0;
        this.currentTime.set(Math.round(100 * nextTime) / 100.0);
    }
}
package entities;

import app.Assets;
import javafx.scene.image.Image;

/**
 * A looping flip-book animation: a list of images shown one after another.
 *
 * Call update() once per game frame, then draw getCurrentFrame().
 * Example: with ticksPerFrame = 6 and a 60 fps game loop, the picture
 * changes 10 times a second.
 */
public class SpriteAnimation {
    private final Image[] frames;
    private final int ticksPerFrame; // how many game frames each picture stays on screen
    private int tickCount = 0;       // game frames since the picture last changed
    private int frameIndex = 0;      // which picture is showing right now

    /**
     * @param ticksPerFrame how many game frames to hold each picture
     * @param fileNames     image paths inside src/main/resources/images, in play order
     */
    public SpriteAnimation(int ticksPerFrame, String... fileNames) {
        this.ticksPerFrame = ticksPerFrame;
        frames = new Image[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            frames[i] = Assets.image(fileNames[i]);
        }
    }

    /** Moves the animation forward by one game frame, looping back to the start at the end. */
    public void update() {
        tickCount++;
        if (tickCount >= ticksPerFrame) {
            tickCount = 0;
            frameIndex = (frameIndex + 1) % frames.length;
        }
    }

    /** Starts the animation over from its first picture. */
    public void reset() {
        tickCount = 0;
        frameIndex = 0;
    }

    public Image getCurrentFrame() {
        return frames[frameIndex];
    }
}

package entities;

import behaviors.Collidable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

public class Coin implements Collidable {
    private Group view;
    // COIN HOOK: the coin pictures are 16x16 pixels; we draw them at 24x24
    private static final double SIZE = 24;

    // COIN HOOK: one ImageView whose picture is swapped each frame, like Player
    private final ImageView sprite;
    // COIN HOOK: 4 spin pictures, each held 8 game frames (one full spin is about half a second)
    private final SpriteAnimation spin = new SpriteAnimation(8,
            "collectable/coin/coin_frame_1 (1).png",
            "collectable/coin/coin_frame_1 (2).png",
            "collectable/coin/coin_frame_1 (3).png",
            "collectable/coin/coin_frame_1 (4).png");

    public Coin(){
        this(0, 0); // COIN HOOK: same as a coin centered on (0, 0)
    }

    /** Makes a coin centered on the point (x, y). */
    public Coin(double x, double y){
        // COIN HOOK: build the sprite and center it on (x, y)
        sprite = new ImageView(spin.getCurrentFrame());
        sprite.setFitWidth(SIZE);
        sprite.setFitHeight(SIZE);
        sprite.setSmooth(false); // keep pixel art sharp instead of blurry when scaled up
        sprite.setX(x - SIZE / 2);
        sprite.setY(y - SIZE / 2);
        view = new Group(sprite);
    }

    public Group getView() {return view;}

    /** Call once per game frame to spin the coin. */
    public void update(){
        // COIN HOOK: step the animation and show its current picture
        spin.update();
        sprite.setImage(spin.getCurrentFrame());
    }

    public boolean isVisible(){return view.isVisible();}

    public void setVisible(boolean visible){
        view.setVisible(visible);
    }

    @Override
    public boolean isColliding(Collidable b) {
        return getBounds().intersects(b.getBounds());
    }

    @Override
    public Bounds getBounds() {
        return view.getBoundsInLocal();
    }
}

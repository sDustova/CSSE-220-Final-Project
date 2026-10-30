package entities;

import behaviors.Collidable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Coin implements Collidable {
    private final Group view;
    private final double FIT_HEIGHT = 150;
    private final double FIT_WIDTH= 150;

    public Coin(){
        Image image = null;
        ImageView coin = new ImageView(image);
        coin.setFitHeight(FIT_HEIGHT);
        coin.setFitWidth(FIT_WIDTH);
        coin.setPreserveRatio(true);
        view = new Group(coin);
    }
    public Coin(double x, double y){
        Image image = null;
        ImageView coin = new ImageView(image);
        coin.setFitHeight(FIT_HEIGHT);
        coin.setFitWidth(FIT_WIDTH);
        coin.setPreserveRatio(true);
        coin.setX(x);
        coin.setY(y);
        view = new Group(coin);
    }

    public Group getView() {return view;}

    @Override
    public boolean isColliding(Collidable b) {
        return getBounds().intersects(b.getBounds());
    }

    @Override
    public Bounds getBounds() {
        return view.getBoundsInLocal();
    }
}

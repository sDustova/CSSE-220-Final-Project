package entities;

import behaviors.Collidable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Coin implements Collidable {
    private Group view;
    private final double FIT_HEIGHT = 50;
    private final double FIT_WIDTH= 50;

    private final String[] framesImage = {"/images/collectable/coin/coin_frame_1 (1).png","/images/collectable/coin/coin_frame_1 (2).png","/images/collectable/coin/coin_frame_1 (3).png","/images/collectable/coin/coin_frame_1 (4).png"};
    private ImageView[] frames = new ImageView[4];
    private int frameAt=0;

    public Coin(){
//        Image image = null;
//        ImageView coin = new ImageView(image);
//        coin.setFitHeight(FIT_HEIGHT);
//        coin.setFitWidth(FIT_WIDTH);
//        coin.setPreserveRatio(true);
        makeFrames(0,0);
        view = new Group(frames[0]);
    }
    public Coin(double x, double y){
//        Image image = null;
//        ImageView coin = new ImageView(image);
//        coin.setFitHeight(FIT_HEIGHT);
//        coin.setFitWidth(FIT_WIDTH);
//        coin.setPreserveRatio(true);
//        coin.setX(x);
//        coin.setY(y);
        makeFrames(x,y);
        view = new Group(frames[0]);
    }

    public Group getView() {return view;}

    public void makeFrames(double x, double y){
        for (int i=0;i<frames.length;i++){
            Image image = new Image(Coin.class.getResource(framesImage[0]).toExternalForm());
            frames[i] = new ImageView(image);
            frames[i].setFitHeight(FIT_HEIGHT);
            frames[i].setFitWidth(FIT_WIDTH);
            frames[i].setPreserveRatio(true);
            frames[i].setX(x);
            frames[i].setY(y);
        }
    };

    public void update(){
        frameAt++;
        if (frameAt==frames.length)
            frameAt=0;
        view.getChildren().clear();
        view.getChildren().addAll(frames[frameAt]);
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

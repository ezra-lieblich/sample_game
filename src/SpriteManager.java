import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class SpriteManager {
    private Group Root;
    private Ship myShip;
    private ArrayList<Rocket> Rockets = new ArrayList<Rocket>();
    
	public SpriteManager(int width, int height, Group root) {
		Root = root;
        myShip = new Ship(width, height);
        Root.getChildren().add(myShip.getNode());
	}
	
	public void keyInput(KeyCode code) {
		switch (code) {
        case RIGHT:
        	myShip.moveRight();
            break;
        case LEFT:
        	myShip.moveLeft();
            break;
        default:
            // do nothing
		}
	}
	
	public void keyRelease(KeyCode code) {
		switch (code) {
        case RIGHT:
        	myShip.stop();
            break;
        case LEFT:
        	myShip.stop();
            break;
        case SPACE:
            fireRocket();
            break;
        default:
            // do nothing
		
		}
	}
	public void fireRocket() {
    	Rocket my_rocket = new Rocket(myShip);
        Root.getChildren().add(my_rocket.getNode()); 
        Rockets.add(my_rocket);
       
    }
	
	public void move(double time) {
		myShip.move(time);
		for (Rocket rocket: Rockets) {
			rocket.move(time);
		}
	}
	public void levelOne() {
		Timeline timeline = new Timeline(new KeyFrame(
				Duration.millis(5000), e -> fireRocket()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	
}

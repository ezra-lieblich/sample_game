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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class SpriteManager {
    private Group Root;
    private Ship myShip;
	private ArrayList<Rocket> Rockets = new ArrayList<Rocket>();
    private ArrayList<Asteroid> Asteroids = new ArrayList<Asteroid>();
    private Boss Boss;
    
	public SpriteManager(Group root) {
		Root = root;
		myShip = new Ship();
	    Root.getChildren().add(myShip.getNode());
	}
	 
	 public Ship getMyShip() {
			return myShip;
		}

		public ArrayList<Rocket> getRockets() {
			return Rockets;
		}

		public ArrayList<Asteroid> getAsteroids() {
			return Asteroids;
		}
	public void move(double time) {
		myShip.move(time);
		for (Rocket rocket: Rockets) {
			rocket.move(time);
		}
		for (Asteroid asteroid: Asteroids) {
			asteroid.move(time);
		}
		if (Boss != null) Boss.move(time);
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
    	Rocket new_rocket = new Rocket(myShip);
        Root.getChildren().add(new_rocket.getNode()); 
        Rockets.add(new_rocket);
       
    }
	
	public void asteroidDrop() {	
			Asteroid new_enemy = new Asteroid(400, 600);
			Root.getChildren().add(new_enemy.getNode());
			Asteroids.add(new_enemy);
	}
	
	public boolean doIntersect(Sprite obj1, Sprite obj2) {
		return !(obj1.getX() > obj2.getX() + obj2.getXSize() || 
				obj1.getX() + obj1.getXSize() < obj2.getX() || 
				obj1.getY() + obj1.getYSize() < obj2.getY() || 
				obj1.getY() > obj2.getY() + obj2.getYSize()); 
	}
	
	public boolean OutOfBoundsY(Sprite sprite) {
		return sprite.getY() > Main.SIZEY || (sprite.getY() + sprite.getYSize()) < 0;
	}
	
	public void removeSprites(ArrayList<Asteroid> dead_asteroids, ArrayList<Rocket> dead_rockets) {
		for (Asteroid asteroid : dead_asteroids) {
			Root.getChildren().remove(asteroid.getNode());
			Asteroids.remove(asteroid);
		}
		for (Rocket rocket : dead_rockets) {
			Root.getChildren().remove(rocket.getNode());
			Rockets.remove(rocket);
		}
		//System.out.println(Asteroids);
		//System.out.println(Rockets);
	}
	public void addBoss() {
		Boss = new Boss();
		Root.getChildren().add(Boss.getNode());
	}
}

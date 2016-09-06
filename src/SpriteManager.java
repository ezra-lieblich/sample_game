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
    private Boss boss;
    private Ship myShip;
    private ArrayList<Rocket> Rockets = new ArrayList<Rocket>();
    private ArrayList<Asteroid> Asteroids = new ArrayList<Asteroid>();
    private int Score; 
    private Text scoreText;
    private int firstLevelEnemiesRemaining = 10;
    
	public SpriteManager(int width, int height, Group root) {
		Root = root;
        myShip = new Ship(width, height);
        Root.getChildren().add(myShip.getNode());
        Score = 0;
        scoreText = new Text(300, 20, "Score: " + Score);
        scoreText.setFont(new Font(20));
        Root.getChildren().add(scoreText);
        boss = new Boss();
    	Root.getChildren().add(boss.getNode());
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
	
	public void move(double time) {
		myShip.move(time);
		for (Rocket rocket: Rockets) {
			rocket.move(time);
		}
		for (Asteroid asteroid: Asteroids) {
			asteroid.move(time);
		}
		boss.move(time);
	}
	public void firstWave() {
		Timeline firstLevel = new Timeline(new KeyFrame(
				Duration.millis(3000), e -> asteroidDrop()));
		firstLevel.setCycleCount(firstLevelEnemiesRemaining);
		firstLevel.play();
	}
	
	public void asteroidDrop() {
		if (firstLevelEnemiesRemaining > 0){
			Asteroid new_enemy = new Asteroid(400, 600);
			Root.getChildren().add(new_enemy.getNode());
			Asteroids.add(new_enemy);
		}
	}
	
	public boolean doIntersect(Sprite obj1, Sprite obj2) {
		if(obj1.getX() > obj2.getX() + obj2.getXSize() || obj1.getX() + obj1.getXSize() < obj2.getX() 
				|| obj1.getY() + obj1.getYSize() < obj2.getY() || obj1.getY() > obj2.getY() + obj2.getYSize()) {
			return false;
		}
		return true;
	}
	
	public void checkCollisions() {
		for (int i = Asteroids.size() - 1; i >= 0; i--) {
			for (int j = Rockets.size() -1; j >= 0; j--) {
				if (doIntersect(Asteroids.get(i), Rockets.get(j))) {
					Root.getChildren().remove(Asteroids.get(i).getNode());
					Root.getChildren().remove(Rockets.get(j).getNode());
					Asteroids.remove(i);
					Rockets.remove(j);
					Score++;
					scoreText.setText("Score: " + Score);
					break;
				}
			}
		}
	}
	public void transitionLevel() {
		/*Text saying first wave is almost over. Finish clearing astroids. Boss incoming soon
		 * Timeline that is delayed 15 seconds to finish.
		 * Handle event add boss to Asteroids.
		 * 
		 */
	}
}

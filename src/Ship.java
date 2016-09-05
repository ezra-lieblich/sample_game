import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Ship extends Sprite {
	private final int shipSpeed = 65;

	public Ship(int width, int height) {
		Image ship = new Image(getClass().getClassLoader().getResourceAsStream("ship.png"));
		image = new ImageView(ship);
		setX(width / 2 - image.getBoundsInLocal().getWidth() / 2);
		setY(height - image.getBoundsInLocal().getHeight());
		velocityY = 0;
		velocityX = 0;
	}
	
	public void moveRight() {
		velocityX = shipSpeed;
	}
	public void moveLeft() {
		velocityX = -shipSpeed;
	}
	public void stop() {
		velocityX = 0; 
	}
}


import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Asteroid extends Sprite {
	public Asteroid (int width, int height) {
		Image asteroid = new Image(getClass().getClassLoader().getResourceAsStream("asteroid.png"));
		image = new ImageView(asteroid);
		setX(Math.random() * (400 - this.getXSize()));
		setY(0);
		velocityY = -40;
		velocityX = 0;
	}
}

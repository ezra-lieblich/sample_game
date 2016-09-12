import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Asteroid extends Sprite. This is the sprite that represent the 
 * enemies in the first level. It is called by the SpriteMangaer 
 * @author ezra
 *
 */
public class Asteroid extends Sprite {
	/**
	 * Finds the image and sets its positions to a random X coord at top
	 * of the screen
	 */
	public Asteroid () {
		Image asteroid = new Image(getClass().getClassLoader().getResourceAsStream("asteroid.png"));
		image = new ImageView(asteroid);
		setX(Math.random() * (Main.SIZEX - this.getXSize()));
		setY(0);
		velocityY = -45;
		velocityX = 0;
	}
}

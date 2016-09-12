import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * Rocket that is launched from the rocket in gameplayLevel
 * @author ezra
 *
 */
public class Rocket extends Sprite{
	/**
	 * Finds the rocket image and also positions the rocket in the center of the rocket
	 * @param ship - ship sprite to determine the coordinate of the ship
	 */
	public Rocket(Ship ship){
		Image rocket = new Image(getClass().getClassLoader().getResourceAsStream("rocket.png"));
		image = new ImageView(rocket);
		setX(ship.getX() + ship.getXSize()/2 - this.getXSize() / 2);
		setY(ship.getY() - this.getYSize());
		velocityY = 150;
		velocityX = 0;
	}
}

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Boss Sprite that appears after the first level is complete. Moves horizontally and 
 * vertically and also has health component
 * @author ezra
 *
 */
public class Boss extends Sprite {
	private int HealthPoints;
	/**
	 * Initializes the Boss sprite and gets the image from the folder as well as set the horizontal
	 * and vertical velocity
	 */
	public Boss () {
		Image boss = new Image(getClass().getClassLoader().getResourceAsStream("boss.png"));
		image = new ImageView(boss);
		setX(Math.random() * (Main.SIZEX - this.getXSize()));
		setY(0);
		velocityY = -10;
		velocityX = 55;
		HealthPoints = 75;
	}
	/**
	 * Updates the position of the Sprite and flips the velocity if the Sprite is out of bounds
	 * causing it to bounce on the screen
	 */
	public void move(double time) {
		if (outofBoundsX()) {
			velocityX = velocityX * -1;
		}
		setX(getX() + time * velocityX);
		setY(getY() - time * velocityY);

	}
	/**
	 * Decreases the health of the Boss
	 */
	public void decreaseHealth(int damage) {
		HealthPoints -= damage;
	}
	/**
	 * Checks if the Boss is dead
	 * @return true if its health is less than zero false otherwise
	 */
	public boolean isDead() {
		return HealthPoints <= 0;
	}
	private boolean outofBoundsX() {
		return (this.getX() < 0 || this.getX() + this.getXSize() > Main.SIZEX);
	}
}

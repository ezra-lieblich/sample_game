import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * Extends the properties of the Sprite and is the ship that fires rockets. Called by the SpriteManager
 * @author ezra
 *
 */
public class Ship extends Sprite {
	private final int shipSpeed = 60;

	/** 
	 * Loads the image and sets the default position to the middle of the screen
	 */
	public Ship() {
		Image ship = new Image(getClass().getClassLoader().getResourceAsStream("ship.png"));
		image = new ImageView(ship);
		setX(Main.SIZEX / 2 - this.getXSize() / 2);
		setY(Main.SIZEY - this.getYSize());
		velocityY = 0;
		velocityX = 0;
	}
	/**
	 * Overrides the move and wrapAround resets your position to the opposite side
	 * if you are outside the map
	 */
	public void move(double time) {
		wrapAround();
		this.setX(getX() + time * velocityX);
	}
	/**
	 * Sets velocity to positive and moves to the right. Called when key input is the right arrow
	 */
	public void moveRight() {	
		velocityX = shipSpeed;
	}
	/**
	 * Sets velocity to negative and moves to the left. Called when key input is the left arrow
	 */
	public void moveLeft() {
		velocityX = -shipSpeed;
	}
	/**
	 * Sets velocity to zero and stop movement. Called when key is released
	 */
	public void stop() {
		velocityX = 0; 
	}
	private void wrapAround() {
		if(this.getX() < 0) this.setX(Main.SIZEX - this.getXSize());
		if(this.getX() + this.getXSize() > Main.SIZEX) this.setX(0); 
	}
}

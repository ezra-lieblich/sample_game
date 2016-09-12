import javafx.scene.Node;
import javafx.scene.image.ImageView;

/**
 * Basic properties of any object in the game where the Node is an ImageView
 * @author ezra
 *
 */
public abstract class Sprite {
	protected int velocityX;
	protected int velocityY;
	protected ImageView image;
	/**
	 * 
	 * @param width - X coordinate Sprite is set to
	 */
	public void setX(double width) {
		image.setX(width);
	}
	/**
	 * 
	 * @return -  get the X coordinate of Sprite
	 */
	public double getX(){
		return image.getX();
	}
	/**
	 * 
	 * @return - The X size of the image
	 */
	public double getXSize(){
		return image.getBoundsInLocal().getWidth();
	}
	/**
	 * 
	 * @param height - Y coordinate Sprite is set to
	 */
	public void setY(double height) {
		image.setY(height);
	}
	/**
	 * 
	 * @return get the Y coordinate of Sprite
	 */
	public double getY() {
		return image.getY();
	}
	/**
	 * 
	 * @return - The Y size of the image
	 */
	public double getYSize() {
		return image.getBoundsInLocal().getHeight();
	}
	/**
	 * Updates the position of the Sprite
	 * @param time - time that has passed in the loop
	 */
	public void move(double time){
		setX(getX() + time * velocityX);
		setY(getY() - time * velocityY);
	}
	/**
	 * 
	 * @return - The imageView node
	 */
	public Node getNode() {
		return image;
	}

}

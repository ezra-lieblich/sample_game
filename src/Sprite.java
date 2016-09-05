import javafx.scene.Node;
import javafx.scene.image.ImageView;

public abstract class Sprite {
	protected int velocityX;
	protected int velocityY;
	protected ImageView image;
	public void setX(double width) {
		image.setX(width);
	}
	public double getX(){
		return image.getX();
	}
	public double getXSize(){
		return image.getBoundsInLocal().getWidth();
	}
	public void setY(double height) {
		image.setY(height);
	}
	public double getY() {
		return image.getY();
	}
	public double getYSize() {
		return image.getBoundsInLocal().getHeight();
	}
	public void move(double time){
		setX(getX() + time * velocityX);
		setY(getY() - time * velocityY);
	}
	public Node getNode() {
		return image;
	}
	
}

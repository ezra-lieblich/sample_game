import javafx.scene.Node;
import javafx.scene.image.ImageView;

public abstract class Sprite {
	protected int velocity;
	private ImageView image;
	public abstract void setX(double width);
	public abstract double getX();
	public abstract double getXSize();
	public abstract void setY(double height);
	public abstract double getY();
	public abstract double getYSize();
	public abstract void move(double time);
	public abstract Node getNode();
	
}

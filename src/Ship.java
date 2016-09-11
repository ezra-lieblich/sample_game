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
	private final int shipSpeed = 60;
	private int Width;

	public Ship() {
		Image ship = new Image(getClass().getClassLoader().getResourceAsStream("ship.png"));
		image = new ImageView(ship);
		setX(Main.SIZEX / 2 - this.getXSize() / 2);
		setY(Main.SIZEY - this.getYSize());
		velocityY = 0;
		velocityX = 0;
		Width = Main.SIZEX;
	}
	
	public void move(double time) {
		wrapAround();
		this.setX(getX() + time * velocityX);
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
	private void wrapAround() {
		if(this.getX() < 0) this.setX(Main.SIZEX - this.getXSize());
		if(this.getX() + this.getXSize() > Main.SIZEX) this.setX(0); 
	}
}

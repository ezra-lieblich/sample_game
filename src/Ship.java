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
	private ImageView myShip;
	private final int shipSpeed = 35;
	private int velocity = 0;
	
	public Ship(int width, int height) {
		Image ship = new Image(getClass().getClassLoader().getResourceAsStream("ship.png"));
		myShip = new ImageView(ship);
		setX(width / 2 - myShip.getBoundsInLocal().getWidth() / 2);
		setY(height - myShip.getBoundsInLocal().getHeight());
	}


	@Override
	public void setX(double width) {
		// TODO Auto-generated method stub
		myShip.setX(width);
	}


	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return myShip.getX();
	}


	@Override
	public void setY(double height) {
		// TODO Auto-generated method stub
		myShip.setY(height);
	}


	@Override
	public double getY() {
		return myShip.getY();
	}


	@Override
	public void move(double time) {
		// TODO Auto-generated method stub
		setX(getX() + time * velocity);
	}


	@Override
	public Node getNode() {
		// TODO Auto-generated method stub
		return myShip;
	}
	
	public void keyInput(KeyCode code){
		switch (code) {
        case RIGHT:
        	velocity = shipSpeed; 
            break;
        case LEFT:
        	velocity = -shipSpeed;
            break;
        case SPACE:
            //fireRocket();
            break;
        case DOWN:
            //myShip.setY(myShip.getY() + KEY_INPUT_SPEED);
            break;
        default:
            // do nothing
		}
	}


	public void keyRelease(KeyCode code) {
		// TODO Auto-generated method stub
		switch (code) {
        case RIGHT:
        	velocity = 0; 
            break;
        case LEFT:
        	velocity = 0;
            break;
        case SPACE:
            //fireRocket();
            break;
        case DOWN:
            //myShip.setY(myShip.getY() + KEY_INPUT_SPEED);
            break;
        default:
            // do nothing
		
		}
	}


	@Override
	public double getXSize() {
		// TODO Auto-generated method stub
		return myShip.getBoundsInLocal().getWidth();
	}


	@Override
	public double getYSize() {
		// TODO Auto-generated method stub
		return myShip.getBoundsInLocal().getHeight();
	}
}

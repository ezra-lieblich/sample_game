import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Rocket extends Sprite{
	private ImageView myRocket;
	private final int velocity = 90; 
	public Rocket(Ship ship){
		Image rocket = new Image(getClass().getClassLoader().getResourceAsStream("rocket.png"));
        myRocket = new ImageView(rocket);
        setX(ship.getX() + ship.getXSize()/2 - rocket.getWidth() / 2);
        setY(ship.getY() - rocket.getHeight());
	}
	@Override
	public void setX(double width) {
		// TODO Auto-generated method stub
		myRocket.setX(width);
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return myRocket.getX();
	}

	@Override
	public void setY(double height) {
		// TODO Auto-generated method stub
		myRocket.setY(height);
	}

	@Override
	public double getY() {
		return myRocket.getY();
	}

	@Override
	public void move(double time) {
		// TODO Auto-generated method stub
		setY(getY() - time * velocity);
	}
	@Override
	public Node getNode() {
		// TODO Auto-generated method stub
		return myRocket;
	}
	
	@Override
	public double getXSize() {
		// TODO Auto-generated method stub
		return myRocket.getBoundsInLocal().getWidth();
	}


	@Override
	public double getYSize() {
		// TODO Auto-generated method stub
		return myRocket.getBoundsInLocal().getHeight();
	}

}

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Rocket extends Sprite{
	//private ImageView myRocket;
	//private final int velocity = 90; 
	public Rocket(Ship ship){
		Image rocket = new Image(getClass().getClassLoader().getResourceAsStream("rocket.png"));
        image = new ImageView(rocket);
        setX(ship.getX() + ship.getXSize()/2 - rocket.getWidth() / 2);
        setY(ship.getY() - this.getYSize());
        velocityY = 90;
        velocityX = 0;
	}
}

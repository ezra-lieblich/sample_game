import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Boss extends Sprite {
	public Boss () {
		Image asteroid = new Image(getClass().getClassLoader().getResourceAsStream("boss.png"));
		image = new ImageView(asteroid);
		setX(Math.random() * (400 - this.getXSize()));
		setY(0);
		velocityY = -15;
		velocityX = 0;
	}

}

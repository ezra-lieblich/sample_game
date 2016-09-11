import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Boss extends Sprite {
	private int HealthPoints = 10;
	public Boss () {
		Image boss = new Image(getClass().getClassLoader().getResourceAsStream("boss.png"));
		image = new ImageView(boss);
		setX(Math.random() * (Main.SIZEX - this.getXSize()));
		setY(0);
		velocityY = -10;
		velocityX = 55;
	}
	public void move(double time) {
		if (outofBoundsX()) {
			velocityX = velocityX * -1;
		}
		setX(getX() + time * velocityX);
		setY(getY() - time * velocityY);

	}
	
	public boolean outofBoundsX() {
		return (this.getX() < 0 || this.getX() + this.getXSize() > Main.SIZEX);
	}
	public void decreaseHealth() {
		HealthPoints--;
	}
	public boolean isDead() {
		return HealthPoints <= 0;
	}

}

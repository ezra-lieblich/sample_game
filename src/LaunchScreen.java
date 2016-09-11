import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LaunchScreen {
	private Group Root;
	public static final String id = "launch node";
	public LaunchScreen() {
		Root = new Group();
		Root.setId(id);
		Image load_screen = new Image(getClass().getClassLoader().getResourceAsStream("SplashScreen.png"));
		ImageView image = new ImageView(load_screen);
		image.setX(Main.SIZEX/2 - image.getBoundsInLocal().getWidth()/2);
		image.setY(Main.SIZEY/2 - image.getBoundsInLocal().getHeight()/2);
		Root.getChildren().add(image);
	}
	public Group getRoot() {
		return Root;
	}
}

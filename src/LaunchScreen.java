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
	public LaunchScreen(int width, int height, Group root) {
		Root = root;
		Canvas canvas = new Canvas( width, height );
        Root.getChildren().add( canvas );
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill( Color.BEIGE );
        gc.fillRect(width*.125, height*.25, width*.75, width*.75);
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(2);
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 42 );
        gc.setFont( theFont );
        gc.setFill(Color.BLACK);
        gc.fillText( "Asteroid Attack", width*.125, height*.25+theFont.getSize() );
        Font sub_font = Font.font("Times New Roman", 20);
        gc.setFont(sub_font);
        gc.fillText( "Instructions:", width*.125, height*.50+theFont.getSize() );
	}
}

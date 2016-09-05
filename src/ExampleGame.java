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


/**
 * Separate the game code from some of the boilerplate code.
 * 
 * @author Robert C. Duvall
 */
class ExampleGame {
    public static final String TITLE = "Asteroid Attack";

    private Group Root;
    private Group title;
    private Scene myScene;
    private SpriteManager spriteManager;
    private LaunchScreen launchScreen;

    /**
     * Returns name of the game.
     */
    public String getTitle () {
        return TITLE;
    }

    /**
     * Create the game's scene
     */
    public Scene init (int width, int height) {
        Root = new Group();
        title = new Group();
        myScene = new Scene(title, width, height, Color.AQUA);
        launchScreen = new LaunchScreen(width, height, title);
        spriteManager = new SpriteManager(width, height, Root);
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        myScene.setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
        return myScene;

    }



	/**
     * Change properties of shapes to animate them
     * 
     * Note, there are more sophisticated ways to animate shapes,
     * but these simple ways work too.
     */
    public void step (double elapsedTime) {
    	spriteManager.move(elapsedTime);
    }


    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
    	switch (code) {
        case ENTER:
        	myScene.setRoot(Root);
        	spriteManager.levelOne();
        	break;
        case Q:
        	myScene.setRoot(title);
    	}
    	spriteManager.keyInput(code);
    	
    }
    private void handleKeyRelease(KeyCode code) {
		// TODO Auto-generated method stub
    		spriteManager.keyRelease(code);
		}	    
   
}

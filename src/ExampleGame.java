import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


/**
 * Separate the game code from some of the boilerplate code.
 * 
 * @author Robert C. Duvall
 */
class ExampleGame {
    public static final String TITLE = "Example JavaFX";

    private Group Root;
    private Scene myScene;
    //private Ship myShip;
    private ArrayList<Sprite> mySprites = new ArrayList<Sprite>();
    private SpriteManager spriteManager;

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
        // create a scene graph to organize the scene
        Root = new Group();
        // create a place to see the shapes
        myScene = new Scene(Root, width, height, Color.WHITE);
        spriteManager = new SpriteManager(width, height, Root);
        /*
        // make some shapes and set their properties
        myShip = new Ship(width, height);
        // order added to the group is the order in whuch they are drawn
        mySprites.add(myShip);
        Root.getChildren().add(myShip.getNode());
        // respond to input
         * 
         */
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
        // update attributes
    	//note that elapsed time is to adjust for frame rate
        //myShip.setX(200);
    	spriteManager.move(elapsedTime);
    }


    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
    	spriteManager.keyInput(code);
    	
    }
    private void handleKeyRelease(KeyCode code) {
		// TODO Auto-generated method stub
    		spriteManager.keyRelease(code);
		}	

    // What to do each time a key is pressed
    
   
}

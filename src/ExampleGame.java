import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

//if you have a magic number and use a conditional on that number, THAT IS VERY BAD
//For placing things its okay to use magic numbers for only THIS project
//Naming conventions for constants (public or private, static(java one instance of this, final)
// ALL CAPS with _ UNDERSCORES
//Every PUBLIC METHODS NEEDS A JAVADOC COMMENT
//BorderPane

/**
 * Separate the game code from some of the boilerplate code.
 * 
 * @author Robert C. Duvall
 */
class ExampleGame {
    public static final String TITLE = "Asteroid Attack";

    private Group title;
    private Scene myScene;
    private LaunchScreen launchScreen;
    private GameplayLevel gameplayLevel;

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
        title = new Group();
        title.setId("titleRoot");
        myScene = new Scene(title, width, height, Color.AQUA);
    	//gameplayLevel = new GameplayLevel();
        launchScreen = new LaunchScreen(width, height, title);
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
    	if (myScene.getRoot().getId().equals(GameplayLevel.id)) {
    		gameplayLevel.step(elapsedTime);
    		//if (gameplayLevel.isGameOver()) {
    			//loadGameOverScreen();
    		//}
    	}
    	//checkStatus();
    	//spriteManager.updateScore();
    }


    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
    	switch (code) {
        case ENTER:
        	gameplayLevel = new GameplayLevel();
        	myScene.setRoot(gameplayLevel.getRoot());
        	break;
        case Q:
        	myScene.setRoot(title);
    	}
    	gameplayLevel.keyInput(code);
    	
    }
    private void handleKeyRelease(KeyCode code) {
		// TODO Auto-generated method stub
    		gameplayLevel.keyRelease(code);
		}	    
   
}

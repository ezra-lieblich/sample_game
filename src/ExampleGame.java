import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

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
    private String currentRootId;
    private GameOverScreen gameOverScreen;

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
        launchScreen = new LaunchScreen();
        myScene = new Scene(launchScreen.getRoot(), width, height, Color.MEDIUMBLUE);
    	//gameplayLevel = new GameplayLevel();
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
    	currentRootId = myScene.getRoot().getId();
    	switch (currentRootId) {
    	case GameplayLevel.id:
    		gameplayLevel.step(elapsedTime);
    		if (gameplayLevel.isGameOver()) {
    			boolean outcome = gameplayLevel.winGame();
    			gameOverScreen = new GameOverScreen(outcome);
    			myScene.setRoot(gameOverScreen.getRoot());
    			//loadGameOverScreen();
    			
    		}
    	}
    }


    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
    	switch (currentRootId) {
        case LaunchScreen.id:
        	launchScreen.keyInput(code);	
        	break;
        case GameplayLevel.id:
        	gameplayLevel.keyInput(code);
        	break;
    	case GameOverScreen.id:
    		gameOverScreen.keyInput(code);
    		break;

    	} 	
    }
    private void handleKeyRelease(KeyCode code) {
		// TODO Auto-generated method stub
    	if (currentRootId.equals(GameplayLevel.id))
    		gameplayLevel.keyRelease(code);

	}
    
    class LaunchScreen {
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
    	public void keyInput(KeyCode code) {
    		switch(code) {
    		case ENTER:
    			gameplayLevel = new GameplayLevel();
            	myScene.setRoot(gameplayLevel.getRoot());
            	break;
    		}
    	}
    }
   class GameOverScreen {
	   private Group Root;
	   public static final String id = "game over node";
	   private boolean winGame;
	   public static final int textSize = 12;
	   
	   public GameOverScreen(boolean outcome) {
		   winGame = outcome;
		   Root = new Group();
		   Root.setId(id);
		   Root.getChildren().add(createBorderPane());
	   }
	   public void keyInput(KeyCode code) {
		   switch(code) {
   			case ENTER:
   				gameplayLevel = new GameplayLevel();
   				myScene.setRoot(gameplayLevel.getRoot());
   				break;
   			case Q:
   				launchScreen = new LaunchScreen();
   				myScene.setRoot(launchScreen.getRoot());
   				break;
		   }
	   }
	public Parent getRoot() {
		return Root;
	}
	private BorderPane createBorderPane() {
		   BorderPane layout = new BorderPane();
		   VBox text_outline = new VBox(10);		   
		   text_outline.getChildren().add(gameOutcomeText());
		   text_outline.getChildren().add(playAgainText());
		   layout.setCenter(text_outline);
		   return layout;
	   }
	   
	   private Text gameOutcomeText() {
		   Text game_outcome;
		   if (winGame) {
			   game_outcome = new Text("Congrats! You defeated the boss and "
			   		+ "his army of asteroids. Go home and celebrate!");
		   }
		   else {
			   game_outcome = new Text("Sorry. You failed to stop the asteroids"
			   		+ "and save Earth. At least you can join Harambe in heaven.");
		   }
		   game_outcome.setFont(new Font(textSize));
		   return game_outcome;
	   }
	   private Text playAgainText() {
		   Text play_again = new Text("Hit Enter to try again or press Q to "
			   		+ "to return to the main menu");
			   play_again.setFont(new Font(textSize));
			   return play_again;
	   }
   }
}

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Controls the root on the scene and switches between load screen,
 * gameplay screen, and the game over screen. Also handles movement
 * and key presses. Called on by main
 * 
 * @author ezra
 */
class Game {
	public static final String TITLE = "Asteroid Attack";
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
	 * Create the game's scene and sets it to the LaunchScreen class
	 * Also sets scene on key presses
	 * @param width - The width of the scene
	 * @param height - The height of the scene
	 */
	public Scene init (int width, int height) {
		launchScreen = new LaunchScreen();
		myScene = new Scene(launchScreen.getRoot(), width, height, Color.MEDIUMBLUE);
		myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		myScene.setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
		return myScene;

	}



	/**
	 * Updates the objects in the gameplay level and also
	 * checks if the game is over
	 * @param - the time that is passed in the game loop 
	 */
	public void step (double elapsedTime) {
		currentRootId = myScene.getRoot().getId();
		if (currentRootId.equals(GameplayLevel.id)) { 	
			gameplayLevel.step(elapsedTime);
			if (gameplayLevel.isGameOver()) {
				//pass whether win or lose to gameOverScreen
				boolean outcome = gameplayLevel.winGame();
				gameOverScreen = new GameOverScreen(outcome);
				myScene.setRoot(gameOverScreen.getRoot());

			}
		}
	}

	/**
	 * Takes key input from the scene and determines which screen
	 * to pass the input to
	 * @param code - key that was pressed in the scene
	 */
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
	/**
	 * Takes key release from the scene and checks if gameplayLevel
	 * is the current root since thats the only screen that handles
	 * key releases
	 * @param code - key that was released in the scene
	 */
	private void handleKeyRelease(KeyCode code) {
		if (currentRootId.equals(GameplayLevel.id))
			gameplayLevel.keyRelease(code);

	}

	/**
	 * The default load screen that shows the splash screen with 
	 * instructions. It is initialized in the super class and also interacts with 
	 * gameplayLevel class.
	 * @author ezra
	 *
	 */
	class LaunchScreen {
		private Group Root;
		public static final String id = "launch node";
		/**
		 * Creates the route and also centers the SplashScreen image
		 */
		public LaunchScreen() {
			Root = new Group();
			Root.setId(id);
			Image load_screen = new Image(getClass().getClassLoader().getResourceAsStream("SplashScreen.png"));
			ImageView image = new ImageView(load_screen);
			image.setX(Main.SIZEX/2 - image.getBoundsInLocal().getWidth()/2);
			image.setY(Main.SIZEY/2 - image.getBoundsInLocal().getHeight()/2);
			Root.getChildren().add(image);
		}
		/** 
		 * @return the Root node of the class 
		 */
		public Group getRoot() {
			return Root;
		}
		/**
		 * Switches and sets scene to gameplayLevel if Enter key is hit
		 * @param code - key that is passed in from myScene
		 */
		public void keyInput(KeyCode code) {
			switch(code) {
			case ENTER:
				gameplayLevel = new GameplayLevel();
				myScene.setRoot(gameplayLevel.getRoot());
				break;
			default:
				break;
			}
		}
	}
	/** 
	 * The gameOverScreen that is called after gamePlayLevel is over and asks you if you want to
	 * play again. It interacts with either the the launchScreen or gamePlayLevel after depending
	 * on what is pressed
	 * @author ezra
	 *
	 */
	class GameOverScreen {
		private Group Root;
		public static final String id = "game over node";
		private boolean winGame;
		public static final int textSize = 12;
		private int centerX;
		private int centerY;

		/**
		 * Sets all the instance variable and records the outcome of the game.
		 * @param outcome - boolean if you have won or lost the game. Also add the the text to Root
		 */
		public GameOverScreen(boolean outcome) {
			winGame = outcome;
			centerX = Main.SIZEX/4;
			centerY = Main.SIZEY/2;
			Root = new Group();
			Root.setId(id);
			Root.getChildren().add(gameOutcomeText());
			Root.getChildren().add(playAgainText());
		}
		/**
		 * 
		 * @return -  The root of the gameOverScreen
		 */
		public Parent getRoot() {
			return Root;
		}
		/**
		 * Based off the key input, the function will switch to either the launchScreen or 
		 * back to gamePlayLevel
		 * @param code - key input passed in from myScene
		 */
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
			default: 
				break;
			}
		}
		/**
		 * Adds text depending on the outcome of the previous game
		 * @return - Text to be added to the root
		 */
		private Text gameOutcomeText() {
			Text game_outcome;
			if (winGame) {
				game_outcome = new Text("Congrats! You defeated the boss and "
						+ "his army\n of asteroids. Go home and celebrate!");
			}
			else {
				game_outcome = new Text("Sorry. You failed to stop the asteroids"
						+ " and\nsave Earth. At least you can join Harambe\nin heaven.");
			}
			game_outcome.setFont(new Font(textSize));
			game_outcome.setX(centerX);
			game_outcome.setY(centerY);
			return game_outcome;
		}
		/**
		 * Adds text asking to quit or playing again
		 * @return - Text to be added to the root
		 */
		private Text playAgainText() {
			Text play_again = new Text("Hit Enter to try again or press Q to"
					+ " return\nto the main menu");
			play_again.setFont(new Font(textSize));
			play_again.setX(centerX);
			play_again.setY(centerY + textSize*3.5);
			return play_again;
		}
	}
}

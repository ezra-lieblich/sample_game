//This entire file is part of my masterpiece.
// Ezra Lieblich
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * This controls the Levels. It is called on by Game and also
 * interacts with spriteManager to update Sprites in the game Also
 * relies on child class gamplayInfo to recieve information about the game.
 * @author ezra
 *
 */
public class GameplayLevel {
	private SpriteManager spriteManager; 
	private GameplayInfo gameplayInfo;
	private Group Root;
	private int firstLevelEnemiesRemaining = 10;
	public static final String id = "level node";
	private Timeline firstLevel;
	private boolean skipGame;
	private boolean directHitsOnly;

	/**
	 * Creates the route and starts the game by setting up the text and sending the first wave of enemies
	 */
	public GameplayLevel() {
		Root = new Group();
		Root.setOnKeyPressed(e -> keyInput(e.getCode()));
		Root.setOnKeyReleased(e -> keyRelease(e.getCode()));
		Root.setId(id);
		spriteManager = new SpriteManager(Root);
		gameplayInfo = new GameplayInfo();
		skipGame = false;
		directHitsOnly = false;
		firstWave();
	}
	/**
	 * @return The root that the scene sets it to
	 */
	public Group getRoot() {
		return Root;
	}
	
	/**
	 * Moves all the Sprites and also checks collisions with Asteroids or Rockets depending on what
	 * level the user is on
	 * @param time
	 */
	public void step(double time) {
		spriteManager.move(time);
		checkAllCollisions();
		if (isLevelOne() && firstLevelEnemiesRemaining == 0){
			startSecondLevel();	
		}
	}
	
	/**
	 * @return returns true if user has no lives or boss is defeated
	 * 
	 */
	public boolean isGameOver() {
		return gameplayInfo.getLives() <= 0 || winGame();
	}
	/**
	 * Calls spriteManager to check key inputs to move Rocket. Also checks for cheat codes
	 * See ReadMe for specifics
	 * @param code - key input that is passed from my scene
	 */
	public void keyInput(KeyCode code) {
		spriteManager.keyInput(code);
		switch (code) {
		//cheat code to skip first level
		case S:
			firstLevel.stop();
			firstLevelEnemiesRemaining = 0;
			break;
		//cheat code to auto win and go to game over screen
		case W:
			skipGame = true;
			break;
		//cheat code to make all rockets direct hits on boss
		case A:
			directHitsOnly = true;
			break;
		default:
			break;
		}
	}
	/**
	 * Calls spriteManager to check key release to stop ship
	 * @param code - key release that is passed from my scene
	 */
	public void keyRelease(KeyCode code) {
		spriteManager.keyRelease(code);
	}
	/**
	 * @return true when boss is dead or cheat code is pressed
	 */
	public boolean winGame() {
		return spriteManager.isBossDead() || skipGame;
	}
	/**
	 * Called when gamePlayLevel is first initialized
	 * Creates a timeline that drops enemy asteroids every 3 seconds
	 */
	private void firstWave() {
		firstLevel = new Timeline(new KeyFrame(
				Duration.millis(3000), e -> spriteManager.asteroidDrop()));
		firstLevel.setCycleCount(firstLevelEnemiesRemaining);
		firstLevel.play();
	}
	/**
	 * Adds the boss to the gameplay to start second level. Also calls gameplayInfo
	 * to switch current level 
	 */
	private void startSecondLevel() {
		gameplayInfo.switchCurrentLevel();
		spriteManager.addBoss();
	}
	/**
	 * Check collisions between all Sprite objects and updates properties of the 
	 * game in GameplayInfo depending on the type of info
	 */
	private void checkAllCollisions() {
		//list of all sprites that have collided with each other or ran off screen
		ArrayList<Sprite> sprites_to_remove = new ArrayList<Sprite>();
		for (Sprite enemy : spriteManager.getAsteroids()) {
			handleEnemyAttack(sprites_to_remove, enemy);
			for (Sprite rocket : spriteManager.getRockets()) {
				removeOutOfBoundsRocket(sprites_to_remove, enemy);
				handleRocketEnemyCollision(sprites_to_remove, rocket, enemy);
			}
		}
		spriteManager.removeSprites(sprites_to_remove);
	}
	/**
	 * Checks whether the enemy is either out of bounds or colliding with the ship.
	 * Removes a life or ends game depending on if it is a boss or asteroid
	 * @param dead_sprites Passes in the list to update the sprites we need to remove
	 * @param enemy the Sprite we are checking 
	 */
	private void handleEnemyAttack(ArrayList<Sprite> dead_sprites, Sprite enemy) {
		if (spriteManager.OutOfBoundsY(enemy) ||
				spriteManager.doIntersect(enemy, spriteManager.getMyShip())){
			if (isLevelOne()) {
				dead_sprites.add(enemy);
				gameplayInfo.removeLife();
				firstLevelEnemiesRemaining--;
			}
			else {
				gameplayInfo.gameOver();
			}
		}
	}
	/**
	 * Checks if the rocket and enemy collides. Removes Sprites and also updates score accordingly
	 * @param dead_sprites
	 * @param rocket
	 * @param enemy
	 */
	private void handleRocketEnemyCollision(ArrayList<Sprite> dead_sprites, Sprite rocket, Sprite enemy) {
		if (spriteManager.doIntersect(enemy, rocket)) {
			dead_sprites.add(rocket);
			//Assume points added is normal i.e 1 point
			int points = GameplayInfo.normalHit;
			if (isLevelOne()) {
				//only remove if it is an asteroid. Boss doesn't get removed
				dead_sprites.add(enemy);
				firstLevelEnemiesRemaining--;
			}
			else {
				if (spriteManager.isDirectHit(enemy, rocket) || directHitsOnly) {
					// A direct hit is worth more points and deals more damage
					points = GameplayInfo.directHit;
					spriteManager.bossDamage(points);
				}
			}
			gameplayInfo.updateScore(points);	
		}
	}
	/**
	 * Removes rockets that are out of bounds
	 * @param dead_sprites - current list of dead Sprites
	 * @param rocket - Rocket to examine
	 */
	private void removeOutOfBoundsRocket(ArrayList<Sprite> dead_sprites, Sprite rocket) {
		if (spriteManager.OutOfBoundsY(rocket)) {
			dead_sprites.add(rocket);
		}
	}
	/**
	 * @return Checks if game is at current level
	 */
	private boolean isLevelOne() {
		return gameplayInfo.currentLevel.getText() == GameplayInfo.levelOne;
	}
	//This nested class is not part of my code masterpiece
	/**
	 * A class that is accessed by GameplayLevel and contains important information about the
	 * current game state such as the lives, level and score
	 * @author ezra
	 *
	 */
	class GameplayInfo {
		public static final String scorePrefix = "Score: ";
		public static final String livePrefix = "Lives: ";
		public static final String levelOne = "First Level";
		public static final String bossLevel = "Boss Level";
		public static final int textSize = 20;
		public static final int normalHit = 1;
		public static final int directHit = 5;
		private int Score; 	
		private Text scoreText;
		private Text liveText;
		private int lives;
		private Text currentLevel;

		/**
		 * Sets the score, current level, and lives information to text at top
		 * of screen
		 */
		public GameplayInfo() {
			currentLevel = new Text(150, textSize, "First Level");
			currentLevel.setFont(new Font(textSize));
			Score = 0;
			scoreText = new Text(300, textSize, "Score: " + Score);
			scoreText.setFont(new Font(textSize));
			lives = 3;
			liveText = new Text(0, textSize, "Lives: " + lives);
			liveText.setFont(new Font(textSize));
			Root.getChildren().add(scoreText);
			Root.getChildren().add(liveText);
			Root.getChildren().add(currentLevel);
		}
		/**
		 * Switches the current level and updates text once first level is complete
		 */
		public void switchCurrentLevel() {
			currentLevel.setText(bossLevel);
		}
		/**
		 * @return the number of lives remaining
		 */
		public int getLives() {
			return lives;
		}
		/**
		 * Removes a life and updates text. Called when asteroid falls past user or hits ship
		 */
		public void removeLife() {
			lives--;
			liveText.setText("Lives: " + lives);
		}
		/**
		 * Increases score and updates text. Called when rocket hits an enemy
		 */
		public void updateScore(int points) {
			Score += points ;
			scoreText.setText("Score: " + Score);
		}
		/**
		 * Sets the game over when the boss hits the ship or goes past the screen
		 */
		public void gameOver() {
			lives = 0;
		}
	}
}

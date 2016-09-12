import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * This controls the Levels and is where the levels are controlled. It is called on by Game and also
 * interacts with spriteManager to update Sprites in the game.
 * @author ezra
 *
 */
public class GameplayLevel {
	private SpriteManager spriteManager; 
    private Group Root;
    private int Score; 
    private Text scoreText;
    private int firstLevelEnemiesRemaining = 10;
    private Text liveText;
    private int lives;
    private Text currentLevel;
    public static final String id = "level node";
    private Timeline firstLevel;
    
    /**
     * Creates the route and starts the game by setting up the text and sending the first wave of enemies
     */
    public GameplayLevel() {
        Root = new Group();
        Root.setOnKeyPressed(e -> keyInput(e.getCode()));
 	   	Root.setOnKeyReleased(e -> keyRelease(e.getCode()));
        Root.setId(id);
        spriteManager = new SpriteManager(Root);
        Score = 0;
        lives = 3;
        setText();
        firstWave();
    }
    /**
     * @return The root that the scene sets it to
     */
   public Group getRoot() {
	   return Root;
   }
   /**
    * Helper method that inserts information about the level into text and into the top
    * right of the screen
    */
   private void setText() {
	   int text_size = 20;
	   currentLevel = new Text(150, text_size, "First Level");
       currentLevel.setFont(new Font(text_size));
       scoreText = new Text(300, text_size, "Score: " + Score);
       scoreText.setFont(new Font(text_size));
       liveText = new Text(0, text_size, "Lives: " + lives);
       liveText.setFont(new Font(text_size));
       Root.getChildren().add(scoreText);
       Root.getChildren().add(liveText);
       Root.getChildren().add(currentLevel);
   }
   /**
    * Moves all the Sprite and also checks collisions with Asteroids or Rockets Depending on what
    * level the user is on
    * @param time
    */
   public void step(double time) {
		spriteManager.move(time);
		if (currentLevel.getText() == "First Level"){
			checkCollisions();
			//check if first level is complete 
			if (firstLevelEnemiesRemaining == 0) {
				spriteManager.clearLevel();
				startSecondLevel();
			}
		}
		if (currentLevel.getText() == "Boss Level"){
			bossCollisions();
		}
	}
   /**
    * Adds the boss to the gameplay to start second level
    */
   private void startSecondLevel() {
	   currentLevel.setText("Boss Level");
	   spriteManager.addBoss();
}
/**
 * @return returns true if user has no lives or boss is defeated
 */
public boolean isGameOver() {
	return lives <= 0 || winGame();
	
}
/**
 * Calls spriteManager to check key inputs to move Rocket. Also checks for cheat codes
 * See ReadMe for specifics
 * @param code - key input that is passed from my scene
 */
public void keyInput(KeyCode code) {
	   spriteManager.keyInput(code);
		switch (code) {
		case S:
			firstLevel.stop();
			firstLevelEnemiesRemaining = 0;
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
	 * Called when gamePlayLevel is first initialized
	 * Creates a timeline that drops enemy asteroids every 3 seconds
	 */
	public void firstWave() {
		firstLevel = new Timeline(new KeyFrame(
				Duration.millis(3000), e -> spriteManager.asteroidDrop()));
		firstLevel.setCycleCount(firstLevelEnemiesRemaining);
		firstLevel.play();
	}
	/**
	 * Check collisions between all Sprite objects and updates properties of the 
	 * game
	 */
	public void checkCollisions() {
		//list of all sprites that have collided with each other or ran off screen
		ArrayList<Sprite> sprites_to_remove = new ArrayList<Sprite>();
		for (Sprite asteroid : spriteManager.getAsteroids()) {
			//lose a life if asteroid falls past you or collides with ship
			if (spriteManager.OutOfBoundsY(asteroid) ||
					spriteManager.doIntersect(asteroid, spriteManager.getMyShip())){
				sprites_to_remove.add(asteroid);
				removeLife();
				firstLevelEnemiesRemaining--;
			}
				for (Sprite rocket : spriteManager.getRockets()) {
					//remove rockets that go above screen
					if (spriteManager.OutOfBoundsY(rocket)) {
						sprites_to_remove.add(rocket);
					}
					//Score increases if rocket hits asteroid
					if (spriteManager.doIntersect(asteroid, rocket)) {
						sprites_to_remove.add(asteroid);
						sprites_to_remove.add(rocket);
						updateScore();
						firstLevelEnemiesRemaining--;
						break;
					}
				}
		}
		spriteManager.removeSprites(sprites_to_remove);
	}
	/**
	 * Check collisions between boss and Ship/Rockets and updates properties of the 
	 * game
	 */
	public void bossCollisions() {
		ArrayList<Sprite> rockets_to_remove = new ArrayList<Sprite>();
		Boss boss = spriteManager.getBoss();
		//automatically lose if boss hits ship or falls past you
		if (spriteManager.OutOfBoundsY(boss) ||
				spriteManager.doIntersect(boss, spriteManager.getMyShip())) {
				lives = 0;
			}
		for (Sprite rocket : spriteManager.getRockets()) {
			if (spriteManager.OutOfBoundsY(rocket)) {
				rockets_to_remove.add(rocket);
			}
			if (spriteManager.doIntersect(rocket, boss)) {
				updateScore();
				rockets_to_remove.add(rocket);
				spriteManager.bossDamage();
			}
		}
		spriteManager.removeSprites(rockets_to_remove);
	}
	/**
	 * Removes a life and updates text. Called when asteroid falls past user or hits ship
	 */
	private void removeLife() {
		lives--;
		liveText.setText("Lives: " + lives);
	}
	/**
	 * Increases score and updates text. Called when rocket hits an enemy
	 */
	private void updateScore() {
		Score++;
		scoreText.setText("Score: " + Score);
	}
	/**
	 * @return true when boss is dead
	 */
	public boolean winGame() {
		return spriteManager.isBossDead();
	}
}

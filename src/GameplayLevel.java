import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class GameplayLevel {
	private SpriteManager spriteManager; 
    private Group Root;
    private int Score; 
    private Text scoreText;
    private int firstLevelEnemiesRemaining = 10;
    private Text liveText;
    private int lives;
    public static final String id = "level node";
    
    public GameplayLevel() {
    	startGame();
    }
    
   public void startGame() {  
       Root = new Group();
       Root.setId(id);
       spriteManager = new SpriteManager(Root);
       Score = 0;
       scoreText = new Text(300, 20, "Score: " + Score);
       scoreText.setFont(new Font(20));
       lives = 3;
       liveText = new Text(0, 20, "Lives: " + lives);
       liveText.setFont(new Font(20));
       Root.getChildren().add(scoreText);
       Root.getChildren().add(liveText);
       firstWave();
   }
   public Group getRoot() {
	   return Root;
   }
   
   public void step(double time) {
		spriteManager.move(time);
		spriteManager.checkCollisions();
	}
   
   public void keyInput(KeyCode code) {
	   spriteManager.keyInput(code);
	}
	
	public void keyRelease(KeyCode code) {
		spriteManager.keyRelease(code);
	}
	
	public void firstWave() {
		Timeline firstLevel = new Timeline(new KeyFrame(
				Duration.millis(3000), e -> spriteManager.asteroidDrop()));
		firstLevel.setCycleCount(firstLevelEnemiesRemaining);
		firstLevel.play();
	}
}

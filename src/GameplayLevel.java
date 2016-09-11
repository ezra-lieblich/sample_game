import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.Button;
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
    private Text currentLevel;
    public static final String id = "level node";
    private Timeline firstLevel;
    
    public GameplayLevel() {
        Root = new Group();
    	startGame();
    }
    
   public void startGame() {  
       //Root = new Group();
	   Root.setOnKeyPressed(e -> keyInput(e.getCode()));
	   Root.setOnKeyReleased(e -> keyRelease(e.getCode()));
       Root.setId(id);
       spriteManager = new SpriteManager(Root);
       currentLevel = new Text(150, 20, "First Level");
       currentLevel.setFont(new Font(20));
       Score = 0;
       scoreText = new Text(300, 20, "Score: " + Score);
       scoreText.setFont(new Font(20));
       lives = 1;
       liveText = new Text(0, 20, "Lives: " + lives);
       liveText.setFont(new Font(20));
       Root.getChildren().add(scoreText);
       Root.getChildren().add(liveText);
       Root.getChildren().add(currentLevel);
       firstWave();
   }
   public Group getRoot() {
	   return Root;
   }
   
   public void step(double time) {
		spriteManager.move(time);
		checkCollisions();
		if (firstLevelEnemiesRemaining == 0 && currentLevel.getText() == "First Level") {
			spriteManager.clearLevel();
			startSecondLevel();
		}
		if (currentLevel.getText() == "Boss Level"){
			bossCollisions();
		}
	}
   
   public void startSecondLevel() {
	// TODO Auto-generated method stub
	   currentLevel.setText("Boss Level");
	   spriteManager.addBoss();
}

public void gameOverMenu() {
	   firstLevel.stop();
	   Root.getChildren().clear();   
	   Button button = new Button("Restart Game");
	   Root.getChildren().add(button);
	   button.setLayoutX(Main.SIZEX/2 - 50);
	   button.setLayoutY(Main.SIZEY/2);
	   button.setOnKeyPressed(e -> restartGame(e.getCode()));
	   button.setOnMousePressed(e -> {
		   System.out.println("button clicked");
	   });
}

   public void restartGame(KeyCode code) {
	   switch (code) {
       case H:
    	   System.out.println("clicked");
	   		startGame();
	   	break;
	   }
   }
public boolean isGameOver() {
	return lives <= 0 || winGame();
	
}

public void keyInput(KeyCode code) {
	   spriteManager.keyInput(code);
		switch (code) {
		case S:
			firstLevel.stop();
			firstLevelEnemiesRemaining = 0;
		}
	}
	
	public void keyRelease(KeyCode code) {
		spriteManager.keyRelease(code);
	}
	
	public void firstWave() {
		firstLevel = new Timeline(new KeyFrame(
				Duration.millis(3000), e -> spriteManager.asteroidDrop()));
		firstLevel.setCycleCount(firstLevelEnemiesRemaining);
		firstLevel.play();
	}
	
	public void checkCollisions() {
		ArrayList<Sprite> sprites_to_remove = new ArrayList<Sprite>();
		for (Sprite asteroid : spriteManager.getAsteroids()) {
			if (spriteManager.OutOfBoundsY(asteroid) ||
					spriteManager.doIntersect(asteroid, spriteManager.getMyShip())){
				sprites_to_remove.add(asteroid);
				removeLife();
				firstLevelEnemiesRemaining--;
			}
				for (Sprite rocket : spriteManager.getRockets()) {
					if (spriteManager.OutOfBoundsY(rocket)) {
						sprites_to_remove.add(rocket);
					}
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
	public void bossCollisions() {
		ArrayList<Sprite> rockets_to_remove = new ArrayList<Sprite>();
		Boss boss = spriteManager.getBoss();
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
	private void removeLife() {
		lives--;
		liveText.setText("Lives: " + lives);
	}
	private void updateScore() {
		Score++;
		scoreText.setText("Score: " + Score);
	}
	public boolean winGame() {
		return spriteManager.isBossDead();
	}
}

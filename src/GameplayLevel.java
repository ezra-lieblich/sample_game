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
			startSecondLevel();
		}
		if (isGameOver()) {
			gameOverMenu();
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
	return lives <= 0;
	
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
		ArrayList<Asteroid> asteroids_to_remove = new ArrayList<Asteroid>();
		ArrayList<Rocket> rockets_to_remove = new ArrayList<Rocket>();
		for (Asteroid asteroid : spriteManager.getAsteroids()) {
			if (spriteManager.OutOfBoundsY(asteroid) ||
					spriteManager.doIntersect(asteroid, spriteManager.getMyShip())){
				asteroids_to_remove.add(asteroid);
				removeLife(asteroid);
				firstLevelEnemiesRemaining--;
			}
				for (Rocket rocket : spriteManager.getRockets()) {
					if (spriteManager.OutOfBoundsY(rocket)) {
						Root.getChildren().remove(rocket.getNode());
						rockets_to_remove.add(rocket);
					}
					if (spriteManager.doIntersect(asteroid, rocket)) {
						asteroids_to_remove.add(asteroid);
						rockets_to_remove.add(rocket);
						updateScore(asteroid, rocket);
						firstLevelEnemiesRemaining--;
						break;
					}
				}
		}
		spriteManager.removeSprites(asteroids_to_remove, rockets_to_remove);
	}
	private void removeLife(Asteroid asteroid) {
		lives--;
		liveText.setText("Lives: " + lives);
	}
	private void updateScore(Asteroid asteroid, Rocket rocket) {
		Score++;
		scoreText.setText("Score: " + Score);
	}
}

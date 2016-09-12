import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * The main program, it creates an animated scene.
 * 
 * @author ezra, Robert Duvall
 */
public class Main extends Application {
	public static final int SIZEX = 400;
	public static final int SIZEY = 600;
	public static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

	private Game myGame;
	/**
	 * Set things up at the beginning. It creates a game and then initializes a scene and starts the
	 * games loop
	 */
	@Override
	public void start (Stage s) {
		myGame = new Game();
		s.setTitle(myGame.getTitle());
		Scene scene = myGame.init(SIZEX, SIZEY);
		s.setScene(scene);
		s.show();
		// sets the game's loop and calls the step method to move events
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
				e -> myGame.step(SECOND_DELAY));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	/**
	 * Start the program.
	 */
	public static void main (String[] args) {
		launch(args);
	}
}

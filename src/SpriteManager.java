import java.util.ArrayList;


import javafx.scene.Group;
import javafx.scene.input.KeyCode;
/**
 * SpriteManager is called by gamePlayLevel and gets, sets, adds, and removes Sprites
 * throughout the level. It also gathers information from the Sprites and passes it back
 * to the gamePlayLevel class.
 * @author ezra
 *
 */
public class SpriteManager {
    private Group Root;
    private Ship myShip;
	private ArrayList<Sprite> Rockets = new ArrayList<Sprite>();
    private ArrayList<Sprite> Asteroids = new ArrayList<Sprite>();
    private Boss Boss;
    
    /**
     * Sets up Ship and sets Root to instance variable
     * @param root - Need Root to be instance variable to add and remove 
     * Sprites from root
     */
	public SpriteManager(Group root) {
		Root = root;
		myShip = new Ship();
	    Root.getChildren().add(myShip.getNode());
	}
	/**
	 * @return - The Boss Sprite
	 */
	 public Boss getBoss() {
		 return Boss;
	 }
	 /**
	  * @return - The Ship Sprite
	  */
	 public Ship getMyShip() {
			return myShip;
	}
	 /**
	  * @return - The list of Rocket Sprites currently in the scene
	  */
	public ArrayList<Sprite> getRockets() {
		return Rockets;
	}
	/**
	 * @return - The list of Asteroid Sprites currently in the scene
	 */
	public ArrayList<Sprite> getAsteroids() {
		return Asteroids;
	}
	/**
	 * Adds boss to scene when the first level is over in gamePlayLevel
	 */
	public void addBoss() {
		Boss = new Boss();
		Root.getChildren().add(Boss.getNode());
	}
	/**
	 * Calls each Sprite's move method and updates the position of all Sprites
	 * @param time - Time passed in the game loop
	 */
	public void move(double time) {
		myShip.move(time);
		for (Sprite rocket: Rockets) {
			rocket.move(time);
		}
		for (Sprite asteroid: Asteroids) {
			asteroid.move(time);
		}
		//Boss isn't set until after first level so null check prevents null pointer
		if (Boss != null) Boss.move(time);
	}
	/**
	 * Moves the ship right or left depending on what key input is pressed
	 * @param code - key input that was passed from the scene
	 */
	public void keyInput(KeyCode code) {
		switch (code) {
        case RIGHT:
        	myShip.moveRight();
            break;
        case LEFT:
        	myShip.moveLeft();
            break;
        default:
        	break;
		}
	}
	/**
	 * Stops the ship once the right or left key is released
	 * Releasing space bar fires a rocket
	 * @param code - key that was released from the scene
	 */
	public void keyRelease(KeyCode code) {
		switch (code) {
        case RIGHT:
        	myShip.stop();
            break;
        case LEFT:
        	myShip.stop();
            break;
        case SPACE:
            fireRocket();
            break;	
        default:
        	break;
		}
	}
	
	/**
	 * Called from the firstLevel timeline. Drops a new enemy onto scene
	 */
	public void asteroidDrop() {	
		Asteroid new_enemy = new Asteroid();
		addProjectile(new_enemy);
	}
	/**
	 * Checks to see if two Sprites intersect each other. The following conditions
	 * are all cases where the two Sprites would not intersect
	 * @param obj1 - First sprite to be compared
	 * @param obj2 - Second sprite to be compared
	 * @return - True if they intersect and false if they don't
	 */
	public boolean doIntersect(Sprite obj1, Sprite obj2) {
		return !(obj1.getX() > obj2.getX() + obj2.getXSize() || 
				obj1.getX() + obj1.getXSize() < obj2.getX() || 
				obj1.getY() + obj1.getYSize() < obj2.getY() || 
				obj1.getY() > obj2.getY() + obj2.getYSize()); 
	}
	/**
	 * Checks if a sprite is vertically outside the games screen. Called by gamePlayLevel
	 * @param sprite - Sprite that is check to be outside of bounds
	 * @return - True if Sprite is above or below the screen false otherwise
	 */
	public boolean OutOfBoundsY(Sprite sprite) {
		return sprite.getY() > Main.SIZEY || (sprite.getY() + sprite.getYSize()) < 0;
	}
	/**
	 * Removes Sprites that have collided when gamePlayLevel checks for collisions
	 * Also checks what class it is to remove it from proper List of Sprites
	 * @param dead_sprites -  Sprites that need to be removed
	 */
	public void removeSprites(ArrayList<Sprite> dead_sprites) {
		for (Sprite sprite : dead_sprites) {
			Root.getChildren().remove(sprite.getNode());
			//Check to see what type of Sprite it is to remove from proper list
			if (sprite.getClass().equals(Rocket.class)) {
				Rockets.remove(sprite);
			}
			else{				
				Asteroids.remove(sprite);
			}
		}
	}
	/**
	 * Called when a rocket collides with boss and decreases bosses health
	 */
	public void bossDamage() {
		Boss.decreaseHealth();
	}
	/**
	 * Clears the scene when the first level is complete
	 */
	public void clearLevel() {
		for (Sprite rocket : Rockets) {
			Root.getChildren().remove(rocket.getNode());
		}
		Rockets.clear();
		for (Sprite asteroid : Asteroids) {
			Root.getChildren().remove(asteroid.getNode());
		}
		Asteroids.clear();
	}
	/**
	 * @return - Checks if the boss is dead but makes sure that it is not null
	 */
	public boolean isBossDead() {
		return Boss != null && Boss.isDead();
	}
	/**
	 * Fires rocket when space bar is pressed
	 */
	private void fireRocket() {
    	Rocket new_rocket = new Rocket(myShip);
        addProjectile(new_rocket);
    }
	/**
	 * Adds either a rocket or asteroid projectile and adds it to corresponding list
	 * @param sprite - Rocket or Asteroid that is to be added to Root/list
	 */
	private void addProjectile(Sprite sprite)  {
		Root.getChildren().add(sprite.getNode());
		if (sprite.getClass().equals(Rocket.class)) Rockets.add(sprite);
		else Asteroids.add(sprite);
	}
}

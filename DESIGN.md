# DESIGN.MD

	The overarching design of the code is main sets up and	initializes a scene. Then we start and go 
	into Game class. Game's scenes is originally set to LaunchScreen class. Then there are conditions 
	in LaunchScreen that switch the root to GameplayLevel class which is where the game is played. 
	GameplayLevel interacts with SpriteManager heavily and SpriteManager adds and removes Sprite objects 
	such as Rocket, Asteroid, Boss, or Ship. To add a new level, in GameplayLevel call the clearLevel method 
	which is a part of SpriteManager. Then back in GameplayLevel, add a Timeline and add enemies. 
	Also, you probably need to create a new enemy class that extends Sprite.
	
	The overall design goal was to make it very simple to
	add objects into the game since interacting with characters
	was a main feature of the project. That is why I chose to have
	a Sprite class that objects extend. The trade off is that everyone 
	has to have those Sprite methods. I also wanted my design to 
	be easy to transition between levels since you need two levels
	in the project. The trade off is it was easy to have two levels,
	but it is hard and not flexible to add more levels than that.
	Finally, I had subclasses as my design for screens because it was
	easy to interact with Game and its myScene to set the root. 
	While this makes it simpler, it isn't necessarily the best looking
	design approach. 

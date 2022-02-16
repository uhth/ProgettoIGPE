package application.controller.game;

import javafx.animation.AnimationTimer;

public class GameCycle extends AnimationTimer
{
	    
	GameController gameController;
	private long lastNow = 0;
	
	public GameCycle( GameController gameController ) {
		this.gameController = gameController;
		this.start();
	}
	
	
	@Override
	public void handle( long now ) {
		gameController.update();
		lastNow = now;

	}
	
	public long getLastNow() {
		return lastNow;
	}
	
	public long elapsedTimeFrom( long last ) {
		return lastNow - last;
	}
	

}

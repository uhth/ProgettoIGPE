package application.controller.game;

import application.controller.audio.AudioController;
import application.controller.scene.GameSceneController;
import application.gamestate.GameState;
import application.utils.BetterTimer;
import application.view.graphics.GameGraphics;
import application.view.sprite.static_backgrounds.BackgroundSpriteRotation;

public class GameController {
	
	//controllers
	private AudioController audioController;
	private GameSceneController gameSceneController;
	
	//game stuff
	private GameState game;
	private GameGraphics graphics;
	private GameCycle cycle;
	private BetterTimer gTimer;
	private BetterTimer goTimer;
	private BetterTimer iTimer;
	boolean playing;
	private long lastClickTime = 0;
	
	//easy to modify stuff
	private final long MULTIPLIER_WAIT_TIME = 900_000_000;
			
	
	public GameController( GameSceneController gameSceneController ) {
		this.game = new GameState();
		this.audioController = AudioController.getInstance();
		this.gameSceneController = gameSceneController;		
		
		initGame();
	}

	
	//get and print bg + create and start game cycle
	public void start() {
		graphics.setBackgroundSprite( BackgroundSpriteRotation.getRandomBackground() );
		this.cycle = new GameCycle( this ); //autoplay
	}
	
	//stop game cycle
	public void stop() {
		this.cycle.stop();
	}
	
	//everything in here will be executed once per frame ( by cycle obj )
	public void update() {
		
		if( iTimer.isRunning() ) {
			gameSceneController.getInitialTimerLabel().setText( String.valueOf( iTimer.getState() + 1 ) );
			iTimer.update();
		}
		
		
		//hide initial timer
		else if( !iTimer.isRunning() && gameSceneController.getInitialTimerLabel().isVisible() ) {
			gameSceneController.getInitialTimerLabel().setVisible( false );
			gTimer.start();
		}
		
		//game stuff
		if( game != null ) {
			
			if( playing ) {
				gameSceneController.getGameTimerLabel().setText( "Time: " + String.valueOf( game.getTimeLeft() ) );
				gameSceneController.getScoreLabel().setText( "Score: " + String.valueOf( game.getScore() ) );
				gameSceneController.getMultiplierNLabel().setText( game.getScoreMultiplier() + "X" );
								
				gTimer.update();
				
				if( gTimer.getState() == 0 ) playing = false;
								
				game.setTimeLeft( gTimer.getState() );
				
				
				if( cycle.getLastNow() - lastClickTime > MULTIPLIER_WAIT_TIME ) //nano seconds
					game.resetScoreMultiplier();			
			}
			game.updateTilesTable();
		}
		
		//tile printing
		if( graphics != null )
			graphics.draw( game.getTilesTable() );
		
		//short pause + game over
		if( !playing ) {
			gameSceneController.blurGameScene( 20.0 );
			goTimer.update();
			if( !goTimer.isRunning() ) {
				goTimer.start();
			}
			if( goTimer.getState() == 0 ) gameSceneController.showGameOver();
		}
		
	}
	
	//tile pop, score multiplier, score and pop effect handling
	public void mouseClick( double y, double x ) {
		if( !playing ) return;
		game.getTilesTable().getTileHitboxMap().forEach( ( k, v ) -> {
			if( v.contains( x, y ) )
				if( game.addScore( game.getTilesTable().popTile( k ) * game.getScoreMultiplier() ) ) {
					game.increaseScoreMultiplier();
					lastClickTime = cycle.getLastNow();
					audioController.playPopSoundEffect( game.getScoreMultiplier() - 1 );
				}
		});
	}
	
	//tile hover effect handling
	public void mouseHover( double y, double x ) {
		if( !playing ) return;
		game.getTilesTable().getTileHitboxMap().forEach( ( k, v ) -> {
			if( v.contains( x, y ) )
				game.getTilesTable().hlTile( k );
		});
	}
	
	//called once every new game
	public void initGame() {
		game.reset();
		playing = true;
		this.iTimer = new BetterTimer( BetterTimer.seconds( 3 ) );
		this.gTimer = new BetterTimer( BetterTimer.seconds( 60 ) );
		this.goTimer = new BetterTimer( BetterTimer.seconds( 1 ) );
		iTimer.start();
		game.setScore( 0 );
		game.setTimeLeft( gTimer.getState() );
	}
	
	public int getScore() {
		return game.getScore();
	}
	
	public void setGraphics( GameGraphics graphics ) { this.graphics = graphics; }
	public void setGameState( GameState game ) { this.game = game; }
	
}

package application.gamestate;

import application.entity.TilesTable;
import application.view.Settings;

public class GameState 
{
	
	private final int ROWS = 10;
	private final int COLS = 15;
	
	private int score = 0;
	private long timeLeft = 0;
	
	private int scoreMultiplier = 1;
	
	//main tile table
	TilesTable table;

	public GameState() {}
	
	public void updateTilesTable() {
		table.update();
	}

	public TilesTable getTilesTable() {
		return table;
	}
	
	public void reset() {
		double initialX = ( Settings.WINDOW_W - ( COLS * Settings.TILE_LENGHT ) ) / 2;
		double initialY = ( Settings.WINDOW_H - ( ROWS * Settings.TILE_LENGHT ) ) / 1.5;
		table = new TilesTable( ROWS, COLS, initialY, initialX );
	}
	
	public int getScoreMultiplier() {
		return scoreMultiplier;
	}
	
	public void increaseScoreMultiplier() {
		if( scoreMultiplier >= 5 ) return;
			scoreMultiplier++;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public boolean addScore( int score ) {
		if( score > 0 ) {
			this.score += score;
			return true;
		}
		return false;
	}
	
	public int getScore() {
		return score;
	}
	
	public long getTimeLeft() {
		return timeLeft;
	}
	
	public void setTimeLeft( long l ) {
		this.timeLeft = l ;
	}
	
	public void decreaseTimeLeft() {
		this.timeLeft--;
	}
	
	public void resetScoreMultiplier() {
		this.scoreMultiplier = 1;
	}
	
}

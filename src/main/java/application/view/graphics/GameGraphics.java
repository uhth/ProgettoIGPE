package application.view.graphics;

import application.entity.TileEntity;
import application.entity.TilesTable;
import application.view.Settings;
import application.view.sprite.Sprite;
import javafx.scene.canvas.GraphicsContext;

public class GameGraphics {
	 
	GraphicsContext graphics;
	Sprite bgSprite;
	
	public GameGraphics( GraphicsContext graphics ) {
		this.graphics = graphics;
	}	
	
	public void draw( TilesTable table ) {
		//clear
		graphics.clearRect( 0, 0, Settings.WINDOW_W, Settings.WINDOW_H );
		
		//draws bg
		if( bgSprite != null )
			graphics.drawImage( bgSprite.getSpriteAt( 0 ), 0, 0, bgSprite.getSpriteAt( 0 ).getWidth(), bgSprite.getSpriteAt( 0 ).getHeight() );
				
		//draws table
		for( int y = 0; y < table.getNumRows(); y++ )
			for( int x = 0; x < table.getNumCols(); x++ ) {
				TileEntity tile = table.at( y, x );
				if( tile == null ) continue;
				graphics.drawImage( tile.getFrame(), tile.getPos().getX(), tile.getPos().getY() );
			}
	}
	
	
	public void setBackgroundSprite( Sprite bgSprite ) {
		this.bgSprite = bgSprite;
	}
	
	
}

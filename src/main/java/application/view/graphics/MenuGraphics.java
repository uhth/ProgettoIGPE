package application.view.graphics;

import application.view.Settings;
import application.view.sprite.Sprite;
import javafx.scene.canvas.GraphicsContext;

public class MenuGraphics {
		 
	GraphicsContext graphics;
	Sprite bgSprite;
	
	public MenuGraphics( GraphicsContext graphics ) {
		this.graphics = graphics;
	}	
	
	public void draw () {
		//clear
		graphics.clearRect( 0, 0, Settings.WINDOW_W, Settings.WINDOW_H );
		
		//draws bg
		if( bgSprite != null )
			graphics.drawImage( bgSprite.getSpriteAt( 0 ), 0, 0, bgSprite.getSpriteAt( 0 ).getWidth(), bgSprite.getSpriteAt( 0 ).getHeight() );
					
	}
		
	
	public void setBackgroundSprite( Sprite bgSprite ) {
		this.bgSprite = bgSprite;
	}
	
	
}

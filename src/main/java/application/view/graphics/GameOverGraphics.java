package application.view.graphics;

import application.view.sprite.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameOverGraphics {
	
	GraphicsContext graphics;
	Sprite bgSprite = null;
	
	public GameOverGraphics( GraphicsContext graphics ) {
		this.graphics = graphics;
		graphics.setFill( Color.BLACK );
	}

	
	public void draw() {
		//clear		
		graphics.fillRect( 0, 0, graphics.getCanvas().getWidth(), graphics.getCanvas().getHeight() );
		
		//draws bg
		if( bgSprite != null )
			graphics.drawImage( bgSprite.getSpriteAt( 0 ), 0, 0, bgSprite.getSpriteAt( 0 ).getWidth(), bgSprite.getSpriteAt( 0 ).getHeight() );
					
	}
		
	
	public void setBackgroundSprite( Sprite bgSprite ) {
		this.bgSprite = bgSprite;
	}
	
}

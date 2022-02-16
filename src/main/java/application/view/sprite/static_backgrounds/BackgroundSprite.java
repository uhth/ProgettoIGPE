package application.view.sprite.static_backgrounds;

import application.view.sprite.Sprite;
import javafx.scene.image.Image;

public class BackgroundSprite extends Sprite
{
	
	public BackgroundSprite( String PATH ) {
		super( new Image( BackgroundSprite.class.getResourceAsStream( PATH ) ), 1280, 720, 1, 1 );
	}
		
}


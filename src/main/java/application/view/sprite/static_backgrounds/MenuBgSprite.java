package application.view.sprite.static_backgrounds;

import application.view.sprite.Sprite;
import javafx.scene.image.Image;

public class MenuBgSprite extends Sprite {

	
	private static String PATH = "/application/sprites/backgrounds/MetropolitanCity/MetropolitanCity_1280x720.png";
	private static MenuBgSprite instance;
	
	private MenuBgSprite( Image spriteSheet ) {
		super( spriteSheet, 1280, 720, 1, 1 );

	}
	
	public static MenuBgSprite getInstance() {
		if( instance == null )
			instance = new MenuBgSprite( new Image( BackgroundSprite.class.getResourceAsStream( PATH ) ) );
		return instance;
	}
	
}

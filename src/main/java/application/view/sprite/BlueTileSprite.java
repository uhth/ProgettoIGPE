package application.view.sprite;

import application.view.animation.Animation;
import application.view.animation.AnimationTimings;
import javafx.scene.image.Image;

public class BlueTileSprite extends TileSprite
{
	private static BlueTileSprite instance;
	private static final String PATH = "/application/sprites/blue_tile_spriteV2.png";
	
	protected BlueTileSprite() {
		super( new Image( BlueTileSprite.class.getResourceAsStream( PATH ) ) );
		initAnimations();
	}
	
	public static TileSprite getInstance() {
		if( instance == null )
			instance = new BlueTileSprite();
		return instance;
	}
	
	private void initAnimations() {
		animations = new Animation[ 3 ];
		animations[ 0 ] = Animation.createAnimation( 1, getSpriteArray(), 0, AnimationTimings.IDLE_ANIMATION_DELAY );
		animations[ 1 ] = Animation.createAnimation( 3, getSpriteArray(), 0, AnimationTimings.HL_ANIMATION_DELAY );
		animations[ 2 ] = Animation.createAnimation( 2, getSpriteArray(), 3, AnimationTimings.POP_ANIMATION_DELAY );
	}
	
}

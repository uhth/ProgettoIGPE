package application.view.sprite;

import application.view.animation.Animation;
import application.view.animation.AnimationTimings;
import javafx.scene.image.Image;

public class GreenTileSprite extends TileSprite
{
	private static GreenTileSprite instance;
	private static final String PATH = "/application/sprites/green_tile_spriteV2.png";
	
	protected GreenTileSprite() {
		super( new Image( GreenTileSprite.class.getResourceAsStream( PATH ) ) );
		 initAnimations();
	}
	
	public static TileSprite getInstance() {
		if( instance == null )
			instance = new GreenTileSprite();
		return instance;
	}
	
	private void initAnimations() {
		animations = new Animation[ 3 ];
		animations[ 0 ] = Animation.createAnimation( 1, getSpriteArray(), 0, AnimationTimings.IDLE_ANIMATION_DELAY );
		animations[ 1 ] = Animation.createAnimation( 3, getSpriteArray(), 0, AnimationTimings.HL_ANIMATION_DELAY );
		animations[ 2 ] = Animation.createAnimation( 2, getSpriteArray(), 3, AnimationTimings.POP_ANIMATION_DELAY );
	}

}

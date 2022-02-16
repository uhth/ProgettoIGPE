package application.view.sprite;

import application.view.animation.Animation;
import application.view.animation.AnimationTimings;
import javafx.scene.image.Image;

public class YellowTileSprite extends TileSprite
{
	private static YellowTileSprite instance;
	private static final String PATH = "/application/sprites/yellow_tile_spriteV2.png";
	
	protected YellowTileSprite() {
		super( new Image( YellowTileSprite.class.getResourceAsStream( PATH ) ) );
		initAnimations();
	}
	
	public static TileSprite getInstance() {
		if( instance == null )
			instance = new YellowTileSprite();
		return instance;
	}
	
	
	private void initAnimations() {
		animations = new Animation[ 3 ];
		animations[ 0 ] = Animation.createAnimation( 1, getSpriteArray(), 0, AnimationTimings.IDLE_ANIMATION_DELAY );
		animations[ 1 ] = Animation.createAnimation( 3, getSpriteArray(), 0, AnimationTimings.HL_ANIMATION_DELAY );
		animations[ 2 ] = Animation.createAnimation( 2, getSpriteArray(), 3, AnimationTimings.POP_ANIMATION_DELAY );
	}

}

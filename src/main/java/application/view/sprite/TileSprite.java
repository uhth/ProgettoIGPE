package application.view.sprite;

import application.view.Settings;
import javafx.scene.image.Image;

public class TileSprite extends Sprite
{		
	
	protected TileSprite( Image spriteSheet ) {
		super( spriteSheet, Settings.TILE_SPRITE_SIZE, Settings.TILE_SPRITE_SIZE, Settings.TILE_SPRITE_ZOOM, Settings.TILE_SPRITE_ZOOM );
	}
		
}

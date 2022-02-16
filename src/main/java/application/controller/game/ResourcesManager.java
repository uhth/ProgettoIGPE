package application.controller.game;

import application.view.sprite.BlueTileSprite;
import application.view.sprite.GreenTileSprite;
import application.view.sprite.RedTileSprite;
import application.view.sprite.YellowTileSprite;
import application.view.sprite.static_backgrounds.BackgroundSpriteRotation;

public class ResourcesManager {
	
	
	/*
	 * THIS IS JUST A UTILITY CLASS TO PRE-LOAD SPRITES
	 * INSTEAD OF LOADING THEM WHEN THE GAME IS ALREADY RUNNING
	 * TO AVOID FREEZES
	 */

	public static void loadResoruces() {
		
		//backgrounds
		BackgroundSpriteRotation.setBackgroundNumber( 19 );
		BackgroundSpriteRotation.getInstance();
		
		//tiles
		BlueTileSprite.getInstance();
		RedTileSprite.getInstance();
		YellowTileSprite.getInstance();
		GreenTileSprite.getInstance();
		
	}
	
}

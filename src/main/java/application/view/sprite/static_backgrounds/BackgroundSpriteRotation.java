package application.view.sprite.static_backgrounds;

import java.util.Random;

public class BackgroundSpriteRotation
{
	//loading backgrounds is pretty slow
	
	private static BackgroundSpriteRotation instance;
	private static BackgroundSprite[] bgs;
	private String path = "/application/sprites/backgrounds/UnknownNights/bg_";
	private static Random random = new Random();
	private static int numBGs = 19;
	
	private BackgroundSpriteRotation() {
		
		bgs = new BackgroundSprite[ numBGs ];
		for( int i = 0; i < numBGs; i++ ) {
			bgs[ i ] = new BackgroundSprite( path + ( i + 1 ) + ".png" );
		}
	}
	
	public static BackgroundSpriteRotation getInstance() {
		if( instance == null )
			instance = new BackgroundSpriteRotation();
		return instance;
	}
	
	public static void reload() {
		instance = new BackgroundSpriteRotation();
	}
	
	public static BackgroundSprite getRandomBackground() {
		if( instance == null )
			instance = new BackgroundSpriteRotation();
		return bgs[ random.nextInt( numBGs ) ];
	}
	
	public static void setBackgroundNumber( int n ) {
		if( n <= 0 || n > 19 ) return;
			numBGs = n;
	}
	
}

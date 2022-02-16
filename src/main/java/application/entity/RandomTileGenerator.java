package application.entity;

import java.util.Random;

import application.view.Settings;

public class RandomTileGenerator {
	
	/*
	 * GENERATES RANDOM TILE ENTITIES
	 */
	
	private static Random random = new Random();
	
	public static TileEntity genTile( double xCoord, int col, double yCoord ) {
		double x = ( ( col * Settings.TILE_LENGHT ) + xCoord );
		switch ( random.nextInt( 4 ) ) {
			case 1:
				return TileEntity.build( TileEntity.YELLOW_TILE_ENTITY, yCoord, x );
			case 2:
				return TileEntity.build( TileEntity.BLUE_TILE_ENTITY, yCoord, x );
			case 3:
				return TileEntity.build( TileEntity.GREEN_TILE_ENTITY, yCoord, x );
		default:
			return TileEntity.build( TileEntity.RED_TILE_ENTITY, yCoord, x );
		}
	}

}

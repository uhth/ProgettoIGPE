package application.entity;

import java.util.ArrayList;
import java.util.HashMap;

import application.view.Settings;
import javafx.geometry.Rectangle2D;

public class TilesTable {
	
	/*
	 * CORE OF THE GAME, THIS TABLE HAS METHODS
	 * FOR EVERYTHING ABOUT TILE BEHAVIOUR AND MANAGEMENT
	 */
	
	private final int LEFT = 0;
	private final int UP = 1;
	private final int RIGHT = 2;
	private final int DOWN = 3;
	
	private TileEntity[][] table;
	private TileEntity hlTile;
	private int numCols;
	private int numRows;
	private double xCoord;
	private double yCoord;
	
	private HashMap<TileEntity, Rectangle2D> tileHitboxMap;
	
	public TilesTable( int rows, int cols, double y, double x ) {
		tileHitboxMap = new HashMap<TileEntity, Rectangle2D>();
		xCoord = x;
		yCoord = y;
		numCols = cols;
		numRows = rows;
		table = new TileEntity[ rows ][ cols ];		
	}
	
	public TileEntity at( int y, int x ) {
		return table[ y ][ x ];
	}
	
	public TileEntity[][] getTilesTable() {
		return table;
	}
	
	public int getNumCols() {
		return numCols;
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	//Finds the tiles of the same color and adds them to the list, also recursively it calls itself on the closes tiles
	private void lookForSCTiles( int y, int x, TileEntity.COLOR color, ArrayList<TileEntity> list, int fromDir ) {
		
		if( y < 0 || y >= numRows || x < 0 || x >= numCols ) return; //base case 0
		if( table[ y ][ x ] == null || table[ y ][ x ].isPopped() ) return; //base case 1 
		if( table[ y ][ x ].getColor() != color ) return; //base case 2
		if( list.contains( table[ y ][ x ] ) ) return; //base case 3
		
		list.add( table[ y ][ x ] ); //adding current tile
		
		//LEFT
		lookForSCTiles( y, x - 1, color, list, RIGHT );
		//UP
		lookForSCTiles( y - 1, x, color, list, DOWN );
		//RIGHT
		lookForSCTiles( y, x + 1, color, list, LEFT );
		//DOWN
		lookForSCTiles( y + 1, x, color, list, UP );	

	}
	
	//Given a tile to start from tries to pop
	public int popTile( TileEntity tile ) {
		ArrayList<TileEntity> scTiles = new ArrayList<TileEntity>();
		TileEntity.COLOR color = tile.getColor();
		int coords[] = findTile( tile );
		if( coords[ 0 ] == -1 && coords[ 1 ] == -1 ) return 0;		
		
		int y = coords[ 0 ];
		int x = coords[ 1 ];
				
		lookForSCTiles( y, x, color, scTiles, -1 );
		
		if( scTiles.size() >= 3 ) {
			scTiles.forEach( ( t ) -> {
				t.pop();
				
			});
			return scTiles.size(); }
		
		return 0;
		
	}
	
	//returns tile coords INSIDE THE TABLE
	private int[] findTile( TileEntity tile ) {
		int[] coords = new int[ 2 ];
		coords[ 0 ] = -1;
		coords[ 1 ] = -1;
		
		for( int y = 0; y < numRows; y++ )
			for( int x = 0; x < numCols; x++ )
				if( tile.equals( table[ y ][ x ] ) ) {
					coords[ 0 ] = y;
					coords[ 1 ] = x;
				}
		
		return coords;
	}
	
	//tries push a tile down of 1 position
	private void pushTileDown( int y, int x ) {
		if( y + 1 >= numRows ) return;
		table[ y + 1 ][ x ] = table[ y ][ x ];
		table[ y ][ x ] = null;
	}
	
	
	//called once per frame, updates the table and calls update on every cell + updates cell's hitbox ( also handles fall and pop )
	public void update() {
		
		tileHitboxMap.clear();
		
		for( int x = 0; x < numCols; x++ )
			pushTile( x );
		
		for( int y = 0; y < numRows; y++ )
			for( int x = 0; x < numCols; x++ ) {
				if( table[ y ][ x ] == null ) continue;
				if( table[ y ][ x ].isPopped() && !table[ y ][ x ].getCurrentAnimation().isPlaying() ) { 
					table[ y ][ x ] = null;
					continue;
				}
				table[ y ][ x ].update();
				tileHitboxMap.put( table[ y ][ x ], table[ y ][ x ].getHitbox() );
				if( y + 1 >= numRows ) continue;
				if( table[ y + 1 ][ x ] == null ) {
					if( !table[ y ][ x ].isFalling() )
						table[ y ][ x ].fall();
					else if( table[ y ][ x ].hasFallen() ) {
						table[ y ][ x ].land(); 
						pushTileDown( y, x );
					}
				}
			}
	}
	
	//highlights tile when mouse's hovering
	public void hlTile( TileEntity tile ) {
		if( tile == null || tile == hlTile ) return;
		if( tile.isPopped() ) return;
		if( hlTile != null && !hlTile.isPopped() )
			hlTile.playAnimationOnce( hlTile.getIdleAnimation(), 0 );
		hlTile = tile;
		hlTile.playAnimationOnce( hlTile.getHLAnimation(), 0 );
	}
	
	//creates and puts a new tile on top of given col if there's room
	public void pushTile( int col ) {
		if( table[ 0 ][ col ] == null )
			table[ 0 ][ col ] = RandomTileGenerator.genTile( xCoord, col, yCoord );
	}
	
	public HashMap<TileEntity, Rectangle2D> getTileHitboxMap() {
		return tileHitboxMap;
	}
	
	public double getXCoord() {
		return xCoord;
	}
	
	public double getYCoord() {
		return yCoord;
	}
	
	public double getWidth() {
		return numCols * Settings.TILE_LENGHT;
	}
	
	public double getHeight() {
		return numRows * Settings.TILE_LENGHT;
	}
	
}

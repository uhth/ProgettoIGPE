package application.entity;

import application.view.Settings;
import application.view.animation.Animation;
import application.view.sprite.BlueTileSprite;
import application.view.sprite.GreenTileSprite;
import application.view.sprite.RedTileSprite;
import application.view.sprite.Sprite;
import application.view.sprite.YellowTileSprite;
import javafx.geometry.Rectangle2D;

public class TileEntity extends Entity
{
	
	/*
	 * TILE ENTITY, EACH TILE DISPLAYED IS DESCRIPTED BY A TILE ENTITY
	 */
	
	public static enum COLOR {	RED, YELLOW, BLUE, GREEN }
	private COLOR color;
	
	private int numStages;
	private boolean fell;
	private int fallStage;
	private boolean isPopped;
	private boolean falling;
	
	public static final TileEntity RED_TILE_ENTITY = new TileEntity( RedTileSprite.getInstance(), TileEntity.COLOR.RED, 0, 0 );
	public static final TileEntity YELLOW_TILE_ENTITY = new TileEntity( YellowTileSprite.getInstance(), TileEntity.COLOR.YELLOW, 0, 0 );
	public static final TileEntity BLUE_TILE_ENTITY = new TileEntity( BlueTileSprite.getInstance(), TileEntity.COLOR.BLUE, 0, 0 );
	public static final TileEntity GREEN_TILE_ENTITY = new TileEntity( GreenTileSprite.getInstance(), TileEntity.COLOR.GREEN, 0, 0 );
	
	private TileEntity( Sprite sprite, COLOR color, double y, double x ) {
		super( sprite );
		this.numStages = 2;
		this.falling = false;
		this.isPopped = false;
		this.fell = false;
		this.fallStage = 0;
		this.color = color;
		this.pos.setY( y );
		this.pos.setX( x );
		entityType = "TILE";
		genHitbox();
	}
	
	//state + movement methods
	
	public void fall() {
		fallStage = 0;
		falling = true;
		pos.setY( pos.getY() + ( Settings.TILE_LENGHT / 2 ) );
		genHitbox();
	}
	
	public void land() {
		fallStage = 0;
		falling = false;
		fell = false;
	}
	
	public boolean hasFallen() {
		return fell;
	}
	
	public void pop() {
		playAnimationOnce( getPopAnimation(), 0 );
		isPopped = true;
	}
	
	public boolean isPopped() {
		return isPopped;
	}	

	public boolean isFalling() {
		return falling;
	}
	
	//color ( needed for the tile table to know which tiles are the "same" )
	public COLOR getColor() {
		return color;
	}
	
	//called once every frame ( updates animation and falling state )
	public void update() {
		updateAnimation();
		if( falling )
			fallStage++;
		if( fallStage >= numStages ) {
			fell = true;
			falling = false;
		}
	}
	
	//static "kind of" copy method used in random tile gen
	public static TileEntity build( TileEntity tileEntity, double y, double x ) {
		return new TileEntity( tileEntity.sprite, tileEntity.color, y, x );
	}
	
	//hitbox to intersect with mouse input
	@Override
	protected void genHitbox() {
		hitbox = new Rectangle2D( pos.getX(), pos.getY(), Settings.TILE_LENGHT, Settings.TILE_LENGHT );
	}
	
	//tells entity class to copy animations from sprite into animation array
	@Override
	protected boolean hasAnimation() {
		return true;
	}
	
	//animations
	@Override
	public Animation getIdleAnimation() {
		return animations[ 0 ];
	}
	
	public Animation getHLAnimation() {
		return animations[ 1 ];
	}
	
	public Animation getPopAnimation() {
		return animations[ 2 ];
	}
	
		
}

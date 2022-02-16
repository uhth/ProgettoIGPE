package application.view.sprite;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import application.view.animation.Animation;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;


public abstract class Sprite extends ImageView
{
	
	/*
	 * Most of cutting and resizing operations executed on sprites
	 * are very slow, but they usually only need to be done once. 
	 */
	
	//sprite properties
	private SnapshotParameters parameters;
	private Rectangle2D[] viewportArray;
	private Image[] spriteArray;
	private int numCells;
	private int numHCells;
	private int numVCells;
	protected double sWidth; //sprite width
	protected double sHeight; //sprite height
	protected double xScale = 1.0;
	protected double yScale = 1.0;
	
	//animations
	protected Animation[] animations;
		
	public Sprite( Image spriteSheet, double sWidth, double sHeight, double xScale, double yScale ) {
	    setImage( spriteSheet );
		
		this.sWidth = sWidth;
		this.sHeight = sHeight;
		this.xScale = xScale;
		this.yScale = yScale;
		this.setSmooth( false );
					
		numHCells = (int) ( spriteSheet.getWidth() / sWidth );
		numVCells = (int) ( spriteSheet.getHeight() / sHeight );
		
		numHCells += numHCells > 0 ? 0 : 1; 
		numVCells += numVCells > 0 ? 0 : 1; 
		
		numCells = numHCells * numVCells;
						
		viewportArray = new Rectangle2D[ numCells ];
		spriteArray = new Image[ numCells ];
		
		int counter = 0;
		
		parameters = new SnapshotParameters();
	    parameters.setFill( Color.TRANSPARENT );
	    

		for( int y = 0; y < numVCells; y++ )
			for( int x = 0; x < numHCells ; x++ ) {
				viewportArray[ counter++ ] = new Rectangle2D( x * sWidth, y * sHeight, sWidth, sHeight );
			}
		
		loadImages();
	}
	
	//nightmare func
	private void loadImages() 
	{
		
		for( int i = 0; i < numCells; i++ ) 
		{
			parameters.setViewport( viewportArray[ i ] );
			
			double widthS = sWidth * xScale;
			double heightS = sHeight * yScale;
			
			
			//SPRITE RESIZING
			
			/*	FASTER BUT INCONSISTENT
				ImageView snap = new ImageView( snapshot(parameters, null ) );
				snap.setFitWidth( widthS );
				snap.setFitHeight( heightS );
				
				spriteArray[ i ] = snap.snapshot(parameters, null);
			
			*/
			
			
			//SLOWER BUT MUCH BETTER
			BufferedImage bufferedImage = SwingFXUtils.fromFXImage( snapshot(parameters, null ), null );			
			ByteArrayOutputStream bArrayOS = new ByteArrayOutputStream();
			try { ImageIO.write( bufferedImage, "png", bArrayOS ); } 
			catch ( IOException e ) { System.out.println("Unable to write sprite"); }
			ByteArrayInputStream bArrayIS = new ByteArrayInputStream( bArrayOS.toByteArray() );
			try { bArrayOS.close();	}
			catch ( IOException e ) { System.out.println("Unable to close stream"); }
			Image img = new Image( bArrayIS, widthS, heightS, false, false );
			try { bArrayIS.close();	} 
			catch ( IOException e ) { System.out.println("Unable to close stream"); }

			spriteArray[ i ] = img;
			
		}
		
	}
	
	public Image getSpriteAt( int i ) {	return spriteArray[ i ]; }
	
	public Image[] getSpriteArray() { return spriteArray; }
	
	public Animation[] getAnimations() { return animations; }
	
	protected void reloadImages() {
		loadImages();
	}
	
}

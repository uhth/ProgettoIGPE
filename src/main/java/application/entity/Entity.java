package application.entity;

import application.utils.Vector2D;
import application.view.animation.Animation;
import application.view.sprite.Sprite;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public abstract class Entity
{
	protected Sprite sprite;
	protected String entityType;
	protected Vector2D pos;
	
	//model stuff
	protected int invincibilityFrames;
	
	//sprite stuff
	protected Animation currentAnimation;
	protected Animation[] animations;
	protected Image frame;
	
	//hitbox
	protected Rectangle2D hitbox;
		
			
	//standard constructor
	protected Entity( Sprite sprite ) 
	{
		//position and movement stuff
		this.pos = new Vector2D();
		this.sprite = sprite;
		
		//hitbox
		genHitbox();
				
		//model properties stuff
		this.invincibilityFrames = 0;
		
		//sprite and animation stuff
		this.frame = sprite.getSpriteAt( 0 );
		if( hasAnimation() )
			initEntityAnimations();
	}
		
	//movement and position
	public Vector2D getPos() { return pos; }		
	
	//properties
	public String getEntityType() { return entityType; }	
	public int getInvincibilityFrames() { return invincibilityFrames; }
	public void setInvincibilityFrames(int invincibilityFrames) { this.invincibilityFrames = invincibilityFrames; }
	
	//animation and drawing
	public void playAnimationOnce( Animation animation, int lastFrame ) { 
		if( this.currentAnimation.equals( animation ) ) return;
		setCurrentAnimation( animation );
		currentAnimation.playOnce( lastFrame );
	}
	public void playAnimationReverseOnce( Animation animation, int lastFrame ) { 
		if( this.currentAnimation.equals( animation ) ) return;
		setCurrentAnimation( animation );
		currentAnimation.playOnceReverse( lastFrame ); 
	}
		public void playAnimationReverse( Animation animation ) { 
		if( this.currentAnimation.equals( animation ) ) return;
		setCurrentAnimation( animation );
		currentAnimation.playReverse(); 
	}
		public void playAnimation( Animation animation ) { 
		if( this.currentAnimation.equals( animation ) ) return;
		setCurrentAnimation( animation );
		currentAnimation.play();
	}
	public void setCurrentAnimation( Animation animation ) {
		animation.reset();
		this.currentAnimation = animation;
	}	
	public Animation getCurrentAnimation() { return currentAnimation; }
	public Image getFrame() { return frame; }
	public void updateAnimation() {
		currentAnimation.nextFrame(); 
		this.frame = currentAnimation.getFrame();
	}

	private void initEntityAnimations() {
		this.animations = new Animation[ sprite.getAnimations().length ]; 
		for( int i = 0; i < sprite.getAnimations().length; i++ ) {
			animations[ i ] = new Animation( sprite.getAnimations()[ i ] );
		}
		this.currentAnimation = animations[ 0 ];
	}
	
	protected boolean hasAnimation() {
		return false;
	}	
	//base animations
	public Animation getIdleAnimation() { return null; }
	public Animation getHurtAnimation() { return null; }
	public Animation getSpawnAnimation() { return null; }
	public Animation getDeathAnimation() { return null; }
	
	//hitbox
	protected void genHitbox() {
		hitbox = new Rectangle2D( 0, 0, 0, 0 );
	}
	
	public Rectangle2D getHitbox() {
		return hitbox;
	}
			
}

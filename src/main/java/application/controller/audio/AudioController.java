package application.controller.audio;

import java.util.ArrayList;

import application.controller.database.DataBaseController;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioController {
	
	//db
	private DataBaseController dbController;
	
	private static AudioController instance;
	
	private static ArrayList<MediaPlayer> musics;
	private static ArrayList<AudioClip> effects;
	
	//musics
	private static MediaPlayer menuMusicPlayer;
	private static MediaPlayer gameMusicPlayer;
	
	//effects
	private static AudioClip[] popSoundEffects;
	private static AudioClip sceneTransitionEffect;
	private static AudioClip buttonHoverEffect;
	
	
	private AudioController() {
		
		dbController = DataBaseController.getInstance();
		
		musics = new ArrayList<MediaPlayer>();
		effects = new ArrayList<AudioClip>();
		
		//menu bg music
		menuMusicPlayer = new MediaPlayer( new Media( getClass().getResource( "/application/sounds/music/Industrial/industrial.wav" ).toString() ) );
		menuMusicPlayer.setCycleCount( MediaPlayer.INDEFINITE );
		menuMusicPlayer.setRate( 1.2 );
		
		musics.add( menuMusicPlayer );
		
		//game bg music
		gameMusicPlayer = new MediaPlayer( new Media( getClass().getResource( "/application/sounds/music/Ludum_Dare/bgm_1.wav" ).toString() ) );
		gameMusicPlayer.setCycleCount( MediaPlayer.INDEFINITE );
		gameMusicPlayer.setRate( 1.2 );
		
		musics.add( gameMusicPlayer );
		
		//game pop tile sound effect
		popSoundEffects = new AudioClip[ 5 ];
		popSoundEffects = new AudioClip[ 5 ];
		for( int i = 0; i < 4; i++ ) {
			popSoundEffects[ i ] = new AudioClip( getClass().getResource( "/application/sounds/effects/mixkit-attention-bell-ding-586_" + ( i + 1 ) + ".wav" ).toString() );
			effects.add( popSoundEffects[ i ] );
		}
		popSoundEffects[ 4 ] = popSoundEffects[ 3 ];
		effects.add( popSoundEffects[ 4 ] );
		
		//transition between scenes
		sceneTransitionEffect = new AudioClip( getClass().getResource( "/application/sounds/effects/mixkit-fast-small-sweep-transition-166.wav" ).toString() );
		
		effects.add( sceneTransitionEffect );

		//button hover effect
		buttonHoverEffect = new AudioClip( getClass().getResource( "/application/sounds/effects/mixkit-message-pop-alert-2354.wav" ).toString() );
		
		effects.add( buttonHoverEffect );
		
		setMusicVolume( dbController.getSetting( "music_volume" ) );
		setEffectsVolume( dbController.getSetting( "effects_volume" ) );
		
	}
	
	public static AudioController getInstance() {
		if( instance == null )
			instance = new AudioController();
		return instance;
	}
	
	
	public static void setEffectsVolume( double vol ) {
		if( effects == null ) return;
		for( AudioClip effect : effects ) {
			if( effect == null ) continue;
			effect.setVolume( vol );
		}
	}
	
    public static void setMusicVolume( double vol ) {
		if( musics == null ) return;
		for( MediaPlayer music : musics ) {
			if( music == null ) continue;
			music.setVolume( vol );
		}
    }
    
    public void playMenuMusic() {
    	menuMusicPlayer.play();
    }
    
    public void playGameMusic() {
    	gameMusicPlayer.play();
    }
    
    public void stopMenuMusic() {
    	menuMusicPlayer.stop();
    }
    
    public void stopGameMusic() {
    	gameMusicPlayer.stop();
    }
    
    public void playPopSoundEffect( int index ) {
    	popSoundEffects[ index ].play();
    }
    
    public void playSceneTransictionEffect() {
    	sceneTransitionEffect.play();
    }
    
    public void playButtonHoverEffect() {
    	buttonHoverEffect.play();
    }
	
}

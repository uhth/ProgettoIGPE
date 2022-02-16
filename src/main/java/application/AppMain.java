package application;

import java.util.Locale;
import application.controller.audio.AudioController;
import application.controller.game.ResourcesManager;
import application.controller.scene.MainMenuSceneController;
import application.view.Settings;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMain extends Application {
	
	Stage primaryStage;
	AudioController audioController;

	public static void main(String[] args) {
		launch( args );
	}

	@Override
	public void start( Stage primaryStage ) throws Exception {
		
		Locale.setDefault( Locale.US ); //makes decimals like 0.### instead of 0,###
		
		this.primaryStage = primaryStage;
		this.audioController = AudioController.getInstance();
							
		primaryStage.setHeight( Settings.WINDOW_H );
		primaryStage.setWidth( Settings.WINDOW_W );
		primaryStage.setTitle("Progetto IGPE - 209673");
		primaryStage.setFullScreenExitHint("");
		primaryStage.setResizable( false );
		primaryStage.show();
		
		ResourcesManager.loadResoruces(); // <--- preloads resources ( i.e sprites ) at the start ( not mandatory ), also you can set how many backgrounds you want to load
		setMainMenuScene();
		setSceneListener();
	}
		
	//sets stage's scene to MainMenu scene
	public void setMainMenuScene() {
		Scene scene = new Scene( MainMenuSceneController.loadScene() );
		scene.getStylesheets().add( getClass().getResource( "/application/css/style.css" ).toString() );
		primaryStage.setScene( scene );	
	}
	
	//add change listener to scene ( to play effect )
	private void setSceneListener() {
		primaryStage.sceneProperty().addListener( new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				audioController.playSceneTransictionEffect();				
			}
			
		});
		
	}
	
}

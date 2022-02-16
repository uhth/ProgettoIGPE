package application.controller.scene;

import java.io.IOException;

import application.alert.ErrorAlert;
import application.controller.audio.AudioController;
import application.view.graphics.MenuGraphics;
import application.view.sprite.static_backgrounds.MenuBgSprite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainMenuSceneController {

	Stage stage;
	AudioController audioController;
	
    @FXML
    private Button quitButton;
    @FXML
    private Canvas menuCanvas;
    @FXML
    private Button playButton;   
    @FXML
    private Label menuTitleLabel;   
    
    //plays an effect when mouse enters buttons
    @FXML
    void ONMOUSEENTERED( MouseEvent event ) {
    	audioController.playButtonHoverEffect();
    }
    
    //menu play button + stops menu music
    @FXML
    void ONPLAYCLICKED( ActionEvent event ) {
    	Stage stage = ( Stage ) ( ( Node ) event.getSource() ).getScene().getWindow();
    	Scene scene = new Scene( GameSceneController.loadScene() );
		scene.getStylesheets().add( getClass().getResource( "/application/css/style.css" ).toString() );
		scene.getStylesheets().add( getClass().getResource( "/application/css/std_label.css" ).toString() );
    	stage.setScene( scene );
    	audioController.stopMenuMusic();
    }
    
    //menu settings button
    @FXML
    void ONSETTINGSCLICKED(ActionEvent event) {
    	Stage stage = ( Stage ) ( ( Node ) event.getSource() ).getScene().getWindow();
    	Scene scene = new Scene( SettingsSceneController.loadScene( stage.getScene() ) );
		scene.getStylesheets().add( getClass().getResource( "/application/css/style.css" ).toString() );
    	stage.setScene( scene );
    }
    
    //menu highscores button
    @FXML
    void ONHIGHSCORESCLICKED(ActionEvent event) {
    	Stage stage = ( Stage ) ( ( Node ) event.getSource() ).getScene().getWindow();
    	Scene scene = new Scene( HighScoresSceneController.loadScene( stage.getScene() ) );
		scene.getStylesheets().add( getClass().getResource( "/application/css/style.css" ).toString() );
    	stage.setScene( scene );
    }
    
    //menu credits button
    @FXML
    void ONCREDITSCLICKED(ActionEvent event) {
    	Stage stage = ( Stage ) ( ( Node ) event.getSource() ).getScene().getWindow();
    	Scene scene = new Scene( CreditsSceneController.loadScene( stage.getScene() ) );
		scene.getStylesheets().add( getClass().getResource( "/application/css/style.css" ).toString() );
    	stage.setScene( scene );
    }

    //menu quit button
    @FXML
    void ONQUITCLICKED( ActionEvent event ) {
    	System.exit(0);
    }
    
    //loads fxml
    public static Parent loadScene() {
    	try {
    		FXMLLoader loader = new FXMLLoader( GameSceneController.class.getResource( "/application/scenes/MainMenuScene.fxml" ) );
    		Parent root = loader.load();    
    		return root;
    	} catch (IOException e) {
    		String error = "Unable to load FXML file, contact developer for support";
			Alert alert = new ErrorAlert( "DB ERROR", error );
			alert.initModality( Modality.APPLICATION_MODAL );
			alert.showAndWait();
    		e.printStackTrace();
    	}
    	return null;
    	
    }
    
    
    //init
    @FXML
    public void initialize() {
    	MenuGraphics mg = new MenuGraphics( menuCanvas.getGraphicsContext2D() );
		audioController = AudioController.getInstance();
    	mg.setBackgroundSprite( MenuBgSprite.getInstance() );
    	mg.draw();
    	audioController.playMenuMusic();
		
    }
    

}

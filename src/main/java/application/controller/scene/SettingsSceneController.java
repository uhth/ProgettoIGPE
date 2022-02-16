package application.controller.scene;

import java.io.IOException;

import application.alert.ErrorAlert;
import application.controller.audio.AudioController;
import application.controller.database.DataBaseController;
import application.view.sprite.static_backgrounds.BackgroundSpriteRotation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SettingsSceneController {

	
	private static Scene previousScene;
	private DataBaseController dbController;
	
    @FXML
    private Canvas settingsCanvas;
    @FXML
    private Button menuButtonSettings;    
    @FXML
    private Slider settingsMusicVolumeSlider;    
    @FXML
    private Slider settingsEffectsVolumeSlider;
    @FXML
    private Label settingsMusicVolumeNLabel;
    @FXML
    private Label settingsEffectsVolumeNLabel;
    
    //menu play button
    @FXML
    void ONMOUSEENTERED( MouseEvent event ) {
    	AudioController.getInstance().playButtonHoverEffect();
    }
    
    //bottom right reset button ( reset settings to default )
    @FXML
    void ONRESETCLICKED(ActionEvent event) {
    	dbController.resetSettingsToDefault();
    	updateSliders();
    }
    
	//top left menu button ( save settings into db )
    @FXML
    void ONMENUCLICKED( ActionEvent event) {
    	Stage stage = ( Stage ) ( ( Node ) event.getSource() ).getScene().getWindow();
    	stage.setScene( previousScene );
    	
    	//save changes into db
    	dbController.updateSetting( "music_volume" , settingsMusicVolumeSlider.getValue() / 100 );
    	dbController.updateSetting( "effects_volume" , settingsEffectsVolumeSlider.getValue() / 100 );
    }
    
    //loads fxml
    public static Parent loadScene( Scene scene ) {
    	try {
    		FXMLLoader loader = new FXMLLoader( GameSceneController.class.getResource( "/application/scenes/SettingsScene.fxml" ) );
    		Parent root = loader.load();    
    		previousScene = scene;
    		return root;
    	} 
    	catch (IOException e) {
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
    	
    	settingsCanvas.getGraphicsContext2D().drawImage( BackgroundSpriteRotation.getRandomBackground().getSpriteAt( 0 ), 0, 0, settingsCanvas.getWidth(), settingsCanvas.getHeight() );	
    	dbController = DataBaseController.getInstance();  	
		GaussianBlur gBlur = new GaussianBlur();
		ColorAdjust cAdjustCanvasTemp = new ColorAdjust();
		cAdjustCanvasTemp.setBrightness( -0.7 );
		gBlur.setInput( cAdjustCanvasTemp );
		gBlur.setRadius( 15 );
		settingsCanvas.setEffect( gBlur );	
		initSliders();
		
	}
    
    //adding listeners and styling sliders
	private void initSliders() {
		
		settingsEffectsVolumeNLabel.textProperty().bind( settingsEffectsVolumeSlider.valueProperty().asString( "%.0f" ) );
		settingsMusicVolumeNLabel.textProperty().bind( settingsMusicVolumeSlider.valueProperty().asString( "%.0f" ) );
		settingsEffectsVolumeSlider.setStyle( "-fx-background-color: linear-gradient( to right, violet 0%, gray 0%);" );
		settingsMusicVolumeSlider.setStyle( "-fx-background-color: linear-gradient( to right, violet 0%, gray 0%);" );
		
		settingsMusicVolumeSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				String style = String.format("-fx-background-color: linear-gradient( to right, violet %d%%, gray %d%%);"
						+ " -fx-background-radius : 0.1em;"
						+ " -fx-border-radius :2.0em;",
						newValue.intValue(), newValue.intValue() );
				settingsMusicVolumeSlider.setStyle(style);
				
				AudioController.setMusicVolume( settingsMusicVolumeSlider.getValue() / 100 );
			}
		} );
		
		settingsEffectsVolumeSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				String style = String.format("-fx-background-color: linear-gradient( to right, violet %d%%, gray %d%%);"
						+ " -fx-background-radius : 0.1em;"
						+ " -fx-border-radius :2.0em;",
						newValue.intValue(), newValue.intValue() );
				settingsEffectsVolumeSlider.setStyle(style);
				
				AudioController.setEffectsVolume( settingsEffectsVolumeSlider.getValue() / 100 );
			}
		} );
		
		updateSliders();
	}
	
	
    private void updateSliders() {
		settingsMusicVolumeSlider.setValue( dbController.getSetting( "music_volume" ) * 100 );
		settingsEffectsVolumeSlider.setValue( dbController.getSetting( "effects_volume" ) * 100 );
	}

}
package application.controller.scene;

import java.io.IOException;

import application.alert.ErrorAlert;
import application.controller.audio.AudioController;
import application.view.sprite.static_backgrounds.BackgroundSpriteRotation;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreditsSceneController {

	private static Scene previousScene;
	
	@FXML
    private TextArea creditsTextArea;

    @FXML
    private Canvas creditsCanvas;

    @FXML
    private Label creditsLabel;
    
    //plays an effect when mouse enters buttons
    @FXML
    void ONMOUSEENTERED( MouseEvent event ) {
    	AudioController.getInstance().playButtonHoverEffect();
    }
    
    //top left menu button
    @FXML
    void ONMENUCLICKED( ActionEvent event ) {
    	Stage stage = ( Stage ) ( ( Node ) event.getSource() ).getScene().getWindow();
    	stage.setScene( previousScene );
    }
    
    //load fxml file
    public static Parent loadScene( Scene scene ) {
    	try {
    		FXMLLoader loader = new FXMLLoader( GameSceneController.class.getResource( "/application/scenes/CreditsScene.fxml" ) );
    		Parent root = loader.load();    
    		previousScene = scene;
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
    	creditsCanvas.getGraphicsContext2D().drawImage( BackgroundSpriteRotation.getRandomBackground().getSpriteAt( 0 ), 0, 0, creditsCanvas.getWidth(), creditsCanvas.getHeight() );
    	creditsTextArea.addEventFilter( ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume ); // <-- no context menu on left click on credits :)
    	
		GaussianBlur gBlur = new GaussianBlur();
		ColorAdjust cAdjustCanvasTemp = new ColorAdjust();
		cAdjustCanvasTemp.setBrightness( -0.7 );
		gBlur.setInput( cAdjustCanvasTemp );
		gBlur.setRadius( 15 );
		creditsCanvas.setEffect( gBlur );
    }
	
}

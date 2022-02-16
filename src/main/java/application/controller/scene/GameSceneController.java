package application.controller.scene;

import java.io.IOException;
import java.util.EventListener;

import application.alert.ErrorAlert;
import application.controller.audio.AudioController;
import application.controller.database.DataBaseController;
import application.controller.game.GameController;
import application.controller.input.MouseHandler;
import application.view.graphics.GameGraphics;
import application.view.graphics.GameOverGraphics;
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
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameSceneController implements EventListener {

	MouseHandler mouseHandler;
	GameController gameController;
	GameGraphics graphics;
	GameOverGraphics goGraphics;
	DataBaseController dbController;
	AudioController audioController;
	
	//effects
	DropShadow dShadowTS = new DropShadow();
	DropShadow dShadowIT = new DropShadow();	
	
	//components
    @FXML
    private Label gameTimerLabel;
    @FXML
    private Label scoreLabelGO;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label gameOverLabel;
    @FXML
    private Label initialTimerLabel;
    @FXML
    private Label gameMultiplierLabel;
    @FXML
    private Label gameMultiplierNLabel;
    @FXML
    private StackPane gameOverSP;
    @FXML
    private TextField nameTextField;
    @FXML
    private Canvas mainCanvas;
    @FXML
    private Canvas canvasGO;
    @FXML
    private Button menuButton;
    @FXML
    private Button saveScoreButton;
    @FXML
    private Button menuButtonGO; 
    @FXML
    private Button retryButton;
    
    //plays an effect when mouse enters buttons
    @FXML
    void ONMOUSEENTERED( MouseEvent event ) {
    	audioController.playButtonHoverEffect();
    }
    
    //top left "Menu" button
    @FXML
    void ONMENUCLICKED( ActionEvent event ) {
    	gameController.stop();
    	audioController.stopGameMusic();
    	Stage stage = ( Stage ) ( ( Node ) event.getSource() ).getScene().getWindow();
    	Scene scene = new Scene( MainMenuSceneController.loadScene() );
		scene.getStylesheets().add( getClass().getResource( "/application/css/style.css" ).toString() );
    	stage.setScene( scene );
    }
    
    //game over "Play Again" button
    @FXML
    void ONRETRYCLICKED(ActionEvent event) {
    	gameOverSP.setVisible( false );
    	gameOverLabel.setVisible( false );
    	initialTimerLabel.setVisible( true );
    	gameController.initGame();
    	gameController.start();
    }

    //game over "Save Score" button
    @FXML
    void ONSAVECLICKED(ActionEvent event) {
    	if( nameTextField.getText().isBlank() || nameTextField.getText().isEmpty() ) return;
    	String name = nameTextField.getText();
    	if( name.length() > 60 )
    		name = name.substring( 0, 60 ); //truncate strings too long
    	dbController.insertScore( name, gameController.getScore() );
    	saveScoreButton.setDisable( true );
    	nameTextField.setText( "" );
    }
    
    //loads fxml file
    public static Parent loadScene() {
    	try {
    		FXMLLoader loader = new FXMLLoader( GameSceneController.class.getResource( "/application/scenes/GameScene.fxml" ) );
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
    	    	
    	gameTimerLabel.getStyleClass().remove( "label" );
    	scoreLabel.getStyleClass().remove( "label" );
    	gameMultiplierNLabel.getStyleClass().remove( "label" );
    	gameMultiplierLabel.getStyleClass().remove( "label" );
    	gameMultiplierNLabel.setStyle( String.format( "-fx-text-fill: radial-gradient( center 50%% 50%%, radius 40%%, %s, %s );", "yellow", "orange" ) );
    	
    	    	
    	mouseHandler = new MouseHandler();
    	mainCanvas.addEventFilter( MouseEvent.MOUSE_CLICKED, mouseHandler ); 
    	mainCanvas.addEventFilter( MouseEvent.MOUSE_MOVED, mouseHandler ); 
    	initialTimerLabel.setVisible( false );
    	
    	setupEffects();
    	
		gameTimerLabel.setEffect( dShadowTS );
		scoreLabel.setEffect( dShadowTS );
		gameMultiplierNLabel.setEffect( dShadowTS );
		initialTimerLabel.setEffect( dShadowIT );
				
		initialTimerLabel.setVisible( true );
    	graphics = new GameGraphics( mainCanvas.getGraphicsContext2D() );
    	goGraphics = new GameOverGraphics( canvasGO.getGraphicsContext2D() );
    	gameController = new GameController( this );
    	dbController = DataBaseController.getInstance();
    	audioController = AudioController.getInstance();
    	mouseHandler.setController( gameController );
    	gameController.setGraphics( graphics );
		audioController.playGameMusic();
    	gameController.start();

    }
    

    //setting up some effects and adding some listeners
    private void setupEffects() {
    	//main effects
		dShadowTS.setSpread( 0.8 );
		dShadowTS.setRadius( 2.4 );
		dShadowTS.setOffsetX( 2 );
		dShadowTS.setOffsetY( 2 );
		dShadowTS.setColor( Color.BLACK );
		dShadowIT.setSpread( 0.4 );
		dShadowIT.setRadius( 5.0 );
		dShadowIT.setOffsetX( 4 );
		dShadowIT.setOffsetY( 2 );
		dShadowIT.setColor( Color.BLACK );
		
		//initial timer blur and darkening of the scene
		initialTimerLabel.visibleProperty().addListener( new ChangeListener<Object>() {
			@Override
			public void changed( ObservableValue<?> observable, Object oldValue, Object newValue ) {
					if( (boolean) newValue == true ) blurGameScene( 10 );
					else normalGameScene();
				}
    	});
		
		//score multiplier effects
		gameMultiplierNLabel.textProperty().addListener( new ChangeListener<Object>() {

			@Override
			public void changed( ObservableValue<? extends Object> observable, Object oldValue, Object newValue ) {
				
				String style = "-fx-text-fill: radial-gradient( center 50%% 50%%, radius 40%%, %s, %s );"
						+ " -fx-font-size: %.1fem;";

				switch( ( String ) newValue ) {					
					case "5X":
						gameMultiplierNLabel.setStyle( style.formatted( "cyan", "snow", 6.5 ) );
						break;					
					case "4X":
						gameMultiplierNLabel.setStyle( style.formatted( "purple", "cyan", 6.0 ) );
						break;						
					case "3X":
						gameMultiplierNLabel.setStyle( style.formatted( "red", "purple", 5.5 ) );
						break;
					case "2X":
						gameMultiplierNLabel.setStyle( style.formatted( "orange", "red", 5.0 ) );
						break;
					default:
						gameMultiplierNLabel.setStyle( style.formatted( "yellow", "orange", 4.5 )  );
						break;
				}
			}
		});
    }
    
    //blurs almost the entire game scene excluding game over stuff
    public void blurGameScene( double radius ) {
		GaussianBlur gBlur = new GaussianBlur();
		GaussianBlur gBlur2 = new GaussianBlur();
		ColorAdjust cAdjustCanvasTemp = new ColorAdjust();
		cAdjustCanvasTemp.setBrightness( -0.7 );
		gBlur.setRadius( radius );
		gBlur2.setRadius( radius );
		gBlur.setInput( cAdjustCanvasTemp );
		mainCanvas.setEffect( gBlur );	
		gameTimerLabel.setEffect( gBlur2 );
		scoreLabel.setEffect( gBlur2 );
		gameMultiplierNLabel.setEffect( gBlur2 );
		gameMultiplierLabel.setEffect( gBlur2 );
    }
   
    //un-blurs almost the entire game scene excluding game over stuff
    public void normalGameScene() {
		ColorAdjust cAdjustCanvasFinal = new ColorAdjust();
		cAdjustCanvasFinal.setContrast( 0.2 );
		mainCanvas.setEffect( cAdjustCanvasFinal );
		gameTimerLabel.setEffect( dShadowTS );
		scoreLabel.setEffect( dShadowTS );
		gameMultiplierNLabel.setEffect( dShadowTS );
		gameMultiplierLabel.setEffect( dShadowTS );
    }
    
    //shows game over
    public void showGameOver() {
    	gameController.stop();
    	gameOverSP.setVisible( true );
    	gameOverLabel.setVisible( true );
    	scoreLabelGO.setText( scoreLabel.getText() );
    	saveScoreButton.setDisable( false );
    	nameTextField.setText( "" );
    	blurGameScene( 20 );
    }
    
    public Label getGameTimerLabel() {
		return gameTimerLabel;
	}
    
    public Label getInitialTimerLabel() {
		return initialTimerLabel;
	}
    
    public Label getScoreLabel() {
		return scoreLabel;
	}
    
    public Label getMultiplierNLabel() {
		return gameMultiplierNLabel;
	}
    
    public GameController getGameController() {
		return gameController;
    	
    }
        
}

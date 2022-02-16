package application.controller.scene;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.alert.ErrorAlert;
import application.controller.audio.AudioController;
import application.controller.database.DataBaseController;
import application.view.sprite.static_backgrounds.BackgroundSpriteRotation;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HighScoresSceneController {
	
	private static Scene previousScene;
	private DataBaseController dbController;
	ToggleGroup orderToggleGroup;
	
	private static final int SCORE = 0;
	private static final int NAME = 1;

    @FXML
    private Button menuButtonScores;
    @FXML
    private Canvas scoresCanvas;
    @FXML
    private Label scoresLabel;
    @FXML
    private GridPane scoresGridPane;    
    @FXML
    private RadioButton scoresOBNameButton;    
    @FXML
    private RadioButton scoresOBScoreButton;    
    
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
    
    //order radial buttons click
    @FXML
    void ONORDERBUTTONCLICKED( ActionEvent event ) {
    	reset();
    	if ( orderToggleGroup.getSelectedToggle().equals( scoresOBNameButton ) )
    		loadScores( NAME );
    	else
    		loadScores( SCORE );
    }
    
    //bottom right reset button click
    @FXML
    void ONRESETBUTTONCLICK( ActionEvent event ) {
    	dbController.resetScores();
    	ONORDERBUTTONCLICKED( null );
    }
    
    //loads fxml file
    public static Parent loadScene( Scene scene ) {
    	try {
    		FXMLLoader loader = new FXMLLoader( GameSceneController.class.getResource( "/application/scenes/HighScoresScene.fxml" ) );
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
    
    //resets the gridpane containing the score labels
    private void reset() {
    	scoresGridPane.getChildren().clear();
    }
    
    //db query + creating labels for name and score and inserting them into score gridpane
    private void loadScores( int order ) {
    	ResultSet scores;
    	
    	if( order == NAME )
    		scores = dbController.getScoresByName();
    	else
    		scores = dbController.getScoresByHighest();
    	
    	if( scores != null ) {
    		try {
    				while( scores.next() ) {
					String name = scores.getString( "name" );
					int score = scores.getInt( "score" );
					scoresGridPane.addRow( scoresGridPane.getRowCount(), new Label( name ), new Label( String.valueOf( score ) ) );
				}
			} 
    		catch (SQLException e) {
				System.err.println( "Unable to load HighScores." );
				e.printStackTrace();
			}
    	}
    }
    
    //init
    @FXML
    public void initialize() {
    	
    	scoresCanvas.getGraphicsContext2D().drawImage( BackgroundSpriteRotation.getRandomBackground().getSpriteAt( 0 ), 0, 0, scoresCanvas.getWidth(), scoresCanvas.getHeight() );
    	dbController = DataBaseController.getInstance();
    	orderToggleGroup = new ToggleGroup();
    	orderToggleGroup.getToggles().add( scoresOBNameButton );
    	orderToggleGroup.getToggles().add( scoresOBScoreButton );
    	ONORDERBUTTONCLICKED( null );
    	
		GaussianBlur gBlur = new GaussianBlur();
		ColorAdjust cAdjustCanvasTemp = new ColorAdjust();
		cAdjustCanvasTemp.setBrightness( -0.7 );
		gBlur.setInput( cAdjustCanvasTemp );
		gBlur.setRadius( 15 );
		scoresCanvas.setEffect( gBlur );
    	
    }
    

}
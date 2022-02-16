package application.alert;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

public class ErrorAlert extends Alert {

	public ErrorAlert( String header, String content ) {
		super( AlertType.ERROR );
		setHeaderText( header );
		setContentText( content );
		initModality( Modality.APPLICATION_MODAL );
		getDialogPane().getStylesheets().add( getClass().getResource( "/application/css/style.css" ).toString() );
	}
	

}

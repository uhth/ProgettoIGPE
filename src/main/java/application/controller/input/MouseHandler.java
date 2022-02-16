package application.controller.input;

import application.controller.game.GameController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MouseHandler implements EventHandler<MouseEvent>
{
	
	GameController gameController;
	
	/*
	 * THIS CLASS LISTENS TO MOUSE COORDS AND
	 * COMMUNICATES TO GAMECONTROLLER TO PERFORM
	 * CLICKS AND HOVERING ON TILES
	 */
		
	@Override
	public void handle( MouseEvent event ) {
		
		//CLICK
		if( event.getEventType().equals( MouseEvent.MOUSE_CLICKED ) ) {
			gameController.mouseClick( event.getY(), event.getX() );
			event.consume();
		}
		
		//MOVE ( hover )
		else if( event.getEventType().equals( MouseEvent.MOUSE_MOVED ) ) {
			gameController.mouseHover( event.getY(), event.getX() );
			event.consume();

		}
	}
	
	public void setController( GameController controller ) {
		gameController = controller;
	}

}

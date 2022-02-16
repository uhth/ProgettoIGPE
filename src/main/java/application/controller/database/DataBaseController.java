package application.controller.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import application.alert.ErrorAlert;
import javafx.scene.control.Alert;
import javafx.stage.Modality;

public class DataBaseController {
	
	private static Connection connection;
	private static String url = "jdbc:sqlite:" + "src/main/resources/application/data/main_db.db";
	private static DataBaseController instance;
	
	private DataBaseController() {
		connect();
	}
	
	public static DataBaseController getInstance() {
		if( instance == null )
			instance = new DataBaseController();
		return instance;
	}
	
	private void connect() {
		try {
			connection = DriverManager.getConnection( url );
			if( connection != null && !connection.isClosed() )
				initDB();
		}
		catch( SQLException e ) {	
							
			String error = "UNABLE TO CONNECT TO DB!\nCONTACT DEVELOPER FOR SUPPORT";
			Alert alert = new ErrorAlert( "FATAL ERROR", error );			
			alert.showAndWait();
			e.printStackTrace();
			System.exit( -1 );
		}
	}
	
	
	/*
	 * DATABASE INIT
	 */
	
	private void initDB() {
		try {
			
			Statement statement = connection.createStatement();
						
			//SCORES
			statement.executeUpdate( "CREATE TABLE IF NOT EXISTS"
					+ " Scores ("
					+ " name varchar(64) not null,"
					+ " score int )" );
				
			
			//SETTINGS
			
			//checks if default settings exists else it initializes it
			if ( !( statement.executeQuery( "SELECT name"
					+ " FROM sqlite_master"
					+ " WHERE type='table'"
					+ " AND name='Default_Settings'") ).next() )
				initDefaultSettings( statement );
			
			//checks if current settings exists else it initializes it
			if ( !( statement.executeQuery( "SELECT name"
					+ " FROM sqlite_master"
					+ " WHERE type='table'"
					+ " AND name='Current_Settings'") ).next() )
				resetSettingsToDefault();
											
		} 
		catch (SQLException e) {
			String error = "UNABLE TO INITIALIZE DB!\nCONTACT DEVELOPER FOR SUPPORT";
			Alert alert = new ErrorAlert(  "FATAL ERROR", error );
			alert.showAndWait();
			e.printStackTrace();
			System.exit( -1 );
		}
	}
	
	//if there's no default settings this will create them
	private void initDefaultSettings( Statement statement ) throws SQLException {
		
		statement.executeUpdate( "CREATE TABLE"
				+ " Default_Settings ("
				+ " name varchar(64) not null unique,"
				+ " value float(53) DEFAULT 0.0 )" );
		
		statement.executeUpdate( "INSERT INTO Default_Settings"
				+ " VALUES"
				+ " ( 'music_volume', '0.5' ),"
				+ " ( 'effects_volume', '0.5' )" );
	}
	
	/*
	 * SETTINGS QUERIES
	 */
	
	public void resetSettingsToDefault() {
		try {
			
			Statement statement = connection.createStatement();
			
			statement.executeUpdate( 
					  "CREATE TABLE IF NOT EXISTS Current_Settings ("
					+ "	name varchar(64) not null unique,"
					+ " value float(53) DEFAULT 0.0 )" );
			statement.executeUpdate("DELETE FROM Current_Settings" );
			statement.executeUpdate( "INSERT INTO Current_Settings SELECT * FROM Default_Settings" );
						
		} 
		catch (SQLException e) {
			String error = "Unable to reset settings, contact developer for support";
			Alert alert = new ErrorAlert( "DB ERROR", error );
			alert.initModality( Modality.APPLICATION_MODAL );
			alert.showAndWait();
			e.printStackTrace();
		}
	}
	
	public void updateSetting( String name, double value ) {
		try {
			String sql = "UPDATE Current_Settings SET value = ? WHERE name = ? ";
			PreparedStatement statement = connection.prepareStatement( sql );
			statement.setDouble( 1, value);
			statement.setString( 2, name );
			statement.executeUpdate();
		}
		catch (SQLException e) {
			String error = "Unable to update settings, contact developer for support";
			Alert alert = new ErrorAlert( "DB ERROR", error );
			alert.initModality( Modality.APPLICATION_MODAL );
			alert.showAndWait();
			e.printStackTrace();
		}
	}
	
	public Double getSetting( String name ) {
		
		String sql = "SELECT * from Current_Settings WHERE name = ? ";
		
		try {
			PreparedStatement statement = connection.prepareStatement( sql );
			statement.setString( 1, name );
			ResultSet rs = statement.executeQuery();
			
			if( !rs.next() ) throw new SQLException();
			
			return rs.getDouble( "value" );
			
		} catch ( SQLException e ) {

			String error = "Setting/s missing or unavailable, contact developer for support";
			Alert alert = new ErrorAlert( "DB ERROR", error );
			alert.initModality( Modality.APPLICATION_MODAL );
			alert.showAndWait();
			
			e.printStackTrace();
		}
		
		return 0.0; //this won't usually crash the scene ( while using null will ), it's a wrong result tho, something didn't work;
	}
	
	
	
	/*
	 * SCORES QUERIES
	 */
	
	public void insertScore( String name, int score ) {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate( "INSERT INTO Scores ( name, score ) VALUES ( '" + name + "', '" + score + "' ) " );
			
		} 
		catch (SQLException e) {
			String error = "Unable to insert score, contact developer for support";
			Alert alert = new ErrorAlert( "DB ERROR", error );
			alert.initModality( Modality.APPLICATION_MODAL );
			alert.showAndWait();
			e.printStackTrace();
		}
		
	}
	
	public ResultSet getScores() {
		try {
			Statement statement = connection.createStatement();
			return statement.executeQuery( "SELECT * FROM Scores" );
			
		} 
		catch (SQLException e) {
			String error = "Unable to get scores, contact developer for support";
			Alert alert = new ErrorAlert( "DB ERROR", error );
			alert.initModality( Modality.APPLICATION_MODAL );
			alert.showAndWait();
			e.printStackTrace();
		}
		return null;
	}
	
	public ResultSet getScoresByName() {
		try {
			Statement statement = connection.createStatement();
			return statement.executeQuery( "SELECT * FROM Scores ORDER BY name COLLATE NOCASE" );
			
		} 
		catch (SQLException e) {
			String error = "Unable to get scores, contact developer for support";
			Alert alert = new ErrorAlert( "DB ERROR", error );
			alert.initModality( Modality.APPLICATION_MODAL );
			alert.showAndWait();
			e.printStackTrace();
		}
		return null;
	}
	
	public ResultSet getScoresByHighest() {
		try {
			Statement statement = connection.createStatement();
			return statement.executeQuery( "SELECT * FROM Scores ORDER BY score DESC" );
			
		} 
		catch (SQLException e) {
			String error = "Unable to get scores, contact developer for support";
			Alert alert = new ErrorAlert( "DB ERROR", error );
			alert.initModality( Modality.APPLICATION_MODAL );
			alert.showAndWait();
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void resetScores() {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate( "DELETE FROM Scores" );
			
		} 
		catch (SQLException e) {
			String error = "Unable to reset scores, contact developer for support";
			Alert alert = new ErrorAlert( "DB ERROR", error );
			alert.initModality( Modality.APPLICATION_MODAL );
			alert.showAndWait();
			e.printStackTrace();
		}
	}
	
	
}

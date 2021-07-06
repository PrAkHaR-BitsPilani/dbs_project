package Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Backend.SQL;
import Backend.EnvironmentCreator;
import java.sql.ResultSet;
import java.util.regex.Pattern;

public class LoginController 
{
	private double xoffset = 0.0;
	private double yoffset = 0.0;
	
	@FXML
	private Button login;
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private Label status;
	@FXML
	private Button exit;
	
	@FXML
	public void login(ActionEvent e) throws Exception
	{
		SQL obj = new SQL();
		obj.createConnection();
		EnvironmentCreator.useDatabase(obj, "library");
		boolean entered = false;
		ResultSet rs;
		
		String un = username.getText();
		String pass = password.getText();
		
		if(un.equals("admin"))
		{
			if(pass.equals("admin"))
			{
				obj.disconnect();
				Stage primaryStage = (Stage)((Node) e.getSource()).getScene().getWindow();
				 
				Parent root = FXMLLoader.load(getClass().getResource("/FXML/AdminLanding.fxml"));
				Scene scene = new Scene(root);
				
				root.setOnMousePressed(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						xoffset = event.getSceneX();
						yoffset = event.getSceneY();
					}
				});
				
				root.setOnMouseDragged(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						primaryStage.setX(event.getScreenX() - xoffset);
						primaryStage.setY(event.getScreenY() - yoffset);
					}
				});	
				
				scene.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
				
				
				primaryStage.setScene(scene);
				primaryStage.show();
			}
			else
			{
				obj.disconnect();
				password.setText(null);
				status.setText("Incorrect administrator password!");
				return;
			}			
		}
		
		if(Pattern.matches("[0-9]+", un))
		{
			rs = obj.query("SELECT uid, password FROM members WHERE uid = " + un + ";");			
		}
		else if (Pattern.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", un))
		{
			rs = obj.query("SELECT email, password FROM members WHERE email = '" + un + "';");
		}
		else
		{
			rs = null;
			status.setText("Incorrect username format!");
			username.setText(null);
			password.setText(null);
			obj.disconnect();
			return;
		}
		
		while(rs.next()) 
		{ 
			entered = true;
			if(rs.getString("password").equals(password.getText())) 
			{
				CurrentStatus mem = CurrentStatus.getINSTANCE();
                mem.setID(un);
				obj.disconnect();
				Stage primaryStage = (Stage)((Node) e.getSource()).getScene().getWindow();
				 
				Parent root = FXMLLoader.load(getClass().getResource("/FXML/MemberLanding.fxml"));
				Scene scene = new Scene(root);
				
				
				root.setOnMousePressed(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						xoffset = event.getSceneX();
						yoffset = event.getSceneY();
					}
				});
				
				root.setOnMouseDragged(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						primaryStage.setX(event.getScreenX() - xoffset);
						primaryStage.setY(event.getScreenY() - yoffset);
					}
				});	
				
				scene.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
				
				primaryStage.setScene(scene); 
			} 
			else 
			{
				status.setText("Incorrect Password!");
				password.setText(null);
			}
		}
		
		if(!entered)
		{
			status.setText("User ID does not exist!");
			username.setText(null);
		}
		password.setText(null);
		obj.disconnect();
	}
	
	@FXML
	public void exit(ActionEvent e)
	{
		System.exit(0);
	}
}



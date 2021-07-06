package Controllers;

import java.sql.ResultSet;
import java.util.regex.Pattern;

import Backend.PublisherHandler;
import Backend.EnvironmentCreator;
import Backend.SQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RemovePublisherController {

    @FXML
    private Button removePublisherClose;

    @FXML
    private TextField publisher_uid;

    @FXML
    private Button removePublisherContinue;

    @FXML
    private Label removePublisherLabel;
    
    private boolean sure = false;
    
    private String prev = "";

    @FXML
    void exit(ActionEvent event) {
    	Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
    	primaryStage.close();
    }

    @FXML
    void removePublisher(ActionEvent event) {

    	String uid = publisher_uid.getText();
    	
    	if(!(prev.equals(uid) && sure))
    		sure = false;
    	
    	prev = uid;
    	
    	if(!Pattern.matches("[0-9]+", uid))
    	{
    		removePublisherLabel.setText("Invalid UID!");
    		return;
    	}
    	SQL obj = new SQL();
    	obj.createConnection();
    	EnvironmentCreator.useDatabase(obj, "library");
    	
    	ResultSet rs = obj.query("SELECT * FROM publishers WHERE publisher_id = " + uid + ";");

    	try
    	{
    		if(!rs.next())
        	{
        		removePublisherLabel.setText("UID does not exist!");
        		obj.disconnect();
        		return;
        	}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}    	
    	
    	if(!sure)
    	{
    		removePublisherLabel.setText("Click again to confirm!");
    		sure = true;
    		return;
    	}
    	
    	PublisherHandler.remove(obj, Integer.valueOf(uid));
    	CurrentStatus.getINSTANCE().getAdmin().publishersButtonPressed();
    	obj.disconnect();
    	exit(event);

    }

}

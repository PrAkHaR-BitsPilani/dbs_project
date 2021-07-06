package Controllers;

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

public class AddPublisherController {

    @FXML
    private Button addPublisherClose;

    @FXML
    private TextField publisher_name;

    @FXML
    private Button addPublisherContinue;

    @FXML
    private Label addPublisherLabel;

    @FXML
    void addPublisher(ActionEvent event) {
    	
    	String u = publisher_name.getText();
    	String name = "";
    	for(int i = 0; i < u.length(); i++)
    	{
    		if(u.charAt(i) == 39)
    		{
    			name += u.charAt(i);
    			name += u.charAt(i);
    		}
    		else
    		{
    			name += u.charAt(i);
    		}
    	}
    	if(!Pattern.matches("[a-z A-z0-9-'.]{2,}", name))
    	{
    		addPublisherLabel.setText("Invalid name!");
    		return;
    	}
    	
    	SQL obj = new SQL();
    	obj.createConnection();
    	EnvironmentCreator.useDatabase(obj, "library");
    	PublisherHandler.add(obj, name);
    	CurrentStatus.getINSTANCE().getAdmin().publishersButtonPressed();
    	obj.disconnect();
    	exit(event);

    }

    @FXML
    void exit(ActionEvent event) {
    	Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
    	primaryStage.close();
    }

}

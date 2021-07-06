package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Backend.SQL;
import Backend.EnvironmentCreator;
import java.util.regex.Pattern;
import Backend.AuthorHandler;

public class AddAuthorController {

    @FXML
    private Button addAuthorClose;

    @FXML
    private TextField author_name;

    @FXML
    private Button addAuthorContinue;

    @FXML
    private Label addAuthorLabel;

    @FXML
    void addAuthor(ActionEvent event) {
    		
    	String u = author_name.getText();
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
    		addAuthorLabel.setText("Invalid name!");
    		return;
    	}
    	
    	SQL obj = new SQL();
    	obj.createConnection();
    	EnvironmentCreator.useDatabase(obj, "library");
    	AuthorHandler.add(obj, name);
    	CurrentStatus.getINSTANCE().getAdmin().authorsButtonPressed();
    	obj.disconnect();
    	exit(event);
    }

    @FXML
    void exit(ActionEvent event) {
    	Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
    	primaryStage.close();
    }

}

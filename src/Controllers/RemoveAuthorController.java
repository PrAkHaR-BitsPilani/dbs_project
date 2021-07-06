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
import java.sql.ResultSet;
import java.util.regex.Pattern;
import Backend.AuthorHandler;

public class RemoveAuthorController {

    @FXML
    private Button removeMemberClose;

    @FXML
    private TextField author_uid;

    @FXML
    private Button removeAuthorContinue;

    @FXML
    private Label removeAuthorLabel;
    
    private boolean sure = false;
    
    private String prev = "";

    @FXML
    void exit(ActionEvent event) {
    	Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
    	primaryStage.close();
    }

    @FXML
    void removeAuthor(ActionEvent event) {

    	String uid = author_uid.getText();

    	if(!(prev.equals(uid) && sure))
    		sure = false;
    	
    	prev = uid;
    	
    	if(!Pattern.matches("[0-9]+", uid))
    	{
    		removeAuthorLabel.setText("Invalid UID!");
    		return;
    	}
    	SQL obj = new SQL();
    	obj.createConnection();
    	EnvironmentCreator.useDatabase(obj, "library");
    	
    	ResultSet rs = obj.query("SELECT * FROM authors WHERE author_id = " + uid + ";");

    	try
    	{
    		if(!rs.next())
        	{
        		removeAuthorLabel.setText("UID does not exist!");
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
    		removeAuthorLabel.setText("Click again to confirm!");
    		sure = true;
    		return;
    	}
    	
    	AuthorHandler.remove(obj, Integer.valueOf(uid));
    	CurrentStatus.getINSTANCE().getAdmin().authorsButtonPressed();
    	obj.disconnect();
    	exit(event);

    }

}

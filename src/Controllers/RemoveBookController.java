package Controllers;

import java.sql.ResultSet;
import java.util.regex.Pattern;

import Backend.BookHandler;
import Backend.EnvironmentCreator;
import Backend.SQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RemoveBookController {

    @FXML
    private Button removeMemberClose;

    @FXML
    private TextField book_uid;

    @FXML
    private Button removeBookContinue;

    @FXML
    private Label removeBookLabel;
    
    private boolean sure = false;
    
    private String prev = "";

    @FXML
    void exit(ActionEvent event) {
    	Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
    	primaryStage.close();
    }

    @FXML
    void removeBook(ActionEvent event) {

    	String uid = book_uid.getText();
    	
    	if(!(prev.equals(uid) && sure))
    		sure = false;
    	
    	prev = uid;
    	
    	if(!Pattern.matches("[0-9]+", uid))
    	{
    		removeBookLabel.setText("Invalid UID!");
    		return;
    	}
    	
    	SQL obj = new SQL();
    	obj.createConnection();
    	EnvironmentCreator.useDatabase(obj, "library");
    	
    	ResultSet rs = obj.query("SELECT * FROM books WHERE uid = " + uid + ";");

    	try
    	{
    		if(!rs.next())
        	{
        		removeBookLabel.setText("UID does not exist!");
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
    		removeBookLabel.setText("Click again to confirm!");
    		sure = true;
    		return;
    	}
    	
    	BookHandler.remove(obj, Integer.valueOf(uid));
    	CurrentStatus.getINSTANCE().getAdmin().booksButtonPressed();
    	obj.disconnect();
    	exit(event);
    }
}

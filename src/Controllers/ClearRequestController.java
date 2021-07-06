package Controllers;

import java.sql.ResultSet;
import java.util.regex.Pattern;

import Backend.EnvironmentCreator;
import Backend.RequestsHandler;
import Backend.SQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClearRequestController {

    @FXML
    private Button clearRequestClose;

    @FXML
    private TextField clearRequestBookName;

    @FXML
    private Button clearRequestContinue;

    @FXML
    private Label clearRequestLabel;
    
    private boolean sure = false;
    
    private String prev = "";

    @FXML
    void clearRequest(ActionEvent event) 
    {
    	String u = clearRequestBookName.getText();
    	if(!(prev.equals(u) && sure))
    		sure = false;
    	
    	prev = u;
    	
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
    	
    	if(!Pattern.matches(".{1,}", name))
    	{
    		clearRequestLabel.setText("Invalid name!");
    		return;
    	}
    	else
    	{
    		SQL obj = new SQL();
    		obj.createConnection();
    		EnvironmentCreator.useDatabase(obj, "library");
    		
    		ResultSet rs = obj.query("SELECT * FROM requests WHERE book_name = '" + name + "';");
    		try
    		{
    			if(rs.next())
    			{
    				if(!sure)
    		    	{
    		    		clearRequestLabel.setText("Click again to confirm!");
    		    		sure = true;
    		    		return;
    		    	}
    				RequestsHandler.remove(obj, name);
    				obj.disconnect();
    				CurrentStatus.getINSTANCE().getAdmin().requestsButtonPressed();
    				exit(event);    				
    			}
    			else
    			{
    				clearRequestLabel.setText("No such record exists!");
    				obj.disconnect();
    				return;    				
    			}
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}    		
    	}

    }

    @FXML
    void exit(ActionEvent event) {
    	Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
    	primaryStage.close();
    }

}

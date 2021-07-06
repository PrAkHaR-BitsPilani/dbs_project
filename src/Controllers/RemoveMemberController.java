package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Backend.SQL;

import java.util.regex.Pattern;

import Backend.EnvironmentCreator;
import Backend.MemberHandler;
import java.sql.ResultSet;

public class RemoveMemberController {
	
	@FXML
    private Button removeMemberClose;

    @FXML
    private TextField member_uid;

    @FXML
    private Button removeMemberContinue;

    @FXML
    private Label removeMemberLabel;
    
    private boolean sure = false;
    
    private String prev = "";

    @FXML
    void exit(ActionEvent event) {
    	Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
    	primaryStage.close();
    }

    @FXML
    void removeMember(ActionEvent event) {
    	
    	String uid = member_uid.getText();
    	
    	if(!(prev.equals(uid) && sure))
    		sure = false;
    	
    	prev = uid;
    	
    	if(!Pattern.matches("[0-9]+", uid))
    	{
    		removeMemberLabel.setText("Invalid UID!");
    		return;
    	}
    	SQL obj = new SQL();
    	obj.createConnection();
    	EnvironmentCreator.useDatabase(obj, "library");
    	
    	ResultSet rs = obj.query("SELECT * FROM members WHERE uid = " + uid + ";");

    	try
    	{
    		if(!rs.next())
        	{
        		removeMemberLabel.setText("UID does not exist!");
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
    		removeMemberLabel.setText("Click again to confirm!");
    		sure = true;
    		return;
    	}
    	
    	MemberHandler.unregister(obj, Integer.valueOf(uid));
    	CurrentStatus.getINSTANCE().getAdmin().membersButtonPressed();
    	obj.disconnect();
    	exit(event); 	
    }
}

package Controllers;

import java.sql.ResultSet;
import java.util.regex.Pattern;

import Backend.EnvironmentCreator;
import Backend.MailHandler;
import Backend.MemberHandler;
import Backend.SQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditMemberController {

    @FXML
    private TextField editMemberUID;

    @FXML
    private TextField editMemberPassword;

    @FXML
    private TextArea editMemberAddress;

    @FXML
    private TextField editMemberPhone;

    @FXML
    private TextField editMemberEmail;

    @FXML
    private Button editMemberClose;

    @FXML
    private Button editMemberContinue;

    @FXML
    private Label editMemberLabel;

    @FXML
    private TextField editMemberName;
    
    private boolean sure = false;
    
    private String prev = "";
    
    @FXML
    void uidTyped(ActionEvent event)
    {
    	SQL obj = new SQL();
    	obj.createConnection();
    	EnvironmentCreator.useDatabase(obj, "library");
    	
    	String uid = editMemberUID.getText();
    	
    	if(!Pattern.matches("[0-9]+", uid))
    	{
    		editMemberLabel.setText("Invalid UID");
    		return;    		
    	}
    	
    	ResultSet rs = obj.query("SELECT * FROM members WHERE uid = " + uid + ";");
    	
    	try
    	{
    		if(rs.next())
    		{
    			editMemberName.setText(rs.getString("name"));
    			editMemberPassword.setText(rs.getString("password"));
    			editMemberAddress.setText(rs.getString("address"));
    			editMemberPhone.setText(rs.getString("phone"));
    			editMemberEmail.setText(rs.getString("email"));    			
    		}
    		else
    		{
    			editMemberLabel.setText("UID does not exist!");
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	obj.disconnect();
    }

    @FXML
    void editMember(ActionEvent event) {
    	
    	String uid = editMemberUID.getText();
    	String u = editMemberName.getText();
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
    	
    	String password = editMemberPassword.getText();
    	String address = editMemberAddress.getText();
    	String phone = editMemberPhone.getText();
    	String email = editMemberEmail.getText();
    	
    	if(!(prev.equals(uid) && sure))
    		sure = false;
    	
    	prev = uid;
    	
    	if(!Pattern.matches("[0-9]+", uid))
    	{
    		editMemberLabel.setText("Invalid UID!");
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
        		editMemberLabel.setText("UID does not exist!");
        		obj.disconnect();
        		return;
        	}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}    	
    	
    	if(!Pattern.matches("[a-z A-z-'.]{2,}", name))
    	{
    		editMemberLabel.setText("Invalid name!");
    	}
    	else if(!Pattern.matches(".{8,25}", password))
    	{
    		editMemberLabel.setText("Password must be at between 8 and 25 characters long!");
    	}
    	else if(!Pattern.matches("^[#.0-9a-zA-Z\\s,-]+$", address))
    	{
    		editMemberLabel.setText("Invalid address!");
    	}
    	else if(!Pattern.matches("[0-9]{10}", phone))
    	{
    		editMemberLabel.setText("Invalid phone number!");
    	}
    	else if(!Pattern.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", email))
    	{
    		editMemberLabel.setText("Invalid email address!");
    	}
    	else
    	{
    		if(!sure)
        	{
        		editMemberLabel.setText("Click again to confirm!");
        		sure = true;
        		return;
        	}
    		ResultSet resultSet = obj.query("SELECT email FROM members WHERE uid = " + uid + ";");
    		boolean newMail = false;
    		String oldMail = "";
    		try {
    			if(resultSet.next())
    			{
    				oldMail = resultSet.getString(1);
    				if(email.equalsIgnoreCase(oldMail));
    					newMail	= true;
    			}
    			else newMail = true;
    		}
    		catch(Exception e) {
    			e.printStackTrace();
    		}
    		
			if(MemberHandler.edit(obj, uid, name, password, address, phone, email) == false)
			{
				editMemberLabel.setText("Email address used!");
    			return;
			}
			
    		try
    		{
    		if(newMail)
    		{
    			String sub = "Email Changed";
        		
        		String message = "Dear " + name + ",\n\n" + 
        		"Your email registered with CadMus has been changed to this email.\n" + 
        		"Have a great day ahead!\n\nTeam CadMus.";
        		
        		new MailHandler("cadmus.library.2020@gmail.com", "$$daDBprojekt$$", email, sub, message).start();
        		
        		
        		{
        			sub = "Email Changed";
            		
            		message = "Dear " + name + ",\n\n" + 
            		"Your email registered with CadMus has been changed to " + email + ".\n" + 
            		"Have a great day ahead!\n\nTeam CadMus.";
            		
            		new MailHandler("cadmus.library.2020@gmail.com", "$$daDBprojekt$$", oldMail, sub, message).start();
        		}
    		}
    		}
    		catch(Exception e)
    		{}
    		
    		CurrentStatus.getINSTANCE().getAdmin().membersButtonPressed();
    		obj.disconnect();
    		exit(event);
    	}

    }

    @FXML
    void exit(ActionEvent event) {
    	Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
    	primaryStage.close();
    }

}

package Controllers;

import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Backend.SQL;
import Backend.MemberHandler;
import Backend.EnvironmentCreator;
import Backend.MailHandler;

public class AddMemberController {
	@FXML
    private TextField addMemberName;

    @FXML
    private TextField addMemberPassword;

    @FXML
    private TextArea addMemberAddress;

    @FXML
    private TextField addMemberPhone;

    @FXML
    private TextField addMemberEmail;

    @FXML
    private Button addMemberClose;

    @FXML
    private Button addMemberContinue;

    @FXML
    private Label addMemberLabel;
    
    public void addMember(ActionEvent event)
    {
    	String u = addMemberName.getText();
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
    	String password = addMemberPassword.getText();
    	String address = addMemberAddress.getText();
    	String phone = addMemberPhone.getText();
    	String email = addMemberEmail.getText();
    	
    	if(!Pattern.matches("[a-z A-z-'.]{2,}", name))
    	{
    		addMemberLabel.setText("Invalid name!");
    	}
    	else if(!Pattern.matches(".{8,25}", password))
    	{
    		addMemberLabel.setText("Password must be between 8 and 25 characters long!");
    	}
    	else if(!Pattern.matches("^[#.0-9a-zA-Z\\s,-]+$", address))
    	{
    		addMemberLabel.setText("Invalid address!");
    	}
    	else if(!Pattern.matches("[0-9]{10}", phone))
    	{
    		addMemberLabel.setText("Invalid phone number!");
    	}
    	else if(!Pattern.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", email))
    	{
    		addMemberLabel.setText("Invalid email address!");
    	}
    	else
    	{
    		SQL obj = new SQL();
    		obj.createConnection();
    		EnvironmentCreator.useDatabase(obj, "library");    		
			if(MemberHandler.register(obj, name, password, address, phone, email) == false)
			{
				addMemberLabel.setText("Email already used!");
    			return;
			}
    		
    		try
    		{
    			String sub = "Welcome to CadMus";
        		
        		
        		String message = "Dear " + name + ",\n\n" + 
        				"Thanks for joining us at CadMus.\n" + 
        				"Have a great day ahead!\n\nTeam CadMus.";
        		
        		MailHandler obj_mail = new MailHandler("cadmus.library.2020@gmail.com", "$$daDBprojekt$$", email, sub, message);
        		obj_mail.start();
    			
    		}
    		catch(Exception e)
    		{}    		
    		
    		CurrentStatus.getINSTANCE().getAdmin().membersButtonPressed();
    		obj.disconnect();
    		exit(event);
    	}
    }
    
    public void exit(ActionEvent event)
    {
    	Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
    	primaryStage.close();
    }

}

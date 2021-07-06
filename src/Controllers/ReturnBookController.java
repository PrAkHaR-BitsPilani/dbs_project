package Controllers;

import java.sql.ResultSet;
import java.util.regex.Pattern;

import Backend.BorrowedHandler;
import Backend.EnvironmentCreator;
import Backend.OverdueCalc;
import Backend.SQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ReturnBookController {

    @FXML
    private TextField returnBookMemberUID;

    @FXML
    private Button returnBookClose;

    @FXML
    private Button returnBookContinue;

    @FXML
    private Label returnBookLabel;

    @FXML
    private TextField returnBookBookUID;
    
    private boolean sure = false;
    
    private String prevMem = "";
    private String prevBook = "";

    @FXML
    void exit(ActionEvent event) {
    	Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
    	primaryStage.close();
    }

    @FXML
    void returnBook(ActionEvent event) {
    	
    	if(returnBookContinue.getText().equals("OK"))
    		exit(event);
    	
    	String memberUID = returnBookMemberUID.getText();
    	String bookUID = returnBookBookUID.getText();
    	
    	if(!(prevMem.equals(memberUID) && prevBook.equals(bookUID) && sure))
    		sure = false;
    	
    	prevMem = memberUID;
    	prevBook = bookUID;
    	
    	SQL objMember = new SQL();
    	SQL objBook = new SQL();
    	
    	objMember.createConnection();
    	objBook.createConnection();
    	
    	EnvironmentCreator.useDatabase(objMember, "library");
    	EnvironmentCreator.useDatabase(objBook, "library");
    	
    	if(!Pattern.matches("[0-9]+", memberUID))
    	{
    		returnBookLabel.setText("Invalid member UID!");
    		return;
    	}
    	else if(!Pattern.matches("[0-9]+", bookUID))
    	{
    		returnBookLabel.setText("Invalid book UID!");
    		return;
    	}
    	
    	ResultSet rs = objMember.query("SELECT * FROM members WHERE uid = " + memberUID + ";");

    	try
    	{
    		if(!rs.next())
        	{
        		returnBookLabel.setText("Member UID does not exist!");
        		objMember.disconnect();
        		objBook.disconnect();
        		return;
        	}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    	rs = objBook.query("SELECT * FROM books WHERE uid = " + bookUID + ";");

    	try
    	{
    		if(!rs.next())
        	{    			
        		returnBookLabel.setText("Book UID does not exist!");
        		objMember.disconnect();
        		objBook.disconnect();
        		return;
        	}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    	rs = objMember.query("SELECT * FROM borrowed WHERE memberuid = " + memberUID + " AND bookuid = " + bookUID + ";");
    	
    	try
    	{
    		if(rs.next())
    		{
    			if(!sure)
    	    	{
    	    		returnBookLabel.setText("Click again to confirm!");
    	    		sure = true;
    	    		return;
    	    	}
    			long od = OverdueCalc.calculate(objMember, Integer.valueOf(memberUID), Integer.valueOf(bookUID));
    			returnBookLabel.setText("Overdue = " + String.valueOf(od));
    			returnBookContinue.setText("OK");
    			returnBookMemberUID.setDisable(true);
    			returnBookBookUID.setDisable(true);
    			BorrowedHandler.remove(objMember, Integer.valueOf(memberUID), Integer.valueOf(bookUID));
    			objMember.disconnect();
        		objBook.disconnect();
        		CurrentStatus.getINSTANCE().getAdmin().borrowedButtonPressed();
    		}
    		else
    		{
    			returnBookLabel.setText("No such record exists!");
        		objMember.disconnect();
        		objBook.disconnect();
        		return;    			
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}

    }
}

package Controllers;

import java.sql.ResultSet;
import java.util.regex.Pattern;

import Backend.BorrowedHandler;
import Backend.EnvironmentCreator;
import Backend.SQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BorrowBookController {

	private boolean sure = false;
    
    private String prev = "";

	
    @FXML
    private Button BorrowBookClose;

    @FXML
    private TextField BorrowBookUID;

    @FXML
    private Button BorrowBookContinue;

    @FXML
    private Label BorrowBookLabel;

    @FXML
    void BorrowBook(ActionEvent event) {
    	String uid = BorrowBookUID.getText();
    	
    	if(!(prev.equals(uid) && sure))
    		sure = false;
    	
    	prev = uid;
    	
    	if(!Pattern.matches("[0-9]+", uid))
    	{
    		BorrowBookLabel.setText("Invalid UID!");
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
        		BorrowBookLabel.setText("UID does not exist!");
        		obj.disconnect();
        		return;
        	}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}    	
    	
    	rs = obj.query("SELECT * from borrowed WHERE memberuid = " + CurrentStatus.getINSTANCE().getID() + " and bookuid = " + uid + ";");
    	try {
    		if(rs.next())
    		{
    			BorrowBookLabel.setText("Already borrowed!");
    			obj.disconnect();
    			return;
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    	rs = obj.query("SELECT available from Books where uid = " + uid + ";");
    	try {
    		rs.next();
    		if(rs.getInt("available") == 0)
    		{
    			BorrowBookLabel.setText("Out of Stock!");
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
    		BorrowBookLabel.setText("Click again to confirm!");
    		sure = true;
    		return;
    	}
    	
    	BorrowedHandler.add(obj,Integer.valueOf(CurrentStatus.getINSTANCE().getID()), Integer.valueOf(uid));
    	
    	CurrentStatus.getINSTANCE().getMember().booksButtonPressed();
    	obj.disconnect();
    	exit(event);
    }

    @FXML
    void exit(ActionEvent event) {
    	Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
    	primaryStage.close();
    }

}

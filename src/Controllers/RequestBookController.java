package Controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


import Backend.EnvironmentCreator;
import Backend.RequestsHandler;
import Backend.SQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RequestBookController implements Initializable {
	
	private boolean sure = false;
    
    private String prevName = "";
    private String prevAuthor = "";
    private String prevPublisher = "";
    private String prevYear = "";
    
    @FXML
    private TextField RequestBookName;

    @FXML
    private Button RequestBookClose;

    @FXML
    private Button RequestBookContinue;

    @FXML
    private Label RequestBookLabel;

    @FXML
    private ComboBox<String> RequestBookPublisherChoice;

    @FXML
    private ComboBox<String> RequestBookAuthorChoice;

    @FXML
    private TextField RequestBookYear;

    public void initialize(URL location, ResourceBundle resources)
    {
    	SQL author = new SQL();
    	SQL publisher = new SQL();
    	author.createConnection();
    	publisher.createConnection();
    	EnvironmentCreator.useDatabase(author, "library");
    	EnvironmentCreator.useDatabase(publisher, "library");
    	
    	ResultSet au = author.query("SELECT * FROM authors ORDER BY author_name;");
    	ResultSet pu = publisher.query("SELECT * FROM publishers ORDER BY publisher_name;");
    	
    	ObservableList <String> authorList = FXCollections.observableArrayList();
    	ObservableList <String> publisherList = FXCollections.observableArrayList();    
    	try
    	{
    		while(au.next())
    		{
    			authorList.add(au.getString("author_name"));    			
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    	try
    	{
    		while(pu.next())
    		{
    			publisherList.add(pu.getString("publisher_name"));    			
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    	RequestBookAuthorChoice.setItems(authorList);
    	RequestBookPublisherChoice.setItems(publisherList);
    	
    	
    	author.disconnect();
    	publisher.disconnect();
    }
    
    @FXML
    void RequestBook(ActionEvent event) {
    	String u = RequestBookName.getText();
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
    	String author = RequestBookAuthorChoice.getValue();
    	String publisher = RequestBookPublisherChoice.getValue();
    	String year = RequestBookYear.getText();
    	
    	if(((!prevName.equals(name)) || (!prevAuthor.equals(author)) || (!prevPublisher.equals(publisher)) || (!prevYear.equals(year))) && sure)
    		sure = false;
    	
    	prevName = name;
    	prevAuthor = author;
    	prevPublisher = publisher;
    	prevYear = year;

    	if(!Pattern.matches(".{1,}", name))
    	{
    		RequestBookLabel.setText("Invalid book name!");
    		return;
    	}
    	else if(author == null)
    	{
    		RequestBookLabel.setText("Select an author!");
    		return;    		
    	}
    	else if(publisher == null)
    	{
    		RequestBookLabel.setText("Select a publisher!");
    		return;    		
    	}
    	else if(!Pattern.matches("[0-9]{0,4}", year))
    	{
    		RequestBookLabel.setText("Invalid year!");
    		return;
    	}
    	
    	SQL obj = new SQL();
    	obj.createConnection();
    	EnvironmentCreator.useDatabase(obj, "library");
    	SQL objAuthor = new SQL();
    	objAuthor.createConnection();
    	EnvironmentCreator.useDatabase(objAuthor, "library");
    	SQL objPublisher = new SQL();
    	objPublisher.createConnection();
    	EnvironmentCreator.useDatabase(objPublisher, "library");
    	
    	ResultSet au = objAuthor.query("SELECT * FROM authors WHERE author_name = '" + author + "';");
    	ResultSet pu = objPublisher.query("SELECT * FROM publishers WHERE publisher_name = '" + publisher + "';");
    	
    	try {
    		au.next();
        	pu.next();
        	ResultSet rs = obj.query("SELECT * from requests WHERE member_id = " + CurrentStatus.getINSTANCE().getID() + " and book_name = '" + name + "' and author_id = " + au.getInt("author_id") + " and publisher_id = " + pu.getInt("publisher_id") + ";");
    		if(rs.next())
    		{
    			
    			RequestBookLabel.setText("Already requested!");
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
    		RequestBookLabel.setText("Click again to confirm!");
    		sure = true;
    		return;
    	}
    	
    	try
    	{
        	RequestsHandler.add(obj, Integer.valueOf(CurrentStatus.getINSTANCE().getID()), name, Integer.valueOf(au.getInt("author_id")), Integer.valueOf(pu.getInt("publisher_id")), year);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}	
    	obj.disconnect();
    	CurrentStatus.getINSTANCE().getMember().requestButtonPressed();
    	exit(event);
    }
    
    

    @FXML
    void exit(ActionEvent event) {
    	Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
    	primaryStage.close();
    }

}

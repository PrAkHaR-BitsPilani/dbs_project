package Controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import Backend.BookHandler;
import Backend.EnvironmentCreator;
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

public class AddBookController implements Initializable {

    @FXML
    private TextField addBookName;

    @FXML
    private Button addBookClose;

    @FXML
    private Button addBookContinue;

    @FXML
    private Label addBookLabel;

    @FXML
    private ComboBox<String> addBookPublisherChoice;

    @FXML
    private ComboBox<String> addBookAuthorChoice;

    @FXML
    private TextField addBookYear;

    @FXML
    private TextField addBookGenre;

    @FXML
    private TextField addBookTotal;

    @FXML
    private TextField addBookPrice;

    @FXML
    private TextField addBookLocation;

    @FXML
    private ComboBox<String> addBookStateChoice;

    @FXML
    private ComboBox<String> addBookTypeChoice;
    
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
    	ObservableList <String> stateList = FXCollections.observableArrayList();
    	ObservableList <String> typeList = FXCollections.observableArrayList();
    	
    	stateList.add("Good");
    	stateList.add("Average");
    	stateList.add("Bad");
    	
    	typeList.add("Paperback");
    	typeList.add("Hardback");
    	
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
    	
    	addBookAuthorChoice.setItems(authorList);
    	addBookPublisherChoice.setItems(publisherList);
    	addBookStateChoice.setItems(stateList);
    	addBookTypeChoice.setItems(typeList);
    	
    	author.disconnect();
    	publisher.disconnect();
    }

    @FXML
    void addBook(ActionEvent event) {
    	
    	String u = addBookName.getText();
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
    	String author = addBookAuthorChoice.getValue();
    	String publisher = addBookPublisherChoice.getValue();
    	String year = addBookYear.getText();
    	String genre = addBookGenre.getText();
    	String total = addBookTotal.getText();
    	String price = addBookPrice.getText();
    	String location = addBookLocation.getText();
    	String condition = addBookStateChoice.getValue();
    	String type = addBookTypeChoice.getValue();

    	if(!Pattern.matches(".{1,}", name))
    	{
    		addBookLabel.setText("Invalid book name!");
    		return;
    	}
    	else if(author == null)
    	{
    		addBookLabel.setText("Select an author!");
    		return;    		
    	}
    	else if(publisher == null)
    	{
    		addBookLabel.setText("Select a publisher!");
    		return;    		
    	}
    	else if(!Pattern.matches("[0-9]{0,4}", year))
    	{
    		addBookLabel.setText("Invalid year!");
    		return;
    	}
    	else if (!Pattern.matches("[0-9]+", total))
    	{
    		addBookLabel.setText("Total copies must be a number!");
    		return;
    	}
    	else if (!Pattern.matches("[0-9]+([.]*[0-9]+)", price))
    	{
    		addBookLabel.setText("Price must be a number!");
    		return;
    	}
    	else if(condition == null)
    	{
    		addBookLabel.setText("Select a condition!");
    		return;
    	}
    	else if(type == null)
    	{
    		addBookLabel.setText("Select a type!");
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
    	
    	int s;
    	boolean t;
    	
    	if(condition.equals("Good"))
    		s = 2;
    	else if(condition.equals("Average"))
    		s = 1;
    	else
    		s = 0;
    	
    	if(type.equals("Paperback"))
    		t = false;
    	else
    		t = true;
    	
    	ResultSet au = objAuthor.query("SELECT * FROM authors WHERE author_name = '" + author + "';");
    	ResultSet pu = objPublisher.query("SELECT * FROM publishers WHERE publisher_name = '" + publisher + "';");
    	try
    	{
        	au.next();
        	pu.next();
        	if(BookHandler.add(obj,
        			name,
        			Integer.valueOf(total),
        			year,
        			genre,
        			au.getInt("author_id"),
        			pu.getInt("publisher_id"),
        			Float.valueOf(price),
        			location,
        			s,
        			t) == false)
        	{
        		addBookLabel.setText("Duplicate entry!");
        		return;
        	}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}	
    	obj.disconnect();
    	CurrentStatus.getINSTANCE().getAdmin().booksButtonPressed();
    	exit(event);
    }

    @FXML
    void exit(ActionEvent event) {
    	Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
    	primaryStage.close();
    }

}

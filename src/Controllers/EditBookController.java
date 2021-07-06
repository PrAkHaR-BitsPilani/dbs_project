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

public class EditBookController implements Initializable{

    @FXML
    private TextField editBookUID;
    
    @FXML
    private TextField editBookName;

    @FXML
    private Button editBookClose;

    @FXML
    private Button editBookContinue;

    @FXML
    private Label editBookLabel;

    @FXML
    private ComboBox<String> editBookPublisherChoice;

    @FXML
    private ComboBox<String> editBookAuthorChoice;

    @FXML
    private TextField editBookYear;

    @FXML
    private TextField editBookGenre;

    @FXML
    private TextField editBookTotalCopies;

    @FXML
    private TextField editBookPrice;

    @FXML
    private TextField editBookLocation;

    @FXML
    private ComboBox<String> editBookCondition;

    @FXML
    private ComboBox<String> editBookCover;
    
    private boolean sure = false;
    
    private String prev = "";
    
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
    	
    	editBookAuthorChoice.setItems(authorList);
    	editBookPublisherChoice.setItems(publisherList);
    	editBookCondition.setItems(stateList);
    	editBookCover.setItems(typeList);
    	
    	author.disconnect();
    	publisher.disconnect();
    }
    
    @FXML
    public void uidTyped(ActionEvent event)
    {
    	String uid = editBookUID.getText();
    	if(!Pattern.matches("[0-9]+", uid))
    	{
    		editBookLabel.setText("Invalid UID!");
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
        		editBookLabel.setText("UID does not exist!");
        		obj.disconnect();
        		return;
        	}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}    	
    	SQL objAuthor = new SQL();
    	objAuthor.createConnection();
    	EnvironmentCreator.useDatabase(objAuthor, "library");
    	SQL objPublisher = new SQL();
    	objPublisher.createConnection();
    	EnvironmentCreator.useDatabase(objPublisher, "library");
    	
    	try
    	{
        	ResultSet au = objAuthor.query("SELECT * FROM authors WHERE author_id = " + rs.getInt("author"));
        	ResultSet pu = objPublisher.query("SELECT * FROM publishers WHERE publisher_id = " + rs.getInt("publisher"));
        	au.next();
        	pu.next();
        	
        	editBookName.setText(rs.getString("name"));
        	editBookAuthorChoice.getSelectionModel().select(au.getString("author_name"));
        	editBookPublisherChoice.getSelectionModel().select(pu.getString("publisher_name"));
        	editBookYear.setText(rs.getString("year"));
        	editBookGenre.setText(rs.getString("genre"));
        	editBookTotalCopies.setText(rs.getString("total"));
        	editBookPrice.setText(rs.getString("price"));
        	editBookLocation.setText(rs.getString("location"));
        	
        	String condition, type;
        	int c = rs.getInt("state");
        	int t = rs.getInt("type");
        	
        	if(c == 2)
        		condition = "Good";
        	else if (c == 1)
        		condition = "Average";
        	else
        		condition = "Bad";
        	
        	if(t == 1)
        		type = "Hardback";
        	else
        		type = "Paperback";
        	
        	editBookCondition.getSelectionModel().select(condition);
        	editBookCover.getSelectionModel().select(type);
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}

    	obj.disconnect();
    	objAuthor.disconnect();
    	objPublisher.disconnect();
    }

    @FXML
    void editBook(ActionEvent event) {

    	String uid = editBookUID.getText();
    	String u = editBookName.getText();
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
    	String author = editBookAuthorChoice.getValue();
    	String publisher = editBookPublisherChoice.getValue();
    	String year = editBookYear.getText();
    	String genre = editBookGenre.getText();
    	String total = editBookTotalCopies.getText();
    	String price = editBookPrice.getText();
    	String location = editBookLocation.getText();
    	String condition = editBookCondition.getValue();
    	String type = editBookCover.getValue();
    	
    	if(!(prev.equals(uid) && sure))
    		sure = false;
    	
    	prev = uid;
    	
    	if(!Pattern.matches("[0-9]+", uid))
    	{
    		editBookLabel.setText("Invalid UID!");
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
        		editBookLabel.setText("UID does not exist!");
        		obj.disconnect();
        		return;
        	}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    	if(author == null)
    	{
    		editBookLabel.setText("Select an author!");
    		return;    		
    	}
    	else if(publisher == null)
    	{
    		editBookLabel.setText("Select a publisher!");
    		return;    		
    	}
    	else if(!Pattern.matches("[0-9]{0,4}", year))
    	{
    		editBookLabel.setText("Invalid year!");
    	}
    	else if (!Pattern.matches("[0-9]+", total))
    	{
    		editBookLabel.setText("Total copies must be a number!");
    		return;
    	}
    	else if (!Pattern.matches("[0-9]+([.]*[0-9]+)", price))
    	{
    		editBookLabel.setText("Price must be a number!");
    		return;
    	}
    	else if(condition == null)
    	{
    		editBookLabel.setText("Select a condition!");
    		return;
    	}
    	else if(type == null)
    	{
    		editBookLabel.setText("Select a type!");
    		return;
    	}
    	
    	if(!sure)
    	{
    		editBookLabel.setText("Click again to confirm!");
    		sure = true;
    		return;
    	}
    	
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
    	
    	int diff, avail;
    	
    	try
    	{
    		au.next();
        	pu.next();
        	diff = Integer.valueOf(total) - rs.getInt("total");
        	avail = rs.getInt("available") + diff;
        	if(BookHandler.edit(obj, name, uid, avail, Integer.valueOf(total), year, genre, au.getInt("author_id"),
        			pu.getInt("publisher_id"), Float.valueOf(price), location, s, t) == false) {
        		editBookLabel.setText("Duplicate data!");
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

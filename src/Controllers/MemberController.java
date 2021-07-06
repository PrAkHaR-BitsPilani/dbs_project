package Controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import Backend.EnvironmentCreator;
import Backend.SQL;
import Backend.SearchHandler;
import TableStyles.BooksTable;
import TableStyles.BorrowedTable;
import TableStyles.LogTable;
import TableStyles.RequestsTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MemberController implements Initializable {
	
	private double xoffset = 0.0;
	private double yoffset = 0.0;
	
	private String ID;

    @FXML
    private Button searchButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button requestButton;
    @FXML
    private Button logButton;
    @FXML
    private Button logout;
    
    

    @FXML
    private Pane logPane;  
    @FXML
    private TextField searchBarLog;
    @FXML
    private ComboBox<String> logSearchChoice;
    @FXML
    private TableView<LogTable> logTable;
    @FXML
    private TableColumn<LogTable, Integer> logTransactionUID;
    @FXML
    private TableColumn<LogTable, Integer> logBookUID;
    @FXML
    private TableColumn<LogTable, String> logDateBorrowed;
    @FXML
    private TableColumn<LogTable, String> logDateReturned;
    @FXML
    private TableColumn<LogTable, Integer> logOverdue;
    @FXML
    private TableColumn<LogTable, String> logBookName;


    @FXML
    private Pane requestPane;
    @FXML
    private TextField searchBarRequests;
    @FXML
    private TableView<RequestsTable> requestsTable;
    @FXML
    private TableColumn<RequestsTable, String> requestsBookName;
    @FXML
    private TableColumn<RequestsTable, String> requestsAuthorName;
    @FXML
    private TableColumn<RequestsTable, String> requestsPublisherName;
    @FXML
    private TableColumn<RequestsTable, String> requestsBookYear;
    @FXML
    private ComboBox<String> requestSearchChoice;
    @FXML
    private Button requestBookButton;

    @FXML
    private Pane profilePane;
    @FXML
    private Text nameField;
    @FXML
    private Text emailField;
    @FXML
    private Text phoneField;
    @FXML
    private Text addressField;
    @FXML
    private Text dateField;
    @FXML
    private Label memberNameLabel;
    @FXML
    private Label memberEmailLabel;
    @FXML
    private Label memberPhoneLabel;
    @FXML
    private Label memberAddressLabel;
    @FXML
    private Label memberJoinDateLabel;
    @FXML
    private Label memberIDLabel;
    @FXML
    private TableView<BorrowedTable> borrowedTable;
    @FXML
    private TableColumn<BorrowedTable, Integer> borrowedBookUID;
    @FXML
    private TableColumn<BorrowedTable, String> borrowedBookBorrowDate;
    @FXML
    private TableColumn<BorrowedTable, String> borrowedBookDueDate;


    @FXML
    private Pane searchPane;
    @FXML
    private Button borrowBookButton;    
    @FXML
    private TextField searchBarBooks;
    @FXML
    private TableView<BooksTable> booksTable;
    @FXML
    private TableColumn<BooksTable, Integer> bookUID;
    @FXML
    private TableColumn<BooksTable, String> bookName;
    @FXML
    private TableColumn<BooksTable, Integer> bookAvailable;
    @FXML
    private TableColumn<BooksTable, Integer> bookTotal;
    @FXML
    private TableColumn<BooksTable, String> bookAuthor;
    @FXML
    private TableColumn<BooksTable, String> bookPublisher;
    @FXML
    private TableColumn<BooksTable, String> bookGenre;
    @FXML
    private TableColumn<BooksTable, String> bookYear;
    @FXML
    private TableColumn<BooksTable, Float> bookPrice;
    @FXML
    private TableColumn<BooksTable, String> bookLocation;
    @FXML
    private TableColumn<BooksTable, String> bookState;
    @FXML
    private TableColumn<BooksTable, String> bookType;
    @FXML
    private ComboBox<String> bookSearchChoice;
    
    
    
    public void initialize(URL location, ResourceBundle resources)
    {		
    	
    	CurrentStatus.getINSTANCE().setMember(this);
    	
    	ObservableList <String> bookSearch = FXCollections.observableArrayList();
    	bookSearch.add("UID");
    	bookSearch.add("Name");
    	bookSearch.add("Available");
    	bookSearch.add("Total");
    	bookSearch.add("Author");
    	bookSearch.add("Publisher");
    	bookSearch.add("Genre");
    	bookSearch.add("Year");
    	bookSearch.add("Price");
    	bookSearch.add("Location");
    	bookSearch.add("State");
    	bookSearch.add("Type");
    	bookSearchChoice.setItems(bookSearch);
    	bookSearchChoice.getSelectionModel().selectFirst();
    	
    	String un = CurrentStatus.getINSTANCE().getID();
    	SQL objMember = new SQL();
    	objMember.createConnection();
    	EnvironmentCreator.useDatabase(objMember, "library");
    	ResultSet rs;

    	if(Pattern.matches("[0-9]+", un))
		{
			rs = objMember.query("SELECT * FROM members WHERE uid = " + un + ";");			
		}
		else
		{
			rs = objMember.query("SELECT * FROM members WHERE email = '" + un + "';");
		}
    	
    	try {
    		rs.next();
    			memberIDLabel.setText(rs.getString("uid"));
    			ID = memberIDLabel.getText().toString();
    			CurrentStatus.getINSTANCE().setID(ID);
    			memberNameLabel.setText(rs.getString("name"));
    			memberEmailLabel.setText(rs.getString("email"));
    			memberPhoneLabel.setText(rs.getString("phone"));
    			memberAddressLabel.setText(rs.getString("address"));
			 	memberJoinDateLabel.setText(rs.getString("joined"));
    		
    	}	
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    	objMember.disconnect();
    	
    	
    	ObservableList <String> logSearch = FXCollections.observableArrayList();
    	logSearch.add("Transaction ID");
    	logSearch.add("Book UID");
    	logSearch.add("Book Name");
    	logSearch.add("Date Borrowed");
    	logSearch.add("Date Returned");
    	logSearchChoice.setItems(logSearch);
    	logSearchChoice.getSelectionModel().selectFirst();   
    	
    	ObservableList <String> requestSearch = FXCollections.observableArrayList();
    	requestSearch.add("Book Name");
    	requestSearch.add("Author");
    	requestSearch.add("Publisher");
    	requestSearch.add("Year");
    	requestSearchChoice.setItems(requestSearch);
    	requestSearchChoice.getSelectionModel().selectFirst();
    	
    	booksButtonPressed();
    	
    }
    
    public void booksButtonPressed()
    {
    	
    	searchButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	profileButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	requestButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	logButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	
    	searchButton.getStylesheets().add(getClass().getResource("/CSS/Admin2.css").toExternalForm());
    	profileButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	requestButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	logButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	
    	SQL objMember = new SQL();
    	objMember.createConnection();
    	EnvironmentCreator.useDatabase(objMember, "library");
    	
    	SQL obj = new SQL();
    	SQL objAuthor = new SQL();
    	SQL objPublisher = new SQL();
		obj.createConnection();
		objAuthor.createConnection();
		objPublisher.createConnection();
		EnvironmentCreator.useDatabase(obj, "library");
		EnvironmentCreator.useDatabase(objAuthor, "library");
		EnvironmentCreator.useDatabase(objPublisher, "library");
		
		ResultSet rs = obj.query("SELECT * FROM books;");
		ObservableList <BooksTable> bookList = FXCollections.observableArrayList();
		
		ResultSet m = objMember.query("SELECT count(*) from borrowed WHERE memberuid = " + ID + ";");
		
		
		try
		{
			
			if(m.next())
			{
				if(m.getInt("count(*)") >= 4)
				{
					borrowBookButton.setDisable(true);
				}
				else
				{
					borrowBookButton.setDisable(false);
				}
			}
			
			while(rs.next())
			{
				ResultSet au = objAuthor.query("SELECT * FROM authors WHERE author_id = " + rs.getString("author") + ";");
				ResultSet pu = objPublisher.query("SELECT * FROM publishers WHERE publisher_id = " + rs.getString("publisher") + ";");
				au.next();
				pu.next();
				
				String author = au.getString("author_name");
				String publisher = pu.getString("publisher_name");
				
				String state, type;
				
				if(rs.getInt("state") == 2)
					state = "Good";
				else if (rs.getInt("state") == 1)
					state = "Average";
				else
					state = "Bad";
				
				if(rs.getInt("type") == 1)
					type = "Hardback";
				else
					type = "Paperback";
				
				bookList.add(new BooksTable(rs.getInt("uid"),
						rs.getString("name"),
						rs.getInt("available"),
						rs.getInt("total"),
						rs.getString("year"),
						rs.getString("genre"),
						author,
						publisher,
						rs.getFloat("price"),
						rs.getString("location"),
						state,
						type));		
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		bookUID.setCellValueFactory(new PropertyValueFactory<BooksTable, Integer>("bookUID"));
		bookName.setCellValueFactory(new PropertyValueFactory<BooksTable, String>("bookName"));
		bookAvailable.setCellValueFactory(new PropertyValueFactory<BooksTable, Integer>("bookAvailable"));
		bookTotal.setCellValueFactory(new PropertyValueFactory<BooksTable, Integer>("bookTotal"));
		bookYear.setCellValueFactory(new PropertyValueFactory<BooksTable, String>("bookYear"));
		bookGenre.setCellValueFactory(new PropertyValueFactory<BooksTable, String>("bookGenre"));
		bookAuthor.setCellValueFactory(new PropertyValueFactory<BooksTable, String>("bookAuthor"));
		bookPublisher.setCellValueFactory(new PropertyValueFactory<BooksTable, String>("bookPublisher"));
		bookPrice.setCellValueFactory(new PropertyValueFactory<BooksTable, Float>("bookPrice"));
		bookLocation.setCellValueFactory(new PropertyValueFactory<BooksTable, String>("bookLocation"));
		bookState.setCellValueFactory(new PropertyValueFactory<BooksTable, String>("bookState"));
		bookType.setCellValueFactory(new PropertyValueFactory<BooksTable, String>("bookType"));
		
		booksTable.setItems(bookList);
		
		obj.disconnect();
		objAuthor.disconnect();
		objPublisher.disconnect();
		searchPane.toFront();    	
    }

    public void requestButtonPressed()
    {
    	
    	searchButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	profileButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	requestButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	logButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	
    	searchButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	profileButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	requestButton.getStylesheets().add(getClass().getResource("/CSS/Admin2.css").toExternalForm());
    	logButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	
    	SQL obj = new SQL();
    	SQL objAuthor = new SQL();
    	SQL objPublisher = new SQL();
		obj.createConnection();
		objAuthor.createConnection();
		objPublisher.createConnection();
		EnvironmentCreator.useDatabase(obj, "library");
		EnvironmentCreator.useDatabase(objAuthor, "library");
		EnvironmentCreator.useDatabase(objPublisher, "library");
		
		ResultSet rs = obj.query("SELECT * FROM requests WHERE member_id = " + ID + " ORDER BY book_name;");
		ObservableList <RequestsTable> requestList = FXCollections.observableArrayList();
		
		try
		{
			while(rs.next())
			{
				ResultSet au = objAuthor.query("SELECT * FROM authors WHERE author_id = " + rs.getString("author_id") + ";");
				ResultSet pu = objPublisher.query("SELECT * FROM publishers WHERE publisher_id = " + rs.getString("publisher_id") + ";");
				
				au.next();
				pu.next();
				
				String author = au.getString("author_name");
				String publisher = pu.getString("publisher_name");
				
				requestList.add(new RequestsTable(rs.getInt("member_id"),
						rs.getString("book_name"),
						author,
						publisher,
						rs.getString("year_published")));	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		requestsBookName.setCellValueFactory(new PropertyValueFactory<RequestsTable, String>("requestsBookName"));
		requestsAuthorName.setCellValueFactory(new PropertyValueFactory<RequestsTable, String>("requestsAuthorName"));
		requestsPublisherName.setCellValueFactory(new PropertyValueFactory<RequestsTable, String>("requestsPublisherName"));
		requestsBookYear.setCellValueFactory(new PropertyValueFactory<RequestsTable, String>("requestsBookYear"));
		
		requestsTable.setItems(requestList);
		
		obj.disconnect();
		requestPane.toFront();
    }
    
    @FXML
    void buttonPressed(ActionEvent event) {
    	if(event.getSource() == searchButton )
    	{
    		booksButtonPressed();
    	}
    	if(event.getSource() == profileButton )
    	{
    		searchButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
        	profileButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
        	requestButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
        	logButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
        	
        	searchButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
        	profileButton.getStylesheets().add(getClass().getResource("/CSS/Admin2.css").toExternalForm());
        	requestButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
        	logButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    		
    		SQL obj = new SQL();
    		obj.createConnection();
    		EnvironmentCreator.useDatabase(obj, "library");
    		ResultSet rs = obj.query("SELECT * FROM borrowed WHERE memberuid = " + ID + " ORDER BY borrowed;");
    		ObservableList <BorrowedTable> borrowedList = FXCollections.observableArrayList();
    		
    		try
    		{
    			while(rs.next())
    			{
    				borrowedList.add(new BorrowedTable(rs.getInt("memberuid"),
    						rs.getInt("bookuid"),
    						rs.getString("borrowed"),
    						rs.getString("due")));		
    			}
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    		
    		borrowedBookUID.setCellValueFactory(new PropertyValueFactory<BorrowedTable, Integer>("borrowedBookUID"));
    		borrowedBookBorrowDate.setCellValueFactory(new PropertyValueFactory<BorrowedTable, String>("borrowedBorrowDate"));
    		borrowedBookDueDate.setCellValueFactory(new PropertyValueFactory<BorrowedTable, String>("borrowedDueDate"));
    		borrowedTable.setItems(borrowedList);
    		
    		obj.disconnect();
    		profilePane.toFront();
    	}
    	if(event.getSource() == requestButton )
    	{
    		requestButtonPressed();
    	}
    	if(event.getSource() == logButton )
    	{
    		
    		searchButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
        	profileButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
        	requestButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
        	logButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
        	
        	searchButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
        	profileButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
        	requestButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
        	logButton.getStylesheets().add(getClass().getResource("/CSS/Admin2.css").toExternalForm());
    		
    		SQL obj = new SQL();
    		obj.createConnection();
    		EnvironmentCreator.useDatabase(obj, "library");
    		ResultSet rs = obj.query("SELECT l.uid uid, l.bookuid bookuid, l.borrowed borrowed, l.returned returned, l.overdue overdue, b.name as bname FROM log l, books b where bookuid = b.uid and l.memberuid = " + ID + " ORDER BY l.uid desc;");
    		ObservableList <LogTable> logList = FXCollections.observableArrayList();
    		
    		try
    		{
    			while(rs.next())
    			{
    				logList.add(new LogTable(rs.getInt("uid"),
    						1,
    						rs.getInt("bookuid"),
    						rs.getString("borrowed"),
    						rs.getString("returned"),
    						rs.getInt("overdue"),
    						"fill",
    						rs.getString("bname")));				
    			}
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    		
    		logTransactionUID.setCellValueFactory(new PropertyValueFactory<LogTable, Integer>("logTransactionID"));
    		logBookUID.setCellValueFactory(new PropertyValueFactory<LogTable, Integer>("logBookUID"));
    		logDateBorrowed.setCellValueFactory(new PropertyValueFactory<LogTable, String>("logBorrowDate"));
    		logDateReturned.setCellValueFactory(new PropertyValueFactory<LogTable, String>("logReturnDate"));
    		logOverdue.setCellValueFactory(new PropertyValueFactory<LogTable, Integer>("logOverdue"));
    		logBookName.setCellValueFactory(new PropertyValueFactory<LogTable, String>("logBookName"));
    		logTable.setItems(logList);
    		
    		obj.disconnect();
    		logPane.toFront();
    	}
    	
    }

    @FXML
    void logout(ActionEvent event)throws Exception {
    	Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
		 
		Parent root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
		Scene scene = new Scene(root);
		
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xoffset = event.getSceneX();
				yoffset = event.getSceneY();
			}
		});
		
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				primaryStage.setX(event.getScreenX() - xoffset);
				primaryStage.setY(event.getScreenY() - yoffset);
			}
		});	
		
		scene.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
		
		primaryStage.setScene(scene);
    }

    @FXML
    void searchBooks(KeyEvent event) {
    	SQL obj = new SQL();
    	SQL objAuthor = new SQL();
    	SQL objAuthor2 = new SQL();
    	SQL objPublisher = new SQL();
    	SQL objPublisher2 = new SQL();
		obj.createConnection();
		objAuthor.createConnection();
		objAuthor2.createConnection();
		objPublisher.createConnection();
		objPublisher2.createConnection();
		EnvironmentCreator.useDatabase(obj, "library");
		EnvironmentCreator.useDatabase(objAuthor, "library");
		EnvironmentCreator.useDatabase(objAuthor2, "library");
		EnvironmentCreator.useDatabase(objPublisher, "library");
		EnvironmentCreator.useDatabase(objPublisher2, "library");
    	
    	String searchText = searchBarBooks.getText();
    	String variable = null;
    	
    	ResultSet rs ;
    	ObservableList <BooksTable> bookList = FXCollections.observableArrayList();
    	
    	if(Pattern.matches(".{1,}", searchText))
    	{
    		variable = bookSearchChoice.getValue();
    		if(variable.equals("Author"))
    		{
    			ResultSet aut = SearchHandler.search(objAuthor2, searchText, "authors", "author_name");
    			try
    			{
    				while(aut.next())
    				{
        				searchText = aut.getString("author_id");
        				rs = SearchHandler.search(obj, searchText, "books", variable);
        				try
        				{
        					while(rs.next())
        					{
        						ResultSet au = objAuthor.query("SELECT * FROM authors WHERE author_id = " + rs.getString("author") + ";");
        						ResultSet pu = objPublisher.query("SELECT * FROM publishers WHERE publisher_id = " + rs.getString("publisher") + ";");
        						
        						au.next();
        						pu.next();
        						
        						String author = au.getString("author_name");
        						String publisher = pu.getString("publisher_name");
        						
        						String state, type;
        						
        						if(rs.getInt("state") == 2)
        							state = "Good";
        						else if (rs.getInt("state") == 1)
        							state = "Average";
        						else
        							state = "Bad";
        						
        						if(rs.getInt("type") == 1)
        							type = "Hardback";
        						else
        							type = "Paperback";
        						
        						bookList.add(new BooksTable(rs.getInt("uid"),
        								rs.getString("name"),
        								rs.getInt("available"),
        								rs.getInt("total"),
        								rs.getString("year"),
        								rs.getString("genre"),
        								author,
        								publisher,
        								rs.getInt("price"),
        								rs.getString("location"),
        								state,
        								type));		
        						
        					}
        				}
        				catch(Exception e)
        				{
        					e.printStackTrace();
        				}
    				}
    			}
    			catch(Exception e)
    			{
    				e.printStackTrace();
    			}    			
    		}
    		else if (variable.equals("Publisher"))
    		{
    			ResultSet pub = SearchHandler.search(objPublisher2, searchText, "publishers", "publisher_name");
    			try
    			{
    				while(pub.next())
    				{
        				searchText = pub.getString("publisher_id");
        				rs = SearchHandler.search(obj, searchText, "books", variable);
        				try
        				{
        					while(rs.next())
        					{
        						ResultSet au = objAuthor.query("SELECT * FROM authors WHERE author_id = " + rs.getString("author") + ";");
        						ResultSet pu = objPublisher.query("SELECT * FROM publishers WHERE publisher_id = " + rs.getString("publisher") + ";");
        						au.next();
        						pu.next();
        						
        						String author = au.getString("author_name");
        						String publisher = pu.getString("publisher_name");
        						
        						String state, type;
        						
        						if(rs.getInt("state") == 2)
        							state = "Good";
        						else if (rs.getInt("state") == 1)
        							state = "Average";
        						else
        							state = "Bad";
        						
        						if(rs.getInt("type") == 1)
        							type = "Hardback";
        						else
        							type = "Paperback";
        						
        						bookList.add(new BooksTable(rs.getInt("uid"),
        								rs.getString("name"),
        								rs.getInt("available"),
        								rs.getInt("total"),
        								rs.getString("year"),
        								rs.getString("genre"),
        								author,
        								publisher,
        								rs.getInt("price"),
        								rs.getString("location"),
        								state,
        								type));		
        						
        					}
        				}
        				catch(Exception e)
        				{
        					e.printStackTrace();
        				}
    				}
    			}
    			catch(Exception e)
    			{
    				e.printStackTrace();
    			}    			
    		}
    		else if(variable.equals("State"))
    		{
    			if(Pattern.matches("[Gg].*", searchText))
    				searchText = "2";
    			else if(Pattern.matches("[Aa].*", searchText))
    				searchText = "1";
    			else if(Pattern.matches("[Bb].*", searchText))
    				searchText = "0";
    			else
    				searchText = "o";

    			rs = SearchHandler.search(obj, searchText, "books", variable);
    			try
        		{
        			while(rs.next())
        			{
        				ResultSet au = objAuthor.query("SELECT * FROM authors WHERE author_id = " + rs.getString("author") + ";");
        				ResultSet pu = objPublisher.query("SELECT * FROM publishers WHERE publisher_id = " + rs.getString("publisher") + ";");
        				au.next();
        				pu.next();
        				
        				String author = au.getString("author_name");
        				String publisher = pu.getString("publisher_name");
        				
        				String state, type;
        				
        				if(rs.getInt("state") == 2)
        					state = "Good";
        				else if (rs.getInt("state") == 1)
        					state = "Average";
        				else
        					state = "Bad";
        				
        				if(rs.getInt("type") == 1)
        					type = "Hardback";
        				else
        					type = "Paperback";
        				
        				bookList.add(new BooksTable(rs.getInt("uid"),
        						rs.getString("name"),
        						rs.getInt("available"),
        						rs.getInt("total"),
        						rs.getString("year"),
        						rs.getString("genre"),
        						author,
        						publisher,
        						rs.getInt("price"),
        						rs.getString("location"),
        						state,
        						type));		
        				
        			}
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
    		}
    		else if(variable.equals("Type"))
    		{
    			if(Pattern.matches("[Hh].*", searchText))
    				searchText = "1";
    			else if(Pattern.matches("[Pp].*", searchText))
    				searchText = "0";
    			else
    				searchText = "o";
    			rs = SearchHandler.search(obj, searchText, "books", variable);
    			try
        		{
        			while(rs.next())
        			{
        				ResultSet au = objAuthor.query("SELECT * FROM authors WHERE author_id = " + rs.getString("author") + ";");
        				ResultSet pu = objPublisher.query("SELECT * FROM publishers WHERE publisher_id = " + rs.getString("publisher") + ";");
        				au.next();
        				pu.next();
        				
        				String author = au.getString("author_name");
        				String publisher = pu.getString("publisher_name");
        				
        				String state, type;
        				
        				if(rs.getInt("state") == 2)
        					state = "Good";
        				else if (rs.getInt("state") == 1)
        					state = "Average";
        				else
        					state = "Bad";
        				
        				if(rs.getInt("type") == 1)
        					type = "Hardback";
        				else
        					type = "Paperback";
        				
        				bookList.add(new BooksTable(rs.getInt("uid"),
        						rs.getString("name"),
        						rs.getInt("available"),
        						rs.getInt("total"),
        						rs.getString("year"),
        						rs.getString("genre"),
        						author,
        						publisher,
        						rs.getInt("price"),
        						rs.getString("location"),
        						state,
        						type));		
        				
        			}
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
    		}
    		else
    		{
    			variable = bookSearchChoice.getValue();
    			rs = SearchHandler.search(obj, searchText, "books", variable);
    			try
        		{
        			while(rs.next())
        			{
        				ResultSet au = objAuthor.query("SELECT * FROM authors WHERE author_id = " + rs.getString("author") + ";");
        				ResultSet pu = objPublisher.query("SELECT * FROM publishers WHERE publisher_id = " + rs.getString("publisher") + ";");
        				au.next();
        				pu.next();
        				
        				String author = au.getString("author_name");
        				String publisher = pu.getString("publisher_name");
        				
        				String state, type;
        				
        				if(rs.getInt("state") == 2)
        					state = "Good";
        				else if (rs.getInt("state") == 1)
        					state = "Average";
        				else
        					state = "Bad";
        				
        				if(rs.getInt("type") == 1)
        					type = "Hardback";
        				else
        					type = "Paperback";
        				
        				bookList.add(new BooksTable(rs.getInt("uid"),
        						rs.getString("name"),
        						rs.getInt("available"),
        						rs.getInt("total"),
        						rs.getString("year"),
        						rs.getString("genre"),
        						author,
        						publisher,
        						rs.getInt("price"),
        						rs.getString("location"),
        						state,
        						type));		
        				
        			}
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
    		}
    	}
    	else
    	{
    		rs = obj.query("SELECT * FROM books ORDER BY uid;");
    		try
    		{
    			while(rs.next())
    			{
    				ResultSet au = objAuthor.query("SELECT * FROM authors WHERE author_id = " + rs.getString("author") + ";");
    				ResultSet pu = objPublisher.query("SELECT * FROM publishers WHERE publisher_id = " + rs.getString("publisher") + ";");
    				au.next();
    				pu.next();
    				
    				String author = au.getString("author_name");
    				String publisher = pu.getString("publisher_name");
    				
    				String state, type;
    				
    				if(rs.getInt("state") == 2)
    					state = "Good";
    				else if (rs.getInt("state") == 1)
    					state = "Average";
    				else
    					state = "Bad";
    				
    				if(rs.getInt("type") == 1)
    					type = "Hardback";
    				else
    					type = "Paperback";
    				
    				bookList.add(new BooksTable(rs.getInt("uid"),
    						rs.getString("name"),
    						rs.getInt("available"),
    						rs.getInt("total"),
    						rs.getString("year"),
    						rs.getString("genre"),
    						author,
    						publisher,
    						rs.getInt("price"),
    						rs.getString("location"),
    						state,
    						type));		
    				
    			}
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    	}  	 
    	
		bookUID.setCellValueFactory(new PropertyValueFactory<BooksTable, Integer>("bookUID"));
		bookName.setCellValueFactory(new PropertyValueFactory<BooksTable, String>("bookName"));
		bookAvailable.setCellValueFactory(new PropertyValueFactory<BooksTable, Integer>("bookAvailable"));
		bookTotal.setCellValueFactory(new PropertyValueFactory<BooksTable, Integer>("bookTotal"));
		bookYear.setCellValueFactory(new PropertyValueFactory<BooksTable, String>("bookYear"));
		bookGenre.setCellValueFactory(new PropertyValueFactory<BooksTable, String>("bookGenre"));
		bookAuthor.setCellValueFactory(new PropertyValueFactory<BooksTable, String>("bookAuthor"));
		bookPublisher.setCellValueFactory(new PropertyValueFactory<BooksTable, String>("bookPublisher"));
		bookPrice.setCellValueFactory(new PropertyValueFactory<BooksTable, Float>("bookPrice"));
		bookLocation.setCellValueFactory(new PropertyValueFactory<BooksTable, String>("bookLocation"));
		bookState.setCellValueFactory(new PropertyValueFactory<BooksTable, String>("bookState"));
		bookType.setCellValueFactory(new PropertyValueFactory<BooksTable, String>("bookType"));
		
		booksTable.setItems(bookList);
		
		obj.disconnect();
		objAuthor.disconnect();
		objPublisher.disconnect();
		searchPane.toFront();
    }

    @FXML
    public void searchLog(KeyEvent event)
    {
    	SQL obj = new SQL();
    	obj.createConnection();
    	EnvironmentCreator.useDatabase(obj, "library");
    	
    	String searchText = searchBarLog.getText();
    	String variable = null;
    	ResultSet rs;
    	ObservableList <LogTable> logList = FXCollections.observableArrayList();
    	
    	if(Pattern.matches(".{1,}", searchText))
    	{
    		if(logSearchChoice.getValue().equals("Transaction ID"))
    			variable = "l.uid";
    		else if(logSearchChoice.getValue().equals("Member UID"))
    			variable = "l.memberuid";
    		else if(logSearchChoice.getValue().equals("Book UID"))
    			variable = "l.bookuid";
    		else if(logSearchChoice.getValue().equals("Book Name"))
    			variable = "b.name";
    		else if(logSearchChoice.getValue().equals("Date Borrowed"))
    			variable = "l.borrowed";
    		else if(logSearchChoice.getValue().equals("Date Returned"))
    			variable = "l.returned";
    		
    		rs = SearchHandler.memberSearch(obj, searchText, "log", variable, ID);
    	}
    	else
    	{
    		rs = obj.query("SELECT l.uid uid, l.bookuid bookuid, l.borrowed borrowed, l.returned returned, l.overdue overdue, b.name as bname FROM log l, books b where bookuid = b.uid and l.memberuid = " + ID + " ORDER BY l.uid desc;");
    	}
    	
    	try
		{
			while(rs.next())
			{
				logList.add(new LogTable(rs.getInt("uid"),
						1,
						rs.getInt("bookuid"),
						rs.getString("borrowed"),
						rs.getString("returned"),
						rs.getInt("overdue"),
						"fill",
						rs.getString("bname")));				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		logTransactionUID.setCellValueFactory(new PropertyValueFactory<LogTable, Integer>("logTransactionID"));
		logBookUID.setCellValueFactory(new PropertyValueFactory<LogTable, Integer>("logBookUID"));
		logDateBorrowed.setCellValueFactory(new PropertyValueFactory<LogTable, String>("logBorrowDate"));
		logDateReturned.setCellValueFactory(new PropertyValueFactory<LogTable, String>("logReturnDate"));
		logOverdue.setCellValueFactory(new PropertyValueFactory<LogTable, Integer>("logOverdue"));
		logBookName.setCellValueFactory(new PropertyValueFactory<LogTable, String>("logBookName"));
		logTable.setItems(logList);
		
		obj.disconnect();
    }
    
    @FXML
    public void searchRequests(KeyEvent event)
    {
    	SQL obj = new SQL();
    	SQL objAuthor = new SQL();
    	SQL objAuthor2 = new SQL();
    	SQL objPublisher = new SQL();
    	SQL objPublisher2 = new SQL();
		obj.createConnection();
		objAuthor.createConnection();
		objAuthor2.createConnection();
		objPublisher.createConnection();
		objPublisher2.createConnection();
		EnvironmentCreator.useDatabase(obj, "library");
		EnvironmentCreator.useDatabase(objAuthor, "library");
		EnvironmentCreator.useDatabase(objAuthor2, "library");
		EnvironmentCreator.useDatabase(objPublisher, "library");
		EnvironmentCreator.useDatabase(objPublisher2, "library");
    	
    	String searchText = searchBarRequests.getText();
    	String variable = null;
    	ResultSet rs;
    	ObservableList <RequestsTable> requestList = FXCollections.observableArrayList();
    	
    	if(Pattern.matches(".{1,}", searchText))
    	{
    		variable = requestSearchChoice.getValue();
    		if(variable.equals("Author"))
    		{
    			variable = "author_id";
    			ResultSet aut = SearchHandler.search(objAuthor2, searchText, "authors", "author_name");
    			try
    			{
    				while(aut.next())
    				{
        				searchText = aut.getString("author_id");
        				rs = SearchHandler.memberSearch(obj, searchText, "requests", variable, ID);
        				try
        				{
        					while(rs.next())
        					{
        						ResultSet au = objAuthor.query("SELECT * FROM authors WHERE author_id = " + rs.getString("author_id") + ";");
        						ResultSet pu = objPublisher.query("SELECT * FROM publishers WHERE publisher_id = " + rs.getString("publisher_id") + ";");
        						
        						au.next();
        						pu.next();
        						
        						String author = au.getString("author_name");
        						String publisher = pu.getString("publisher_name");
        						
        						requestList.add(new RequestsTable(rs.getInt("member_id"),
        								rs.getString("book_name"),
        								author,
        								publisher,
        								rs.getString("year_published")));	
        					}
        				}
        				catch(Exception e)
        				{
        					e.printStackTrace();
        				}
    				}
    			}
    			catch(Exception e)
    			{
    				e.printStackTrace();
    			}    			
    		}
    		else if (variable.equals("Publisher"))
    		{
    			variable = "publisher_id";
    			ResultSet pub = SearchHandler.search(objPublisher2, searchText, "publishers", "publisher_name");
    			try
    			{
    				while(pub.next())
    				{
        				searchText = pub.getString("publisher_id");
        				rs = SearchHandler.memberSearch(obj, searchText, "requests", variable, ID);
        				try
        				{
        					while(rs.next())
        					{
        						ResultSet au = objAuthor.query("SELECT * FROM authors WHERE author_id = " + rs.getString("author_id") + ";");
        						ResultSet pu = objPublisher.query("SELECT * FROM publishers WHERE publisher_id = " + rs.getString("publisher_id") + ";");
        						
        						au.next();
        						pu.next();
        						
        						String author = au.getString("author_name");
        						String publisher = pu.getString("publisher_name");
        						
        						requestList.add(new RequestsTable(rs.getInt("member_id"),
        								rs.getString("book_name"),
        								author,
        								publisher,
        								rs.getString("year_published")));	
        					}
        				}
        				catch(Exception e)
        				{
        					e.printStackTrace();
        				}
    				}
    			}
    			catch(Exception e)
    			{
    				e.printStackTrace();
    			}    			
    		}
    		else
    		{
    			if(requestSearchChoice.getValue().equals("Member UID"))
    				variable = "member_id";
    			else if(requestSearchChoice.getValue().equals("Book Name"))
    				variable = "book_name";
    			else if(requestSearchChoice.getValue().equals("Year"))
    				variable = "year_published";
    			rs = SearchHandler.memberSearch(obj, searchText, "requests", variable, ID);
    			try
        		{
        			while(rs.next())
        			{
        				ResultSet au = objAuthor.query("SELECT * FROM authors WHERE author_id = " + rs.getString("author_id") + ";");
        				ResultSet pu = objPublisher.query("SELECT * FROM publishers WHERE publisher_id = " + rs.getString("publisher_id") + ";");
        				
        				au.next();
        				pu.next();
        				
        				String author = au.getString("author_name");
        				String publisher = pu.getString("publisher_name");
        				
        				requestList.add(new RequestsTable(rs.getInt("member_id"),
        						rs.getString("book_name"),
        						author,
        						publisher,
        						rs.getString("year_published")));	
        			}
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
    		}
    	}
    	else
    	{
    		rs = obj.query("SELECT * FROM requests WHERE member_id = " + ID + " ORDER BY book_name;");
    		try
    		{
    			while(rs.next())
    			{
    				ResultSet au = objAuthor.query("SELECT * FROM authors WHERE author_id = " + rs.getString("author_id") + ";");
    				ResultSet pu = objPublisher.query("SELECT * FROM publishers WHERE publisher_id = " + rs.getString("publisher_id") + ";");
    				
    				au.next();
    				pu.next();
    				
    				String author = au.getString("author_name");
    				String publisher = pu.getString("publisher_name");
    				
    				requestList.add(new RequestsTable(rs.getInt("member_id"),
    						rs.getString("book_name"),
    						author,
    						publisher,
    						rs.getString("year_published")));	
    			}
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    	}   	
		requestsBookName.setCellValueFactory(new PropertyValueFactory<RequestsTable, String>("requestsBookName"));
		requestsAuthorName.setCellValueFactory(new PropertyValueFactory<RequestsTable, String>("requestsAuthorName"));
		requestsPublisherName.setCellValueFactory(new PropertyValueFactory<RequestsTable, String>("requestsPublisherName"));
		requestsBookYear.setCellValueFactory(new PropertyValueFactory<RequestsTable, String>("requestsBookYear"));
		
		requestsTable.setItems(requestList);
		
		obj.disconnect();
    }
    
    @FXML
    void borrowBook(ActionEvent event)throws Exception {
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML/BorrowBook.fxml"));
    	Scene scene = new Scene(root);
    	Stage stage = new Stage();
		
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xoffset = event.getSceneX();
				yoffset = event.getSceneY();
			}
		});
		
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setX(event.getScreenX() - xoffset);
				stage.setY(event.getScreenY() - yoffset);
			}
		});
		
		scene.getStylesheets().add(getClass().getResource("/CSS/AddMember.css").toExternalForm());	
				
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
    }
    
    @FXML
    void requestBook(ActionEvent event)throws Exception {
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML/RequestBook.fxml"));
    	Scene scene = new Scene(root);
    	Stage stage = new Stage();
		
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xoffset = event.getSceneX();
				yoffset = event.getSceneY();
			}
		});
		
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setX(event.getScreenX() - xoffset);
				stage.setY(event.getScreenY() - yoffset);
			}
		});
		
		scene.getStylesheets().add(getClass().getResource("/CSS/AddMember.css").toExternalForm());	
				
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
    }
    
}

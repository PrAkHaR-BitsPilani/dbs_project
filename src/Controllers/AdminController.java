package Controllers;

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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Backend.SearchHandler;
import TableStyles.AuthorsTable;
import TableStyles.BooksTable;
import TableStyles.BorrowedTable;
import TableStyles.LogTable;
import TableStyles.MembersTable;
import TableStyles.PublishersTable;
import TableStyles.RequestsTable;
import Backend.SQL;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import Backend.EnvironmentCreator;

public class AdminController implements Initializable {
	private double xoffset = 0.0;
	private double yoffset = 0.0;
	
	@FXML
    private Button bookButton;

    @FXML
    private Button membersButton;

    @FXML
    private Button borrowedButton;

    @FXML
    private Button requestsButton;

    @FXML
    private Button authorsButton;

    @FXML
    private Button publishersButton;

    @FXML
    private Button logButton;

    @FXML
    private Button logout;

    @FXML
    private Pane booksPane;

    @FXML
    private Pane membersPane;

    @FXML
    private Pane borrowedPane;

    @FXML
    private Pane requestsPane;

    @FXML
    private Pane authorsPane;

    @FXML
    private Pane publishersPane;

    @FXML
    private Pane logPane;
    
    @FXML
    private TextField searchBarBooks;
    
    @FXML
    private TextField searchBarMembers;
    
    @FXML
    private TextField searchBarBorrowed;
    
    @FXML
    private TextField searchBarRequests;
    
    @FXML
    private TextField searchBarAuthors;
    
    @FXML
    private TextField searchBarPublishers;

    @FXML
    private TextField searchBarLog;
    
    @FXML
    private Button addMember;
	
    @FXML
    private Button removeMember;
    
    @FXML
    private Button addAuthor;
    
    @FXML
    private Button removeAuthor;
    
    @FXML
    private Button addPublisher;
    
    @FXML
    private Button removePublisher;
    
    @FXML
    private Button addBook;

    @FXML
    private Button removeBook;
    
    @FXML
    private TableView<MembersTable> membersTable;

    @FXML
    private TableColumn<MembersTable, Integer> memberUID;

    @FXML
    private TableColumn<MembersTable, String> memberName;

    @FXML
    private TableColumn<MembersTable, String> memberPassword;

    @FXML
    private TableColumn<MembersTable, String> memberAddress;

    @FXML
    private TableColumn<MembersTable, String> memberPhone;

    @FXML
    private TableColumn<MembersTable, String> memberEmail;

    @FXML
    private TableColumn<MembersTable, String> memberJoined;
    
    @FXML
    private TableView<AuthorsTable> authorsTable;

    @FXML
    private TableColumn<AuthorsTable, Integer> authorUID;

    @FXML
    private TableColumn<AuthorsTable, String> authorName;
    
    @FXML
    private TableView<PublishersTable> publishersTable;

    @FXML
    private TableColumn<PublishersTable, Integer> publisherUID;

    @FXML
    private TableColumn<PublishersTable, String> publisherName;
    
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
    private TableView<BorrowedTable> borrowedTable;

    @FXML
    private TableColumn<BorrowedTable, Integer> borrowedMemberUID;

    @FXML
    private TableColumn<BorrowedTable, Integer> borrowedBookUID;

    @FXML
    private TableColumn<BorrowedTable, String> borrowedBorrowDate;

    @FXML
    private TableColumn<BorrowedTable, String> borrowedDueDate;
    
    @FXML
    private TableView<RequestsTable> requestsTable;

    @FXML
    private TableColumn<RequestsTable, Integer> requestsMemberUID;

    @FXML
    private TableColumn<RequestsTable, String> requestsBookName;

    @FXML
    private TableColumn<RequestsTable, String> requestsAuthorName;

    @FXML
    private TableColumn<RequestsTable, String> requestsPublisherName;

    @FXML
    private TableColumn<RequestsTable, String> requestsBookYear;
    
    @FXML
    private TableView<LogTable> logTable;

    @FXML
    private TableColumn<LogTable, Integer> logTransactionID;

    @FXML
    private TableColumn<LogTable, Integer> logMemberUID;

    @FXML
    private TableColumn<LogTable, Integer> logBookUID;

    @FXML
    private TableColumn<LogTable, String> logBorrowDate;

    @FXML
    private TableColumn<LogTable, String> logReturnDate;
    
    @FXML
    private TableColumn<LogTable, Integer> logOverdue;
    
    @FXML
    private TableColumn<LogTable, String> logMemberName;

    @FXML
    private TableColumn<LogTable, String> logBookName;
    
    @FXML
    private ComboBox<String> bookSearchChoice;
    
    @FXML
    private ComboBox<String> memberSearchChoice;
    
    @FXML
    private ComboBox<String> borrowedSearchChoice;
    
    @FXML
    private ComboBox<String> requestSearchChoice;
    
    @FXML
    private ComboBox<String> authorSearchChoice;
    
    @FXML
    private ComboBox<String> publisherSearchChoice;
    
    @FXML
    private ComboBox<String> logSearchChoice;
    
    @FXML
    private Button editBook;
    
    @FXML
    private Button editMember;
    
    @FXML
    private Button returnBook;
    
    @FXML
    private Button clearRequest;
    
    @FXML
    private Label requestsTotalResults;
    
    @FXML
    private Label booksTotalResults;
    
    @FXML
    private Label membersTotalResults;
    
    @FXML
    private Label borrowedTotalResults;
    
    @FXML
    private Label authorsTotalResults;
    
    @FXML
    private Label publishersTotalResults;
    
    @FXML
    private Label logTotalResults;
    
    public void initialize(URL location, ResourceBundle resources)
    {
    	CurrentStatus.getINSTANCE().setAdmin(this);
    	
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
    	
    	ObservableList <String> memberSearch = FXCollections.observableArrayList();
    	memberSearch.add("UID");
    	memberSearch.add("Name");
    	memberSearch.add("Address");
    	memberSearch.add("Phone");
    	memberSearch.add("Email");
    	memberSearch.add("Join Date");    	
    	memberSearchChoice.setItems(memberSearch);
    	memberSearchChoice.getSelectionModel().selectFirst();
    	
    	ObservableList <String> borrowedSearch = FXCollections.observableArrayList();
    	borrowedSearch.add("Member UID");
    	borrowedSearch.add("Book UID");
    	borrowedSearch.add("Date Borrowed");
    	borrowedSearch.add("Date Due");
    	borrowedSearchChoice.setItems(borrowedSearch);
    	borrowedSearchChoice.getSelectionModel().selectFirst();
    	
    	ObservableList <String> requestSearch = FXCollections.observableArrayList();
    	requestSearch.add("Member UID");
    	requestSearch.add("Book Name");
    	requestSearch.add("Author");
    	requestSearch.add("Publisher");
    	requestSearch.add("Year");
    	requestSearchChoice.setItems(requestSearch);
    	requestSearchChoice.getSelectionModel().selectFirst();
    	
    	ObservableList <String> authorSearch = FXCollections.observableArrayList();
    	authorSearch.add("UID");
    	authorSearch.add("Name");
    	authorSearchChoice.setItems(authorSearch);
    	authorSearchChoice.getSelectionModel().selectFirst();
    	
    	ObservableList <String> publisherSearch = FXCollections.observableArrayList();
    	publisherSearch.add("UID");
    	publisherSearch.add("Name");
    	publisherSearchChoice.setItems(publisherSearch);
    	publisherSearchChoice.getSelectionModel().selectFirst();
    	
    	ObservableList <String> logSearch = FXCollections.observableArrayList();
    	logSearch.add("Transaction ID");
    	logSearch.add("Member UID");
    	logSearch.add("Member Name");
    	logSearch.add("Book UID");
    	logSearch.add("Book Name");
    	logSearch.add("Date Borrowed");
    	logSearch.add("Date Returned");
    	logSearchChoice.setItems(logSearch);
    	logSearchChoice.getSelectionModel().selectFirst();    	
    	
    	booksButtonPressed();
    }
    
    public void booksButtonPressed()
    {
    	
    	bookButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	membersButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	borrowedButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	requestsButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	authorsButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	publishersButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	logButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	
    	bookButton.getStylesheets().add(getClass().getResource("/CSS/Admin2.css").toExternalForm());
    	membersButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	borrowedButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	requestsButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	authorsButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	publishersButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
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
		
		ResultSet rs = obj.query("SELECT * FROM books;");
		ObservableList <BooksTable> bookList = FXCollections.observableArrayList();
		
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
		booksTotalResults.setText(bookList.size() + " Result(s) ");
		
		obj.disconnect();
		objAuthor.disconnect();
		objPublisher.disconnect();
		booksPane.toFront();    	
    }
    
    public void membersButtonPressed()
    {
    	
    	bookButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	membersButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	borrowedButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	requestsButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	authorsButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	publishersButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	logButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	
    	bookButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	membersButton.getStylesheets().add(getClass().getResource("/CSS/Admin2.css").toExternalForm());
    	borrowedButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	requestsButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	authorsButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	publishersButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	logButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	
    	SQL obj = new SQL();
		obj.createConnection();
		EnvironmentCreator.useDatabase(obj, "library");
    	
		ResultSet rs = obj.query("SELECT * FROM members;");
		ObservableList <MembersTable> memberList = FXCollections.observableArrayList();
		
		try
		{
			while(rs.next())
			{
				memberList.add(new MembersTable(rs.getInt("uid"), rs.getString("name"),
						rs.getString("password"), rs.getString("address"), rs.getString("phone"), rs.getString("email"),
						rs.getString("joined")));
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		memberUID.setCellValueFactory(new PropertyValueFactory<MembersTable, Integer>("memberUID"));
		memberName.setCellValueFactory(new PropertyValueFactory<MembersTable, String>("memberName"));
		memberPassword.setCellValueFactory(new PropertyValueFactory<MembersTable, String>("memberPassword"));
		memberAddress.setCellValueFactory(new PropertyValueFactory<MembersTable, String>("memberAddress"));
		memberPhone.setCellValueFactory(new PropertyValueFactory<MembersTable, String>("memberPhone"));
		memberEmail.setCellValueFactory(new PropertyValueFactory<MembersTable, String>("memberEmail"));
		memberJoined.setCellValueFactory(new PropertyValueFactory<MembersTable, String>("memberJoined"));
		membersTable.setItems(memberList);
		membersTotalResults.setText(memberList.size() + " Result(s) ");
		
		obj.disconnect();
		membersPane.toFront();
    }  
    
    public void borrowedButtonPressed()
    {
    	
    	bookButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	membersButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	borrowedButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	requestsButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	authorsButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	publishersButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	logButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	
    	bookButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	membersButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	borrowedButton.getStylesheets().add(getClass().getResource("/CSS/Admin2.css").toExternalForm());
    	requestsButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	authorsButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	publishersButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	logButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	
    	SQL obj = new SQL();
		obj.createConnection();
		EnvironmentCreator.useDatabase(obj, "library");
		ResultSet rs = obj.query("SELECT * FROM borrowed ORDER BY memberuid;");
		ObservableList <BorrowedTable> borrowedList = FXCollections.observableArrayList();
		
		try
		{
			if(!rs.next())
			{
				returnBook.setDisable(true);
			}
			else {
				returnBook.setDisable(false);
			}
			rs = obj.query("SELECT * FROM borrowed ORDER BY memberuid;");
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
		
		borrowedMemberUID.setCellValueFactory(new PropertyValueFactory<BorrowedTable, Integer>("borrowedMemberUID"));
		borrowedBookUID.setCellValueFactory(new PropertyValueFactory<BorrowedTable, Integer>("borrowedBookUID"));
		borrowedBorrowDate.setCellValueFactory(new PropertyValueFactory<BorrowedTable, String>("borrowedBorrowDate"));
		borrowedDueDate.setCellValueFactory(new PropertyValueFactory<BorrowedTable, String>("borrowedDueDate"));
		borrowedTable.setItems(borrowedList);
		borrowedTotalResults.setText(borrowedList.size() + " Result(s) ");
		
		obj.disconnect();
		borrowedPane.toFront();
    }
    
    
    public void requestsButtonPressed()
    {
    	
    	bookButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	membersButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	borrowedButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	requestsButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	authorsButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	publishersButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	logButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	
    	bookButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	membersButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	borrowedButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	requestsButton.getStylesheets().add(getClass().getResource("/CSS/Admin2.css").toExternalForm());
    	authorsButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	publishersButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
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
		
		ResultSet rs = obj.query("SELECT * FROM requests ORDER BY member_id;");
		ObservableList <RequestsTable> requestList = FXCollections.observableArrayList();
		
		try
		{
			if(!rs.next())
			{
				clearRequest.setDisable(true);
			}
			else
			{
				clearRequest.setDisable(false);
			}
			rs = obj.query("SELECT * FROM requests ORDER BY member_id;");
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
		
		requestsMemberUID.setCellValueFactory(new PropertyValueFactory<RequestsTable, Integer>("requestsMemberUID"));
		requestsBookName.setCellValueFactory(new PropertyValueFactory<RequestsTable, String>("requestsBookName"));
		requestsAuthorName.setCellValueFactory(new PropertyValueFactory<RequestsTable, String>("requestsAuthorName"));
		requestsPublisherName.setCellValueFactory(new PropertyValueFactory<RequestsTable, String>("requestsPublisherName"));
		requestsBookYear.setCellValueFactory(new PropertyValueFactory<RequestsTable, String>("requestsBookYear"));
		requestsTable.setItems(requestList);
		requestsTotalResults.setText(String.valueOf(requestList.size()) + " Result(s) ");
		
		
		obj.disconnect();
		requestsPane.toFront();
    }

    
    public void authorsButtonPressed()
    {
    	
    	bookButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	membersButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	borrowedButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	requestsButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	authorsButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	publishersButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	logButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	
    	bookButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	membersButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	borrowedButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	requestsButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	authorsButton.getStylesheets().add(getClass().getResource("/CSS/Admin2.css").toExternalForm());
    	publishersButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	logButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	
    	SQL obj = new SQL();
		obj.createConnection();
		EnvironmentCreator.useDatabase(obj, "library");
		ResultSet rs = obj.query("SELECT * FROM authors ORDER BY author_id;");
		ObservableList <AuthorsTable> authorList = FXCollections.observableArrayList();
		
		try
		{
			while(rs.next())
			{
				authorList.add(new AuthorsTable(rs.getInt("author_id"), rs.getString("author_name")));				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		authorUID.setCellValueFactory(new PropertyValueFactory<AuthorsTable, Integer>("authorUID"));
		authorName.setCellValueFactory(new PropertyValueFactory<AuthorsTable, String>("authorName"));
		authorsTable.setItems(authorList);
		authorsTotalResults.setText(authorList.size() + " Result(s) ");
		
		obj.disconnect();
		authorsPane.toFront();
    }

    
    public void publishersButtonPressed()
    {
    	
    	bookButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	membersButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	borrowedButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	requestsButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	authorsButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	publishersButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	logButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	
    	bookButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	membersButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	borrowedButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	requestsButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	authorsButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	publishersButton.getStylesheets().add(getClass().getResource("/CSS/Admin2.css").toExternalForm());
    	logButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	
    	SQL obj = new SQL();
		obj.createConnection();
		EnvironmentCreator.useDatabase(obj, "library");
		ResultSet rs = obj.query("SELECT * FROM publishers ORDER BY publisher_id;");
		ObservableList <PublishersTable> publisherList = FXCollections.observableArrayList();
		
		try
		{
			while(rs.next())
			{
				publisherList.add(new PublishersTable(rs.getInt("publisher_id"), rs.getString("publisher_name")));				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		publisherUID.setCellValueFactory(new PropertyValueFactory<PublishersTable, Integer>("publisherUID"));
		publisherName.setCellValueFactory(new PropertyValueFactory<PublishersTable, String>("publisherName"));
		publishersTable.setItems(publisherList);
		publishersTotalResults.setText(publisherList.size() + " Result(s) ");
		
		obj.disconnect();
		publishersPane.toFront();
    }

    
    public void logButtonPressed()
    {
    	bookButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	membersButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	borrowedButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	requestsButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	authorsButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	publishersButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	logButton.getStylesheets().removeAll(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	
    	bookButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	membersButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	borrowedButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	requestsButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	authorsButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	publishersButton.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
    	logButton.getStylesheets().add(getClass().getResource("/CSS/Admin2.css").toExternalForm());
    	
    	SQL obj = new SQL();
		obj.createConnection();
		EnvironmentCreator.useDatabase(obj, "library");
		ResultSet rs = obj.query("SELECT l.uid uid, l.memberuid memberuid, l.bookuid bookuid, l.borrowed borrowed, l.returned returned, l.overdue overdue, m.name as mname, b.name as bname FROM log l, members m, books b where memberuid = m.uid and bookuid = b.uid ORDER BY l.uid desc;");
		ObservableList <LogTable> logList = FXCollections.observableArrayList();
		
		try
		{
			while(rs.next())
			{
				logList.add(new LogTable(rs.getInt("uid"),
						rs.getInt("memberuid"),
						rs.getInt("bookuid"),
						rs.getString("borrowed"),
						rs.getString("returned"),
						rs.getInt("overdue"),
						rs.getString("mname"),
						rs.getString("bname")));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		logTransactionID.setCellValueFactory(new PropertyValueFactory<LogTable, Integer>("logTransactionID"));
		logMemberUID.setCellValueFactory(new PropertyValueFactory<LogTable, Integer>("logMemberUID"));
		logBookUID.setCellValueFactory(new PropertyValueFactory<LogTable, Integer>("logBookUID"));
		logBorrowDate.setCellValueFactory(new PropertyValueFactory<LogTable, String>("logBorrowDate"));
		logReturnDate.setCellValueFactory(new PropertyValueFactory<LogTable, String>("logReturnDate"));
		logOverdue.setCellValueFactory(new PropertyValueFactory<LogTable, Integer>("logOverdue"));
		logMemberName.setCellValueFactory(new PropertyValueFactory<LogTable, String>("logMemberName"));
		logBookName.setCellValueFactory(new PropertyValueFactory<LogTable, String>("logBookName"));
		logTable.setItems(logList);
		logTotalResults.setText(logList.size() + " Result(s) ");
		
		obj.disconnect();
		logPane.toFront();
    }
    
    @FXML
    public void buttonPressed(ActionEvent event)
    {
    	if(event.getSource() == bookButton)
    	{
    		booksButtonPressed();
    	}
    	else if(event.getSource() == membersButton)
    	{
    		membersButtonPressed();    		
    	}
    	else if(event.getSource() == borrowedButton)
    	{
    		borrowedButtonPressed();
    	}
    	else if(event.getSource() == requestsButton)
    	{
    		requestsButtonPressed();
    	}
    	else if(event.getSource() == authorsButton)
    	{
    		authorsButtonPressed();
    	}
    	else if(event.getSource() == publishersButton)
    	{
    		publishersButtonPressed();
    	}
    	else if(event.getSource() == logButton)
    	{
    		logButtonPressed();
    	}
    	
    }

    @FXML    
    public void searchBooks(KeyEvent event)
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
		booksTotalResults.setText(bookList.size() + " Result(s) ");
		
		obj.disconnect();
		objAuthor.disconnect();
		objPublisher.disconnect();
		booksPane.toFront();
    }
    
    @FXML
    public void searchMembers(KeyEvent event)
    {
    	SQL obj = new SQL();
    	obj.createConnection();
    	EnvironmentCreator.useDatabase(obj, "library");
    	
    	String searchText = searchBarMembers.getText();
    	String variable = null;
    	ResultSet rs;
    	
    	if(Pattern.matches(".{1,}", searchText))
    	{
    		if(memberSearchChoice.getValue().equals("Join Date"))
    			variable = "joined";
    		else
    			variable = memberSearchChoice.getValue();
    		rs = SearchHandler.search(obj,  searchText, "members", variable);    		
    	}
    	else
    	{
    		rs = obj.query("SELECT * FROM members ORDER BY uid;");    	
    	}
    	 
    	ObservableList <MembersTable> memberList = FXCollections.observableArrayList();
    	
    	try
		{
			while(rs.next())
			{
				memberList.add(new MembersTable(rs.getInt("uid"), rs.getString("name"),
						rs.getString("password"), rs.getString("address"), rs.getString("phone"), rs.getString("email"),
						rs.getString("joined")));
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		memberUID.setCellValueFactory(new PropertyValueFactory<MembersTable, Integer>("memberUID"));
		memberName.setCellValueFactory(new PropertyValueFactory<MembersTable, String>("memberName"));
		memberPassword.setCellValueFactory(new PropertyValueFactory<MembersTable, String>("memberPassword"));
		memberAddress.setCellValueFactory(new PropertyValueFactory<MembersTable, String>("memberAddress"));
		memberPhone.setCellValueFactory(new PropertyValueFactory<MembersTable, String>("memberPhone"));
		memberEmail.setCellValueFactory(new PropertyValueFactory<MembersTable, String>("memberEmail"));
		memberJoined.setCellValueFactory(new PropertyValueFactory<MembersTable, String>("memberJoined"));
		membersTable.setItems(memberList);
		membersTotalResults.setText(memberList.size() + " Result(s) ");
    	obj.disconnect();
    }
    
    @FXML
    public void searchBorrowed(KeyEvent event)
    {
    	SQL obj = new SQL();
    	obj.createConnection();
    	EnvironmentCreator.useDatabase(obj, "library");
    	
    	String searchText = searchBarBorrowed.getText();
    	String variable = null;
    	ResultSet rs;
    	ObservableList <BorrowedTable> borrowedList = FXCollections.observableArrayList();
    	
    	if(Pattern.matches(".{1,}", searchText))
    	{
    		if(borrowedSearchChoice.getValue().equals("Member UID"))
    			variable = "memberuid";
    		else if(borrowedSearchChoice.getValue().equals("Book UID"))
    			variable = "bookuid";
    		else if(borrowedSearchChoice.getValue().equals("Date Borrowed"))
    			variable = "borrowed";
    		else if(borrowedSearchChoice.getValue().equals("Date Due"))
    			variable = "due";
    		rs = SearchHandler.search(obj, searchText, "borrowed", variable);    		
    	}
    	else
    	{
    		rs = obj.query("SELECT * FROM borrowed ORDER BY borrowed DESC;");    		
    	}
		
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
		
		borrowedMemberUID.setCellValueFactory(new PropertyValueFactory<BorrowedTable, Integer>("borrowedMemberUID"));
		borrowedBookUID.setCellValueFactory(new PropertyValueFactory<BorrowedTable, Integer>("borrowedBookUID"));
		borrowedBorrowDate.setCellValueFactory(new PropertyValueFactory<BorrowedTable, String>("borrowedBorrowDate"));
		borrowedDueDate.setCellValueFactory(new PropertyValueFactory<BorrowedTable, String>("borrowedDueDate"));
		borrowedTable.setItems(borrowedList);
		borrowedTotalResults.setText(borrowedList.size() + " Result(s) ");
		
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
        				rs = SearchHandler.search(obj, searchText, "requests", variable);
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
        				rs = SearchHandler.search(obj, searchText, "requests", variable);
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
    			rs = SearchHandler.search(obj, searchText, "requests", variable);
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
    		rs = obj.query("SELECT * FROM requests ORDER BY book_name;");
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
		
		requestsMemberUID.setCellValueFactory(new PropertyValueFactory<RequestsTable, Integer>("requestsMemberUID"));
		requestsBookName.setCellValueFactory(new PropertyValueFactory<RequestsTable, String>("requestsBookName"));
		requestsAuthorName.setCellValueFactory(new PropertyValueFactory<RequestsTable, String>("requestsAuthorName"));
		requestsPublisherName.setCellValueFactory(new PropertyValueFactory<RequestsTable, String>("requestsPublisherName"));
		requestsBookYear.setCellValueFactory(new PropertyValueFactory<RequestsTable, String>("requestsBookYear"));
		
		requestsTable.setItems(requestList);
		requestsTotalResults.setText(String.valueOf(requestList.size()) + " Result(s) ");
		
		obj.disconnect();
    }

    @FXML
    public void searchAuthors(KeyEvent event)
    {
    	SQL obj = new SQL();
    	obj.createConnection();
    	EnvironmentCreator.useDatabase(obj, "library");
    	
    	String searchText = searchBarAuthors.getText();
    	String variable = null;
    	ResultSet rs;
    	
    	if(Pattern.matches(".{1,}", searchText))
    	{
    		if(authorSearchChoice.getValue().equals("UID"))
    			variable = "author_id";
    		else
    			variable = "author_name";
    		
    		rs = SearchHandler.search(obj, searchText, "authors", variable);
    	}
    	else
    	{
    		rs = obj.query("SELECT * FROM authors ORDER BY author_id;");
    	}
    	ObservableList <AuthorsTable> authorList = FXCollections.observableArrayList();
    	
    	try
		{
			while(rs.next())
			{
				authorList.add(new AuthorsTable(rs.getInt("author_id"), rs.getString("author_name")));				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		authorUID.setCellValueFactory(new PropertyValueFactory<AuthorsTable, Integer>("authorUID"));
		authorName.setCellValueFactory(new PropertyValueFactory<AuthorsTable, String>("authorName"));
		authorsTable.setItems(authorList);
		authorsTotalResults.setText(authorList.size() + " Result(s) ");
		obj.disconnect();
    }

    @FXML
    public void searchPublishers(KeyEvent event)
    {
    	SQL obj = new SQL();
    	obj.createConnection();
    	EnvironmentCreator.useDatabase(obj, "library");
    	
    	String searchText = searchBarPublishers.getText();
    	String variable = null;
    	ResultSet rs;
    	ObservableList <PublishersTable> publisherList = FXCollections.observableArrayList();
    	
    	if(Pattern.matches(".{1,}", searchText))
    	{
    		if(publisherSearchChoice.getValue().equals("UID"))
    			variable = "publisher_id";
    		else
    			variable = "publisher_name";
    		
    		rs = SearchHandler.search(obj, searchText, "publishers", variable);
    	}
    	else
    	{
    		rs = obj.query("SELECT * FROM publishers ORDER BY publisher_id;");
    	} 	 
    	
    	try
		{
			while(rs.next())
			{
				publisherList.add(new PublishersTable(rs.getInt("publisher_id"), rs.getString("publisher_name")));				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		publisherUID.setCellValueFactory(new PropertyValueFactory<PublishersTable, Integer>("publisherUID"));
		publisherName.setCellValueFactory(new PropertyValueFactory<PublishersTable, String>("publisherName"));
		publishersTable.setItems(publisherList);
		publishersTotalResults.setText(publisherList.size() + " Result(s) ");
		
		obj.disconnect();
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
    			variable = "memberuid";
    		else if(logSearchChoice.getValue().equals("Member Name"))
    			variable = "m.name";
    		else if(logSearchChoice.getValue().equals("Book UID"))
    			variable = "bookuid";
    		else if(logSearchChoice.getValue().equals("Book Name"))
    			variable = "b.name";
    		else if(logSearchChoice.getValue().equals("Date Borrowed"))
    			variable = "l.borrowed";
    		else if(logSearchChoice.getValue().equals("Date Returned"))
    			variable = "l.returned";
    		
    		rs = SearchHandler.search(obj, searchText, "log", variable);
    	}
    	else
    	{
    		 rs = obj.query("SELECT l.uid uid, l.memberuid memberuid, l.bookuid bookuid, l.borrowed borrowed, l.returned returned, l.overdue overdue, m.name as mname, b.name as bname FROM log l, members m, books b where memberuid = m.uid and bookuid = b.uid ORDER BY l.uid desc;");
    	}
    	
    	try
		{
			while(rs.next())
			{
				logList.add(new LogTable(rs.getInt("uid"),
						rs.getInt("memberuid"),
						rs.getInt("bookuid"),
						rs.getString("borrowed"),
						rs.getString("returned"),
						rs.getInt("overdue"),
						rs.getString("mname"),
						rs.getString("bname")));				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		logTransactionID.setCellValueFactory(new PropertyValueFactory<LogTable, Integer>("logTransactionID"));
		logMemberUID.setCellValueFactory(new PropertyValueFactory<LogTable, Integer>("logMemberUID"));
		logBookUID.setCellValueFactory(new PropertyValueFactory<LogTable, Integer>("logBookUID"));
		logBorrowDate.setCellValueFactory(new PropertyValueFactory<LogTable, String>("logBorrowDate"));
		logReturnDate.setCellValueFactory(new PropertyValueFactory<LogTable, String>("logReturnDate"));
		logOverdue.setCellValueFactory(new PropertyValueFactory<LogTable, Integer>("logOverdue"));
		logMemberName.setCellValueFactory(new PropertyValueFactory<LogTable, String>("logMemberName"));
		logBookName.setCellValueFactory(new PropertyValueFactory<LogTable, String>("logBookName"));
		logTable.setItems(logList);
		logTotalResults.setText(logList.size() + " Result(s) ");
		
		obj.disconnect();
    }
    
    @FXML
    public void addBook(ActionEvent event) throws Exception
    {
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML/AddBook.fxml"));
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
    public void removeBook(ActionEvent event) throws Exception
    {
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML/RemoveBook.fxml"));
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
    
    public void editBook(ActionEvent event) throws Exception
    {
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML/EditBook.fxml"));
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
    public void addMember(ActionEvent event) throws Exception
    {
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML/AddUser.fxml"));
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
    public void editMember(ActionEvent event) throws Exception
    {
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML/EditUser.fxml"));
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
    public void removeMember(ActionEvent event) throws Exception
    {
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML/RemoveUser.fxml"));
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
    public void returnBook(ActionEvent event) throws Exception
    {
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML/ReturnBook.fxml"));
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
    public void addAuthor(ActionEvent event) throws Exception
    {
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML/AddAuthor.fxml"));
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
    public void removeAuthor(ActionEvent event) throws Exception
    {
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML/RemoveAuthor.fxml"));
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
    public void addPublisher(ActionEvent event) throws Exception
    {
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML/AddPublisher.fxml"));
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
    public void removePublisher(ActionEvent event) throws Exception
    {
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML/RemovePublisher.fxml"));
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
    public void clearRequest(ActionEvent event) throws Exception
    {
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML/clearRequest.fxml"));
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
	public void logout(ActionEvent event) throws Exception
	{
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
}

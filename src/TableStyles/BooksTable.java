package TableStyles;

public class BooksTable {
	public final int bookUID;
	public final String bookName;
	public final int bookAvailable;
	public final int bookTotal;
	public final String bookYear;
	public final String bookGenre;
	public final String bookAuthor;
	public final String bookPublisher;
	public final float bookPrice;
	public final String bookLocation;
	public final String bookState;
	public final String bookType;
	
	public BooksTable(int uid, String name, int avail, int total, String year, String genre, String author, String publisher, float price, String location, String state, String type)
	{
		this.bookUID = uid;
		this.bookName = name;
		this.bookAvailable = avail;
		this.bookTotal = total;
		this.bookYear = year;
		this.bookGenre = genre;
		this.bookAuthor = author;
		this.bookPublisher = publisher;
		this.bookPrice = price;
		this.bookLocation = location;
		this.bookState = state;
		this.bookType = type;
	}

	public int getBookUID() {
		return bookUID;
	}

	public String getBookName() {
		return bookName;
	}

	public int getBookAvailable() {
		return bookAvailable;
	}

	public int getBookTotal() {
		return bookTotal;
	}

	public String getBookYear() {
		return bookYear;
	}

	public String getBookGenre() {
		return bookGenre;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public String getBookPublisher() {
		return bookPublisher;
	}

	public float getBookPrice() {
		return bookPrice;
	}

	public String getBookLocation() {
		return bookLocation;
	}

	public String getBookState() {
		return bookState;
	}

	public String getBookType() {
		return bookType;
	}
}

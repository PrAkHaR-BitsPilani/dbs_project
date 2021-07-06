package TableStyles;

public class RequestsTable {
	public final int requestsMemberUID;
	public final String requestsBookName;
	public final String requestsAuthorName;
	public final String requestsPublisherName;
	public final String requestsBookYear;
	
	public RequestsTable(Integer memuid, String bookName, String authorName, String publisherName, String bookYear)
	{
		this.requestsMemberUID = memuid;
		this.requestsBookName = bookName;
		this.requestsAuthorName = authorName;
		this.requestsPublisherName = publisherName;
		this.requestsBookYear = bookYear;
	}

	public int getRequestsMemberUID() {
		return requestsMemberUID;
	}

	public String getRequestsBookName() {
		return requestsBookName;
	}

	public String getRequestsAuthorName() {
		return requestsAuthorName;
	}

	public String getRequestsPublisherName() {
		return requestsPublisherName;
	}

	public String getRequestsBookYear() {
		return requestsBookYear;
	}
}

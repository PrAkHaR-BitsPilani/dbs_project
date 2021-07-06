package TableStyles;

public class PublishersTable {
	public final int publisherUID;
	public final String publisherName;
	
	public PublishersTable(Integer id, String name)
	{
		this.publisherUID = id;
		this.publisherName = name;
	}

	public int getPublisherUID() {
		return publisherUID;
	}

	public String getPublisherName() {
		return publisherName;
	}

}

package TableStyles;

public class AuthorsTable {
	public final int authorUID;
	public final String authorName;
	
	public AuthorsTable(Integer uid, String name)
	{
		this.authorUID = uid;
		this.authorName = name;
	}

	public int getAuthorUID() {
		return authorUID;
	}

	public String getAuthorName() {
		return authorName;
	}	
}

package TableStyles;

public class BorrowedTable {
	public final int borrowedMemberUID;
	public final int borrowedBookUID;
	public final String borrowedBorrowDate;
	public final String borrowedDueDate;
	
	public BorrowedTable(Integer mUID, Integer bUID, String bDate, String dDate)
	{
		this.borrowedMemberUID = mUID;
		this.borrowedBookUID = bUID;
		this.borrowedBorrowDate = bDate;
		this.borrowedDueDate = dDate;
	}

	public int getBorrowedMemberUID() {
		return borrowedMemberUID;
	}

	public int getBorrowedBookUID() {
		return borrowedBookUID;
	}

	public String getBorrowedBorrowDate() {
		return borrowedBorrowDate;
	}

	public String getBorrowedDueDate() {
		return borrowedDueDate;
	}
}

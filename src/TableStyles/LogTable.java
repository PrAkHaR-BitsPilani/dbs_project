package TableStyles;

public class LogTable {
	public final int logTransactionID;
	public final int logMemberUID;
	public final int logBookUID;
	public final String logBorrowDate;
	public final String logReturnDate;
	public final int logOverdue;
	public final String logMemberName;
	public final String logBookName;
	
	public LogTable(Integer tID, Integer mID, Integer bID, String borrowed, String returned, Integer overdue, String memberName, String bookName)
	{
		this.logTransactionID = tID;
		this.logMemberUID = mID;
		this.logBookUID = bID;
		this.logBorrowDate = borrowed;
		this.logReturnDate = returned;
		this.logOverdue = overdue;
		this.logMemberName = memberName;
		this.logBookName = bookName;
	}

	public String getLogMemberName() {
		return logMemberName;
	}

	public String getLogBookName() {
		return logBookName;
	}

	public int getLogTransactionID() {
		return logTransactionID;
	}

	public int getLogMemberUID() {
		return logMemberUID;
	}

	public int getLogBookUID() {
		return logBookUID;
	}

	public String getLogBorrowDate() {
		return logBorrowDate;
	}

	public String getLogReturnDate() {
		return logReturnDate;
	}

	public int getLogOverdue() {
		return logOverdue;
	}
}

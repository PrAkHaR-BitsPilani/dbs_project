package Backend;
public class RequestsHandler
{
    public static boolean add(SQL obj, int memberId, String bookName, int authorId, int publisherId, String year)
    {
        return obj.update("INSERT INTO Requests VALUES (" + memberId + ", '" + bookName + "', " + authorId + ", " + publisherId + ", '" + year + "');");
    }
    
    public static boolean remove(SQL obj, String bookName)
    {
        return obj.update("DELETE FROM Requests WHERE book_name = '" + bookName + "';");
    }
}
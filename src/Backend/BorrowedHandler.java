package Backend;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BorrowedHandler
{
    static private boolean addBook(SQL obj, int memID, int bookID, String startDate, String endDate)
    {
        ResultSet rs = obj.query("SELECT available FROM BOOKS WHERE uid = " + bookID + ";");
        try
        {
            rs.next();
            if(rs.getInt(1) == 0)
                return false;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return obj.update("INSERT INTO Borrowed VALUES (" + memID + ", " + bookID + ", '" + startDate + "', '" + endDate + "');");
    }

    static public boolean add(SQL obj, int memID, int bookID)
    {
        String startDate, endDate;
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	Calendar cal = Calendar.getInstance();
        
        startDate = formatter.format(cal.getTime());
        
	cal.add(Calendar.DAY_OF_MONTH, 7);
	endDate = formatter.format(cal.getTime());
	
        if(BorrowedHandler.addBook(obj, memID, bookID, startDate, endDate))
        {
            int available = 0;
            try
            {
                ResultSet rs = obj.query("SELECT available FROM BOOKS WHERE uid = " + bookID + ";");
                rs.next();
                available = rs.getInt(1) - 1;
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
            obj.update("UPDATE Books SET available = " + available + " WHERE uid = " + bookID + ";");
            return true;
        }
        return false;
    }

    static private boolean removeBook(SQL obj, int memID, int bookID)
    {
        return obj.update("DELETE FROM Borrowed WHERE memberuid = " + memID + " AND bookuid = " + bookID + ";");
    }

    static public boolean remove(SQL obj, int memID, int bookID)
    {
        ResultSet rs1 = obj.query("SELECT borrowed FROM Borrowed WHERE memberuid = " + memID + " AND bookuid = " + bookID + ";");
        String borrowedDate = null, returnDate = null;
        try
        {
            rs1.next();
            borrowedDate = rs1.getString(1);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	Calendar cal = Calendar.getInstance();
        
        returnDate = formatter.format(cal.getTime());
        long od = OverdueCalc.calculate(obj, memID, bookID);
            
        if(removeBook(obj, memID, bookID))
        {
            int available = 0;
            obj.update("INSERT INTO Log (memberuid, bookuid, borrowed, returned, overdue) VALUES (" + memID + ", " + bookID + ", '" + borrowedDate + "', '" + returnDate + "', " + od + ");");
            
            try
            {
                ResultSet rs = obj.query("SELECT available FROM BOOKS WHERE uid = " + bookID + ";");
                rs.next();
                available = rs.getInt(1) + 1;
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
            obj.update("UPDATE Books SET available = " + available + " WHERE uid = " + bookID + ";");
            return true;
        }
        return false;
    }
}
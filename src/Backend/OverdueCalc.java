package Backend;
import java.sql.ResultSet;
import java.util.Date;
import java.text.SimpleDateFormat;

public class OverdueCalc
{
    public static long calculate(SQL obj, int memID, int bookID)
    {
        Date currentDate = new Date();
        Date dueDate = new Date();

        ResultSet rs = obj.query("SELECT due FROM Borrowed WHERE memberuid = " + memID + " AND bookuid = " + bookID + ";");

        try
        {
            rs.next();
            dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(1));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        long diff = currentDate.getTime() - dueDate.getTime();
        if(diff < 0)
            return 0;
        else
            return (10 * (diff / 86400000));
    }
}
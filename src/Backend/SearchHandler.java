package Backend;
import java.sql.ResultSet;

public class SearchHandler
{
    public static ResultSet search(SQL obj, String t, String table, String variable)
    {
    	String term = "";
    	for(int i = 0; i < t.length(); i++)
    	{
    		if(t.charAt(i) == 39)
    		{
    			term += '\'';
    			term += '\'';
    		}
    		else
    		{
    			term += t.charAt(i);
    		}
    	}
        ResultSet rs;
        
        if(table.equalsIgnoreCase("log"))
        	rs = obj.query("SELECT l.uid uid, l.memberuid memberuid, l.bookuid bookuid, l.borrowed borrowed, l.returned returned, l.overdue overdue, m.name as mname, b.name as bname FROM log l, members m, books b where memberuid = m.uid and bookuid = b.uid and LOWER(" + variable + ") LIKE LOWER('" + term + "%');");
        else
        	rs = obj.query("SELECT * FROM " + table + " WHERE LOWER(" + variable + ") LIKE LOWER('" + term + "%');");
		return rs;
    }
    
    public static ResultSet memberSearch(SQL obj, String t, String table, String variable, String uid)
    {
    	String term = "";
    	for(int i = 0; i < t.length(); i++)
    	{
    		if(t.charAt(i) == 39)
    		{
    			term += '\'';
    			term += '\'';
    		}
    		else
    		{
    			term += t.charAt(i);
    		}
    	}
        ResultSet rs;
        
        if(table.equalsIgnoreCase("log"))
        	rs = obj.query("SELECT l.uid uid, l.memberuid memberuid, l.bookuid bookuid, l.borrowed borrowed, l.returned returned, l.overdue overdue, m.name as mname, b.name as bname FROM log l, members m, books b where memberuid = " + uid + " and memberuid = m.uid and bookuid = b.uid and LOWER(" + variable + ") LIKE LOWER('" + term + "%');");
        else if(table.equalsIgnoreCase("requests"))
        	rs = obj.query("SELECT * FROM " + table + " WHERE member_id = " + uid + " AND LOWER(" + variable + ") LIKE LOWER('" + term + "%');");
        else
        	rs = obj.query("SELECT * FROM " + table + " WHERE LOWER(" + variable + ") LIKE LOWER('" + term + "%');");
		return rs;
    }
}
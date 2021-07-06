package Backend;

public class BookHandler
{
    static public boolean add(SQL obj, String name, int total, String year, String genre, int author, int publisher, float price, String location, int state, boolean type)
    {
        return obj.update("INSERT INTO Books (name, available, total, year , genre , author , publisher , price , location , state , type) VALUES ('" + name + "', " + total + ", "
            + total + ", '" + year + "', '" + genre + "', '" + author + "', '" + publisher + "', " + price + ", '" + location + "', " + state + ", " + (type ? "TRUE" : "FALSE") + ");");
    }
    
    static public boolean remove(SQL obj, int uid)
    {
        return obj.update("DELETE FROM Books WHERE uid = " + uid);
    }
    
    static public boolean edit(SQL obj, String name, String uid, int available, int total, String year, String genre, int author, int publisher, float price, String location, int state, boolean type)
    {
    	return obj.update("UPDATE books SET "
    			+ "name = '" + name + "', "
    			+ "total = " + total + ", "
    			+ "available = " + available + ", "
    			+ "year = '" + year + "', "
    			+ "genre = '" + genre + "', "
    			+ "author = " + author + ", "
    			+ "publisher = " + publisher + ", "
    			+ "price = " + price + ", "
    			+ "location = '" + location + "', "
    			+ "state = " + state + ", "
    			+ "type = " + (type ? "TRUE" : "FALSE") + " "
    			+ "WHERE uid = " + uid + ";");
    }
}
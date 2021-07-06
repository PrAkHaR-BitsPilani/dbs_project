package Backend;
public class AuthorHandler
{
    static public boolean add(SQL obj, String name)
    {
        return obj.update("INSERT INTO Authors (author_name) VALUES ('" + name + "');");
    }
    
    static public boolean remove(SQL obj, int uid)
    {
        return obj.update("DELETE FROM Authors WHERE author_id = " + uid + ";");
    }
}
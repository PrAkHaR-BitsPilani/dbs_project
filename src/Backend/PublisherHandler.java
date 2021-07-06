package Backend;
public class PublisherHandler
{
    static public boolean add(SQL obj, String name)
    {
        return obj.update("INSERT INTO Publishers (publisher_name) VALUES ('" + name + "');");
    }
    
    static public boolean remove(SQL obj, int uid)
    {
        return obj.update("DELETE FROM Publishers WHERE publisher_id = " + uid + ";");
    }
}
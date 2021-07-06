package Backend;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MemberHandler
{
    static public boolean register(SQL obj, String name, String password, String address, String phone, String email)
    {
        String joinDate;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        
        joinDate = formatter.format(cal.getTime());
        
        return obj.update("INSERT INTO Members (name, password, address, phone, email, joined) VALUES ('" + name + "', '" + password + "', '" + address + "', '" + phone + "', '" + email + "', '" + joinDate + "');");
    }
    
    static public boolean unregister(SQL obj, int uid)
    {
        return obj.update("DELETE FROM Members WHERE uid = " + uid);
    }
    
    static public boolean edit(SQL obj, String uid, String name, String password, String address, String phone, String email)
    {
    	return obj.update("UPDATE members SET "
    			+ "name = '" + name + "', "
    			+ "password = '" + password + "', "
    			+ "address = '" + address + "', "
    			+ "phone = '" + phone + "', "
    			+ "email = '" + email + "' "
    			+ "WHERE uid = " + uid + ";");
    }
}
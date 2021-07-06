package Backend;
import java.sql.*;

public class SQL
{
    private String uName;
    private String uPassword;
    private String url = "jdbc:mysql://localhost:3306/";
    private Connection connection;
    private Statement statement;
    
    public SQL()
    {
        uName = "root";
        uPassword = "admin";        
    }

    public SQL(String uName, String uPassword)
    {
        this.uName = uName;
        this.uPassword = uPassword;
    }

    public boolean checkDriver()
    {    
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;        
    }

    public boolean createConnection()
    {
        try
        {
            this.connection = DriverManager.getConnection(url, uName, uPassword);
            this.statement = connection.createStatement();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;     
    }

    public boolean disconnect()
    {
        try
        {
            if(statement!=null)
                statement.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        try
        {
            if(connection!=null)
                connection.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean update(String command)
    {
        try
        {
            this.statement.executeUpdate(command);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;        
    }

    public ResultSet query(String command)
    {
        try
        {
            return this.statement.executeQuery(command);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
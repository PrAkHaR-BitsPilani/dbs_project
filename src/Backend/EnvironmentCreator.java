package Backend;
public class EnvironmentCreator
{
    public static boolean createEnvironment(SQL obj)
    {
        createMembersTable(obj);
        createAuthorsTable(obj);
        createPublishersTable(obj);
        createBooksTable(obj);
        createBorrowedTable(obj);
        createRequestsTable(obj);
        createLogTable(obj);
        return true;
    }

    public static boolean createDatabase(SQL obj, String name)
    {
        return obj.update("CREATE DATABASE " + name + ";");
    }

    public static boolean useDatabase(SQL obj, String name)
    {
        return obj.update("USE " + name + ";");
    }

    public static boolean createMembersTable(SQL obj)
    {
        return obj.update("CREATE TABLE Members (uid int PRIMARY KEY AUTO_INCREMENT, name varchar(30) NOT NULL, password varchar(25) NOT NULL, address varchar(200) NOT NULL, phone varchar(10) NOT NULL, email varchar(100) UNIQUE NOT NULL, joined varchar(11) NOT NULL, "
        + "UNIQUE(name, phone));");
    }

    public static boolean createBooksTable(SQL obj)
    {
        return obj.update("CREATE TABLE Books (uid int PRIMARY KEY AUTO_INCREMENT, name varchar(100) NOT NULL, available int NOT NULL, total int NOT NULL, year varchar(4) NOT NULL, genre varchar(25) NOT NULL, "
            + "author int NOT NULL, publisher int NOT NULL, price float(10,2) NOT NULL, location varchar(25) NOT NULL, state int NOT NULL, type boolean NOT NULL, FOREIGN KEY(author) REFERENCES Authors(author_id) ON DELETE CASCADE, "
            + "FOREIGN KEY(publisher) REFERENCES Publishers(publisher_id) ON DELETE CASCADE, UNIQUE(name, author));");
    }

    public static boolean createBorrowedTable(SQL obj)
    {
        return obj.update("CREATE TABLE Borrowed (memberuid int, bookuid int, borrowed varchar(11) NOT NULL, due varchar(11) NOT NULL, PRIMARY KEY(memberuid, bookuid), FOREIGN KEY(memberuid) REFERENCES Members(uid) ON DELETE CASCADE, "
            + "FOREIGN KEY(bookuid) REFERENCES Books(uid) ON DELETE CASCADE);");
    }

    public static boolean createAuthorsTable(SQL obj)
    {
        return obj.update("CREATE TABLE Authors (author_id int PRIMARY KEY AUTO_INCREMENT, author_name varchar(30) UNIQUE);");
    }

    public static boolean createPublishersTable(SQL obj)
    {
        return obj.update("CREATE TABLE Publishers (publisher_id int PRIMARY KEY AUTO_INCREMENT, publisher_name varchar(30) UNIQUE);");
    }
    
    public static boolean createRequestsTable(SQL obj)
    {
        return obj.update("CREATE TABLE Requests (member_id int, book_name varchar(100), author_id int, publisher_id int, year_published varchar(4), PRIMARY KEY(member_id, book_name), FOREIGN KEY(author_id) "
            + "REFERENCES Authors(author_id) ON DELETE CASCADE, FOREIGN KEY(publisher_id) REFERENCES Publishers(publisher_id) ON DELETE CASCADE);");
    }

    public static boolean createLogTable(SQL obj)
    {
        return obj.update("CREATE TABLE Log (uid int PRIMARY KEY AUTO_INCREMENT, memberuid int, bookuid int, borrowed varchar(11) NOT NULL, returned varchar(11) NOT NULL, Overdue INT NOT NULL);");
    }
}
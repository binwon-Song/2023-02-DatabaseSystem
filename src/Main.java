import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseManagement.DBInit();
        DatabaseManagement.DBInsert(23,"testBook","testPub",1234);
        DatabaseManagement.DBSelect();
        DatabaseManagement.DBDelete(23);
        DatabaseManagement.DBSelect();
    }
}
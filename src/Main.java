import java.sql.*;
public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://192.168.31.133:3308/madang";
        String userName = "binwon";
        String password = "1234";

        Connection connection = DriverManager.getConnection(url, userName, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from Book");

        while(resultSet.next())
            System.out.println(resultSet.getInt(1)+"  "+resultSet.getString(2)+resultSet.getString(3));
        connection.close();
    }
}
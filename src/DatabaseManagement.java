import java.sql.*;

public class DatabaseManagement {
    private static Connection connection;

    public static void DBInit() throws SQLException {
        try{
            String url = "jdbc:mysql://192.168.31.133:4567/madang";
            String userName = "binwon";
            String password = "1234";
            connection = DriverManager.getConnection(url, userName, password);
        }catch(Exception e){System.out.println(e);}

    }

    public static void DBInsert(Integer id, String name,String publisher, Integer price) throws SQLException {
        String sql = "INSERT INTO Book (bookid, bookname,publisher,price) VALUES (?, ?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, publisher);
        preparedStatement.setInt(4, price);


        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Data inserted successfully.");
        } else {
            System.out.println("Data insertion failed.");
        }
    }

    public static void DBSelect() throws SQLException {
        String sql = "SELECT * FROM Book";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
        }
    }
    public static void DBDelete(int bookId) throws SQLException {
        String sql = "DELETE FROM Book WHERE bookid = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, bookId);

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Data deleted successfully.");
        } else {
            System.out.println("Data deletion failed. (Book with ID " + bookId + " not found)");
        }
    }
    public static void DBClose() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing the connection: " + e.getMessage());
            }
        }
    }


}

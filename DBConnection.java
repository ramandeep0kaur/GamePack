import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/gamepack";
    private static final String USER = "root";
    private static final String PASS = "raman@217"; // apna MySQL password yahan likh

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

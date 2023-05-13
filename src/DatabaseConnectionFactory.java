import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionFactory {
    public static Connection openConnection() throws SQLException {
        String databasePassword = retrieveDatabasePassword();
        Connection conn = DriverManager
                .getConnection("jdbc:mysql://localhost/music_library_app?" + "user=root&password=" + databasePassword);
        return conn;
    }

    public static void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static String retrieveDatabasePassword() throws NullPointerException {
        String databasePassword = System.getenv("DBPWRD");
        if (databasePassword == null) {
            throw new NullPointerException();
        }
        return databasePassword;
    }
}

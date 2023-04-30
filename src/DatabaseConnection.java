import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection openConnection() {
        String databasePassword = System.getenv("DBPWRD");
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/music_library_app?" +
                    "user=root&password=" + databasePassword);
            return conn;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    public void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            } else {
                System.out.println("Connection already closed.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SongSearch extends DatabaseConnection {
    private Connection conn;

    public SongData getSongData(String title, String artist) throws IllegalArgumentException {
        conn = openConnection();
        if (songExists(title, artist)) {
            String query = findSongQuery(title, artist);

            SongData songData = extractSongData(query);
            closeConnection(conn);
            return songData;
        } else {
            closeConnection(conn);
            throw new IllegalArgumentException("Song not available.");
        }
    }

    public SongData getSongData(int ID) {
        conn = openConnection();
        String query = findSongWithIDQuery(ID);
        SongData songData = extractSongData(query);
        closeConnection(conn);
        return songData;
    }

    private String findSongQuery(String title, String artist) {
        DatabaseQueryBuilder queryBuilder = new DatabaseQueryBuilder();
        String[] select = {"*"};
        String from = "Songs";
        String[] where = {"Title", "Artist"};
        String[] condition = {title, artist};
        return queryBuilder.selectFromWhere(select, from, where, condition);
    }

    private boolean songExists(String title, String artist) {
        String query = findSongQuery(title, artist);
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    private SongData extractSongData(String databaseQuery) {
        SongData songData = new SongData();
        try {
            ResultSet rs = getSongResultSet(databaseQuery);

            if (rs.next()) {
                songData.songID = rs.getInt(rs.findColumn("SongID"));
                songData.title = rs.getString(rs.findColumn("Title"));
                songData.artist = rs.getString(rs.findColumn("Artist"));
                songData.album = rs.getString(rs.findColumn("Album"));
                songData.genre = rs.getString(rs.findColumn("Genre"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return songData;
    }

    private ResultSet getSongResultSet(String query) {
        try {
            Statement statement = conn.createStatement();
            return statement.executeQuery(query);
        } catch (Exception e) {
            System.out.println("Error getting song result set.");
            System.out.println(e);
        }
        return null;
    }

    private String findSongWithIDQuery(int ID) {
        DatabaseQueryBuilder queryBuilder = new DatabaseQueryBuilder();
        String[] select = {"*"};
        String from = "Songs";
        String[] where = {"SongID"};
        String[] condition = {Integer.toString(ID)};
        return queryBuilder.selectFromWhere(select, from, where, condition);
    }

    public static void main(String[] args) {
        SongSearch ml = new SongSearch();

        System.out.println(ml.getSongData("Banger", "Jack").album);
    }
}

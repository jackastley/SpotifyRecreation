import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SongSearch{
    private Connection conn;

    public SongSearch(){
        try{
            conn = DatabaseConnectionFactory.openConnection();
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }

    public SongData getSongData(String title, String artist) throws IllegalArgumentException {
        if (songExists(title, artist)) {
            String query = findSongQuery(title, artist);
            SongData songData = extractSongData(query);
            return songData;
        } else {
            throw new IllegalArgumentException("Song not available.");
        }
    }

    public SongData getSongData(int ID) {
        String query = findSongWithIDQuery(ID);
        SongData songData = extractSongData(query);
        return songData;
    }

    private String findSongQuery(String title, String artist) {
        String[] select = {"*"};
        String from = "Songs";
        String[] where = {"Title", "Artist"};
        String[] condition = {title, artist};
        return DatabaseQueryGenerator.selectFromWhere(select, from, where, condition);
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
        try {
            ResultSet rs = getSongResultSet(databaseQuery);

            if (rs.next()) {
                int songID = rs.getInt(rs.findColumn("SongID"));
                String title = rs.getString(rs.findColumn("Title"));
                String artist = rs.getString(rs.findColumn("Artist"));
                String album = rs.getString(rs.findColumn("Album"));
                String genre = rs.getString(rs.findColumn("Genre"));
                SongData songData = new SongData(songID, title, artist, album, genre);
                return songData;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
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
        String[] select = {"*"};
        String from = "Songs";
        String[] where = {"SongID"};
        String[] condition = {Integer.toString(ID)};
        return DatabaseQueryGenerator.selectFromWhere(select, from, where, condition);
    }

}

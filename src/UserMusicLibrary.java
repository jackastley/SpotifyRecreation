import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class UserMusicLibrary extends DatabaseConnectionFactory {
    private UserData userData;
    private Connection conn;

    public UserMusicLibrary(UserData userData) {
        this.userData = userData;
        try{
            conn = DatabaseConnectionFactory.openConnection();
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }

    public void playSong(SongData songData) {
        System.out.println("Playing " + songData.title + " by " + songData.artist);
        System.out.println();
    }

    public void addSongToPlaylist(SongData songData, UserPlaylistData userPlaylistData) {
        try {
            if (!songIsInPlaylist(songData, userPlaylistData)) {
                conn = openConnection();
                String query = addSongQuery(songData.songID, userPlaylistData.playlistID);
                Statement statement = conn.createStatement();
                statement.execute(query);
                System.out.println(songData.title + " has been added to your playlist.");
            } else {
                System.out.println("Song already in playlist.");
            }
        } catch (Exception e) {
            System.out.println("Song could not be added.");
            System.out.println(e);
        }
        System.out.println();
    }

    public SongData[] getSongsInPlaylist(UserPlaylistData playlistData) {
        ResultSet songsInPlaylistResultSet = getSongsInPlaylistResultSet(playlistData.playlistID);
        SongData[] songData = extractPlaylistSongData(songsInPlaylistResultSet);
        return songData;
    }

    public UserPlaylistData getUserPlaylistData() {
        if (userHasPlaylist()) {
            ResultSet rs = getPlaylistResultSet();
            UserPlaylistData userPlaylistData = extractUserPlaylistData(rs);
            return userPlaylistData;
        } else {
            System.out.println("User does not have playlist.");
        }
        return null;
    }

    private boolean songIsInPlaylist(SongData songData, UserPlaylistData playlistData) {
        try {
            conn = openConnection();
            String[] select = {"*"};
            String from = "UserPlaylistContent";
            String[] where = {"SongID", "PlaylistID"};
            String[] condition = {Integer.toString(songData.songID), Integer.toString(playlistData.playlistID)};
            String query = DatabaseQueryGenerator.selectFromWhere(select, from, where, condition);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error checking if song is in playlist");
            System.out.println(e);
        }
        return false;
    }

    private String addSongQuery(int songID, int playlistID) {
        String[] columns = {"SongID", "PlaylistID"};
        String[] values = {Integer.toString(songID), Integer.toString(playlistID)};
        return DatabaseQueryGenerator.insert("UserPlaylistContent", columns, values);
    }

    private ResultSet getSongsInPlaylistResultSet(int playlistID) {
        try {
            // get songIDs
            Statement statement = conn.createStatement();
            String[] select = {"*"};
            String from = "UserPlaylistContent";
            String[] where = {"PlaylistID"};
            String[] condition = {Integer.toString(playlistID)};
            String query = DatabaseQueryGenerator.selectFromWhere(select, from, where, condition);
            return statement.executeQuery(query);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private SongData[] extractPlaylistSongData(ResultSet songsInPlaylistResultSet) {
        LinkedList<Integer> songIDs = new LinkedList<>();
        try {
            while (songsInPlaylistResultSet.next()) {
                songIDs.add(songsInPlaylistResultSet.getInt(songsInPlaylistResultSet.findColumn("SongID")));
            }
            SongSearch songSearcher = new SongSearch();
            SongData[] songDataArray = new SongData[songIDs.size()];
            int index = 0;
            for (int id : songIDs) {
                songDataArray[index] = songSearcher.getSongData(id);
                index++;
            }
            return songDataArray;
        } catch (Exception e) {
            System.out.println("Error while extracting playlist data.");
            System.out.println(e);
        }
        return null;
    }

    private boolean userHasPlaylist() {
        try {
            ResultSet rs = getPlaylistResultSet();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    private ResultSet getPlaylistResultSet() {
        try {
            Statement statement = conn.createStatement();
            String[] select = {"*"};
            String from = "UserPlaylists";
            String[] where = {"OwnerID"};
            String[] condition = {Integer.toString(userData.userID)};
            String query = DatabaseQueryGenerator.selectFromWhere(select, from, where, condition);
            return statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    private UserPlaylistData extractUserPlaylistData(ResultSet rs) {
        try {
            rs.next();
            int playlistID = rs.getInt(rs.findColumn("PlaylistID"));
            String playlistTitle = rs.getString(rs.findColumn("PlaylistTitle"));
            return new UserPlaylistData(playlistID, playlistTitle);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}

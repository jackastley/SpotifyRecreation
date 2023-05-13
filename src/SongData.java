public class SongData {
    public final int songID;
    public final String title;
    public final String artist;
    public final String album;
    public final String genre;

    public SongData(int songID, String title, String artist, String album, String genre) {
        this.songID = songID;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
    }
}

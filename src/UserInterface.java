import java.util.Scanner;

public class UserInterface {
    private UserData userData;
    private UserMusicLibrary userMusicLibrary;
    private SongSearch songSearcher;
    private boolean isLoggedIn;

    public UserInterface() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            while (!isLoggedIn) {
                System.out.print("Enter your username: ");
                String username = scanner.nextLine();

                System.out.print("Enter your password: ");
                String password = scanner.nextLine();

                login(username, password);
                System.out.println();
            }
            userMusicLibrary = new UserMusicLibrary(userData);
            songSearcher = new SongSearch();
            userCommands();
        }
    }

    public void login(String username, String password) {
        try {
            UserValidator userValidator = new UserValidator();
            userData = userValidator.getUserData(username, password);
            isLoggedIn = true;

        } catch (IllegalArgumentException e) {
            System.out.println("Username or password are incorrect.");
            System.out.println("Try again.");
        }
    }

    public void logout() {
        userData = null;
        userMusicLibrary = null;
        songSearcher = null;
        isLoggedIn = false;
    }

    public void userCommands() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Enter one of the following commands:");
        System.out.println("- Play song (s)");
        System.out.println("- Add song to playlist (a)");
        System.out.println("- Print my playlist (p)");
        System.out.println("- Logout (l)");

        System.out.print("Command: ");
        String command = scanner.nextLine();
        System.out.println();

        switch (command) {
            case "s":
                playSong();
                break;
            case "a":
                addSongToPlaylist();
                break;
            case "p":
                printPlaylist();
                break;
            case "l":
                logout();
                break;
            default:
                System.out.println("Invalid command. Try again.\n");
        }
    }

    public void playSong() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter song title: ");
        String title = scanner.nextLine();

        System.out.print("Enter song artist: ");
        String artist = scanner.nextLine();
        System.out.println();

        try {
            SongData songData = songSearcher.getSongData(title, artist);
            userMusicLibrary.playSong(songData);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void printPlaylist() {
        UserPlaylistData playlistData = userMusicLibrary.getUserPlaylistData();
        SongData[] songs = userMusicLibrary.getSongsInPlaylist(playlistData);

        System.out.format("%8s %8s %8s %8s", "Title", "Artist", "Album", "Genre\n");
        for (SongData song : songs) {
            printSongData(song);
        }
        System.out.println();
    }

    public void addSongToPlaylist() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter song title: ");
        String title = scanner.nextLine();

        System.out.print("Enter song artist: ");
        String artist = scanner.nextLine();
        System.out.println();

        try {
            SongData songData = songSearcher.getSongData(title, artist);
            UserPlaylistData playlistData = userMusicLibrary.getUserPlaylistData();
            userMusicLibrary.addSongToPlaylist(songData, playlistData);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void printSongData(SongData song) {
        System.out.format("%8s %8s %8s %8s", song.title, song.artist, song.artist, song.genre + "\n");
    }

}

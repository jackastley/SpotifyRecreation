USE music_library_app;

CREATE TABLE Users(
    UserID int NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (UserID),
    UserName varchar(255) NOT NULL,
    FirstName varchar(255),
    LastName varchar(255),
    LastSignedIn timestamp
);


CREATE TABLE Songs(
    SongID int NOT NULL AUTO_INCREMENT,
    Title varchar(255) NOT NULL,
    Artist varchar(255) NOT NULL,
    Album varchar(255),
    Genre varchar(255),
    PRIMARY KEY (SongID)
);


CREATE TABLE UserPlaylists(
    PlaylistID int NOT NULL AUTO_INCREMENT,
    PlaylistTitle varchar(255),
    OwnerID int NOT NULL,
    PRIMARY KEY (PlaylistID),
    FOREIGN KEY (OwnerID) REFERENCES Users(UserID)
);

CREATE TABLE UserPlaylistContent(
    SongID int NOT NULL,
    PlaylistID int NOT NULL,
    FOREIGN KEY (SongID) REFERENCES Songs(SongID),
    FOREIGN KEY (PlaylistID) REFERENCES UserPlaylists(PlaylistID)
);


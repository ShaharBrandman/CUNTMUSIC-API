# PostgresSQL Setup
<img src = "CUNTMUSIC-DB-Diagram.svg">

## Enable [`pgcrypto`](https://www.postgresql.org/docs/current/pgcrypto.html)
```CREATE EXTENSION IF NOT EXISTS pgcrypto;```

## Create Users Table
```
CREATE TABLE Users (
    ID SERIAL PRIMARY KEY,
    username text NOT NULL UNIQUE,
    password CHAR(64) NOT NULL,                     -- Store the password hash (64 chars for SHA256)
    TokenIssueDate TIMESTAMP  NOT NULL,             -- Timestamp for when the JWT token was issued
    TokenExpireDate TIMESTAMP  NOT NULL             -- Timestamp for when the JWT token expires
);
```

Example for inserting values into Users
```
INSERT INTO Users (username, password, TokenIssueDate, TokenExpireDate)
VALUES (
    'Test',
    ENCODE(DIGEST('Test', 'sha256'), 'hex'),
    NOW(),
    NOW() + INTERVAL '1 Hour'
);
```

## Create Playlists Table
```
CREATE TABLE Playlists (
    PlaylistID SERIAL PRIMARY KEY,
    UsernameID INT REFERENCES Users(ID) ON DELETE CASCADE NOT NULL UNIQUE,              -- Foreign key from Users table
    PlaylistName text NOT NULL
);
```

Example for inserting values into Playlists
```
INSERT INTO Playlists (UsernameID, PlaylistName) 
VALUES (
    (SELECT ID FROM Users WHERE username = 'Test'),
    'Test Songs'
);
```

## Create the Songs Table
```
CREATE TABLE Songs (
    SongID VARCHAR(11) PRIMARY KEY NOT NULL UNIQUE,
    Title text NOT NULL,
    Author text NUL NULL,
    Path text NOT NULL UNIQUE
);
```

Example for inserting value into Songs
```
INSERT INTO Songs (SongID, Title, Author, Path)
VALUES(
    'DEEL9nvxwsc',
    'Dove Cameron - Too Much (Official Visualizer)',
    'Dove Cameron',
    'home/cuntmusic/fs/DEEL9nvxwsc'
);
```

## Create the Playlists Songs Table
```
CREATE TABLE PlaylistSongs (
    PlaylistID INT REFERENCES Playlists(PlaylistID) ON DELETE CASCADE,
    SongID VARCHAR(11) REFERENCES Songs(SongID) ON DELETE CASCADE,
    PRIMARY KEY (PlaylistID, SongID)
);
```

Example for inserting Songs into PlaylistSongs
```
INSERT INTO PlaylistSongs (PlaylistID, SongID)
VALUES (
    (
        SELECT PlaylistID FROM Playlists WHERE PlaylistName = 'Test Songs'
    ),
    (
        SELECT SongID FROM Songs WHERE Title = 'Dove Cameron - Too Much (Official Visualizer)'
    )
);
```


# Finding all playlists of a User
```
SELECT
    P.PlaylistID,
    P.PlaylistName
FROM
    Playlists P
JOIN
    Users U ON P.PlaylistID = U.ID
WHERE
    U.username = 'Test';
```

# Finding all songs for a specific Playlist by name
```
SELECT
    S.SongID,
    S.Title
FROM
    Songs S
JOIN
    PlaylistSongs PS ON PS.SongID = S.SongID
JOIN
    Playlists P ON P.PlaylistID = PS.PlaylistID
WHERE
    P.PlaylistName = 'Test Songs';
```
package com.cuntmusic.utils.tables;

import jakarta.persistence.*;

@Entity
@Table(name = "playlistsongs")
@IdClass(PlaylistSongsId.class) //composite the primary key
public class PlaylistSongs {
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "playlistid", nullable = false)
    private Playlists playlistid;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "songid", nullable = false)
    private Songs songid;

    // public PlaylistSongs(Playlists playlist, Songs song) {
    //     this.playlistid = playlist;
    //     this.songid = song;
    // }

}

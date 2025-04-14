package com.cuntmusic.utils.tables;

import java.io.Serializable;
import java.util.Objects;

//a simple class that is used to composite the playlistSongs Primary key
//pretty stupid but it will be changed in the future
public class PlaylistSongsId implements Serializable {

    private Long playlistid;
    private String songid;

    public PlaylistSongsId() {}

    public PlaylistSongsId(Long playlistid, String songid) {
        this.playlistid = playlistid;
        this.songid = songid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaylistSongsId)) return false;
        PlaylistSongsId that = (PlaylistSongsId) o;
        return Objects.equals(playlistid, that.playlistid) &&
               Objects.equals(songid, that.songid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlistid, songid);
    }
}
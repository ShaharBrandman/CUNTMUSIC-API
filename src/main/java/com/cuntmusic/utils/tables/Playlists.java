package com.cuntmusic.utils.tables;

import jakarta.persistence.*;

@Entity
@Table(name = "playlists")
public class Playlists {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlistid;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Users usernameid;

    @Column(nullable = false)
    private String playlistname;
}

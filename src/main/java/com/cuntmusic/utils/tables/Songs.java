package com.cuntmusic.utils.tables;

import jakarta.persistence.*;

@Entity
@Table(name = "songs")
public class Songs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songid;
    
    @Column(nullable = false, unique = true)
    private long title;

    @Column(nullable = false)
    private String path;
}

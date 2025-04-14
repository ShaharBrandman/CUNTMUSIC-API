package com.cuntmusic.utils.tables;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String username;

    @Column(length = 64, nullable = false)
    private String password;
    
    @Column(nullable = false)
    private LocalDateTime tokenIssuedDate;

    @Column(nullable = false)
    private LocalDateTime tokenExpireDate;

    public Users(String username, String password, LocalDateTime tokenIssuedDate, LocalDateTime tokenExpireDate) {
        this.username = username;
        this.setPassword(password);
        this.tokenIssuedDate = tokenIssuedDate;
        this.tokenExpireDate = tokenExpireDate;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
    
    //stores the password hashed using SHA256 and encoded as bash64
    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getTokenIssuedDate() {
        return this.tokenIssuedDate;
    }

    public LocalDateTime getTokenExpireDate() {
        return this.tokenExpireDate;
    }

    public void setTokenIssuedDate(LocalDateTime tokenIssuedDate) {
        this.tokenIssuedDate = tokenIssuedDate;
    }

    public void setTokenExpireDate(LocalDateTime tokenExpireDate) {
        this.tokenExpireDate = tokenExpireDate;
    }
}

package com.eax.videoapp.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Getter @Setter
    String username;

    @Getter @Setter
    String publicName;

    @Getter @Setter
    String password;

    @Getter @Setter
    String email;

    @Getter @Setter
    String accessLevel;

    @Getter @Setter
    Date joinDate;

    @Getter @Setter
    int subscribers;

    public User(String username, String password, String email, Date joinDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.joinDate = joinDate;
    }
}

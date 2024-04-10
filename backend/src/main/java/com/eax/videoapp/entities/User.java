package com.eax.videoapp.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    public String username;

    @Getter @Setter
    public String publicName;
    @Getter @Setter
    public String password;
    @Getter @Setter
    public String email;

    @Getter @Setter
    public String accessLevel;

}

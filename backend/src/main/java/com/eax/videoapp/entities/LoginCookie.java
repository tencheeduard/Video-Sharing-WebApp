package com.eax.videoapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LoginCookie {

    @Getter @Setter
    @Id
    public String UUID;

    @Getter @Setter
    public String user;

}

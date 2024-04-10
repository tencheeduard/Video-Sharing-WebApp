package com.eax.videoapp.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    @Id
    public String videoId;

    public String user;

    public String title;

    public String description;

    public String tags;

}

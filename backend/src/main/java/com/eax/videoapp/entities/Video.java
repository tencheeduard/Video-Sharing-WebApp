package com.eax.videoapp.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    @Id
    @Getter @Setter
    String videoId;

    @Getter @Setter
    String user;

    @Getter @Setter
    String title;

    @Getter @Setter
    String description;

    @Getter @Setter
    String tags;

    @Getter @Setter
    Timestamp date;

    @Getter @Setter
    String category;

    @Getter @Setter
    int views;

    @Getter @Setter
    int likes;

}

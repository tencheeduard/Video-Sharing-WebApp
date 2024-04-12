package com.eax.videoapp.entities;

import com.eax.videoapp.embeddableIds.UserInteractionId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class LikedVideo {

    @EmbeddedId
    UserInteractionId id;

}

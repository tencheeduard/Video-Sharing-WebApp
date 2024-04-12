package com.eax.videoapp.embeddableIds;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class UserInteractionId implements Serializable {

    @Getter
    String username;
    @Getter
    String videoId;

}

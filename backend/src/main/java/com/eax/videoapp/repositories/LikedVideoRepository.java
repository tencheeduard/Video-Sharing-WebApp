package com.eax.videoapp.repositories;

import com.eax.videoapp.embeddableIds.UserInteractionId;
import com.eax.videoapp.entities.LikedVideo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LikedVideoRepository extends CrudRepository<LikedVideo, UserInteractionId> {

    public List<LikedVideo> findAllByIdUsername(String username);
}

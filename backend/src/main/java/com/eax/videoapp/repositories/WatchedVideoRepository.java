package com.eax.videoapp.repositories;

import com.eax.videoapp.embeddableIds.UserInteractionId;
import com.eax.videoapp.entities.LikedVideo;
import com.eax.videoapp.entities.WatchedVideo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface WatchedVideoRepository extends CrudRepository<WatchedVideo, UserInteractionId> {
    public List<WatchedVideo> findAllByIdUsername(String username);
}

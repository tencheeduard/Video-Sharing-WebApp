package com.eax.videoapp.repositories;

import com.eax.videoapp.entities.Video;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VideoRepository extends CrudRepository<Video,String> {

    List<Video> findByVideoId(String videoId);
    List<Video> findByCategory(String category);

    List<Video> findTop50ByOrderByDateDesc();

    List<Video> findTop50ByOrderByViewsDesc();

    List<Video> findTop50ByOrderByLikesDesc();

}
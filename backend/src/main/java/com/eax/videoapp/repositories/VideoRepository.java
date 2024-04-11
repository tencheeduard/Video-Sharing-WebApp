package com.eax.videoapp.repositories;

import com.eax.videoapp.entities.Video;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VideoRepository extends CrudRepository<Video,String> {

    List<Video> findByVideoId(String videoId);
    List<Video> findByCategory(String category);

    @Query("SELECT video FROM Video video ORDER BY video.date DESC")
    List<Video> findFirst20OrderByDateDesc();

    @Query("SELECT video FROM Video video ORDER BY video.likes DESC")
    List<Video> findFirst20OrderByLikesDesc();

}
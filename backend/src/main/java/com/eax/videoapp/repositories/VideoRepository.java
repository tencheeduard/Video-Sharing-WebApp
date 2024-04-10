package com.eax.videoapp.repositories;

import com.eax.videoapp.entities.User;
import com.eax.videoapp.entities.Video;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VideoRepository extends CrudRepository<Video,String> {

    List<Video> findByVideoId(String videoId);

}
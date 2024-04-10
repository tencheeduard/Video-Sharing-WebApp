package com.eax.videoapp.services;

import com.eax.videoapp.entities.Video;
import com.eax.videoapp.repositories.VideoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class StreamingService {

    @Autowired
    private VideoRepository videoRepository;

    public static final String FILE_PATH = "C://Users//edi//Documents//GitHub//webapp//backend//src//main//resources//videos//";

    public Mono<Resource> getVideo(String id) throws Exception {
        List<Video> videos = videoRepository.findByVideoId(id);

        if(!videos.isEmpty()) {
            return Mono.just(new FileSystemResource(FILE_PATH + id + ".mp4"));
        }
        throw new Exception("Could Not Find Video With Id " + id);
    }

}

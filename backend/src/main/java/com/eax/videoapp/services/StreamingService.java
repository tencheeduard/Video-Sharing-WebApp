package com.eax.videoapp.services;

import com.eax.videoapp.embeddableIds.UserInteractionId;
import com.eax.videoapp.entities.LikedVideo;
import com.eax.videoapp.entities.User;
import com.eax.videoapp.entities.Video;
import com.eax.videoapp.entities.WatchedVideo;
import com.eax.videoapp.repositories.LikedVideoRepository;
import com.eax.videoapp.repositories.VideoRepository;
import com.eax.videoapp.repositories.WatchedVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class StreamingService {

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    LikedVideoRepository likedVideoRepository;

    @Autowired
    WatchedVideoRepository watchedVideoRepository;

    @Autowired
    LoginService loginService;

    public static final String FILE_PATH = "C:\\Users\\edi\\Documents\\GitHub\\webapp\\backend\\src\\main\\resources\\videos\\";
    public static final String THUMB_PATH = "C:\\Users\\edi\\Documents\\GitHub\\webapp\\backend\\src\\main\\resources\\thumbnails\\";

    public Mono<Resource> getVideo(String id, String loginCookie) {
        User user = loginService.authorizeCookie(loginCookie);

        List<Video> videos = videoRepository.findByVideoId(id);

        if(!videos.isEmpty()) {
            Video video = videos.getFirst();
            List<WatchedVideo> watchedVideos = new ArrayList<>();
            if(user!=null)
                watchedVideos = watchedVideoRepository.findAllByIdUsername(user.getUsername()).stream().filter((WatchedVideo watchedVideo) -> watchedVideo.getId().getVideoId().equals(id)).toList();

            if(watchedVideos.isEmpty()) {
                video.setViews(video.getViews() + 1);

                if(user!=null)
                    watchedVideoRepository.save(new WatchedVideo(new UserInteractionId(user.getUsername(), video.getVideoId())));

                videoRepository.save(video);
            }
            return Mono.just(new FileSystemResource(FILE_PATH + id + ".mp4"));
        }
        return null;
    }

    public Mono<Resource> getThumb(String id) {

        List<Video> videos = videoRepository.findByVideoId(id);

        if(!videos.isEmpty()) {
            Video video = videos.getFirst();
            List<WatchedVideo> watchedVideos = new ArrayList<>();

            return Mono.just(new FileSystemResource(THUMB_PATH + id + "_thumb.jpg"));
        }
        return null;
    }

    public int likeVideo(String id, String loginCookie)
    {
        User user = loginService.authorizeCookie(loginCookie);

        if(user==null)
            return 1;

        List<LikedVideo> likedVideos = likedVideoRepository.findAllByIdUsername(user.getUsername());
        likedVideos = likedVideos.stream().filter((LikedVideo likedVideo) -> likedVideo.getId().getVideoId().equals(id)).toList();
        if(likedVideos.isEmpty()) {
            Video video = videoRepository.findByVideoId(id).getFirst();
            video.setLikes(video.getLikes() + 1);
            videoRepository.save(video);

            LikedVideo likedVideo = new LikedVideo(new UserInteractionId(user.getUsername(), video.getVideoId()));
            likedVideoRepository.save(likedVideo);
            return 0;
        }
        return 2;
    }

}

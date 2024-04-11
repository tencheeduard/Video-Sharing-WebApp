package com.eax.videoapp.controllers;


import com.eax.videoapp.entities.Video;
import com.eax.videoapp.repositories.VideoRepository;
import com.eax.videoapp.services.StreamingService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
public class StreamingController {

    @Autowired
    StreamingService streamingService;

    @Autowired
    VideoRepository videoRepository;

    @GetMapping(value="/video/{id}", produces = "video/mp4")
    public Mono<Resource> stream(@PathVariable String id)
    {
        try {
            return streamingService.getVideo(id);
        }
        catch (Exception e)
        {
            return Mono.error(e);
        }
    }

    @GetMapping(value="/watch/{id}")
    public ModelAndView watch(@PathVariable String id)
    {
        ModelAndView modelAndView = new ModelAndView("video");
        try {

            Video video = videoRepository.findByVideoId(id).getFirst();

            modelAndView.addObject("video", "/video/" + id);
            modelAndView.addObject("videoId", id);
            modelAndView.addObject("title", video.getTitle());
            modelAndView.addObject("description", video.getDescription());
            modelAndView.addObject("uploader", video.getUser());
            modelAndView.addObject("views", video.getViews());
            modelAndView.addObject("likes", video.getLikes());

            return modelAndView;
        }
        catch (Exception e)
        {
            return modelAndView;
        }
    }

    @PostMapping(value="/like/{id}")
    public void likeVideo(@PathVariable String id, @CookieValue(name="login",defaultValue = "None") String loginCookie, HttpServletResponse response)
    {
        int exitCode = streamingService.likeVideo(id, loginCookie);

        if(exitCode == 1)
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

}

package com.eax.videoapp.controllers;


import com.eax.videoapp.services.StreamingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
public class StreamingController {

    @Autowired
    StreamingService streamingService;

    @GetMapping(value="video/{title}", produces = "video/mp4")
    public Mono<Resource> stream(@PathVariable String title)
    {
        try {
            return streamingService.getVideo(title);
        }
        catch (Exception e)
        {
            return Mono.error(e);
        }
    }

}

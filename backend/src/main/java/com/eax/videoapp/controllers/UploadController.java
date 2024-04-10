package com.eax.videoapp.controllers;

import com.eax.videoapp.entities.Video;
import com.eax.videoapp.repositories.VideoRepository;
import com.eax.videoapp.services.StreamingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import uk.co.caprica.vlcj.player.base.MediaPlayer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.UUID.randomUUID;

@RestController
public class UploadController {

    @Autowired
    VideoRepository videoRepository;

    @GetMapping(value="/upload")
    public ModelAndView upload(@CookieValue(value="login", defaultValue = "NULL") String loginCookie)
    {
        if(loginCookie.equals("NULL"))
            return new ModelAndView("redirect:/login");

        return new ModelAndView("upload.html");
    }


    @PostMapping(value = "/upload/file")
    public String uploadVideo(@CookieValue(value="login", defaultValue = "NULL") String loginCookie, @RequestParam("file")MultipartFile file, String title, String description, String tags) {

        if(loginCookie.equals("NULL"))
             return "You must log in to perform this action";

        if(file.isEmpty())
            return "You must upload a file";

        try {
            String videoId = randomUUID().toString();
            videoId = videoId.replace("-","");

            byte[] bytes = file.getBytes();
            Path path = Paths.get(StreamingService.FILE_PATH + videoId + ".mp4");

            Files.write(path, bytes);

            Video video = new Video(videoId, loginCookie, title, description,tags);
            videoRepository.save(video);
        }
        catch (IOException e)
        {
            return(e.getMessage());
        }

        return "redirect:/login";
    }

}

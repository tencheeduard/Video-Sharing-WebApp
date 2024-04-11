package com.eax.videoapp.controllers;

import com.eax.videoapp.entities.Video;
import com.eax.videoapp.repositories.VideoRepository;
import com.eax.videoapp.services.LoginService;
import com.eax.videoapp.services.StreamingService;
import com.eax.videoapp.services.UploadService;
import io.metaloom.video4j.VideoFile;
import io.metaloom.video4j.Videos;
import net.coobird.thumbnailator.Thumbnails;
import nu.pattern.OpenCV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;

import static java.util.UUID.randomUUID;

@RestController
public class UploadController {


    @Autowired
    UploadService uploadService;

    @Autowired
    LoginService loginService;

    @GetMapping(value="/upload")
    public ModelAndView upload(@CookieValue(value="login", defaultValue = "NULL") String loginCookie)
    {
        if(loginService.authorizeCookie(loginCookie) == null)
            return new ModelAndView("redirect:/login");

        return new ModelAndView("upload");
    }


    @PostMapping(value = "/upload/file")
    public ModelAndView uploadVideo(@CookieValue(value="login", defaultValue = "NULL") String loginCookie,
                              @RequestParam("file")MultipartFile file,
                              String title, String description, String tags, String category,
                              MultipartFile thumbnail) {

        ModelAndView modelAndView = new ModelAndView("upload");

        if(loginService.authorizeCookie(loginCookie) == null)
        {
            modelAndView.setViewName("redirect:/login");
            modelAndView.addObject("error", "You must log in to perform this action.");
            return modelAndView;
        }

        if(file.isEmpty())
        {
            modelAndView.addObject("error", "You must select a file.");
            return modelAndView;
        }

        String videoId;
        try {
            videoId = uploadService.uploadVideo(loginService.authorizeCookie(loginCookie).getUsername(), file, title, description, tags, thumbnail, category);
        }
        catch (IOException e)
        {
            {
                modelAndView.setViewName("redirect:/login");
                modelAndView.addObject("error", e.getMessage());
                return modelAndView;
            }
        }

        modelAndView.setViewName("redirect:/watch/" + videoId);
        return modelAndView;
    }

}

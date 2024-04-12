package com.eax.videoapp.controllers;

import com.eax.videoapp.entities.Video;
import com.eax.videoapp.repositories.VideoRepository;
import com.eax.videoapp.services.LoginService;
import com.eax.videoapp.services.StreamingService;
import com.eax.videoapp.services.UploadService;
import io.metaloom.video4j.VideoFile;
import io.metaloom.video4j.Videos;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public void uploadVideo(@CookieValue(value="login", defaultValue = "NULL") String loginCookie,
                            @RequestParam MultipartFile file,
                            @RequestParam String title, @RequestParam String description, @RequestParam String tags, @RequestParam String category,
                            @RequestParam MultipartFile thumbnail,
                            HttpServletResponse response) {

        if(loginService.authorizeCookie(loginCookie) == null)
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        if(file.isEmpty())
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String videoId;
        try {
            videoId = uploadService.uploadVideo(loginService.authorizeCookie(loginCookie).getUsername(), file, title, description, tags, thumbnail, category);
        }
        catch (IOException e)
        {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        try {
            response.sendRedirect("/watch/" + videoId);
        }
        catch(IOException e)
        {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}

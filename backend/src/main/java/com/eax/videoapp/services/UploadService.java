package com.eax.videoapp.services;

import com.eax.videoapp.entities.Video;
import com.eax.videoapp.repositories.VideoRepository;
import io.metaloom.video4j.VideoFile;
import io.metaloom.video4j.Videos;
import net.coobird.thumbnailator.Thumbnails;
import nu.pattern.OpenCV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;

import static java.util.UUID.randomUUID;

@Service
public class UploadService {

    @Autowired
    VideoRepository videoRepository;

    public String uploadVideo(String loginCookie, MultipartFile file, String title, String description, String tags, MultipartFile thumbnail, String category) throws IOException {
        String videoId = randomUUID().toString();
        videoId = videoId.replace("-","");

        byte[] bytes = file.getBytes();
        Path path = Paths.get(StreamingService.FILE_PATH + videoId + ".mp4");

        Files.write(path, bytes);



        if(thumbnail.isEmpty()) {
            OpenCV.loadLocally();

            VideoFile videoFile = Videos.open(String.valueOf(path));

            BufferedImage image = videoFile.frameToImage();
            image = Thumbnails.of(image).size(1280, 720).asBufferedImage();
            File outputFile = new File(StreamingService.THUMB_PATH + videoId + "_thumb.jpg");
            ImageIO.write(image, "jpg", outputFile);
        }
        else
        {
            byte[] thumbBytes = thumbnail.getBytes();
            Path thumbPath = Paths.get(StreamingService.THUMB_PATH + videoId + "_thumb.jpg");

            Files.write(thumbPath, thumbBytes);
        }

        Video video = new Video(videoId, loginCookie, title, description, tags, new Date(System.currentTimeMillis()), category, 0, 0);
        videoRepository.save(video);

        return videoId;
    }
}

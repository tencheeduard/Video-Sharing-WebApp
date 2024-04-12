package com.eax.videoapp.controllers;

import com.eax.videoapp.entities.Video;
import com.eax.videoapp.services.LoginService;
import com.eax.videoapp.services.VideoRecommendationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VideoRecommendationController {

    @Autowired
    VideoRecommendationService videoRecommendationService;

    @Autowired
    LoginService loginService;

    @GetMapping("/recommend")
    public List<Video> recommend(@CookieValue(name="login",defaultValue = "None") String loginCookie,
                                 HttpServletResponse response,
                                 @RequestParam(name="searchType",defaultValue = "RECOMMENDED") String searchType,
                                 @RequestParam(name="amount",defaultValue = "10") String amount
                                 ){

        if(loginService.authorizeCookie(loginCookie) == null && searchType.equals("RECOMMENDED"))
            searchType = "LIKED";

        List<Video> videos = videoRecommendationService.getVideosBySearchType(loginCookie, VideoRecommendationService.SEARCH_TYPE.valueOf(searchType)).stream().limit(Integer.parseInt(amount)).toList();

        if(videos.isEmpty())
        {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return null;
        }

        return videos;

    }

}

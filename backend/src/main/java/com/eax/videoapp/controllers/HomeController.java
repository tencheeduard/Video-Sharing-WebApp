package com.eax.videoapp.controllers;

import com.eax.videoapp.entities.Video;
import com.eax.videoapp.services.LoginService;
import com.eax.videoapp.services.VideoRecommendationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HomeController {

    @Autowired
    LoginService loginService;
    @Autowired
    VideoRecommendationService videoRecommendationService;



    @GetMapping(value="/")
    public ModelAndView home(@CookieValue(name="login",defaultValue = "None") String loginCookie, @CookieValue(name="searchType",defaultValue = "RECENT") String searchType)
    {
        String username = "Anonymous";
        if(!loginCookie.equals("None"))
            username = loginService.authorizeCookie(loginCookie).getUsername();

        List<Video> videos = videoRecommendationService.getVideosBySearchType(VideoRecommendationService.SEARCH_TYPE.valueOf(searchType));
        ModelAndView modelAndView = new ModelAndView("home");

        List<String> titles = videos.stream().map(Video::getTitle).toList();
        List<String> ids = videos.stream().map(Video::getVideoId).toList();

        modelAndView.addObject("titles", titles);
        modelAndView.addObject("ids", ids);
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    @PostMapping(value="/setFilterType/{searchType}")
    public void setSearchType(@PathVariable String searchType, HttpServletResponse response)
    {
        videoRecommendationService.setRecommendedCookie(response, VideoRecommendationService.SEARCH_TYPE.valueOf(searchType));
    }


}

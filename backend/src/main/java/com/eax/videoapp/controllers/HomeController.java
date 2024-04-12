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
        return new ModelAndView("home");
    }

    @PostMapping(value="/setFilterType/{searchType}")
    public void setSearchType(@PathVariable String searchType, HttpServletResponse response)
    {
        videoRecommendationService.setRecommendedCookie(response, VideoRecommendationService.SEARCH_TYPE.valueOf(searchType));
    }


}

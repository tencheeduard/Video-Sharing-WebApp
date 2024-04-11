package com.eax.videoapp.services;

import com.eax.videoapp.entities.LoginCookie;
import com.eax.videoapp.entities.Video;
import com.eax.videoapp.repositories.VideoRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoRecommendationService {

    public enum SEARCH_TYPE
    {
        RECENT,
        RECOMMENDED,
        POPULAR
    }

    @Autowired
    VideoRepository videoRepository;

    public List<Video> getMostRecentVideos()
    {
        return videoRepository.findFirst20OrderByDateDesc();
    }

    public List<Video> getVideosBySearchType(SEARCH_TYPE searchType)
    {
        switch(searchType)
        {
            case RECENT:
                return videoRepository.findFirst20OrderByDateDesc();
            case RECOMMENDED:
                break;
            case POPULAR:
                return videoRepository.findFirst20OrderByLikesDesc();
        }
        return null;
    }


    public Cookie setRecommendedCookie(HttpServletResponse response, SEARCH_TYPE searchType)
    {
        Cookie searchTypeCookie = new Cookie("searchType", searchType.name());
        searchTypeCookie.setPath("/");
        response.addCookie(searchTypeCookie);

        return searchTypeCookie;
    }

}

package com.eax.videoapp.services;

import com.eax.videoapp.entities.Video;
import com.eax.videoapp.repositories.VideoRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoRecommendationService {

    public enum SEARCH_TYPE
    {
        RECENT,
        RECOMMENDED,
        VIEWED,
        LIKED
    }

    @Autowired
    VideoRepository videoRepository;

    public List<Video> getMostRecentVideos()
    {
        return videoRepository.findTop50ByOrderByDateDesc();
    }

    public List<Video> getVideosBySearchType(String cookie, SEARCH_TYPE searchType)
    {
        switch(searchType)
        {
            case RECENT:
                return videoRepository.findTop50ByOrderByDateDesc();
            case RECOMMENDED:
                return videoRepository.findTop50ByOrderByDateDesc();
            case VIEWED:
                return videoRepository.findTop50ByOrderByViewsDesc();
            case LIKED:
                return videoRepository.findTop50ByOrderByLikesDesc();
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

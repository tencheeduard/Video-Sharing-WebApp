package com.eax.videoapp.controllers;

import com.eax.videoapp.entities.User;
import com.eax.videoapp.repositories.LoginCookieRepository;
import com.eax.videoapp.repositories.UserRepository;
import com.eax.videoapp.services.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
public class LoginController {


    @Autowired
    LoginService loginService;
    @Autowired
    private LoginCookieRepository loginCookieRepository;

    @PostMapping(value="/login/request")
    public void requestLogin(@RequestParam(name="username") String username,
                              @RequestParam(name="password") String password,
                              HttpServletResponse response) {

        int exitCode = loginService.authorizeLogin(username,password);


        switch(exitCode)
        {
            case 0:
                loginService.createLoginCookie(username, response);
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            case 1:
                response.setStatus(600);
                return;
            case 2:
                response.setStatus(700);
                return;
        }

    }

    @PostMapping(value="/login/signup")
    public void requestLogin(@RequestParam(name="username") String username,
                             @RequestParam(name="email") String email,
                             @RequestParam(name="password") String password,
                             HttpServletResponse response) {

        User user = loginService.registerUser(username, password, email);
        if(user != null) {
            requestLogin(username, password, response);
            return;
        }

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

    }

    @GetMapping(value="/login")
    public ModelAndView login()
    {
        return new ModelAndView("login");
    }

    @GetMapping(value="/getUserData")
    public Map<String, Object> getUserData(@CookieValue(name="login",defaultValue = "None") String loginCookie, HttpServletResponse response)
    {

        String username = loginService.authorizeCookie(loginCookie).getUsername();

        Map<String, Object> userData = loginService.getPublicUserData(username);
        if(userData == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        return userData;
    }

}

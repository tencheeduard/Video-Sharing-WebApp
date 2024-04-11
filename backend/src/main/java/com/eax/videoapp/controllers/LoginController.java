package com.eax.videoapp.controllers;

import com.eax.videoapp.entities.User;
import com.eax.videoapp.repositories.UserRepository;
import com.eax.videoapp.services.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class LoginController {


    @Autowired
    LoginService loginService;

    @PostMapping(value="/login/send")
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

    @GetMapping(value="/login")
    public ModelAndView login(@RequestParam(name="redirect") String redirect)
    {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("redirect", redirect);
        return modelAndView;
    }

}

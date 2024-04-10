package com.eax.videoapp.controllers;

import com.eax.videoapp.entities.User;
import com.eax.videoapp.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @PostMapping(value="/login/send")
    public String login(@RequestParam(name="username") String username, @RequestParam(name="password") String password, HttpServletResponse response) {
        List<User> users = userRepository.findByUsername(username);
        if(!users.isEmpty())
        {
            if(password.equals(users.getFirst().getPassword()))
            {
                Cookie loginCookie = new Cookie("login", username);
                loginCookie.setMaxAge(3600);
                loginCookie.setPath("/");
                response.addCookie(loginCookie);
                return "Logged in Successfully!";
            }
            return "Password is incorrect";
        }

        return "Username does not exist";
    }

    @GetMapping(value="/login")
    public ModelAndView loginGet()
    {
        return new ModelAndView("login.html");
    }

}

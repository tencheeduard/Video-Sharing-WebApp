package com.eax.videoapp.services;

import com.eax.videoapp.entities.LoginCookie;
import com.eax.videoapp.entities.User;
import com.eax.videoapp.repositories.LoginCookieRepository;
import com.eax.videoapp.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.UUID.randomUUID;

@Service
public class LoginService {

    @Autowired
    LoginCookieRepository loginCookieRepository;

    @Autowired
    UserRepository userRepository;

    public User authorizeCookie(String cookie)
    {
        List<LoginCookie> cookies = loginCookieRepository.findByUUID(cookie);
        if(!cookies.isEmpty())
            return userRepository.findByUsername(cookies.getFirst().getUser()).getFirst();

        return null;
    }

    public int authorizeLogin(String username, String password)
    {
        List<User> users = userRepository.findByUsername(username);
        if(users.isEmpty())
        {
            users = userRepository.findByEmail(username);
            if(users.isEmpty())
                return 1;
        }
        if (password.equals(users.getFirst().getPassword()))
            return 0;
        else
            return 2;
    }

    public User registerUser(String username, String password, String email)
    {
        if(!username.isEmpty() &&
            !password.isEmpty() &&
            !email.isEmpty() &&
            userRepository.findByUsername(username).isEmpty() &&
            userRepository.findByEmail(email).isEmpty())
        {
            User user = new User(username,password,email, new Date(System.currentTimeMillis()));
            userRepository.save(user);
            return user;
        }

        return null;
    }

    public Cookie createLoginCookie(String username, HttpServletResponse response)
    {
        String uuid = randomUUID().toString();
        uuid = uuid.replace("-","");

        List<User> users = userRepository.findByEmail(username);
        if(!users.isEmpty())
            username = users.getFirst().getUsername();

        Cookie loginCookie = new Cookie("login", uuid);
        loginCookie.setMaxAge(3600);
        loginCookie.setPath("/");
        response.addCookie(loginCookie);

        loginCookieRepository.save(new LoginCookie(uuid, username));
        return loginCookie;
    }

    public Map<String, Object> getPublicUserData(String username)
    {
        List<User> users = userRepository.findByUsername(username);
        if(!users.isEmpty())
        {
            User user = users.getFirst();
            Map<String, Object> userData = new HashMap<>();
            userData.put("username", user.getUsername());
            userData.put("publicName", user.getPublicName());
            userData.put("joinDate", user.getJoinDate());
            userData.put("subscribers", user.getSubscribers());

            return userData;
        }

        return null;
    }

}

package com.eax.videoapp.repositories;

import com.eax.videoapp.entities.LoginCookie;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoginCookieRepository extends CrudRepository<LoginCookie, String> {

    public List<LoginCookie> findByUUID(String UUID);

}
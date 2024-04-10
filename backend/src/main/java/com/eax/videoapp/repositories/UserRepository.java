package com.eax.videoapp.repositories;

import com.eax.videoapp.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User,String> {

    List<User> findByUsername(String username);
    List<User> findByEmail(String email);

}
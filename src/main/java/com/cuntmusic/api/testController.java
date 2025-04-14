package com.cuntmusic.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;

import com.cuntmusic.utils.Repos.*;
import com.cuntmusic.utils.tables.*;

@RestController
public class testController {

    @Autowired
    private UsersRepo userRepo;

    @GetMapping("/getUsers")
    public Iterable<Users> findAllUsers() {
      return this.userRepo.findAll();
    } 

    @PostMapping("/postUsers")
    public Users postUsers(@RequestBody Users user) {
        return this.userRepo.save(user);
    }
}

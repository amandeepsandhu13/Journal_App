package org.aman.journalapp.controller;

import org.aman.journalapp.entity.User;
import org.aman.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public User createUser(@RequestBody User user){
        return  userService.createUser(user);
    }

}

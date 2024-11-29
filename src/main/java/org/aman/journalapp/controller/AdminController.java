package org.aman.journalapp.controller;

import org.aman.journalapp.entity.User;
import org.aman.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("all-users")
    public ResponseEntity<?> getAllusers(){
        List<User> allUsers = userService.getAllUsers();
        if (!allUsers.isEmpty()) {
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        } return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping
    public User createNewAdmin(@RequestBody User user){
        User admin = userService.createAdminUser(user);
        return user;
    }

}

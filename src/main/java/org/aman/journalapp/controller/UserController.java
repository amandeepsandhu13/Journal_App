package org.aman.journalapp.controller;

import org.aman.journalapp.entity.User;
import org.aman.journalapp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
         if(userService.getAllUsers().size() > 0){
             return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
         } return  new ResponseEntity<>(userService.getAllUsers(),HttpStatus.NO_CONTENT);
    }

    @GetMapping("/id/{id}")
    public User getUserByID(@PathVariable ObjectId id){
        return userService.getUserById(id).orElse(null);
    }

    @GetMapping("/getUser")
    public ResponseEntity<User> getUserByUsername(@RequestParam String username){
        return new ResponseEntity<>(userService.findUserByUsername(username), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestParam String userName, @RequestBody User newUser){
           User userDb = userService.findUserByUsername(userName);
           if(userDb == null){
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
           }
               userDb.setUserName(newUser.getUserName());
               userDb.setPassword(newUser.getPassword());
               userService.createUser(userDb);
               return new ResponseEntity<>("user updated successfully",HttpStatus.ACCEPTED);

    }

}

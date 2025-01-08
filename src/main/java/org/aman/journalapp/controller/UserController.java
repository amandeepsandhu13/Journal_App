package org.aman.journalapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.aman.journalapp.Repository.UserRepo;
import org.aman.journalapp.customException.UserNotFoundException;
import org.aman.journalapp.entity.User;
import org.aman.journalapp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name="User APIs", description = "Read, Update, Delete User")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepository;

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
    public ResponseEntity<User> getUserByUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found") );
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User newUser){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userDb = userService.findUserByUsername(userName).orElseThrow(()->new RuntimeException("User Not Found"));
        // Update the user details
        if (!newUser.getUserName().isEmpty()) {
            userDb.setUserName(newUser.getUserName());
        }

        if (!newUser.getPassword().isEmpty()) {
            userDb.setPassword(newUser.getPassword());
        }
            userService.createUser(userDb);
            return new ResponseEntity<>("user updated successfully",HttpStatus.ACCEPTED);

    }

    @DeleteMapping
    public  ResponseEntity deleteUserByID(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userRepository.deleteByUserName(username);
        return new ResponseEntity<>("user deleted successfully",HttpStatus.ACCEPTED);
    }


}

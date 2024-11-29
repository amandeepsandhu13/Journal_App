package org.aman.journalapp.service;

import org.aman.journalapp.Repository.UserRepo;
import org.aman.journalapp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User saveUser(User user){
        return userRepo.save(user);
    }

    public User createUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("User"));
        return userRepo.save(user);
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }
    public Optional<User> getUserById(ObjectId id){
        if(userRepo.findById(id).isPresent()){
            return userRepo.findById(id);
        }else{
            return Optional.empty();
        }
    }

    public void deleteUser(ObjectId id){
        userRepo.deleteById(id);
    }

    public User findUserByUsername(String username){
        return userRepo.findByUserName(username);
    }


    public User createAdminUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("User", "ADMIN"));
        return userRepo.save(user);
    }
}

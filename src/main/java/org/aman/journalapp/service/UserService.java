package org.aman.journalapp.service;

import org.aman.journalapp.Repository.UserRepo;
import org.aman.journalapp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User createUser(User user){
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


}

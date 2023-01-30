package com.company.social.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.social.entities.UserEntity;
import com.company.social.entities.Role;
import com.company.social.repository.RoleRepo;
import com.company.social.repository.UserRepo;
import java.util.*;

@Service
public class UserService {

    @Autowired
    public UserRepo  userRepo;

    @Autowired
    public RoleRepo roleRepo;

    public UserEntity updateRole(String username , UserEntity user){
        try{
            return userRepo.save(user);
        }catch(Exception e){}
        return null;
    }

    public List<UserEntity> showAllUser(){
        try{
            List<UserEntity> users = new ArrayList<>();
            for(UserEntity user : userRepo.findAllByOrderByIdDesc()){
                users.add(user);
            }
            return users;
        }catch(Exception e){}
        return null;
    }

    public UserEntity getUserByUsername(String username){
        return userRepo.findByUsername(username).get();
    }

    public Boolean deleteUser(Long id){
        userRepo.delete(userRepo.findById(id).orElseThrow());
        return userRepo.findById(id).isEmpty();
    }

    public Boolean addRole(String username , String nameRole){
        UserEntity user = userRepo.findByUsername(username).get();
        Role role = roleRepo.findByName(nameRole);
       return  user.getRoles().add(role);
    }
     
    
}

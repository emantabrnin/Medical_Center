package com.company.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.social.entities.UserEntity;
import com.company.social.repository.UserRepo;
import com.company.social.service.UserService;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public UserRepo userRepo;

    @GetMapping(path = "/showAllUser")
    public Object showAllUser(){
        try{
         return userService.showAllUser();
            
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping(path = "/updateRole")
    public Object updateRole(@RequestParam String username , @RequestBody UserEntity user){
       try{
        UserEntity userRole = userRepo.findByUsername(username).get();
        userRole.setRoles(user.getRoles());
        userService.updateRole(username, userRole);
        return userRole;
    }catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}

@DeleteMapping(path = "/deleteUser")
public Boolean deleteUser(@RequestParam long id){
    return userService.deleteUser(id);
}

    
}

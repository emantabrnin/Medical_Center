package com.company.social.controller;

// import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.social.entities.ERole;
import com.company.social.entities.Role;
import com.company.social.entities.UserEntity;
import com.company.social.payload.request.LoginRequest;
import com.company.social.payload.request.SignupRequest;
import com.company.social.payload.request.VerificationRequest;
import com.company.social.payload.response.FullResponse;
import com.company.social.payload.response.MessageResponse;
import com.company.social.repository.RoleRepo;
import com.company.social.repository.UserRepo;
import com.company.social.security.UserDetailsImp;
import com.company.social.security.Jwt.JwtUtils;

import java.util.Random;
import java.util.*;

import java.util.stream.Collectors;


@RestController
// @RequestMapping(path = "/user")
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    public UserRepo userRepo;

    @Autowired
    public RoleRepo roleRepo;
    @Autowired
    PasswordEncoder encoder;
    @Autowired(required = true)
    public AuthenticationManager authenticationManager;

  @Autowired
  JwtUtils jwtUtils;
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser( @RequestBody SignupRequest signUpRequest) {
      if (userRepo.existsByUsername(signUpRequest.getUsername())) {
        return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
      }

      //create account
      UserEntity user = new UserEntity(signUpRequest.getUsername(),encoder.encode( signUpRequest.getPassword()), signUpRequest.getStatus());
      user.setFirstname(signUpRequest.getFirstName());
      user.setLastname(signUpRequest.getLastName());
      user.setMobile(signUpRequest.getMobile());
      user.setGender(signUpRequest.getGender());
      user.setJob(signUpRequest.getJob());
      user.setBirthday(signUpRequest.getBirthday());
     
      user.setSocialState(signUpRequest.getSocialStatus());
      user.setStatus(false);
      user.setIsActive(false);
      String zipCode = genint(user);
      user.setZipCode(zipCode);
    //   Set<String> strRoles = signUpRequest.getRole();
    //   Set<Role> roles = new HashSet<>();
      
    // if (strRoles == null) {
    //     Role userRole = roleRepo.findByName(ERole.ROLE_USER)
    //         .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    //     roles.add(userRole);
    //   } else {
    //     strRoles.forEach(role -> {
    //       switch (role) {
    //         case "admin":
    //           Role adminRole = roleRepo.findByName(ERole.ROLE_ADMIN)
    //               .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    //           roles.add(adminRole);
  
    //           break;
    //         case "mod":
    //           Role modRole = roleRepo.findByName(ERole.ROLE_DOCTOR)
    //               .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    //           roles.add(modRole);
  
    //           break;
    //         default:
    //           Role userRole = roleRepo.findByName(ERole.ROLE_USER)
    //               .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    //           roles.add(userRole);
    //       }
    //     });
    //   }
  
    //   user.setRoles(roles);
      userRepo.save(user);
  
      return ResponseEntity
          .ok(new MessageResponse("Please enter a code number to evaluate the completion of the registration process "));
    }

    @PostMapping("/signin")
    public Object authenticateUser( @RequestBody LoginRequest loginRequest){
     try{
      Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        
    SecurityContextHolder.getContext().setAuthentication(authentication);;
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());
        UserEntity userEntity = new UserEntity();
        userEntity = userRepo.findByUsername(userDetails.getUsername()).get();
        if (userEntity.getIsActive() == false)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: You have not completed the registration process. "); 
        FullResponse fullResponse = new FullResponse();
        fullResponse.setId(userDetails.getId());
        fullResponse.setUsername(userDetails.getUsername());
        fullResponse.setToken(jwt);
        fullResponse.setRoles(roles);
        fullResponse.setFirstName(userEntity.getFirstname());
        fullResponse.setLastName(userEntity.getLastname());
        fullResponse.setPassword(userEntity.getPassword());
        fullResponse.setBirthday(userEntity.getBirthday());
        fullResponse.setGender(userEntity.getGender());
        fullResponse.setSocialStatus(userEntity.getSocialState());
        fullResponse.setMobile(userEntity.getMobile());
        fullResponse.setIsActive(userEntity.getIsActive());
        fullResponse.setStatus(userEntity.getStatus());
        fullResponse.setZipCode(userEntity.getZipCode());
        fullResponse.setJob(userEntity.getJob());
        return fullResponse;
     }
       catch(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Username or Password is not true");
      }
    }
    
  @PostMapping(path = "/verification")
  public Object verification(@RequestBody VerificationRequest verificationRequest) {
    Optional<UserEntity> user = userRepo.findByUsername(verificationRequest.getUsername());
    
    try {
      if (user == null) {
        return ResponseEntity.badRequest().body(new MessageResponse("Error: Username not Found"));
      }
      if (verificationRequest.getZipCode().equals(user.get().getZipCode()) == false) {
        return ResponseEntity.badRequest().body(new MessageResponse("Error: Code is not true"));
      }
      boolean isMatch = encoder.matches(verificationRequest.getPassword(), user.get().getPassword());
      if(isMatch == false){
        return ResponseEntity.badRequest().body(new MessageResponse("Error: Password not match"));
      }
      user.get().setStatus(true);
      user.get().setIsActive(true);
      userRepo.save(user.get());
      Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.get().getUsername(), verificationRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());
    UserEntity userEntity = new UserEntity();
    userEntity = userRepo.findByUsername(userDetails.getUsername()).get();

    FullResponse fullResponse = new FullResponse();
    fullResponse.setId(userDetails.getId());
    fullResponse.setUsername(userDetails.getUsername());
    fullResponse.setToken(jwt);
    fullResponse.setRoles(roles);
    fullResponse.setFirstName(userEntity.getFirstname());
    fullResponse.setLastName(userEntity.getLastname());
    fullResponse.setPassword(userEntity.getPassword());
    fullResponse.setBirthday(userEntity.getBirthday());
    fullResponse.setGender(userEntity.getGender());
    fullResponse.setSocialStatus(userEntity.getSocialState());
    fullResponse.setMobile(userEntity.getMobile());
    fullResponse.setIsActive(userEntity.getIsActive());
    // fullResponse.setScore(userEntity.getsc());
    fullResponse.setStatus(userEntity.getStatus());
    fullResponse.setZipCode(userEntity.getZipCode());
    fullResponse.setJob(userEntity.getJob());
    return fullResponse;
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
    @RequestMapping("/testInteger")
    public String genint(UserEntity user) {
  
      int intRange;
      try {
  
        for (int i = 0; i < 10; i++) {
          intRange = generateRandomIntIntRange(1000, 10000);
          String num = Integer.toString(intRange);
          user.setZipCode(num);
          userRepo.save(user);
        }
      } catch (Exception e) {
  
        e.printStackTrace();
      }
  
      return user.getZipCode();
  
    }
  
    public static int generateRandomIntIntRange(int min, int max) {
      Random r = new Random();
      return r.nextInt((max - min) + 1) + min;
  
    }
}

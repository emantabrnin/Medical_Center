package com.company.social.entities;

import javax.persistence.Entity;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.*;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String username;
    private String mobile;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date  birthday;
    private String gender;
    private String password;
    private String job;
    private String socialState;
    private Boolean status;
    private Boolean isActive;
    private String zipCode;
    @JsonIgnoreProperties(value = {"user"},allowSetters = true)
    @OneToMany(mappedBy = "user")
    private List<AppointmentEntity> appointmens;
   
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public UserEntity(String username, String password, Boolean status) {
        this.username = username;
        this.password = password;
        this.status = status;

    }
   
}

package com.company.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.social.entities.Role;
import java.util.Optional;
import com.company.social.entities.ERole;;

public interface RoleRepo extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(ERole name);

    Role findByName(String roleName);
}
    


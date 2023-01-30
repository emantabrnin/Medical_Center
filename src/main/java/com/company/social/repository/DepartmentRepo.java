package com.company.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.social.entities.DepartmentEntity;
@Repository
public interface DepartmentRepo extends JpaRepository<DepartmentEntity,Long> {
    
}

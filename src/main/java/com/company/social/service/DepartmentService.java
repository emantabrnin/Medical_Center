package com.company.social.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.social.entities.DepartmentEntity;
import com.company.social.repository.DepartmentRepo;

@Service
public class DepartmentService {

    @Autowired
    public DepartmentRepo departmentRepo;

    public DepartmentEntity addDepartment(DepartmentEntity department){
        return departmentRepo.save(department);
    }

    public List<DepartmentEntity> getAllDepatment(){
        return departmentRepo.findAll();
    }
    public Boolean deleteDepartment(long id){
        departmentRepo.delete(departmentRepo.findById(id).orElseThrow());
        return departmentRepo.findById(id).isEmpty();
    }
    
}

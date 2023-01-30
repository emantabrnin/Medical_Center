package com.company.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.social.entities.DepartmentEntity;
import com.company.social.service.DepartmentService;

@ReadingConverter
@RequestMapping(path = "/api/v1/department")
public class DepatmentController {

    @Autowired
    public DepartmentService departmentService;

    @PostMapping(path = "/add")
    public Object addDepartment(@RequestBody DepartmentEntity department){
        try{
            return departmentService.addDepartment(department);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(path = "/getAll")
    public Object getAllDepatment(){
        try{
            return departmentService.getAllDepatment();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/delete")
    public Object deleteDepartment(@RequestParam Long id){
        try{
            return departmentService.deleteDepartment(id);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    
}

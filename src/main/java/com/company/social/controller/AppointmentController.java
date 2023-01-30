package com.company.social.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.social.entities.AppointmentEntity;
import com.company.social.service.AppointmentService;

@RestController
@RequestMapping(path = "/api/v1/appointment")
public class AppointmentController {

    @Autowired
    public AppointmentService appointmentService;
     //mobile
    @PostMapping(path = "/add")
    public Object createAppointment(@RequestBody AppointmentEntity appointment){
        try{
            return appointmentService.createAppintment(appointment);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //web
    @GetMapping(path = "/details")
    public Object getAppointment(@RequestParam Long id){
        try{
            return appointmentService.getAppointment(id);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //web
    @GetMapping(path = "/nowTime")
    public Object showAppointmentOfNowDay(){
        try{
            return appointmentService.getAppointmentOfNowDay();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping(path = "/AvailableTimes")
    public Object AvailableTimes(@RequestParam(name = "appointmentDate")@DateTimeFormat(iso =DateTimeFormat.ISO.DATE) LocalDate appointmentDate){
        try {
            return appointmentService.AvailableTimes(appointmentDate);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //web
    @GetMapping(path = "/Time")
    public Object showAppointmentOfDay(@RequestParam LocalDate localDate){
        try{
            return appointmentService.showAppointmentOfDay(localDate);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    // mobile
    @GetMapping(path = "/ShowAppointmentofUser")
    public Object ShowAppointmentofUser(@RequestParam(name = "id") Long id){
        try {
            return appointmentService.showAppointmentOfUser(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    // mobile and dashboard
    @DeleteMapping(path = "/cancelAppointment")
    public boolean cancelAppointment(@RequestParam(name = "id") Long id)    
    {
        return appointmentService.deleteAppointment(id);
    }

    
}

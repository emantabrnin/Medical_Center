package com.company.social.service;

import java.lang.StackWalker.Option;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.social.entities.AppointmentEntity;
import com.company.social.entities.UserEntity;
import com.company.social.repository.AppointmentRepo;
import com.company.social.repository.UserRepo;

@Service
public class AppointmentService {

    @Autowired
    public AppointmentRepo appointmentRepo;

    @Autowired
    public UserRepo userRepo;

    public AppointmentEntity createAppintment(AppointmentEntity appointment) throws Exception{
      UserEntity user = userRepo.findByUsername(appointment.getUser().getUsername()).get();
      userRepo.save(user);
      appointment.setUser(user);
      return appointmentRepo.save(appointment);
    }
    public List<Time> AvailableTimes(LocalDate appointmentDate) {

        List<Time> intervals = new ArrayList<>(25);
        Time startTime = Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("19:30:00");
        intervals.add(startTime);

        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        while (cal.getTime().before(endTime)) {
            cal.add(Calendar.MINUTE, 30);
            intervals.add(new Time(cal.getTimeInMillis()));
        }
        List<AppointmentEntity> appointmentinDay = appointmentRepo
                .getAppointmentEntityByAppointmentDateOrderByAppointmentTime(appointmentDate);

        List<Time> booked = new ArrayList<>(25);
        for (int i = 0; i < appointmentinDay.size(); i++) {
            booked.add(appointmentinDay.get(i).getAppointmentTime());
        }

        List<Time> avaliables = new ArrayList<>(25);

        for(int j=0; j<intervals.size(); j++){
          if(booked.contains(intervals.get(j)) != true)
          avaliables.add(intervals.get(j));
        }

        return avaliables;
    }

    public Optional<AppointmentEntity>  getAppointment(long id){
        return appointmentRepo.findById(id);
    }

    public List<AppointmentEntity> getAppointmentOfNowDay(){
        LocalDate localDate = LocalDate.now();
        List<AppointmentEntity> appointment = appointmentRepo.findByAppointmentTimeOrderByAppointmentTimeDesc(localDate);
        return appointment;
       
    }

    public List<AppointmentEntity> showAppointmentOfDay(LocalDate localDate){
        List<AppointmentEntity> appointment = appointmentRepo.findByAppointmentTimeOrderByAppointmentTimeDesc(localDate);
        return appointment;
    }
    public List<AppointmentEntity>  showAppointmentOfUser(long id){
        Optional<UserEntity> user = userRepo.findById(id);
        List<AppointmentEntity> appointment = user.get().getAppointmens();
        return  appointment;
    }

    public Boolean deleteAppointment(long id){
        appointmentRepo.delete(appointmentRepo.findById(id).orElseThrow());
        return appointmentRepo.findById(id).isEmpty();
    }
    
    
}

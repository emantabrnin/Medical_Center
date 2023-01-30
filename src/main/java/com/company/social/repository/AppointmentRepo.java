package com.company.social.repository;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.company.social.entities.AppointmentEntity;
@Repository
public interface AppointmentRepo extends JpaRepository<AppointmentEntity,Long> {
    // @Query(value = "SELECT * FROM AppointmentEntity where appointmentDate = ?1 ")
    // List<AppointmentEntity> getAppointmentEntityByAppointmentDateOrderByAppointmentTime(LocalDate appointmentDate);
   //test
    @Query(value = "SELECT * FROM AppointmentEntity where appointmentDate = ?1 ")
    List<AppointmentEntity> getAppointmentEntityByAppointmentDateOrderByAppointmentTime(LocalDate appointmentDate);
    public List<AppointmentEntity>  findByAppointmentTimeOrderByAppointmentTimeDesc(LocalDate appointmentTime);
}

package com.company.social.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;

import java.util.*;

@Entity
@Table(name = "appointment")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate appointmentDate;
    private Time appointmentTime;
    private String note;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private UserEntity user;
    @ManyToOne(targetEntity = DepartmentEntity.class)
    private DepartmentEntity deparetment;

}

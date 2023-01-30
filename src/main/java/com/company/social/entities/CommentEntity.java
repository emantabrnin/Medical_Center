package com.company.social.entities;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     private String title;
     @ManyToOne
     @JoinColumn(name = "post_id")
     private PostEntity post;
     @ManyToOne
     @JoinColumn(name = "user_id")
     private UserEntity users ;
}

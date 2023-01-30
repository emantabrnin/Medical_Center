package com.company.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

import com.company.social.entities.QuestionEntity;
@Repository
public interface QuestionRepo extends JpaRepository<QuestionEntity,Long> {

    public List<QuestionEntity> findAllByOrderByIdDesc();

    public List<QuestionEntity> findByQuestion(String question);
    public List<QuestionEntity>  findByAnswer(String answer);
    
}

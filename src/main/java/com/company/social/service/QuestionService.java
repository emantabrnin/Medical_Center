package com.company.social.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.social.entities.QuestionEntity;
import com.company.social.entities.UserEntity;
import com.company.social.repository.QuestionRepo;
import com.company.social.repository.UserRepo;

import java.util.*;

@Service
public class QuestionService {

    @Autowired
    public QuestionRepo questionRepo;

    @Autowired
    public UserRepo userRepo;

    public List<QuestionEntity> showAllQuestionAndAnswer(){
       List<QuestionEntity> questions  = new ArrayList<>();
       for(QuestionEntity question : questionRepo.findAllByOrderByIdDesc()){
        if(question.getAnswer() !=null)
        questions.add(question);
       }
       return questions;
    }
    public List<QuestionEntity> showAllQuestion(){
        List<QuestionEntity> questions  = new ArrayList<>();
        for(QuestionEntity question : questionRepo.findAllByOrderByIdDesc()){
         if(question.getAnswer() ==null)
         questions.add(question);
        }
        return questions;
     }

     public QuestionEntity getQuestionById(long id){
        return questionRepo.findById(id).get();
     }

     public QuestionEntity addQuestion(QuestionEntity question){
        return questionRepo.save(question);
     }

     public QuestionEntity addAnswer(long id , QuestionEntity question){
        try{
            return questionRepo.save(question);
        }catch(Exception e){}
        return null;
     }

     public Boolean deleteQuestion(long id){
        questionRepo.delete(questionRepo.findById(id).orElseThrow());
        return questionRepo.findById(id).isEmpty();
     }

     public UserEntity getUser(String username){
        return userRepo.findByUsername(username).get();
     }
    
}

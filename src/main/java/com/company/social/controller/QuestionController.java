package com.company.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.social.entities.QuestionEntity;
import com.company.social.payload.request.TextRequest;
import com.company.social.repository.QuestionRepo;
import com.company.social.service.QuestionService;

import java.util.*;

@RestController
@RequestMapping(path = "/question")
public class QuestionController {

    @Autowired
    public QuestionRepo questionRepo;

    @Autowired
    public QuestionService questionService;


    //mobile
    @GetMapping(path = "/search")
    public List<QuestionEntity>  searchQuestion(@RequestParam TextRequest textRequest){
        List<QuestionEntity> question = questionRepo.findByQuestion(textRequest.getText());
        List<QuestionEntity>  answer = questionRepo.findByAnswer(textRequest.getText());
        List<QuestionEntity> collection = new ArrayList<>();
        collection.addAll(question);
        collection.addAll(answer);
        return collection;
    }

    //web
    @PostMapping(path = "/addAnswer")
    public Object addAnswer(@RequestBody QuestionEntity questionEntity,@RequestParam Long id){
        try{
            QuestionEntity question = questionService.getQuestionById(id);
            question.setAnswer(questionEntity.getAnswer());
            question.setUser(questionService.getUser(questionEntity.getUser().getUsername()));
            questionService.addAnswer(id, question);
            return question;
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //mobile
    @GetMapping(path = "/showAllQuestionAndAnswer")
    public Object showAllQuestionAndAnswer(){
        try{
            return questionService.showAllQuestionAndAnswer();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //web
    @GetMapping(path = "/showAllQuestion")
    public Object showAllQuestion(){
        try{
            return questionService.showAllQuestion();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //mobile
    @GetMapping(path = "/details")
    public Object detailsQuestion(@RequestParam Long id){
        try{
            return questionService.getQuestionById(id);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //mobile
    @PostMapping(path = "add")
    public Object addQuestion(@RequestBody QuestionEntity question){
        try{
            return questionRepo.save(question);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //web
    @DeleteMapping(path = "/delete")
    public Object deleteQuestion(@RequestParam long id){
        try{
            return questionService.deleteQuestion(id);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    
}

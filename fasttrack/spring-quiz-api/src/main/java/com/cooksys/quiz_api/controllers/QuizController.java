package com.cooksys.quiz_api.controllers;

import java.util.List;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.services.QuizService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

  private final QuizService quizService;

  @GetMapping
  public List<QuizResponseDto> getAllQuizzes() {
    return quizService.getAllQuizzes();
  }

  @PostMapping
  public ResponseEntity<QuizResponseDto> createQuiz(@RequestBody QuizRequestDto quizRequestDto){ return quizService.createQuiz(quizRequestDto);}

  @DeleteMapping("/{name}")
  public ResponseEntity<QuizResponseDto> deleteQuiz(@PathVariable String name){return quizService.deleteQuiz(name);}

  @PatchMapping("/{name}/rename/{newName}")
  public ResponseEntity<QuizResponseDto> updateQuizName(@PathVariable String name, @PathVariable String newName){return quizService.updateQuizName(name, newName);}

  @GetMapping("/{name}/random")
  public ResponseEntity<QuestionResponseDto> getRandomQuestion(@PathVariable String name){return quizService.getRandomQuestion(name);}

  @PatchMapping("/{name}/add")
  public ResponseEntity<QuizResponseDto> addQuestionToQuiz(@PathVariable String name, @RequestBody QuestionRequestDto questionRequestDto){return quizService.addQuestionToQuiz(name, questionRequestDto);}

  @DeleteMapping("/{name}/delete/{questionID}")
  public ResponseEntity<QuestionResponseDto> deleteQuestionFromQuiz(@PathVariable String name, @PathVariable long questionID){return quizService.deleteQuestionFromQuiz(name, questionID);}
}

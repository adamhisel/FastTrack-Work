package com.cooksys.quiz_api.services;

import java.util.List;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import org.springframework.http.ResponseEntity;

public interface QuizService {

  List<QuizResponseDto> getAllQuizzes();

  ResponseEntity<QuizResponseDto> createQuiz(QuizRequestDto quizRequestDto);

  ResponseEntity<QuizResponseDto> deleteQuiz(String name);

  ResponseEntity<QuizResponseDto> updateQuizName(String name, String newName);

  ResponseEntity<QuestionResponseDto> getRandomQuestion(String name);

  ResponseEntity<QuizResponseDto> addQuestionToQuiz(String name, QuestionRequestDto questionRequestDto);

  ResponseEntity<QuestionResponseDto> deleteQuestionFromQuiz(String name, long questionID);
}

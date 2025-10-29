package com.cooksys.quiz_api.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.entities.Answer;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.entities.Quiz;
import com.cooksys.quiz_api.mappers.AnswerMapper;
import com.cooksys.quiz_api.mappers.QuestionMapper;
import com.cooksys.quiz_api.repositories.AnswerRepository;
import com.cooksys.quiz_api.repositories.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.mappers.QuizMapper;
import com.cooksys.quiz_api.repositories.QuizRepository;
import com.cooksys.quiz_api.services.QuizService;

import lombok.RequiredArgsConstructor;

import javax.swing.text.html.Option;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

  private final QuizRepository quizRepository;
  private final QuestionRepository questionRepository;
  private final AnswerRepository answerRepository;
  private final QuizMapper quizMapper;
  private final QuestionMapper questionMapper;
  private final AnswerMapper answerMapper;


  @Override
  public List<QuizResponseDto> getAllQuizzes() {
    // Get all non-deleted quizzes from the repository.
    // Map the quizzes to DTOs and return them.
    return quizMapper.entitiesToDtos(quizRepository.findAllByDeletedFalse());
  }

  @Override
  public ResponseEntity<QuizResponseDto> createQuiz(QuizRequestDto quizRequestDto) {
    if(quizRequestDto.getName() == null || quizRequestDto.getQuestions() == null){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    List<Question> questions = quizRequestDto.getQuestions();
    Quiz toSaveQuiz;
    Optional<Quiz> deletedQuiz = quizRepository.findByNameIgnoreCaseAndDeletedTrue(quizRequestDto.getName());
    Optional<Quiz> existingQuiz = quizRepository.findByNameIgnoreCaseAndDeletedFalse(quizRequestDto.getName());
    if(existingQuiz.isPresent()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    else if(deletedQuiz.isPresent()){
      toSaveQuiz = deletedQuiz.get();
      toSaveQuiz.setDeleted(false);
    }
    else{
      toSaveQuiz = quizRepository.saveAndFlush(quizMapper.dtoToEntity(quizRequestDto));
    }

    for(Question q: questions){
      List<Answer> answers = q.getAnswers();
      q.setQuiz(toSaveQuiz);
      questionRepository.saveAndFlush(q);
      for(Answer a : answers){
        a.setQuestion(q);
        answerRepository.saveAndFlush(a);
      }
    }
    return new ResponseEntity<>(quizMapper.entityToDto(toSaveQuiz), HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<QuizResponseDto> deleteQuiz(String name) {
    Optional<Quiz> optionalQuizToDelete = quizRepository.findByNameIgnoreCaseAndDeletedFalse(name);
    if(optionalQuizToDelete.isEmpty()){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    Quiz preDeletedQuiz = optionalQuizToDelete.get();
    Quiz quizToDelete = optionalQuizToDelete.get();
    List<Question> questionsToDelete = quizToDelete.getQuestions();
    for(Question q: questionsToDelete){
      q.setDeleted(true);
      questionRepository.saveAndFlush(q);
    }
    quizToDelete.setDeleted(true);
    quizRepository.saveAndFlush(quizToDelete);
    return new ResponseEntity<>(quizMapper.entityToDto(preDeletedQuiz), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<QuizResponseDto> updateQuizName(String name, String newName) {
    Optional<Quiz> optionalQuizToUpdate = quizRepository.findByNameIgnoreCaseAndDeletedFalse(name);
    if(optionalQuizToUpdate.isEmpty()){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    Quiz quizToUpdate = optionalQuizToUpdate.get();
    quizToUpdate.setName(newName);
    return new ResponseEntity<>(quizMapper.entityToDto(quizRepository.saveAndFlush(quizToUpdate)), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<QuestionResponseDto> getRandomQuestion(String name) {
    Optional<Quiz> optionalQuizToRead = quizRepository.findByNameIgnoreCaseAndDeletedFalse(name);
    if(optionalQuizToRead.isEmpty()){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    Quiz quizToRead = optionalQuizToRead.get();
    List<Question> questions = questionRepository.findAllByQuizIdAndDeletedFalse(quizToRead.getId());
    if(questions.isEmpty()){
      return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }
    Random random = new Random();
    int index = random.nextInt(questions.size());
    Question picked = questions.get(index);
    return new ResponseEntity<>(questionMapper.entityToDto(picked), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<QuizResponseDto> addQuestionToQuiz(String name, QuestionRequestDto questionRequestDto) {
    Optional<Quiz> optionalQuizToUpdate = quizRepository.findByNameIgnoreCaseAndDeletedFalse(name);
    if(optionalQuizToUpdate.isEmpty()){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    Quiz quizToUpdate = optionalQuizToUpdate.get();
    Question questionToAdd = questionMapper.dtoToEntity(questionRequestDto);
    questionToAdd.setQuiz(quizToUpdate);
    questionRepository.saveAndFlush(questionToAdd);
    List<Answer> answers = questionToAdd.getAnswers();
    for(Answer a: answers) {
      a.setQuestion(questionToAdd);
      answerRepository.saveAndFlush(a);
    }
    quizToUpdate.getQuestions().add(questionToAdd);
    return new ResponseEntity<>(quizMapper.entityToDto(quizRepository.saveAndFlush(quizToUpdate)), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<QuestionResponseDto> deleteQuestionFromQuiz(String name, long questionID) {
    Optional<Quiz> optionalQuiz = quizRepository.findByNameIgnoreCaseAndDeletedFalse(name);
    if(optionalQuiz.isEmpty()){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    Optional<Question> optionalQuestionToDelete = questionRepository.findByIdAndQuizIdAndDeletedFalse(questionID, optionalQuiz.get().getId());
    if(optionalQuestionToDelete.isEmpty()){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    Question questionToDelete = optionalQuestionToDelete.get();
    questionToDelete.setDeleted(true);
    return new ResponseEntity<>(questionMapper.entityToDto(questionRepository.saveAndFlush(questionToDelete)), HttpStatus.OK);
  }
}

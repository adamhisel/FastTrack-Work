package com.cooksys.quiz_api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.quiz_api.entities.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

  List<Quiz> findAllByDeletedFalse();

  Optional<Quiz> findByNameIgnoreCaseAndDeletedFalse(String name);

  Optional<Quiz> findByNameIgnoreCaseAndDeletedTrue(String name);



}

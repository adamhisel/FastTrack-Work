package com.cooksys.quiz_api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Answer {

  @Id
  @GeneratedValue
  private Long id;

  private String text;

  private boolean correct = false;

  @ManyToOne
  @JoinColumn(name = "question_id")
  private Question question;

}

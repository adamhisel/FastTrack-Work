package com.cooksys.quiz_api.entities;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Question {

  @Id
  @GeneratedValue
  private Long id;

  private String text;

  @CreationTimestamp
  private Timestamp created;

  private boolean deleted = false;

  @ManyToOne
  @JoinColumn(name = "quiz_id")
  private Quiz quiz;

  @OneToMany(mappedBy = "question")
  private List<Answer> answers;

}

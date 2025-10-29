package com.cooksys.quiz_api.dtos;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class QuizResponseDto {

  private Long id;

  private String name;

  private Timestamp created;

  public List<QuestionResponseDto> questions;

}

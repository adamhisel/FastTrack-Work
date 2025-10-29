package com.cooksys.quiz_api.dtos;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class QuestionResponseDto {

  private Long id;

  private String text;

  private Timestamp created;

  private List<AnswerResponseDto> answers;

}

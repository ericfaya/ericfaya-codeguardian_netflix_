package com.nttdata.indhub.controller.model.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChapterRest {

  private Long id;

  private Integer number;

  private String name;

  private Double duration;

  private SeasonRest season;
}

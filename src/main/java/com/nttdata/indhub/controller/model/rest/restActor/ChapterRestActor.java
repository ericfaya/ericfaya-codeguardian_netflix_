package com.nttdata.indhub.controller.model.rest.restActor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nttdata.indhub.controller.model.rest.SeasonRest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChapterRestActor {

  private Long id;

  private Integer number;

  private String name;

  private Double duration;
}

package com.nttdata.indhub.controller.model.rest.restActor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TVShowRestActor {

  private Long id;

  private String name;

  private String shortDescription;

  private String longDescription;

  private Integer year;

  private Integer recommendedAge;

  private String advertising;

}

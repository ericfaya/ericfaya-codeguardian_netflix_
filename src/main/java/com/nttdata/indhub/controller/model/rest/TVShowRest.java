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
public class TVShowRest {

  private Long id;

  private String name;

  private String shortDescription;

  private String longDescription;

  private Integer year;

  private Integer recommendedAge;

  private String advertising;

  private List<CategoryRest> categories;

}

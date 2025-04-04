package com.nttdata.indhub.controller.model.rest.restTVShow;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nttdata.indhub.controller.model.rest.TVShowRest;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryRestTVShow {

  private Long id;

  private String name;

  private List<TVShowRest> tvShows;

}

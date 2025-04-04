package com.nttdata.indhub.controller.model.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nttdata.indhub.controller.model.rest.restActor.ChapterRestActor;
import com.nttdata.indhub.controller.model.rest.restActor.TVShowRestActor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActorRest {

    private Long id;

    private String name;

    private String description;

    private List<ChapterRestActor> chapters;

    private List<TVShowRestActor> tvShows;

}

package com.nttdata.indhub.controller.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.nttdata.indhub.controller.ActorControllerRest;
import com.nttdata.indhub.controller.model.rest.ActorRest;
import com.nttdata.indhub.controller.model.rest.D4iPageRest;
import com.nttdata.indhub.controller.model.rest.D4iPaginationInfo;
import com.nttdata.indhub.controller.model.rest.NetflixResponse;
import com.nttdata.indhub.controller.model.rest.restActor.PostActorRest;
import com.nttdata.indhub.exception.NetflixException;
import com.nttdata.indhub.service.ActorService;
import com.nttdata.indhub.util.constant.CommonConstantsUtils;
import com.nttdata.indhub.util.constant.RestConstantsUtils;

@RestController
@Tag(name = "Actor", description = "Actor Controller")
@RequiredArgsConstructor
public class ActorControllerRestImpl implements ActorControllerRest {

    private final ActorService actorService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = RestConstantsUtils.RESOURCE_ACTORS, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "getAllActors", description = "Get all Actors paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    public NetflixResponse<D4iPageRest<PostActorRest>> getAllActors(
            @RequestParam(defaultValue = CommonConstantsUtils.ZERO) final int page,
            @RequestParam(defaultValue = CommonConstantsUtils.TWENTY) final int size,
            @Parameter(hidden = true) final Pageable pageable)
            throws NetflixException {
        final Page<PostActorRest> postActorRestList = actorService.getAllActors(pageable);
        return new NetflixResponse<>(HttpStatus.OK.toString(),
                String.valueOf(HttpStatus.OK.value()),
                CommonConstantsUtils.OK,
                new D4iPageRest<>(postActorRestList.getContent().toArray(PostActorRest[]::new),
                        new D4iPaginationInfo(postActorRestList.getNumber(),
                                pageable.getPageSize(),
                                postActorRestList.getTotalPages())));
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = RestConstantsUtils.RESOURCE_ACTORS + RestConstantsUtils.RESOURCE_ACTOR_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "getActorById", description = "Get Actors by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    public NetflixResponse<PostActorRest> getActorById(final Long id) throws NetflixException {
        final PostActorRest postActorRest = actorService.getActorById(id);
        return new NetflixResponse<>(HttpStatus.OK.toString(),
                String.valueOf(HttpStatus.OK.value()),
                CommonConstantsUtils.OK, postActorRest);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = RestConstantsUtils.RESOURCE_ACTORS, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "createActor", description = "Create new Actor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    public NetflixResponse<PostActorRest> createActor(
            @RequestBody final PostActorRest actor) throws NetflixException {
        final PostActorRest actorRest = actorService.createActor(actor);
        return new NetflixResponse<>(HttpStatus.OK.toString(),
                String.valueOf(HttpStatus.OK.value()),
                CommonConstantsUtils.OK, actorRest);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = RestConstantsUtils.RESOURCE_ACTORS + RestConstantsUtils.RESOURCE_ACTOR_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "updateActor", description = "Update an existing Actor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    public NetflixResponse<PostActorRest> updateActor(@RequestBody final PostActorRest actor) throws NetflixException {
        final PostActorRest actorRest = actorService.updateActor(actor, actor.getId());
        return new NetflixResponse<>(HttpStatus.OK.toString(),
                String.valueOf(HttpStatus.OK.value()),
                CommonConstantsUtils.OK, actorRest);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = RestConstantsUtils.RESOURCE_ACTORS + RestConstantsUtils.RESOURCE_ACTOR_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "deleteActor", description = "Delete an existing Actor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    public NetflixResponse<Object> deleteActor(@RequestParam final Long id) throws NetflixException {
        actorService.deleteActor(id);
        return new NetflixResponse<>(HttpStatus.OK.toString(),
                String.valueOf(HttpStatus.OK.value()),
                CommonConstantsUtils.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = RestConstantsUtils.RESOURCE_ACTORS_LARGE + RestConstantsUtils.RESOURCE_ACTOR_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "getActorInfoById", description = "Get Actors TVShows and Chapters by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @Override
    public NetflixResponse<ActorRest> getActorTVShowsChapters(final Long id) throws NetflixException {
        final ActorRest actorRest = actorService.getActorTVShowsChapters(id);
        return new NetflixResponse<>(HttpStatus.OK.toString(),
                String.valueOf(HttpStatus.OK.value()),
                CommonConstantsUtils.OK, actorRest);
    }
}
